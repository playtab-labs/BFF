package com.playtab.bff.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clients.content-service")
public class ContentServiceProperties {

    private String host = "localhost";
    private int port = 9096;
    private boolean plaintext = true;
    private long deadlineSeconds = 5L;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isPlaintext() {
        return plaintext;
    }

    public void setPlaintext(boolean plaintext) {
        this.plaintext = plaintext;
    }

    public long getDeadlineSeconds() {
        return deadlineSeconds;
    }

    public void setDeadlineSeconds(long deadlineSeconds) {
        this.deadlineSeconds = deadlineSeconds;
    }
}
