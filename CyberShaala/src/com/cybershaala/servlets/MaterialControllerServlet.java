package com.cybershaala.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybershaala.CyberShaalaDAL;
import com.cybershaala.data.model.QA;
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
		CyberShaalaDAL.createConnectionAndStatement();
		Hashtable<String, QA> qsHt = new Hashtable<String, QA>();
		Hashtable<String, ArrayList<QA>> ansHt = new Hashtable<String, ArrayList<QA>>();

		String sql = "SELECT * from cybershaala_QA where MaterialURL = 'https://www.youtube.com/watch?v=V6mKVRU1evU&feature=youtube_gdata'";
		try {
			ResultSet rs = CyberShaalaDAL.statement.executeQuery(sql);
			
			/*Create two hashtables- one for questions and other for answers
			Question hashtable will have key- question ID, value- QA obj
			Answers hashtable will have key- question, value- List of QA objs
			where each obj will have details of one QA record representing answer*/
			ArrayList<QA> qaList;
			
			while (rs.next()){
				System.out.println("whiling...");
				if (rs.getString("Answer")==null) {		//if this is null then the record is a Question
					System.out.println("answer is null");
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute("qsHt", qsHt);
		request.setAttribute("ansHt", ansHt);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/material.jsp?materialUrl=https://www.youtube.com/embed/V6mKVRU1evU&materialType=youtube");
		dispatcher.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	QA createQaObj(ResultSet rs) throws SQLException, ParseException{
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
