package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.shoktan.ingressCompagnion.model.Faction;

@Entity @Inheritance(strategy=InheritanceType.JOINED)
public class Agent {
	static final Logger logger = LoggerFactory.getLogger(Agent.class);
	
	// ==== are not supposed to change ====
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// ==== can change once in a while ====
	@Column
	@NotNull
	@Size(min=3, max=16, message="{codename.size}")
	private String codename;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Faction faction; 
	
	// ==== can change frequently      ====
	
	//private int level; // 1 <= level <= 16
	
	
	
	
	/**
	 * @param id
	 * @param codename
	 * @param faction
	 */
	public Agent(Long id, String codename, Faction faction) {
		super();
		this.id = id;
		this.codename = codename;
		this.faction = faction;
	}
	
	public Agent(){
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the codename
	 */
	public String getCodename() {
		if(logger.isDebugEnabled())logger.debug("getCodename():: "+codename);
		return codename;
	}

	/**
	 * @return the faction
	 */
	public Faction getFaction() {
		return faction;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codename == null) ? 0 : codename.hashCode());
		result = prime * result + ((faction == null) ? 0 : faction.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Agent))
			return false;
		Agent other = (Agent) obj;
		if (codename == null) {
			if (other.codename != null)
				return false;
		} else if (!codename.equals(other.codename))
			return false;
		if (faction != other.faction)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Agent [id=");
		builder.append(id);
		builder.append(", codename=");
		builder.append(codename);
		builder.append(", faction=");
		builder.append(faction);
		builder.append("]");
		return builder.toString();
	}
	
	public boolean isRegistered(){
		return this instanceof RegisteredAgent;
	}

	/**
	 * @param codename the codename to set
	 */
	public void setCodename(String codename) {
		this.codename = codename;
	}

	/**
	 * @param faction the faction to set
	 */
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	
	
	
}
