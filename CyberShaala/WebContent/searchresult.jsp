<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
   
   <script type="text/javascript">
	function getConvList(sel_material)
	{
		document.location.href="/mainservlet?mode=DisplayMaterial&sel_material="+sel_material;
	}
	
	function search()
	{
		var searchTxt = document.getElementById("searchTxt").value;
		document.location.href="/CyberShaala/mainservlet?mode=SearchMaterial&searchTxt="+searchTxt;
	}
	</script>
</head>
<body>
<form action="mainservlet?mode=UploadMaterial" name="homepage" method="POST" enctype="multipart/form-data">
<table height="100" border="0">
<tr>
<input type="text" id="searchTxt" name="searchTxt" size="100" maxlength="100" ></input>
</tr>
<tr>
<input type="button" name="searchbutton" value="Search" onclick="search();"></input>
</tr>
 
<%List<String> searchMaterialsList = (List<String>)request.getAttribute("searchMaterialsList");
    for(int i=0;i<searchMaterialsList.size();i++)
    {
    %>
  	<div>
  	<p>Relevant Study Material <%= i+1 %></p>
  	</br>

	  <iframe title="YouTube video player" class="youtube-player" type="text/html" width="640" height="390" 
	  src="<%=searchMaterialsList.get(i)%>" frameborder="0" allowFullScreen></iframe>
	  <input type="button" value="Go to Material" name="<%=searchMaterialsList.get(i)%>" onclick="getConvList('<%= searchMaterialsList.get(i)%>');"></input>
	</div>
	</br>
<%}%>

<tr>
<h3>Upload a Study Material:</h3><input type="file" size="50" id="filename" name="filename" value="" >
<tr>
<br>
<input type="submit" value="Upload Material" name="Upload" id ="Upload"></input>

</table>
</form>
</body>
</html>