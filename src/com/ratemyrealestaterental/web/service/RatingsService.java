package com.ratemyrealestaterental.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.ratemyrealestaterental.web.dao.Rating;
import com.ratemyrealestaterental.web.dao.RatingsDAO;
import com.ratemyrealestaterental.web.dao.UsersDAO;

@Service
public class RatingsService {

	private RatingsDAO ratingsDAO;

	@Autowired
	private UsersDAO usersDAO;

	public List<Rating> getCurrent() {
		return ratingsDAO.getRatings();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public void createRating(Rating rating) {
		ratingsDAO.create(rating);
	}

	public List<Rating> getRatingsForAgent(int agentId) {
		return ratingsDAO.getRatingsForAgent(agentId);
	}

	public List<Rating> getRatingsForUser(int userId) {
		return ratingsDAO.getRatingsForUser(userId);
	}
	
	public Rating getRating(int agentId, int userId) {
		return ratingsDAO.getRating(agentId, userId);
	}

	@Autowired
	public void setRatingsDAO(RatingsDAO ratingsDAO) {
		this.ratingsDAO = ratingsDAO;
	}

	public boolean hasRatings(String name) {
		if (null == name) {
			return false;
		}
		int userId = usersDAO.getUserIdByUsername(name);
		List<Rating> ratings = ratingsDAO.getRatingsForUser(userId);
		if (ratings.size() == 0) {
			return false;
		}
		return true;
	}

	public void updateRating(Rating rating) {
		ratingsDAO.update(rating);
		
	}

}
