package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

public class DemoPredictUserTrack {
	public static void main(String [] args) throws SQLException{
		User user = new User("hlaw");

		
		Coordinate c1 = new Coordinate(22.329,114.1865);
//		Coordinate c2 = new Coordinate(22.305154480173552,114.18084794740665);
//		Coordinate c3 = new Coordinate(22.305065973995234,114.1808579659842);
//		Coordinate c4 = new Coordinate(22.305094830809715,114.18084447902604);
//		Coordinate c5 = new Coordinate(22.305161835907583,114.18081043897244);
		
		Random random = new Random();
		
		int num = Math.max(5, random.nextInt(30));
		

		
		Coordinate[] locs = new Coordinate[num];
		Route actualRoute = new Route();
		locs[0] = c1;
		actualRoute.add(locs[0]);
		for(int i=1;i<num;i++){
			locs[i] = GenerateRandomLocation.getLocation(locs[i-1], 10);
			actualRoute.add(locs[i]);

		}
		
		
		
		
		Coordinate[] locss = {c1};
//		

		
		TrackPrediction trackpredictor = new TrackPrediction(user);

		Map<Route,Integer> predictedRoutes = trackpredictor.getPredictedTracks(locss);
		Route predictedRoute =  trackpredictor.getPredictedTrack(predictedRoutes);
//		Route predictedRoute1 =  trackpredictor.getPredictedTrack();
		
		
//		double minDistance = actualRoute.computeDistanceFrom(predictedRoute);
//		
//		System.out.println(actualRoute);
//		System.out.println("Overall Distance Deviation= " + minDistance + " km.");
//		if(actualRoute.size()<predictedRoute.size()){
//		System.out.println("Average Distance Deviation= " + minDistance/actualRoute.size() + " km.");
//		}
//		else{
//			System.out.println("Average Distance Deviation= " + minDistance/predictedRoute.size() + " km.");	
//		}
//		
//		
//		double tempDistance;
//		
//		for(Map.Entry<Route, Integer> entry:predictedRoutes.entrySet()){
//			Route tempRoute = entry.getKey();
//			tempDistance = actualRoute.computeDistanceFrom(tempRoute);
//			if(tempDistance<minDistance&&tempRoute.getOverallDistance()>=actualRoute.getOverallDistance()) {minDistance = tempDistance;
//			System.out.println(tempRoute);
//			}
//		}
//		
//		System.out.println("Min Distance = " + minDistance);
	}

}
