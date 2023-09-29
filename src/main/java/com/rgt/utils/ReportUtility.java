package com.rgt.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ReportUtility 
{
	static ExtentReports extentreport;
	static ExtentSparkReporter spark;
	static ExtentTest extentTest;

	public static ExtentReports testReport(String ExtentReport_Path)
	{
		extentreport = new ExtentReports();
		spark = new ExtentSparkReporter(ExtentReport_Path).viewConfigurer().viewOrder().as(new ViewName[] {
				ViewName.TEST, ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.DEVICE, ViewName.EXCEPTION }).apply();
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setDocumentTitle("API Test Report");
		spark.config().setReportName("API Automation Test Report");
		spark.config().setProtocol(Protocol.HTTPS);
		extentreport.setSystemInfo("Organization", "Ratna Global Technologies Pvt Ltd");
		extentreport.setSystemInfo("QE Name", "HariKrishna");
		extentreport.attachReporter(spark);
		extentreport.setSystemInfo("Environment", "QA");
		extentreport.setSystemInfo("Emil ID", "hari.parvatam@ratnaglobaltech.com");
		extentreport.setSystemInfo("OS", System.getProperty("os.name"));
		extentreport.setSystemInfo("Java Version", System.getProperty("java.version"));

		return extentreport;
	}


}
