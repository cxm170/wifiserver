package com.enda.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.User;

public class QueryCoordinate {
	private ConnectMySql connectToMySql;
	private Connection con;
	private Map<Timestamp,Coordinate> results = new TreeMap<Timestamp,Coordinate>();
	
	public QueryCoordinate() throws SQLException{
		connectToMySql = new ConnectMySql("mysql","localhost","track",3306,"root","polyu");
		con = connectToMySql.getConnection();
	}
	
	public Map<Timestamp,Coordinate> queryLoc() throws SQLException, ParseException{
		
		Statement stmt = null;


	    try {
	    	String sql = "select * from usertrace";
	    	System.out.println(sql);
	        stmt = con.createStatement();
	        ResultSet rs=stmt.executeQuery(
	            sql);
	        while(rs.next()){
	        	String datestring=rs.getString("datetime");
	        	Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(datestring);
	        	

	        	
	        	Timestamp datetime = new Timestamp(date.getTime());
	    		
	        	
	        	
	        	
	        	
	        	Coordinate loc = new Coordinate();
	        	loc.setX(rs.getDouble("x"));
	        	loc.setY(rs.getDouble("y"));
	        	results.put(datetime, loc);
	        }

//	        System.out.println(results);
	        
	    } catch (SQLException e) {
	        JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) {
	          stmt.close();
	          System.out.println("Connection closed.");
	        }
	    }
	    return results;
	}
}
