package com.appspot.deuvarneyjava.services;

import  org.apache.commons.lang3.StringEscapeUtils;
//import com.appspot.deuvarneyjava.services.CommonService;







import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;


/**Handles general web site functions
 * @author dsanderson13
 *
 */
public class CommonService {

	private static String subject;
	private static Text content;
	private static String content2;

	/**Handles escaping html characters.
	 * @param string Unescaped html string.
	 * @return Escaped Html string.
	 */
	public static String escapeHTML(String string){
		string = string.replace("&", "&amp;");
		string = string.replace(">", "&gt;");
		string = string.replace("<", "&lt;");
		//string = string.replace("'", "&#039;");
		//string = string.replace("\"", "&#034;");
		
		return string;
		
	}
	
	public static String returnJson(Entity result){

		getValuesFromEntity(result);
		
		String returnString = String.format("{\"content\":"+ "\"%s\"" + ", "  
				+ "\"subject\":" +'"'+ subject + '"' +
				"}", content2);	
		
		return returnString;
	}
	
	public static String returnJson(Iterable<Entity> result){
		String json ="{\"all_posts\": [";
		
		for(Entity e: result){
			json += returnJson(e) + ",";
		}
		if(json.endsWith(",")){
			json = json.substring(0, json.length()-1);
		}
		json += "]}";
		return json;
		
	}
	
	public static String returnEscapedJson(Entity result){
		return StringEscapeUtils.escapeHtml4(returnJson(result));
		
	}
	
	public static String returnXml(Entity result){

		getValuesFromEntity(result);
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" +
				
					"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">" +
						"<post>"+
							"<content>" +
								content2 +
							"</content>"+
							"<subject>"+
								subject +
							"</subject>"+
						"</post>" +
					"</xs:schema>";
		return xml;
		
	}
	
	private static void getValuesFromEntity(Entity result){
		subject = (String) result.getProperty("subject");
		
		content = (Text) result.getProperty("content");
		content2 = content.getValue();
	}
//	private String escapeHtmlHelper(String string){
//
//		
//		return string;
//		
//	}


}
