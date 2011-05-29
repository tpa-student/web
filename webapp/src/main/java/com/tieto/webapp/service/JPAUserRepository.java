package com.tieto.webapp.service;

import java.util.*;

import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.tieto.webapp.domain.*;

@Repository("jpa")
public class JPAUserRepository implements UserRepository {

	public JPAUserRepository() {}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long countUsers() {
		return (Long) entityManager.createQuery("select count(o) from User o")
				.getSingleResult();
	}

	@Override
	@Transactional
	public void save(User user) {
		this.entityManager.persist(user);
	}

	@Override
	@Transactional
	public void remove(User user) {
		if (entityManager.contains(user)) {
			entityManager.remove(user);
		} else {
			User attached = entityManager.find(User.class, user.getId());
			entityManager.remove(attached);
		}
	}

	@Override
	@Transactional
	public User update(User user) {
		User merged = entityManager.merge(user);
		entityManager.flush();
		return merged;
	}

	@Override
	public User findUser(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("User id cannot be null");
		}
		return entityManager.find(User.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		return entityManager.createQuery("select o from User o")
				.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return entityManager.createQuery("select o from User o")
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Override
	public User findForCredentials(Credentials credentials) {
		try {
			Query q = entityManager
					.createQuery("select o from User o where o.login = :login and o.password = :password");
			q.setParameter("login", credentials.getLogin());
			q.setParameter("password", credentials.getPassword());
			return (User) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
