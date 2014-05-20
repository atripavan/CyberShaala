package com.cybershaala.servlets;

import com.amazonaws.services.sns.*;
import com.cybershaala.vo.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.util.PortalUtil;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNSClient;
/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	public static final long serialVersionUID = 1L;
       
   ServletContext context;

    public void init(ServletConfig config) throws ServletException
    {
      super.init(config);
      context = config.getServletContext();
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String mode = (String)req.getParameter("mode");
        String UserId = (String)req.getParameter("user");
        Enumeration paramEnum = req.getParameterNames();
        String sel_material = null;
        String mainvid = null;
        String searchTxt =null;
        
        if (UserId==null)
        	UserId = "YouTubeFeeds";
        session.setAttribute("UserId", UserId);
        
        if (mode!=null){
        
        if(mode.equalsIgnoreCase("Login"))
           	homePage(req,res,UserId);
        if (mode.equalsIgnoreCase("SearchMaterial")){
        	searchTxt = (String)req.getParameter("searchTxt");
        	searchMaterial(req,res,searchTxt);
        }else if (mode.equalsIgnoreCase("UpdateUser")){
        	updateUser(req,res,UserId);
        }
        else if (mode.equalsIgnoreCase("DisplayMaterial")){
        	sel_material = (String)req.getParameter("sel_material");
        	displayMaterialPage(req,res,UserId,sel_material);
        }
        else if(mode.equalsIgnoreCase("UploadMaterial")){
          	uploadMaterial(req,res,UserId);
        }
        else if(mode.equalsIgnoreCase("PostComment")){
        	mainvid = (String)req.getParameter("materialselected");
        	postComment(req,res,mainvid);
        }else if(mode.equalsIgnoreCase("displayProfile")){
        	displayProfile(req,res,UserId);
        }
       
        }
 
	}

	public void homePage(HttpServletRequest req, HttpServletResponse res, String UserId) throws ServletException, IOException{
		Connection con;
		String interests = null;
		HttpSession session = req.getSession();
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select interests from cybershaala_user where UserId =  '"+UserId+"'");
			if (rs != null) {
			 while (rs.next())
				 interests = rs.getString("interests");
			}
			MaterialsVO materials = getMaterials(interests);
			req.setAttribute("materialdetails", materials);
			//session.setAttribute("materialsList", materialsList);
			cleanup(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage");
            dispatcher.forward(req, res);
	}
	
	public void updateUser(HttpServletRequest req, HttpServletResponse res, String UserId) throws ServletException, IOException{
		String EmailID = req.getParameter("EmailID");
		String FirstName = req.getParameter("FirstName");
		String LastName = req.getParameter("LastName");
		String Address = req.getParameter("Address");
		String PhoneNumber = req.getParameter("PhoneNumber");
		String FBLink = req.getParameter("FBLink");
		String LinkedIn = req.getParameter("LinkedIn");
		String GitHubLink = req.getParameter("GitHubLink");
		String[] Interests = req.getParameterValues("interests");
		System.out.println("there u go "+ EmailID+FirstName+LastName+Address+PhoneNumber+FBLink+LinkedIn+GitHubLink+Interests);
		System.out.println("here it is inter "+Interests);
		StringBuffer intrsts_string = new StringBuffer();
		 for(int i=0;i<Interests.length; i++) {
			 intrsts_string.append(Interests[i]);
			 intrsts_string.append(",");
			 System.out.println("here it is sb "+Interests[i]);
			 System.out.println("here it is sb1 "+intrsts_string);
		 }
		
		String SQL_UPDATE_USER = "update cybershaala_user set EmailID= '"+EmailID+"', LastName= '"+LastName+"', FirstName= '"+FirstName+"', " +
				"Address= '"+Address+"', PhoneNumber='"+PhoneNumber+"', FBLink= '"+FBLink+"', LinkedIn= '"+LinkedIn+"', "+
				"GitHubLink= '"+GitHubLink+"', Interests= '"+intrsts_string.toString()+"' WHERE UserID='"+UserId+"'";
		System.out.println("there u go query "+ SQL_UPDATE_USER);
		Enumeration paramEnum = req.getParameterNames();
		//System.out.println("there u go "+ EmailID+FirstName+LastName+Address+PhoneNumber+FBLink+LinkedIn+GitHubLink+Interests);
		System.out.println("here it is "+paramEnum);
		 while (paramEnum.hasMoreElements()) {
			 System.out.println("here it is "+paramEnum.nextElement());
		 }
		 Enumeration para = req.getParameterNames();
		 
		 while (para.hasMoreElements()) {
			 System.out.println("here it is getParameterNames "+para.nextElement());
		 }
	 
		Connection con;
		HttpSession session = req.getSession();
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL_UPDATE_USER);

			cleanup(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		homePage(req,res,UserId);
       // RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage");
         //   dispatcher.forward(req, res);
	}
	
	public MaterialsVO getMaterials(String searchtag) throws Exception{
		MaterialsVO materialdetails = new MaterialsVO();
		String[] interestsArray = null;
		String materialLink = null;
		String embedMaterial = null;
        List<String> interestsList = null;
        List<String> materialList = new ArrayList<String>();
        List<String> materialNames = new ArrayList<String>();
       // List<int> materialIDs = new ArrayList<int>();
        List<String> materialDesc = new ArrayList<String>();
		interestsArray = searchtag.split(",");
		//if ((interestsArray.length == 0) || (interestsArray.isEmpty()length != null))
		//	interestsArray = searchtag.split("~!@#$%^");
		interestsList = Arrays.asList(interestsArray);
		System.out.println("interested materials are "+ interestsList.toString());
		Connection con = getConnection();
		if (interestsArray.length != 0){
		for (int i=0; i<interestsArray.length; i++){
			Statement stmt = con.createStatement();
			String query = "select MaterialURL,MaterialName,MaterialDesc from cybershaala_materials where MaterialName like '%"+
					interestsArray[i]+"%' or MaterialDesc like '%"+interestsArray[i]+"%' or Tags like '%"+interestsArray[i]+"%'";
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs != null) {
				 while (rs.next()){
					//materialIDs.add(rs.getInt("MaterialID"));
					materialLink = rs.getString("MaterialURL");
					materialNames.add(rs.getString("MaterialName"));
					materialDesc.add(rs.getString("MaterialDesc"));
					embedMaterial = materialLink.substring(materialLink.indexOf("watch?v=")+8, materialLink.indexOf("&feature"));
					materialList.add("https://www.youtube.com/embed/"+embedMaterial);
				 }
				 materialdetails.setMaterialNamesList(materialNames);
				 materialdetails.setMaterialDescList(materialDesc);
				 materialdetails.setMaterialLinkList(materialList);
			}
			
		}
		}
		System.out.println("collected materials are "+ materialList.toString());
		return materialdetails;
	}
	
	public void searchMaterial(HttpServletRequest req, HttpServletResponse res, String searchTxt) throws IOException, ServletException{
		System.out.println("here at searchMaterial");
		//List<String> searchMaterialsList = new ArrayList<String>();
		try {
			MaterialsVO materials = getMaterials(searchTxt);
			req.setAttribute("searchMaterials", materials);
		
			System.out.println("here at searchMaterial are "+ materials);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("/searchresult");
        dispatcher.forward(req, res);
	
	}
	
	public void postComment(HttpServletRequest req, HttpServletResponse res, String mainvid) throws IOException, ServletException{
		System.out.println("here at post comment video");
		String existingBucketName = "cloudchatvideos";
		String cmntvidfile = null; 
		File tempFile = null;
		String mainvideo = mainvid;
		  /************************************************************************/
	        try{  
	          List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
				for (FileItem item : items) {
					if (!item.isFormField()) {
						if (FilenameUtils.getName(item.getName()).endsWith(".mp4"))
							cmntvidfile = item.getName();
						System.out.println("New comment Msg keyname:"+cmntvidfile);
						InputStream is = item.getInputStream();
						tempFile = new File(cmntvidfile);
						OutputStream outputStream = new FileOutputStream(cmntvidfile);

						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = is.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
				throw new ServletException("Cannot parse multipart request.", e);	
			}

			AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
			AmazonS3 s3client = new AmazonS3Client(credentialsProvider);
			try {
	          System.out.println("Uploading a new object to S3 from a file\n");
	          
	          AccessControlList acl = new AccessControlList();
	          acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);             	
	          s3client.putObject(new PutObjectRequest(existingBucketName, cmntvidfile, tempFile).withAccessControlList(acl));

			} catch (AmazonServiceException ase) {
	          System.out.println("Caught an AmazonServiceException, which " +
	          		"means your request made it " +
	                  "to Amazon S3, but was rejected with an error response" +
	                  " for some reason.");
	          System.out.println("Error Message:    " + ase.getMessage());
	          System.out.println("HTTP Status Code: " + ase.getStatusCode());
	          System.out.println("AWS Error Code:   " + ase.getErrorCode());
	          System.out.println("Error Type:       " + ase.getErrorType());
	          System.out.println("Request ID:       " + ase.getRequestId());
	      } catch (AmazonClientException ace) {
	          System.out.println("Caught an AmazonClientException, which " +
	          		"means the client encountered " +
	                  "an internal error while trying to " +
	                  "communicate with S3, " +
	                  "such as not being able to access the network.");
	          System.out.println("Error Message: " + ace.getMessage());
	      }
	  Connection con;
		  try {
				con = getConnection();
				Statement stmt = con.createStatement();
				String selvidname= mainvideo.substring(mainvideo.lastIndexOf("/")+1);
				String insertquery = "insert into VIDEO_INFO(name,s3link,cflink,commentvideo) values('user1','https://s3-us-west-2.amazonaws.com/cloudchatvideos/"+selvidname+"','"+mainvideo+"','https://d1qbtfddxv4pr5.cloudfront.net/"+cmntvidfile+"')";
				 stmt.executeUpdate(insertquery);
				 cleanup(con);	 
				 sendMessage("Uploaded a Comment Video "+cmntvidfile);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
      RequestDispatcher dispatcher = req.getRequestDispatcher("/mainservlet?mode=displayvideo&keyName="+mainvideo);
          dispatcher.forward(req, res);
	
		
	}
	
	
	public void uploadMaterial(HttpServletRequest req, HttpServletResponse res, String user) throws IOException, ServletException{
		System.out.println("here at uploadmaterial");
		HttpSession session = req.getSession();
		Enumeration paramEnum = req.getParameterNames();
		String uploadfile = null;
       // String selvid = null;
                	
        while (paramEnum.hasMoreElements()) {
            String paramName = (String)paramEnum.nextElement();
            System.out.println("param name in uploadmaterial is "+ paramName);
        }
		String existingBucketName = "cybershaalamaterials";
		File tempFile = null;
		Connection con;
      /************************************************************************/
        try{  
          List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					
						uploadfile = item.getName();
					System.out.println("New upload file keyname:"+uploadfile);
					InputStream is = item.getInputStream();
					tempFile = new File(uploadfile);
					OutputStream outputStream = new FileOutputStream(tempFile);

					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = is.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			throw new ServletException("Cannot parse multipart request.", e);	
		}

		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		AmazonS3 s3client = new AmazonS3Client(credentialsProvider);
		try {
          System.out.println("Uploading a new object to S3 from a file\n");
          
          AccessControlList acl = new AccessControlList();
          acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);             	
          s3client.putObject(new PutObjectRequest(existingBucketName, uploadfile, tempFile).withAccessControlList(acl));

		} catch (AmazonServiceException ase) {
          System.out.println("Caught an AmazonServiceException, which " +
          		"means your request made it " +
                  "to Amazon S3, but was rejected with an error response" +
                  " for some reason.");
          System.out.println("Error Message:    " + ase.getMessage());
          System.out.println("HTTP Status Code: " + ase.getStatusCode());
          System.out.println("AWS Error Code:   " + ase.getErrorCode());
          System.out.println("Error Type:       " + ase.getErrorType());
          System.out.println("Request ID:       " + ase.getRequestId());
      } catch (AmazonClientException ace) {
          System.out.println("Caught an AmazonClientException, which " +
          		"means the client encountered " +
                  "an internal error while trying to " +
                  "communicate with S3, " +
                  "such as not being able to access the network.");
          System.out.println("Error Message: " + ace.getMessage());
      }
	try {
				con = getConnection();
				Statement stmt = con.createStatement();
				java.util.Date currentDate = new java.util.Date();
				String insertquery = "insert into cybershaala_materials(UserID,MaterialURL,MaterialName,Type) values('"+user+"','https://s3-us-west-2.amazonaws.com/"+existingBucketName+"/"+uploadfile+"','userupload','userupload')";
				 stmt.executeUpdate(insertquery);
				 cleanup(con);	 
				 sendMessage("Uploaded a Broadcast Video "+uploadfile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
      
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mainservlet?mode=Login&user=YouTubeFeeds");
      dispatcher.forward(req, res);
          
 	}
	
	public void displayProfile(HttpServletRequest req, HttpServletResponse res, String user) throws ServletException, IOException{
		System.out.println("here at display profile");
		try {
			UserVO userdetails = new UserVO();
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			String userquery="select * from cybershaala_user where UserID = '"+user+"'";
			ResultSet rs = stmt.executeQuery(userquery);
			if (rs != null) {
				 while (rs.next()){
					 userdetails.setUserId(rs.getString("UserID")); //read only
					 userdetails.setEmailID(rs.getString("EmailID"));
					 userdetails.setLastName(rs.getString("LastName"));
					 userdetails.setFirstName(rs.getString("FirstName")); 
					 userdetails.setAddress(rs.getString("Address"));
					 userdetails.setPhoneNumber(rs.getString("PhoneNumber"));
					 userdetails.setFBLink(rs.getString("FBLink"));
					 userdetails.setLinkedInLink(rs.getString("LinkedIn"));
					 userdetails.setGitHubLink(rs.getString("GitHubLink"));
					 userdetails.setInterests(rs.getString("Interests"));
					 userdetails.setReputation(rs.getString("Reputation"));
					 userdetails.setJoinDate(rs.getDate("JoinDate"));
				 }
			}
			
			req.setAttribute("userdetails", userdetails);
		}catch(Exception ex){
			
		}
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateuserpage");
	      dispatcher.forward(req, res);
			
	}
	
	
	public void displayMaterialPage(HttpServletRequest req, HttpServletResponse res, String user, String video) throws ServletException, IOException{
		Connection con;
		String commentvideo = null;
		String buttonName = null;
        List<String> commentvideoList = new ArrayList<String>();
        HttpSession session = req.getSession();
        Enumeration paramEnum = req.getParameterNames();
        while (paramEnum.hasMoreElements()) {
            String paramName = (String)paramEnum.nextElement();
            System.out.println("the submit is "+paramName);
        if(paramName.equals("submit")){
            buttonName = req.getParameter(paramName);
        }
        }
        System.out.println("here at display video "+ video);
        req.setAttribute("brdvidselected", video);
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select commentvideo from VIDEO_INFO where cflink='"+video +"' ORDER BY timestamp desc");
			System.out.println("here at dispaly video "+ video);
			if (rs != null) {
			 while (rs.next()){
				 commentvideo = rs.getString("commentvideo");
				 System.out.println("comment video is "+commentvideo);
				 if((commentvideo!=null) && (commentvideo!="") && (commentvideo!="null"))
					 commentvideoList.add(commentvideo);
			}
			}
			req.setAttribute("commentvideoList", commentvideoList);
			cleanup(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = req.getRequestDispatcher("/commentpage");
            dispatcher.forward(req, res);
             
	}
	
    public Connection getConnection() throws Exception {
    	String conn = System.getProperty("JDBC_CONNECTION_STRING");
    	if (conn ==null)
    		conn="jdbc:mysql://cyberschool.ctnp3jyumtv3.us-west-2.rds.amazonaws.com:3306/cyberschool?user=schooluser&password=cyberuser";
    	String connectionURL = conn.substring(0, conn.indexOf("?"));
    	String dbuser = conn.substring(conn.indexOf("?user=")+6, conn.indexOf("&"));
    	String dbpass= conn.substring(conn.indexOf("&password=")+10);
    	System.out.println("I see stars*********   "+connectionURL+"    "+dbuser+"   "+dbpass);
    	Class.forName("com.mysql.jdbc.Driver");
    	Connection con = DriverManager.getConnection (connectionURL,dbuser,dbpass);
        return con;
    }
    public void cleanup(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
        }
    }
    
    public void sendMessage(String msg) throws IOException {
		// TODO Auto-generated method stub
		//PropertiesCredentials myCredentials = new PropertiesCredentials(SNSClient.class.getResourceAsStream("AwsCredentials.properties"));
		AWSCredentialsProvider myCredentials = new ClasspathPropertiesFileCredentialsProvider();
		AmazonSNSClient sns = new AmazonSNSClient(myCredentials);
		String topicArn = sns.createTopic("MyTopic").getTopicArn();
		String subscriptionArn = sns.subscribe(topicArn, "email", "pu273@nyu.edu").getSubscriptionArn();
		String smssubscriptionArn = sns.subscribe(topicArn, "sms", "13474295947").getSubscriptionArn();
		sns.publish(topicArn, "Hello from SNS world! A new video is uploaded. "+ msg);
		sns.publish(topicArn, "Hello from SNS world! A new video is uploaded. "+msg,"TwittTube");
		//sns.unsubscribe(subscriptionArn);
		//sns.deleteTopic(topicArn);

	}
     
}


