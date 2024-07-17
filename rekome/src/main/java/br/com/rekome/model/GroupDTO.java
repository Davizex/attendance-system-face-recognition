package br.com.rekome.model;

import java.util.Date;

import br.com.rekome.entities.Groups;

public class GroupDTO {
	
	private String uuid;
	
	private OrganizationDTO organization;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date creationDate;

	public GroupDTO(String uuid, OrganizationDTO organization, Date startDate, Date endDate, Date creationDate) {
		super();
		this.uuid = uuid;
		this.organization = organization;
		this.startDate = startDate;
		this.endDate = endDate;
		this.creationDate = creationDate;
	}

	public GroupDTO(Groups group) {
		this.uuid = group.getUuid();
		this.organization = new OrganizationDTO(group.getOrganization());
		this.startDate = group.getStartDate();
		this.endDate = group.getEndDate();
		this.creationDate = group.getCreationDate();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
