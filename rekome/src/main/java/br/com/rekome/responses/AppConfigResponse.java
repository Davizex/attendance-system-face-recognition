package br.com.rekome.responses;

import br.com.rekome.entities.AppConfig;
import jakarta.validation.constraints.NotNull;

public class AppConfigResponse {

	@NotNull
	private String value;

	@NotNull
	private String uuid;

	@NotNull
	private String name;

	public AppConfigResponse(AppConfig appConfig) {
		this.value = appConfig.getValue();
		this.uuid = appConfig.getUuid();
		this.name = appConfig.getName();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
