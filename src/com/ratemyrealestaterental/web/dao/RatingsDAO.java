package com.ratemyrealestaterental.web.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("ratingsDao")
public class RatingsDAO {

	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rating> getRatings() {
		Criteria crit = session().createCriteria(Rating.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.enabled", true));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rating> getRatingsForAgent(int agentId) {
		Criteria crit = session().createCriteria(Rating.class);
		crit.createAlias("user", "u");
		crit.createAlias("agent", "a");
		crit.add(Restrictions.eq("u.enabled", true));
		crit.add(Restrictions.eq("a.id", agentId));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rating> getRatingsForUser(User user) {
		Criteria crit = session().createCriteria(Rating.class);
		crit.createAlias("user", "user");
		crit.add(Restrictions.eq("user.id", user.getId()));
		List<Rating> ratings = crit.list();
		return ratings;
	}

	public void saveOrUpdate(Rating rating) {
		session().saveOrUpdate(rating);
	}

	public boolean delete(int agentId, User user) {
		Criteria crit = session().createCriteria(Rating.class);
		crit.add(Restrictions.eq("agentID", agentId));
		crit.add(Restrictions.eq("userID", user.getId()));
		Query query = session().createQuery("delete from Rating where agentID = :agentId and userID = :userId");
		query.setLong("agentId",agentId);
		query.setLong("userId", user.getId());
		return query.executeUpdate() == 1;

		//		MapSqlParameterSource params = new MapSqlParameterSource();
//		params.addValue("agentId", agentId);
//		params.addValue("userId", user.getId());
//
//		return jdbc.update("delete from rating where agentID=:agentId and userID=:userId", params) == 1;
	}

	public Rating getRating(int agentId, User user) {
		Criteria crit = session().createCriteria(Rating.class);
		crit.createAlias("user", "user");
		crit.add(Restrictions.eq("agentID", agentId));
		crit.add(Restrictions.eq("user.id", user.getId()));
		Rating rating = (Rating) crit.uniqueResult();
		return rating;
	}



}
