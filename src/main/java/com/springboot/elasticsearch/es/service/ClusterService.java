package com.springboot.elasticsearch.es.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: BilBo BaGGins
 * @Date: 2020/4/29 17:17
 */
public interface ClusterService {
    /**
     * 检查集群健康状况
     * @return
     */
    public JSONObject clusterHealth();
}
