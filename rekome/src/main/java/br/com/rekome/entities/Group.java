package br.com.rekome.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.com.rekome.operations.GroupsCreateOperation;
import br.com.rekome.utils.EntitiesUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "groups")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="groups_users",
			joinColumns = @JoinColumn(name="groups_id"),
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="groups_monitors_users",
			joinColumns = @JoinColumn(name="groups_id"),
			inverseJoinColumns = @JoinColumn(name="user_monitor_id"))
	private List<User> monitors;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="organizationId", nullable = false)
	private Organization organization;
	
	@OneToOne(mappedBy = "group")
	private GroupInfos groupsInfos;

	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate endDate;

	@CreationTimestamp
	@Column(nullable = false)
	private Date creationDate;

	public Group(Long id, String uuid, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
		this.id = id;
		this.uuid = uuid;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Group(GroupsCreateOperation group, List<User> usersList, List<User> monitorsList, Organization organization) {
		this.uuid = EntitiesUtils.generateUUID();
		this.users = usersList;
		this.monitors = monitorsList;
		this.startDate = group.getStartDate();
		this.endDate = group.getEndDate();
		this.organization = organization;
	}

	public Group() {}


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

	public GroupInfos getInfos() {
		return groupsInfos;
	}

	public void setInfos(GroupInfos infos) {
		this.groupsInfos = infos;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<User> monitors) {
		this.monitors = monitors;
	}

	public GroupInfos getGroupsInfos() {
		return groupsInfos;
	}

	public void setGroupsInfos(GroupInfos groupsInfos) {
		this.groupsInfos = groupsInfos;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
}
