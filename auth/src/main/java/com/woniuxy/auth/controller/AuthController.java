package com.woniuxy.auth.controller;

import com.woniuxy.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class AuthController {
    @Value("${server.port}")
    private int port;

    @RequestMapping("/auth")
    public ResponseResult<?> auth(String url, String jwt) throws URISyntaxException {
        //发送HTTP请求到本项目，会经过Shiro过滤器
        URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(port).setPath(url).build();
        // build http headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("jwt", jwt);
        RestTemplate restTemplate = new RestTemplate();
        // send request and get response
        ResponseResult<?> result = restTemplate.postForObject(uri, new HttpEntity<String>(headers), ResponseResult.class);
        log.info(String.valueOf(result));
        return result;
    }
}
