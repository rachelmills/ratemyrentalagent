package com.ratemyrealestaterental.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.ratemyrealestaterental.web.dao.User;
import com.ratemyrealestaterental.web.dao.UsersDAO;

@Service
public class UsersService {
	
	private UsersDAO usersDAO;

	public List<User> getCurrent() {
		return usersDAO.getUsers();
	}

	@Autowired
	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	
	public void createUser(User user) {
		usersDAO.create(user);
	}

	public boolean exists(String username) {
		return usersDAO.exists(username);
	}
	
	public int getUserId(String username) {
		return usersDAO.getUserIdByUsername(username);
	}

	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDAO.getAllUsers();
	}
	
}
