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

public class APIRequestHandler {
	static StringBuilder responseContent = new StringBuilder();
	
	static HttpURLConnection connection;
	static URL url;
	static int responseCode;
	public static String actualString = null;

	public static List<String> getRequest(String enPoint) {
		List<String> getresponse = new ArrayList<String>();;
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
			String strChar = responseContent.toString().substring(0, 1);
			String endChar = responseContent.toString().substring(responseContent.toString().length() - 1,
					responseContent.toString().length());
			if (strChar.contentEquals("[") && endChar.contentEquals("]")) {
				actualString = responseContent.toString().substring(1, responseContent.toString().length() - 1);
			} else {
				actualString = responseContent.toString();
			}
			
			getresponse.add(Integer.toString(responseCode));
			getresponse.add(actualString.toString());
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return getresponse;
	}

	public static List<String> postRequest(String endPoint, String playload,String headers) {
		List<String> postresponse = new ArrayList<String>();;		
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
}
