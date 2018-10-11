package com.premierinc.webanalytics.druidry.client;

public enum DruidQueryProtocol {
    HTTP("http"),
    HTTPS("https");

    private String protocol;

    DruidQueryProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return this.protocol;
    }
}
