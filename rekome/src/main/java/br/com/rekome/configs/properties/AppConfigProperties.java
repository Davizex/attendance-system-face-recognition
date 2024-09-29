package br.com.rekome.configs.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("app")
public class AppConfigProperties {
	
	private CorsProperties cors;
	
	private SecureProperties secure;
	
	private Map<String, String> properties;

	@ConstructorBinding
	public AppConfigProperties(CorsProperties cors, SecureProperties secure, Map<String, String> properties) {
		this.cors = cors;
		this.secure = secure;
		this.properties = properties;
	}

	public SecureProperties getSecure() {
		return secure;
	}

	public void setSecure(SecureProperties secure) {
		this.secure = secure;
	}

	public CorsProperties getCors() {
		return cors;
	}

	public void setCors(CorsProperties cors) {
		this.cors = cors;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
		
}
