package edu.bu.cs673b1s1p3.nextbus.service;

import java.util.ArrayList;
import java.util.List;

import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Direction;
import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;

public class FindStopsHelper {
	
	/**
	 * Because in the Direction tag, stops include only element tag but not title, lat, lon...
	 * Identify all stops in the selected direction and match them with the stops 
	 * in the entire route to find out the stop information (title, lat, lon...).
	 * 
	 * @param direction the selected direction
	 * @param stopsInAllDirections the List of {@link Stop} objects
	 * @return stopsSelected the List of {@link Stop} objects with information
	 * 
	 * @author Jimson
	 */
	public static List<Stop> identifyStopsForDirection(Direction direction, final List<Stop> stopsInAllDirections) {
		List<Stop> stopsInSelectedDirection = direction.getStops();
		List<Stop> stopsSelected = new ArrayList<Stop>(stopsInSelectedDirection.size());
		
		for (int i=0; i<stopsInSelectedDirection.size(); i++) {
			for (Stop stopA : stopsInAllDirections) {
				if (stopsInSelectedDirection.get(i).getTag().equals(stopA.getTag())) {
					stopsSelected.add(i, stopA);
					break;
				}
			}
		}
		return stopsSelected;
	}

}
