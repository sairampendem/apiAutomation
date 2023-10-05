package com.rgt.engine;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.devtools.v113.console.model.ConsoleMessage.Level;
import org.slf4j.*;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
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
import com.rgt.utils.ReportUtility;

import lombok.var;

public class TestDriver {

	ExtentReports extentreport;
	ExtentSparkReporter spark;
	static ExtentTest extentTest;
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
		extentreport = ReportUtility.testReport(ExtentReport_Path);
		List<String> table = new ArrayList<String>();
		Map<String, String> expectedDataElementsAndValues = null;
		Map<String, String> actualDataElementsAndXpath = excel.getAPIMappingData();
		Map<String, String> actualDataElementsAndValues = new LinkedHashMap<String, String>();
		String status = "";
		List<String> actual = new ArrayList<String>();
		List<String> expected = new ArrayList<String>();

		System.out.println("Number of TestCases to be Executing = " + testCaseCount);

		for (int j = 0; j < testCaseCount; j++) {

			List<String> apidetails = null;

			expectedDataElementsAndValues = CommonUtils.getExpectedDataElementsAndValues(
					excel.getAPIData().get(j).getdataElements(), excel.getAPIData().get(j).getExpected());
			table.add("<table border=1>" + "<tr style=color:black>" + "<th>Data Elements</th>"
					+ "<th>Actual Values</th>" + "<th>Expected Values</th>" + "<th>Data Elements Status</th></tr>");
			extentTest = extentreport.createTest(excel.getAPIData().get(j).getAPIName());

			String[] dataElements = excel.getAPIData().get(j).getdataElements().split(";");
			String[] expdataElementsValues = excel.getAPIData().get(j).getExpected().split(";");

			for (int i = 0; i < dataElements.length; i++) {

				try {
					if (excel.getAPIData().get(j).getrequestType().equalsIgnoreCase("GET")) {
						switch ((excel.getAPIData().get(j).getAPIName()).trim()) {

						case "PostsGetAPi":
							apidetails = APIDriver.PostsGetAPI(excel.getAPIData().get(j).getendPoint());
							break;

						case "POST1":
							apidetails = APIRequestHandler.getRequest(excel.getAPIData().get(j).getendPoint());

							break;

						case "CommentsGetApi":
							apidetails = APIDriver.CommentsGetAPI(excel.getAPIData().get(j).getendPoint());
							break;

						default:
							System.out.println("API name incorrect");
						}
					} else {

						switch ((excel.getAPIData().get(j).getAPIName()).trim()) {

						case "CommentsPostApi":
							apidetails = APIDriver.PostsAPI(excel.getAPIData().get(j).getendPoint(),
									excel.getAPIData().get(j).getServiceType());
							break;
						case "SoapResponseTest":
							apidetails = APIDriver.getXmlResponse(excel.getAPIData().get(j).getendPoint(),
									excel.getAPIData().get(j).getServiceType());
							break;

						default:
							System.out.println("API name incorrect");
						}

					}

				} catch (Exception e) {
					System.out.println(e);
				}

				for (String element : expectedDataElementsAndValues.keySet()) {
					if (excel.getAPIData().get(j).getServiceType().equalsIgnoreCase("JSON")) {
						if (excel.getAPIData().get(j).getrequestType().equalsIgnoreCase("GET")) {
							actualDataElementsAndValues.put(element, JsonPath
									.read(apidetails.get(1), actualDataElementsAndXpath.get(element)).toString());
							actual.add(JsonPath.read(apidetails.get(1), actualDataElementsAndXpath.get(element))
									.toString());
							expected.add(expectedDataElementsAndValues.get(element));
						} else {
							actualDataElementsAndValues.put(element, JsonPath
									.read(apidetails.get(2), actualDataElementsAndXpath.get(element)).toString());
							actual.add(JsonPath.read(apidetails.get(2), actualDataElementsAndXpath.get(element))
									.toString());
							expected.add(expectedDataElementsAndValues.get(element));
						}
					} else {
						if (excel.getAPIData().get(j).getrequestType().equalsIgnoreCase("GET")) {
							actualDataElementsAndValues.put(element,
									APIDriver.getXMLResponeValueByXpath(apidetails.get(1),
											actualDataElementsAndXpath.get(element)).toString());
							actual.add(APIDriver.getXMLResponeValueByXpath(apidetails.get(1),
									actualDataElementsAndXpath.get(element)).toString());
							expected.add(expectedDataElementsAndValues.get(element));
						} else {
							actualDataElementsAndValues.put(element,
									APIDriver.getXMLResponeValueByXpath(apidetails.get(1),
											actualDataElementsAndXpath.get(element)).toString());
							actual.add(APIDriver.getXMLResponeValueByXpath(apidetails.get(1),
									actualDataElementsAndXpath.get(element)).toString());
							expected.add(expectedDataElementsAndValues.get(element));
						}
					}
				}
				if (actualDataElementsAndValues.get(dataElements[i]).toString()
						.equalsIgnoreCase(expectedDataElementsAndValues.get(dataElements[i]).toString())) {

					table.add("<tr>" + "<td style=color:indigo>" + dataElements[i] + "</td>" + "<td style=color:green>"
							+ actualDataElementsAndValues.get(dataElements[i]) + "</td>" + "<td style=color:green>"
							+ expectedDataElementsAndValues.get(dataElements[i]) + "</td>" + "<td style=color:green>"
							+ "Pass" + "</td></tr>");
				} else {
					System.out.println(actualDataElementsAndValues.get(dataElements[i]).toString());
					System.out.println(expectedDataElementsAndValues.get(dataElements[i]).toString());

					table.add("<tr>" + "<td style=color:indigo>" + dataElements[i] + "</td>" + "<td style=color:green>"
							+ actualDataElementsAndValues.get(dataElements[i]) + "</td>" + "<td style=color:green>"
							+ expectedDataElementsAndValues.get(dataElements[i]) + "</td>" + "<td style=color:green>"
							+ "Fail" + "</td></tr>");

				}
			}
			table.add("</table>");

			if (actualDataElementsAndValues.values().toString()
					.contentEquals(expectedDataElementsAndValues.values().toString())) {
				status = "Pass";
				actualDataElementsAndValues.values().clear();
			} else {
				status = "Fail";
				actualDataElementsAndValues.values().clear();
			}

			if (status.equalsIgnoreCase("Pass")) {
				if (excel.getAPIData().get(j).getrequestType().equalsIgnoreCase("GET")) {

					extentTest.info(MarkupHelper.createLabel(
							"API data elements validation details || " + "Status Code : " + apidetails.get(0),
							ExtentColor.GREEN));
					extentTest
							.info(MarkupHelper.createLabel(excel.getAPIData().get(j).getendPoint(), ExtentColor.AMBER));
					extentTest.pass(MarkupHelper
							.createLabel(table.toString().substring(1, table.toString().length() - 1), null));
				} else if(excel.getAPIData().get(j).getServiceType().equalsIgnoreCase("SOAP")){
					extentTest.info(MarkupHelper.createLabel(
							"API data elements validation pass, please see details below || " + "Status Code : " + apidetails.get(0),
							ExtentColor.GREEN));
					extentTest
							.info(MarkupHelper.createLabel(excel.getAPIData().get(j).getendPoint(), ExtentColor.AMBER));
					extentTest.pass(MarkupHelper
							.createLabel(table.toString().substring(1, table.toString().length() - 1), null));
				}else {
					extentTest.info(MarkupHelper.createLabel(apidetails.get(0), ExtentColor.GREEN));
					extentTest.info(MarkupHelper.createLabel(
							"API data elements validation details || " + "Status Code : " + apidetails.get(1),
							ExtentColor.GREEN));
					extentTest
							.info(MarkupHelper.createLabel(excel.getAPIData().get(j).getendPoint(), ExtentColor.AMBER));
					extentTest.pass(MarkupHelper
							.createLabel(table.toString().substring(1, table.toString().length() - 1), null));
				}
			} else {
				if (excel.getAPIData().get(j).getrequestType().equalsIgnoreCase("GET")) {
				
				extentTest.info(
						MarkupHelper.createLabel("API data elements validation fail, please see below details || "
								+ "Status Code : " + apidetails.get(0), ExtentColor.RED));
				extentTest.info(MarkupHelper.createLabel(excel.getAPIData().get(j).getendPoint(), ExtentColor.AMBER));
				extentTest.info(MarkupHelper.createCodeBlock(apidetails.get(1), CodeLanguage.JSON));
				extentTest.fail(
						MarkupHelper.createLabel(table.toString().substring(1, table.toString().length() - 1), null));
				}else {
					extentTest.info(MarkupHelper.createLabel(apidetails.get(0), ExtentColor.RED));
					extentTest.info(
							MarkupHelper.createLabel("API data elements validation fail please verify below details || "
									+ "Status Code : " + apidetails.get(1), ExtentColor.RED));
					extentTest.info(MarkupHelper.createLabel(excel.getAPIData().get(j).getendPoint(), ExtentColor.AMBER));
					extentTest.info(MarkupHelper.createCodeBlock(apidetails.get(2), CodeLanguage.JSON));
					extentTest.fail(
							MarkupHelper.createLabel(table.toString().substring(1, table.toString().length() - 1), null));
				}
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
			// System.out.println(expectedDataElementsAndValues.keySet());

			for (String element : expectedDataElementsAndValues.keySet()) {
				actualDataElementsAndValues.put(element,
						JsonPath.read(request, actualDataElementsAndXpath.get(element)));
			}

		}
//		System.out.println(actualDataElementsAndValues.values());
//		System.out.println(expectedDataElementsAndValues.values());
		{

		}
		if (actualDataElementsAndValues.values().toString()
				.contentEquals(expectedDataElementsAndValues.values().toString())) {
			status = "Pass";
			actualDataElementsAndValues.values().clear();
		} else {
			status = "Fail";
			actualDataElementsAndValues.values().clear();
		}

		return status;
	}

}
