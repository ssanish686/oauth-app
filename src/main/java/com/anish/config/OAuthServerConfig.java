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
	@Value("${customer.client.id}")
	private String customerClientId;
	@Value("${customer.client.secret}")
	private String customerClientSecret;
	@Value("${partner.client.id}")
	private String partnerClientId;
	@Value("${partner.client.secret}")
	private String partnerClientSecret;
	@Value("${admin.client.id}")
	private String adminClientId;
	@Value("${admin.client.secret}")
	private String adminClientSecret;


	private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEowIBAAKCAQEA7F6NMrf1Lzo/GJ06bchqcZPIL4pULqGl+NWhpCXrYDh+/Ms4\r\n"
			+ "XY+E0lk43pThsPPNvtCMmO03EZsv+maOMOETSAfcorHxoFYZnxpCvY1SkgyRImgy\r\n"
			+ "xfmQKA3GOmDNuvnyKbiMC8IfiCS/xz53Wm5rlsmQMnnqFLLDTPgyTuJi1Q4TUQVN\r\n"
			+ "ztjezv2BoajjYZqJ/B9s24hgBWX9XfSSuS+Z2KojmG86YkS/0ykvRozPXci3jGkE\r\n"
			+ "LaZdyRDuarc94W6/68IWh0ogD2IuaLGgJlpjTuFjMLWefSLL1mPFE6Me3S2dvMMH\r\n"
			+ "SLuavNZJbqQ6mLpmuKWZjYXXkUSSa/OFmNOVzQIDAQABAoIBAFZnlewFbR5vh2Mj\r\n"
			+ "DpnVGnwcTqB6S52kP7y/s1ADAJDy9hDI5PjmnuIZ9X0AJaR+6yYmrE624FuSRC6f\r\n"
			+ "0Lizfk3ledULgU1gTAtqGR2bjKSQ7XkLaAwQ146cwmrSukHwXoK70I33z3kAHg7i\r\n"
			+ "mdS36qJ7WjfpcxVP5666SAGoJgLFZ4aSZquR6TxAYG1esH4Tyhjg7zfL6WEkrahR\r\n"
			+ "Vb452PHSCFqCMYCjo2kWGvRJIpIHQOxlrSgM4OFddMf00CsbwVfv2wx4j5BDbUaS\r\n"
			+ "AAZ5kXc6ebBAmmxecWI/vFqipEIFv+uIAC2LyUQ3Cqxo1xaJwtLuCadphYSFTBf+\r\n"
			+ "G7WVRU0CgYEA/Nkd02EiSBYxIG4qqGjNB5Bo2SyLYRxbnLda8i62wXHDRTF9XGEp\r\n"
			+ "Hde4CE8Amh2+E36zVYdQ/X2tS+WXH1i6Y25cQpyb0oIA6mBK1EeNvaSSn3hID1Ll\r\n"
			+ "lVArPWLiF3K8nOoLpVGmsTlFUhsOZ4z+BFjOXNnQ+WM8JRFd4U95gR8CgYEA71DZ\r\n"
			+ "LZOWljqEf+o1YvtoN37Rr44V0+N+dat5ERlo6/BtsJYVslVMyXViCsNQggamYxZp\r\n"
			+ "h3p9PuNWfV8pwazVhnTQnzizwJj8Hmh5iG1Uw33VffzbVoqKhEyG3zmC1rPoAwAA\r\n"
			+ "76yv/JaNOi7MmDSqBa2ijMFZtT307f9LIfyub5MCgYBwJ6ZxMq7QJvfXmMZXOVVo\r\n"
			+ "u4emfXm9s6R3WWV/83ShTeL9+DvRFE/2qs848EImGndLknHdyE4Ggw6xkb986hCT\r\n"
			+ "Nkq6SVldAMmDLP9ENhELQ8q97c6Y7X5kwtDLQ+dh20UKnU5ZIGS+S9cR2mqDSCkM\r\n"
			+ "qI+w4/UsCfaNwqxP2r7cpQKBgDeS68wksNyUHD7kMZ+ZyFrSzXjaIwGAm9ZRipzI\r\n"
			+ "7M+VlrNiNwmhu2IxKyJatvBrAOqTws/eRwV6n5QfrD+4liFSMTw4W63HGQ0qr8ZZ\r\n"
			+ "tUxshU5rkLfpFj+g0dU2ssaPIrrHnnAKBxg+Ee/H/GUAHdeRueS75cwr5hv6pATx\r\n"
			+ "e8SXAoGBAPEOBAQG065cJll02rZKpKTvgBjV0lKwrlONy5pVH5J9G6Df1m/r7fQ3\r\n"
			+ "9UUayh6zArqwXSrrqV69VLET/hhSlQT/cdzquGAqPZGbjSzcy4bos2xzrAgF5sDe\r\n"
			+ "nvupdMML4NCHcrpJvscPsmqCEijMKxGdGW0TJ8CLV1q7sl2kH6cA\r\n" + "-----END RSA PRIVATE KEY-----";
	private String publicKey = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7F6NMrf1Lzo/GJ06bchq\r\n"
			+ "cZPIL4pULqGl+NWhpCXrYDh+/Ms4XY+E0lk43pThsPPNvtCMmO03EZsv+maOMOET\r\n"
			+ "SAfcorHxoFYZnxpCvY1SkgyRImgyxfmQKA3GOmDNuvnyKbiMC8IfiCS/xz53Wm5r\r\n"
			+ "lsmQMnnqFLLDTPgyTuJi1Q4TUQVNztjezv2BoajjYZqJ/B9s24hgBWX9XfSSuS+Z\r\n"
			+ "2KojmG86YkS/0ykvRozPXci3jGkELaZdyRDuarc94W6/68IWh0ogD2IuaLGgJlpj\r\n"
			+ "TuFjMLWefSLL1mPFE6Me3S2dvMMHSLuavNZJbqQ6mLpmuKWZjYXXkUSSa/OFmNOV\r\n" + "zQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

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
		// three clients
		clients.inMemory().withClient(customerClientId).secret(customerClientSecret).scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
				.refreshTokenValiditySeconds(20000)
				.and()
				.withClient(partnerClientId).secret(partnerClientSecret).scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
				.refreshTokenValiditySeconds(20000)
				.and()
				.withClient(adminClientId).secret(adminClientSecret).scopes("admin")
				.authorizedGrantTypes("client_credentials").accessTokenValiditySeconds(20000)
				.refreshTokenValiditySeconds(20000);
	}
}