<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<form action="mainservlet?mode=UploadMaterial" name="homepage" method="POST" enctype="multipart/form-data">
<table height="100" border="0">
<tr>
<h3>Upload Study Material:</h3><tr><h3>What is the Material about?</h3>
<input type="text" size="50" id="taggedAs" name="taggedAs"></input></tr>
</br>
<br>
<br>
<input type="file" size="50" id="filename" name="filename" value="" >
<tr>
<br>
<input type="submit" value="Upload" name="Upload" id ="Upload"></input>
</table>
</form>
</body>
</html>