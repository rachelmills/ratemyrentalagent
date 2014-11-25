package com.ratemyrealestaterental.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("usersDao")
public class UsersDAO {

	private NamedParameterJdbcTemplate jdbc;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public List<User> getUsers() {

		return jdbc.query("select * from user", new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();

				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));

				return user;
			}
		});
	}

	@Transactional()
	public boolean create(User user) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", passwordEncoder.encode(user.getPassword()));
		params.addValue("enabled", user.isEnabled());
		params.addValue("authority", user.getAuthority());

		jdbc.update(
				"insert into user (username, password, enabled) values (:username, :password, :enabled)",
				params);
		return jdbc
				.update("insert into authorities (username, authority) values (:username, :authority)",
						params) == 1;
	}

	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);

		return jdbc.update("delete from offers where id=:id", params) == 1;
	}

	public User getUser(int id) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);

		return jdbc.queryForObject("select * from rating where id=:id", params,
				new RowMapper<User>() {

					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();

						user.setId(rs.getInt("id"));
						user.setUsername(rs.getString("username"));

						return user;
					}

				});
	}
	
	public int getUserIdByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		return jdbc.queryForObject("select ID from user where username = :username", params, Integer.class);
	}

	public boolean exists(String username) {
		return jdbc.queryForObject(
				"select count(*) from user where username = :username",
				new MapSqlParameterSource("username", username), Integer.class) > 0;
	}

	public List<User> getAllUsers() {
		return jdbc.query("select * from user, authorities where user.username = authorities.username", BeanPropertyRowMapper.newInstance(User.class));
	}

}
