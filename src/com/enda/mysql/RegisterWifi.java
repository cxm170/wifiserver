package com.enda.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.enda.wifiselector.Wifi;


public class RegisterWifi {
	private ConnectMySql connectToMySql;
	private Connection con;
	
	public RegisterWifi() throws SQLException{
		connectToMySql = new ConnectMySql("mysql","localhost","track",3306,"root","polyu");
		con = connectToMySql.getConnection();
		
	}
		
	public void registerWifi(Wifi newwifi) throws SQLException{
		String APname = newwifi.getAPname();
		double x = newwifi.getxPos();
		double y = newwifi.getyPos();
		int radius = newwifi.getRadius();
		
		Statement stmt = null;
		
	    try {
	    	String sql = "insert into wifilocation (name,x,y,radius) " +
		            "values('" + APname + "'," + x + ", " +
		            y+ "," + radius+")";
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
