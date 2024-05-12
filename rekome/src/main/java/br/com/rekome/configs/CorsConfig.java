package br.com.rekome.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Value("${APP_CORS_ORIGINS}")
	private List<String> origins;
	
	@Value("${APP_CORS_METHODS}")
	private List<String> methods;
	
	@Value("${APP_CORS_HEADERS}")
	private List<String> headers;

	@Bean
	CorsFilter cors() {
		var source = new UrlBasedCorsConfigurationSource();
		var config = new CorsConfiguration();
		config.setAllowedOrigins(origins);
		config.setAllowedMethods(methods);
		config.setAllowedHeaders(headers);
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}
}
