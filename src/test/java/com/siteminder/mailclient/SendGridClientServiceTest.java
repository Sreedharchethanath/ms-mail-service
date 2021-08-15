package com.siteminder.mailclient;

import com.sendgrid.SendGrid;

import com.siteminder.mailclient.model.ApiResponse;
import com.siteminder.mailclient.model.MailModel;
import com.siteminder.mailclient.service.SendGridClientService;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class SendGridClientServiceTest {

    private SendGridClientService service ;


    @Test
    public void testMailToSingleRecipient() throws IOException {
        service = new SendGridClientService(new SendGrid(null));
        MailModel testMail = new MailModel();
        testMail.setSender("sreedhars966@gmail.com");
        testMail.setSubject("Test Mail");
        List<String> toEmails = new ArrayList<>(1);
        toEmails.add("sreedhars966@gmail.com");
        testMail.setRecipients(toEmails);
        testMail.setContent("THis is a test mail");
        ApiResponse response  = service.send(testMail);
        assertEquals(HttpStatus.OK.value(), response.getCode());
    }

}
