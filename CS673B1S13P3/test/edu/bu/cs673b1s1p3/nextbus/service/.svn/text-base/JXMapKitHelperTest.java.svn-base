/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.junit.Test;

import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Direction;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;

/**
 * Unit tests for the JXMapKitHelper class.
 * 
 * @author ton and jimson
 *
 */
public class JXMapKitHelperTest {

	/**
	 * Test that the JXMapKitHelper class converts
	 * Stops to GEOPostions correctly.
	 */
	@Test
	public void testConvertStopsToGeoPositions() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();
		// Get the MBTA Agency
		Agency mbta = nextBus.getAgency("mbta");
		Assert.assertNotNull(mbta);

		// Get route 225
		Route twoTwentyFive = nextBus.getRoute(mbta, "225");
		Assert.assertNotNull(twoTwentyFive);
		
		// Get Route Config
		RouteConfig routeConfig = nextBus.getRouteConfig(twoTwentyFive);
		Assert.assertNotNull(routeConfig);
		
		// Use the JXMapKitHelper to get GeoPositions
		ArrayList<GeoPosition> geoPositions = JXMapKitHelper.convertStopsToGeoPositions(routeConfig.getBusRoute().getStops());
		
		Assert.assertNotNull(geoPositions);
		Assert.assertTrue(!geoPositions.isEmpty());
		
		// List out the positions
		for (GeoPosition pos : geoPositions) {
			System.out.println(pos);
		}
	
	}
	
	@Test
	public void testConvertStopsToWaypoints() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();;

		// Get the MBTA Agency
		Agency mbta = nextBus.getAgency("mbta");
		Assert.assertNotNull(mbta);

		// Get route 225
		Route twoTwentyFive = nextBus.getRoute(mbta, "225");
		Assert.assertNotNull(twoTwentyFive);		
		
		// Get Route Config
		RouteConfig routeConfig = nextBus.getRouteConfig(twoTwentyFive);
		Assert.assertNotNull(routeConfig);
		
		Set<Waypoint> waypoints = JXMapKitHelper.convertStopsToWaypoints(routeConfig.getBusRoute().getStops());
		
		Assert.assertNotNull(waypoints);
		Assert.assertTrue(!waypoints.isEmpty());
	}
	
	@Test
	public void testConvertPointsToGeoPositions() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();;

		// Get the MBTA Agency
		Agency mbta = nextBus.getAgency("mbta");
		Assert.assertNotNull(mbta);

		// Get route 225
		Route twoTwentyFive = nextBus.getRoute(mbta, "225");
		Assert.assertNotNull(twoTwentyFive);
		
		// Get route's direction
		Direction weymouthLandingviaShawSt = nextBus.getDirection(mbta, twoTwentyFive, "225_0_var1");
		
		// Get Route Config
		RouteConfig routeConfig = nextBus.getRouteConfig(twoTwentyFive);
		Assert.assertNotNull(routeConfig);
		
		//Use the FindStopsHelper to find all lats and lons for the stops in a certain direction
		List<Stop> stopsInDirection = FindStopsHelper.identifyStopsForDirection(weymouthLandingviaShawSt, routeConfig.getBusRoute().getStops());

		// Use the JXMapKitHelper to get GeoPositions
		ArrayList<GeoPosition> geoPositions = JXMapKitHelper.convertStopsToGeoPositions(stopsInDirection);
		
		Assert.assertNotNull(geoPositions);
		Assert.assertTrue(!geoPositions.isEmpty());
		
		// List out the positions
		for (GeoPosition pos : geoPositions) {
			System.out.println(pos);
		}
	}
	
	/**
	 * Tests the method in FindStopsHelpers class (should have moved
	 * to JXMapKitHelper class)
	 */
	@Test
	public void testIdentifyStopsForDirection() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();;

		// Get the MBTA Agency
		Agency mbta = nextBus.getAgency("mbta");
		Assert.assertNotNull(mbta);

		// Get route 225
		Route twoTwentyFive = nextBus.getRoute(mbta, "225");
		Assert.assertNotNull(twoTwentyFive);
		
		// Get route's direction
		Direction weymouthLandingviaShawSt = nextBus.getDirection(mbta, twoTwentyFive, "225_0_var1");
		
		// Get Route Config
		RouteConfig routeConfig = nextBus.getRouteConfig(twoTwentyFive);
		Assert.assertNotNull(routeConfig);
		
		//Use the FindStopsHelper to find all lats and lons for the stops in a certain direction
		List<Stop> stopsInDirection = FindStopsHelper.identifyStopsForDirection(weymouthLandingviaShawSt, routeConfig.getBusRoute().getStops());

		Assert.assertNotNull(stopsInDirection);
		Assert.assertTrue(!stopsInDirection.isEmpty());
	
		// List out the positions
		for (Stop s : stopsInDirection) {
			System.out.println(s);
		}
	}
}
