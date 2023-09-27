package com.rgt.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.rgt.utils.APIConstants;

public class SampleAPI {

//	public static final String REQUEST_PATH = "./src/main/java/com/ebay/cs/casemanagementservice/apirequests/%s.txt";
	public static final String REQUEST_PATH = "C:\\Users\\RoopaVenkataSaiRamPe\\Downloads\\testingFramework-main\\src\\main\\java\\com\\rgt\\api\\request\\post.txt";


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

	public static String playload() 
	{
		String payload = readFileAsString(getPayloadPath()).replace ("", "").replace("APIConstants.BUYERID_PLACEHOLDER", "");
		return payload;
	}

	public static void main(String[] args) 
	{


	}

}
