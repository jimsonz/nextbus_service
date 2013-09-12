/**
 * Boston University
 * CS673 - Software Engineering
 * Professor Yuting Zhang
 * Team 3 
 *
 */
package edu.bu.cs673b1s1p3.nextbus.service.agencylist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edu.bu.cs673b1s1p3.nextbus.service.routelist.Route;

/**
 * The AgencyList contains the list of @{link Agency} objects that
 * are returned from the following NextBus command:
 * 
 * http://webservices.nextbus.com/service/publicXMLFeed?command=agencyList
 * 
 * @author ton
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "agency"
})
@XmlRootElement(name = "body")
public class AgencyList {

    @XmlElement(required = true)
    protected List<Agency> agency;
    
    @XmlAttribute(name = "copyright", required = true)
    protected String copyright;

    /**
     * Return a list of {@link Agency} objects or null.
     * 
     * @return a list
     */
     public List<Agency> getAgencies() {
        if (agency == null) {
            agency = new ArrayList<Agency>();
        }
        return this.agency;
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
		StringBuilder str = new StringBuilder("AgencyList ["); 
		
		// Add Agencys
		for(Agency agency : getAgencies()) {
			str.append("\n\t" + agency.toString());
		}
		str.append("]");
		
		return str.toString();
	}

}
