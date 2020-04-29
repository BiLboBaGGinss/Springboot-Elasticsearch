package com.springboot.elasticsearch.es.service.impl;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.elasticsearch.es.service.ClusterService;

/**
 * @Author: BilBo BaGGins
 * @Date: 2020/4/29 17:18
 */
@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public JSONObject clusterHealth() {
        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = null;
        try {
            response = client.cluster().health(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject res = JSONObject.parseObject(response.toString());
        return res;
    }
}
