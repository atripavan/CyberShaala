<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
  	<script type="text/javascript">
	function search()
	{
		var searchTxt = document.getElementById("searchTxt").value;
		document.location.href="/CyberShaala/mainservlet?mode=SearchMaterial&searchTxt="+searchTxt;
	}
	</script>
</head>
<body>
<table width="1200">
   <tr valign="top"></tr>
      <tr>
          <jsp:include page="masterpage.jsp"/>
      </tr>
</table>
</br>
</br>
</br>
<form action="mainservlet?mode=updateUser" name="homepage" method="POST">
<%com.cybershaala.vo.UserVO userdetails = (com.cybershaala.vo.UserVO)request.getAttribute("userdetails");%>
<table align="center" height="100" border="0"><tr align="center">
		<tr align="left"><td><p>User ID</p></td><td><input type="text" id="UserID" name="UserID" size="50" maxlength="100" value="<%=userdetails.getUserId()%>" readonly></input></td></tr>
		<tr align="left"><td><p>Join Date</p></td><td><input type="text" id="JoinDate" name="JoinDate" size="50" maxlength="100" value="<%=userdetails.getJoinDate()%>" readonly></input></td></tr>
		<tr align="left"><td><p>Reputation</p></td><td><input type="text" id="Reputation" name="Reputation" size="50" maxlength="100" value="<%=userdetails.getReputation()%>" readonly></input></td></tr>
		<tr align="left"><td><p>Primary Email ID</p></td><td><input type="text" id="EmailID" name="EmailID" size="50" maxlength="100" value="<%=userdetails.getEmailID()%>"></input></td></tr>
		<tr align="left"><td><p>First Name</p></td><td><input type="text" id="FirstName" name="FirstName" size="50" maxlength="100" value="<%=userdetails.getFirstName()%>"></input></td></tr>
		<tr align="left"><td><p>Last Name</p></td><td><input type="text" id="LastName" name="LastName" size="50" maxlength="100" value="<%=userdetails.getLastName()%>"></input></td></tr>
		<tr align="left"><td><p>Address</p></td><td><input type="text" id="Address" name="Address" size="50" maxlength="100" value="<%=userdetails.getAddress()%>"></input></td></tr>
		<tr align="left"><td><p>Phone Number</p></td><td><input type="text" id="PhoneNumber" name="PhoneNumber" size="50" maxlength="100" value="<%=userdetails.getPhoneNumber()%>"></input></td></tr>
		<tr align="left"><td><p>FaceBook Link</p></td><td><input type="text" id="FBLink" name="FBLink" size="50" maxlength="100" value="<%=userdetails.getFBLink()%>"></input></td></tr>
		<tr align="left"><td><p>LinkedIn Profile</p></td><td><input type="text" id="LinkedIn" name="LinkedIn" size="50" maxlength="100" value="<%=userdetails.getLinkedInLink()%>"></input></td></tr>
		<tr align="left"><td><p>GitHub Link</p></td><td><input type="text" id="GitHubLink" name="GitHubLink" size="50" maxlength="100" value="<%=userdetails.getGitHubLink()%>"></input></td></tr>
		<tr align="left"><td><p>Interests</p></td></tr>
		<tr align="left"><td><input type="checkbox" name="interests" value="Engineering">Engineering</td><td><input type="checkbox" name="interests" value="Social">Social</td></tr>
        <tr align="left"><td><input type="checkbox" name="interests" value="Literature">Literature</td><td><input type="checkbox" name="interests" value="Cloud">Cloud</td></tr>
        <tr align="left"><td><input type="checkbox" name="interests" value="Hadoop">Hadoop</td><td><input type="checkbox" name="interests" value="Dijkstra">Dijkstra</td>
		<tr height="30"></tr>
		
	<tr align="center"><td align="left"><input type="submit" value="Update" name="Update" id ="Update"></input></td><td align="left">
		<input type="button" name="Cancel" value="Cancel" onclick="location.href='<%= request.getContextPath() %>/mainservlet?mode=displayProfile';"></input>
	</tr>


</table>
</form>
</body>
</html>