/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service;

import java.util.ArrayList;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.junit.Test;

import junit.framework.Assert;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.AgencyList;
import edu.bu.cs673b1s1p3.nextbus.service.predictions.Predictions;
import edu.bu.cs673b1s1p3.nextbus.service.predictions.PredictionsList;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.BusRoute;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Direction;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Path;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.RouteList;

/**
 * JUnit test class for the NextBusService class.
 * This class tests just the individual functionality
 * of the NextBusService.
 * 
 * @author ton
 *
 */
public class NextBusServiceTest {

	/**
	 * Test that the NextBusService.getAgencyList() returns 
	 * valid values.
	 */
	@Test
	public void testAgencyList()  throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();
		
		AgencyList agencyList = nextBus.getAgencyList();
		Assert.assertNotNull(agencyList.getAgencies());
		Assert.assertFalse(agencyList.getAgencies().isEmpty());
	}
	
	/**
	 * Test the getAgency(agencyTag) functionality of the 
	 * NextBusService.
	 */
	@Test
	public void testGetAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();
		
		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a fictional Agency - test for null
		agency = nextBus.getAgency("terry");
		Assert.assertNull(agency);
	}
	
	/**
	 * Test that the NextBusService.getRouteList() returns 
	 * valid values.
	 */
	@Test
	public void testRouteList() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// Get a known Agency by tag
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		RouteList routeList = nextBus.getRouteList(agency); 
		Assert.assertNotNull(routeList);
		Assert.assertFalse(routeList.getRoutes().isEmpty());
	}
	
	/**
	 * Test RouteList by passing in a null agency, expect null back.
	 */
	@Test
	public void testRouteListNullAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		RouteList routeList = nextBus.getRouteList(null);
		Assert.assertNull(routeList);
	}
	
	/**
	 * Test that the NextBusService.getRouteList() 
	 * with limits applied, returns valid values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRouteListWithLimits() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// Get a known Agency by tag
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// get limited list of routes
		RouteList limitedRouteList = nextBus.getRouteList(agency, 20);
		Assert.assertNotNull(limitedRouteList);
		Assert.assertFalse(limitedRouteList.getRoutes().isEmpty());
		
		// ensure that limits are met
		int limitedListSize = limitedRouteList.getRoutes().size();
		Assert.assertEquals(20, limitedListSize);
		
		// Now try a negative limit - should throw Exception
		limitedRouteList = nextBus.getRouteList(agency, -20);
	}
	
	/**
	 * Test the getRoute(Agency, routeTag) functionality of the 
	 * NextBusService.
	 */
	@Test
	public void testGetRoute() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
	}
	
	/**
	 * Test Route by passing in a null agency, expect null back.
	 */
	@Test
	public void testRouteNullAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		Route route = nextBus.getRoute(null, "routeTag");
		Assert.assertNull(route);
	}
	
	/**
	 * Test the getRouteConfig(Agency, routeTag) functionality of the 
	 * NextBusService.
	 */
	@Test
	public void testGetRouteConfig() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		RouteConfig route = nextBus.getRouteConfig(agency, "225");
		Assert.assertNotNull(route);
	}
	
	/**
	 * Test the getRouteConfig(Agency, routeTag) functionality of the 
	 * NextBusService. Test with NullAgency
	 */
	@Test
	public void testGetRouteConfigNullAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		RouteConfig route = nextBus.getRouteConfig(null, "225");
		Assert.assertNull(route);
	}
	
	/**
	 * Test the ability to generate a list of GeoPositions from a 
	 * Route.
	 */
	@Test
	public void testGetStopsForDisplayByRoute() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "225");
		Assert.assertNotNull(routeConfig);
		
		// Generate the List of GeoPositions
		ArrayList<GeoPosition> positions = nextBus.getStopsForDisplay(routeConfig);
		Assert.assertNotNull(positions);
		Assert.assertTrue(!positions.isEmpty());
	}
	
	/**
	 * Test the ability to generate a list of GeoPositions from a 
	 * Route with limits applied.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetStopsForDisplayByRouteWithLimits() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "225");
		Assert.assertNotNull(routeConfig);
		
		// Generate the List of GeoPositions
		ArrayList<GeoPosition> positions = nextBus.getStopsForDisplay(routeConfig, 20);
		Assert.assertNotNull(positions);
		Assert.assertTrue(!positions.isEmpty());
		
		// check the actual number
		Assert.assertEquals(20,  positions.size());
		
		// Should throw exception
		positions = nextBus.getStopsForDisplay(routeConfig, -20);
	}
	
	/**
	 * Test the ability to generate a list of GeoPositions from a 
	 * RouteConfig.
	 */
	@Test
	public void testGetStopsForDisplayByRouteConfig() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of GeoPositions
		ArrayList<GeoPosition> positions = nextBus.getStopsForDisplay(route);
		Assert.assertNotNull(positions);
		Assert.assertTrue(!positions.isEmpty());
	}
	
	/**
	 * Test the ability to generate a list of GeoPositions from a 
	 * RouteConfig, limited by size
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetStopsForDisplayByRouteConfigWithLimits() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of GeoPositions
		ArrayList<GeoPosition> positions = nextBus.getStopsForDisplay(route, 20);
		Assert.assertNotNull(positions);
		Assert.assertTrue(!positions.isEmpty());
		
		// check the actual number
		Assert.assertEquals(20,  positions.size());
		
		// Should throw exception
		positions = nextBus.getStopsForDisplay(route, -20);

	}
	
	/**
	 * Test get direction list.
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetDirectionsList() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of Direction
		ArrayList<Direction> directions = nextBus.getDirectionList(agency, route);
		Assert.assertNotNull(directions);
		Assert.assertTrue(!directions.isEmpty());
	}
	
	/**
	 * Test getDirections with null agency
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetDirectionsListNullAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of Direction with null agency
		ArrayList<Direction> directions = nextBus.getDirectionList(null, route);
		Assert.assertNull(directions);
	}
	
	@Test
	public void testGetDirectionsListNullRoute() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of Direction with null agency
		ArrayList<Direction> directions = nextBus.getDirectionList(agency, null);
		Assert.assertNull(directions);
	}
	
	/**
	 * Test get path list.
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPathList() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of Direction
		ArrayList<Path> directions = nextBus.getPathList(agency, route);
		Assert.assertNotNull(directions);
		Assert.assertTrue(!directions.isEmpty());
	}
	
	/**
	 * Test getPathList with null agency
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPathListNullAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of Direction with null agency
		ArrayList<Path> directions = nextBus.getPathList(null, route);
		Assert.assertNull(directions);
	}
	
	/**
	 * Get PathList with null route s/b null.
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPathListNullRoute() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known route - test for not null
		Route route = nextBus.getRoute(agency, "225");
		Assert.assertNotNull(route);
		
		// Generate the List of Direction with null agency
		ArrayList<Path> directions = nextBus.getPathList(agency, null);
		Assert.assertNull(directions);
	}
	
	
	/**
	 * Test get Predictions list using the url below:
	 * 
	 * http://webservices.nextbus.com/service/publicXMLFeed?command=predictionsForMultiStops&a=mbta&stops=57|11780&stops=57|11781&stops=57|11520&stops=57|1807&stops=57|1808&stops=57|1809&stops=57|1810&stops=57|955&stops=57|956&stops=57|957&stops=57|958&stops=57|959&stops=57|960&stops=57|961&stops=57|962&stops=57|963&stops=57|964&stops=57|965&stops=57|966&stops=57|967&stops=57|968&stops=57|969&stops=57|970&stops=57|971&stops=57|972&stops=57|9721&stops=57|973&stops=57|974&stops=57|975&stops=57|976&stops=57|977&stops=57|9780&stops=57|979&stops=57|980&stops=57|981&stops=57|982&stops=57|984&stops=57|983&stops=57|985&stops=57|986&stops=57|987&stops=57|988&stops=57|989&stops=57|990&stops=57|900_ar&stops=57|899&stops=57|951&stops=57|952&stops=57|953&stops=57|954&stops=57|979_ar&stops=57|1994&stops=57|1026&stops=57|919&stops=57|920&stops=57|19201&stops=57|921&stops=57|922&stops=57|923&stops=57|924&stops=57|925&stops=57|926&stops=57|927&stops=57|928&stops=57|929&stops=57|930&stops=57|931&stops=57|932&stops=57|933&stops=57|934&stops=57|935&stops=57|936&stops=57|937&stops=57|938&stops=57|939&stops=57|941&stops=57|899_ar&stops=57|900&stops=57|901&stops=57|902&stops=57|1900&stops=57|903&stops=57|9031&stops=57|904&stops=57|905&stops=57|906&stops=57|907&stops=57|908&stops=57|909&stops=57|910&stops=57|911&stops=57|912&stops=57|913&stops=57|914&stops=57|915&stops=57|916&stops=57|917&stops=57|918&stops=57|926_ar
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPredictionsList() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known RouteConfig - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "57");
		Assert.assertNotNull(routeConfig);
		
		// get bus route from Route Config - test for not null
		BusRoute busRoute = routeConfig.getBusRoute();
		Assert.assertNotNull(busRoute);
		
		// get list of bus stops from bus Route
		ArrayList<Stop> stops = new ArrayList<Stop>();
		stops = (ArrayList<Stop>) busRoute.getStops();
		Assert.assertNotNull(stops);
		
		// Generate the List of Predictions
		PredictionsList predictionsList = nextBus.getPredictionsList(agency, "57", stops);
		Assert.assertNotNull(predictionsList);
		//System.out.println(predictionsList.toString());
	}
	
	/**
	 * Get Predictions list with null agency
	 *  
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPredictionsListNullAgency() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known RouteConfig - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "57");
		Assert.assertNotNull(routeConfig);
		
		// get bus route from Route Config - test for not null
		BusRoute busRoute = routeConfig.getBusRoute();
		Assert.assertNotNull(busRoute);
		
		// get list of bus stops from bus Route
		ArrayList<Stop> stops = new ArrayList<Stop>();
		stops = (ArrayList<Stop>) busRoute.getStops();
		Assert.assertNotNull(stops);
		
		// Generate the List of Predictions
		PredictionsList predictionsList = nextBus.getPredictionsList(null, "57", stops);
		Assert.assertNull(predictionsList);

	}
	
	/**
	 * Get Predictions list with null routeTag
	 *  
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPredictionsListNullRoute() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known RouteConfig - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "57");
		Assert.assertNotNull(routeConfig);
		
		// get bus route from Route Config - test for not null
		BusRoute busRoute = routeConfig.getBusRoute();
		Assert.assertNotNull(busRoute);
		
		// get list of bus stops from bus Route
		ArrayList<Stop> stops = new ArrayList<Stop>();
		stops = (ArrayList<Stop>) busRoute.getStops();
		Assert.assertNotNull(stops);
		
		// Generate the List of Predictions
		PredictionsList predictionsList = nextBus.getPredictionsList(agency, null, stops);
		Assert.assertNull(predictionsList);
	}
	
	/**
	 * Get Predictions list with null ArrayList<stops>
	 *  
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetPredictionsListNullStops() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known RouteConfig - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "57");
		Assert.assertNotNull(routeConfig);
		
		// get bus route from Route Config - test for not null
		BusRoute busRoute = routeConfig.getBusRoute();
		Assert.assertNotNull(busRoute);
		
		// get list of bus stops from bus Route
		ArrayList<Stop> stops = new ArrayList<Stop>();
		stops = (ArrayList<Stop>) busRoute.getStops();
		Assert.assertNotNull(stops);
		
		// Generate the List of Predictions
		PredictionsList predictionsList = nextBus.getPredictionsList(agency, "57", null);
		Assert.assertNull(predictionsList);

	}
	
	/**
	 * Test get Predictions list using the url below:
	 * 
	 * http://webservices.nextbus.com/service/publicXMLFeed?command=predictionsForMultiStops&a=mbta&stops=57|11780&stops=57|11781&stops=57|11520&stops=57|1807&stops=57|1808&stops=57|1809&stops=57|1810&stops=57|955&stops=57|956&stops=57|957&stops=57|958&stops=57|959&stops=57|960&stops=57|961&stops=57|962&stops=57|963&stops=57|964&stops=57|965&stops=57|966&stops=57|967&stops=57|968&stops=57|969&stops=57|970&stops=57|971&stops=57|972&stops=57|9721&stops=57|973&stops=57|974&stops=57|975&stops=57|976&stops=57|977&stops=57|9780&stops=57|979&stops=57|980&stops=57|981&stops=57|982&stops=57|984&stops=57|983&stops=57|985&stops=57|986&stops=57|987&stops=57|988&stops=57|989&stops=57|990&stops=57|900_ar&stops=57|899&stops=57|951&stops=57|952&stops=57|953&stops=57|954&stops=57|979_ar&stops=57|1994&stops=57|1026&stops=57|919&stops=57|920&stops=57|19201&stops=57|921&stops=57|922&stops=57|923&stops=57|924&stops=57|925&stops=57|926&stops=57|927&stops=57|928&stops=57|929&stops=57|930&stops=57|931&stops=57|932&stops=57|933&stops=57|934&stops=57|935&stops=57|936&stops=57|937&stops=57|938&stops=57|939&stops=57|941&stops=57|899_ar&stops=57|900&stops=57|901&stops=57|902&stops=57|1900&stops=57|903&stops=57|9031&stops=57|904&stops=57|905&stops=57|906&stops=57|907&stops=57|908&stops=57|909&stops=57|910&stops=57|911&stops=57|912&stops=57|913&stops=57|914&stops=57|915&stops=57|916&stops=57|917&stops=57|918&stops=57|926_ar
	 * 
	 * @throws ServiceUnavailableException
	 */
	@Test
	public void testGetNextBusTimes() throws ServiceUnavailableException {
		NextBusService nextBus = NextBusService.getInstance();
		ArrayList<String> nextBusTimes = new ArrayList<String>();

		// go after a known agency - test for not null
		Agency agency = nextBus.getAgency("mbta");
		Assert.assertNotNull(agency);
		
		// go after a known RouteConfig - test for not null
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, "57");
		Assert.assertNotNull(routeConfig);
		
		// get bus route from Route Config - test for not null
		BusRoute busRoute = routeConfig.getBusRoute();
		Assert.assertNotNull(busRoute);
		
		// get list of bus stops from bus Route
		ArrayList<Stop> stops = new ArrayList<Stop>();
		stops = (ArrayList<Stop>) busRoute.getStops();
		Assert.assertNotNull(stops);
		
		// Generate the List of Predictions
		nextBusTimes = (ArrayList<String>) nextBus.getNextBusTimes(agency, "57", stops);
		Assert.assertNotNull(nextBusTimes);
		
		//Print out Results
		
		int i = 1;
		StringBuffer sb = new StringBuffer("Next Bus Times [ \n");
		for(String nextBusTime : nextBusTimes){
			sb.append("Stop ").append(i).append(" : ").append(nextBusTime).append("\n");
			i++;
		}
		sb.append("]");
		System.out.println(sb.toString());
		
	}
	
}
