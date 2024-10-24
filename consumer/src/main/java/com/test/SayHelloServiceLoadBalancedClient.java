package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@LoadBalancerClient(value = "say-hello", configuration = SayHelloServiceConfiguration.class)
public class SayHelloServiceLoadBalancedClient {

    @Autowired
    RestTemplate restTemplate;

    public String greetings() {
        return restTemplate.getForObject("http://say-hello/hello", String.class);
    }
}