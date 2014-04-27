package com.cybershaala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.cybershaala.CyberShaalaDAL;
import com.cybershaala.data.model.Feedback;

import com.cybershaala.data.model.Materials;

public class CyberShaalaDAL {
	private static Connection connect = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static final String DB_END_POINT = "cyberschool.ctnp3jyumtv3.us-west-2.rds.amazonaws.com";
	private static final String DB_USER_NAME = "schooluser";
	private static final  String DB_PWD = "cyberuser";
	private static final String DB_NAME = "cyberschool";
	private static final int DB_PORT = 3306;

	public static void main(String[] args) {
		CyberShaalaDAL obj = new CyberShaalaDAL();
		obj.createConnectionAndStatement();
//		obj.insertRecord("aleiuflkjewf",1,"abcd.flv");		
	}

	public static void createConnectionAndStatement()
	{
		try{
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://"+DB_END_POINT+":"+DB_PORT+"/"+DB_NAME,DB_USER_NAME,DB_PWD);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
	}

	public static void insertIntoMaterials(Materials materials)
	{
		try {
			String sql = "INSERT INTO cybershaala_materials (MaterialURL, MaterialName, MaterialDesc, UserID, Tags, Type, DateCreated)" +
					" VALUES ('"+materials.getMaterialUrl()+"', '"+materials.getMaterialName()+"', '"+materials.getMaterialDesc()+"', "
							+ "'"+materials.getUserId()+"', '"+materials.getTags()+"', '"+materials.getType()+"', '"+materials.getDateTime()+"')";
			System.out.println(sql);
			statement.executeUpdate(sql);
			System.out.println("<--------SUCCESSFULLY INSERTED-------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertIntoMaterialsAsBatch(List<Materials> materialsList)
	{
		createConnectionAndStatement();
		try {
			for (Materials material : materialsList) {
				String sql = "INSERT INTO cybershaala_materials (MaterialURL, MaterialName, MaterialDesc, UserID, Tags, Type, DateCreated)" +
						" VALUES ('"+material.getMaterialUrl()+"', '"+material.getMaterialName()+"', '"+material.getMaterialDesc()+"', "
								+ "'"+material.getUserId()+"', '"+material.getTags()+"', '"+material.getType()+"', '"+material.getDateTime()+"')";
				System.out.println(sql);
				statement.executeUpdate(sql);
			}
			close();
		} catch (SQLException e) {
			System.err.println("=====ERROR OCCURRED WHILE INSERTING=====");
			e.printStackTrace();
		}
		finally{
			close();
		}
	}
	
	public static void insertIntoFeedbackAsBatch(List<Feedback> feedbackList)
	{
		createConnectionAndStatement();
		try {
			for (Feedback fdb : feedbackList) {
				String sql = "INSERT INTO cybershaala_feedback (MaterialURL, FinalScore)" +
						" VALUES ('"+fdb.getMaterialUrl()+"', '"+fdb.getFinalScore()+"')";
				statement.executeUpdate(sql);
			}
			close();
		} catch (SQLException e) {
			System.err.println("=====ERROR OCCURRED WHILE INSERTING=====");
			e.printStackTrace();
		}
		finally{
			close();
		}
	}
	

//	public void createTable()
//	{
//		try {
//			createConnectionAndStatement();
//			String createTableSql = "CREATE TABLE VIDEO_INFO (name VARCHAR(255) not NULL, timestamp TIMESTAMP, " + 
//					" s3link VARCHAR(255), cflink VARCHAR(255), rating INTEGER, totalvotes INTEGER)";
//			statement.executeUpdate(createTableSql);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close();
//		}
//
//	}
	// You need to close the resultSet
	public static void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}