package com.javabrains.springblog.repository;


import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.javabrains.springblog.model.User;

@Repository
public class UserRepository{
	
	@Autowired
	private EntityManager entityManager;
	

	public void save(User user) {
		System.out.println("Cal************");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(user);
	}


	public User findByUserName(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		User user = currentSession.get(User.class, username);
		return user;
	}
	
	
}
