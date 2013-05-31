package com.enda.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


import com.enda.wifiselector.Wifi;

public class QueryWifi {
	private ConnectMySql connectToMySql;
	private Connection con;
	private List<Wifi> results = new ArrayList<>();
	
	public QueryWifi() throws SQLException{
		connectToMySql = new ConnectMySql("mysql","localhost","track",3306,"root","polyu");
		con = connectToMySql.getConnection();
	}
	
	public List<Wifi> getWifi() throws SQLException{
		
		Statement stmt = null;


	    try {
	    	String sql = "select * from wifilocations ";
	    	System.out.println(sql);
	        stmt = con.createStatement();
	        ResultSet rs=stmt.executeQuery(
	            sql);
	        while(rs.next()){

	        	
	        	String APname = rs.getString("name");
	    		double x = rs.getDouble('x');
	    		double y = rs.getDouble('y');
	    		int radius = rs.getInt("radious");
	    		
	        	Wifi wifi = new Wifi(APname,x,y,radius);
	        	results.add(wifi);
	        }

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
