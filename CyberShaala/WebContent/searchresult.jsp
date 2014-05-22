<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>

<script type='text/javascript'
	src='https://d1mhrncrfklq8e.cloudfront.net/jwplayer.js'></script>
<%@ include file="masterpage.jsp" %>

 <script type="text/javascript">
	function getMaterial(materialURL,materialName,materialDesc,type)
	{
		document.location.href="/CyberShaala/material?materialURL="+materialURL+"&materialName="+materialName+"&materialDesc="+materialDesc+"&type="+type;
	}
	
	function search()
	{
		var searchTxt = document.getElementById("searchTxt").value;
		document.location.href="/CyberShaala/mainservlet?mode=SearchMaterial&searchTxt="+searchTxt;
	}
	</script>
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
<table  align="center" border="0"><tr align=left><td style="font-size: 20px;"><p><b>Search Results</b></p></td></tr>
<%for(int i=0;i<mats3vidList.size();i++){ %>
	 <tr><td style="width: 320px;"><p><b><%=mats3vidnamesList.get(i)%></b></p>
		<video width="320" height="240" controls="controls">
		<source src="<%=mats3vidnamesList.get(i)%>">

		</video>
	    </td><td style="font-size: 12px;"><p><%=mats3viddescList.get(i)%></p>
	<input class="cs_but" style="width:90px;" type="button" value="Go to Material" name="<%=mats3vidList.get(i)%>" onclick="getMaterial('<%= mats3vidList.get(i)%>','<%=mats3vidnamesList.get(i)%>','<%=mats3viddescList.get(i)%>','s3video');"></input></td>
	</tr>
	
	<%}
    for(int i=0;i<matyoutubeList.size();i++){
    %>
    <tr><td style="width: 320px;"><p><b><%=matyoutubenamesList.get(i)%></b></p>
      <iframe title="YouTube video player" class="youtube-player" width="320" height="200" 
	  src="<%=matyoutubeList.get(i)%>" frameborder="0" allowFullScreen></iframe>
	</td><td style="font-size: 12px;"><p><%=matyoutubedescList.get(i)%></p>
	<input class="cs_but" style="width:90px;" type="button" value="Go to Material" name="<%=matyoutubeList.get(i)%>" onclick="getMaterial('<%= matyoutubeList.get(i)%>','<%=matyoutubenamesList.get(i)%>','<%=matyoutubedescList.get(i)%>','youtube');"></input></td>
	</tr>
	<%}  for(int i=0;i<mats3pdfList.size();i++){ %>
	 <tr><td><p><b><%=mats3pdfnamesList.get(i)%></b></p>
	 <p><%=mats3pdfList.get(i)%></p>
		<a href="<%=mats3pdfList.get(i)%>" ><%=mats3pdfnamesList.get(i)%></a>
	    </td><td style="font-size: 12px;"><p><%=mats3pdfdescList.get(i)%></p>
	  	<input class="cs_but" style="width:90px;" type="button" value="Go to Material" name="<%=mats3pdfList.get(i)%>" onclick="getMaterial('<%= mats3pdfList.get(i)%>','<%=mats3pdfnamesList.get(i)%>','<%=mats3pdfdescList.get(i)%>','pdf');"></input></td>
	</tr>
	<%} %>

</table>
</form>