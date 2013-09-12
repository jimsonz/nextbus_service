/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routeconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RouteConfig is returned from the following NextBus call:
 * 
 * http://webservices.nextbus.com/service/publicXMLFeed?command=routeConfig&a=mbta&r=225
 * 
 * The best use of this object is to get the {@link BusRoute} object
 * via getBusRoute() as it contains the pertinent information.
 * 
 * @author ton
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "route"
})
@XmlRootElement(name = "body")
public class RouteConfig {

    @XmlElement(required = true)
    protected BusRoute route;
    
    @XmlAttribute(name = "copyright", required = true)
    protected String copyright;

    /**
     * Gets the value of the route property.
     * 
     * @return the BusRoute
     */
    public BusRoute getBusRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value the BusRoute
     */
    public void setBusRoute(BusRoute value) {
        this.route = value;
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
	 * 
	 * RouteConfig delegates to the toString in BusRoute.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RouteConfig [" + getBusRoute().toString() + "]";
	}    
}
