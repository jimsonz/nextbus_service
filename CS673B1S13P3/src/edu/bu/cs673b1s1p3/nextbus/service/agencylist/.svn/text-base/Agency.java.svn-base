/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.agencylist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Agency object from the the NextBus Public XML Feed Revision 1.20.
 * An Agency represents an agency that provides bus transportation
 * to the public. This object is contained in {@link AgencyList}.
 * 
 * This information is returned from the following NextBus command:
 * 
 * http://webservices.nextbus.com/service/publicXMLFeed?command=agencyList
 * 
 * @author ton
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "agency")
public class Agency {
	
    @XmlAttribute(name = "tag", required = true)
	protected String tag;
    
    @XmlAttribute(name = "title", required = true)
	protected String title;
	
    @XmlAttribute(name = "regionTitle", required = true)
	protected String regionTitle;
	
    @XmlAttribute(name = "shortTitle")
    protected String shortTitle;
	
    /**
     * Constructor.
     */
    public Agency() {
    }
   
	/**
	 * Constructor for an Agency that accepts a tag,
	 * title, regionTitile and an optional shortTitle.
	 * 
	 * @param tag Agency tag, unique identifier
	 * @param title the Agency title
	 * @param regionTitle the Agency region title
	 * @param shortTitle the optional Agency short title
	 */
	public Agency(String tag, String title, String regionTitle, String shortTitle) {
		super();
		this.tag = tag;
		this.title = title;
		this.regionTitle = regionTitle;
		this.shortTitle = shortTitle;
	}

	/**
	 * Returns the Agency tag.
	 * The Tag represents the primary key for an Agency.
	 * An example of a NextBus tag is "mbta".
	 * 
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Returns the Agency title.
	 * An example of a NextBus Agency title is "MBTA".
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Return the Agency region title.
	 * An example region title is "Massachusetts".
	 * 
	 * @return the regionTitle
	 */
	public String getRegionTitle() {
		return regionTitle;
	}
	
	/**
	 * Return the Agency short title.
	 * The short title is provided only if it is different
	 * than the standard title element.
	 * An example of a short title is "MIT".
	 * @return the shortTitle
	 */
	public String getShortTitle() {
		return shortTitle;
	}

	/*
	 * Override object hashCode for an Agency.
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
	 * Override Object equals for an Agency.
	 * Equals is based on the tag bing unique for an Agency.
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
		Agency other = (Agency) obj;
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

	/*
	 * Override Object toString.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Agency [" +
				"tag=" + tag +
				", title=" + title + 
				", regionTitle=" + regionTitle + 
				", shortTitle=" + shortTitle + 
				"]";
	}

}
