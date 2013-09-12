/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routeconfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A BusRoute, in the context of the RouteConfig Service, contains:
 * <ul>
 * <li>a unique tag for the route
 * <li>a title
 * <li>list of {@link Stop} objects
 * <li>list of {@link Direction} objects
 * <li>list of {@link Path} objects
 * <li>a color attribute
 * <li>max latitude value
 * <li>min latitude value
 * <li>max longitude value
 * <li>min longitude value
 * <li>opposite color
 * </ul>
 * 
 * @author ton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stop",
    "direction",
    "path"
})
@XmlRootElement(name = "route")
public class BusRoute {

    @XmlElement(required = true)
    protected List<Stop> stop;
    
    @XmlElement(required = true)
    protected List<Direction> direction;
    
    @XmlElement(required = true)
    protected List<Path> path;
    
    @XmlAttribute(name = "color", required = true)
    protected String color;
    
    @XmlAttribute(name = "latMax", required = true)
    protected BigDecimal latMax;

    @XmlAttribute(name = "latMin", required = true)
    protected BigDecimal latMin;

    @XmlAttribute(name = "lonMax", required = true)
    protected BigDecimal lonMax;

    @XmlAttribute(name = "lonMin", required = true)
    protected BigDecimal lonMin;
    
    @XmlAttribute(name = "oppositeColor", required = true)
    protected String oppositeColor;

    @XmlAttribute(name = "tag", required = true)
    protected String tag;
    
    @XmlAttribute(name = "title", required = true)
    protected String title;

    /**
     * Get the list of {@link Stop} objects.
     * 
     * @return the list of {@link Stop} objects
     */
    public List<Stop> getStops() {
        if (stop == null) {
            stop = new ArrayList<Stop>();
        }
        return this.stop;
    }

    /**
     * Gets the list of {@link Direction} objects.
     * 
     * @return the list of {@link Direction} objects
     */
    public List<Direction> getDirections() {
        if (direction == null) {
            direction = new ArrayList<Direction>();
        }
        return this.direction;
    }

    /**
     * Gets list of {@link Path} objects.
     * 
     * @return the list of {@link Path} objects
     */
    public List<Path> getPaths() {
        if (path == null) {
            path = new ArrayList<Path>();
        }
        return this.path;
    }

    /**
     * Gets the value of the color property.
     * The color is a hexadecimal format associated with the route.
     * Useful for user interfaces such as maps.
     * 
     * @return the color property
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value the hexadecimal color property
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Gets the value of the maximum latitude property.
     * This property specifies the extent of the route.
     * 
     * @return the maximum latitude property
     *     
     */
    public BigDecimal getLatMax() {
        return latMax;
    }

    /**
     * Sets the value of the maximum latitude property.
     * This property specifies the extent of the route.
     * 
     * @param value the maximum latitude
     */
    public void setLatMax(BigDecimal value) {
        this.latMax = value;
    }

    /**
     * Gets the value of the minimum latitude property.
     * This property specifies the extent of the route.
     * 
     * @return the minimum latitude
     */
    public BigDecimal getLatMin() {
        return latMin;
    }

    /**
     * Sets the value of the minimum latitude property.
     * This property specifies the extent of the route.
     * 
     * @param value the minimum latitude
     */
    public void setLatMin(BigDecimal value) {
        this.latMin = value;
    }

    /**
     * Gets the value of the maximum longitude property.
     * This property specifies the extent of the route.
     * 
     * @return the maximum longitude
     */
    public BigDecimal getLonMax() {
        return lonMax;
    }

    /**
     * Sets the value of the maximum longitude property.
     * This property specifies the extent of the route.
     * 
     * @param value the maximum longitude
     */
    public void setLonMax(BigDecimal value) {
        this.lonMax = value;
    }

    /**
     * Gets the value of the minimum longitude property.
     * This property specifies the extent of the route.
     * 
     * @return the minimum longitude
     */
    public BigDecimal getLonMin() {
        return lonMin;
    }

    /**
     * Sets the value of the minimum longitude property.
     * This property specifies the extent of the route.
     * 
     * @param value the minimum longitude    
     */
    public void setLonMin(BigDecimal value) {
        this.lonMin = value;
    }

    /**
     * Gets the value of the oppositeColor property.
     * This color contrasts with the route color.
     * This field is specified in hexadecimal, and
     * will either be black or white.
     * Useful for interfaces such as maps.
     * 
     * @return the hexadecimal value of the opposite color
     *     
     */
    public String getOppositeColor() {
        return oppositeColor;
    }

    /**
     * Sets the value of the oppositeColor property.
     * This color contrasts with the route color.
     * This field is specified in hexadecimal, and
     * will either be black or white.
     * Useful for interfaces such as maps.
     * 
     * @param value the hexadecimal color value
     *     
     */
    public void setOppositeColor(String value) {
        this.oppositeColor = value;
    }

    /**
     * Gets the value of the tag property.
     * This is a unique identifier for this object.
     * 
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value the unique tag value
     *     
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value the title
     */
    public void setTitle(String value) {
        this.title = value;
    }

	/* (non-Javadoc)
	 * 
	 * Override implementation of hashCode to be 
	 * based on the unique tag attribute of this
	 * object.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * 
	 * Override equals() to be based on the unique
	 * tag attribute of this object.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusRoute other = (BusRoute) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("BusRoute [tag=" + tag +
				", title=" + title +
				", color=" + color + 
				", latMax=" + latMax + 
				", latMin=" + latMin + 
				", lonMax=" + lonMax + 
				", lonMin=" + lonMin +
				", oppositeColor=" + oppositeColor);
		
		// Add stops
		str.append("\n[ Stops:");
		for(Stop stop : getStops()) {
			str.append("\n\t" + stop.toString());
		}
		str.append("]");
		
		// Add directions
		str.append("\n[ Directions:");
		for(Direction direction : getDirections()) {
			str.append("\n\t" + direction.toString());
		}
		str.append("]");

		// Add directions
		str.append("\n[ Paths:");
		for(Path path : getPaths()) {
			str.append("\n\t" + path.toString());
		}
		str.append("]");
		
		// close the initial "["
		str.append("]");

		return str.toString();
	}
}
