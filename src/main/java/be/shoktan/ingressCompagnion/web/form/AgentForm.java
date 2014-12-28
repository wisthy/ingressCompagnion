package be.shoktan.ingressCompagnion.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import be.shoktan.ingressCompagnion.model.Faction;

public class AgentForm {
	@NotNull
	@Size(min=5, max=16, message="{codename.size}")
	private String codename;
	
	@NotNull
	private Faction faction;

	/**
	 * @return the codename
	 */
	public String getCodename() {
		return codename;
	}

	/**
	 * @param codename the codename to set
	 */
	public void setCodename(String codename) {
		this.codename = codename;
	}

	/**
	 * @return the faction
	 */
	public Faction getFaction() {
		return faction;
	}

	/**
	 * @param faction the faction to set
	 */
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	
}
