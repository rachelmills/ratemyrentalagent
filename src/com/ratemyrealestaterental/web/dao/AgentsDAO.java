package com.ratemyrealestaterental.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component("agentsDao")
public class AgentsDAO {

	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public List<Agent> getAgents() {

		return jdbc.query("select * from agent", new RowMapper<Agent>() {

			public Agent mapRow(ResultSet rs, int rowNum) throws SQLException {
				Agent agent = new Agent();

				agent.setId(rs.getInt("id"));
				agent.setAgentName(rs.getString("agent_name"));

				return agent;
			}
		});
	}

	public boolean create(Agent agent) {
		agent.setSuburb(agent.getSuburb().toUpperCase());

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
				agent);

		return jdbc
				.update("insert into agent (agent_name, suburb) values (:agentName, :suburb)",
						params) == 1;
	}

	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);

		return jdbc.update("delete from offers where id=:id", params) == 1;
	}

	public Agent getAgent(int id) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);

		return jdbc.queryForObject("select * from agent where id=:id", params,
				new RowMapper<Agent>() {

					public Agent mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Agent agent = new Agent();

						agent.setId(rs.getInt("id"));
						agent.setAgentName(rs.getString("agent_name"));
						agent.setSuburb(rs.getString("suburb"));

						return agent;
					}
				});
	}

	public String getAgentName(int agentId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("agentId", agentId);
		return jdbc.queryForObject(
				"select agent_name from agent where id = :agentId", params,
				String.class);
	}

	public List<Agent> search(String name) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", name.toUpperCase());

		return jdbc.query(
				"select * from agent where upper(agent_name) = :name", params,
				new RowMapper<Agent>() {

					public Agent mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Agent agent = new Agent();
						agent.setId(rs.getInt("id"));
						agent.setAgentName(rs.getString("agent_name"));
						agent.setSuburb(rs.getString("suburb"));
						return agent;
					}

				});
	}

}
