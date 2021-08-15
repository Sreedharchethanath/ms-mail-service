package com.siteminder.mailclient.service;

import com.siteminder.mailclient.model.ApiResponse;
import com.siteminder.mailclient.model.MailModel;

import java.io.IOException;

public interface NotificationService {
   ApiResponse sendNotifications(MailModel model) throws IOException;
}
