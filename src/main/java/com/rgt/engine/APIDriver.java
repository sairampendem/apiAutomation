package com.rgt.engine;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rgt.utils.APIConstants;

public class APIDriver 
{
	public static List<String> PostsAPI(String endPoint)
	{
		List<String> postsApidetails =APIRequestHandler.postRequest(endPoint, readFileAsString(getPayloadPath()));

		return postsApidetails;
	}
	public static List<String> PostsGetAPI(String endPoint)
	{
		List<String> postsGetApidetails =APIRequestHandler.getRequest(endPoint);
		return postsGetApidetails;
	}
	public static List<String> CommentsGetAPI(String endPoint)
	{
		List<String> CommentsGetAPI =APIRequestHandler.getRequest(endPoint);
		return CommentsGetAPI;
	}
	public static List<String> getXmlResponse(String endPoint)
	{
		List<String> soapResponse =APIRequestHandler.soapRequest(endPoint,readFileAsString(getPayloadPath()));
		System.out.println(soapResponse);
		return soapResponse;
	}
	public static String getPayloadPath() 
	{
		String parentMathod = Thread.currentThread().getStackTrace() [2].getMethodName();
		return String.format(APIConstants.REQUEST_PATH, parentMathod);
	}
	public static String readFileAsString(String file) 
	{
		try {
			return new String (Files.readAllBytes(Paths.get(file))); 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getXMLResponeValueByXpath(String xmlResponse,String xpath) {
		
		String actualValue="";
		 
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

}
