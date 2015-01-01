package com.ratemyrealestaterental.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("usersDao")
public class UsersDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}
	
	public void delete(int id) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(id));
		User user = (User) crit.uniqueResult();
		session().delete(user);
	}

	public User getUser(int id) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(id));
		User user = (User) crit.uniqueResult();
		return user;
	}
	
	public int getUserIdByUsername(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		User user = (User) crit.uniqueResult();
		return user.getId();
	}

	public boolean exists(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		User user = (User) crit.uniqueResult();
		return user != null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}
}
