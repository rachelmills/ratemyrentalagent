package com.ratemyrealestaterental.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ratemyrealestaterental.web.dao.Agent;
import com.ratemyrealestaterental.web.dao.AgentsDAO;
import com.ratemyrealestaterental.web.dao.Rating;
import com.ratemyrealestaterental.web.dao.RatingsDAO;
import com.ratemyrealestaterental.web.dao.User;
import com.ratemyrealestaterental.web.dao.UsersDAO;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:com/ratemyrealestaterental/web/config/dao-context.xml",
		"classpath:com/ratemyrealestaterental/web/config/security-context.xml",
		"classpath:com/ratemyrealestaterental/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RatingDaoTests {

	@Autowired
	private RatingsDAO ratingsDao;
	
	@Autowired
	private AgentsDAO agentsDao;

	@Autowired
	private UsersDAO usersDao;

	@Autowired
	private DataSource dataSource;

	private int rating = 1;
	private int userId = 0;
	private User user1;
	private User user2;
	private User user3;
	private Agent agent1;
	private Agent agent2;
	
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from rating");
		jdbc.execute("delete from user");
		user1 = new User("rachel1", "hello", true, "user");
		user2 = new User("rachel2", "hello", true, "user");
		user3 = new User("rachel3", "hello", false, "user");
		agent1 = new Agent("Ray White", "Nundah");
		agent2 = new Agent("Harcourts", "Inner City");
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		agentsDao.create(agent1);
		agentsDao.create(agent2);
		user2.setId(usersDao.getUserIdByUsername(user2.getUsername()));
		userId = usersDao.getUserIdByUsername(user1.getUsername());
		user1.setId(userId);
		user3.setId(usersDao.getUserIdByUsername(user3.getUsername()));	
	}

	@Test
	public void testCreateRating() {
		Rating rating1 = new Rating(agent1, user1, rating);
		ratingsDao.saveOrUpdate(rating1);
		assertEquals(1, ratingsDao.getRatings().size());
	}

	@Test
	public void testGetRatings() {
		Rating rating1 = new Rating(agent1, user1, rating);
		Rating rating2 = new Rating(agent2, user1, rating + 1);
		Rating rating3 = new Rating(agent1, user3, rating);

		ratingsDao.saveOrUpdate(rating1);
		ratingsDao.saveOrUpdate(rating2);
		ratingsDao.saveOrUpdate(rating3);

		List<Rating> ratings = ratingsDao.getRatings();

		assertEquals(2, ratings.size());
	}
	
	@Test
	public void testGetRatingsWhenNoRatingsExist() {
		List<Rating> ratings = ratingsDao.getRatings();
		assertEquals(0, ratings.size());
	}
	
	@Test
	public void testGetRatingsForAgent() {
		Rating rating1 = new Rating(agent1, user1, rating);
		Rating rating2 = new Rating(agent1, user2, rating + 1);
		ratingsDao.saveOrUpdate(rating1);
		ratingsDao.saveOrUpdate(rating2);
		List<Rating> ratings = ratingsDao.getRatingsForAgent(agent1.getId());
		assertTrue(ratings.size() == 2);
	}
	
	@Test
	public void testGetRating() {
		Rating rating1 = new Rating(agent1, user1, rating);
		ratingsDao.saveOrUpdate(rating1);
		Rating rating = ratingsDao.getRating(agent1.getId(), user1);
		assertEquals(rating1, rating);
	}
	
	@Test
	public void testGetRatingWhenNoRatingsExist() {
		Rating rating = ratingsDao.getRating(agent1.getId(), usersDao.getUser(userId));
		assertNull(rating);
	}
	
	@Test
	public void testGetRatingsForUser() {
		Rating rating1 = new Rating(agent1, user1, rating);
		Rating rating2 = new Rating(agent2, user1, rating + 1);
		ratingsDao.saveOrUpdate(rating1);
		ratingsDao.saveOrUpdate(rating2);
		List<Rating> ratings = ratingsDao.getRatingsForUser(user1);
		assertTrue(ratings.size() == 2);
	}
	
	@Test
	public void testUpdateRating() {
		Rating rating1 = new Rating(agent1, user1, rating);
		ratingsDao.saveOrUpdate(rating1);
		assertTrue(ratingsDao.getRating(agent1.getId(), user1).getRating() == 1);
		rating1.setRating(2);
		ratingsDao.saveOrUpdate(rating1);
		assertTrue(ratingsDao.getRating(agent1.getId(), user1).getRating() == 2);		
	}
	
	@Test
	public void testDeleteRating() {
		Rating rating1 = new Rating(agent1, user1, rating);
		ratingsDao.saveOrUpdate(rating1);
		
		Rating ratings = ratingsDao.getRating(agent1.getId(), user1);

		assertTrue(ratings.getRating() == 1);
		assertTrue(ratingsDao.delete(agent1.getId(), user1));
		
		Rating rating = ratingsDao.getRating(agent1.getId(), user1);
		assertNull("rating should be null", rating);
	}
}