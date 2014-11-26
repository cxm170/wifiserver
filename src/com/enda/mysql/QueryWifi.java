package com.enda.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.enda.usertrackprediction.Coordinate;
import com.enda.wifiselector.Wifi;
import com.enda.wifiselector.WifiNew;

public class QueryWifi {
	private ConnectMySql connectToMySql;
	private Connection con;
	
	
	public QueryWifi() throws SQLException{
		connectToMySql = new ConnectMySql("mysql","localhost","wifi_traces",3306,"root","polyu");
		con = connectToMySql.getConnection();
	}
	
	public List<WifiNew> getAllWifi() throws SQLException{
		
		Statement stmt = null;
		List<WifiNew> results = new ArrayList<>();

	    try {
	    	String sql = "select * from wifitrace_distinct";
//	    	System.out.println(sql);
	        stmt = con.createStatement();
	        ResultSet rs=stmt.executeQuery(
	            sql);
	        while(rs.next()){

	        	
	        	String ssid = rs.getString("ssid");
	        	
	    		double lat = rs.getDouble("lat");
	    		double lon = rs.getDouble("lon");
	    		String bssid = rs.getString("bssid");
	    		int level = rs.getInt("dbm");
	    		int id = rs.getInt("_id");

//	    		double tempX;
//	    		double tempY;
//	    		Coordinate tempCoor;
//	    		
//	    		String delims = "[ ]";
//	    		String[] tokens = coors.split(delims);
//
//	    		List<Coordinate> allcoordinates = new ArrayList<>();
//	    		
//	    		for(int i=0;i<tokens.length;i=i+2){
//
//	    			tempX = Double.parseDouble(tokens[i]);
//	    			tempY = Double.parseDouble(tokens[i+1]);
//	    			tempCoor = new Coordinate(tempX,tempY);
//	    			allcoordinates.add(tempCoor);
//	    		}
	    		
	    		
	    		
	        	WifiNew wifi = new WifiNew(ssid,bssid,lat,lon,level,id);
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
	
	
public List<WifiNew> getWifiForLocation(Coordinate coor) throws SQLException{
		
		Statement stmt = null;
		List<WifiNew> results = new ArrayList<>();

	    try {
	    	String sql = "select * from wifitrace_distinct where lat =" + coor.getX() + "and lon =" + coor.getY();
//	    	System.out.println(sql);
	        stmt = con.createStatement();
	        ResultSet rs=stmt.executeQuery(
	            sql);
	        while(rs.next()){

	        	
	        	String ssid = rs.getString("ssid");
	        	
	    		double lat = rs.getDouble("lat");
	    		double lon = rs.getDouble("lon");
	    		String bssid = rs.getString("bssid");
	    		int level = rs.getInt("dbm");
	    		int id = rs.getInt("_id");

//	    		double tempX;
//	    		double tempY;
//	    		Coordinate tempCoor;
//	    		
//	    		String delims = "[ ]";
//	    		String[] tokens = coors.split(delims);
//
//	    		List<Coordinate> allcoordinates = new ArrayList<>();
//	    		
//	    		for(int i=0;i<tokens.length;i=i+2){
//
//	    			tempX = Double.parseDouble(tokens[i]);
//	    			tempY = Double.parseDouble(tokens[i+1]);
//	    			tempCoor = new Coordinate(tempX,tempY);
//	    			allcoordinates.add(tempCoor);
//	    		}
	    		
	    		
	    		
	        	WifiNew wifi = new WifiNew(ssid,bssid,lat,lon,level,id);
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
