package com.premierinc.webanalytics.druidry.client.exception;

public class DruidryException extends Exception {

    protected DruidryException(Exception e) {
        super(e);
    }

    protected DruidryException(String message) {
        super(message);
    }
}