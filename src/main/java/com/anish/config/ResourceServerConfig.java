package com.anish.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @author anishkumarss
 *
 * @since 28-Feb-2023
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String ADMIN_SCOPE = "#oauth2.hasScope('admin')";
	private static final String PRIVILEGED_CLIENT_PATTERN = "/privileged-client/**";
	/*
	 * private static final String RESOURCE_ID = "resource_id";
	 * 
	 * @Override public void configure(ResourceServerSecurityConfigurer resources) {
	 * resources.resourceId(RESOURCE_ID).stateless(false); }
	 */

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
				.antMatchers(PRIVILEGED_CLIENT_PATTERN).and().authorizeRequests()
				.antMatchers(PRIVILEGED_CLIENT_PATTERN).access(ADMIN_SCOPE);
	}

}
