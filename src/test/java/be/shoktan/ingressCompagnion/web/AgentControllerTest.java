package be.shoktan.ingressCompagnion.web;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.model.Faction;
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
			mockMvc.perform(get("/agent/"+name))
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
		
		mockMvc.perform(get("/agent/void"))
			.andExpect(status().is(404));
	}
}
