package com.example.demo.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;


import java.util.List;

@Slf4j
@Configuration
public class RetrofitConfig {

    private final DiscoveryClient discoveryClient;

    public RetrofitConfig(DiscoveryClient discoveryClient){
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(getURI("ADDRESSSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private String getURI(String serviceName) {
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);

        if (services.isEmpty()) {
            throw new IllegalStateException("No instances available for service: " + serviceName);
        }

        log.warn("INSTACNE NAME : " + services.get(0).getUri());
        return services.get(0).getUri().toString();
    }
}
