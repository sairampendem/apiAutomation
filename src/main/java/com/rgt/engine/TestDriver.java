package com.rgt.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.jayway.jsonpath.JsonPath;
import com.lowagie.text.DocumentException;
import com.rgt.utils.CommonUtils;
import com.rgt.utils.ExcelUtils;

public class TestDriver {

	static List<String> apidetails = null;
	ExtentReports extentreport;
	ExtentSparkReporter spark;
	static ExtentTest extentTest;

	APIRequestHandler apidriver;
	static Object userInput;
	static Object classofInput;

	public final static String SCENARIO_SHEET_PATH = System.getProperty("user.dir") + "/API_Sheet.xlsx";
	public final String ExtentReport_Path = System.getProperty("user.dir")
			+ "/resources/reports/APIAutomationReport.html";
	public final String ExcelReport_Path = System.getProperty("user.dir") + "/resources/reports/ExcelReport.xlsx";
	static ExcelUtils excel = new ExcelUtils(SCENARIO_SHEET_PATH);
	int testCaseCount = excel.getAPIData().size();
	static Map<String, String> actualDataElementsXpath;

	public void startExecution() throws IOException, DocumentException {
		extentreport = new ExtentReports();
		spark = new ExtentSparkReporter(ExtentReport_Path).viewConfigurer().viewOrder().as(new ViewName[] {
				ViewName.TEST, ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.DEVICE, ViewName.EXCEPTION }).apply();
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setDocumentTitle("API Test");
		spark.config().setReportName("API Test Result");
		spark.config().setProtocol(Protocol.HTTPS);
		extentreport.setSystemInfo("username", "Sai Ram");
		extentreport.attachReporter(spark);
		extentreport.setSystemInfo("Environment", "QA");
		extentreport.setSystemInfo("User", "sairamprince33@gmail.com");
		extentreport.setSystemInfo("OS", System.getProperty("os.name"));
		extentreport.setSystemInfo("Java Version", System.getProperty("java.version"));
		apidriver = new APIRequestHandler();
		List<String> table = new ArrayList<String>();
		//added
		Map<String, String> expectedDataElementsAndValues = null;
		Map<String, String> actualDataElementsAndXpath = excel.getAPIMappingData();
		Map<String, String> actualDataElementsAndValues = new LinkedHashMap<String, String>();
		String status = "";

		System.out.println("Number of TestCases to be Executing = " + testCaseCount);

		for (int j = 0; j < testCaseCount; j++) {
			//added
			expectedDataElementsAndValues = CommonUtils.getExpectedDataElementsAndValues(
					excel.getAPIData().get(j).getdataElements(), excel.getAPIData().get(j).getExpected());
			table.add("<table border=1>" + "<tr style=color:black>" + "<th>Data Elements</th>" + "<th>Actual Values</th>"
					+ "<th>Expected Values</th>");
			extentTest = extentreport.createTest(excel.getAPIData().get(j).getAPIName());

			String[] dataElements = excel.getAPIData().get(j).getdataElements().split(";");
			String[] expdataElementsValues = excel.getAPIData().get(j).getExpected().split(";");

			for (int i = 0; i < dataElements.length; i++) {

				try {
					switch ((excel.getAPIData().get(j).getAPIName()).trim()) {

					case "POST":
						apidetails = APIDriver.PostsGetAPI(excel.getAPIData().get(j).getendPoint());
						//System.out.println(compareDataElements(apidetails.get(1)));
						break;

					case "POST1":
						apidetails = APIRequestHandler.getRequest(excel.getAPIData().get(j).getendPoint());
						
						break;

					case "COMMENTS":
						apidetails = APIRequestHandler.getRequest(excel.getAPIData().get(j).getendPoint());
						break;

					case "COMMENT1":
						apidetails = APIDriver.PostsAPI(excel.getAPIData().get(j).getendPoint(), "Test1", "Test2","Test3");
						break;

					default:
						System.out.println("API name incorrect");
					}

				} catch (Exception e) {
					System.out.println(e);
				}
				for (String element : expectedDataElementsAndValues.keySet()) {
					actualDataElementsAndValues.put(element,
							JsonPath.read(apidetails.get(1), actualDataElementsAndXpath.get(element)));
					
				}

				table.add("<tr>" + "<td style=color:indigo>" + dataElements[i] + "</td>" + "<td style=color:green>" +actualDataElementsAndValues.get(dataElements[i]) 
						+ "</td>" +"<td style=color:green>" + expdataElementsValues[i]
								+ "</td>" + "</tr>");
				

			}
			table.add("</table>");

			//added
//			for (String element : expectedDataElementsAndValues.keySet()) {
//				actualDataElementsAndValues.put(element,
//						JsonPath.read(apidetails.get(1), actualDataElementsAndXpath.get(element)));
//				
//			}
			table.add("</table>");
			System.out.println(actualDataElementsAndValues.values());
			System.out.println(expectedDataElementsAndValues.values());
			
			if (actualDataElementsAndValues.values().toString().contentEquals(expectedDataElementsAndValues.values().toString())) {
				status = "Pass";
				actualDataElementsAndValues.values().clear();				
			}else {
				status = "Fail";	
				actualDataElementsAndValues.values().clear();	
			}
			
			
			System.out.println(status);
			
		if (status.equalsIgnoreCase("Pass")) {
				extentTest.pass(MarkupHelper.createLabel(table.toString().substring(1, table.toString().length()-1),null));
			} else {
				extentTest.fail(MarkupHelper.createLabel(table.toString().substring(1, table.toString().length()-1), null));
				extentTest.info(MarkupHelper.createLabel("API Validation Fail", ExtentColor.RED));
			}
		
			table.clear();
		}

		extentreport.flush();
	}

	// @SuppressWarnings({ "unused", "null" })
	public static String compareDataElements(String request) {
		Map<String, String> expectedDataElementsAndValues = null;
		Map<String, String> actualDataElementsAndXpath = excel.getAPIMappingData();
		Map<String, String> actualDataElementsAndValues = new LinkedHashMap<String, String>();
		String status = "";

		for (int i = 0; i < excel.getAPIData().size(); i++) {

			expectedDataElementsAndValues = CommonUtils.getExpectedDataElementsAndValues(
					excel.getAPIData().get(i).getdataElements(), excel.getAPIData().get(i).getExpected());
			//System.out.println(expectedDataElementsAndValues.keySet());

			for (String element : expectedDataElementsAndValues.keySet()) {
				actualDataElementsAndValues.put(element,
						JsonPath.read(request, actualDataElementsAndXpath.get(element)));
			}

		}
		System.out.println(actualDataElementsAndValues.values());
		System.out.println(expectedDataElementsAndValues.values());
		{
			
		}
		if (actualDataElementsAndValues.values().toString().contentEquals(expectedDataElementsAndValues.values().toString())) {
			status = "Pass";
			actualDataElementsAndValues.values().clear();				
		}else {
			status = "Fail";	
			actualDataElementsAndValues.values().clear();	
		}
		
		return status;
	}


}
