package be.shoktan.ingressCompagnion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2471309489388137726L;
	private Class impactedClass;

	/**
	 * @param message
	 */
	public NotFoundException(Class clazz, String message) {
		super("error finding instance of class <"+clazz.toString()+"> :: "+message);
		this.impactedClass = clazz;
	}
	
	

}
