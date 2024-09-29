package br.com.rekome.model;

import java.time.LocalDate;
import java.util.Date;

import br.com.rekome.entities.Group;

public class GroupDTO {
	
	private String uuid;
	
	private OrganizationDTO organization;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Date creationDate;

	public GroupDTO(String uuid, OrganizationDTO organization, LocalDate startDate, LocalDate endDate, Date creationDate) {
		super();
		this.uuid = uuid;
		this.organization = organization;
		this.startDate = startDate;
		this.endDate = endDate;
		this.creationDate = creationDate;
	}

	public GroupDTO(Group group) {
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
