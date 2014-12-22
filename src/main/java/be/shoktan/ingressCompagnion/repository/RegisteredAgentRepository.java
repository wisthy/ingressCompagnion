package be.shoktan.ingressCompagnion.repository;

import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;

public interface RegisteredAgentRepository extends IRepository<RegisteredAgent>{
	/**
	 * search a registered agent in the repository based on his/her codename
	 * @param codename the codename of the searched registered agent
	 * @return the registered agent
	 * @throws NotFoundException when the registered agent cannot be found in the repository
	 */
	RegisteredAgent findByCodename(String codename) throws NotFoundException;
	
	/**
	 * search a registered agent in the repository based on his/her email
	 * @param email the email of the searched registered agent
	 * @return the registered agent
	 * @throws NotFoundException when the registered agent cannot be found in the repository
	 */
	RegisteredAgent findByEmail(String email) throws NotFoundException;
}
