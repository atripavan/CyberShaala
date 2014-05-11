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
<form action="mainservlet?mode=SearchMaterial" name="homepage" method="POST" enctype="multipart/form-data">
<%com.servlets.UserVO userdetails = (com.servlets.UserVO)request.getAttribute("userdetails");%>
<table height="100" border="0"><tr align="center">
		<input type="text" id="UserID" name="UserID" size="50" maxlength="100" value="<%=userdetails.getUserId()%>" readonly></input>
		<input type="text" id="JoinDate" name="JoinDate" size="50" maxlength="100" value="<%=userdetails.getJoinDate()%>" readonly></input>
		<input type="text" id="Reputation" name="Reputation" size="50" maxlength="100" value="<%=userdetails.getReputation()%>" readonly></input>
		<input type="text" id="EmailID" name="EmailID" size="50" maxlength="100" value="<%=userdetails.getEmailID()%>"></input>
		<input type="text" id="FirstName" name="FirstName" size="50" maxlength="100" value="<%=userdetails.getFirstName()%>"></input>
		<input type="text" id="LastName" name="LastName" size="50" maxlength="100" value="<%=userdetails.getLastName()%>"></input>
		<input type="text" id="Address" name="Address" size="50" maxlength="100" value="<%=userdetails.getAddress()%>"></input>
		<input type="text" id="PhoneNumber" name="PhoneNumber" size="50" maxlength="100" value="<%=userdetails.getPhoneNumber()%>"></input>
		<input type="text" id="FBLink" name="FBLink" size="50" maxlength="100" value="<%=userdetails.getFBLink()%>"></input>
		<input type="text" id="LinkedIn" name="LinkedIn" size="50" maxlength="100" value="<%=userdetails.getLinkedInLink()%>"></input>
		<input type="text" id="GitHubLink" name="GitHubLink" size="50" maxlength="100" value="<%=userdetails.getGitHubLink()%>"></input>
		<input type="text" id="Interests" name="Interests" size="50" maxlength="100" value="<%=userdetails.getInterests()%>"></input>

	<tr align="center">
		<input type="submit" value="Update" name="Update" id ="Update"></input>
	</tr>
	</tr><tr align="center">
		<input type="button" name="Cancel" value="Cancel" onclick="search();"></input>
	</tr>


</table>
</form>
</body>
</html>