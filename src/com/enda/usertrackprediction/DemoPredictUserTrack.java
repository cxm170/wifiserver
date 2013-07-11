package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Map;

public class DemoPredictUserTrack {
	public static void main(String [] args) throws SQLException{
		User user = new User("Inman");

		
		Coordinate c1 = new Coordinate(22.30508281467609,114.18086309953941);
		Coordinate c2 = new Coordinate(22.305098376549246,114.18095496479062);
		
		Coordinate[] locs = {c2,c1};
		
		TrackPrediction trackpredictor = new TrackPrediction(user,locs);

		Map<Route,Integer> predictedRoutes = trackpredictor.getPredictedTracks();
		Route predictedRoute =  trackpredictor.getPredictedTrack(predictedRoutes);
//		Route predictedRoute1 =  trackpredictor.getPredictedTrack();
	}

}
