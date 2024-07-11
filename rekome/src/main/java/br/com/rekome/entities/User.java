package br.com.rekome.entities;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import br.com.rekome.enums.UserRolesEnum;
import br.com.rekome.operations.UserCreateOperation;
import br.com.rekome.utils.UserUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Email
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	private String document;
	
	@Column(nullable = false)
	private Date birthday;	
	
	@Column(unique = true, length = 36, nullable = false)
	private String uuid;
		
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime creationDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(255) DEFAULT 'DEFAULT'")
	private UserRolesEnum role;

	@NotBlank
	private String password;
	
	@NotBlank
	private String salt;
	
	public User(Long id, String name, @Email String email, String document, Date birthday, String uuid,
			LocalDateTime creationDate, UserRolesEnum role, @NotBlank String password, @NotBlank String salt) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.document = document;
		this.birthday = birthday;
		this.uuid = uuid;
		this.creationDate = creationDate;
		this.role = role;
		this.password = password;
		this.salt = salt;
	}
	
	public User(UserCreateOperation userOperation) {
		this.name = userOperation.getName();
		this.email = userOperation.getEmail();
		this.document = userOperation.getDocument();
		this.birthday = userOperation.getBirthday();
		this.uuid = UserUtils.generateUUID();
		this.role = UserRolesEnum.DEFAULT;
		this.salt = UserUtils.generatePasswordSalt();
		this.password = UserUtils.generatePassword(userOperation.getPassword(), this.salt);
	}
	
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public UserRolesEnum getRole() {
		return role;
	}

	public void setRole(UserRolesEnum role) {
		this.role = role;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
