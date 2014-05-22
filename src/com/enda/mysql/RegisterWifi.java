package com.enda.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.enda.usertrackprediction.Coordinate;
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
		String MAC = newwifi.getMAC();
		
		List<Coordinate> allcoordinates = newwifi.getCoorsContained();
		
		StringBuilder sb = new StringBuilder();
		
		Iterator it = allcoordinates.iterator();
		
		Coordinate tempcoor = new Coordinate();
		
		while(it.hasNext()){
			tempcoor = (Coordinate) it.next();
			
			sb.append(String.valueOf(tempcoor.getX()));
			sb.append(" ");
			sb.append(String.valueOf(tempcoor.getY()));
			sb.append(" ");
			
		}
		
		String allcoor = sb.toString();
		
		Statement stmt = null;
		
	    try {
	    	String sql = "insert into wifilocation (MAC,name,x,y,radius,coordinates) " +
		            "values('"+MAC+"','" + APname + "'," + x + ", " +
		            y+ "," + radius+",'"+allcoor+"')";
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
