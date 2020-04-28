package com.springboot.elasticsearch.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "es")
public class EsProperties {
    /**
     * 节点地址,若是集群用逗号隔开如: 192.168.1.1:9200,192.168.1.2:9200,
     */
    private String address;
    private int maxRetryTimeoutMillis;
    private int connectTimeout;
    private int socketTimeout;

}
