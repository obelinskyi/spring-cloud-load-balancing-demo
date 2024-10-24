package com.test;

//import com.netflix.appinfo.ApplicationInfoManager;

import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class SayHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SayHelloApplication.class, args);
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(10000);
        return String.format("Hello from instance at %s", port);
    }


    @Autowired
    private ApplicationInfoManager aim;

    @GetMapping("update_cpu")
    public void updateCpu(@RequestParam String cpu) {
        Map<String, String> metadata = aim.getInfo().getMetadata();
        metadata.put("cpu", cpu);
        aim.registerAppMetadata(metadata);
    }
}