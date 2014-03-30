<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.appspot.deuvarneyjava.services.MyDatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Blog Home</title>
</head>
<body>

<% Iterable<Entity> all_posts = new MyDatastoreService().queryAllPosts(); %>
	<h1>Blog Home</h1>
	<br>
	<form action="/blog/newpost" method="get">
	<input type="submit" value="Create New Post">
	</form>
	  
	<%for(Entity e: all_posts){ %>
		<a href="/blog/<%=e.getProperty("insert_id")%>"><label style="background-color:aqua;">Subject:<span> <%=e.getProperty("subject") %></span></label></a> 
		<br><br>
		<label style="background-color: yellow;">Content:<span style="white-space: pre;"><%=((Text)e.getProperty("content")).getValue()%></span></label> 
		<hr>
	<%};%> 
</body>
</html>