package com.ratemyrealestaterental.web.dao;

public class Rating {

	private int id;
	private int agentID;
	private int userID;
	private int rating;
	private Agent agent;

	public Rating() {
	}

	public Rating(int agentID, int userID, int rating) {
		this.agentID = agentID;
		this.userID = userID;
		this.rating = rating;
	}

	public Rating(int id, int agentID, int userID, int rating) {
		this.id = id;
		this.agentID = agentID;
		this.userID = userID;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", agentID=" + agentID + ", userID="
				+ userID + ", rating=" + rating + ", agent=" + agent + "]";
	}

}
