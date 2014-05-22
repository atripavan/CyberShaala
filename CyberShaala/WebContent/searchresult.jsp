<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
  	<script type="text/javascript">
	function getMaterial(materialURL,materialName,materialDesc,type)
	{
		document.location.href="/CyberShaala/mainservlet?mode=DisplayMaterial&materialURL="+materialURL"&materialName="+materialName"&materialDesc="+materialdesc"&type="+type;
	}
	
	function search()
	{
		var searchTxt = document.getElementById("searchTxt").value;
		document.location.href="/CyberShaala/mainservlet?mode=SearchMaterial&searchTxt="+searchTxt;
	}
	</script>
	<script type='text/javascript'
	src='https://d1mhrncrfklq8e.cloudfront.net/jwplayer.js'></script>
</head>
<body>
<table width="1200">
   <tr valign="top"></tr>
      <tr>
          <jsp:include page="masterpage.jsp"/>
      </tr>
</table>
<form action="mainservlet?mode=SearchMaterial" name="homepage" method="POST">
<%com.cybershaala.vo.MaterialsVO materialdetails = (com.cybershaala.vo.MaterialsVO)request.getAttribute("searchMaterials");
	List<String> matyoutubenamesList = (List<String>)materialdetails.getMatyoutubeNamesList();
	List<String> matyoutubeList = (List<String>)materialdetails.getMatyoutubevidList();
	List<String> matyoutubedescList = (List<String>)materialdetails.getMatyoutubeDescList();
	List<String> mats3vidnamesList = (List<String>)materialdetails.getMats3vidNamesList();
	List<String> mats3vidList = (List<String>)materialdetails.getMats3vidList();
	List<String> mats3viddescList = (List<String>)materialdetails.getMats3vidDescList();
	List<String> mats3pdfnamesList = (List<String>)materialdetails.getMats3pdfNamesList();
	List<String> mats3pdfList = (List<String>)materialdetails.getMats3pdfList();
	List<String> mats3pdfdescList = (List<String>)materialdetails.getMats3pdfDescList(); %>
<table  align="center" border="0"><tr align="center"><td><p>Relevant Materials</p></td></tr>
<%for(int i=0;i<mats3vidList.size();i++){ %>
	 <tr><td><p><%= i+1 %>&nbsp;&nbsp;<%=mats3vidnamesList.get(i)%></p>
		<video width="320" height="240" controls="controls">
		<source src="<%=mats3vidnamesList.get(i)%>">

		</video>
	    </td><td><p><%=mats3viddescList.get(i)%></p>
	<input type="button" value="Go to Material" name="<%=mats3vidList.get(i)%>" onclick="getMaterial('<%= mats3vidList.get(i)%>','<%=mats3vidnamesList.get(i)%>','<%=mats3viddescList.get(i)%>','s3video');"></input></td>
	</tr>
	</br>
	<%}
    for(int i=0;i<matyoutubeList.size();i++){
    %>
    <tr><td><p><%= i+1 %>&nbsp;&nbsp;<%=matyoutubenamesList.get(i)%></p>
      <iframe title="YouTube video player" class="youtube-player" width="320" height="200" 
	  src="<%=matyoutubeList.get(i)%>" frameborder="0" allowFullScreen></iframe>
	</td><td><p><%=matyoutubedescList.get(i)%></p>
	<input type="button" value="Go to Material" name="<%=matyoutubeList.get(i)%>" onclick="getMaterial('<%= matyoutubeList.get(i)%>','<%=matyoutubenamesList.get(i)%>','<%=matyoutubedescList.get(i)%>','youtube');"></input></td>
	</tr>
	</br>
	<%}  for(int i=0;i<mats3pdfList.size();i++){ %>
	 <tr><td><p><%= i+1 %>&nbsp;&nbsp;<%=mats3pdfnamesList.get(i)%></p>
	 <p><%=mats3pdfList.get(i)%></p>
		<a href="<%=mats3pdfList.get(i)%>" ><%=mats3pdfnamesList.get(i)%></a>
	    </td><td><p><%=mats3pdfdescList.get(i)%></p>
	  	<input type="button" value="Go to Material" name="<%=mats3pdfList.get(i)%>" onclick="getMaterial('<%= mats3pdfList.get(i)%>','<%=mats3pdfnamesList.get(i)%>','<%=mats3pdfdescList.get(i)%>','pdf');"></input></td>
	</tr>
	</br>
	<%} %>

</table>
</form>
</body>
</html>