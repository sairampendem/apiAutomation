package com.rgt.engine;

public class APIResponseData 
{
	private String statusCode;
	private String response;
	
	public String getStatusCode()
	{
		return statusCode;
	}
	public void setStatusCode(String statusCode)
	{
		this.statusCode = statusCode;
	}
	
	public String getResponse()
	{
		return response;
	}
	public void setResponse(String response)
	{
		this.response = response;
	}
	
	@Override
	public String toString()
	{
		return "APIResponseData [Rresponse=" +response+ ", statusCode = "+statusCode+"]";
	}


}
