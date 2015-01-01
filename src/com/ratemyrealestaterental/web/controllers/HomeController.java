package com.ratemyrealestaterental.web.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ratemyrealestaterental.web.dao.AgentSearch;
import com.ratemyrealestaterental.web.service.RatingsService;
import com.ratemyrealestaterental.web.service.UsersService;

@Controller
public class HomeController {
	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private RatingsService ratingsService;
	
	@Autowired
	private UsersService usersService;

	@RequestMapping("/")
	public String showHome(HttpServletRequest request, Model model, Principal principal) {
		boolean hasRatings = false;
//		logger.log(null, "hi");
		if (null != principal) {
			hasRatings = ratingsService.hasRatings(principal.getName());
			model.addAttribute("userid", usersService.getUserId(principal.getName()));
		}

		model.addAttribute("agentSearch", new AgentSearch());
		model.addAttribute("hasRatings", hasRatings);
		return "home";
	}
}
