package com.premierinc.webanalytics.druidry.client;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DruidConfiguration {

    private static final int DEFAULT_HTTP_PORT = 8082;
    private static final int DEFAULT_HTTPS_PORT = 8282;

    /**
     * Protocol by which Druid Broker is accessible
     * Defaults to HTTP
     */
    private DruidQueryProtocol protocol;

    /**
     * Address of Druid Broker Instance
     */
    private String host;

    /**
     * Port at which Druid Broker is listening.
     * {@value DEFAULT_HTTP_PORT} if protocol is 8082
     * {@value DEFAULT_HTTPS_PORT} if protocol is 8282
     */
    private Integer port;

    /**
     * Endpoint (without host address) at which query needs to be fired
     */
    private String endpoint;

    /**
     * Number of connections to be maintained in connection pool
     * Is Ignored when custom JerseyConfig is passed.
     */
    private Integer concurrentConnectionsRequired;

    private String username;

    private String password;

    @Builder
    private DruidConfiguration(DruidQueryProtocol protocol,
                               String host,
                               Integer port,
                               String endpoint,
                               Integer concurrentConnectionsRequired,
                               String username,
                               String password) {

        if (StringUtils.isEmpty(host)) {
            throw new IllegalArgumentException("Host cannot be null or empty");
        }

        if (port != null && port < 0) {
            throw new IllegalArgumentException("Port cannot be negative");
        }

        if (concurrentConnectionsRequired != null && concurrentConnectionsRequired < 1) {
            throw new IllegalArgumentException("Connections required cannot be less than 1");
        }

        if (protocol == null) {
            protocol = DruidQueryProtocol.HTTP;
        }

        if (port == null) {
            port = getDefaultPortOnBasisOfProtocol(protocol);
        }

        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.endpoint = endpoint;
        this.concurrentConnectionsRequired = concurrentConnectionsRequired;
        this.username = username;
        this.password = password;
    }

    protected String getUrl() {

        String endpoint = this.getEndpoint();
        if (endpoint == null) {
            endpoint = "";
        }

        return String.format("%s://%s:%d/%s",
                this.getProtocol(),
                this.getHost(),
                this.getPort(),
                endpoint);
    }

    private Integer getDefaultPortOnBasisOfProtocol(DruidQueryProtocol protocol) {

        switch (protocol) {
            case HTTP:
                return DEFAULT_HTTP_PORT;
            case HTTPS:
                return DEFAULT_HTTPS_PORT;
            default:
                throw new IllegalArgumentException("Druid Query Protocol not handled");
        }
    }
}