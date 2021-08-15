package com.siteminder.mailclient.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.siteminder.mailclient.model.ApiResponse;
import com.siteminder.mailclient.model.MailModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SendGridClientService implements GenericMailClientService {
    private SendGrid sendGridClient;

    @Autowired
    public SendGridClientService(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;
    }

    @Override
    public ApiResponse send(MailModel mailModel) throws IOException {

        log.info("Invoking SendGrid service");
        Mail mail = new Mail();
        final ApiResponse clientResponse = new ApiResponse();

        Email from = new Email(mailModel.getSender());
        Personalization personalization = getPersonilizationForMail(mailModel);
        Content content = new Content("text/plain", mailModel.getContent());
        mail.addPersonalization(personalization);
        mail.setFrom(from);
        mail.addContent(content);

        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridClient.api(request);
            log.info("Response from sendgrid {}" , response.getBody());
            clientResponse.setCode(response.getStatusCode());
            clientResponse.setMessage(response.getBody());
        } catch (IOException ex) {
            log.error("Error calling SendGrid service for sending mail ");
            clientResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return clientResponse;
    }

    private Personalization getPersonilizationForMail(MailModel mailModel) {

        Personalization personalization = new Personalization();
        List<String> recipients = Objects.nonNull(mailModel.getRecipients()) ? mailModel.getRecipients() : null;
        List<String> ccs = Objects.nonNull(mailModel.getCcs()) ? mailModel.getCcs() : null;
        List<String> bccs = Objects.nonNull(mailModel.getBccs()) ? mailModel.getBccs() : null;
        personalization.setSubject(Objects.nonNull(mailModel.getSubject()) ? mailModel.getSubject() : null);

        for (String recipient : recipients) {
            Email to = new Email(recipient);
            personalization.addTo(to);
        }
        if(ccs != null) {
            for (String ccSource : ccs) {
                Email cc = new Email(ccSource);
                personalization.addCc(cc);
            }
        }
        if(bccs != null) {
            for (String bccSource : bccs) {
                Email bcc = new Email(bccSource);
                personalization.addBcc(bcc);
            }
        }

        return personalization;
    }


}
