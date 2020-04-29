package com.springboot.elasticsearch.es.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.springboot.elasticsearch.es.config.properties.EsProperties;

/**
 * @author BilBo BaGGins
 */
@Configuration
public class EsConfig {

    @Resource
    private EsProperties esp;

    private static String schema;
    private static String hosts;
    private static int connectTimeOut;
    private static int socketTimeOut;
    private static int connectionRequestTimeOut;
    private static int maxConnectNum;
    private static int maxConnectPerRoute;

    /**
     * 创建RestHighLevelClient
     * @return
     * @throws Exception
     */
    @Bean
    public RestHighLevelClient client() throws Exception {
        //读取配置
        readProperties();
        RestClientBuilder builder = RestClient.builder(getHostArray(hosts));
        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public Builder customizeRequestConfig(Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });
        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    /**
     * 地址处理
     * @param esServerHost
     * @return
     * @throws Exception
     */
    private HttpHost[] getHostArray(String esServerHost) throws Exception {
        List<HttpHost> hosts=new ArrayList<HttpHost>();
        String[] hostArray1 = esServerHost.split(",");

        for(String host:hostArray1) {
            String[] ipPort= host.split(":");
            HttpHost hostNew =new HttpHost(ipPort[0],Integer.valueOf(ipPort[1]),schema);
            hosts.add(hostNew);
        }
        return hosts.toArray(new HttpHost[] {});
    }

    /**
     * 读取配置
     */
    private void readProperties(){
        System.out.println("esp: "+esp);
       schema = esp.getSchema();
       hosts = esp.getAddress();
       connectTimeOut = esp.getConnectTimeOut();
       socketTimeOut = esp.getSocketTimeOut();
       connectionRequestTimeOut = esp.getConnectionRequestTimeOut();
       maxConnectNum = esp.getMaxConnectNum();
       maxConnectPerRoute = esp.getMaxConnectNum();
    }
}
