package edu.bu.cs673b1s1p3.nextbus.main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.junit.Test;

import edu.bu.cs673b1s1p3.nextbus.service.InvalidAddressException;
import edu.bu.cs673b1s1p3.nextbus.service.NextBusService;
import edu.bu.cs673b1s1p3.nextbus.service.ServiceUnavailableException;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.RouteList;
import junit.framework.Assert;


public class MainModelTest {
	
	@Test
	public void testGetRouteList() throws ServiceUnavailableException
	{
		// Create MainModel Instance 
		MainModel mainModel = new MainModel("mbta");
		Assert.assertNotNull(mainModel);
		
		// Get Agency supplied in constructor
		Agency agency = mainModel.getAgency();
		Assert.assertNotNull(agency);
		
		RouteList routeList = mainModel.getRouteList();
		Assert.assertNotNull(routeList);
		Assert.assertFalse(routeList.getRoutes().isEmpty());
	}
	
	@Test
	public void testGetRoute() throws ServiceUnavailableException {
		// Create MainModel Instance 
		MainModel mainModel = new MainModel("mbta");
		Assert.assertNotNull(mainModel);
		
		// Get Agency supplied in constructor
		Agency agency = mainModel.getAgency();
		Assert.assertNotNull(agency);
		
		// Get Known Sample Route
		Route route = mainModel.getRoute("225");
		Assert.assertNotNull(route);
	}
	
	@Test
	public void testGetRouteConfig() throws ServiceUnavailableException {
		// Create MainModel Instance 
		MainModel mainModel = new MainModel("mbta");
		Assert.assertNotNull(mainModel);
		
		// Get Agency supplied in constructor
		Agency agency = mainModel.getAgency();
		Assert.assertNotNull(agency);
		
		// Get Known Sample Route
		Route route = mainModel.getRoute("225");
		Assert.assertNotNull(route);
		
		// go after a known route - test for not null
		RouteConfig routeConfig = mainModel.getRouteConfig(route);
		Assert.assertNotNull(routeConfig);
	}
	
	@Test
	public void testGetAllRouteConfig() throws ServiceUnavailableException {
		// Create MainModel Instance 
		MainModel mainModel = new MainModel("mbta");
		Assert.assertNotNull(mainModel);
		
		// Get Agency supplied in constructor
		Agency agency = mainModel.getAgency();
		Assert.assertNotNull(agency);
		
		// Get Known Sample Route
		Route route = mainModel.getRoute("225");
		Assert.assertNotNull(route);
		
		// go after a known route - test for not null
		RouteConfig routeConfig = mainModel.getRouteConfig(route);
		Assert.assertNotNull(routeConfig);
	}
	
	@Test
	public void testGetStopsForDisplay() throws ServiceUnavailableException {
		// Create MainModel Instance 
		MainModel mainModel = new MainModel("mbta");
		Assert.assertNotNull(mainModel);
		
		// Get Agency supplied in constructor
		Agency agency = mainModel.getAgency();
		Assert.assertNotNull(agency);
		
		// Get Known Sample Route
		Route route = mainModel.getRoute("225");
		Assert.assertNotNull(route);
		
		// Retrieve Stops and Check for Known Lat and Lon
		ArrayList<GeoPosition> stopsForDisplay = mainModel.getStopsForDisplay(route);
		Assert.assertNotNull(stopsForDisplay);
		Assert.assertEquals(42.2518099, stopsForDisplay.get(0).getLatitude());
		Assert.assertEquals(-71.00541, stopsForDisplay.get(0).getLongitude());
		Assert.assertEquals(42.2487399, stopsForDisplay.get(1).getLatitude());
		Assert.assertEquals(-71.00211, stopsForDisplay.get(1).getLongitude());
	}
	
	@Test
	public void testGetStopsForDisplay2() throws ServiceUnavailableException, InvalidAddressException {
		//Test Address
		String address = "31 Kahler Ave Milton MA";
		
		// Create MainModel Instance 
		MainModel mainModel = new MainModel("mbta");
		Assert.assertNotNull(mainModel);
		
		// Get Agency supplied in constructor
		Agency agency = mainModel.getAgency();
		Assert.assertNotNull(agency);
		
		RouteList routeList = mainModel.getRouteList();
		Assert.assertNotNull(routeList);
		Assert.assertFalse(routeList.getRoutes().isEmpty());
		
		Map<String, RouteConfig> allRouteConfigs = new HashMap<String, RouteConfig>(routeList.getRoutes().size());
		
		for (Route route : routeList.getRoutes())
		{
			RouteConfig routeConfig = mainModel.getRouteConfig(route);
			allRouteConfigs.put(route.getTag(), routeConfig);
		}
		
		mainModel.setAllRouteConfigs(allRouteConfigs);
		
		// go after a known route - test for not null
		ArrayList<RouteStopGeoPositionDTO> stopsForDisplay = mainModel.getStopsForDisplay(address);
		Assert.assertNotNull(stopsForDisplay);
		Assert.assertFalse(stopsForDisplay.isEmpty());
		Assert.assertEquals(42.2607599, stopsForDisplay.get(0).getGeoPosition().getLatitude());
		Assert.assertEquals(-71.0865399, stopsForDisplay.get(0).getGeoPosition().getLongitude());
	}
	
	@Test
	public void testProcessAllRouteConfigs()
	{
		String agency = "mbta";
		
		try
		{
			MainModel mainModel = new MainModel(agency);
			RouteList routeList = mainModel.getRouteList();
			Map<String, RouteConfig> allRouteConfigs = new HashMap<String, RouteConfig>(routeList.getRoutes().size());
			
			for (Route route : routeList.getRoutes()) 
			{
				RouteConfig routeConfig = mainModel.getRouteConfig(route);
				allRouteConfigs.put(route.getTag(), routeConfig);
				Thread.sleep(100);
			}
			
			mainModel.setAllRouteConfigs(allRouteConfigs);
			assertNotNull(allRouteConfigs.get("1"));
		}
		catch (InterruptedException | ServiceUnavailableException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
