/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routeconfig;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A Stop contains:
 * <ul>
 * <li>a unique tag
 * <li>a title
 * <li>a latitude
 * <li>a longitude
 * <li>a stop id
 * </ul>
 * 
 * @author ton
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "stop")
public class Stop {

    @XmlAttribute(name = "lat")
    protected BigDecimal lat;

    @XmlAttribute(name = "lon")
    protected BigDecimal lon;
    
    @XmlAttribute(name = "stopId")
    protected BigInteger stopId;
    
    @XmlAttribute(name = "tag", required = true)
    protected String tag;

    @XmlAttribute(name = "title")
    protected String title;
    
    protected String nextBusTime;

    /**
     * Gets the value of the latitude property.
     * 
     * @return the latitude property
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value the latitude property
     */
    public void setLat(BigDecimal value) {
        this.lat = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return the longitude value    
     */
    public BigDecimal getLon() {
        return lon;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value the longitude value 
     */
    public void setLon(BigDecimal value) {
        this.lon = value;
    }

    /**
     * Gets the value of the stopId property.
     * An optional numeric ID to identify the stop.
     * 
     * @return the stop Id
     */
    public BigInteger getStopId() {
        return stopId;
    }

    /**
     * Sets the value of the stopId property.
     * An optional numeric ID to identify the stop.
     * 
     * @param value the optional stop id
     */
    public void setStopId(BigInteger value) {
        this.stopId = value;
    }

    /**
     * Gets the value of the tag property.
     * This is a unique identifier for this stop.
     * 
     * @return the unique tag for this stop
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     *  This is a unique identifier for this stop.
     *  
     * @param value the unique identifier for this stop
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
    
	public String getNextBusTime() {
		return nextBusTime;
	}

	public void setNextBusTime(String nextBusTime) {
		this.nextBusTime = nextBusTime;
	}

	/* (non-Javadoc)
	 * 
	 * Object identity is provided by the tag attribute
	 * of this object.
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
	 * Objected identity is provided by the tag
	 * attribute of this object.
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
		Stop other = (Stop) obj;
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
		return "Stop [" +
				"tag=" + tag + 
				", title=" + title + 
				", stopId=" + stopId + 
				", lat=" + lat + 
				", lon=" + lon + 
				"]";
	}
}
