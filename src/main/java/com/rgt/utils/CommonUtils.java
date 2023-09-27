
package com.rgt.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.jayway.jsonpath.JsonPath;

public class CommonUtils {

	public static String getXMLtagValue(String xml, String tagName) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(xml));
		Document doc = builder.parse(src);
		return doc.getElementsByTagName(tagName).item(0).getTextContent();
	}

//	public static String getAllJsonArrayValue(String responseString, String xpath)
//	{
//		StringBuilder sb = new StringBuilder();
//		try {
//			int arraySize = JsonPath.with(responseString).arraySize(xpath);
//			for(int i=0; i < arraySize; i++)
//			{
//				String actualValue = JsonPath.with(responseString).getAsString(xpath + "[" + i+"]");
//				sb.append(actualValue + " , ");
//			}
//		}catch (Exception e)
//		{
//			return sb.toString();
//					}
//	}

	public static Map<String, String> getAllDataElements(String dataelement, String expectedValue) {
		Map<String, String> dataElementsAndValue = new LinkedHashMap<>();
		String[] arrDataelement = dataelement.split(",");
		String[] arrExpValue = expectedValue.split(",");
		for (int i = 0; i < arrDataelement.length; i++) {
			dataElementsAndValue.put(arrDataelement[i], arrExpValue[i]);
		}
		return dataElementsAndValue;
	}

	public static String getJsonValue(String json, String xpath) {
		String value = JsonPath.read(json, xpath);

		return value;
	}

	public static Map<String, String> getExpectedDataElementsAndValues(String dataelements, String expectedValues) {

		Map<String, String> dataElementsAndValues = new LinkedHashMap<String, String>();
		String[] arrDataElements = dataelements.split(";");
		String[] arrExpValues = expectedValues.split(";");
		for (int i = 0; i < arrDataElements.length; i++) {
			dataElementsAndValues.put(arrDataElements[i], arrExpValues[i]);
		}
		return dataElementsAndValues;
	}

	public static Map<String, String> getActualDataElementsXpath(String dataelements, String xpath) {

		Map<String, String> actualDataElementsXpath = new LinkedHashMap<String, String>();

		actualDataElementsXpath.put(dataelements, xpath);

		return actualDataElementsXpath;
	}

}
