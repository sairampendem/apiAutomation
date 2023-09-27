package com.rgt.utils;

public class TCMaster 
{
	private String TC_ID;
	private String ToBeExecuted;
	private String API_NAME;
	private String DATA_ELEMENTS;
	private String EXPECTED;
	private String REQUEST_TYPE;
	private String END_POINT;


	public String getTC_ID() {
		return TC_ID;
	}
	public void setTC_ID(String tc_ID){
		TC_ID = tc_ID;
	}


	public String getToBeExecuted() {
		return ToBeExecuted;
	}
	public void setToBeExecuted(String toBeExecuted) {
		ToBeExecuted = toBeExecuted;
	}

	
	public String getAPIName()
	{
		return API_NAME;
	}
	
	public void  setAPIName(String apiName)
	{
		API_NAME = apiName;
	}
	
	public String getdataElements()
	{
		return DATA_ELEMENTS;
	}
	
	public void  setdataElements(String dataelements)
	{
		DATA_ELEMENTS = dataelements;	
	}
	
	public String getExpected()
	{
		return EXPECTED;
	}
	
	public void  setExpected(String expected)
	{
		EXPECTED = expected;
	}
	
	public String getrequestType()
	{
		return REQUEST_TYPE;
	}
	
	public void  setrequestType(String requestType)
	{
		REQUEST_TYPE = requestType;
	}
	
	public String getendPoint()
	{
		return END_POINT;
	}
	
	public void  setendPoint(String endPoint)
	{
		END_POINT = endPoint;
	}
	
	public String toString() {
		return "Testdata [TC_ID="+TC_ID+", ToBeExecuted="+ToBeExecuted+", API_NAME="+API_NAME+", DATA_ELEMENTS="+DATA_ELEMENTS+", EXPECTED="+EXPECTED+", REQUEST_TYPE="+REQUEST_TYPE+", END_POINT="+END_POINT+"]";

	}


}
