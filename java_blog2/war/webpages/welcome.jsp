<%@page import="com.appspot.deuvarneyjava.services.LoginService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>
</head>
<body>
	<%
		//String userName = request.getParameter("username"); 
		Cookie[] cookies = request.getCookies();
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
	%>
	Welcome Home!
	<%
		} else {
	%>
	Sorry, you are not Authenticated
	<%
		}
		;
	%>

	<!--  Welcome, -->

</body>
</html>