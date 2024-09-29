package br.com.rekome.entities;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import br.com.rekome.operations.GroupsInfoCreateOperation;
import br.com.rekome.utils.EntitiesUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "groups_infos")
public class GroupInfos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;
	
	@OneToOne(cascade =  CascadeType.ALL)
	@JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
	private Group group;

	@Max(100)
	@Min(0)
	private int attendanceLimit;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection
	@JoinTable(
		    name="groups_infos_days",
		    joinColumns=@JoinColumn(name="group_info_id")
		)
	private Set<DayOfWeek> daysOfAttendace;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime creationDate;
	
	public GroupInfos() {
	}


	public GroupInfos(GroupsInfoCreateOperation groupsInfosOp, Group group) {
		this.uuid = EntitiesUtils.generateUUID();
		this.attendanceLimit  = groupsInfosOp.getAttendanceLimit();
		this.daysOfAttendace = groupsInfosOp.getDays();
		this.group = group;
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Set<DayOfWeek> getDaysOfAttendace() {
		return daysOfAttendace;
	}


	public void setDaysOfAttendace(Set<DayOfWeek> daysOfAttendace) {
		this.daysOfAttendace = daysOfAttendace;
	}


	public int getAttendanceLimit() {
		return attendanceLimit;
	}

	public void setAttendanceLimit(int attendanceLimit) {
		this.attendanceLimit = attendanceLimit;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

}
