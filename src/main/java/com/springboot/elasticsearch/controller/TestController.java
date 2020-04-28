package com.springboot.elasticsearch.controller;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springboot.elasticsearch.config.EsConfig;

/**
 * @author BilBo BaGGins
 * 测试
 */

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RestHighLevelClient client;

    @GetMapping("/health")
    @ResponseBody
    public JSONObject health() throws IOException {
        //集群健康状况
        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        JSONObject res = JSONObject.parseObject(response.toString());
        System.out.println(res.toString(SerializerFeature.PrettyFormat));
        return res;
    }
}
