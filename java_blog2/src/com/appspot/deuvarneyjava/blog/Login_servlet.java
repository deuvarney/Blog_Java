package com.appspot.deuvarneyjava.blog;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deuvarneyjava.services.*;

@SuppressWarnings("serial")
public class Login_servlet extends HttpServlet {
	
	private String fromUrl;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
		
			req.getRequestDispatcher("/login_page").forward(req, resp);
			//super.doGet(req, resp);
			System.out.println("Parameter Names " + req.getParameter("from"));
			fromUrl = req.getParameter("from");
		}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = req.getParameter("user");
		String password = req.getParameter("passw");
		LoginService result = new LoginService();
		
		UserInfo userInfo = result.authenticate_user(username, password);
		
		if(userInfo != null){
			//resp.setContentType("text/plain");
			//resp.getWriter().println("Welcome " + userInfo.getUserName());
			
			resp.addCookie(new Cookie("userCookie", userInfo.getUserCookie()));
			//RequestDispatcher dispatcher = req.getRequestDispatcher("/welcome");
			//dispatcher.forward(req, resp);
			
			if(fromUrl != null){
				System.out.println("Variable" + fromUrl);
				resp.sendRedirect(fromUrl);
			}
			else{
				resp.sendRedirect("/welcome");
			}
		}
		else{
			resp.setContentType("text/plain");
			resp.getWriter().println(username + ": Does not exist");
		}
	}
}
