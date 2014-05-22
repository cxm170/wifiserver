package com.enda.wifiselector;

import java.sql.SQLException;
import java.util.Map;

import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.GenerateRandomLocation;
import com.enda.usertrackprediction.Route;
import com.enda.usertrackprediction.TrackAdjust;
import com.enda.usertrackprediction.TrackPrediction;
import com.enda.usertrackprediction.User;

public class DemoGetQualifiedWifi {
	public static void main(String [] args) throws SQLException{
//		Coordinate coor = new Coordinate(22.3044967,114.1807311); 
		
		
		Coordinate c1 = new Coordinate(22.304798857977385,114.18129648732399);
		Coordinate c2 = new Coordinate(22.304830023199596,114.18129993690198);
		Coordinate c3 = new Coordinate(22.305098376549246,114.18095496479062);
		Coordinate c4 = new Coordinate(22.305135774361815,114.18093903272596);
//		Coordinate c5 = new Coordinate(22.305161835907583,114.18081043897244);
		
		Coordinate[] coordinates ={c4,c3};
		
		System.out.println(GenerateRandomLocation.getLocation(c3, 5));
		
		
		User user = new User("Inman");
		TrackPrediction trackpredictor = new TrackPrediction(user);
		Map<Route,Integer> predictedRoutes =  trackpredictor.getPredictedTracks(coordinates);
		
		//Pre-define the bandwidth for each cloudlet.
		double bandwidth = 1*128; //unit: KB/s
				
		TrackAdjust trackAdjust = new TrackAdjust();
		
		for(double datasize = 0.25 * 128; datasize<=1024 *128;datasize*=2){
		Map<Route,Integer> adjustedRoutes = trackAdjust.adjustTrack(predictedRoutes, datasize, bandwidth);
		Route predictedRoute = trackpredictor.getPredictedTrack(adjustedRoutes);
		System.out.println("datasize = "+datasize/128+". The length of predicted route is "+ predictedRoute.getOverallDistance());
		trackAdjust = new TrackAdjust();
		}
//		System.out.println(WifiDistributionMap.getWifiMap(coordinates));
		
		
		
	}
	
}
