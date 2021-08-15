package com.siteminder.mailclient.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MailModel {
    private String sender;
    private List<String> recipients;
    private List<String> ccs;
    private List<String> bccs;
    private String subject;
    private String content;
}
