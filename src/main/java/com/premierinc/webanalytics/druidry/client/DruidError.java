package com.premierinc.webanalytics.druidry.client;

import lombok.Getter;

@Getter
public class DruidError {

    private String error;
    private String errorMessage;
    private String errorClass;
    private String host;
}