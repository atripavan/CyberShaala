<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Registration</title>
    </head>
    <body>
        <form action="mainservlet?mode=UserRegister" name="RegisterPage" method="POST">
            <center>
            <table border="0" align="center" width="30%" cellpadding="5">
                <thead>
                    <tr>
                        <th colspan="2">Enter Information Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="fname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="email" value="" /></td>
                    </tr>
                    <tr>
                        <td>Phone Number</td>
                        <td><input type="text" name="phonenumber" value="" /></td>
                    </tr>
                    <tr>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uname" value="" /></td>
                    </tr>
                    <tr align="left"><td><p>Interests</p></td></tr>
                    <%com.cybershaala.vo.MaterialsVO materialdetails = new com.cybershaala.vo.MaterialsVO();
						List<String> materialsList = (List<String>)materialdetails.MaterialList();
						for(int i=0;i<materialsList.size();i++){ %>
					<tr align="left"><td><input type="checkbox" name="interests" value="<%=materialsList.get(i)%>"><%=materialsList.get(i)%></td></tr>
					<%}%>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>

                </tbody>
            </table>
            </center>
        </form>
    </body>
</html>
</body>
</html>