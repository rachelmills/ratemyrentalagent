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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ratemyrealestaterental.web.dao.Agent;
import com.ratemyrealestaterental.web.dao.AgentsDAO;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:com/ratemyrealestaterental/web/config/dao-context.xml",
		"classpath:com/ratemyrealestaterental/web/config/security-context.xml",
		"classpath:com/ratemyrealestaterental/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AgentDaoTests {

	@Autowired
	private AgentsDAO agentsDao;

	@Autowired
	private DataSource dataSource;

	private Agent agent1 = new Agent("Ray White", "Nundah");
	private Agent agent2 = new Agent("Ray White", "Clayfield");

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from agent");
	}

	@Test
	public void testCreateAgent() {
		agentsDao.create(agent1);

		List<Agent> agents = agentsDao.getAgents();
		assertEquals(1, agents.size());

		assertTrue("Agent should exist", agentsDao.exists(agent1.getAgentName(), agent1.getSuburb()));
		assertFalse("Agent should not exist", agentsDao.exists("randomagentname", "randomsuburb"));

		assertEquals(agent1, agents.get(0));
	}

	@Test
	public void testDeleteUser() {
		agentsDao.create(agent1);
		
		List<Agent> agents = agentsDao.getAgents();
		assertEquals(1, agents.size());
		
		agentsDao.delete(agent1.getId());
		
		List<Agent> newList = agentsDao.getAgents();
		assertEquals(0, newList.size());	
	}

	@Test
	public void testGetAllUsers() {
		agentsDao.create(agent1);
		agentsDao.create(agent2);

		List<Agent> agents = agentsDao.getAgents();

		assertTrue("Two users should have been retrieved", agents.size() == 2);
		assertEquals(agent1, agents.get(0));
		assertEquals("Clayfield".toUpperCase(), agents.get(1).getSuburb());
	}

	@Test
	public void testGetAgentById() {
		agentsDao.create(agent1);
		Agent agent = agentsDao.getAgent(agent1.getId());
		assertTrue(agent.getAgentName().equals(agent1.getAgentName()));
		assertTrue(agent.getSuburb().equals(agent1.getSuburb()));
	}

	@Test
	public void testGetAgentName() {
		agentsDao.create(agent1);
		String agentName = agentsDao.getAgentName(agent1.getId());
		String suburb = agentsDao.getSuburb(agent1.getId());
		assertEquals(agentName, agent1.getAgentName());
		assertEquals(suburb, agent1.getSuburb());
	}
	
	@Test
	public void testGetSuburb() {
		agentsDao.create(agent1);
		String suburb = agentsDao.getSuburb(agent1.getId());
		assertEquals(agent1.getSuburb(), suburb);
	}

	@Test
	public void testSearchBringsBackCorrectResults() {
		agentsDao.create(agent1);
		agentsDao.create(agent2);
		
		List<Agent> agents = agentsDao.search("Ray White");
		assertEquals(2, agents.size());
		assertEquals(agents.get(1), agent2);	
	}
}