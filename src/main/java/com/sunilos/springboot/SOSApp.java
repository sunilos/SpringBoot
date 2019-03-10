package com.sunilos.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication

/**
 * Auto discover beans inside com.sunilos.springboot package. By default current
 * package of this class is scanned
 */
@ComponentScan(basePackages = { "com.sunilos.springboot" })

/**
 * Starts spring boot application
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */
public class SOSApp {

	@Autowired
	private static Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SOSApp.class, args);
	}
}
