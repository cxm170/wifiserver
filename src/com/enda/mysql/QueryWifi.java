package com.enda.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.enda.usertrackprediction.Coordinate;
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
	    	String sql = "select * from wifilocation ";
//	    	System.out.println(sql);
	        stmt = con.createStatement();
	        ResultSet rs=stmt.executeQuery(
	            sql);
	        while(rs.next()){

	        	
	        	String APname = rs.getString("name");
	    		double x = rs.getDouble("x");
	    		double y = rs.getDouble("y");
	    		String MAC = rs.getString("MAC");
	    		
	    		String coors = rs.getString("coordinates");

	    		double tempX;
	    		double tempY;
	    		Coordinate tempCoor;
	    		
	    		String delims = "[ ]";
	    		String[] tokens = coors.split(delims);

	    		List<Coordinate> allcoordinates = new ArrayList<>();
	    		
	    		for(int i=0;i<tokens.length;i=i+2){

	    			tempX = Double.parseDouble(tokens[i]);
	    			tempY = Double.parseDouble(tokens[i+1]);
	    			tempCoor = new Coordinate(tempX,tempY);
	    			allcoordinates.add(tempCoor);
	    		}
	    		
	    		
	    		
	        	Wifi wifi = new Wifi(APname,x,y,MAC,allcoordinates);
	        	results.add(wifi);
	        }

	    } catch (SQLException e) {
	        JDBCTutorialUtilities.printSQLException(e);
	    } finally {
	        if (stmt != null) {
	          stmt.close();
//	          System.out.println("Connection closed.");
	        }
	    }
	    return results;
	}
}
