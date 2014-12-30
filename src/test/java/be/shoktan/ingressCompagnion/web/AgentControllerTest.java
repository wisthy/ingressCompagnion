package be.shoktan.ingressCompagnion.web;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.model.Faction;
import be.shoktan.ingressCompagnion.model.Trust;
import be.shoktan.ingressCompagnion.repository.AgentRepository;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class AgentControllerTest {
	@Test
	public void shouldShowProfile() throws Exception {
		AgentRepository repo = mock(AgentRepository.class);
		Agent saved = new Agent(1L, "Bob", Faction.RESISTANCE);
		Agent clone = new Agent(1L, "Bob", Faction.RESISTANCE);
		when(repo.findOne(1)).thenReturn(saved);
		when(repo.findByCodename("bob")).thenReturn(saved);
		when(repo.findByCodename("Bob")).thenReturn(saved);
		when(repo.findByCodename("BOB")).thenReturn(saved);
		
		AgentController control = new AgentController(repo);
		
		MockMvc mockMvc = standaloneSetup(control).build();
		
		
		String[] names = new String[]{"Bob", "bob", "BOB"};
		for(String name : names){
			mockMvc.perform(get("/agent/show/"+name))
			.andExpect(view().name("agent_profile"))
			.andExpect(model().attributeExists("agent"))
			.andExpect(model().attribute("agent", clone));
		}
	}
	
	@Test
	public void should404onShowProfile() throws Exception{
		AgentRepository repo = mock(AgentRepository.class);
		when(repo.findByCodename("void")).thenThrow(new NotFoundException(Agent.class, "no agent with codename void"));
		
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		mockMvc.perform(get("/agent/show/void"))
			.andExpect(status().is(404));
	}
	
	@Test
	public void shouldShowAllAgents() throws Exception {
		List<Agent> saved = new ArrayList<Agent>();
		Agent bob = new Agent(1L, "Bob", Faction.RESISTANCE);
		Agent tul = new Agent(2L, "Tul", Faction.ENLIGHTED);
		Agent koin = new RegisteredAgent(3L, "koin", Faction.RESISTANCE, "koin@test.be", Trust.ADMIN);
		saved.add(bob);
		saved.add(tul);
		saved.add(koin);
		
		AgentRepository repo = mock(AgentRepository.class);
		when(repo.findAll()).thenReturn(saved);
		
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		ResultActions result = mockMvc.perform(get("/agent/list"))
			.andExpect(view().name("agents"))
			.andExpect(model().attributeExists("agents"))
			.andExpect(model().attribute("agents", Matchers.contains(saved.toArray())));
		
		for(Agent a : saved){
			result.andExpect(model().attribute("agents", hasItem(a)));
		}
	}
	
	@Test
	public void shouldShowEditProfile() throws Exception {
		AgentRepository repo = mock(AgentRepository.class);
		Agent saved = new Agent(1L, "Bob", Faction.RESISTANCE);
		Agent clone = new Agent(1L, "Bob", Faction.RESISTANCE);
		when(repo.findOne(1)).thenReturn(saved);
		when(repo.findByCodename("bob")).thenReturn(saved);
		when(repo.findByCodename("Bob")).thenReturn(saved);
		when(repo.findByCodename("BOB")).thenReturn(saved);
		
		AgentController control = new AgentController(repo);
		
		MockMvc mockMvc = standaloneSetup(control).build();
		
		
		String[] names = new String[]{"Bob", "bob", "BOB"};
		for(String name : names){
			mockMvc.perform(get("/agent/modify/"+name))
			.andExpect(view().name("agent_modify"))
			.andExpect(model().attributeExists("agent"))
			.andExpect(model().attribute("agent", clone))
			.andExpect(model().attributeExists("factions"))
			.andExpect(model().attribute("factions", Faction.values()));
		}
	}
	
	@Test
	public void should404onShowEditProfile() throws Exception{
		AgentRepository repo = mock(AgentRepository.class);
		when(repo.findByCodename("void")).thenThrow(new NotFoundException(Agent.class, "no agent with codename void"));
		
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		mockMvc.perform(get("/agent/modify/void"))
			.andExpect(status().is(404));
	}
	
	@Test
	public void saveNewAgentOK() throws Exception {
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		String name = "Bob";
		
		mockMvc.perform(post("/agent/modify/")
					.param("codename", "Bob") 
					.param("faction", Faction.ENLIGHTED.toString()))
				.andExpect(redirectedUrl("/agent/show/"+name));
		
		verify(repo, atLeastOnce()).save(new Agent(null, "Bob", Faction.ENLIGHTED));
	}
	
	@Test
	public void saveNewAgentShouldSendValidationErrorOnWrongCodename() throws Exception {
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		String[] names = new String[]{"Bo", null, "Bob45678901234567"};
		
		for(String name : names){
			Agent clone = new Agent(null, name, Faction.ENLIGHTED);
			mockMvc.perform(post("/agent/modify/")
					.param("codename", name) 
					.param("faction", Faction.ENLIGHTED.toString()))
				.andExpect(view().name("agent_modify"))
				.andExpect(model().attributeExists("agent"))
				.andExpect(model().attribute("agent", clone))
				.andExpect(model().attributeExists("factions"))
				.andExpect(model().attribute("factions", Faction.values()));
				verify(repo, never()).save(clone);
		}
	}
	
	@Test
	public void saveNewAgentShouldSendValidationErrorOnWrongFaction() throws Exception {
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		String[] factions = new String[]{"Bo", null};
		String name = "Bob";
		
		for(String faction : factions){
			Agent clone = new Agent(null, name, null);
			mockMvc.perform(post("/agent/modify/")
					.param("codename", name) 
					.param("faction", faction))
				.andExpect(view().name("agent_modify"))
				.andExpect(model().attributeExists("agent"))
				//.andExpect(model().attribute("agent", clone))
				.andExpect(model().attributeExists("factions"))
				.andExpect(model().attribute("factions", Faction.values()));
				verify(repo, never()).save(clone);
		}
	}
}
