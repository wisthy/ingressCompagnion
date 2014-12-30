/**
 * 
 */
package be.shoktan.ingressCompagnion.repository.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.repository.AgentRepository;

/**
 * @author wisthler
 *
 */
@Transactional
@Repository
public class AgentHibernateRepository implements AgentRepository {
	static final Logger logger = LoggerFactory.getLogger(AgentHibernateRepository.class);
	private SessionFactory sessionFactory;
	
	@Inject
	public AgentHibernateRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory; //<co id="co_InjectSessionFactory"/>
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();//<co id="co_RetrieveCurrentSession"/>
	}
	
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.IRepository#count()
	 */
	public long count() {
		return findAll().size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.IRepository#save(java.lang.Object)
	 */
	public Agent save(Agent item) {
		Serializable id = currentSession().save(item); //<co id="co_UseCurrentSession"/>
		
		return new Agent((Long)id, item.getCodename(), item.getFaction());
	}
	
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.IRepository#findOne(long)
	 */
	public Agent findOne(long id) {
		Agent agent = (Agent) currentSession().get(Agent.class, id);
		if(agent == null){
			String message = "cannot find an Agent with id <"+id+">";
			logger.error(message);
			throw new NotFoundException(Agent.class, message);
		}else{
			return agent;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.AgentRepository#findByCodename(java.lang.String)
	 */
	public Agent findByCodename(String codename) {
		@SuppressWarnings("unchecked")
		List<Agent> list = (List<Agent>)currentSession()
				.createCriteria(Agent.class)
				.add(Restrictions.eq("codename", codename).ignoreCase())
				.list();
		if(list.size() == 0){
			String message = "cannot find an Agent with codename <"+codename+">";
			logger.error(message);
			throw new NotFoundException(Agent.class, message);
		}else{
			return list.get(0);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.IRepository#findAll()
	 */
	public List<Agent> findAll() {
		return (List<Agent>) currentSession()
				.createCriteria(Agent.class).list();
	}

	/* (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.AgentRepository#delete(java.lang.String)
	 */
	@Override
	public void delete(String codename) {
		Agent agent = (Agent) currentSession()
					.createCriteria(Agent.class)
					.add(Restrictions.eq("codename", codename).ignoreCase())
					.uniqueResult();
		currentSession().delete(agent);
		
	}
	
	
	
}
