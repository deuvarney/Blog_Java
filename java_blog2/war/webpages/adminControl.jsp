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
	
	
	<hr>
		<form method="post">
		<label> Require Admin Rights to access this page <input type="checkbox" name="requireAdminRights" <%= request.getAttribute("requireAdminRightsChecked") %>/></label>
		<input type="submit">
	</form>
	<hr>
	<h3>Reset password</h3>
	<form action="">
		<label>Enter password to reset: <input type="text"/></label>
		<input type="submit">//Not yet Implemented
	</form>
	
	<hr>
	<h3>Make a user an Administrator</h3>
	<form action="" method="post" >
		<label>Enter user to be admin: <input name="makeAdmin" type="text"/></label>
		<input type="submit">
	</form>
	
	<hr>	
	<br>
	<form style="border: 6px solid red;">
		<label style ="color: red; font-size:larger;">
		
		Full Site Lock Down<input type="checkbox" id="fullLockDown">
		</label> <input type="submit"> //No functionality yet
	</form>
</body>
</html>