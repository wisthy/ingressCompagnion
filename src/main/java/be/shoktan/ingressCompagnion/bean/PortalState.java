package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bean class representing the "dynamic" info of a portal
 * @author wisthler
 *
 */
@Entity
public class PortalState {
	static final Logger logger = LoggerFactory.getLogger(PortalState.class);
	
	

	private int ownership; // 2 factions + no-one

	private Agent guardian; // or agent name?
	private boolean flagedAsPivot;

	private Object[] resonators = new Object[8];
	private Object[] mods = new Object[4];
}
