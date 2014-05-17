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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.cybershaala.data.model.Feedback;
import com.cybershaala.data.model.Materials;
import com.cybershaala.util.CyberShaalaConstants;

public class CyberShaalaDAL {
	public static Connection connect = null;
	public static Statement statement = null;
	private static ResultSet resultSet = null;

	public static void main(String[] args) {
		CyberShaalaDAL obj = new CyberShaalaDAL();
		createConnectionAndStatement();
//		updateFeedbackTableWithMatId();
//		obj.insertRecord("aleiuflkjewf",1,"abcd.flv");		
	}

	public static void createConnectionAndStatement()
	{
		try{
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://"+CyberShaalaConstants.DB_END_POINT+":"
						+CyberShaalaConstants.DB_PORT+"/"+CyberShaalaConstants.DB_NAME,
							CyberShaalaConstants.DB_USER_NAME,CyberShaalaConstants.DB_PWD);

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
		for (Feedback fdb : feedbackList) {
			String sql = "INSERT INTO cybershaala_feedback (MaterialURL, FinalScore)" +
					" VALUES ('"+fdb.getMaterialUrl()+"', '"+fdb.getFinalScore()+"')";
			try {
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				System.err.println("=====ERROR OCCURRED WHILE INSERTING=====");
				e.printStackTrace();
			}
		}
		close();
	}
	
	public static void updateFeedbackTableWithMatId()
	{		
		try {
			createConnectionAndStatement();
			String sql = "SELECT * from cybershaala_materials";
			System.out.println("Executing query-\n"+sql);
			ResultSet rs = statement.executeQuery(sql);
			Hashtable<String, String> ht = new Hashtable<String, String>();  
			while(rs.next())
			{
				ht.put(rs.getString("MaterialURL"), rs.getString("MaterialID"));
			}
			rs.close();
			
			Iterator<Entry<String, String>> it = ht.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String sql2 = "UPDATE cybershaala_feedback SET MaterialID="+entry.getValue()
						+ " WHERE MaterialURL='"+entry.getKey()+"'";
				System.out.println("Executing query-\n"+sql2);
				statement.executeUpdate(sql2);
			}			
			
		} catch (SQLException e) {
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