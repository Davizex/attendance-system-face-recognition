package br.com.rekome.entities;

import br.com.rekome.enums.UserTermsEnum;
import br.com.rekome.utils.UserUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_terms")
public class UserTerms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;

	@Enumerated(EnumType.STRING)
	private UserTermsEnum key;
	
	private String value;

	@ManyToOne
	private User user;

	public UserTerms(Long id, String uuid, UserTermsEnum key, String value, User user) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.key = key;
		this.value = value;
		this.user = user;
	}

	public UserTerms() {
		this.uuid = UserUtils.generateUUID();
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

	public UserTermsEnum getKey() {
		return key;
	}

	public void setKey(UserTermsEnum key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
