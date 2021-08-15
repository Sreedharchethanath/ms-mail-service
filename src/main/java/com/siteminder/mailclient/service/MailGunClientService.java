package com.siteminder.mailclient.service;

import com.siteminder.mailclient.model.ApiResponse;
import com.siteminder.mailclient.model.MailModel;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MailGunClientService implements GenericMailClientService{

    @Value("${app.mailgun.api.key}")
    private String apiKey;

    @Value("${app.mailgun.base.url}")
    private String url;

    private OkHttpClient client;

    public MailGunClientService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.authenticator((route, response) -> {
            final String credential = Credentials.basic("api", apiKey);
            return response.request().newBuilder().header("Authorization", credential).build();
        });

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);

        client = builder.build();
    }



    @Override
    public ApiResponse send(MailModel mailModel) throws IOException {

        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(url)
                .post(map(mailModel).build())
                .build();

        Response response = client.newCall(request).execute();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(response.code());
        apiResponse.setMessage(response.body().string());
        return apiResponse;
    }

   /* private MultiValueMap<String, String> getPostRequestObject(String from, String to, String subject) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("from", from);
        map.add("to", to);
        map.add("subject", subject);
        return map;
    }*/

    MultipartBody.Builder map(MailModel mailModel) {
        List<String> recipients = mailModel.getRecipients();
        final MultipartBody.Builder mailBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("from", mailModel.getSender())
                .addFormDataPart("to", recipients.stream().collect(Collectors.joining(",")))
                .addFormDataPart("text", mailModel.getContent());


        if (mailModel.getSubject() != null) {
            mailBodyBuilder.addFormDataPart("subject", mailModel.getSubject());
        }

        return mailBodyBuilder;
    }
}
