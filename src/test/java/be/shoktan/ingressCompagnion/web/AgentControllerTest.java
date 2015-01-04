package be.shoktan.ingressCompagnion.web;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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

public class AgentControllerTest {
	@Test
	public void shouldShowProfile() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		// step 2: init test variables/mocking
		String[] names = new String[]{"Bob", "bob", "BOB"};
		Agent saved = new Agent(1L, "Bob", Faction.RESISTANCE);
		Agent clone = new Agent(1L, "Bob", Faction.RESISTANCE);
		doReturn(saved).when(repo).findOne(saved.getId());
	
		for(String name : names){
			// step 2B: init test variables/mocking - loop
			doReturn(saved).when(repo).findByCodename(name);
			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(get("/agent/show/{name}", name));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_profile"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", clone));
			performed.andExpect(model().attribute("showActions", true));
			verify(repo).findByCodename(name);
		}
	}

	
	
	@Test
	public void should404onShowProfile() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		// step 2: init test variables/mocking
		String name = "void";
		doThrow(NotFoundException.class).when(repo).findByCodename(name);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(get("/agent/show/{name}", name));
		
		// step 4: check the result
		performed.andExpect(status().is(404));
	}

	
	
	@Test
	public void shouldShowAllAgents() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		// step 2: init test variables/mocking
		List<Agent> saved = new ArrayList<Agent>();
		Agent bob = new Agent(1L, "Bob", Faction.RESISTANCE);
		Agent tul = new Agent(2L, "Tul", Faction.ENLIGHTED);
		Agent koin = new RegisteredAgent(3L, "koin", Faction.RESISTANCE, "koin@test.be", Trust.ADMIN);
		saved.add(bob);
		saved.add(tul);
		saved.add(koin);
		doReturn(saved).when(repo).findAll();
	
		// step 3: do the test
		ResultActions performed = mockMvc.perform(get("/agent/list"));
		
		// step 4: check the result
		performed.andExpect(view().name("agents"));
		performed.andExpect(model().attributeExists("agents"));
		performed.andExpect(model().attribute("agents", Matchers.contains(saved.toArray())));
		verify(repo).findAll();

		for(Agent a : saved){
			performed.andExpect(model().attribute("agents", hasItem(a)));
		}
	}

	@Test
	public void shouldShowEditProfile() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		// step 2: init test variables/mocking - fixe
		Agent saved = new Agent(1L, "Bob", Faction.RESISTANCE);
		Agent clone = new Agent(1L, "Bob", Faction.RESISTANCE);
		String[] names = new String[]{"Bob", "bob", "BOB"};
		
		for(String name : names){
			// step 2B: init test variables/mocking - loop
			doReturn(saved).when(repo).findByCodename(name);
			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(get("/agent/modify/{codename}", name));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_modify"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", clone));
			performed.andExpect(model().attributeExists("factions"));
			performed.andExpect(model().attribute("factions", Faction.values()));
			performed.andExpect(model().attribute(AgentController.FLAG_RETURN, AgentController.RETURN_MODIFY));
		}
	}
	
	@Test
	public void shouldShowAddAgent() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		// step 2: init test variables/mocking - fixe
		//String[] names = new String[]{"Bob", "bob", "BOB"};
		
		//for(String name : names){			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(get("/agent/add"));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_modify"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", new Agent()));
			performed.andExpect(model().attributeExists("factions"));
			performed.andExpect(model().attribute("factions", Faction.values()));
			performed.andExpect(model().attribute(AgentController.FLAG_RETURN, AgentController.RETURN_CREATION));
		//}
	}

	@Test
	public void should404onShowEditProfile() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();
		
		// step 2: init test variables/mocking
		String name = "void";
		doThrow(NotFoundException.class).when(repo).findByCodename(name);
		
		
		// step 3: do the test
		ResultActions performed = mockMvc.perform(get("/agent/modify/{name}", name));
		
		// step 4: check the result
		performed.andExpect(status().is(404));
	}

	@Test
	public void modifyAgentOK() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String name = "bob";
		Agent mockedAgent = new Agent(null, name, Faction.ENLIGHTED);
		doReturn(mockedAgent).when(repo).findByCodename(name);
		
		String newName = "Bob2";
		Faction faction = Faction.RESISTANCE;
		//Agent saved = new Agent(mockedAgent.getId(), newName, faction);
		//doReturn(saved).when(repo).save(saved);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(post("/agent/modify/{codename}", name)
				.param("codename", newName)
				.param("faction", faction.toString()));

		// step 4: check the result
		performed.andExpect(redirectedUrl("/agent/show/"+newName));
		performed.andExpect(model().attribute("codename", newName));
		performed.andExpect(flash().attribute("agent", mockedAgent));
		verify(repo).update(mockedAgent);
	}

	@Test
	public void modifyAgentShouldSendValidationErrorOnWrongCodename() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String[] names = new String[]{"Bo", null, "Bob45678901234567"};

		for(String name : names){
			// step 2B: init test variables/mocking - loop
			Agent clone = new Agent(null, name, Faction.ENLIGHTED);
			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(post("/agent/modify/{name}", "Bob")
					.param("codename", name) 
					.param("faction", Faction.ENLIGHTED.toString()));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_modify"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", clone));
			performed.andExpect(model().attributeExists("factions"));
			performed.andExpect(model().attribute("factions", Faction.values()));
			performed.andExpect(model().attribute(AgentController.FLAG_RETURN, AgentController.RETURN_MODIFY));
			verify(repo, never()).update(clone);
		}
	}

	@Test
	public void modifyAgentShouldSendValidationErrorOnWrongFaction() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String[] factions = new String[]{"Bo", null};
		String name = "Bob";

		for(String faction : factions){
			// step 2B: init test variables/mocking - loop
			Agent clone = new Agent(null, name, null);
			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(post("/agent/modify/{name}", name)
					.param("codename", name) 
					.param("faction", faction));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_modify"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", hasProperty("codename", is(name))));
			performed.andExpect(model().attribute("agent", clone));
			performed.andExpect(model().attributeExists("factions"));
			performed.andExpect(model().attribute("factions", Faction.values()));
			performed.andExpect(model().attribute(AgentController.FLAG_RETURN, AgentController.RETURN_MODIFY));
			verify(repo, never()).update(clone);
		}
	}


	@Test
	public void createAgentOK() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String name = "Bob2";
		Agent saved = new Agent(null, name, Faction.ENLIGHTED);
		doReturn(saved).when(repo).findByCodename(name);
		doReturn(saved).when(repo).save(saved);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(post("/agent/add")
				.param("codename", name) 
				.param("faction", Faction.ENLIGHTED.toString()));
		
		// step 4: check the result
		performed.andExpect(redirectedUrl("/agent/show/"+name));
		performed.andExpect(flash().attribute("agent", saved));
		verify(repo).save(saved);
	}

	@Test
	public void createAgentShouldSendValidationErrorOnWrongCodename() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String[] names = new String[]{"Bo", null, "Bob45678901234567"};

		for(String name : names){
			// step 2B: init test variables/mocking - loop
			Agent clone = new Agent(null, name, Faction.ENLIGHTED);
			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(post("/agent/add")
					.param("codename", name) 
					.param("faction", Faction.ENLIGHTED.toString()));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_modify"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", clone));
			performed.andExpect(model().attributeExists("factions"));
			performed.andExpect(model().attribute("factions", Faction.values()));
			performed.andExpect(model().attribute(AgentController.FLAG_RETURN, AgentController.RETURN_CREATION));
			verify(repo, never()).save(clone);
		}
	}

	@Test
	public void createAgentShouldSendValidationErrorOnWrongFaction() throws Exception {
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String[] factions = new String[]{"Bo", null};
		String name = "Bob";

		for(String faction : factions){
			// step 2B: init test variables/mocking - loop
			Agent clone = new Agent(null, name, null);
			
			// step 3: do the test
			ResultActions performed = mockMvc.perform(post("/agent/add")
					.param("codename", name) 
					.param("faction", faction));
			
			// step 4: check the result
			performed.andExpect(view().name("agent_modify"));
			performed.andExpect(model().attributeExists("agent"));
			performed.andExpect(model().attribute("agent", clone));
			performed.andExpect(model().attributeExists("factions"));
			performed.andExpect(model().attribute("factions", Faction.values()));
			performed.andExpect(model().attribute(AgentController.FLAG_RETURN, AgentController.RETURN_CREATION));
			verify(repo, never()).save(clone);
		}
	}

	@Test
	public void showDeleteAgentPage() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		Agent mockedAgent = new Agent(1L, "Bob", Faction.ENLIGHTED);
		Agent mockedAgentClone = new Agent(1L, "Bob", Faction.ENLIGHTED);
		when(repo.findByCodename("bob")).thenReturn(mockedAgent);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(get("/agent/delete/bob"));

		// step 4: check the result
		performed.andExpect(view().name("agent_delete"));
		performed.andExpect(model().attribute("agent", mockedAgentClone));
	}

	@Test
	public void deleteAgentOK() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String name = "Bob";

		// step 3: do the test
		ResultActions performed = mockMvc.perform(post("/agent/delete/"+name));

		// step 4: check the result
		performed.andExpect(redirectedUrl("/agent/list"));
		performed.andExpect(flash().attribute("status", "deleted"));
		performed.andExpect(flash().attribute("codename", name));
		verify(repo, atLeastOnce()).delete(name);
	}

	@Test
	public void deleteUnknowAgent() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String name = "Bob";
		doThrow(new NotFoundException(Agent.class, "the agent to delete does not exist")).when(repo).findByCodename(name);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(post("/agent/delete/"+name));

		// step 4: check the result
		performed.andExpect(status().is(404));
		verify(repo, never()).delete(name);
	}
	
	@Test
	public void deleteRegisteredAgent() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String name = "Bob";
		Agent mocked = new RegisteredAgent(1L, name, Faction.ENLIGHTED, "koin@test.be", Trust.ADMIN);
		doReturn(mocked).when(repo).findByCodename(name);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(post("/agent/delete/"+name));

		// step 4: check the result
		performed.andExpect(redirectedUrl("/agent/list"));
		performed.andExpect(flash().attribute("codename", name));
		performed.andExpect(flash().attribute("status", "RegisteredAgent"));
		verify(repo, atLeastOnce()).findByCodename(name);
		verify(repo, never()).delete(name);
	}
	
	@Test
	public void agentShouldBeCachedOnRedirect() throws Exception{
		// step 1: init environment/bean/archi stuff
		AgentRepository repo = mock(AgentRepository.class);
		AgentController control = new AgentController(repo);
		MockMvc mockMvc = standaloneSetup(control).build();

		// step 2: init test variables/mocking
		String name = "Bob";
		Agent mocked = new RegisteredAgent(1L, name, Faction.ENLIGHTED, "koin@test.be", Trust.ADMIN);
		doReturn(mocked).when(repo).findByCodename(name);

		// step 3: do the test
		ResultActions performed = mockMvc.perform(get("/agent/show/{name}", name)
				.flashAttr("agent", mocked));

		// step 4: check the result
		performed.andExpect(view().name("agent_profile"));
		performed.andExpect(model().attributeExists("agent"));
		performed.andExpect(model().attribute("agent", mocked));
		performed.andExpect(model().attribute("showActions", true));
		verify(repo, never()).findByCodename(name);
	}
}
