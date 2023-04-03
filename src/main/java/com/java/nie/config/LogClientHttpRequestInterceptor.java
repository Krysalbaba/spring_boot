package com.java.nie.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author nie
 * restTemplate拦截器
 */
@Slf4j
public class LogClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {




    @Resource
    private RedisTemplate<String,Object> redisTemplate ;


    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        traceResponse(httpRequest,bytes,response);
        return response;
    }

    private void traceResponse(HttpRequest request,byte[] bytes,ClientHttpResponse response) throws IOException {

        int value = response.getStatusCode().value();
//        if (200 != value) {
            String url = request.getURI().toString();
            if (url.contains("")){

                String statusKey =  "" ;
                String recordKey =  "" ;
                if (!redisTemplate.hasKey(statusKey)){
                    redisTemplate.opsForValue().set(statusKey,1,2, TimeUnit.MINUTES);
                }

                StringBuilder inputStringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(),StandardCharsets.UTF_8));

                String line  = bufferedReader.readLine();
                while (line!=null){
                    inputStringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }

                // 记录日志   请求时间  、 路径  、返回报文  、状态码
               /* ThirdPartyRecord record = new ThirdPartyRecord();
                record.setDateTime(StatUtils.timestampToDateFormat(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
                record.setCode(value);
                record.setUrl(url);
                record.setRequest(new String(bytes, StandardCharsets.UTF_8));
                record.setResponse(inputStringBuilder.toString());
                redisTemplate.opsForList().leftPush(recordKey,record);
                redisTemplate.expire(recordKey,2,TimeUnit.MINUTES);*/

            }
            log.info("请求错误地址 {} ,状态码: {}" ,url ,value);
//        }

    }

}
