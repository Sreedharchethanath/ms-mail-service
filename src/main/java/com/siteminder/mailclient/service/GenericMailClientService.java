package com.siteminder.mailclient.service;

import com.siteminder.mailclient.model.ApiResponse;
import com.siteminder.mailclient.model.MailModel;

import java.io.IOException;

public interface GenericMailClientService {
    ApiResponse send(MailModel mailModel) throws IOException;
}
