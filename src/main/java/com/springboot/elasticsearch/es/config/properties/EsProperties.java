package com.springboot.elasticsearch.es.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * ES配置
 * @author BilBo BaGGins
 */
@Data
@Component
@ConfigurationProperties(prefix = "es")
public class EsProperties {
    private String schema;
    private String address;
    private int connectTimeOut;
    private int socketTimeOut;
    private int connectionRequestTimeOut;
    private int maxConnectNum;
}
