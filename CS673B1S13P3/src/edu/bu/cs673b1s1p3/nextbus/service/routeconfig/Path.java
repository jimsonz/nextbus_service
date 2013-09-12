/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routeconfig;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A Path contains:
 * <ul>
 * <li>a list of points
 * </ul>
 * 
 * Paths are simply lists of coordinates that can be
 * used to draw a route on a map. Due to the nature of
 * the data, there can be many separate paths, some of
 * them overlapping. A map client should simply draw all
 * of the paths. The paths are not necessarily in any
 * kind of order so you should just connect the points 
 * within a path. You should not connect the points between 
 * two separate paths.
 * 
 * @author ton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "point"
})
@XmlRootElement(name = "path")
public class Path {

    @XmlElement(required = true)
    protected List<Point> point;

    /**
     * Return the list of Points in the Path.
     * 
     * @return list of points
     */
    public List<Point> getPoints() {
        if (point == null) {
            point = new ArrayList<Point>();
        }
        return this.point;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Path [\n"); 
		
		// Add points
		for(Point point : getPoints()) {
			str.append("\t" + point.toString() + "\n");
		}
		str.append("]");
		str.append("]");
		
		return str.toString();
	}
}
