package com.ratemyrealestaterental.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
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

		assertEquals(user1, users.get(0));
	}
	
	@Test
	public void getAllUsers() {
		usersDao.create(user1);
		
		usersDao.create(user2);
		
		List<User> users = usersDao.getAllUsers();
		
		assertTrue("Two users should have been retrieved", users.size() == 2);
		assertEquals(user1, users.get(0));
		assertEquals(users.get(1).getUsername(), "rachel2");
	}
	
	
	@Test(expected=HibernateException.class)
	public void testUnableToCreateDuplicateUser() {
		usersDao.create(user1);
		usersDao.create(user1);
	}
}