package com.ratemyrealestaterental.web.dao;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AgentSearch {

	private int id;

	@NotNull
	@NotBlank
	private String agentName;

	public AgentSearch() {
	}

	public AgentSearch(String agentName) {
		this.agentName = agentName;
	}

	public AgentSearch(int id, String agentName) {
		this.id = id;
		this.agentName = agentName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", agentName=" + agentName + "]";
	}

}
