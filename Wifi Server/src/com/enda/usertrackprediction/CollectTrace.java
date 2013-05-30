package com.enda.usertrackprediction;

import java.sql.SQLException;

import com.enda.mysql.InsertCoordinate;

public class CollectTrace {
	private User user;
	private InsertCoordinate insertCoordinate;
	
	
	public CollectTrace(User user) throws SQLException{
		this.user = user;
		insertCoordinate = new InsertCoordinate();
	}
	
	

	

	
	public void storeTrace(Coordinate loc) throws SQLException{

		insertCoordinate.insertLoc(loc,this.user);
	}

		
	
}
