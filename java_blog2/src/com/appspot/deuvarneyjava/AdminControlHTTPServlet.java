package com.appspot.deuvarneyjava;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deuvarneyjava.services.LoginService;

public class AdminControlHTTPServlet extends HttpServlet {

	public AdminControlHTTPServlet() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie[] cookies = req.getCookies();
		String userName = null;
		System.out.println("1");
		Cookie userCookie = null;
		System.out.println("2");
		for (Cookie c : cookies) {
			if (c.getName().equals("userCookie")) {
				userCookie = c;
				break;
			}
		}
		System.out.println("3");
		
		if (userCookie != null) {
			userName = new LoginService().authenticateUserCookie(userCookie
					.getValue());
		}
		System.out.println("4");
		if (userCookie != null && userName != null) {
			
		}
		else{
			resp.sendRedirect("/signup");
		}
	
	
	} 

}
