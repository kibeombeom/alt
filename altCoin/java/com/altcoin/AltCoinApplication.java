package com.altcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.altcoin.base.interceptor.APIInterceptor;

@SpringBootApplication
@EnableScheduling
public class AltCoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltCoinApplication.class, args);
	}

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new APIInterceptor()).addPathPatterns("/**").excludePathPatterns("/login**");
			}
		};
	}

}
