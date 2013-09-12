/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.jdesktop.swingx.mapviewer.GeoPosition;

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
 * This class represents a service layer that abstracts away the
 * NextBus web services and treats it basically as a database.
 * 
 * @author ton
 *
 */
public class NextBusService {
	
	public static final String AGENCY_LIST_URL = "http://webservices.nextbus.com/service/publicXMLFeed?command=agencyList";
	public static final String ROUTE_LIST_URL = "http://webservices.nextbus.com/service/publicXMLFeed?command=routeList";
	public static final String ROUTE_CONFIG_URL = "http://webservices.nextbus.com/service/publicXMLFeed?command=routeConfig";
	public static final String PREDICTIONS_LIST_URL = "http://webservices.nextbus.com/service/publicXMLFeed?command=predictionsForMultiStops";
	
	public static final String AGENCY_PARAM = "&a=";
	public static final String ROUTE_PARAM = "&r=";
	public static final String PREDICTION_PARAM = "&stops=";
	public static final String VERBOSE_PARAM = "";
	
	//Value to show if NextBus Time is not available
	public static final String EMPTY_NEXTBUS_TIME = "";
	
	private Map<String, Agency> agencyMap = new HashMap<String, Agency>();
	private Agency currentAgency;
	private Map<String, Route> routeMap = new HashMap<String, Route>();
	private Route currentRoute;
	private Map<String, Direction> dirMap = new HashMap<String, Direction>();

	private static NextBusService instance;
	
	/**
	 * NextBusService is a facade onto the NextBus API. 
	 * It contains a number of state variables (above) so
	 * it is better to have a singleton accessed through
	 * the getInstance method.
	 * 
	 * @return NextBusService and instance of the NextBusService
	 */
	public static synchronized NextBusService getInstance() {
		if (instance == null) {
			instance = new NextBusService();
		}
		return instance;
	}
	
	/**
	 * Private constructor so instances cannot be
	 * instantiated directly.
	 */
	private NextBusService() {
		// empty
	}

	/**
	 * Return the AgencyList data that represents 
	 * Agencies that provide public services.
	 * 
	 * @return the list of Agencies
	 */
	public AgencyList getAgencyList() 
			throws ServiceUnavailableException 
	{
		try {
			// Make a call to the NextBusService to return the AgencyList class
			AgencyList list = nextBusCall(AgencyList.class, AGENCY_LIST_URL);
			return list;
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}
	}
	
	/**
	 * Search for and return a specific Agency by AgencyTag
	 * 
	 * @return the {@link Agency} or null if the {@link Agency} is not found
	 */
	public Agency getAgency(final String agencyTag) 
			throws ServiceUnavailableException 
	{
		if (agencyMap.isEmpty()) {
			for(Agency agency : getAgencyList().getAgencies()) {
				agencyMap.put(agency.getTag(), agency);
			}
		}
		Agency agency = agencyMap.get(agencyTag);
		currentAgency = agency;
		return agency;
	}
	
	/**
	 * Return the RouteList of bus routes that an Agency 
	 * services.
	 * 
	 * @param agency the {@link Agency} to which the RouteList applies
	 * @return the list of Routes
	 */
	public RouteList getRouteList(final Agency agency) 
			throws ServiceUnavailableException 
	{
		if (agency == null) {
			return null;
		}
		// create the URL to include Agency parameter
		String urlString = ROUTE_LIST_URL + AGENCY_PARAM + agency.getTag();

		try {
			// Make a call to the NextBusService to return the RouteList class
			RouteList list = nextBusCall(RouteList.class, urlString);
			return list;
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}
	}
	
	/**
	 * Return the RouteList of bus routes that an Agency
	 * services, limited by the number you want to display.
	 * 
	 * @param agency the {@link Agency} to which the {@link RouteList} applies
	 * @param limit the number of {@link Route} objects to display
	 * @return the list of Routes
	 */
	public RouteList getRouteList(final Agency agency, final int limit) 
			throws ServiceUnavailableException 
	{
		// this is the full route list returned from the API
		RouteList routeList = getRouteList(agency);
		
		// this is the route list with the limit applied
		return new RouteList(routeList, limit);
	}
	
	/**
	 * Search for and return a specific bus route by Agency
	 * and Route tag.
	 * 
	 * @param agency the {@link Agency} to search with
	 * @param routeTag the route tag of the {@link Route}
	 * @return the {@link Route} or null if the {@link Agency} is not found
	 */
	public Route getRoute(final Agency agency, final String routeTag) 
			throws ServiceUnavailableException 
	{
		
		// Test for a null Agency
		if (agency == null) {
			return null;
		}
		
		// Since we create map that contains the routes, those routes need to be
		// associated with a currentAgency. Here we check to ensure that the
		// currentAgency is null, different or the routeMap is empty
		if (currentAgency == null || !currentAgency.equals(agency) || routeMap.isEmpty()) {
			for(Route route : getRouteList(agency).getRoutes()) {
				routeMap.put(route.getTag(), route);
			}
			// Set the currentAgency for future reference
			currentAgency = agency;
		}
		return routeMap.get(routeTag);
	}
	
	/**
	 * Return the RouteList of bus routes that an Agency 
	 * services.
	 * 
	 * @param agency the {@link Agency} to which the RouteList applies
	 * @return the list of Routes
	 * 
	 * @author Jimson
	 */
	public ArrayList<Direction> getDirectionList(final Agency agency, final Route route) 
		throws ServiceUnavailableException
	{
		if (agency == null) {
			return null;
		}
		
		if (route == null) {
			return null;
		}
		
		RouteConfig routeConfig = getRouteConfig(agency, route.getTag());
		BusRoute busRoute = routeConfig.getBusRoute();
		ArrayList<Direction> busDirectionList = (ArrayList<Direction>) busRoute.getDirections();
		return busDirectionList;

	}
	
	/**
	 * Search for and return a specific bus route by Agency
	 * and Route tag.
	 * 
	 * @param agency the {@link Agency} to search with
	 * @param routeTag the route tag of the {@link Route}
	 * @param route the route number to search with
	 * @return the {@link Route} or null if the {@link Agency} is not found
	 * 
	 * @author Jimson
	 */
	public Direction getDirection(final Agency agency, final Route route, final String directionTag) 
			throws ServiceUnavailableException
	{
		
		// Test for a null Agency
		if (agency == null) {
			return null;
		}
		
		// Since we create map that contains the routes, those routes need to be
		// associated with a currentAgency. Here we check to ensure that the
		// currentAgency is null, different or the routeMap is empty
		if (currentAgency == null || !currentAgency.equals(agency) || routeMap.isEmpty()
				|| currentRoute == null || !currentRoute.equals(route) || dirMap.isEmpty()) {
			for(Direction direction : getDirectionList(agency, route)) {
				dirMap.put(direction.getTag(), direction);
			}
			// Set the currentAgency for future reference
			currentAgency = agency;
			currentRoute = route;
		}
		return dirMap.get(directionTag);
	}
	
	/**
	 * Return the PathList of bus routes that an Agency 
	 * services.
	 * 
	 * @param agency the {@link Agency} to which the RouteList applies
	 * @param route the {@link Route} to which the PathList applies
	 * @return the list of Path
	 * 
	 * @author Jimson
	 */
	public ArrayList<Path> getPathList(final Agency agency, final Route route) 
		throws ServiceUnavailableException
	{
		if (agency == null) {
			return null;
		}
		
		if (route == null) {
			return null;
		}
		
		RouteConfig routeConfig = getRouteConfig(agency, route.getTag());
		BusRoute busRoute = routeConfig.getBusRoute();
		ArrayList<Path> pathList = (ArrayList<Path>) busRoute.getPaths();
		return pathList;

	}
	
	/**
	 * Search for and return a specific bus route configuration
	 * from an agency, and a Route tag.
	 * 
	 * @param agency the {@link Agency} to search with
	 * @param routeTag the route tag of the route
	 * @return the RouteConfig or null if the {@link Agency} or Route is 
	 * not found
	 */
	public RouteConfig getRouteConfig(final Agency agency, final String routeTag) 
			throws ServiceUnavailableException 
	{
		// Test for a null Agency
		if (agency == null) {
			return null;
		}
		
		// create the URL to include Agency and Route parameters
		// The verbose tag is added to the URL to return all of the
		// directions and their associated stops
		String urlString = ROUTE_CONFIG_URL + AGENCY_PARAM + agency.getTag()
				+ ROUTE_PARAM + routeTag 
				+ VERBOSE_PARAM;
		
		try {
			// Make a call to the NextBusService to return the RouteList class
			RouteConfig routeConfig = nextBusCall(RouteConfig.class, urlString);
			return routeConfig;
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}
	}
	
	/**
	 * Search for and return a specific bus route configuration
	 * from an agency, and a Route.
	 * 
	 * @param route the {@link Route} to get the {@link RouteConfig} for
	 * @return the RouteConfig or null if not found
	 */
	public RouteConfig getRouteConfig(final Route route) 
			throws ServiceUnavailableException 
	{
		// convenience method, chains to method accepting Agency
		// and the Route tag
		return getRouteConfig(currentAgency, route.getTag());
	}
	
	/**
	 * Return a List of GeoPositions for a Route. This is to
	 * be used with the JXMapKit API to display a route.
	 * 
	 * @param route the {@link Route} to get the positions for
	 * @return list of GeoPositions
	 */
	public ArrayList<GeoPosition> getStopsForDisplay(final Route route) 
			throws ServiceUnavailableException 
	{
		RouteConfig routeConfig = getRouteConfig(route);
		return getStopsForDisplay(routeConfig);
	}
	
	/**
	 * Return a List of GeoPositions for a Route, limited by the number
	 * of stops you wish to display. This is to
	 * be used with the JXMapKit API to display a route.
	 * 
	 * @param route the {@link Route} to display
	 * @param limit the number of routes you wish to display
	 * @return list of GeoPositions
	 */
	public ArrayList<GeoPosition> getStopsForDisplay(final Route route, final int limit) 
			throws ServiceUnavailableException 
	{
		RouteConfig routeConfig = getRouteConfig(route);
		return getStopsForDisplay(routeConfig, limit);
	}
	
	/**
	 * Return a List of GeoPositions for a RouteConfig. This is to
	 * be used with the JXMapKit API to display a route.
	 * 
	 * @param routeConfig @{link RouteConfig} to get the GeoPositions for
	 * @return list of GeoPosition coordinates
	 * 
	 * @author Jimson
	 */
	public ArrayList<GeoPosition> getStopsForDisplay(final RouteConfig routeConfig) {
		return JXMapKitHelper.convertStopsToGeoPositions(routeConfig.getBusRoute().getStops());
	}
	
	/**
	 * Return a List of GeoPositions for a RouteConfig limited by the number
	 * of stops. This is to be used with the JXMapKit API to display a route.
	 * 
	 * @param routeConfig @{link RouteConfig} to get the GeoPositions for
	 * @param limit the number of GeoPositions to be returned
	 * @return list of GeoPosition coordinates
	 */
	public ArrayList<GeoPosition> getStopsForDisplay(final RouteConfig routeConfig, final int limit) {
		ArrayList<GeoPosition> positions = getStopsForDisplay(routeConfig);
		List<GeoPosition> limitedPositions = positions.subList(0, limit);
		return new ArrayList<GeoPosition>(limitedPositions);
	}
	
	/**
	 * Return a List of GeoPositions for a Route. This is to
	 * be used with the JXMapKit API to display a route.
	 * 
	 * @param route the {@link Route} to get the positions for
	 * @return list of GeoPositions
	 */
	public ArrayList<GeoPosition> getPointsForDisplay(final RouteConfig routeConfig, int pathIndex) {
		return JXMapKitHelper.convertPointsToGeoPositions(routeConfig.getBusRoute().getPaths().get(pathIndex).getPoints());
	}
	
	/**
	 * Returns a {@link PredictionsList} object that contains information about the different NextBus time predictions.
	 * 
	 * @param agency {@link Agency}
	 * @param routeTag {@link RouteTag}
	 * @param list of {@link Stop} items 
	 * @return list of GeoPositions
	 */
	public PredictionsList getPredictionsList(Agency agency, String routeTag, ArrayList<Stop> stops) throws ServiceUnavailableException  {
		if(agency == null || routeTag == null || stops == null) return null;
		
		StringBuilder sb = new StringBuilder(PREDICTIONS_LIST_URL);
		sb.append(AGENCY_PARAM).append(agency.getTag());
		
		for(Stop stop : stops){
			sb.append(PREDICTION_PARAM).append(routeTag).append("|").append(stop.getTag());
		}
		
		try {
			return nextBusCall(PredictionsList.class, sb.toString());
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}

	}
	
	/**
	 * Returns a {@link PredictionsList} object that contains information about the different NextBus time predictions.
	 * 
	 * @param agency {@link Agency}
	 * @param routeTag {@link RouteTag}
	 * @param list of {@link Stop} items 
	 * @return list of GeoPositions
	 */
	public PredictionsList getPredictionsList(Agency agency, ArrayList<RouteStop> routeStopList) throws ServiceUnavailableException  {
		if(agency == null || routeStopList == null) return null;
		
		StringBuilder sb = new StringBuilder(PREDICTIONS_LIST_URL);
		sb.append(AGENCY_PARAM).append(agency.getTag());
		
		for(RouteStop routeStop : routeStopList){
			sb.append(PREDICTION_PARAM).append(routeStop.getRouteTag()).append("|").append(routeStop.getStop().getTag());
		}
		
		try {
			return nextBusCall(PredictionsList.class, sb.toString());
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}

	}
	
	/**
	 * Returns a list of NextBus Times for a list of specified {@link Stop} objects.
	 * 
	 * @param agency {@link Agency}
	 * @param routeTag {@link RouteTag}
	 * @param list of {@link Stop} items 
	 * @return List<String> of NextBus Time Predictions in Minutes
	 */
	public List<String> getNextBusTimes(Agency agency, String routeTag, ArrayList<Stop> stops) throws ServiceUnavailableException  {
		if(agency == null || routeTag == null || stops == null) return null;
		
		try {
			PredictionsList predictionsList = getPredictionsList(agency, routeTag, stops);	
			return predictionsToTime(predictionsList);
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}
	}
	
	/**
	 * Returns a list of NextBus Times for a list of specified {@link Stop} objects.
	 * 
	 * @param agency {@link Agency}
	 * @param routeStopList {@link ArrayList<RouteStop>}
	 * @return List<String> of NextBus Time Predictions in Minutes
	 */
	public List<String> getNextBusTimes(Agency agency, ArrayList<RouteStop> routeStopList) throws ServiceUnavailableException  {
		if(agency == null || routeStopList == null || routeStopList == null) return null;
		
		try {
			PredictionsList predictionsList = getPredictionsList(agency, routeStopList);
			return predictionsToTime(predictionsList);
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}
	}
	
	/**
	 * Returns a list of NextBus Times for a list of specified {@link Stop} objects.
	 * 
	 * @param routeTag
	 * @param stops {@link ArrayList<Stop>}
	 * @return List<String> of NextBus Time Predictions in Minutes
	 */
	public List<String> getNextBusTimes(String routeTag, ArrayList<Stop> stops) throws ServiceUnavailableException  {
		return getNextBusTimes(this.currentAgency, routeTag, stops);
	}
	
	/**
	 * Returns a list of NextBus Times for a list of specified {@link RouteStop} objects.
	 * 
	 * @param routeStopList {@link ArrayList<RouteStop>}
	 * @return List<String> of NextBus Time Predictions in Minutes
	 */
	public List<String> getNextBusTimes(ArrayList<RouteStop> routeStopList) throws ServiceUnavailableException  {
		return getNextBusTimes(this.currentAgency, routeStopList);
	}
	
	/**
	 * Returns a list of NextBus Times for a {@link PredictionsList} 
	 * 
	 * @param predictionsList {@link PredictionsList}
	 * @return List<String> of NextBus Time Predictions in Minutes
	 */
	private List<String> predictionsToTime(PredictionsList predictionsList) throws ServiceUnavailableException
	{
		try{
			List<String> nextBusTimes = new ArrayList<String>();
			ArrayList<Predictions> predictions  = (ArrayList<Predictions>) predictionsList.getPredictions();
		
			for(Predictions p : predictions){
				if(p.getDirection() != null){
					nextBusTimes.add(p.getDirection().getTopPrediction() + " min");
				}
				else{
					nextBusTimes.add(EMPTY_NEXTBUS_TIME);
				}
			}
			return nextBusTimes;
		} 
		catch (Exception e) {
			throw new ServiceUnavailableException(e);
		}
	}
	
	/**
	 * Call the NextBus Service and return the values as objects.
	 * 
	 * @param clazz the class of the return type
	 * @param urlString the URL to pass to NextBus
	 * @return the class
	 * @throws JAXBException thrown when there is a problem marshalling data
	 * @throws MalformedURLException thrown when an invalid URL has been passed
	 */
	public <T> T nextBusCall(final Class<T> clazz, final String urlString) throws JAXBException, MalformedURLException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		T values = clazz.cast(jaxbUnmarshaller.unmarshal(new URL(urlString)));
		return values;
	}
	
}
