package be.shoktan.ingressCompagnion.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.config.RepositoryTestConfig;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.model.Faction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class AgentRepositoryTest {

	@Autowired
	AgentRepository agentRepository;
	
	private static Agent[] AGENTS = new Agent[3];
	
	@BeforeClass
	public static void before(){
		AGENTS[0] = new Agent(1L, "God", Faction.RESISTANCE);
		AGENTS[1] = new Agent(2L, "Second", Faction.RESISTANCE);
		AGENTS[2] = new Agent(3L, "TacTac", Faction.ENLIGHTED);
	}
	private static void assertAgent(int index, Agent actual){
		Agent expected = AGENTS[index];
		assertEquals(expected, actual);
	}
	
	
	/* ==== tests === */
		
	@Test
	@Transactional
	public void count() {
		assertEquals(AGENTS.length, agentRepository.count());
	}
	
	@Test
	@Transactional
	public void findAll(){
		List<Agent> all = agentRepository.findAll();
		assertEquals(AGENTS.length, all.size());
		for(int i = 0; i < AGENTS.length; i++){
			assertEquals(AGENTS[i], all.get(i));
		}
	}
	
	@Test
	@Transactional
	public void findByCodename(){
		assertAgent(0, agentRepository.findByCodename("God"));
		assertAgent(1, agentRepository.findByCodename("Second"));
	}
	
	@Test
	@Transactional
	public void findByCodenameShouldBeCaseInsensitive(){
		for(String name : new String[]{"God", "god", "GOD"}){
			assertAgent(0, agentRepository.findByCodename(name));
		}
	}
	
	@Test
	@Transactional
	public void findByCodenameNotFound(){
		try{
			agentRepository.findByCodename("void");
			fail("this agent doesn't exist");
		}catch(NotFoundException e){
			
		}
	}
	
	@Test
	@Transactional
	public void findOne(){
		assertAgent(0, agentRepository.findOne(1L));
		assertAgent(1, agentRepository.findOne(2L));
	}
	
	@Test
	@Transactional
	public void saveNewAgent(){
		int size = AGENTS.length;
		assertEquals(size, agentRepository.count());
		Agent agent = new Agent(null, "newbee", Faction.ENLIGHTED);
		Agent saved = agentRepository.save(agent);
		assertEquals(agent, saved);
		assertEquals(size + 1, agentRepository.count());
		assertEquals(agent, agentRepository.findOne(saved.getId()));
	}
	
	@Test
	@Transactional
	public void updateAgent(){
		int size = AGENTS.length;
		assertEquals(size, agentRepository.count());
		
		Agent agent = agentRepository.findOne(1L);
		String codename = "maxiKoin";
		agent.setCodename(codename);
		agentRepository.update(agent);
		
		assertEquals(size, agentRepository.count());
		
		List<Agent> agents = new ArrayList<Agent>();
		agents.add(agentRepository.findOne(agent.getId()));
		agents.add(agentRepository.findByCodename(agent.getCodename()));
		agents.add(agentRepository.findByCodename(codename));
		
		for(Agent saved : agents){
			assertEquals(agent, saved);
			assertEquals(new Long(1L), saved.getId());
			assertEquals(codename, saved.getCodename());
		}
		
		
	}
	
	@Test
	@Transactional
	public void notFoundOnFindByCodename(){
		try {
			Agent a = agentRepository.findByCodename("void");
			assertNull("agent should be null", a);
		} catch (NotFoundException e) {
			assertTrue("the exception should be raiserd here", true);
		}
	}
	
	@Test
	@Transactional
	public void notFoundOnFindOne(){
		try {
			Agent a = agentRepository.findOne(2042L);
			assertNull("agent should be null", a);
		} catch (NotFoundException e) {
			assertTrue("the exception should be raiserd here", true);
		}
	}
	
	@Test
	@Transactional
	public void notFoundOnDelete(){
		try {
			agentRepository.delete("void");
			fail("should not be able to delete this agent");
		} catch (NotFoundException e) {
			assertTrue("the exception should be raiserd here", true);
		}
	}
	
	@Test
	@Transactional
	public void deleteAgent(){
		int size = AGENTS.length;
		assertEquals(size, agentRepository.count());
		
		long id = 1L;
		Agent a = agentRepository.findOne(id);
		
		agentRepository.delete(a.getCodename());
		
		assertEquals(size - 1, agentRepository.count());
		
		try {
			Agent b = agentRepository.findOne(id);
			assertNull("agent should be null", a);
		} catch (NotFoundException e) {
			assertTrue("the exception should be raiserd here", true);
		}
		
	}
}
