package com.anish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuthServerConfig extends AuthorizationServerConfigurerAdapter{
	@Value("${admin.client.id}")
	private String adminClientId;
	@Value("${admin.client.secret}")
	private String adminClientSecret;

	@Value("${user.client.id}")
	private String userClientId;
	@Value("${user.client.secret}")
	private String userClientSecret;

	@Value("${jwt.signing-key}")
	private String privateKey;

	@Value("${jwt.verifier-key}")
	private String publicKey;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter jwtAccesTokenConvertor() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
		// By default while using JwtTokenStore, AuthenticationPrincipal (mainly used in
		// controller end point) contains only the userName. By setting
		// UserAuthenticationConverter here, we can get all the UserDetails from
		// AuthenticationPrincipal.
		/*((DefaultAccessTokenConverter) converter.getAccessTokenConverter())
				.setUserTokenConverter(userAuthenticationConverter());*/
		return converter;
	}

	/*@Bean
	public UserAuthenticationConverter userAuthenticationConverter() {
		DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
		// defaultUserAuthenticationConverter.setUserDetailsService(customDetailsService);
		return defaultUserAuthenticationConverter;
	}*/

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(jwtAccesTokenConvertor());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(jwtAccesTokenConvertor());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// two clients
		clients.inMemory().withClient(userClientId).secret(userClientSecret).scopes("user")
				.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(36000)
				.refreshTokenValiditySeconds(72000)
				.and()
				.withClient(adminClientId).secret(adminClientSecret).scopes("admin")
				.authorizedGrantTypes("client_credentials").accessTokenValiditySeconds(7200);
	}
}