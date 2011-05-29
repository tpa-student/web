package com.tieto.webapp.domain;

import javax.validation.constraints.*;

public class Credentials {

	@NotNull
	private String login;

	private String return_to;

	@NotNull
	private String password;

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setReturn_to(String return_to) {
		this.return_to = return_to;
	}

	public String getReturn_to() {
		return return_to;
	}
}
