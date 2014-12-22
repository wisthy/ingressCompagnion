/**
 * 
 */
package be.shoktan.ingressCompagnion.model;

/**
 * @author wisthler
 * enum representing the factions
 */
public enum Faction {
	ENLIGHTED(Ownership.ENLIGHTED),
	NEUTRAL(Ownership.NEUTRAL);
	
	private Ownership ownership;

	/**
	 * @param ownership
	 */
	private Faction(Ownership ownership) {
		this.ownership = ownership;
	}

	/**
	 * @return the ownership
	 */
	public Ownership getOwnership() {
		return ownership;
	}
	
	/**
	 * test if the ownership in parameter is the same as the faction
	 * @param ownership the ownership to test
	 * @return true if the ownership in param is the same as the faction, false elsewhere
	 */
	public boolean belongTo(Ownership ownership){
		return this.ownership.equals(ownership);
	}
}
