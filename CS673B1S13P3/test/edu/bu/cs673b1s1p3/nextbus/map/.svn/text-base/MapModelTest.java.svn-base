package edu.bu.cs673b1s1p3.nextbus.map;

import junit.framework.Assert;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.junit.Test;

import edu.bu.cs673b1s1p3.nextbus.service.NextBusService;
import edu.bu.cs673b1s1p3.nextbus.service.ServiceUnavailableException;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;

public class MapModelTest {
	
	/**
	 * Tests the get map view method
	 */
	@Test
	public void testGetMapViewer() {
		JXMapKit mapViewer = new JXMapKit();
		MapModel mapModel = new MapModel(mapViewer);
		Assert.assertEquals(mapViewer, mapModel.getMapViewer());
	}
	
	/**
	 * Tests the get default map provider
	 */
	@Test
	public void testGetMapProvider() {
		JXMapKit mapViewer = new JXMapKit();
		MapModel mapModel = new MapModel(mapViewer);
		Assert.assertEquals(DefaultProviders.OpenStreetMaps, mapModel.getMapProvider());
	}
	
	/**
	 * Tests the waypoint non-existing in the route 225
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetWaypointIndexExist() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();
		Agency agency = nextBus.getAgency("mbta");
		Route route = nextBus.getRoute(agency, "225");
		RouteConfig routeConfig = nextBus.getRouteConfig(route);
		
		MapModel mapModel = new MapModel();
		
		// declare a waypoint that is not in the route 225
		Waypoint wp = new Waypoint(40.716667,-74);
		
		// no index found and -1 returned
		Assert.assertEquals(-1, mapModel.getWaypointIndex(routeConfig.getBusRoute().getStops(), wp));
	}
	
	/**
	 * Tests the waypoint existing in the route 225
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetWaypointIndexNonExist() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();
		Agency agency = nextBus.getAgency("mbta");
		Route route = nextBus.getRoute(agency, "225");
		RouteConfig routeConfig = nextBus.getRouteConfig(route);
		
		MapModel mapModel = new MapModel();
		
		// declare a waypoint that is in the route 225
		Waypoint wp = new Waypoint(42.2518099,-71.00541);
		
		// index of 0 found
		Assert.assertEquals(0, mapModel.getWaypointIndex(routeConfig.getBusRoute().getStops(), wp));
	}

}
