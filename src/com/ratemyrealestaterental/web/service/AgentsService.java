package com.ratemyrealestaterental.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ratemyrealestaterental.web.dao.Agent;
import com.ratemyrealestaterental.web.dao.AgentsDAO;

@Service
public class AgentsService {
	
	private AgentsDAO agentsDAO;

	public List<Agent> getCurrent() {
		return agentsDAO.getAgents();
	}

	
	public void createAgent(Agent agent) {
		agentsDAO.create(agent);
	}

	public void throwTestException() {
		agentsDAO.getAgent(6);	
	}
	
	public Agent getAgent(int id) {
		return agentsDAO.getAgent(id);
	}
	
	public List<Agent> search(String agentName) {
		return agentsDAO.search(agentName);
	}
	
	public String getAgentName(int agentId) {
		return agentsDAO.getAgentName(agentId);
	}
	
	@Autowired
	public void setAgentsDAO(AgentsDAO agentsDAO) {
		this.agentsDAO = agentsDAO;
	}

}
