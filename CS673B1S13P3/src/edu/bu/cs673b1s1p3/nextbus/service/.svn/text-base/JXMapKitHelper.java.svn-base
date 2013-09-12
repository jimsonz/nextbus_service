/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;

import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Point;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;

/**
 * @author ton
 *
 */
public class JXMapKitHelper {

	/**
	 * Convert a list of {@link Stop} objects for the NextBus Service, into a list
	 * of GeoPosition objects for use with the JXMapKit.
	 * 
	 * @param stops the List of {@link Stop} objects
	 * @return the List of GeoPosition objects for use with JXMapKit
	 */
	public static ArrayList<GeoPosition> convertStopsToGeoPositions(final List<Stop> stops) {
		ArrayList<GeoPosition> geos = new ArrayList<GeoPosition>(stops.size()); 
		for (Stop stop : stops) {
			geos.add(new GeoPosition(stop.getLat().doubleValue(), stop.getLon().doubleValue()));
		}
		return geos;
	}	
	
	/**
	 * Convert a list of {@link Stop} objects for the NextBus Service, into a set
	 * of Waypoint objects for use with the JXMapKit.
	 * 
	 * @param stops the List of {@link Stop} objects
	 * @return the Set of Waypoint objects for use with JXMapKit
	 */
	public static Set<Waypoint> convertStopsToWaypoints(final List<Stop> stops) {
		Set<Waypoint> waypoints = new HashSet<Waypoint>(stops.size()); 
		for (Stop stop : stops) {
			waypoints.add(new Waypoint(stop.getLat().doubleValue(), stop.getLon().doubleValue()));
		}
		return waypoints;
	}	
	
	/**
	 * Convert a list of {@link Point} objects for the NextBus Service, into a list
	 * of GeoPosition objects for use with the JXMapKit.
	 * 
	 * @param points the List of {@link Point} objects
	 * @return the List of GeoPosition objects for use with JXMapKit
	 */
	public static ArrayList<GeoPosition> convertPointsToGeoPositions(final List<Point> points) {
		ArrayList<GeoPosition> geos = new ArrayList<GeoPosition>(points.size()); 
		for (Point point : points) {
			geos.add(new GeoPosition(point.getLat().doubleValue(), point.getLon().doubleValue()));
		}
		return geos;
	}	
}
