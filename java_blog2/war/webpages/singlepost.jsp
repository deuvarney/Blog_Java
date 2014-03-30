<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="com.google.appengine.api.datastore.Text" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Single Post View</title>
</head>
<body>
	<%
		String subject = (String) request.getAttribute("subject");
		String content = (String) request.getAttribute("content");
	%>

	<a href="/blog"><h2 class="subject"><%=subject%></h2></a>
	<br>
	<form action="/blog/newpost">
	<input type="submit" value="Create New Post"></form>
	<hr>
	<div class="content" style="white-space: pre;"><%=content%></div>
</body>
</html>