package com.tieto.webapp.service;

import org.springframework.stereotype.*;

import com.tieto.webapp.domain.Credentials;

@Component
public class Authenticator {

	public boolean authenticate(Credentials credentials) {
		return true;
	}

}
