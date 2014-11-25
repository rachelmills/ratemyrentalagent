package com.ratemyrealestaterental.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ratemyrealestaterental.web.dao.User;
import com.ratemyrealestaterental.web.service.UsersService;

@Controller
public class LoginController {

	private UsersService usersService;

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}

	@RequestMapping("/newuser")
	public String showNewUser(Model model) {
		model.addAttribute("user", new User());
		return "newuser";
	}

	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public String createUser(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "newuser";
		}

		user.setEnabled(true);
		user.setAuthority("ROLE_USER");

		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newuser";
		}

		try {
			usersService.createUser(user);
		} catch (DuplicateKeyException ex) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newuser";
		}

		return "usercreated";
	}

	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}

	@RequestMapping("/admin")
	public String showAdmin(Model model) {

		List<User> users = usersService.getAllUsers();
		model.addAttribute("users", users);

		return "admin";
	}

	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

}
