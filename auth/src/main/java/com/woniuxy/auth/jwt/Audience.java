package com.woniuxy.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "audience")
@Component
@Getter
@Setter
public class Audience {
	private String clientId;
	private String base64Secret;
	private String name;
	private int expiresSecond;

}
