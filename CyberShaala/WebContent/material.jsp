<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.amazonaws.*"%>
<%@ page import="com.amazonaws.auth.*"%>
<%@ page import="com.amazonaws.services.ec2.*"%>
<%@ page import="com.amazonaws.services.ec2.model.*"%>
<%@ page import="com.amazonaws.services.s3.*"%>
<%@ page import="com.amazonaws.services.s3.model.*"%>
<%@ page import="com.amazonaws.services.dynamodbv2.*"%>
<%@ page import="com.amazonaws.services.dynamodbv2.model.*"%>
<%@ page import="com.cybershaala.data.model.Materials"%>
<%@ page import="com.cybershaala.data.model.QA"%>
<%@ page import="com.cybershaala.data.model.Comment"%>
<%@ page import="com.cybershaala.util.CyberShaalaConstants"%>
<%@ page import="com.cybershaala.util.CyberShaalaUtilities"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Enumeration"%>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<%
	Hashtable<String, ArrayList<QA>> ansHt = (Hashtable<String, ArrayList<QA>>)request.getAttribute("ansHt");
	Hashtable<String, QA> qsHt = (Hashtable<String, QA>)request.getAttribute("qsHt");
	Materials mat = (Materials) request.getAttribute("materialObj");
	ArrayList<Comment> comList = (ArrayList<Comment>)request.getAttribute("comList");
	String materialUrl = CyberShaalaUtilities.getMatEmbedLink(mat.getMaterialUrl());
	//String userId = (String)request.getSession().getAttribute("userId");
	String origMaterialUrl = mat.getMaterialUrl();
	String score = (String)request.getSession().getAttribute("score");
%>
<script type="text/javascript">
var starVal=3;
function postQuestion(){
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'content' : $("#qId").val(),
			'action' : 'postQuestion',
			'materialUrl' : '<%=origMaterialUrl%>',
			'queId' : $("#lrgstQueId").val(),
		},		
		success : function() {
			alert("Question posted successfully!");
			$(function(){
			document.location.href="/CyberShaala/material?materialURL=<%=materialUrl %>&materialName=<%=mat.getMaterialName()%>&materialDesc=<%=mat.getMaterialDesc()%>&type=<%=mat.getType()%>";
			});
		}
	});
}
function postAnswer(obj){
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'content' : $(obj).closest(".qanda_div").find("#ans_cont").val(),
			'action' : 'postAnswer',
			'materialUrl' : '<%=origMaterialUrl%>',
			'queId' : $(obj).closest(".qanda_div").attr("id"),
			'nextAnsId' : $(obj).closest(".qanda_div").children("#nextAnsId").val(),
		},		
		success : function() {
			alert("Answer posted successfully!");
			$(function(){
				document.location.href="/CyberShaala/material?materialURL=<%=materialUrl %>&materialName=<%=mat.getMaterialName()%>&materialDesc=<%=mat.getMaterialDesc()%>&type=<%=mat.getType()%>";
				});
		}
	});
}

function voteAnsUp(obj){
	var voteNo = $(obj).closest(".answer_div").find("#voteNo").text();	
	voteNo =parseInt(voteNo)+1;
	$(obj).closest(".answer_div").find("#voteNo").text(voteNo);
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'action' : 'voteAnsUp',
			'materialUrl' : '<%=origMaterialUrl%>',
			'queId' : $(obj).closest(".qanda_div").attr("id"),
			'ansId' : $(obj).closest(".answer_div").attr("id"),
		},		
		success : function() {
			alert("Thanks for vote!");
		}
	});
}
function voteAnsDown(obj){
	var voteNo = $(obj).closest(".answer_div").find("#voteNo").text();	
	voteNo = parseInt(voteNo)-1;
	$(obj).closest(".answer_div").find("#voteNo").text(voteNo);
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'action' : 'voteAnsDown',
			'materialUrl' : '<%=origMaterialUrl%>',
			'queId' : $(obj).closest(".qanda_div").attr("id"),
			'ansId' : $(obj).closest(".answer_div").attr("id"),
		},		
		success : function() {
			alert("Thanks for vote!");
			$("#thnq").attr("hidden","false");
		}
	});
}

function voteQueUp(obj){
	var voteNo = $(obj).closest(".question_div").find("#voteNo").text();	
	voteNo =parseInt(voteNo)+1;
	$(obj).closest(".question_div").find("#voteNo").text(voteNo);
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'action' : 'voteQueUp',
			'materialUrl' : '<%=origMaterialUrl%>',
			'queId' : $(obj).closest(".qanda_div").attr("id"),
		},		
		success : function() {
			alert("Thanks for vote!");
			$("#thnq").attr("hidden","false");
		}
	});
}
function voteQueDown(obj){
	var voteNo = $(obj).closest(".question_div").find("#voteNo").text();	
	voteNo = parseInt(voteNo)-1;
	$(obj).closest(".question_div").find("#voteNo").text(voteNo);
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'action' : 'voteQueDown',
			'materialUrl' : '<%=origMaterialUrl%>',
			'queId' : $(obj).closest(".qanda_div").attr("id"),
		},		
		success : function() {
			alert("Thanks for vote!");
			$("#thnq").attr("hidden","false");
		}
	});
}
function getStarVal(idVal){
	starVal = idVal;
}

function checkChkBox(chk){
	if($(chk).is(":checked")){
		$(chk).val("true");
	}
	else{
		$(chk).val("false");
	}
}

function sendFeedback(){	
	//var queryStr = "starVal="+starVal+"&qlty="+$('#qlty').val()+"&eg="+$('#eg').val()+"&undstnd="+$('#undstnd').val()+"&rel="+$('#rel').val()+"&rec="+$('#rec').val();
	//alert(queryStr);
	$.ajax({
		type : 'POST',
		url : '/CyberShaala/material',
		data : {
			'action' : 'sendFeedback',
			'materialUrl' : '<%= origMaterialUrl%>',
			'starVal' : starVal,
			'qlty' : $('#qlty').val(),
			'eg' : $('#eg').val(),
			'undstnd' : $('#undstnd').val(),
			'rel' : $('#rel').val(),
			'rec' : $('#rec').val(),
		},		
		success : function() {
			alert("Thanks for your feedback!");
			$("#thnq").attr("hidden","false");
		}
	});	
}
</script>
<script type='text/javascript' src='https://d4sa5ams81uzt.cloudfront.net/jwplayer.js'></script>
<link rel="stylesheet" type="text/css" href="styles/material.css">
<%@ include file="masterpage.jsp" %>
<div class="left_content_div">
	<div class="material_div">		
			<%				
				if (mat.getType().equalsIgnoreCase("youtube")){%>
				<iframe width="560" height="315" src="<%=materialUrl %>"></iframe>
				<%} else if (mat.getType().equalsIgnoreCase("s3video")){%>
					<div align="center"  id="<%=materialUrl %>"></div>
					<script type="text/javascript">									
						jwplayer('<%=materialUrl%>').setup(
						{
							file : "rtmp://s3kmh9e07uly4d.cloudfront.net/cfx/st/<%= materialUrl.substring(materialUrl.lastIndexOf("/")+1) %>",
							height : "315",
							width : "560"
						});	
					</script>
				<%} else if (mat.getType().equalsIgnoreCase("pdf")){%>
					<object data="<%=materialUrl %>" type="application/pdf" width="100%" height="100%">
					  <p>It appears you don't have a PDF plugin for this browser. 
					  <a href=""<%=materialUrl %>">click here to download the PDF file.</a></p>
					</object>
				<%} else if (mat.getType().equalsIgnoreCase("doc")){%>
					<iframe width="80" height="80" src="<%= materialUrl %>"></iframe>
				<%} 
			%>
			<input id="lrgstQueId" type="hidden" value="<%=request.getAttribute("lrgstQueId") %>" >		
	</div>
	<div style="width:560px;">
		<table>
			<tr>
				<td><b><%=mat.getMaterialName() %></b></td>
			</tr>
			<tr>				
				<td style="font-size: 12px;"><%=mat.getMaterialDesc() %></td>
			</tr>
		</table>
	</div>
	<div style="width:560px;">
		<table>
			<tr>
				<td align="left"><b>Have a question?</b></td>
				<td align="right"><button class="cs_but" onclick="postQuestion();">Post</button></td>
			</tr>
			<tr>
				<td style="width:560px;" colspan="2"><textarea rows="1" cols="60" id="qId" maxlength="250" ></textarea></td>
			</tr>
		</table>
		<p></p> 
	</div>
	
	<div style="width:560px;">
		<%
			Enumeration<String> qsEnum = qsHt.keys();
			while(qsEnum.hasMoreElements()){
				String qId = qsEnum.nextElement();
				QA objQa = qsHt.get(qId);%>
			<div id="<%=objQa.getQuestion() %>" class="qanda_div" >
				<div class="question_div">
					<div style="float:left;">
					<table>
						<tr><td><a onclick="voteQueUp(this);"><img src="images/up_arrow.png"/></a></td></tr>																
						<tr><td align="center"><span id="voteNo"><%=objQa.getVoteUp() %></span></td></tr>																
						<tr><td><a onclick="voteQueDown(this);"><img src="images/down_arrow.png"/></a></td></tr>																
					</table>
					</div>
					<p><%= objQa.getText()%></p>
				</div>
				<div class="post_answer_div">
					<table>
					<tr>
						<td align="left" style="font-size: 12px;">Post your answer</td>
						<td align="right"><button class="cs_but" onclick="postAnswer(this);">Post</button></td>
					</tr>
					<tr>
						<td style="width:560px;" colspan="2"><textarea  rows="1" cols="60" id="ans_cont" maxlength="250" ></textarea></td>
					</tr>
					</table>					
				</div>
				<%
				int nextAnsId = 0;
				ArrayList<QA> qaList = ansHt.get(qId);
				if(qaList!=null){
					for(QA a : qaList){
						if(Integer.parseInt(a.getAnswer()) > nextAnsId){
							nextAnsId = Integer.parseInt(a.getAnswer());
						}%>
						<div id="<%=a.getAnswer() %>" class="answer_div">
							<div style="float:left;" >
							<table>
								<tr><td><a onclick="voteAnsUp(this);" ><img src="images/up_arrow.png"/></a></td></tr>																
								<tr><td align="center"><span id="voteNo"><%=a.getVoteUp() %></span></td></tr>																
								<tr><td><a onclick="voteAnsDown(this);"><img src="images/down_arrow.png"/></a></td></tr>																
							</table>
							</div>
							<p><%= a.getText() %></p>
						</div>
					<%}
				}
				nextAnsId = nextAnsId+1;
				%>
				<input id="nextAnsId" type="hidden" value="<%=nextAnsId %>"/>
			</div>
			<%}%>
	</div>
</div>
<div class="starRate">
		<p>Please give your feedback on the material and help the student community</p>
		
	<table>
		<tr>
			<td colspan="2"><span style="font-size:18pt;">CyberShaala Score: <%=score %></span> </td>
		</tr>
		<tr>
			<td colspan="2">
			<ul>
				<li><a onclick="getStarVal(this.id);" href="#" id="5"><span>Give it 5 stars</span><b></b></a></li>
				<li><a onclick="getStarVal(this.id);" href="#" id="4"><span>Give it 4 stars</span></a></li>
				<li><a onclick="getStarVal(this.id);" href="#" id="3"><span>Give it 3 stars</span></a></li>
				<li><a onclick="getStarVal(this.id);" href="#" id="2"><span>Give it 2 stars</span></a></li>
				<li><a onclick="getStarVal(this.id);" href="#" id="1"><span>Give it 1 star</span></a></li>
			</ul> 
			</td>
		</tr>
		<tr>
			<td style="font-weight: bold;">Question</td>
			<td style="font-weight: bold;">YES/NO</td>
		</tr>
		<tr>
			<td>Quality of the material was good?</td>
			<td><input class="chkBox" type="checkbox" onclick="checkChkBox(this);" id="qlty" value="false"><br></td>
		</tr>
		<tr>
			<td>Covered enough examples?</td>
			<td><input class="chkBox" type="checkbox" onclick="checkChkBox(this);" id="eg" value="false"><br></td>
		</tr>
		<tr>
			<td>Was easy to understand?</td>
			<td><input class="chkBox" type="checkbox" onclick="checkChkBox(this);" id="undstnd" value="false"><br></td>
		</tr>
		<tr>	
			<td>Was it relevant what you were looking?</td>
			<td><input class="chkBox" type="checkbox" onclick="checkChkBox(this);" id="rel" value="false"><br></td>
		</tr>
		<tr>
			<td>Would you recommend it to others?</td>
			<td><input class="chkBox" type="checkbox" onclick="checkChkBox(this);" id="rec" value="false"><br></td>
		</tr>
		<tr>
			<td colspan="2"><button class="cs_but" onclick="sendFeedback();">Send</button></td>
		</tr>
	</table>
</div>