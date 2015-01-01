package com.ratemyrealestaterental.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("agentsDao")
public class AgentsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Agent> getAgents() {
		return session().createQuery("from Agent").list();
	}

	public void create(Agent agent) {
		agent.setSuburb(agent.getSuburb().toUpperCase());
		session().save(agent);
	}

	public void delete(int id) {
		Criteria crit = session().createCriteria(Agent.class);
		crit.add(Restrictions.idEq(id));
		Agent agent = (Agent) crit.uniqueResult();
		session().delete(agent);
	}

	public Agent getAgent(int id) {
		Criteria crit = session().createCriteria(Agent.class);
		crit.add(Restrictions.idEq(id));
		return (Agent) crit.uniqueResult();
	}

	public String getAgentName(int agentId) {
		Query query = session().createQuery("select agentName from Agent where id = :id");
		query.setParameter("id", agentId);
		return (String) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Agent> search(String name) {
		Criteria crit = session().createCriteria(Agent.class);
		crit.add(Restrictions.eq("agentName", name));
		List<Agent> agents = crit.list();
		return agents;
	}
	
	public boolean exists(String agentName, String suburb) {
		Criteria crit = session().createCriteria(Agent.class);
		crit.add(Restrictions.eq("agentName", agentName));
		crit.add(Restrictions.eq("suburb", suburb));
		Agent agent = (Agent) crit.uniqueResult();
		return agent != null;
	}

	public String getSuburb(int agentId) {
		Query query = session().createQuery("select suburb from Agent where id = :id");
		query.setParameter("id", agentId);
		return (String) query.uniqueResult();
	}
}
