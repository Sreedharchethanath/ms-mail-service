package com.siteminder.mailclient.service;

import com.siteminder.mailclient.model.ApiResponse;
import com.siteminder.mailclient.model.MailModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationMailClientServiceImpl implements NotificationService {
    private List<GenericMailClientService> emailClients;

    public NotificationMailClientServiceImpl(
            MailGunClientService mailGunClient,
            SendGridClientService sendGridClient
    ) {
        emailClients = new ArrayList<>(2);
        emailClients.add(mailGunClient);
        emailClients.add(sendGridClient);
    }

    public ApiResponse sendNotifications(MailModel model) throws IOException {
        ApiResponse response = null;

        for(GenericMailClientService client : emailClients) {
            response = client.send(model);
            if (response ==  null) {
                log.error("Failed getting a response using client {} ", client.getClass().getSimpleName());
                continue;
            }
            if (response.getCode() == HttpStatus.OK.value()) {
                break;
            }

        }
        return null;
    }
}
