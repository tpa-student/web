package com.tieto.webapp.service;

import java.util.*;

import org.springframework.transaction.annotation.*;

import com.tieto.webapp.domain.*;

public interface UserRepository {

	public abstract Long countUsers();

	@Transactional
	public abstract void save(User user);

	@Transactional
	public abstract void remove(User user);

	@Transactional
	public abstract User update(User user);

	public abstract User findUser(Long id);

	public abstract List<User> findAllUsers();

	public abstract List<User> findUserEntries(int firstResult, int maxResults);

	public abstract User findForCredentials(Credentials credentials);

}