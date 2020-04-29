package com.springboot.elasticsearch.es.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.springboot.elasticsearch.es.service.ClusterService;

/**
 * @author BilBo BaGGins
 */

@Controller
@RequestMapping("/cluster")
public class ClusterController {
    @Autowired
    private ClusterService clusterService;

    /**
     * 集群健康状况
     * @return
     * @throws IOException
     */
    @GetMapping("/health")
    @ResponseBody
    public JSONObject health() throws IOException {
        JSONObject res = clusterService.clusterHealth();
        return res;
    }
}
