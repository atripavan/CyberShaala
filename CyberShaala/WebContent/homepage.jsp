<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
  	<script type="text/javascript">
	function getMaterial(materialURL,materialName,materialDesc)
	{
		//var searchTxt = document.getElementById("searchTxt").value;
		document.location.href="/CyberShaala/mainservlet?mode=displayMaterialPage&materialURL="+materialURL"&materialName="+materialName"&materialDesc="+materialdesc;
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
<form action="mainservlet?mode=SearchMaterial" name="homepage" method="POST">
<%com.cybershaala.vo.MaterialsVO materialdetails = (com.cybershaala.vo.MaterialsVO)request.getAttribute("materialdetails");
	List<String> materialsnamesList = (List<String>)materialdetails.getMaterialNamesList();
	List<String> materialsList = (List<String>)materialdetails.getMaterialLinkList();
	List<String> materialsdescList = (List<String>)materialdetails.getMaterialDescList();%>
		<div align="center"> <input type="text" id="searchTxt" name="searchTxt" size="50" maxlength="100" ></input>
		<input type="button" name="searchbutton" value="Search" onclick="search();"></input></div>
		<div align="center"><p>Relevant Study Materials</p></div>
<table  align="center" height="100" width="600" border="0">
	<%
    for(int i=0;i<materialsList.size();i++)
    {
    %>
    <tr><td> 
  	<p><%= i+1 %>&nbsp;&nbsp;<%=materialsnamesList.get(i)%></p>
  	
	
	  <iframe title="YouTube video player" class="youtube-player" type="text/html" width="320" height="200" 
	  src="<%=materialsList.get(i)%>" frameborder="0" allowFullScreen></iframe>
	  
	</td><td><p><%=materialsdescList.get(i)%></p>
	<input type="button" value="Go to Material" name="<%=materialsList.get(i)%>" onclick="getMaterial('<%= materialsList.get(i)%>','<%=materialsnamesList.get(i)%>','<%=materialsdescList.get(i)%>');"></input></td>
	</tr>
	</br>
<%}%>


</table>
</form>
</body>
</html>