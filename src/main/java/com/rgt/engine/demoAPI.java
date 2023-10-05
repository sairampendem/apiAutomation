package com.rgt.engine;

import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rgt.utils.CommonUtils;
import com.rgt.utils.ExcelUtils;
import com.rgt.utils.TestData;

public class demoAPI 
{
	public static void PostsAPI() {
		System.out.println();
		Boolean b = APIDriver.validateJsonSchema(APIDriver.getSchemaPath(), APIDriver.getPayloadPath("JSON"));
		System.out.println(b);
	}
	 @SuppressWarnings("unlikely-arg-type")
	public static void main(String args[]) throws MalformedURLException
	 {
//		 
//		 final String SCENARIO_SHEET_PATH = System.getProperty("user.dir") + "/API_Sheet.xlsx";
//		 ExcelUtils excelutils = new ExcelUtils(SCENARIO_SHEET_PATH);
//		 Map<String, String> m = CommonUtils.getExpectedDataElementsAndValues(excelutils.getAPIData().get(0).getdataElements(),excelutils.getAPIData().get(0).getExpected());
//		for(String s: m.keySet()) {
//		 System.out.println(s);
//		}
		//System.out.println( m.get(excelutils.getAPIData().get(0).getdataElements()));
//		System.out.println();
//		//TestDriver.tableCreation();
//		APIDriver ad = new APIDriver();
//		Boolean b = APIDriver.validateJsonSchema(APIDriver.getSchemaPath(), APIDriver.getPayloadPath("JSON"));
//		System.out.println(b);
		 PostsAPI();

	 }

}
