package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bean class representing the "static" info of a portal
 * @author wisthler
 *
 */
@Entity
public class Portal {	
	static final Logger logger = LoggerFactory.getLogger(Portal.class);

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	@Size(min=3, max=16)
	private String name;

	@OneToOne
	private GPSAddress gps;
	
	@OneToOne
	private PostalAddress address;
	
	// ========== Constructors ========== //
	
	/**
	 * 
	 */
	public Portal() {
		super();
		this.address = new PostalAddress();
		this.gps = new GPSAddress();
	}
	
	// ========== Override ========== //
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Portal [id=");
		builder.append(id);
		builder.append(",\n name=");
		builder.append(name);
		builder.append(",\n gps=");
		builder.append(gps);
		builder.append(",\n address=");
		builder.append(address);
		builder.append("]");
		return builder.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((gps == null) ? 0 : gps.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Portal)) {
			return false;
		}
		Portal other = (Portal) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (gps == null) {
			if (other.gps != null) {
				return false;
			}
		} else if (!gps.equals(other.gps)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	

	// ========== Getters & Setters ========== //

	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gps
	 */
	public GPSAddress getGps() {
		return gps;
	}

	/**
	 * @param gps the gps to set
	 */
	public void setGps(GPSAddress gps) {
		this.gps = gps;
	}

	/**
	 * @return the address
	 */
	public PostalAddress getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(PostalAddress address) {
		this.address = address;
	}

	/**
	 * @return
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#getStreet()
	 */
	public String getStreet() {
		return address.getStreet();
	}

	/**
	 * @param street
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#setStreet(java.lang.String)
	 */
	public void setStreet(String street) {
		address.setStreet(street);
	}

	/**
	 * @return
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#getNumber()
	 */
	public String getNumber() {
		return address.getNumber();
	}

	/**
	 * @param number
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#setNumber(java.lang.String)
	 */
	public void setNumber(String number) {
		address.setNumber(number);
	}

	/**
	 * @return
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#getPostalCode()
	 */
	public String getPostalCode() {
		return address.getPostalCode();
	}

	/**
	 * @param postalCode
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#setPostalCode(java.lang.String)
	 */
	public void setPostalCode(String postalCode) {
		address.setPostalCode(postalCode);
	}

	/**
	 * @return
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#getLocality()
	 */
	public String getLocality() {
		return address.getLocality();
	}

	/**
	 * @param locality
	 * @see be.shoktan.ingressCompagnion.bean.PostalAddress#setLocality(java.lang.String)
	 */
	public void setLocality(String locality) {
		address.setLocality(locality);
	}

	/**
	 * @return
	 * @see be.shoktan.ingressCompagnion.bean.GPSAddress#getLongitude()
	 */
	public Double getLongitude() {
		return gps.getLongitude();
	}

	/**
	 * @param longitude
	 * @see be.shoktan.ingressCompagnion.bean.GPSAddress#setLongitude(java.lang.Double)
	 */
	public void setLongitude(Double longitude) {
		gps.setLongitude(longitude);
	}

	/**
	 * @return
	 * @see be.shoktan.ingressCompagnion.bean.GPSAddress#getLatitude()
	 */
	public Double getLatitude() {
		return gps.getLatitude();
	}

	/**
	 * @param latitude
	 * @see be.shoktan.ingressCompagnion.bean.GPSAddress#setLatitude(java.lang.Double)
	 */
	public void setLatitude(Double latitude) {
		gps.setLatitude(latitude);
	}
	
	
}