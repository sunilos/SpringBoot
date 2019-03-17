package com.sunilos.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Starts spring boot application
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@SpringBootApplication

/**
 * Auto discover beans inside com.sunilos.springboot package. By default current
 * package of this class is scanned
 */
@ComponentScan(basePackages = { "com.sunilos.springboot" })

@EnableScheduling

public class SOSApp {

	@Autowired
	private static Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SOSApp.class, args);
	}

	/**
	 * Enables CORS to all urls 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//registry.addMapping("/**").allowedOrigins("http://localhost:8080");
				registry.addMapping("/**");
			}
		};
	}
}
