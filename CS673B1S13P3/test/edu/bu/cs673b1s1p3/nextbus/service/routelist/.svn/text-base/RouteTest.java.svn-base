/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routelist;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test for the Route POJO from the RouteList based service.
 * 
 * @author ton
 *
 */
public class RouteTest {

	/**
	 * Test an Route be ensuring that two Routes with
	 * the same tag are considered equal. Similarly, test
	 * inequality.
	 */
	@Test
	public void testEquals() {
		// Test equality - same tags should be equal
		Route route1 = new Route("tag1", "Title1");
		Route route2 = new Route("tag1", "Title1");
		Assert.assertEquals(route1, route2);
		
		// Test inequality - diff tags should be non-equal
		Route route3 = new Route("tag3", "Title3");
		Assert.assertNotSame(route1, route3);
	}
}
