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
	private UsersDAO usersDao;

	@Autowired
	private DataSource dataSource;

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from rating");
		jdbc.execute("delete from user");
	}

	@Test
	public void testCreateRating() {
		usersDao.create(new User("rachel1", "hello", true, "user"));

		int agentId = 1;
		int rating = 1;
		int userId = usersDao.getUserIdByUsername("rachel1");

		Rating rating1 = new Rating(agentId, userId, rating);

		assertTrue(ratingsDao.create(rating1));
	}

	@Test
	public void testGetRatings() {
		usersDao.create(new User("rachel1", "hello", true, "user"));
		int agentId = 1;
		int rating = 1;

		Rating rating1 = new Rating(agentId,
				usersDao.getUserIdByUsername("rachel1"), rating);
		Rating rating2 = new Rating(agentId + 1,
				usersDao.getUserIdByUsername("rachel1"), rating + 1);

		ratingsDao.create(rating1);
		ratingsDao.create(rating2);

		List<Rating> ratings = ratingsDao.getRatings();

		assertEquals(2, ratings.size());
	}
	
	@Test
	public void testDeleteRating() {
		usersDao.create(new User("rachel1", "hello", true, "user"));
		
		int agentId = 1;
		int ratingId = 1;
		
		int userId = usersDao.getUserIdByUsername("rachel1");

		Rating rating1 = new Rating(agentId, userId, ratingId);
		assertTrue(ratingsDao.create(rating1));
		
		Rating ratings = ratingsDao.getRating(agentId, userId);

		assertTrue(ratings.getRating() == 1);
		
		assertTrue(ratingsDao.delete(agentId, userId));
	}

}