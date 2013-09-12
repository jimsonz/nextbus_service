/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.Test;

import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.RouteList;

/**
 * NextBusIntegrationTest is intended to be a Junit Test
 * that submits a number of calls to the NextBusService 
 * and tests the integration of the multiple services 
 * offered.
 * 
 * @author ton
 *
 */
public class NextBusIntegrationTest {

	@Test
	public void testIntegration() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// Get the MBTA Agency
		Agency mbta = nextBus.getAgency("mbta");
		Assert.assertNotNull(mbta);
		
		System.out.println("\n MBTA AGENCY:");
		System.out.println(mbta);

		// Get the RouteList for the MBTA
		RouteList routeList = nextBus.getRouteList(mbta);
		Assert.assertNotNull(routeList);
		
		// Print out a handful of routes (via Route.toString())
		System.out.println("\n ROUTES:");
		System.out.println(routeList);

		// Get route 225
		Route twoTwentyFive = nextBus.getRoute(mbta, "225");
		Assert.assertNotNull(twoTwentyFive);
		
		
		// Get Route Config
		RouteConfig routeConfig = nextBus.getRouteConfig(mbta, "225");
		Assert.assertNotNull(routeConfig);
		System.out.println(routeConfig);
	}
	
	@Test
	public void getAllRouteConfigs() throws ServiceUnavailableException, InterruptedException {
		NextBusService nextBus = NextBusService.getInstance();

		// Get the MBTA Agency
		Agency mbta = nextBus.getAgency("mbta");
		Assert.assertNotNull(mbta);
		
		System.out.println("\n MBTA AGENCY:");
		System.out.println(mbta);

		// Get the RouteList for the MBTA
		RouteList routeList = nextBus.getRouteList(mbta);
		Assert.assertNotNull(routeList);
		
		// Get all RouteConfigs
		long current = System.currentTimeMillis();
		Map<String, RouteConfig> allRouteConfigs = new HashMap<String, RouteConfig>(routeList.getRoutes().size());
		for (Route route : routeList.getRoutes()) {
			RouteConfig routeConfig = nextBus.getRouteConfig(mbta, route.getTag());
			allRouteConfigs.put(route.getTag(), routeConfig);
			Thread.sleep(100);
		}
		System.out.println("It took " + (System.currentTimeMillis() - current) + " ms to get all routeConfigs.");
		
		// Sizes should be equal
		Assert.assertEquals(routeList.getRoutes().size(), allRouteConfigs.size());

		
		// List all stops with close to 31 Kahler Ave Milton
		BigDecimal searchLat = new BigDecimal(42.260324);
		BigDecimal searchLong = new BigDecimal(-71.090954);
		
		// Get all the RouteConfigs
		final int R = 6371;
		final int CLOSEST_COUNT = 10;
		SortedMap<Double, Stop>  closest = new TreeMap<Double, Stop>();
		for (String route : allRouteConfigs.keySet()) {
			RouteConfig routeConfig = allRouteConfigs.get(route);
			List<Stop> stops = routeConfig.getBusRoute().getStops();
			for (Stop stop : stops) {
				// calculate the distance
				double x = (stop.getLon().doubleValue() - searchLong.doubleValue()) * Math.cos((stop.getLat().doubleValue() + searchLat.doubleValue())/2);
				double y = stop.getLat().doubleValue() - searchLat.doubleValue();
				double d = Math.sqrt(x * x + y * y) * R;
				closest.put(d, stop);
			}
		}
		
		// Print out the closest 20 stops
		Iterator<Double> k = closest.keySet().iterator();
		for (int i = 0; i < 20; i++) {
			if (k.hasNext()) {
				Double d = k.next();
				Stop stop = closest.get(d);
				System.out.println("Distance: " + d + " Stop: " + stop.getTitle() +
						" Lat:" + stop.getLat() + " Lon:" + stop.getLon());
			}
		}
	}
}
