package be.shoktan.ingressCompagnion.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * model entity representing an adress
 * @author wisthler
 *
 */
@Entity
public class PostalAddress extends AbstractAddress{
	static final Logger logger = LoggerFactory.getLogger(PostalAddress.class);

	@Column
	private String street;
	
	@Column
	private String number;
	
	@Column
	private String postalCode;
	
	@Column
	private String locality;

	// ========== Override ========== //
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostalAddress [street=");
		builder.append(street);
		builder.append(", number=");
		builder.append(number);
		builder.append(", postalCode=");
		builder.append(postalCode);
		builder.append(", locality=");
		builder.append(locality);
		builder.append(", getId()=");
		builder.append(getId());
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
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		if (!(obj instanceof PostalAddress)) {
			return false;
		}
		PostalAddress other = (PostalAddress) obj;
		if (locality == null) {
			if (other.locality != null) {
				return false;
			}
		} else if (!locality.equals(other.locality)) {
			return false;
		}
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!number.equals(other.number)) {
			return false;
		}
		if (postalCode == null) {
			if (other.postalCode != null) {
				return false;
			}
		} else if (!postalCode.equals(other.postalCode)) {
			return false;
		}
		if (street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!street.equals(other.street)) {
			return false;
		}
		return true;
	}

	
	
	// ========== Getters & Setters ========== //
	
	




	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}


	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}


	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}





	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}





	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}





	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}





	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}





	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}





	

	
}
