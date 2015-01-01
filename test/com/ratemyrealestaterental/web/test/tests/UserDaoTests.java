package com.ratemyrealestaterental.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

	private User user1 = new User("rachel1", "hello", true, "user");
	private User user2 = new User("rachel2", "hello", true, "admin");

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from rating");
		jdbc.execute("delete from user");
	}

	@Test
	public void testCreateUser() {
		usersDao.create(user1);

		List<User> users = usersDao.getAllUsers();
		assertEquals(1, users.size());

		assertTrue("User should exists", usersDao.exists(user1.getUsername()));
		assertFalse("User should not exist", usersDao.exists("randomusername"));

		assertEquals(user1, users.get(0));
	}

	@Test
	public void testDeleteUser() {
		usersDao.create(user1);
		
		List<User> users = usersDao.getAllUsers();
		assertEquals(1, users.size());
		
		usersDao.delete(usersDao.getUserIdByUsername(user1.getUsername()));
		
		List<User> newList = usersDao.getAllUsers();
		assertEquals(0, newList.size());	
	}

	@Test
	public void testGetAllUsers() {
		usersDao.create(user1);
		usersDao.create(user2);

		List<User> users = usersDao.getAllUsers();

		assertTrue("Two users should have been retrieved", users.size() == 2);
		assertEquals(user1, users.get(0));
		assertEquals(users.get(1).getUsername(), "rachel2");
	}

	@Test
	public void testGetUserIdByUsername() {
		usersDao.create(user1);
		int userId = usersDao.getUserIdByUsername("rachel1");
		assertTrue(userId > 0);
	}

	@Test
	public void testGetUserById() {
		usersDao.create(user1);
		User user = usersDao.getUser(usersDao.getUserIdByUsername(user1.getUsername()));
		assertEquals(user1, user);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testUnableToCreateDuplicateUser() {
		usersDao.create(user1);
		usersDao.create(user1);
	}
}