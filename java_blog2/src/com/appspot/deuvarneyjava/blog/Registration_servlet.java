package com.appspot.deuvarneyjava.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;

import com.appspot.deuvarneyjava.services.*;
import com.google.apphosting.api.DatastorePb.DatastoreService;


public class Registration_servlet extends HttpServlet {
	private final String EMPTY_STRING = "";
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Hits DoGET");
		
		
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/signup");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/signup_servlet/");
		request.setAttribute("userName", EMPTY_STRING);
		request.setAttribute("emailAddress", EMPTY_STRING);

		// Error Messages
		request.setAttribute("userNameError", EMPTY_STRING);
		request.setAttribute("passwordErrorText", EMPTY_STRING);
		request.setAttribute("matchPassErrorText", EMPTY_STRING);
		request.setAttribute("invalidEmailText", EMPTY_STRING);
		dispatcher.forward(request, response);

		// super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName, password, pwVerify, emailAddress;
		
		userName = req.getParameter("username");
		password = req.getParameter("password");
		pwVerify = req.getParameter("verify");
		emailAddress = req.getParameter("email");
		System.out.println("Hits DoPost");

		// default error messages
		req.setAttribute("userNameError", EMPTY_STRING);
		req.setAttribute("passwordErrorText", EMPTY_STRING);
		req.setAttribute("matchPassErrorText", EMPTY_STRING);
		req.setAttribute("invalidEmailText", EMPTY_STRING);

//		RequestDispatcher dispatcher = request.getRequestDispatcher("/signup");
		RequestDispatcher dispatcher = req.getRequestDispatcher("/signup_servlet/");
		//RequestDispatcher dispatcher = req.getRequestDispatcher("/signup");
		RegistrationService registration = new RegistrationService();

		// HttpSession session = req.getSession();

		// Flag to check if there are any registration errors
		boolean pageFlag = false;

		if (!registration.verifyUsername(userName)) {
			req.setAttribute("userNameError", "This is not a valid User Name");
			pageFlag = true;
		}

		if (!registration.verifyPassword(password)) {
			req.setAttribute("passwordErrorText",
					"This is not a valid password");
			pageFlag = true;
		}

		if (registration.verifyPassword(password)
				&& !registration.verifyMatchingPasswords(password, pwVerify)) {
			req.setAttribute("matchPassErrorText", "Passwords do not match");
			pageFlag = true;
		}

		if (!registration.verifyEmail(emailAddress)) {
			req.setAttribute("invalidEmailText", "This is not a valid email");
			pageFlag = true;
		}
		
		//Flag to check whether username and email is already registered
		if (!pageFlag) { 
			MyDatastoreService datastore = new MyDatastoreService();

			if (!datastore.confirmNonExistingUser(userName)) {
				req.setAttribute("userNameError", "This User is already Taken");
				pageFlag = true;
			}
			if (!datastore.confirmNonExisitingEmail(emailAddress) && !emailAddress.trim().equals("")) {
				req.setAttribute("invalidEmailText", "This email is already registered");
				pageFlag = true;
			}
		}
		// If flag is true, user will be redirected back to login page with appropriate error messages
		if (pageFlag) {
			// CommonService escapeHtml = new CommonService();
			req.setAttribute("userName", CommonService.escapeHTML(userName));
			req.setAttribute("emailAddress",
					CommonService.escapeHTML(emailAddress));
			// resp.sendRedirect("/signup");

			// System.out.println("Before "+ userName +"\nAfter " +
			// escapeHtml.escapeHTML("userName"));

			dispatcher.forward(req, resp);
		}

		else {

			// resp.sendRedirect("/welcome?username=" +
			// CommonService.escapeHTML(userName));

			MyDatastoreService datastore = new MyDatastoreService();

			try {
				datastore.add_user(userName, password, emailAddress);
				System.out.println("User Added");

			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
