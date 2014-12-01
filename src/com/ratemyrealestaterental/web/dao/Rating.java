package com.ratemyrealestaterental.web.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

// this annotation is to tell hibernate this is to be considered as a bean, 
//and which table to map to with primary key
@Entity
@Table(name="rating")
public class Rating {

	@Id
	private int id;
	
	@Transient
	private Agent agent;
	
	// not required as same name as table column
	@Column(name="agentID")
	private int agentID;
	private int userID;
	private int rating;
	
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
				+ userID + ", rating=" + rating + ", agent=" + agent + 
				"]";
	}

}
