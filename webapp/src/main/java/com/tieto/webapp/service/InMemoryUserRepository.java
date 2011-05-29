package com.tieto.webapp.service;

import java.util.*;

import org.springframework.stereotype.*;

import com.tieto.webapp.domain.*;

@Repository("in-memory")
public class InMemoryUserRepository implements UserRepository {

	private Map<Long, User> users;
	
	public InMemoryUserRepository() {
		users = new Hashtable<Long, User>();
		users.put(1L, new User(1L, "user1", "user1"));
		users.put(2L, new User(2L, "user2", "user2"));
		users.put(3L, new User(3L, "user3", "user3"));
	}

	@Override
	public Long countUsers() {
		return (long) users.size();
	}

	@Override
	public void save(User user) {
		Long id = user.getId();
		if (id == null) {
			id = generateId();
			user.setId(id);
		}
		users.put(id, user);
	}

	private long generateId() {
		return countUsers() + 1;
	}

	@Override
	public void remove(User user) {
		users.remove(user);
	}

	@Override
	public User update(User user) {
		save(user);
		return user;
	}

	@Override
	public User findUser(Long id) {
		return users.get(id);
	}

	@Override
	public List<User> findAllUsers() {
		return new ArrayList<User>(users.values());
	}

	@Override
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return findAllUsers();
	}

	@Override
	public User findForCredentials(Credentials credentials) {
		for (User user : users.values()) {
			if (user.getLogin().equals(credentials.getLogin()) &&
					user.getPassword().equals(credentials.getPassword())) {
				return user;
			}
		}
		return null;
	}

}
