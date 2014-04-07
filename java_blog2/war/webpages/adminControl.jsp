<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Deuvarney Java Admin Panel</title>
</head>
<body>
	<form method="post">
		<label> Require Login for New Posts <input type="checkbox" name="requireNewPostLogin" <%= request.getAttribute("loginNewPostChecked") %>/></label>
		<input type="submit">
	</form>

	<br>
	<form style="border: 6px solid red;">
		<label style ="color: red; font-size:larger;">
		
		Full Site Lock Down<input type="checkbox" id="fullLockDown">
		</label> <input type="submit">
	</form>
</body>
</html>