package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.enda.util.*;

public class TrackPrediction {
		
	private Coordinate[] currentLoc;
	private Route predictedTrack = new Route();
	private Map<Route, Integer> routesCompareTo;
	private GenRoute genRoute; 
	
	private final double MATCH_threshold = 0.01;

	
	
	public TrackPrediction(User user, Coordinate[] currentLoc) throws SQLException{
		this.currentLoc = currentLoc;
		genRoute = new GenRoute(user);
	}
	
	//compare with genRoute, so as to get predicated track for target user
	public Route getPredictedTrack(){

		routesCompareTo = this.genRoute.getRefinedRoutes();

		
		//prediction algorithm. Match currentLoc with all coor in routesCompareTo.
		
		MatchRoute matchRoute = new MatchRoute(this.currentLoc);
		
		Map<Integer,Route> tempMatchedRoutes = new TreeMap<Integer,Route>();
		
		for(Map.Entry<Route, Integer> entry:routesCompareTo.entrySet()){
			Route routeCompareTo = entry.getKey();
			Integer frequency = entry.getValue();

			if (matchRoute.allWithinRoute(routeCompareTo, MATCH_threshold)) {
				tempMatchedRoutes.put(frequency,routeCompareTo);
//				System.out.println(entry);
				}
			}
		
		if(!tempMatchedRoutes.isEmpty()){
			
		System.out.println("In all, "+tempMatchedRoutes.size()+ " routes are found matched.");	
			
		this.predictedTrack = ((TreeMap<Integer, Route>) tempMatchedRoutes).lastEntry().getValue();
		}

		
		System.out.println("The most matched route is:");
		System.out.println(predictedTrack);
		return this.predictedTrack;
		
	}
	

	
}
