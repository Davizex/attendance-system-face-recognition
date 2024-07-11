package br.com.rekome.operations;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class InsertInGroupOperation {
	
	@NotNull
	private String groupUUID;
	
	@NotEmpty
	private List<String> usersUUID;

	public String getGroupUUID() {
		return groupUUID;
	}

	public void setGroupUUID(String groupUUID) {
		this.groupUUID = groupUUID;
	}

	public List<String> getUsersUUID() {
		return usersUUID;
	}

	public void setUsersUUID(List<String> usersUUID) {
		this.usersUUID = usersUUID;
	}	
}
