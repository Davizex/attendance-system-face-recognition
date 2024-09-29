package br.com.rekome.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import br.com.rekome.enums.UserRolesEnum;

public class EntitiesUtils {
	
	private static final int EXPIRE_TIME_TOKEN = 60;
	
	private static final String REMOKE_SERVICE = "remoke-service";

	public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?\\\\/~`-]).*$";
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String generatePasswordSalt() {
		final byte[] salt = new byte[32];
		final SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
			
		return Base64.getEncoder().encodeToString(salt);		
	}
	
	/**
	 * @param password
	 * @param salt
	 * @return 
	 * 		encode password + salt.
	 * @throws Exception 
	 */
	public static String generatePassword(final String password, String salt) {
		try {
			final StringBuilder builder = new StringBuilder();
			builder.append(password);
			
			if(salt == null) {
				salt = generatePasswordSalt();
			}
			
			builder.append(salt);
			
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(builder.toString().getBytes("UTF-8"));
			
			return Base64.getEncoder().encodeToString(md.digest());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static JwtClaimsSet claimSet(String userUUID, UserRolesEnum scope) {
		Instant now = Instant.now();
		
		return JwtClaimsSet.builder()
			.issuer(REMOKE_SERVICE)
			.subject(userUUID)
			.issuedAt(now)
			.expiresAt(now.plus(EXPIRE_TIME_TOKEN, ChronoUnit.MINUTES))
			.claim("scope", scope)
			.build();
	}
}
