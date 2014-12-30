package be.shoktan.ingressCompagnion.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.model.Faction;
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
	
	/* ===== Create queries ===== */
	
	@RequestMapping(value="/add/", method=RequestMethod.GET)
	public String addAgent(Model model){
		Agent agent = new Agent();
		if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
		model.addAttribute("agent", agent);
		model.addAttribute("factions", Faction.values());
		return "agent_modify";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addAgent(@Valid @ModelAttribute("agent") Agent form, BindingResult result, Model model) throws Exception {
		if(logger.isDebugEnabled())logger.debug("creating agent <"+form+">");
		
		if(result.hasErrors()){
			logger.warn("unable to save the new agent due to validation errors");
			for(ObjectError err : result.getAllErrors()){
				logger.warn("validation error:: "+err.getDefaultMessage());
			}
			model.addAttribute("factions", Faction.values());
			return "agent_create";
		}
		
		repository.save(form);
		return "redirect:/agent/show/"+form.getCodename();
	}
	
	/* ===== Read queries ===== */
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
	public String showProfile(Principal userPrincipal, Model model){
		String name = userPrincipal.getName();
		if(logger.isDebugEnabled())logger.debug("showProfile():: "+name);
		return "redirect:/agent/show/"+name;
	}
	
	@RequestMapping(value="/show/{codename}", method=RequestMethod.GET)
	public String showAgent(@PathVariable String codename, Model model){
		Agent agent = repository.findByCodename(codename);
		if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
		model.addAttribute("agent", agent);
		return "agent_profile";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String showAgentsList(Model model){
		List<Agent> agents = repository.findAll();
		if(logger.isDebugEnabled())logger.debug("#"+agents.size()+" agents found");
		model.addAttribute("agents", agents);
		return "agents";
	}
	
	/* ===== Update queries ===== */
	
	@RequestMapping(value="/modify/{codename}", method=RequestMethod.GET)
	public String modifyAgent(@PathVariable String codename, Model model){
		Agent agent = repository.findByCodename(codename);
		if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
		model.addAttribute("agent", agent);
		model.addAttribute("factions", Faction.values());
		return "agent_modify";
	}
	
	@RequestMapping(value="/modify/{codename}", method=RequestMethod.POST)
	public String saveAgent(@PathVariable String codename, @Valid @ModelAttribute("agent") Agent form, BindingResult result, Model model) throws Exception {
		if(logger.isDebugEnabled())logger.debug("updating agent <"+form+">");
		
		if(result.hasErrors()){
			logger.warn("unable to save the agent modification due to validation errors");
			for(ObjectError err : result.getAllErrors()){
				logger.warn("validation error:: "+err.getDefaultMessage());
			}
			model.addAttribute("factions", Faction.values());
			return "agent_modify";
		}
		
		Agent agent = repository.findByCodename(codename);
		agent.setCodename(form.getCodename());
		agent.setFaction(form.getFaction());
		
		repository.save(agent);
		return "redirect:/agent/show/"+form.getCodename();
	}
	
	/* ===== Delete queries ===== */
}
