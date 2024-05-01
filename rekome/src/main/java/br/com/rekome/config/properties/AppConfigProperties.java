package br.com.rekome.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("app")
public class AppConfigProperties {
	
	private CorsProperties cors;
	
	private String publicKey;
	
	private String privateKey;
		
	@ConstructorBinding
	public AppConfigProperties(CorsProperties cors, String publicKey, String privateKey) {
		this.cors = cors;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
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
		
}
