/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routelist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RouteList contains a list of Bus Routes returned
 * from the NextBus call:
 * 
 * http://webservices.nextbus.com/service/publicXMLFeed?command=routeList&a=agency
 * 
 *  @author ton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "route"
})
@XmlRootElement(name = "body")
public class RouteList {

    @XmlElement(required = true)
    protected List<Route> route;
    
    @XmlAttribute(name = "copyright", required = true)
    protected String copyright;

    /**
     * Default no-arg constructor
     */
    public RouteList() {
    	// no-arg
    }
    
    /**
     * Constructor that will make a copy of the route list
     * being passed in, but limiting the routes to the number
     * in the limit arg.
     * 
     * @param routeList the {@link RouteList} to limit
     * @param limit the number to limit routes to
     * 
     */
    public RouteList(final RouteList routeList, final int limit) {
    	this.route = routeList.getRoutes().subList(0, limit);
    	this.copyright = routeList.copyright;
    }
    

    /**
     * Return a list of Bus Routes that a particular
     * Agency runs.
     * 
     * @return list of bus routes
     */
    public List<Route> getRoutes() {
        if (route == null) {
            route = new ArrayList<Route>();
        }
        return this.route;
    }

    /**
     * Gets the value of the copyright property.
     * 
     * @return the copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the value of the copyright property.
     *
     * @param value the copyright 
     */
    public void setCopyright(String value) {
        this.copyright = value;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("RouteList ["); 
		
		// Add routes
		for(Route route : getRoutes()) {
			str.append("\n\t" + route.toString());
		}
		str.append("]");
		
		return str.toString();
	}

}
