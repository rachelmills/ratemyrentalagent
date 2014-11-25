package com.ratemyrealestaterental.web.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
//	public List<Rating> getRatings() {
//
//		return jdbc.query("select * from rating", new RatingRowMapper());
//	}
	
	@SuppressWarnings("unchecked")
	public List<Rating> getRatings() {
		return session().createQuery("from Rating").list();
	}

	public List<Rating> getRatingsForAgent(int agentId) {

		MapSqlParameterSource params = new MapSqlParameterSource("agentId",
				agentId);
		return jdbc.query("select * from rating where agentID = :agentId",
				params, new RatingRowMapper());
	}

	public boolean create(Rating rating) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
				rating);

		return jdbc
				.update("insert into rating (agentID, userID, rating) values (:agentID, :userID, :rating)",
						params) == 1;
	}
	
	public boolean update(Rating rating) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
				rating);
		
		return jdbc.update("update rating set rating = :rating where id = :id", params) == 1;
	}

	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);

		return jdbc.update("delete from offers where id=:id", params) == 1;
	}

	public Rating getRating(int agentId, int userId) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("agentid", agentId);
		params.addValue("userid", userId);

		return jdbc.queryForObject("select * from rating where agentid=:agentid and userid=:userid", params,
				new RatingRowMapper());
	}

	public List<Rating> getRatingsForUser(int userId) {
		return jdbc.query("select * from rating where userID = :userId",
				new MapSqlParameterSource("userId", userId),
				new RatingRowMapper());
	}


}
