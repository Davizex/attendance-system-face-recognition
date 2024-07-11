package br.com.rekome.enums;

public enum UserTermsEnum {
	
	COLLECTION_ID("collection-id"),
	BUCKET_KEY("bucket-file-key"),
	FACE_RECORD("face-record")
	;
	
	private final  String value;

	private UserTermsEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
