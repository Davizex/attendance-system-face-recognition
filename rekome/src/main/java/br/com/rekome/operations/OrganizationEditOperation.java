package br.com.rekome.operations;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrganizationEditOperation {
	
	@NotNull
	private String name;

	@NotNull
	@Min(1)
	List<String> admins;

	public OrganizationEditOperation(@NotNull String name, @NotNull @Min(1) List<String> admins) {
		super();
		this.name = name;
		this.admins = admins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
	}
	
	
}
