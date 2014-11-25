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
//		HttpSession session = request.getSession();
//		logger.info("showing home page");
//		out.println("<p>(Session creation time is "
//				+ new Date(session.getCreationTime()) + ")</p>");
//		out.println("<p>(Session last access time is "
//				+ new Date(session.getLastAccessedTime()) + ")</p>");
//		out.println("<p>(Session max inactive interval  is "
//				+ session.getMaxInactiveInterval() + " seconds)</p>");

		boolean hasRatings = false;
		if (null != principal) {
			hasRatings = ratingsService.hasRatings(principal.getName());
			model.addAttribute("userid", usersService.getUserId(principal.getName()));
		}

		model.addAttribute("agentSearch", new AgentSearch());
		model.addAttribute("hasRatings", hasRatings);
		return "home";
	}
	

}