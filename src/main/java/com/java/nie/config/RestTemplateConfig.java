package com.java.nie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {



    @Bean
    public RestTemplate createRestTemplate(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(3000);
        httpRequestFactory.setConnectTimeout(3000);
        httpRequestFactory.setReadTimeout(20000);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.setInterceptors(Collections.singletonList(logClientHttpRequestInterceptor()));
        return  restTemplate ;
    }

    @Bean
    public LogClientHttpRequestInterceptor logClientHttpRequestInterceptor (){
        return new LogClientHttpRequestInterceptor();
    }

}
