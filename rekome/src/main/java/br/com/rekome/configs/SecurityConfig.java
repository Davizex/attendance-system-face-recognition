package br.com.rekome.configs;

import static org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.CrossOriginEmbedderPolicyHeaderWriter.CrossOriginEmbedderPolicy;
import org.springframework.security.web.header.writers.CrossOriginOpenerPolicyHeaderWriter.CrossOriginOpenerPolicy;
import org.springframework.security.web.header.writers.CrossOriginResourcePolicyHeaderWriter.CrossOriginResourcePolicy;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${app.secure.public-key}")
	private RSAPublicKey publicKey;

	@Value("${app.secure.private-key}")
	private RSAPrivateKey privateKey;

	@Value("${app.secure.secure-headers}")
	private boolean isSecureHeaders;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {

		if (isSecureHeaders) {
			http.headers((headers) -> headers.frameOptions((frame) -> frame.deny())
					.contentSecurityPolicy(
							(securityPolice) -> securityPolice.policyDirectives("frame-ancestors 'self'"))
					.crossOriginEmbedderPolicy(coep -> coep.policy(CrossOriginEmbedderPolicy.REQUIRE_CORP))
					.crossOriginResourcePolicy(corp -> corp.policy(CrossOriginResourcePolicy.SAME_ORIGIN))
					.crossOriginOpenerPolicy(coop -> coop.policy(CrossOriginOpenerPolicy.SAME_ORIGIN))
					.httpStrictTransportSecurity(
							(sts) -> sts.maxAgeInSeconds(31536000).includeSubDomains(true).preload(true))
					.referrerPolicy((police) -> police.policy(SAME_ORIGIN)));
		}

		http.csrf(csrf -> csrf.disable());

		http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/auth/login").permitAll()
				.requestMatchers("/user/create").permitAll()
				.requestMatchers("/actuator/**").permitAll()
				.anyRequest().authenticated());

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}
}
