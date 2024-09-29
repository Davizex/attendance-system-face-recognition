package br.com.rekome.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("app.secure")
public class SecureProperties {

	private Boolean secureHeaders;

	private String publicKey;
	
	private String privateKey;

	@ConstructorBinding
	public SecureProperties(Boolean secureHeaders, String publicKey, String privateKey) {
		this.secureHeaders = secureHeaders;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public Boolean getSecureHeaders() {
		return secureHeaders;
	}

	public void setSecureHeaders(Boolean secureHeaders) {
		this.secureHeaders = secureHeaders;
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
