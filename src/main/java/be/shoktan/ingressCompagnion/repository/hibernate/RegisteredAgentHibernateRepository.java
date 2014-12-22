package be.shoktan.ingressCompagnion.repository.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.repository.RegisteredAgentRepository;

@Repository
public class RegisteredAgentHibernateRepository implements RegisteredAgentRepository{
	static final Logger logger = LoggerFactory.getLogger(RegisteredAgentHibernateRepository.class);
	private SessionFactory sessionFactory;

	@Inject
	public RegisteredAgentHibernateRepository(SessionFactory sessionFactory) {
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
	public RegisteredAgent save(RegisteredAgent item) {
		Serializable id = currentSession().save(item); //<co id="co_UseCurrentSession"/>
		return new RegisteredAgent((Long)id, item.getCodename(), item.getFaction(), item.getEmail(), item.getTrustLevel());
	}

	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.IRepository#findOne(long)
	 */
	public RegisteredAgent findOne(long id) {
		RegisteredAgent registeredAgent = (RegisteredAgent) currentSession().get(RegisteredAgent.class, id);
		if(registeredAgent == null){
			String message = "cannot find a Registered Agent with id <"+id+">";
			logger.error(message);
			throw new NotFoundException(RegisteredAgent.class, message);
		}
		return registeredAgent;
	}


	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.RegisteredAgentRepository#findByCodename(java.lang.String)
	 */
	public RegisteredAgent findByCodename(String codename) {
		@SuppressWarnings("unchecked")
		List<RegisteredAgent> list = currentSession()
		.createCriteria(Agent.class, "agent")
		.add(Restrictions.eq("codename", codename).ignoreCase())
		.add(Restrictions.eq("agent.class", RegisteredAgent.class))
		.list();
		if(list.size() == 0){
			String message = "cannot find a Registered Agent with codename <"+codename+">";
			logger.error(message);
			throw new NotFoundException(RegisteredAgent.class, message);
		}else{
			return list.get(0);
		}
	}




	/* (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.RegisteredAgentRepository#findByEmail(java.lang.String)
	 */
	@Override
	public RegisteredAgent findByEmail(String email) throws NotFoundException {
		@SuppressWarnings("unchecked")
		List<RegisteredAgent> list = currentSession()
		.createCriteria(RegisteredAgent.class)
		.add(Restrictions.eq("email", email).ignoreCase())
		.list();
		if(list.size() == 0){
			String message = "cannot find a Registered Agent with email <"+email+">";
			logger.error(message);
			throw new NotFoundException(RegisteredAgent.class, message);
		}else{
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	/*
	 * (non-Javadoc)
	 * @see be.shoktan.ingressCompagnion.repository.IRepository#findAll()
	 */
	public List<RegisteredAgent> findAll() {
		return (List<RegisteredAgent>) currentSession()
				.createCriteria(RegisteredAgent.class).list();
	}
}
