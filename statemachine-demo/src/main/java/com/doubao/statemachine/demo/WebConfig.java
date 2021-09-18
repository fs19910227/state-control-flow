package com.doubao.statemachine.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * cors config
 *
 * @author doubao
 * @date 2021/05/25 22:02
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	/**
	 * 跨域支持
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(false).maxAge(3600);
	}
}
