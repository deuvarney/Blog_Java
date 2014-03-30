package com.appspot.deuvarneyjava.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deuvarneyjava.services.*;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class BlogSinglePost_servlet extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		boolean returnJson = false;
		boolean returnXml = false;
		// String x = req.getRequestURI();
		// System.out.println("Request Url :" + x.toString());

		String postNumber = (String) req.getParameter("post");
		System.out.println(postNumber);// added to capture

		if (postNumber.endsWith(".json")) {
			postNumber = postNumber.replace(".json", "");
			returnJson = true;
		}
		else if (postNumber.endsWith(".xml")){
			postNumber = postNumber.replace(".xml", "");
			returnXml = true;
		}
		

		MyDatastoreService db = new MyDatastoreService();
		Entity result = db.querySinglePost(postNumber);
		

		
		if (returnJson) {
			//subject = subject.substring(beginIndex, endIndex);
			
			
				resp.setContentType("text/json");
				resp.getWriter().println(CommonService.returnJson(result));
//				resp.getWriter().println(
//						//"{\"content\":"+ '"' + content.getValue()+ '"' + ", "  
//						"{\"content\":"+ '"' + content2 + '"' + ", "  
//						+ "\"subject\":" +'"'+ subject + '"' +
//						"}");
		
			return;
		}
		
				
		if(returnXml){
			resp.setContentType("text/xml");
			
			
//				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" +
//			
//					"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">" +
//					"<post>"+
//						"<content>"+
//							//content.getValue()+
//							content2+
//						"</content>"+
//						"<subject>"+
//							subject +
//						"</subject>"+
//					"</post>" +
//					"</xs:schema>";
				
			resp.getWriter().println(CommonService.returnXml(result));
			return;
		}

		String subject = (String) result.getProperty("subject");
		//Text content = (Text) result.getProperty("content");
		Text content = (Text) result.getProperty("content");
		String content2 = content.getValue();//CommonService.escapeHTML(content.getValue());

		req.setAttribute("subject", subject);//(String) result.getProperty("subject"));
		req.setAttribute("content", content2);//(Text) result.getProperty("content"));

		System.out.println("Before sending request");
		req.getRequestDispatcher("/blog/singlepost_page").forward(req, resp);
		System.out.println(postNumber);

		// super.doGet(req, resp);
	}
}
