package com.rgt.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelUtils 
{
	protected Fillo fillo;
	protected Connection connection;

	public ExcelUtils(String excelpath) {
		try { 
			fillo = new Fillo();
			connection = fillo.getConnection(excelpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<TCMaster> getAPIData()
	{
		List<TCMaster> listTestCases = new ArrayList<TCMaster>();
		try {
			String selectQuery = "select * from TC_Master where ToBeExecuted='Y'";
			Recordset recordset = connection.executeQuery(selectQuery);

			while(recordset.next()) {

				TCMaster data = new TCMaster();
				data.setTC_ID(recordset.getField("TC_ID"));
				data.setAPIName(recordset.getField("API_NAME"));
				data.setToBeExecuted(recordset.getField("ToBeExecuted"));
				data.setdataElements(recordset.getField("DATA_ELEMENTS"));
				data.setExpected(recordset.getField("EXPECTED"));
				data.setrequestType(recordset.getField("REQUEST_TYPE"));
				data.setendPoint(recordset.getField("END_POINT"));
				data.setRequestDataElements(recordset.getField("REQUEST_DATA_ELEMENTS"));
				data.setRequestDataElementsValues(recordset.getField("REQUEST_DATA_ELEMENTS_VALUES"));
				listTestCases.add(data);
//				System.out.println(listTestCases.get(0));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return listTestCases;
	}
	
	
	public List<TestData> getAPIMapping(){
		List<TestData> listTestCaseDetails = new ArrayList<TestData>();
		try {
			String selectQuery = "select * from Mapping";
			Recordset recordset = connection.executeQuery(selectQuery);

			while(recordset.next()) {
				TestData data = new TestData();
				data.setdataElements(recordset.getField("DATA_ELEMENTS"));
				data.setxpath(recordset.getField("XPATH"));
				
				listTestCaseDetails.add(data);
//				System.out.println(listTestCaseDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTestCaseDetails;
	}
	
	public Map<String,String> getAPIMappingData(){
		Map<String,String> listTestCaseDetails = new LinkedHashMap<String,String>();
		try {
			String selectQuery = "select * from Mapping";
			Recordset recordset = connection.executeQuery(selectQuery);

			while(recordset.next()) {
				listTestCaseDetails.put(recordset.getField("DATA_ELEMENTS"), recordset.getField("XPATH"));
				//System.out.println(listTestCaseDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listTestCaseDetails;
	}
	
	



}
