package be.shoktan.ingressCompagnion.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.repository.AgentRepository;

/**
 * Controller in charge for all agents related actions
 * @author wisthler
 *
 */
@Controller
@RequestMapping("/agent")
public class AgentController {
	static final Logger logger = LoggerFactory.getLogger(AgentController.class);
	private AgentRepository repository;
	
	@Autowired
	public AgentController(AgentRepository agentRepository){
		this.repository = agentRepository;
	}
	
	@RequestMapping(value="/profile/{codename}", method=RequestMethod.GET)
	public String showAgentProfile(@PathVariable String codename, Model model){
		Agent agent = repository.findByCodename(codename);
		if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
		model.addAttribute("agent", agent);
		return "agent_profile";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String showAllAgents(Model model){
		List<Agent> agents = repository.findAll();
		if(logger.isDebugEnabled())logger.debug("#"+agents.size()+" agents found");
		model.addAttribute("agents", agents);
		return "agents";
	}
}
