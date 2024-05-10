package com.anish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OAuthServerConfig extends AuthorizationServerConfigurerAdapter{
	@Value("${api.client.id}")
	private String apiClientId;
	@Value("${api.client.secret}")
	private String apiClientSecret;

	@Value("${jwt.signing-key}")
	private String privateKey;

	@Value("${jwt.verifier-key}")
	private String publicKey;

	@Value("${jwt.issuer}")
	private String jwtIssuer;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConvertor() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConvertor());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(List.of((token, auth) -> {
			String clientId = auth.getOAuth2Request().getClientId();
			Map<String, Object> additionalInfo = new HashMap<>();
			additionalInfo.put("iss",jwtIssuer);
			if(clientId.equals(apiClientId)) {
				additionalInfo.put("user_name", httpServletRequest.getHeader("User-Name"));
				additionalInfo.put("user_id", httpServletRequest.getHeader("User-Id"));
				additionalInfo.put("user_provider", httpServletRequest.getHeader("User-Provider"));
				additionalInfo.put("authorities", httpServletRequest.getHeader("User-Role") != null ?
						Arrays.stream(httpServletRequest.getHeader("User-Role").split("\\s*,\\s*")).toList() :
						Collections.emptyList());
			}
			((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionalInfo);
			return token;
		}, jwtAccessTokenConvertor()));
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				//.accessTokenConverter(jwtAccesTokenConvertor())
				.tokenEnhancer(tokenEnhancerChain);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// single clients
		clients.inMemory().withClient(apiClientId).secret(apiClientSecret).scopes("api")
				.authorizedGrantTypes("client_credentials").accessTokenValiditySeconds(36000);
	}


}