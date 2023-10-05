package com.rgt.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rgt.utils.APIConstants;

public class APIDriver {
//	public static List<String> PostsAPI(String endPoint,String serviceType)
//	{
//		System.out.println(validateJsonSchema(getSchemaPath(),getPayloadPath(serviceType)));
//		List<String> postsApidetails =APIRequestHandler.postRequest(endPoint, readFileAsString(getPayloadPath(serviceType)));
//
//		return postsApidetails;
//	}
	public static List<String> PostsAPI(String endPoint, String serviceType) {
		List<String> postsApidetails = null;
		List<String> response = new ArrayList<>();
		if (validateJsonSchema(getSchemaPath(), getPayloadPath(serviceType))) {
			response.add("Schema validation success..!");
			postsApidetails = APIRequestHandler.postRequest(endPoint, readFileAsString(getPayloadPath(serviceType)));
			if(postsApidetails.get(0).equals("201")) {
			response.addAll(postsApidetails);
			}else {
				response.add(postsApidetails.get(0));
				response.add("API Response error");
			}
			//System.out.println(response);
			return response;
		} else {
			response.add("Schema validation fail..!");
			return response;
		}
		
	}

	public static List<String> PostsGetAPI(String endPoint) {
		List<String> postsGetApidetails = APIRequestHandler.getRequest(endPoint);
		return postsGetApidetails;
	}

	public static List<String> CommentsGetAPI(String endPoint) {
		List<String> CommentsGetAPI = APIRequestHandler.getRequest(endPoint);
		return CommentsGetAPI;
	}

	public static List<String> getXmlResponse(String endPoint, String serviceType) {
		List<String> soapResponse = APIRequestHandler.soapRequest(endPoint,
				readFileAsString(getPayloadPath(serviceType)));
		System.out.println(soapResponse);
		return soapResponse;
	}

//	public static String getPayloadPath() 
//	{
//		String parentMathod = Thread.currentThread().getStackTrace() [2].getMethodName();
//		return String.format(APIConstants.REQUEST_PATH, parentMathod);
//	}
	public static String getPayloadPath(String serviceType) {
		String parentMathod = Thread.currentThread().getStackTrace()[2].getMethodName();
		if (serviceType.equalsIgnoreCase("JSON")) {
			return String.format(APIConstants.JSON_REQUEST_PATH, parentMathod);
		} else {
			return String.format(APIConstants.SOAP_REQUEST_PATH, parentMathod);
		}
	}

	public static String getSchemaPath() {
		String parentMathod = Thread.currentThread().getStackTrace()[2].getMethodName();
		return String.format(APIConstants.REQUEST_SCHEMA_PATH, parentMathod);
	}

	public static String readFileAsString(String file) {
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getXMLResponeValueByXpath(String xmlResponse, String xpath) {

		String actualValue = "";

		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xmldoc = docBuilder.parse(new InputSource(new StringReader(xmlResponse)));
			XPath xpathFactory = XPathFactory.newInstance().newXPath();
			actualValue = xpathFactory.evaluate(xpath, xmldoc);
			System.out.println(actualValue);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actualValue;
	}

	public static Boolean validateJsonSchema(String schema, String request) {
		Boolean isValid = false;
		try {
			File schemaFile = new File(schema);
			JSONTokener schemaData = new JSONTokener(new FileInputStream(schemaFile));
			JSONObject jsonSchema = new JSONObject(schemaData);
			File jsonData = new File(request);
			JSONTokener jsonDataFile = new JSONTokener(new FileInputStream(jsonData));
			JSONObject jsonObject = new JSONObject(jsonDataFile);
			// validate schema
			Schema schemaValidator = SchemaLoader.load(jsonSchema);
			schemaValidator.validate(jsonObject);
			isValid = true;
		} catch (Exception e) {
			isValid = false;
			System.out.println("Schema validation fail, please verify request...!");
		}

		return isValid;
	}

}
