package edu.bu.cs673b1s1p3.nextbus.service;

import junit.framework.Assert;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.junit.Test;

public class GeoCoderServiceTest {

	@Test
	public void testConvertAddressToGeoPosition() throws Exception {
		String address = "31 Kahler Ave Milton MA";
		GeoCoderService geoCoderService = new GeoCoderService();
		GeoPosition testResult = new GeoPosition(42.260324, -71.090954);
		GeoPosition result = geoCoderService.convertAddressToGeoPosition(address);
		Assert.assertEquals(result, testResult);
	}
	
	@Test
	public void testReplaceSpaces() {
		String address = "31 Kahler,Ave Milton,MA";
		String testResult = "31%20Kahler%20Ave%20Milton%20MA";
		String result = GeoCoderService.replaceSpaces(address);
		Assert.assertEquals(result, testResult);
	}
	
	@Test
	public void testConvertResponseToGeopositionn() throws InvalidAddressException {
		String address = "42.260324,-71.090954,31 Kahler Ave,Milton,MA,02186";
		GeoPosition testResult = new GeoPosition(42.260324, -71.090954);
		GeoPosition result = GeoCoderService.convertResponseToGeoposition(address);
		Assert.assertEquals(result, testResult);
	}
	
	@Test
	public void testInvalidAddress() throws InvalidAddressException {
		String address = "---------------------";
		GeoCoderService geoCoderService = new GeoCoderService();
		try {
			geoCoderService.convertAddressToGeoPosition(address);
			Assert.fail();
		} 
		catch (Exception e) {
		}
	}
}
