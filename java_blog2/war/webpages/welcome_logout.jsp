<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length()
			- request.getRequestURI().length())
			+ request.getContextPath() + "/";
	String redirectURL = baseURL + "blog";
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" language="javascript">
	function goToBlog(){
		window.location.replace("<%=redirectURL%>");
	}
	window.setTimeout(function() {
		goToBlog()
	}, 3000);
</script>
<title>User Signed Out</title>
</head>
<body>
	<h2>You are now signed out. Redirecting Back To Blog....</h2>
</body>
</html>