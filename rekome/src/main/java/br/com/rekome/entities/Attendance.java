package br.com.rekome.entities;

import java.util.Date;

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
	private Groups group;
	
	@NotNull
	private Date attencadeDateTime;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '0'")
	private boolean isPresent;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT '0'")
	private boolean justified;

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

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}

	public Date getAttencadeDateTime() {
		return attencadeDateTime;
	}

	public void setAttencadeDateTime(Date attencadeDateTime) {
		this.attencadeDateTime = attencadeDateTime;
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
