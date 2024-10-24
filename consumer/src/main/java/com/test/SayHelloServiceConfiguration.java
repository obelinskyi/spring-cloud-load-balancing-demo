package com.test;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.WeightFunction;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SayHelloServiceConfiguration {

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
//        ServiceInstanceListSupplier serviceInstanceListSupplier = ServiceInstanceListSuppliers.from("say-hello",
//                new DefaultServiceInstance("myservice1", "say-hello", "localhost", 8081, false),
//                new DefaultServiceInstance("myservice2", "say-hello", "localhost", 8082, false),
//                new DefaultServiceInstance("myservice3", "say-hello", "localhost", 8083, false)
//        );

        return ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                .withWeighted(weightFunctionCpu())
                .build(context);
    }

//    @Bean
    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplier) {
        return new RandomLoadBalancer(serviceInstanceListSupplier, "say-hello");

    }


    private WeightFunction weightFunction() {
        return instance -> {
            if (instance.getPort() == 8081) {
                return 40;
            } else if (instance.getPort() == 8082) {
                return 40;
            } else if (instance.getPort() == 8083) {
                return 10;
            } else {
                return 0;
            }
        };
    }


    private WeightFunction weightFunctionCpu() {
        return instance -> {
            String cpu = instance.getMetadata().get("cpu");
            System.out.println(instance.getInstanceId() + "'s CPU is " + cpu);
            return Integer.parseInt(cpu);
        };
    }
}