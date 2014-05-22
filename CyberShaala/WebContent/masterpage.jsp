<html>
	<head>
		<title>CyberShaala</title>
		<link rel="stylesheet" type="text/css" href="styles/application.css">
		<script type="text/javascript">
			function search()
			{
				var searchTxt = document.getElementById("searchTxt").value;
				document.location.href="/CyberShaala/mainservlet?mode=SearchMaterial&searchTxt="+searchTxt;
			}
	</script>
	</head>
	<body>

	<div style="text-align: left;">
		<table class="navtable">
			<tr>
				<td class="logotd">
					<a href="">
						<img src="images/logo.jpg">
					</a>
				</td>
				<td style="text-align:left; vertical-align: center;border: none;">
					<table style="border: none; width: 580px; height:100%;">
						<tr>
							<td class="navlinkstd" style="padding-left: 10px;">
								<a href="mainservlet?mode=Login">Home</a>
							</td>
							<td class="navlinkstd">	
								<a href="upload.jsp">Upload</a>
							</td>
							<td class="search-text">	
								<input style="width:320px;color:#000000;" type="text" name="searchTxt" id="searchTxt"/>
							</td>
							<td class="navlinkstd">
							<a href="javascript:search();">Search</a>
							</td>		
						</tr>
					</table>
				</td>
				<td style="text-align:left; vertical-align: center;border: none;">	
					<table style="width:180px;">
						<tr>
							<td class="sesslinkstd"><font color="white"><i>Welcome <%= session.getAttribute("UserId")%></i></font>
							</td>
							<td class="sesslinkstd">
								<a href="mainservlet?mode=displayProfile">profile</a>
							</td>
							<td class="sesslinkstd">	
								<a href="/log_out">Logout</a>
							</td>				
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div>
		<div class="left-panel-div">
			<table style="width:100%;">
				<tr>
					<td class="leftpaneltd"><a href="check_out_later.jsp">Check-out Later</a></td>
				</tr>
				<tr>
					<td class="leftpaneltd"><a href="user_history.jsp">History</a></td>
				</tr>
				<tr>
					<td class="leftpaneltd"><a href="notifications.jsp">Notifications</a></td>
				</tr>
			</table>
		</div>
		<div class="">
		
		</div>
	</div>
	
	</body>
</html>