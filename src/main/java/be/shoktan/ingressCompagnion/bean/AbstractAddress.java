package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity @Inheritance(strategy=InheritanceType.JOINED)
public abstract class AbstractAddress {
	static final Logger logger = LoggerFactory.getLogger(AbstractAddress.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// ========== Getters & Setters ========== //
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
