package com.rgt.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestData 
{
	
	private String DATA_ELEMENTS;
	private String XPATH;



	public String getdataElements() {
		return DATA_ELEMENTS;
	}

	public void setdataElements(String dataelements) {
		DATA_ELEMENTS = dataelements;
	}

	public String getxpath() {
		return XPATH;
	}

	public void setxpath(String xpath) {
		XPATH = xpath;
	}


//	public String toString() {
//		return "Testdata [DATA_ELEMENTS="+DATA_ELEMENTS+", XPATH="+XPATH+"]";
//
//	}

//	public static Map<String, String> actualDataElements() {
//
//		int mappingcount = excel.getAPIMapping().size();
//		// System.out.println(mappingcount);
//		for (int i = 0; i < mappingcount; i++) {
//			actualDataElementsXpath = CommonUtils.getActualDataElementsXpath(
//					excel.getAPIMapping().get(i).getdataElements(), excel.getAPIMapping().get(i).getxpath());
//			// System.out.println(actualDataElementsXpath);
//		}
//		Set set = actualDataElementsXpath.entrySet();// Converting to Set so that we can traverse
//		Iterator itr = set.iterator();
//		while (itr.hasNext()) {
//			// Converting to Map.Entry so that we can get key and value separately
//			Map.Entry entry = (Map.Entry) itr.next();
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
//		// System.out.println(actualDataElementsXpath.size());
//		return actualDataElementsXpath;
//	}
//
//	public static Map<String, String> expectedDataElements() {
//		Map<String, String> expDataElementsAndValues = null;
//		int count = excel.getAPIData().size();
//		for (int i = 0; i < count; i++) {
//			expDataElementsAndValues = CommonUtils.getExpectedDataElementsAndValues(
//					excel.getAPIData().get(i).getdataElements(), excel.getAPIData().get(i).getExpected());
//		}
//
//		return expDataElementsAndValues;
//	}

}
