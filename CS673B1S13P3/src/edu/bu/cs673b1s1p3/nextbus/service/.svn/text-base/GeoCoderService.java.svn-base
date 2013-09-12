package edu.bu.cs673b1s1p3.nextbus.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * 
 * @author Jimson
 *
 */
@SuppressWarnings("deprecation")
public class GeoCoderService {
	public static final String GEOCODER_URL = "http://rpc.geocoder.us/service/csv?address=";
	//private static String responseBody = "";
	
	/**
	 * Constructor
	 */
	public GeoCoderService() {
		
	}
	
	/**
	 * Converts the input address to GeoPosition object
	 * 
	 * @param address a string address entered by user
	 * @return geoPosition the geoPosition contains latitude and longitude
	 * @throws IOException
	 * @throws InvalidAddressException 
	 */
	public GeoPosition convertAddressToGeoPosition(String address) throws IOException, InvalidAddressException {
		@SuppressWarnings({ "resource" })
		HttpClient httpclient = new DefaultHttpClient();
		StringBuilder sb = new StringBuilder();

		try {
			// String mAddress = replaceSpaces(address);
			String mAddress = URLEncoder.encode(address, "UTF-8");
			HttpGet httpGet = new HttpGet(GEOCODER_URL + mAddress);
			HttpResponse response = httpclient.execute(httpGet);
			
			// Get the response
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			
			String responseBody = "";
			
			while ((responseBody = reader.readLine()) != null) {
				sb.append(responseBody);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
		}
		
		GeoPosition position = convertResponseToGeoposition(sb.toString());
		return position;
	}
	
	/**
	 * Replaces all spaces in the address string with %20
	 * 
	 * @param address input address
	 * @return outStr output string with %20
	 */
	public static String replaceSpaces(String address) {
		char[] str = address.toCharArray();
		int spaceCount = 0, newLength, i = 0;
		for (i=0; i<str.length; i++) {
			if (str[i] == ' '||str[i] == ','||str[i] == '.') {
				spaceCount++;
			}
		}
		newLength = str.length + spaceCount*2;
		char[] newStr = new char[newLength];
		int j=0;
		for (i=0; i<str.length; i++) {
			if (str[i] == ' '||str[i] == ','||str[i] == '.') {
				newStr[j] = '%';
				newStr[j+1] = '2';
				newStr[j+2] = '0';
				j += 3;
			} else {
				newStr[j] = str[i];
				j++;
			}
		}
		String outStr = new String(newStr);
		return outStr;
	}
	
	/**
	 * Converts an address response from geocoder to the GeoPosition
	 * 
	 * @param response the address response from geocoder
	 * @return geoPosition that contains latitude and longitude
	 */
	public static GeoPosition convertResponseToGeoposition(String response) throws InvalidAddressException {
		// Invalid address entered may return "2: couldn't find this address! sorry"
		if (response.contains("find this address! sorry")) {
			throw new InvalidAddressException("Invalid Address");
		}
		
		String[] tokens = response.split("\\,");
		
		BigDecimal lat = new BigDecimal(tokens[0]);
		BigDecimal log = new BigDecimal(tokens[1]);
		
		return new GeoPosition(lat.doubleValue(), log.doubleValue());
	}
}
