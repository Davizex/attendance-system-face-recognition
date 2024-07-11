package br.com.rekome.configs.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("app")
public class AppConfigProperties {
	
	private CorsProperties cors;
	
	private String publicKey;
	
	private String privateKey;

	private Map<String, String> properties;
	
	@ConstructorBinding
	public AppConfigProperties(CorsProperties cors, String publicKey, String privateKey,
			Map<String, String> properties) {
		super();
		this.cors = cors;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.properties = properties;
	}

	public CorsProperties getCors() {
		return cors;
	}


	public void setCors(CorsProperties cors) {
		this.cors = cors;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
		
}
