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
	
	
	static HttpURLConnection connection;
	static URL url;
	static int responseCode;
	public static String actualString = null;

	public static List<String> getRequest(String enPoint) {
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

	public static List<String> postRequest(String endPoint, String playload) {
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
