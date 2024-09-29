package br.com.rekome.entities;

import java.util.Date;

import br.com.rekome.utils.EntitiesUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "attendance")
public class Attendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Group group;
	
	@NotNull
	private Date attendanceDateTime;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '0'")
	private boolean isPresent;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '0'")
	private boolean justified;

	public Attendance() {
		this.isPresent = false;
		this.justified = false;
	}

	public Attendance(User user, Group group, Date date, boolean present, boolean justified) {
		this.uuid = EntitiesUtils.generateUUID();
		this.attendanceDateTime = date;
		this.isPresent = present;
		this.justified = justified;
		this.group = group;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Date getAttendanceDateTime() {
		return attendanceDateTime;
	}

	public void setAttendanceDateTime(Date attendanceDateTime) {
		this.attendanceDateTime = attendanceDateTime;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

	public boolean isJustified() {
		return justified;
	}

	public void setJustified(boolean justified) {
		this.justified = justified;
	}
}
