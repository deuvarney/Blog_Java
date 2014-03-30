<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Post</title>
<link type="text/css" rel="stylesheet" href="/stylesheets/signup.css">
</head>
<body>
<h1>New Post</h1>
<br>
<hr>
<br>
<% String subject = (String) request.getAttribute("subject");
	String content = (String) request.getAttribute("content");
	
	String subjectError = (String) request.getAttribute("subjectError");
	String contentError = (String) request.getAttribute("contentError");
%>


<form method="post">
	<label>Subject: <input type="text" name="subject" value="<%=subject%>"></label>
	<div class="error" id="subject"><%=subjectError%></div>
	<br>
	<br>
	<label>Content: <textarea rows="10" cols="40" name="content"><%=content%></textarea></label>
	<div class="error" id="content"><%=contentError%></div>
	<br>
	<input type="submit">
	</form>
</body>
</html>