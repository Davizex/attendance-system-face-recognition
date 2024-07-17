package br.com.rekome.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.com.rekome.operations.GroupsCreateOperation;
import br.com.rekome.utils.UserUtils;
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
public class Groups {
	
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
	private GroupsInfos groupsInfos;

	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;

	@CreationTimestamp
	@Column(nullable = false)
	private Date creationDate;

	public Groups(Long id, String uuid, @NotNull Date startDate, @NotNull Date endDate) {
		this.id = id;
		this.uuid = uuid;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Groups() {
		super();
	}

	public Groups(GroupsCreateOperation group, List<User> usersList, List<User> monitorsList, Organization organization) {
		this.uuid = UserUtils.generateUUID();
		this.users = usersList;
		this.monitors = monitorsList;
		this.startDate = group.getStartDate();
		this.endDate = group.getEndDate();
		this.organization = organization;
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

	public GroupsInfos getInfos() {
		return groupsInfos;
	}

	public void setInfos(GroupsInfos infos) {
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

	public GroupsInfos getGroupsInfos() {
		return groupsInfos;
	}

	public void setGroupsInfos(GroupsInfos groupsInfos) {
		this.groupsInfos = groupsInfos;
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
