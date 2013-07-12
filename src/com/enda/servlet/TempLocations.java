package com.enda.servlet;

import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;

import com.enda.usertrackprediction.Coordinate;

public class TempLocations {
	
	public static Map<Timestamp, Coordinate> locations = new TreeMap<>();
	
	public static void add(Timestamp t,Coordinate coor){
		
		
		
		locations.put(t,coor);
	}
	
	public static void clear(){
		locations.clear();
	}
	


	
}
