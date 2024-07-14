package br.com.rekome.model;

import br.com.rekome.entities.Organization;

public class OrganizationDTO {
	
	private String uuid;
	
	private String name;

	public OrganizationDTO() {}

	public OrganizationDTO(String uuid, String name) {
		super();
		this.uuid = uuid;
		this.name = name;
	}

	public OrganizationDTO(final Organization org) {
		this.uuid = org.getUuid();
		this.name = org.getName();
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
