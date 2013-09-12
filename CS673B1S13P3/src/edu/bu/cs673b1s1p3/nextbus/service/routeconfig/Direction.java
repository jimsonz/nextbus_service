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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Direction contains:
 * <ul>
 * <li>a unique tag
 * <li>a title
 * <li>a list of {@link Stop} objects
 * <li>a name
 * <li>use for ui indicator
 * </ul>
 * 
 * Directions can have multiple different sets of stops.
 * Therefore, there aren't just two simple directions
 * for a Route. If you are displaying a Route on a 
 * map, just display all the Stops. But if you are 
 * creating route/direction/stop information for a UI,
 * then you will need to use all of the direction data.
 * 
 * @author ton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stop"
})
@XmlRootElement(name = "direction")
public class Direction {

    @XmlElement(required = true)
    protected List<Stop> stop;
    
    @XmlAttribute(name = "name", required = true)
    protected String name;

    @XmlAttribute(name = "tag", required = true)
    protected String tag;

    @XmlAttribute(name = "title", required = true)
    protected String title;

    @XmlAttribute(name = "useForUI", required = true)
    protected boolean useForUI;

    /**
     * Get the list of {@link Stop} objects for this direction.
     * These stops are in order. This is useful
     * for when a user selects a route, a direction
     * and then stop in order to obtain predictions
     * for this stop.
     * 
     * @return list of {@link Stop} for this direction
     */
    public List<Stop> getStops() {
        if (stop == null) {
            stop = new ArrayList<Stop>();
        }
        return this.stop;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return the direction name     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value the direction name
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the tag property.
     * This is an unique value for this direction.
     * 
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * This is a unique value for this direction.
     *
     * @param value the tag value to set
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return title of the direction
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value the title to set
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the useForUI property.
     * If the direction is important enough to be listed
     * to a passenger, the this flag is set to true.
     *
     * @return iseUseForUi property
     */
    public boolean isUseForUI() {
        return useForUI;
    }

    /**
     * Sets the value of the useForUI property.
     * If the direction is important enough to be listed
     * to a passenger, the this flag is set to true.
     * 
     * @param value the value to set
     */
    public void setUseForUI(boolean value) {
        this.useForUI = value;
    }

	/* (non-Javadoc)
	 * 
	 * Object identification is based on the tag
	 * attribute of this object.
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
	 * Object identification is based on the tag
	 * attribute for this object.
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
		Direction other = (Direction) obj;
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
		StringBuilder str = new StringBuilder("Direction [" +
				"tag=" + tag +
				", title=" + title + 
				", name=" + name + 
				", useForUI=" + useForUI); 
		
		// Add stops
		str.append("\n[ Stops:");
		for(Stop stop : getStops()) {
			str.append("\n\t" + stop.toString());
		}
		str.append("]");
		str.append("]");
		
		return str.toString();
	}
}
