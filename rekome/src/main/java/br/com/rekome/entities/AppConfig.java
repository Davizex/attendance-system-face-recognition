package br.com.rekome.entities;

import br.com.rekome.operations.AppConfigOperation;
import br.com.rekome.utils.EntitiesUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_config")
public class AppConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;
	
	@Column(unique = true)
	private String name;
	
	@Lob
	private String value;

	public AppConfig(Long id, String uuid, String name, String value) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.value = value;
	}

	public AppConfig(AppConfigOperation app) {
		this.uuid = EntitiesUtils.generateUUID();
		this.name = app.getName();
		this.value = app.getValue().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
