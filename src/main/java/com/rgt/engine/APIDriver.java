package com.rgt.engine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.rgt.utils.APIConstants;

public class APIDriver {
		
	public static List<String> PostsAPI(String endPoint, String userId, String id, String title)
	{
		List<String> postsApidetails =APIRequestHandler.postRequest(endPoint, SampleAPI.readFileAsString(getPayloadPath())
				.replace(APIConstants.USERID, userId)
				.replace(APIConstants.ID, id)
				.replace(APIConstants.TITLE, title), "");
		System.out.println(postsApidetails);
		
		return postsApidetails;
	}
	
	public static List<String> PostsGetAPI(String endPoint)
	{
		List<String> postsGetApidetails =APIRequestHandler.getRequest(endPoint);
		return postsGetApidetails;
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

}
