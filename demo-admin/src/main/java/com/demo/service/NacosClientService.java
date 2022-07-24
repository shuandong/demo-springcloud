package com.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shu
 * @description
 * @date 2022-07-24  22:49
 */
@Slf4j
@Service
public class NacosClientService {

    private final DiscoveryClient discoveryClient;

    public NacosClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }


    public List<ServiceInstance> getNacosClientInfo(String serviceId) {
        log.debug("serviceId 为 {}" + serviceId);

        return discoveryClient.getInstances(serviceId);

    }
}
