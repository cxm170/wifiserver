package com.enda.mysql;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import com.enda.mobiletraces.Location;
import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.User;

public class InsertCoordinate {
	private ConnectMySql connectToMySql;
	private Connection con;
	
	public InsertCoordinate() throws SQLException{
		connectToMySql = new ConnectMySql("mysql","localhost","track",3306,"root","polyu");
		con = connectToMySql.getConnection();
		
	}
		
	public void insertLoc(Coordinate loc, User user) throws SQLException{
		double x = loc.getX();
		double y = loc.getY();
		java.sql.Timestamp datetime = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Statement stmt = null;
		
	    try {
	    	String sql = "insert into usertrace (username,datetime,x,y) " +
		            "values('" + user.getUser() + "','" + datetime + "', " +
		            x+ "," + y+")";
	    	System.out.println(sql);
	        stmt = con.createStatement();
	        stmt.executeUpdate(
	            sql);

	    } catch (SQLException e) {
	        JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) {
	          stmt.close();
//	          System.out.println("Connection closed.");
	        }
	    }
	}
	
	
	public void insertLoc(Location loc) throws SQLException{
		double x = Double.valueOf(loc.lat);
		double y = Double.valueOf(loc.lon);
		
		DecimalFormat df1 = new DecimalFormat("##.####");
		df1.setRoundingMode(RoundingMode.HALF_UP);
		
		String lat_round = df1.format(x);
		
		DecimalFormat df2 = new DecimalFormat("###.####");
		df2.setRoundingMode(RoundingMode.HALF_UP);
		
		String lon_round = df2.format(y);
		
		x = Double.valueOf(lat_round);
		y = Double.valueOf(lon_round);
		
		Statement stmt = null;
		
	    try {
	    	String sql = "insert into usertrace (username,datetime,x,y) " +
		            "values('" + loc.name + "','" + loc.ts + "', " +
		            x+ "," + y+")";
	    	System.out.println(sql);
	        stmt = con.createStatement();
	        stmt.executeUpdate(
	            sql);

	    } catch (SQLException e) {
	        JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) {
	          stmt.close();
//	          System.out.println("Connection closed.");
	        }
	    }
	}
	
}

