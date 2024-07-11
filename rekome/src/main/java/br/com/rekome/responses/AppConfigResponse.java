package br.com.rekome.responses;

import br.com.rekome.entities.AppConfig;
import jakarta.validation.constraints.NotNull;

public class AppConfigResponse {

	@NotNull
	private String value;

	@NotNull
	private String uuid;

	@NotNull
	private String term;

	public AppConfigResponse(AppConfig appConfig) {
		this.value = appConfig.getValue();
		this.uuid = appConfig.getUuid();
		this.term = appConfig.getName();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

}
