package be.shoktan.ingressCompagnion.bean;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortalTest {
	static final Logger logger = LoggerFactory.getLogger(PortalTest.class);

	@Test
	public void testToString() {
		Portal portal = new Portal();
		portal.setId(31L);
		portal.setLatitude(50.157431);
		portal.setLongitude(5.220810);
		portal.setName("Statue François Crépin");
		portal.setLocality("Rochefort");
		portal.setStreet("square Crépin");
		portal.setNumber("3-5");
		logger.info("portal created:: "+portal);
	}

}
