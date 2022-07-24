package com.demo.controller;

import com.demo.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shu
 * @description
 * @date 2022-07-24  22:52
 */
@RestController
@Slf4j
@RequestMapping("/nacos")
public class NacosLearningController {

    private final NacosClientService nacosClientService;

    public NacosLearningController(NacosClientService nacosClientService) {
        this.nacosClientService = nacosClientService;
    }

    @RequestMapping("/getById")
    public List<ServiceInstance> getServiceById(@RequestParam String serviceId) {
        return nacosClientService.getNacosClientInfo(serviceId);

    }
}
