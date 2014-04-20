package com.appspot.deuvarneyjava;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deuvarneyjava.services.LoginService;

public class AdminControlHTTPServlet extends HttpServlet {
	protected boolean adminAccess;
	private LoginService loginService;


	public AdminControlHTTPServlet() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie[] cookies = req.getCookies();
		String userName = null;
		Cookie userCookie = null;
		for (Cookie c : cookies) {
			if (c.getName().equals("userCookie")) {
				userCookie = c;
				break;
			}
		}


		if (userCookie != null) {
			loginService = new LoginService();
			userName = loginService.authenticateUserCookie(userCookie
					.getValue());
		}
		
		if (userCookie != null && userName != null) {
			if(adminAccess){
				if(loginService.isAdministrator()){
					//Do nothing-Allows user to continue
				}
				else{
					resp.sendRedirect("/login");
					return;
				}
			}
		//Do nothing-Allows user to continue
		} else {
			resp.sendRedirect("/login");
			return;
		}

	} 
	
	public void requireAdminAccess(boolean adminAccess) {
		this.adminAccess = adminAccess;
	}

}
