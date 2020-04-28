package com.springboot.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Configuration
public class EsConfig {

    private static String hosts = "127.0.0.1:9200"; // 集群地址，多个用,隔开
    private static int port = 9200; // 使用的端口号
    private static String schema = "http"; // 使用的协议
    private static ArrayList hostList = null;

    private static int connectTimeOut = 1000; // 连接超时时间
    private static int socketTimeOut = 30000; // 连接超时时间
    private static int connectionRequestTimeOut = 500; // 获取连接的超时时间

    private static int maxConnectNum = 100; // 最大连接数
    private static int maxConnectPerRoute = 100; // 最大路由连接数

    public static HttpHost[] getHostArray(String esServerHost) throws Exception {
        List<HttpHost> hosts=new ArrayList<HttpHost>();
        String[] hostArray1 = esServerHost.split(",");

        for(String host:hostArray1) {
            String[] ipPort= host.split(":");
            HttpHost hostNew =new HttpHost(ipPort[0],Integer.valueOf(ipPort[1]),schema);
            hosts.add(hostNew);
        }
        return hosts.toArray(new HttpHost[] {});
    }

    @Bean
    public RestHighLevelClient client() throws Exception {
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
        //集群健康状况
        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        JSONObject res = JSONObject.parseObject(response.toString());
        System.out.println(res.toString(SerializerFeature.PrettyFormat));
        return client;
    }

}
