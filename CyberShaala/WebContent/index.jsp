<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login here</title>
</head>
<body>
<form action="mainservlet?mode=Login" method="POST" name="index">
<table border="0" width="30%" >
                <thead>
                    <tr>
                       <th colspan="2"><font style="color:black; font-size:15pt; ">Login As</font></th>
                    </tr>
                </thead>
                <tbody>
				<tr><td><input type="text" name="user" size="50" maxlength="50"></input></td></tr>
				<tr><td><input type="text" name="password" size="50" maxlength="50"></input></td></tr>
				<tr><td><input type="submit" name="loginsubmit" id="loginsubmit" value="Login"/></td></tr>
				<tr height="20"></tr>
				<tr></tr>
   				<tr><td colspan="2">New User!! Register Here <a href="register.jsp">Register Here</a></td></tr>
</tbody>
</table>
</form>
</body>
</html>