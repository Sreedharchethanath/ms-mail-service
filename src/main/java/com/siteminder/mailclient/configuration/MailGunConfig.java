package com.siteminder.mailclient.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Data
@Configuration
public class MailGunConfig {
    @Value("${app.mailgun.api.key}")
    private String apiKey;

    @Value("${app.mailgun.base.url}")
    private String url;



}
