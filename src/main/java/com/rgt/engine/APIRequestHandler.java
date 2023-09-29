package com.rgt.engine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rgt.utils.APIConstants;

public class APIRequestHandler 
{
	static HttpURLConnection connection;
	static URL url;
	static int responseCode;
	public static String actualString = null;

	public static List<String> getRequest(String enPoint) 
	{
		List<String> getresponse = new ArrayList<String>();
		StringBuilder responseContent = new StringBuilder();
		List<String> tempresponse = new ArrayList<>();
		try {
			url = new URL(enPoint);
			// Open a connection to the URL
			connection = (HttpURLConnection) url.openConnection();
			// Set the request method (GET)
			connection.setRequestMethod("GET");
			// connection.setRequestProperty("Authorization", "Bearer YourAccessToken");
			connection.setRequestProperty("Content-Type", "application/json");
			responseCode = connection.getResponseCode();
			// Read and print the response content
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				responseContent.append(line);
			}
			getresponse.add(Integer.toString(connection.getResponseCode()));
			getresponse.add(responseContent.toString());
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tempresponse.addAll(getresponse);
		getresponse.clear();
		return tempresponse;
	}

	public static List<String> postRequest(String endPoint, String playload) 
	{
		List<String> postresponse = new ArrayList<String>();	
		try {

			// Create a URL object
			url = new URL(endPoint);
			// Open a connection to the URL
			connection = (HttpURLConnection) url.openConnection();
			// Set the request method to POST
			connection.setRequestMethod("POST");
			// Set request headers
			// connection.setRequestProperty("Authorization", "Bearer YourAccessToken");
			connection.setRequestProperty("Content-Type", "application/json");
			// Enable input and output streams for writing the request body
			connection.setDoOutput(true);
			try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
				dataOutputStream.writeBytes(playload);
				dataOutputStream.flush();
			}
			// Get the response code
			responseCode = connection.getResponseCode();
			// Read and print the response content
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder responseContent = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				responseContent.append(line);
			}
			postresponse.add(Integer.toString(responseCode));
			postresponse.add(responseContent.toString());
			reader.close();
			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postresponse;
	}
	public static List<String> soapRequest(String endPoint,String playload) {
		List<String> soapresponse = new ArrayList<String>();	
		List<String> tempsoapresponse = new ArrayList<String>();
		StringBuffer response = new StringBuffer();
		try {
			 String url = "https://www.crcind.com/csp/samples/SOAP.Demo.cls?soap_method=FindPerson&id=1";
			 URL obj = new URL(endPoint);
			 connection = (HttpURLConnection) obj.openConnection();
			 connection.setRequestMethod("POST");
			 connection.setRequestProperty("Content-Type","application/xml; charset=utf-8");
			 String countryCode="Canada";
			 String xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\">\r\n"
			 		+ "<SOAP-ENV:Body>\r\n"
			 		+ "<FindPersonResponse xmlns=\"http://tempuri.org\">\r\n"
			 		+ "<FindPersonResult>\r\n"
			 		+ "<Name>Newton,Dave R.</Name>\r\n"
			 		+ "<SSN>384-10-6538</SSN>\r\n"
			 		+ "<DOB>2000-03-20</DOB>\r\n"
			 		+ "<Home>\r\n"
			 		+ "<Street>6977 First Street</Street>\r\n"
			 		+ "<City>Pueblo</City>\r\n"
			 		+ "<State>AK</State>\r\n"
			 		+ "<Zip>63163</Zip>\r\n"
			 		+ "</Home>\r\n"
			 		+ "<Office>\r\n"
			 		+ "<Street>9984 Second Blvd</Street>\r\n"
			 		+ "<City>Washington</City>\r\n"
			 		+ "<State>MN</State>\r\n"
			 		+ "<Zip>42829</Zip>\r\n"
			 		+ "</Office>\r\n"
			 		+ "<FavoriteColors>\r\n"
			 		+ "<FavoriteColorsItem>Red</FavoriteColorsItem>\r\n"
			 		+ "</FavoriteColors>\r\n"
			 		+ "<Age>23</Age>\r\n"
			 		+ "</FindPersonResult>\r\n"
			 		+ "</FindPersonResponse>\r\n"
			 		+ "</SOAP-ENV:Body>\r\n"
			 		+ "</SOAP-ENV:Envelope>";
			 connection.setDoOutput(true);
			 DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			 wr.writeBytes(playload);
			 wr.flush();
			 wr.close();
			 soapresponse.add(Integer.toString(connection.getResponseCode()));
			 BufferedReader in = new BufferedReader(new InputStreamReader(
					 connection.getInputStream()));
			 String inputLine;
			 
			 while ((inputLine = in.readLine()) != null) {
			 response.append(inputLine);
			 
			 }
			 in.close();
			 soapresponse.add(response.toString());
			 tempsoapresponse.addAll(soapresponse);
			 soapresponse.clear();
			 } catch (Exception e) {
			 System.out.println(e);
			 }
		return tempsoapresponse;
	}
	
}
