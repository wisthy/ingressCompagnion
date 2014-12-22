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

import be.shoktan.ingressCompagnion.model.Faction;

@Entity @Inheritance(strategy=InheritanceType.JOINED)
public class Agent {
	// ==== are not supposed to change ====
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// ==== can change once in a while ====
	@Column
	@NotNull
	@Size(min=5, max=16, message="{codename.size}")
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
		return codename;
	}

	/**
	 * @return the faction
	 */
	public Faction getFaction() {
		return faction;
	}
	
	
	
}
