package com.enda.wifiselector;

import java.sql.SQLException;
import com.enda.usertrackprediction.Coordinate;

public class DemoGetQualifiedWifi {
	public static void main(String [] args) throws SQLException{
		Coordinate coor = new Coordinate(22.3044967,114.1807311); 
		Coordinate[] coordinates ={coor};

		System.out.println(WifiDistributionMap.getWifiMap(coordinates));
	}
	
}
