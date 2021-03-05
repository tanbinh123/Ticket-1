package com.woniuxy.gateway;

import com.woniuxy.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 网关过滤器
 * 过滤在 yml 中进行了配置的路由
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Auth Filter");
        String path = exchange.getRequest().getURI().getPath();
        String jwt = exchange.getRequest().getHeaders().getFirst("jwt");

        ResponseResult<?> result;
        if (Objects.equals(path, "/user/login")) {
            result = authService.login("110", "123123");
        } else {
            result = authService.auth(path, jwt);
        }


        if (!(result.getCode() == 200)) return authError(exchange.getResponse(), "error");

        return chain.filter(exchange);
    }

    private Mono<Void> authError(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        DataBuffer buffer = resp.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    /**
     * 配置过滤器顺序
     *
     * @return 相对顺序
     */
    @Override
    public int getOrder() {
        return -100;
    }
}
