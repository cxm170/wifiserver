package com.enda.wifiselector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enda.mysql.RegisterWifi;
import com.enda.usertrackprediction.Coordinate;

public class DemoRegisterNewWifi {
	public static void main(String [] args) throws SQLException{
		List<Coordinate> coor = new ArrayList<>();
		Coordinate c = new Coordinate(11.1,21.1);
		coor.add(c);
		String MAC = "12:12:31:12:41:13";
		
		Wifi newwifi = new Wifi("PolyuWLAN",22.3044967,114.1807311,MAC,coor);
		
		new RegisterWifi().registerWifi(newwifi);
	}

}
