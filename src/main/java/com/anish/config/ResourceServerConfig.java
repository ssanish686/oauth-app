package com.anish.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author anishkumarss
 *
 * @since 28-Feb-2023
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements WebMvcConfigurer {
	private static final String PUBLIC_URL = "/public/**";

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests()
				.antMatchers(PUBLIC_URL).permitAll()
				.anyRequest().authenticated();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("GET")
				.allowedHeaders("*")
				.allowedOrigins("*")
				.exposedHeaders("Access-Control-Allow-Origin");
	}

}
