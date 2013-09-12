package edu.bu.cs673b1s1p3.nextbus.main;

import java.util.ArrayList;
import java.util.List;

import edu.bu.cs673b1s1p3.nextbus.service.routeconfig.Stop;

public class stopsToArray {
	public static String[][] convert(List<Stop> list) 
	{
		String[][] stopListArray = new String[list.size()][2];
		int i = 0;
		
		
		for(Stop stop: list)
		{
			stopListArray[i][1] = stop.getTitle();
			stopListArray[i][2] = "";
			i++;
		}
		return stopListArray;
	}
	
	public static String[][] convert(ArrayList<RouteStopGeoPositionDTO> closestStops) 
	{
		String[][] stopListArray = new String[closestStops.size()][3];
		int i = 0;
		
		for (RouteStopGeoPositionDTO r : closestStops) {
			stopListArray[i][0] = r.getRouteTag();
			stopListArray[i][1] = r.getStop().getTitle();
			stopListArray[i][2] = "";
			i++;
		}
		return stopListArray;
	}
}
