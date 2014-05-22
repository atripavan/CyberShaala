package com.cybershaala.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cybershaala.CyberShaalaDAL;
import com.cybershaala.data.model.Feedback;
import com.cybershaala.data.model.Materials;
import com.cybershaala.data.model.QA;
import com.cybershaala.data.model.Comment;
import com.cybershaala.util.CyberShaalaUtilities;

/**
 * Servlet implementation class MaterialControllerServlet
 */
public class MaterialControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaterialControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in servlet"+request.getParameter("action"));
		Materials obj = new Materials();
//		obj.setDateTime(java.sql.Timestamp.valueOf("2014-05-17 10:10:10.0"));
		obj.setMaterialDesc(request.getParameter("materialDesc"));
		obj.setMaterialName(request.getParameter("materialName"));
		String matUrl = CyberShaalaUtilities.getOriginalUrl(request.getParameter("materialURL"));
		obj.setMaterialUrl(matUrl);
		obj.setType(request.getParameter("type"));
		request.setAttribute("materialObj", obj);
		//TODO comment above lines of code
		
		int queId = 0;
		
			CyberShaalaDAL.createConnectionAndStatement();
			Hashtable<String, QA> qsHt = new Hashtable<String, QA>();
			Hashtable<String, ArrayList<QA>> ansHt = new Hashtable<String, ArrayList<QA>>();
			ArrayList<Comment> comList = new ArrayList<Comment>();
			Materials material = (Materials) request.getAttribute("materialObj");
			HttpSession session = request.getSession();
			session.setAttribute("materialUrl", material.getMaterialUrl());
			String sql = "SELECT * from cybershaala_QA where MaterialURL = '"+material.getMaterialUrl()+"'";
			try {
				ResultSet rs = CyberShaalaDAL.statement.executeQuery(sql);
				
				/*Create two hashtables- one for questions and other for answers
				Question hashtable will have key- question ID, value- QA obj
				Answers hashtable will have key- question, value- List of QA objs
				where each obj will have details of one QA record representing answer*/
				ArrayList<QA> qaList;
				
				while (rs.next()){
					if (rs.getString("Answer")==null) {		//if this is null then the record is a Question
						if(Integer.parseInt(rs.getString("Question")) > queId)
							queId = Integer.parseInt(rs.getString("Question"));
						qsHt.put(rs.getString("Question"), createQaObj(rs));
						
					}
					else {//Else its an answer record that contains both question and answer IDs
						if (ansHt.containsKey(rs.getString("Question"))){
							qaList = ansHt.get(rs.getString("Question"));//Get the list of answer QAs
							//Create new QA obj and add into qaList
							qaList.add(createQaObj(rs));
						}
						else {
							qaList = new ArrayList<QA>();
							qaList.add(createQaObj(rs));
							ansHt.put(rs.getString("Question"), qaList);						
						}
					}
				}
				rs.close();
				
//				String sql2 = "SELECT * from cybershaala_Comments where MaterialURL = '"+material.getMaterialUrl()+"'";
//				rs = CyberShaalaDAL.statement.executeQuery(sql2);
//				while(rs.next()){
//					Comment objCom = new Comment();
//					objCom.setMaterialURL(rs.getString("MaterialURL"));
//					objCom.setDateTime(CyberShaalaUtilities.convertStrToTimestamp(rs.getString("UpdatedDate")));
//					objCom.setUserId(rs.getString("UserID"));
//					objCom.setComment(rs.getString("Comment"));
//					
//					comList.add(objCom);
//				}
//				rs.close();
				
				String sql3 = "SELECT * from cybershaala_feedback where MaterialURL = '"+material.getMaterialUrl()+"'";
				rs = CyberShaalaDAL.statement.executeQuery(sql3);
				while(rs.next()){
					request.getSession().setAttribute("score", rs.getString("FinalScore"));
				}
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			finally{
				CyberShaalaDAL.close();
			}
			request.setAttribute("lrgstQueId", queId);
			request.setAttribute("qsHt", qsHt);
			request.setAttribute("ansHt", ansHt);
			//request.setAttribute("comList", comList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/material.jsp?materialUrl="+material.getMaterialUrl());
			dispatcher.forward(request, response);		
	}
	
	void postQuestion(HttpServletRequest request) {
		System.out.println("posting question");
		int queId = Integer.parseInt(request.getParameter("queId"))+1;
		String userId = (String)request.getSession().getAttribute("userId");
		Timestamp curTime = new Timestamp(new Date().getTime());
		String sql = "INSERT INTO cybershaala_QA (MaterialURL, Question, Text, UserID, UpdatedDate, VoteUp)"
				+ " VALUES ('"+request.getParameter("materialUrl")+"', '"+queId+"', "
						+ "'"+request.getParameter("content")+"', '"+userId+"', '"
								+ curTime.toString() +"',"+0+")";
		System.out.println(sql);
		try {
			CyberShaalaDAL.createConnectionAndStatement();
			CyberShaalaDAL.statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			CyberShaalaDAL.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "false";
		System.out.println("action"+request.getParameter("action"));
		String action = request.getParameter("action");
		
		if(action !=null && action.equalsIgnoreCase("postQuestion")){
			postQuestion(request);
		}
		else if(action !=null && action.equalsIgnoreCase("sendFeedback")){
			result = sendFeedback(request);
		}
		else if(action !=null && action.equalsIgnoreCase("postAnswer")){
			result = postAnswer(request);
		}
		else if(action !=null && action.equalsIgnoreCase("voteAnsUp")){
			result = voteAnsUp(request);
		}
		else if(action !=null && action.equalsIgnoreCase("voteAnsDown")){
			result = voteAnsDown(request);
		}
		else if(action !=null && action.equalsIgnoreCase("voteQueUp")){
			result = voteQueUp(request);
		}
		else if(action !=null && action.equalsIgnoreCase("voteQueDown")){
			result = voteQueDown(request);
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(result);
	}

	private String voteQueDown(HttpServletRequest request) {
		String result = "";
		int queId = Integer.parseInt(request.getParameter("queId"));
		String sql = "UPDATE cybershaala_QA SET VoteUp = VoteUp - 1 WHERE MaterialURL="
				+ "'"+request.getParameter("materialUrl")+"' AND Question="+queId+" AND Answer is null";
		System.out.println("QueVoteDown:\n"+sql);
		try {
			CyberShaalaDAL.createConnectionAndStatement();
			CyberShaalaDAL.statement.executeUpdate(sql);
			result="success";
		} catch (SQLException e) {
			e.printStackTrace();
			result = "failure";
		}
		finally{
			CyberShaalaDAL.close();
		}
		return result;
	}

	private String voteQueUp(HttpServletRequest request) {
		String result = "";
		int queId = Integer.parseInt(request.getParameter("queId"));
		String sql = "UPDATE cybershaala_QA SET VoteUp = VoteUp + 1 WHERE MaterialURL="
				+ "'"+request.getParameter("materialUrl")+"' AND Question="+queId+" AND Answer is null";
		System.out.println("QueVoteUp:\n"+sql);
		try {
			CyberShaalaDAL.createConnectionAndStatement();
			CyberShaalaDAL.statement.executeUpdate(sql);
			result="success";
		} catch (SQLException e) {
			e.printStackTrace();
			result = "failure";
		}
		finally{
			CyberShaalaDAL.close();
		}
		return result;
	}

	private String voteAnsDown(HttpServletRequest request) {
		String result = "";
		int queId = Integer.parseInt(request.getParameter("queId"));
		int ansId = Integer.parseInt(request.getParameter("ansId"));
		String sql = "UPDATE cybershaala_QA SET VoteUp = VoteUp - 1 WHERE MaterialURL="
				+ "'"+request.getParameter("materialUrl")+"' AND Question="+queId+" AND Answer="+ansId+"";
		System.out.println("AnsVoteDown:\n"+sql);
		try {
			CyberShaalaDAL.createConnectionAndStatement();
			CyberShaalaDAL.statement.executeUpdate(sql);
			result="success";
		} catch (SQLException e) {
			e.printStackTrace();
			result = "failure";
		}
		finally{
			CyberShaalaDAL.close();
		}
		return result;
	}
	
	private String voteAnsUp(HttpServletRequest request) {
		String result = "";
		int queId = Integer.parseInt(request.getParameter("queId"));
		int ansId = Integer.parseInt(request.getParameter("ansId"));
		String sql = "UPDATE cybershaala_QA SET VoteUp = VoteUp + 1 WHERE MaterialURL="
				+ "'"+request.getParameter("materialUrl")+"' AND Question="+queId+" AND Answer="+ansId+"";
		System.out.println("AnsVoteUp:\n"+sql);
		try {
			CyberShaalaDAL.createConnectionAndStatement();
			CyberShaalaDAL.statement.executeUpdate(sql);
			result="success";
		} catch (SQLException e) {
			e.printStackTrace();
			result = "failure";
		}
		finally{
			CyberShaalaDAL.close();
		}
		return result;
	}

	private String postAnswer(HttpServletRequest request) {
		String result = "";
		int queId = Integer.parseInt(request.getParameter("queId"));
		int nextAnsId = Integer.parseInt(request.getParameter("nextAnsId"));
		String userId = (String)request.getSession().getAttribute("userId");
		Timestamp curTime = new Timestamp(new Date().getTime());
		String sql = "INSERT INTO cybershaala_QA (MaterialURL, Question, Answer, Text, UserID, UpdatedDate, VoteUp)"
				+ " VALUES ('"+request.getParameter("materialUrl")+"', '"+queId+"', '"+nextAnsId+"', "
						+ "'"+request.getParameter("content")+"', '"+userId+"', '"
								+ curTime.toString() +"', "+0+")";
		System.out.println(sql);
		try {
			CyberShaalaDAL.createConnectionAndStatement();
			CyberShaalaDAL.statement.executeUpdate(sql);
			result="success";
		} catch (SQLException e) {
			e.printStackTrace();
			result = "failure";
		}
		finally{
			CyberShaalaDAL.close();
		}
		return result;
	}

	public String sendFeedback(HttpServletRequest request) {
		Feedback objFdb = new Feedback();		
		objFdb.setMaterialUrl((String) request.getSession().getAttribute("materialUrl"));
		objFdb.setStarRating(Integer.parseInt(request.getParameter("starVal")));
		objFdb.setqOne(Boolean.parseBoolean(request.getParameter("qlty")));
		objFdb.setqTwo(Boolean.parseBoolean(request.getParameter("eg")));
		objFdb.setqThree(Boolean.parseBoolean(request.getParameter("undstnd")));
		objFdb.setqFour(Boolean.parseBoolean(request.getParameter("rel")));
		objFdb.setqFive(Boolean.parseBoolean(request.getParameter("rec")));
		objFdb.setFinalScore(getCyberShaalaScore(objFdb));
		
		if(CyberShaalaDAL.insertIntoFeedback(objFdb))
			return "success";
		else
			return "failure";
	}

	public float getCyberShaalaScore(Feedback objFdb) {	
		float finalscore = 0;
		finalscore+=0.35*objFdb.getStarRating();
		if(objFdb.isqOne())
			finalscore+=0.5;
		if(objFdb.isqTwo())
			finalscore+=0.2;
		if(objFdb.isqThree())
			finalscore+=0.5;
		if(objFdb.isqFour())
			finalscore+=2.0;
		if(objFdb.isqFive())
			finalscore+=1.0;
		return finalscore;
	}

	public QA createQaObj(ResultSet rs) throws SQLException, ParseException{
		QA objQa = new QA();
//		objQa.setMaterialUrl(rs.getString("MaterialURL"));
		objQa.setText(rs.getString("Text"));
		objQa.setQuestion(rs.getString("Question"));
		objQa.setAnswer(rs.getString("Answer"));
		objQa.setUserId(rs.getString("UserID"));
		objQa.setDateTime(CyberShaalaUtilities.convertStrToTimestamp(rs.getString("UpdatedDate")));
		objQa.setVoteUp(rs.getString("VoteUp"));
		objQa.setVoteDown(rs.getString("VoteDown"));
		return objQa;
	}	
}
