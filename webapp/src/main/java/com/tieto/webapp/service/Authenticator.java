package com.tieto.webapp.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;

import com.tieto.webapp.domain.Credentials;
import com.tieto.webapp.domain.User;

@Component
public class Authenticator {

	@Autowired 
//	@Qualifier("in-memory-repository")
	@Qualifier("jpa")
	private UserRepository userRepository;
	
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean authenticate(Credentials credentials) {
		this.user = userRepository.findForCredentials(credentials);
		return user!=null;
	}

}
