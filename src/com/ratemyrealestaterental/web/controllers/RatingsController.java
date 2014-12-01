package com.ratemyrealestaterental.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ratemyrealestaterental.web.dao.Agent;
import com.ratemyrealestaterental.web.dao.Rating;
import com.ratemyrealestaterental.web.service.AgentsService;
import com.ratemyrealestaterental.web.service.RatingsService;
import com.ratemyrealestaterental.web.service.UsersService;

@Controller
public class RatingsController {

	private RatingsService ratingsService;
	private AgentsService agentsService;
	private UsersService usersService;

	@RequestMapping("/ratingsOld")
	public String showRatings(Model model) {
		model.addAttribute("name", "Ed");
		List<Rating> ratings = ratingsService.getCurrent();
		model.addAttribute("ratings", ratings);
		return "ratings";
	}

	@RequestMapping("/ratings")
	public String showRatingsForAgent(@RequestParam("agentid") int id,
			Model model) {
		List<Rating> ratings = ratingsService.getRatingsForAgent(id);
		int sum = 0;
		int count = 0;
		for (Rating rating : ratings) {
			rating.setAgent(agentsService.getAgent(id));
			count++;
			sum += rating.getRating();
		}
		double averageRating = getAverageRating(sum, count);
		model.addAttribute("ratings", ratings);
		model.addAttribute("averageRating", averageRating);
		model.addAttribute("id", id);
		model.addAttribute("display", "agent");
		return "ratings";
	}

	@RequestMapping("/userratings")
	public String showRatingsForUser(@RequestParam("userid") int id, Model model) {
		List<Rating> ratings = ratingsService.getRatingsForUser(id);

		for (Rating rating : ratings) {
			Agent agent = agentsService.getAgent(rating.getAgentID());
			rating.setAgent(agent);
		}

		model.addAttribute("ratings", ratings);
		model.addAttribute("id", id);
		model.addAttribute("display", "user");
		return "ratings";
	}

	private double getAverageRating(int sum, int count) {
		if (count > 0) {
			return sum / (double) count;
		}
		return 0;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String showTest(Model model, @RequestParam("id") int id) {
		System.out.println("id is " + id);
		return "home";
	}

	@RequestMapping(value = "/createrating")
	public String createRating(@RequestParam("agentid") int id, Model model) {
		model.addAttribute("agent", agentsService.getAgent(id));
		return "createrating";
	}

	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(Rating rating, Principal principal, @RequestParam(value="edit", required=false) String edit, @RequestParam(value="ratingID", required=false) Integer ratingId) {
		String username = principal.getName();
		rating.setUserID(usersService.getUserId(username));
		if (edit == null) {
			ratingsService.createRating(rating);
			return "ratingcreated";
		} else {
			rating.setId(ratingId);
			ratingsService.updateRating(rating);
			return "ratingupdated";
		}
	}
	
	@RequestMapping("/editrating")
	public String editRating(@RequestParam("agentid") Integer agentId, @RequestParam("userid") Integer userId, Model model) {
		Rating rating = ratingsService.getRating(agentId, userId);
		model.addAttribute("rating", rating);
		Agent agent = agentsService.getAgent(agentId);
		model.addAttribute("agent", agent);
		return "createrating";
	}
	
	@RequestMapping("/deleterating")
	public String deleteRating(@RequestParam("agentid") Integer agentId, @RequestParam("userid") Integer userId, Model model) {
		ratingsService.deleteRating(agentId, userId);
		return "ratingdeleted";
	}

	@Autowired
	public void setRatingsService(RatingsService ratingsService) {
		this.ratingsService = ratingsService;
	}

	@Autowired
	public void setAgentsService(AgentsService agentsService) {
		this.agentsService = agentsService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
}
