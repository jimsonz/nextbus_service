package edu.bu.cs673b1s1p3.nextbus.map;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.Waypoint;

import edu.bu.cs673b1s1p3.nextbus.map.MapControl;
import edu.bu.cs673b1s1p3.nextbus.map.MapControlInterface;
import edu.bu.cs673b1s1p3.nextbus.map.MapModel;
import edu.bu.cs673b1s1p3.nextbus.service.NextBusService;
import edu.bu.cs673b1s1p3.nextbus.service.ServiceUnavailableException;
import edu.bu.cs673b1s1p3.nextbus.service.agencylist.Agency;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Path;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.RouteConfig;
import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;

/**
 * @author Jimson
 */

public class MapTest {
	
	public static void main(String[] args) throws ServiceUnavailableException {
		JFrame frame = new JFrame();
		
		JXMapKit mapViewer = new JXMapKit();

		// default map view
		MapModel mapModel = new MapModel(mapViewer);
		MapControlInterface mapControl = new MapControl(mapModel);
		mapControl.createMapView();
		
		// draw a route on the map
		NextBusService nextBus = NextBusService.getInstance();
		Agency agency = nextBus.getAgency("mbta");
		Route route = nextBus.getRoute(agency, "225");
		
/*		
 		// draw route for only one direction
		Direction dir = nextBus.getDirection(agency, route, "225_0_var2");
		RouteConfig routeConfig = nextBus.getRouteConfig(route);
		
		List<Stop> stopsInDirection = FindStopsHelper.identifyStopsForDirection(dir, routeConfig.getBusRoute().getStops());
		ArrayList<GeoPosition> geoPositions = JXMapKitHelper.convertStopsToGeoPositions(stopsInDirection);
		//RouteConfig config = nextBus.getRouteConfig(route);
		MapModel mapModel1 = new MapModel(mapViewer, geoPositions);
		MapControlInterface mapControl1 = new MapControl(mapModel1);
		
		//mapModel.setRouteStations(nextBus, route);
		mapControl1.drawRoute();
*/
		
/*
		// draw route based on all stops in route 225
		mapModel.setRouteStations(nextBus, route);
		mapControl.drawRoute();
*/
		
		// draw route using a list of paths in route 225
		RouteConfig routeConfig = nextBus.getRouteConfig(route);
		ArrayList<Path> ttfPaths = nextBus.getPathList(agency, route);
		//Set<Waypoint> waypoints = JXMapKitHelper.convertStopsToWaypoints(routeConfig.getBusRoute().getStops());
		
		mapControl.drawRoute(routeConfig, ttfPaths);
		//mapControl.addWaypoint(waypoints);
		mapControl.addHomeWaypoint(new Waypoint(42.3518099,-71.00541));
		//mapControl.addStopIdentifications(routeConfig.getBusRoute().getStops());
		//mapControl.addAllWaypointLabels(routeConfig.getBusRoute().getStops());
		mapControl.getAllOverlays();
		//mapControl.setStop(home);
		//mapControl.clearOverlays();
				
		frame.add(mapViewer);
		frame.pack();
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}
