package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import be.shoktan.ingressCompagnion.model.Faction;
import be.shoktan.ingressCompagnion.model.Trust;

@Entity
@Table(name="registered_agent")
public class RegisteredAgent extends Agent {
	// ==== are not supposed to change ====

	@Column(name="email")
	@Email
	private String email;
	
	
	
	// ==== can change once in a while ====



	// ==== can change frequently      ====
	@Column(name="trust_level")
	@Enumerated(EnumType.STRING)
	private Trust trustLevel;



	
	
	/**
	 * @param id
	 * @param codename
	 * @param faction
	 * @param email
	 * @param trustLevel
	 */
	public RegisteredAgent(Long id, String codename, Faction faction,
			String email, Trust trustLevel) {
		super(id, codename, faction);
		this.email = email;
		this.trustLevel = trustLevel;
	}


	


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}





	/**
	 * @return the trustLevel
	 */
	public Trust getTrustLevel() {
		return trustLevel;
	}





	protected RegisteredAgent(){}
}
