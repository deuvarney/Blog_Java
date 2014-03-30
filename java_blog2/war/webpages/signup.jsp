<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration for Wiki</title>
	<link type="text/css" rel="stylesheet" href="/stylesheets/signup.css">
</head>
<body>
	<h2>Register Here</h2>
	<br>

	<%
		//Repassable Fields
		String userName = (String) request.getAttribute("userName");
		String emailAddress = (String) request.getAttribute("emailAddress");
			//Debug System.out.println("USERNAME ISdd "+ userName);

		//Error Messages
		String userNameError = (String) request.getAttribute("userNameError");
		String passErrorMsg = (String) request.getAttribute("passwordErrorText");
		String matchPassErrorMsg = (String)  request.getAttribute("matchPassErrorText");
		String nonValidEmailMsg = (String) request.getAttribute("invalidEmailText");
	%>

	<form method="post" >
		<label>Username: <input type="text" name="username"
			value="<%=userName%>"/></label> <br>
		<div class="error" id="username"><%=userNameError%></div>
		<label>Password: <input type="password" name="password"></label><br>
		<div class="error" id="password"><%=passErrorMsg%></div>
		<label>Verify Password: <input type="password" name="verify"></label><br>
		<div class="error" id="matchPassword"><%=matchPassErrorMsg%></div>
		<label>Email(Optional): <input type="text" name="email"
			value="<%=emailAddress%>"></label><br>
		<div class="error" id="nonValidEmail"><%=nonValidEmailMsg%></div>
		<input type="submit"><br> <label>Remember Me?<input
			type="checkbox" name="remember"></label><br>
	</form>

	<div>
		Already have an account? <span><a href="/login">Log-in Here</a></span>
	</div>
</body>
</html>