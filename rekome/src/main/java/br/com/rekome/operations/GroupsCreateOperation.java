package br.com.rekome.operations;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class GroupsCreateOperation {
	
	@NotNull
	private GroupsInfoCreateOperation groupInfo;
	
	private List<String> users;
	
	private List<String> monitors;
	
	private String organizationUuid;

	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate endDate;
	
	public GroupsCreateOperation(@NotNull LocalDate startDate, @NotNull LocalDate endDate,
			@NotNull GroupsInfoCreateOperation groupInfo, List<String> users, List<String> monitors) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.groupInfo = groupInfo;
		this.users = users;
		this.monitors = monitors;
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

	public GroupsInfoCreateOperation getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(GroupsInfoCreateOperation groupInfo) {
		this.groupInfo = groupInfo;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<String> monitors) {
		this.monitors = monitors;
	}

	public String getOrganizationUuid() {
		return organizationUuid;
	}

	public void setOrganizationUuid(String organizationUuid) {
		this.organizationUuid = organizationUuid;
	}

}
