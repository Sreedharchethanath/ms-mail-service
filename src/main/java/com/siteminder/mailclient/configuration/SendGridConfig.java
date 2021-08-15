package com.siteminder.mailclient.configuration;

import com.sendgrid.SendGrid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Data
public class SendGridConfig {

    @Value("${app.sendgrid.api.key}")
    private String apiKey;

    @Value("${app.sendgrid.base.url}")
    private String url;


    @Bean
    public SendGrid sendGridClient() {
        return new SendGrid(apiKey);
    }

    /*@Bean(name = "SendGridClient")
    public WebClient sendGridClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(url)
                .build();
    }*/


}
