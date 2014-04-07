package com.appspot.deuvarneyjava.blog;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.appspot.deuvarneyjava.AdminControlHTTPServlet;
import com.appspot.deuvarneyjava.services.*;

public class NewPost_servlet extends AdminControlHTTPServlet {

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(new MyDatastoreService().queryRequireLoginNewPost()){		
			System.out.println("New Post Requires Login");
			super.doGet(req, resp);
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/blog/newpost_page");
		
		req.setAttribute("subject", "");
		req.setAttribute("content", "");
		req.setAttribute("subjectError", "");
		req.setAttribute("contentError", "");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String subjectError = "Please Enter a valid subject";
		String contentError = "Please Enter valid content";
		
		//req.setAttribute("subject", "");
		//req.setAttribute("content", "");
		req.setAttribute("subjectError", "");
		req.setAttribute("contentError", "");
		
		
		boolean errorFlag = false;
		NewPostService verify = new NewPostService();
		
		if(!verify.verifySubject(subject)){
			errorFlag = true;
			req.setAttribute("subjectError", subjectError);
			
		}
		if(!verify.verifyContent(content)){
			errorFlag = true;
			req.setAttribute("contentError", contentError);
		}
		
		if(errorFlag){
			
			req.setAttribute("subject", subject);
			req.setAttribute("content", content);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/blog/newpost_page");
			dispatcher.forward(req, resp);
			return;
		}
		
		MyDatastoreService db = new MyDatastoreService();
		String currentId = db.getNextAvailableInsertId();
		db.add_post(subject, content);
				
		resp.sendRedirect("/blog/" + currentId);
		
		
		
		
		
	}
}
