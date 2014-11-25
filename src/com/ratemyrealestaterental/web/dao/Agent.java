package com.ratemyrealestaterental.web.dao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Agent {

	private int id;
	
	@NotNull
	@Size(min=5, max=50)
	private String agentName;
	
	@NotNull
	@Size(min=5, max=20)
	private String suburb;

	public Agent() {
	}

	public Agent(String agentName) {
		this.agentName = agentName;
	}

	public Agent(int id, String agentName) {
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

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	
	public String getSuburb() {
		return suburb;
	}
	
	@Override
	public String toString() {
		return "Agent [id=" + id + ", agentName=" + agentName + " + suburb=" + suburb + "]";
	}

}
