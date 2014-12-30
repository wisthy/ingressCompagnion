package be.shoktan.ingressCompagnion.repository;

import be.shoktan.ingressCompagnion.bean.Agent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;

/**
 * Repository interface with operations for {@link Agent} persistence.
 * @author wisthler
 *
 */
public interface AgentRepository extends IRepository<Agent> {

	/**
	 * search an agent in the repository based on his/her codename
	 * @param codename the codename of the searched agent
	 * @return the agent
	 * @throws NotFoundException when the agent cannot be found in the repository
	 */
	Agent findByCodename(String codename) throws NotFoundException;
	
	/**
	 * remove an agent from the repository
	 * @param codename the codename of the agent to delete 
	 */
	void delete(String codename);
}
