package br.com.rekome.entities;

import java.util.List;

import br.com.rekome.operations.OrganizationCreateOperation;
import br.com.rekome.utils.EntitiesUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "organization")
public class Organization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;
	
	@NotNull
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private List<Group> groups;
	
	@ManyToMany
	@JoinTable(name="organization_admins",
		joinColumns = @JoinColumn(name="organization_id"),
		inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> admins;

	public Organization(Long id, String uuid, @NotNull String name, List<Group> groups, List<User> admins) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.groups = groups;
		this.admins = admins;
	}

	public Organization() {
		super();
	}

	public Organization(OrganizationCreateOperation organizationOp, List<User> admins) {
		this.uuid = EntitiesUtils.generateUUID();
		this.name = organizationOp.getName();
		this.admins = admins;
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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<User> getAdmins() {
		return admins;
	}

	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

}
