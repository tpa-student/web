package com.tieto.webapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.roo.addon.entity.*;
import org.springframework.roo.addon.javabean.*;
import org.springframework.roo.addon.tostring.*;

@Entity
@Table(name = "WEB_USER", uniqueConstraints = @UniqueConstraint(columnNames = { "login" }))
@RooJavaBean
@RooToString
@RooEntity
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@NotNull
	private String login;

	@NotNull
	private String password;

	@Version
	@Column(name = "version")
	private Integer version;

	public User() {}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public User(Long id, String login, String password) {
		this(login, password);
		this.id = id;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ").append(getId()).append(", ");
		sb.append("Version: ").append(getVersion()).append(", ");
		sb.append("Login: ").append(getLogin()).append(", ");
		return sb.toString();
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
