package br.com.rekome.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("app.cors")
public class CorsProperties {
	
	private String[] origins;
	
	private String[] methods;
	
	private String[] headers;
	
	@ConstructorBinding
	public CorsProperties(String[] origins, String[] methods, String[] headers) {
		this.origins = origins;
		this.methods = methods;
		this.headers = headers;
	}

	public String[] getOrigins() {
		return origins;
	}

	public void setOrigins(String[] origins) {
		this.origins = origins;
	}

	public String[] getMethods() {
		return methods;
	}

	public void setMethods(String[] methods) {
		this.methods = methods;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	
}
