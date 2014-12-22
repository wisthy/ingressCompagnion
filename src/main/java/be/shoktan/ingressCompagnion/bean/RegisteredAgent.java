package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;

import be.shoktan.ingressCompagnion.model.Faction;
import be.shoktan.ingressCompagnion.model.Trust;

@Entity
public class RegisteredAgent extends Agent {
	// ==== are not supposed to change ====

	@Column(name="email")
	@Email
	private String email;
	
	
	
	// ==== can change once in a while ====



	// ==== can change frequently      ====

	private Trust trustLevel;



	/**
	 * @param id
	 * @param codename
	 * @param faction
	 */
	public RegisteredAgent(Long id, String codename, Faction faction) {
		super(id, codename, faction);
		// TODO Auto-generated constructor stub
	}
}
