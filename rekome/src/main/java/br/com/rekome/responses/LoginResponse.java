package br.com.rekome.responses;

import java.time.Instant;
import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;

public class LoginResponse {
	
	private Instant expireAt;
	
	private Instant issuedAt;
	
	private String token;
	
	private Map<String, Object> claims;

	public LoginResponse(Jwt jwt) {
		this.expireAt = jwt.getExpiresAt();
		this.issuedAt = jwt.getIssuedAt();
		this.token = jwt.getTokenValue();
		this.claims = jwt.getClaims();
	}

	public Instant getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Instant expireAt) {
		this.expireAt = expireAt;
	}

	public Instant getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Instant issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}

	public void setClaims(Map<String, Object> claims) {
		this.claims = claims;
	}
	
}
