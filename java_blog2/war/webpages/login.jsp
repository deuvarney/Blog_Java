.<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log in Page</title>
</head>
<body>
	<h1 style="text-decoration: underline;">Login Here</h1><br>
	
	<form method="post">
		<label>Username: <input type="text" name="user"></label><br>
		<label>Password: <input type="password" name="passw"></label><br>
		<input type="submit"><br>
	</form>
	
	<div>Don't have an account?: <a href="/signup"><span>Sign up</span></div></a>
</body>
</html>