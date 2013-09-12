package edu.bu.cs673b1s1p3.nextbus.main;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import org.jdesktop.swingx.mapviewer.Waypoint;

import edu.bu.cs673b1s1p3.nextbus.map.MapControlInterface;
import edu.bu.cs673b1s1p3.nextbus.service.FindStopsHelper;
import edu.bu.cs673b1s1p3.nextbus.service.InvalidAddressException;
import edu.bu.cs673b1s1p3.nextbus.service.JXMapKitHelper;
import edu.bu.cs673b1s1p3.nextbus.service.ServiceUnavailableException;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Direction;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Path;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.RouteList;

public class MainController {
	
	private MainModel mainModel;
	private MainView mainView;
	private MapControlInterface mapControl;
	private ItemEvent lastItemEvent = null;
	private String lastAddress = null;
	private static final String[] BUS_ROUTE_COLUMNS = {"Stop","Stop Name", "Next Bus","",""};
	private static final String[] SEARCH_ADDRESS_COLUMNS = {"Stop","Bus", "Stop Name", "Next Bus","",""};
	
	/**
	 * constructor
	 */
	public MainController(MainView mainView, MainModel mainModel) {
		this.mainModel = mainModel;
		this.mainView = mainView;
	}
	
	
	//---- ACTION METHODS
	
	/**
	 * Performs actions after specific Route is selected. 
	 * 
	 * @param ae - Route Object Tag value (String)
	 */
	public void selectRouteAction(ItemEvent ae)
	{
		Item item = (Item)((JComboBox)ae.getSource()).getSelectedItem();
		String routeTag = item.getId();
		Vector<Item> busDirectionItems = null;
		mainView.enableRefresh(false);

		try
		{
			busDirectionItems = mainModel.getBusDirectionItems(routeTag);

			if(busDirectionItems.size() > 0)
			{
				//Clear Out Bus Stops Table
				mainView.clearStops();
				
				//Populate Directions Combobox
				updateMapDirections(busDirectionItems);
				
				//Populate Directions Hash Map
				mainModel.setDirectionsMap(routeTag);
				
				//Draw Route on map
				drawMapRoute(routeTag);
			}
			else
			{
				mainView.enableRouteDirections(false);	
				
			}
		}
		catch(ServiceUnavailableException e)
		{
			mainView.notifyAndExit(MainView.NEXTBUS_SERVICE_UNAVAILABLE);
		}
		catch(NullPointerException npe)
		{
			resetAll();
			//npe.printStackTrace();
		}
	}
	
	/**
	 * Performs actions after specific Direction is selected. 
	 * 
	 * @param ae - Direction Object Tag value (String)
	 * @throws ServiceUnavailableException 
	 */
	public void selectDirectionAction(ItemEvent ae) throws ServiceUnavailableException
	{
		Item item = (Item)((JComboBox)ae.getSource()).getSelectedItem();
		String directionTag = item.getId();
		Direction direction = null;
		mainView.enableRefresh(false);
		this.lastItemEvent = ae;
		
		
		try
		{
			direction = mainModel.getDirection(directionTag);
			
			//Show Stops for Direction on Table
			updateDirectionStopsTable(direction);
			
			//Draw Stops on map
			drawMapStops(direction);
		}
		catch(NullPointerException npe)
		{
			mainView.clearStops();
		}	
	}
	
	/**
	 * Action performed when Search By Address Radio Button is selected
	 */
	public void rdBusRouteAction() {
		resetAll();
		mainView.enableAddressSearch(false);
		mainView.enableRouteDirections(false);
		mainView.enableRoute(true);
		mainView.enableRefresh(false);
		mainView.updateStatus(MainView.DEFAULT_STATUS);
		
		String[] tableColumns = BUS_ROUTE_COLUMNS;
		mainView.setTableColumns(tableColumns);
	}
	
	/**
	 * Action performed when Search By Address Radio Button is selected
	 */
	public void rdSearchAddressAction() {
		resetAll();
		
		mainView.enableRoute(false);
		mainView.enableRouteDirections(false);
		mainView.enableAddressSearch(true);
		mainView.enableRefresh(false);
		mainView.updateStatus(MainView.DEFAULT_STATUS);
		
		
		String[] tableColumns = SEARCH_ADDRESS_COLUMNS;
		mainView.setTableColumns(tableColumns);
	}
	
	
	/**
	 * Method called when the user inputs an address on the user
	 * interface and they want to see the closes bus stops.
	 * 
	 * @param address the user entered address
	 */
	public void searchPressedAction(String address) {
		mainView.enableRefresh(false);
		this.lastAddress = address;
		
		try {
			if (address != null && address.length() > 0) {
				
				//Get closest stops to address
				ArrayList<RouteStopGeoPositionDTO> closestStops = mainModel.getStopsForDisplay(address);
				
				//Get stops with next bus times added
				ArrayList<Stop> stopsList = mainModel.addNextBusTimes(closestStops);

				//Clear out Route and Stop Information 
				resetAll();
				
				//Update Stops Table with Closest Stops
				String[][] closestStopsArray = StopsToArray.convert(closestStops);
				mainView.enableRefresh(stopsExist(closestStopsArray.length));
				mainView.updateStopsTable(closestStopsArray);
				
				//Draw Closest Stops on Map
				drawClosestMapStops(stopsList, address);
				
				//Update Status
				mainView.updateStatus(MainView.DEFAULT_STATUS);
			} 
		}
		catch (ServiceUnavailableException e) {
			mainView.notifyAndExit(MainView.NEXTBUS_SERVICE_UNAVAILABLE);
		}
		catch (InvalidAddressException e) {
			mainView.updateStatus(MainView.INVALID_ADDRESS);
		}	
	}
	
	/**
	 * Action performed when refresh button in MainView is pressed
	 * @throws ServiceUnavailableException 
	 */
	public void refreshPressedAction() throws ServiceUnavailableException
	{
		if(mainView.getRadioByRoute().isSelected()){
			selectDirectionAction(this.lastItemEvent);
		}
		else if(mainView.getRadioByAddress().isSelected()){
			searchPressedAction(this.lastAddress);
		}
		
	}
	
	/**
	 * Action performed when row is selected
	 */
	public void rowSelectedAction(JTable table) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try{
			int row = table.getSelectedRow();
			
			int latCol = table.getModel().getColumnCount() - 2;
			int lonCol = table.getModel().getColumnCount() - 1;
			
			BigDecimal lat = new BigDecimal((String)table.getModel().getValueAt(row, latCol));
			BigDecimal lon = new BigDecimal((String)table.getModel().getValueAt(row, lonCol));
			
			mapControl.setCenterPosition(lat,lon);
			mapControl.setZoom(1);
		}
		catch(IndexOutOfBoundsException e){
			//Do Nothing
		}
		
		
	}
	
	//---- PUBLIC METHODS
	
	/**
	 * Return the RouteList of bus routes that an Agency 
	 * services.
	 * 
	 * @param agency the {@link Agency} to which the RouteList applies
	 * @return the list of Routes
	 */
	public Vector<Item> getRouteListItems()
	{
		Vector<Item> routeListItems = new Vector<Item>();
	
		try
		{
			routeListItems = mainModel.getRouteListItems();
		}
		catch(ServiceUnavailableException e)
		{
			mainView.notifyAndExit(MainView.NEXTBUS_SERVICE_UNAVAILABLE);
		}
		
		return routeListItems;

	}
	
	//---- BACKGROUND THREAD
	/**
	 * Retrieves a list of all the RouteConfigs for specified Agency and stores the result in a MainModel variable
	 */
	public void processAllRouteConfigs()
	{
		mainView.updateStatus("Address search disabled until Route Configs loaded...");
		try
		{
			long current = System.currentTimeMillis();
			RouteList routeList = mainModel.getRouteList();
			Map<String, RouteConfig> allRouteConfigs = new HashMap<String, RouteConfig>(routeList.getRoutes().size());
			
			mainView.setupProgressBar(routeList.getRoutes().size());
			int progressLength = 1;
			
			for (Route route : routeList.getRoutes())
			{
				RouteConfig routeConfig = mainModel.getRouteConfig(route);
				allRouteConfigs.put(route.getTag(), routeConfig);
				mainView.updateProgressBar(progressLength++);
				Thread.sleep(1);
			}
			
			mainModel.setAllRouteConfigs(allRouteConfigs);
			mainView.updateStatus("It took " + (System.currentTimeMillis() - current) + " ms to get all routeConfigs.");
			Thread.sleep(5000);
			mainView.updateStatus(MainView.DEFAULT_STATUS);
			
		}
		catch (InterruptedException | ServiceUnavailableException e) 
		{
			e.printStackTrace();
		}
	}
	
	//---- PRIVATE METHODS
	
	// Draw on Map
	
	/**
	 * Accepts {@link Stop} List and converts it to a set of WayPoints. 
	 * 
	 * @param stopList - List of Stop objects
	 */
	private Set<Waypoint> getWayPoints(List<Stop> stopList) {
		Set<Waypoint> waypoints = JXMapKitHelper.convertStopsToWaypoints(stopList);
		return waypoints;		
	}
	
	/**
	 * Accepts Route Tag value to retrieve Route object. 
	 * Gets list of GeoPosition points using Route object.
	 * Draws route on map based on retrieved GeoPosition points.
	 * 
	 * @param routeTag - Route Object Tag value (String)
	 */
	private void drawMapRoute(String routeTag)
	{
		try 
		{
			Route route = mainModel.getRoute(routeTag);
			RouteConfig routeConfig = mainModel.getRouteConfig(route);
			ArrayList<Path> ttfPaths = mainModel.getPathList(route); 
			
			mapControl = mainView.getMapControl();
			mapControl.drawRoute(routeConfig, ttfPaths);
			mapControl.getAllOverlays();
			
			mainView.updateMapViewer();
		} 

		catch (ServiceUnavailableException e) 
		{
			mainView.notifyAndExit(MainView.NEXTBUS_SERVICE_UNAVAILABLE);
		}
	}
	
	/**
	 * Accepts {@link Stop} List to draw stops on map for specific set of Stop objects. 
	 * 
	 * @param stopList - List of Stop objects
	 */
	private void drawMapStops(List<Stop> stopList) 
	{
		mapControl = mainView.getMapControl();
		mapControl.addWaypoint(getWayPoints(stopList), stopList);
		mapControl.getAllOverlays();
	}
	
	/**
	 * Accepts {@link Stop} List to draw stops on map for specific set of Stop objects. 
	 * 
	 * @param stopList - List of Stop objects
	 */
	private void drawClosestMapStops(List<Stop> stopList, String address) 
	{
		try {
			mapControl = mainView.getMapControl();
			mapControl.setCenterPosition(mainModel.getSearchGeoPosition(address));
			mapControl.setZoom(2);
			mapControl.addWaypoint(getWayPoints(stopList), stopList);
			mapControl.addHomeWaypoint(mainModel.getSearchWayPoint(address));
			mapControl.getStopsNearHome();
		}
		catch (ServiceUnavailableException e) {
			mainView.notifyAndExit(MainView.NEXTBUS_SERVICE_UNAVAILABLE);
		}
		catch (InvalidAddressException e) {
			mainView.updateStatus(MainView.INVALID_ADDRESS);
		}
	}
	
	/**
	 * Accepts {@link Direction} object to draw stops on map for specific Direction. 
	 * 
	 * @param direction - Direction Object
	 */
	private void drawMapStops(Direction direction) 
	{
		// Uses the FindStops Helper to retrieve a list of stops specific to a Direction
		drawMapStops(FindStopsHelper.identifyStopsForDirection(direction, mainModel.getAllStopsList()));
	}
	
	
	// Update ComboBoxes
	
	/**
	 * Accepts Item Vector List of all the Bus Directions.
	 * Populates Bus Directions ComboBox with provided Bus Directions List.
	 * 
	 * @param busDirectionItems Item Vector List
	 */
	private void updateMapDirections(Vector<Item> busDirectionItems)
	{
		mainView.clearDirections();
		mainView.enableRouteDirections(true);
		mainView.enableRefresh(true);
		
		Iterator<Item> itr = busDirectionItems.iterator();
		while(itr.hasNext())
		{
			mainView.addRouteDirectionItem((Item)itr.next());
		}
	}
	
	
	// Update Table
	
	/**
	 * Retrieves list of Route stops from Model and passes over to the View updateStops method
	 * @throws ServiceUnavailableException 
	 */
	private void updateRouteStopsTable(ArrayList<Stop> stopList) throws ServiceUnavailableException
	{
		mainView.updateStopsTable(mainModel.getRouteStopsArray(stopList));
	}
	
	
	/**
	 * Retrieves list of Direction stops from Model and passes over to the View updateStops method
	 * @throws ServiceUnavailableException 
	 */
	private void updateDirectionStopsTable(Direction direction) throws ServiceUnavailableException
	{
		String[][] stopList = mainModel.getRouteStopsArray(FindStopsHelper.identifyStopsForDirection(direction, mainModel.getAllStopsList()));
		mainView.enableRefresh(stopsExist(stopList.length));
		mainView.updateStopsTable(stopList);
	}
	
	
	// Clear Out Data
	
	/**
	 * Clears Bus Route and Stop information from Map and Table
	 */
	private void resetAll() {
		mainView.clearStops();
		mainView.clearDirections();
		mainView.enableRouteDirections(false);
		mainView.resetMapViewer();
	}
	
	/**
	 * Check if # of stops returned is > 0 to enable Refresh Button
	 */
	private boolean stopsExist(int arraySize)
	{
		if(arraySize > 0 ) {
			return true;		
		}
		else
			return false;
	}

}
