package edu.bu.cs673b1s1p3.nextbus.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;

import edu.bu.cs673b1s1p3.nextbus.service.GeoCoderService;
import edu.bu.cs673b1s1p3.nextbus.service.InvalidAddressException;
import edu.bu.cs673b1s1p3.nextbus.service.NextBusService;
import edu.bu.cs673b1s1p3.nextbus.service.RouteStop;
import edu.bu.cs673b1s1p3.nextbus.service.ServiceUnavailableException;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.BusRoute;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Direction;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Path;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.RouteList;

public class MainModel {
	private final static int RADIUS = 6371;
	private final static int CLOSEST_COUNT = 20;
	
	private NextBusService nextBus;
	private GeoCoderService geoCoder;
	private Agency agency;
	private BusRoute busRoute;
	private Map<String, RouteConfig> allRouteConfigs;
	private Map<String, Direction> routeDirections;
	
	
	/**
	 * Main Model Constructor. 
	 * Accepts Agency Name String as argument to define 
	 * Nextbus Agency that will be used. 
	 * 
	 * @param agencyName - Name of Agency to use (String)
	 */
	public MainModel(String agencyName) throws ServiceUnavailableException
	{
		nextBus = NextBusService.getInstance();
		this.agency = nextBus.getAgency(agencyName);
		geoCoder = new GeoCoderService();
	}
	
	public Agency getAgency()
	{
		return this.agency;
	}
	
	// Returns List from NextBus Service Methods
	
	/**
	 * Uses the defined NextBus Agency to retrieve a list of all available Routes.
	 * 
	 * @return RouteList
	 */
	public RouteList getRouteList() throws ServiceUnavailableException
	{
		return nextBus.getRouteList(this.agency);
	}
	
	
	/**
	 * Uses nextBus service to search for and return a specific bus route 
	 * configuration from a Route.
	 * 
	 * @param route the {@link Route} to get the RouteConfig for
	 * @return the RouteConfig or null if the {@link Agency} or Route is 
	 * not found
	 */
	public RouteConfig getRouteConfig(Route route) throws ServiceUnavailableException
	{
		return nextBus.getRouteConfig(this.agency,route.getTag());
	}
	
	
	/**
	 * Uses nextBus service to search for and return a specific bus route 
	 * by Agency and Route tag.
	 * 
	 * @param routeTag the route tag of the {@link Route}
	 * @return the {@link Route} or null if the {@link Agency} is not found
	 */
	public Route getRoute(String routeTag) throws ServiceUnavailableException 
	{
		return nextBus.getRoute(this.agency, routeTag);
	}
	

	/**
	 * Return a List of GeoPositions for a Route. This is to
	 * be used with the JXMapKit API to display a route.
	 * 
	 * @param route the {@link Route} to display
	 * @return list of GeoPositions
	 */
	public ArrayList<GeoPosition> getStopsForDisplay(Route route)
			throws ServiceUnavailableException
	{
		return nextBus.getStopsForDisplay(route);
	}
	
	/**
	 * Return a List of GeoPositions for an Address. This is to
	 * be used with the JXMapKit API to display a route.
	 * 
	 * @param route the {@link Route} to display
	 * @return list of GeoPositions
	 * @throws InvalidAddressException 
	 */
	public ArrayList<RouteStopGeoPositionDTO> getStopsForDisplay(String address)
			throws ServiceUnavailableException, InvalidAddressException
	{
		ArrayList<RouteStopGeoPositionDTO> closestStops = new ArrayList<RouteStopGeoPositionDTO>();
		GeoPosition position;
		try {
			// Get the GeoPosition of the address
			position = geoCoder.convertAddressToGeoPosition(address);
			
			// Get a full list of all calculated distances
			SortedMap<Double, RouteStopGeoPositionDTO> routesAndStops = getClosestRoutesAndStops(position);
			
			// Now we want just the top CLOSEST_COUNT
			int count = 0;
			Iterator<RouteStopGeoPositionDTO> iter = routesAndStops.values().iterator();
			while(iter.hasNext() && count++ < CLOSEST_COUNT) {
				RouteStopGeoPositionDTO routeStop = iter.next();
				closestStops.add(routeStop);
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return closestStops;
	}
	
	public Waypoint getSearchWayPoint(String address) 
			throws ServiceUnavailableException, InvalidAddressException
	{
		GeoPosition position = null;
		try {
			// Get the GeoPoisition of the address
			position = geoCoder.convertAddressToGeoPosition(address);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Waypoint(position.getLatitude(), position.getLongitude());
	}
	
	public GeoPosition getSearchGeoPosition(String address) 
			throws ServiceUnavailableException, InvalidAddressException
	{
		GeoPosition position = null;
		try {
			// Get the GeoPoisition of the address
			position = geoCoder.convertAddressToGeoPosition(address);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return position;
	}
	
	/**
	 * Method for calculating the distances of all of the Bus stops
	 * in all of the RouteConfigs and stores them in a sorted map.
	 * The Map is sorted by the distance (which is the double key).
	 *
	 * @param position the GeoPosition of the origin
	 * @return the sorted map 
	 * 
	 * @author Ton
	 */
	private SortedMap<Double, RouteStopGeoPositionDTO> getClosestRoutesAndStops(GeoPosition position) {
		SortedMap<Double, RouteStopGeoPositionDTO> closest = new TreeMap<Double, RouteStopGeoPositionDTO>();
		
		// Set search values from the position
		double searchLong = position.getLongitude();
		double searchLat = position.getLatitude();

		// Iterate through all of the route configs by route
		for (String route : getAllRouteConfigs().keySet()) {
			RouteConfig routeConfig = allRouteConfigs.get(route);
			
			if (routeConfig.getBusRoute() == null) {
				continue;
			}
			List<Stop> stops = routeConfig.getBusRoute().getStops();
			for (Stop stop : stops) {
				// calculate the distance
				double x = (stop.getLon().doubleValue() - searchLong) * 
						Math.cos((stop.getLat().doubleValue() + searchLat)/2);
				double y = stop.getLat().doubleValue() - searchLat;
				double d = Math.sqrt(x * x + y * y) * RADIUS;
				RouteStopGeoPositionDTO routeAndStop = new RouteStopGeoPositionDTO(route, stop);
				closest.put(d, routeAndStop);
			}
		}
		return closest;
	}
	
	/**
	 * Accepts Route Tag value to retrieve {@link RouteConfig} object. 
	 * RouteConfig object then retrieves {$link BusRoute} object.
	 * List of {@link Direction} is then extracted from BusRoute.
	 * Populates Direction Map with Direction values
	 * 
	 * @param routeTag
	 */
	public void setDirectionsMap(String routeTag) throws ServiceUnavailableException
	{
		BusRoute busRoute = null;
		
		if(this.busRoute == null)
		{
			RouteConfig routeConfig = nextBus.getRouteConfig(agency, routeTag);
			busRoute = routeConfig.getBusRoute();
		}
		else
		{
			busRoute = this.busRoute;
		}
		
		ArrayList<Direction> busDirectionList = (ArrayList<Direction>) busRoute.getDirections();
		Map<String, Direction> routeDirections = new HashMap<String, Direction>(busDirectionList.size());
		
		for(Direction direction: busDirectionList)
		{
			routeDirections.put(direction.getTag(),direction);
		}
		
		setRouteDirectionsMap(routeDirections);
	}
	
	/**
	 * Sets the next bus prediction time for a list of stops selected
	 * 
	 * @param list
	 * @throws ServiceUnavailableException
	 * @author Jimson
	 */
	public void setNextBusTimeForStops(List<Stop> list) throws ServiceUnavailableException {
		ArrayList<Stop> stops = (ArrayList<Stop>) list;
		ArrayList<RouteStop> routeStopList = new ArrayList<RouteStop>();
		
		for (Stop s : stops) {
			routeStopList.add(new RouteStop(busRoute.getTag(), s));
		}
		
		String[] nextBusTimes = getNextBusTimes(routeStopList).toArray(new String[stops.size()]);
		
		//Add the nextbus time info to each Stop object
		int i=0;
		for(Stop s : stops) {
			s.setNextBusTime(nextBusTimes[i]);
			i++;
		}
	}
	
	/**
	 * Retrieves list of stops from busRoute defined in the MainModel as a 2D array.
	 * 
	 * @return 2D stopList String array
	 * @throws ServiceUnavailableException 
	 */
	public String[][] getRouteStopsArray(List<Stop> list) throws ServiceUnavailableException
	{
		//TODO - Add method to retreive nextbus times
		setNextBusTimeForStops(list);
		return StopsToArray.convert(list);
	}
	
	/**
	 *  Retrieves list of stops from busRoute defined in the MainModel
	 * 
	 * @return ArrayList<Stop> 
	 */
	public ArrayList<Stop> getAllStopsList()
	{
		return (ArrayList<Stop>) this.busRoute.getStops();
	}
	
	public synchronized void setAllRouteConfigs(Map<String, RouteConfig> allRouteConfigs)
	{
		this.allRouteConfigs = allRouteConfigs;
	}
	
	public synchronized Map<String, RouteConfig> getAllRouteConfigs()
	{
		return this.allRouteConfigs;
	}
	
	public void setRouteDirectionsMap(Map<String, Direction> routeDirections)
	{
		this.routeDirections = routeDirections;
	}
	
	public Map<String, Direction> getRouteDirectionsMap()
	{
		return this.routeDirections;
	}
	
	public Direction getDirection(String directionTag)
	{
		return routeDirections.get(directionTag);
	}
	
	public ArrayList<Path> getPathList(Route route) throws ServiceUnavailableException
	{
		 return nextBus.getPathList(this.agency, route);
	}
	
	
	// Return Vector<Item> for ComboBox
	
	/**
	 * Uses the defined NextBus Agency to retrieve a list of all available Routes.
	 * RouteList is then returned as a routeItem <Item> Vector.
	 * 
	 * @return Vector<Item> of Route Tags and Route Titles
	 */
	public Vector<Item> getRouteListItems() throws ServiceUnavailableException
	{
		ArrayList<Route> routeList = (ArrayList<Route>) nextBus.getRouteList(this.agency).getRoutes();
		Vector<Item> routeItems = new Vector<Item>();		
		
		routeItems.add(new Item("","-- Select a Bus --"));
		
		for(Route route: routeList)
		{
			routeItems.add(new Item(route.getTag(),route.getTitle()));
		}
		
		return routeItems;	
	}
	
	
	/**
	 * Accepts Route Tag value to retrieve {@link RouteConfig} object. 
	 * RouteConfig object then retrieves {$link BusRoute} object.
	 * List of {@link Direction} is then extracted from BusRoute.
	 * 
	 * @param routeTag
	 * @return Vector of Bus Route Directions
	 */
	public Vector<Item> getBusDirectionItems(String routeTag) throws ServiceUnavailableException
	{
		RouteConfig routeConfig = nextBus.getRouteConfig(agency, routeTag);
		BusRoute busRoute = routeConfig.getBusRoute();
		this.busRoute = busRoute;
		
		ArrayList<Direction> busDirectionList = (ArrayList<Direction>) busRoute.getDirections();
		Vector<Item> busDir = new Vector<Item>();
		
		busDir.add(new Item("","-- Select a Direction --"));
		
		for(Direction directions: busDirectionList)
		{
			busDir.add(new Item(directions.getTag(), directions.getTitle()));
		}

		return busDir;	
	}
	
	
	// Next Bus Methods
	
	public List<String> getNextBusTimes(ArrayList<RouteStop> routeStopList) throws ServiceUnavailableException{
		return nextBus.getNextBusTimes(routeStopList);
	}
	
	public ArrayList<Stop> addNextBusTimes(ArrayList<RouteStopGeoPositionDTO> closestStops) throws ServiceUnavailableException {
		ArrayList<Stop> stopsList = new ArrayList<Stop>();
		ArrayList<RouteStop> routeStopList = new ArrayList<RouteStop>();
		int i = 0;
		
		//Grab the route and stop info from RouteStopGeoPositionDTO and store it in routeStopList
		for (RouteStopGeoPositionDTO r : closestStops) {
			routeStopList.add(new RouteStop(r.getRouteTag(),r.getStop()));
		}
		
		//Create an array of all the next bus times from the routeStopList
		String[] nextBusTimes = getNextBusTimes(routeStopList).toArray(new String[closestStops.size()]);
		
		//Add the nextbus time info to each RouteStopGeoPositionDTO object
		for(RouteStopGeoPositionDTO r : closestStops) {
			r.setNextBusTime(nextBusTimes[i]);
			stopsList.add(r.getStop());
			i++;
		}
		return stopsList;
	}
	  
}
