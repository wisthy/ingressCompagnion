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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
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
	
	/* ===== Enum mapping ==== */
	@ModelAttribute("factions")
	public Faction[] factions() {
		return Faction.values();
	}
	
	/* ===== Create queries ===== */
	
	@RequestMapping(value="/add/", method=RequestMethod.GET)
	public String addAgentInit(Model model){
		Agent agent = new Agent();
		if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
		model.addAttribute("agent", agent);
		//model.addAttribute("factions", Faction.values());
		return "agent_modify";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addAgentProcess(@Valid @ModelAttribute("agent") Agent form, BindingResult result, RedirectAttributes model) throws Exception {
		if(logger.isDebugEnabled())logger.debug("creating agent <"+form+">");
		
		if(result.hasErrors()){
			logger.warn("unable to save the new agent due to validation errors");
			for(ObjectError err : result.getAllErrors()){
				logger.warn("validation error:: "+err.getDefaultMessage());
			}
			//model.addAttribute("factions", Faction.values());
			return "agent_create";
		}
		
		Agent agent = repository.save(form);
		model.addAttribute("codename", agent.getCodename());
		model.addFlashAttribute("agent", agent);
		return "redirect:/agent/show/{codename}";
	}
	
	/* ===== Read queries ===== */
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
	public String showProfile(Principal userPrincipal, Model model){
		String name = userPrincipal.getName();
		if(logger.isDebugEnabled())logger.debug("showProfile():: "+name);
		model.addAttribute("codename", name);
		return "redirect:/agent/show/{codename}";
	}
	
	@RequestMapping(value="/show/{codename}", method=RequestMethod.GET)
	public String showAgent(@PathVariable String codename, Model model){
		Agent agent = null;
		if(!model.containsAttribute("agent")){
			agent = repository.findByCodename(codename);
			if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
			model.addAttribute("agent", agent);
		}
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
	public String modifyAgentInit(@PathVariable String codename, Model model){
		Agent agent = repository.findByCodename(codename);
		if(logger.isDebugEnabled())logger.debug("agent found: "+agent);
		model.addAttribute("agent", agent);
		//model.addAttribute("factions", Faction.values());
		return "agent_modify";
	}
	
	@RequestMapping(value="/modify/{codename}", method=RequestMethod.POST)
	public String modifyAgentProcess(@PathVariable String codename, @Valid @ModelAttribute("agent") Agent form, BindingResult result, RedirectAttributes model) throws Exception {
		if(logger.isDebugEnabled())logger.debug("updating agent <"+form+">");
		
		if(result.hasErrors()){
			logger.warn("unable to save the agent modification due to validation errors");
			for(ObjectError err : result.getAllErrors()){
				logger.warn("validation error:: "+err.getDefaultMessage());
			}
			//model.addAttribute("factions", Faction.values());
			return "agent_modify";
		}
		
		Agent agent = repository.findByCodename(codename);
		agent.setCodename(form.getCodename());
		agent.setFaction(form.getFaction());
		
		Agent saved = repository.save(agent);
		model.addFlashAttribute("agent", saved);
		model.addAttribute("codename", saved.getCodename());
		return "redirect:/agent/show/{codename}";
	}
	
	/* ===== Delete queries ===== */
	
	@RequestMapping(value = "/delete/{codename}", method=RequestMethod.GET)
	public String deleteAgentInit(@PathVariable String codename, Model model){
		Agent agent = repository.findByCodename(codename);
		
		if(agent instanceof RegisteredAgent){
			return "redirect:/agent/list";
		}
		
		model.addAttribute("agent", agent);
		return "agent_delete";
	}
	
	@RequestMapping(value = "/delete/{codename}", method=RequestMethod.POST)
	public String deleteAgentProcess(@PathVariable String codename, RedirectAttributes model){
		Agent agent = repository.findByCodename(codename);
		model.addFlashAttribute("codename", codename);
		
		if(agent instanceof RegisteredAgent){
			model.addFlashAttribute("status", "RegisteredAgent");
			return "redirect:/agent/list";
		}
		
		repository.delete(codename);
		model.addFlashAttribute("status", "deleted");
		return "redirect:/agent/list";
	}
}
