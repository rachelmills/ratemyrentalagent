package com.ratemyrealestaterental.web.test.tests;

import static org.junit.Assert.assertEquals;
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
import com.ratemyrealestaterental.web.dao.User;
import com.ratemyrealestaterental.web.dao.UsersDAO;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:com/ratemyrealestaterental/web/config/dao-context.xml",
		"classpath:com/ratemyrealestaterental/web/config/security-context.xml",
		"classpath:com/ratemyrealestaterental/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

	@Autowired
	private UsersDAO usersDao;

	@Autowired
	private DataSource dataSource;

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from rating");
		jdbc.execute("delete from user");
		jdbc.execute("delete from authorities");
	}

	@Test
	public void testCreateUser() {
		User user = new User("rachel", "hello", true, "user");
		assertTrue(usersDao.create(user));

		List<User> users = usersDao.getAllUsers();
		assertEquals(1, users.size());

		assertTrue("User should exists", usersDao.exists(user.getUsername()));

		assertEquals(user, users.get(0));
	}
}