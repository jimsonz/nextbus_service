/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.routelist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A Route represents a Bus Route.
 * A Route is represented in the context of an agency.
 * A Route contains a unique tag value and a title.
 * 
 * @author ton
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "route")
public class Route {

    @XmlAttribute(name = "tag", required = true)
    protected String tag;

    @XmlAttribute(name = "title", required = true)
    protected String title;

    /**
     * No arg constructor
     */
    public Route() {
    	// intentionally empty
    }
    
    /**
     * Constructor that accepts a tag and
     * a title.
     * 
     * @param tag the unique tag
     * @param title the title
     */
    protected Route(final String tag, final String title) {
    	this.tag = tag;
    	this.title = title;
    }
    
    /**
     * Gets the value of the tag property.
     * Tag acts as the primary key for other
     * NextBus web service calls that return
     * information related to routes.
     * 
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value the tag 
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return the route title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value title
     */
    public void setTitle(String value) {
        this.title = value;
    }

	/*
	 * Override object hashCode for an Route.
	 * The hashcode is based on the tag being a unique identifier.
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

	/*
	 * Override Object equals for an Route.
	 * Equals is based on the tag being unique for an Agency.
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
		Route other = (Route) obj;
		if (tag == null) {
			if (other.tag != null) {
				return false;
			}
		} 
		else if (!tag.equals(other.tag)) {
			return false;
		}
		return true;
	}

    
	/* (non-Java doc)
	 * Override Object toString.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Route [tag=" + tag + ", title=" + title + "]";
	}
}
