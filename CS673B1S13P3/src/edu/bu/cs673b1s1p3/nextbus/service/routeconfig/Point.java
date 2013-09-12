/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routeconfig;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A Point contains:
 * <ul>
 * <li>a latitude
 * <li>a longitude
 * </ul>
 * 
 * @author ton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "point")
public class Point {

    @XmlAttribute(name = "lat", required = true)
    protected BigDecimal lat;
    
    @XmlAttribute(name = "lon", required = true)
    protected BigDecimal lon;

    /**
     * Gets the value of the lat property.
     * 
     * @return the latitude value   
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * Sets the value of the lat property.
     * 
     * @param value the latitude
     */
    public void setLat(BigDecimal value) {
        this.lat = value;
    }

    /**
     * Gets the value of the lon property.
     * 
     * @return the longitude value    
     */
    public BigDecimal getLon() {
        return lon;
    }

    /**
     * Sets the value of the lon property.
     * 
     * @param value the longitude value
     */
    public void setLon(BigDecimal value) {
        this.lon = value;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point [lat=" + lat + ", lon=" + lon + "]";
	}
}
