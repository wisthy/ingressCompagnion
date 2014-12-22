/**
 * 
 */
package be.shoktan.ingressCompagnion.repository.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.repository.AgentRepository;

/**
 * @author wisthler
 *
 */
@Repository
public class AgentHibernateRepository implements AgentRepository {
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
		return (Agent) currentSession().get(Agent.class, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.AgentRepository#findByCodename(java.lang.String)
	 */
	public Agent findByCodename(String codename) {
		return (Agent) currentSession()
				.createCriteria(Agent.class)
				.add(Restrictions.eq("codename", codename))
				.list().get(0);
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
}
