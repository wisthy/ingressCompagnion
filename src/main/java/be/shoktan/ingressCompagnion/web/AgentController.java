package be.shoktan.ingressCompagnion.web;

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
	private AgentRepository repository;
	
	@Autowired
	public AgentController(AgentRepository agentRepository){
		this.repository = agentRepository;
	}
	
	@RequestMapping(value="/{codename}", method=RequestMethod.GET)
	public String showAgentProfile(@PathVariable String codename, Model model){
		Agent agent = repository.findByCodename(codename);
		model.addAttribute(agent);
		return "profile";
	}
}
