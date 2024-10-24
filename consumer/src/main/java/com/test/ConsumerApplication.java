package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerApplication {

    private final SayHelloServiceLoadBalancedClient sayHelloServiceLoadBalancedClient;

    public ConsumerApplication(SayHelloServiceLoadBalancedClient sayHelloServiceLoadBalancedClient) {
        this.sayHelloServiceLoadBalancedClient = sayHelloServiceLoadBalancedClient;
    }

    @GetMapping("/consume")
    public String user() {
        return sayHelloServiceLoadBalancedClient.greetings();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
