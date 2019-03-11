package com.sunilos.springboot.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configure and register Interceptors
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private FrontCtl frontCtl;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(frontCtl);
	}

}
