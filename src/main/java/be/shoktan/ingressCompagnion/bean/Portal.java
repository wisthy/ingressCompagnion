package be.shoktan.ingressCompagnion.bean;

/**
 * bean class representing a portal
 * @author wisthler
 *
 */
public class Portal {
	
	// ==== can change once in a while ====
	
	private String name;
	
	// two variables? or a new object to encapsulate both?
	private double latitude;
	private double longitude;
	
	// this one will have an address object;
	private String address;
	
	// ==== can change frequently ====
	
	private int ownership; // 2 factions + no-one
	
	private Agent guardian; // or agent name?
	private boolean flagedAsPivot;
	
	private Object[] resonators = new Object[8];
	private Object[] mods = new Object[4];
	
	// ==== are not supposed to change ====
	
	
}
