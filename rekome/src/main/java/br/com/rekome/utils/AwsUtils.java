package br.com.rekome.utils;

import java.util.UUID;

public class AwsUtils {
	
	public static String generateCollectionId(String userId) {
		return new StringBuilder().append(UUID.randomUUID().toString()).append("-rkm-").append(userId).toString();
	}

	public static final Integer MAX_FACES_INDEX_FACE = 1;

}
