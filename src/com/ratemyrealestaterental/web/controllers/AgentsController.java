package com.ratemyrealestaterental.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ratemyrealestaterental.web.dao.Agent;
import com.ratemyrealestaterental.web.dao.AgentSearch;
import com.ratemyrealestaterental.web.service.AgentsService;

@Controller
public class AgentsController {

	private AgentsService agentsService;
	
	@RequestMapping("/createagent")
	public String createNewAgent(Model model) {
		model.addAttribute("agent", new Agent());
		return "createagent";
	}
	
	@RequestMapping(value="/docreateagent", method=RequestMethod.POST)
	public String doCreateAgent(Model model, @Valid Agent agent, BindingResult result) {
		if (result.hasErrors()) {
			return "createagent";
		} 
		agentsService.createAgent(agent);
		return "agentcreated";
	}
	
	@RequestMapping("/agents")
	public String getAgents(Model model) {
		List<Agent> agents = agentsService.getCurrent();
		model.addAttribute("agents", agents);
		return "agents";
	}
	
	@RequestMapping(value="/search")
	public String search(@RequestParam(value="agentName") String name, @Valid AgentSearch agent, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return "home";
		}
		
		List<Agent> agents = agentsService.search(name);
		
		model.addAttribute("agents", agents);
		model.addAttribute("agentName", name);
		return "agents";
	}
	
//	@ModelAttribute("agent") 
//	public Agent createAgent() {
//	
//		Agent agent = new Agent();
//		agent.setAgentName("test");
//		agent.setId(1);
//		return agent;
//	}

	@Autowired
	public void setAgentsService(AgentsService agentsService) {
		this.agentsService = agentsService;
	}

}
