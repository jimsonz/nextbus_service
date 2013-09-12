/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.agencylist;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.junit.Test;


import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;

/**
 * Unit test class for the Agency POJO.
 * 
 * @author ton
 *
 */
public class AgencyTest {

	/**
	 * Test an Agency be ensuring that two Agencies with
	 * the same tag are considered equal. Similarly, test
	 * inequality.
	 */
	@Test
	public void testEquals() {
		// Test equality - same tags should be equal
		Agency agency1 = new Agency("tag1", "Title1", "Region1", "Short title 1");
		Agency agency2 = new Agency("tag1", "Title1", "Region1", "Short title 1");
		Assert.assertEquals(agency1, agency2);
		
		// Test inequality - diff tags should be non-equal
		Agency agency3 = new Agency("tag3", "Title3", "Region3", "Short title 3");
		Assert.assertNotSame(agency1, agency3);
	}
}
