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





	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((trustLevel == null) ? 0 : trustLevel.hashCode());
		return result;
	}





	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RegisteredAgent)) {
			return false;
		}
		RegisteredAgent other = (RegisteredAgent) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (trustLevel != other.trustLevel) {
			return false;
		}
		return true;
	}
	
	
	
}
