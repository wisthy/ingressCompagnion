package be.shoktan.ingressCompagnion.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
import be.shoktan.ingressCompagnion.config.RepositoryTestConfig;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.model.Faction;
import be.shoktan.ingressCompagnion.model.Trust;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class RegisteredAgentRepositoryTest {

	@Autowired
	RegisteredAgentRepository repo;
	
	private static RegisteredAgent[] AGENTS = new RegisteredAgent[2];
	
	@BeforeClass
	public static void before(){
		AGENTS[0] = new RegisteredAgent(1L, "God", Faction.RESISTANCE, "god@test.be", Trust.ADMIN);
		AGENTS[1] = new RegisteredAgent(2L, "Second", Faction.RESISTANCE, "second@test.be", Trust.SEMI_TRUSTED);
	}
	private static void assertAgent(int index, RegisteredAgent actual){
		RegisteredAgent expected = AGENTS[index];
		assertEquals(expected, actual);
	}
	
	
	/* ==== tests === */
		
	@Test
	@Transactional
	public void count() {
		assertEquals(AGENTS.length, repo.count());
	}
	
	@Test
	@Transactional
	public void findAll(){
		List<RegisteredAgent> all = repo.findAll();
		assertEquals(AGENTS.length, all.size());
		for(int i = 0; i < AGENTS.length; i++){
			assertEquals(AGENTS[i], all.get(i));
		}
	}
	
	@Test
	@Transactional
	public void findByCodename(){
		assertAgent(0, repo.findByCodename("God"));
		assertAgent(1, repo.findByCodename("Second"));
	}
	
	@Test
	@Transactional
	public void findByCodenameNotFound(){
		String[] users = new String[]{"void", "TacTac"};
		for(String user : users){
			try{
				repo.findByCodename(user);
				fail("agent <"+user+"> shouldn't exist");
			}catch(NotFoundException e){
			}
		}
	}
	
	@Test
	@Transactional
	public void findOne(){
		assertAgent(0, repo.findOne(1L));
		assertAgent(1, repo.findOne(2L));
	}
	
	@Test
	@Transactional
	public void saveNewAgent(){
		int size = AGENTS.length;
		assertEquals(size, repo.count());
		RegisteredAgent agent = new RegisteredAgent(null, "newbee", Faction.ENLIGHTED, "newbee@test.be", Trust.NONE);
		RegisteredAgent saved = repo.save(agent);
		assertEquals(agent, saved);
		assertEquals(size + 1, repo.count());
		assertEquals(agent, repo.findOne(saved.getId()));
	}
	
	@Test
	@Transactional
	public void saveExistingAgent(){
		long id = 1L;
		RegisteredAgent before = repo.findOne(id);
		String oldEmail = before.getEmail();
		assertEquals(AGENTS[0].getEmail(), oldEmail);
		before.setEmail("more-Than-God@test.be");
		RegisteredAgent after = repo.save(before);
		assertEquals(before, after);
		
		RegisteredAgent refresh = repo.findOne(id);
		
		for(RegisteredAgent agent : new RegisteredAgent[]{after, refresh}){
			assertEquals(new Long(id), after.getId());
			assertEquals("more-Than-God@test.be", agent.getEmail());
		}
	}
}
