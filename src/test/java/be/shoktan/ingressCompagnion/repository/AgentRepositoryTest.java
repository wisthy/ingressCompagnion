package be.shoktan.ingressCompagnion.repository;

import static org.junit.Assert.assertEquals;

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
import be.shoktan.ingressCompagnion.model.Faction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class AgentRepositoryTest {

	@Autowired
	AgentRepository agentRepository;
	
	private static Agent[] AGENTS = new Agent[2];
	
	@BeforeClass
	public static void before(){
		AGENTS[0] = new Agent(1L, "Shoktan", Faction.RESISTANCE);
		AGENTS[1] = new Agent(2L, "Babel XVII", Faction.RESISTANCE);
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
		assertAgent(0, agentRepository.findByCodename("Shoktan"));
		assertAgent(1, agentRepository.findByCodename("Babel XVII"));
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
		assertEquals(agent, agentRepository.findOne(size + 1));
	}
}
