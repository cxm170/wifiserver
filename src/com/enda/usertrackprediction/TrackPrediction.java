package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.enda.util.*;

public class TrackPrediction {
		
	private Coordinate[] currentLoc;
//	private Route predictedTrack = new Route();
//	private Map<Route,Integer> predictedTracks = new TreeMap<>();

	private GenRoute genRoute; 
	
	private final double MATCH_threshold = 0.003;

	
	
	public TrackPrediction(User user, Coordinate[] currentLoc) throws SQLException{
		this.currentLoc = currentLoc;
		this.genRoute = new GenRoute(user);
	}
	
//	//compare with genRoute, so as to get predicated track for target user
//	public Route getPredictedTrack(){
//		Route predictedTrack = new Route();
//		Map<Route, Integer> routesCompareTo = this.genRoute.getRefinedRoutes();
//		int frequencyOfPredictedTrack = 0;
//		
//		//prediction algorithm. Match currentLoc with all coor in routesCompareTo.
//		
//		MatchRoute matchRoute = new MatchRoute(this.currentLoc);
//		
//		Map<Route,Integer> tempMatchedRoutes = new TreeMap<Route,Integer>();
//		
//		for(Map.Entry<Route, Integer> entry:routesCompareTo.entrySet()){
//			Route routeCompareTo = entry.getKey();
//			Integer frequency = entry.getValue();
//
//			if (matchRoute.allWithinRoute(routeCompareTo, MATCH_threshold)) {
//				tempMatchedRoutes.put(routeCompareTo,frequency);
////				System.out.println(entry);
//				}
//			}
//		
//		if(!tempMatchedRoutes.isEmpty()){
//			
//		System.out.println("In all, "+tempMatchedRoutes.size()+ " routes are found matched.");	
//		
//		
//		
//		
//		//the predicted Track must start with the first item in currentLoc.
//		
//		//Choose the optimal one from adjustRoutes
//		
//		Map<Route,Integer> tempRefinedMatchedRoutes = getQualifiedRoutes(tempMatchedRoutes);
//		
//		Map.Entry<Route,Integer> maxEntry = null;
//
//		for (Map.Entry<Route,Integer> entry : tempRefinedMatchedRoutes.entrySet())
//		{
//		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
//		    {
//		        maxEntry = entry;
//		        
//		    }
//		}
//		
//		
//		predictedTrack = maxEntry.getKey();
//	
//		frequencyOfPredictedTrack = maxEntry.getValue();
//		
//		}
//
//		if(predictedTrack.size()>0){
//		System.out.println("The most matched route is:");
//		System.out.println(predictedTrack+" This route has been recorded by "+frequencyOfPredictedTrack+" times.");}
//		else{
//			System.out.println("No matched route is found.");
//		}
//		return predictedTrack;
//		
//	}
	
	
	public Route getPredictedTrack() throws SQLException{
		Map<Route,Integer> temp = getPredictedTracks();
		
		return getPredictedTrack(temp);
	}
	
	public Route getPredictedTrack(Map<Route,Integer> predictedTracks){
		int frequencyOfPredictedTrack = 0;
		Route predictedTrack = new Route();
		Map.Entry<Route,Integer> maxEntry = null;
		
		if(predictedTracks.size()>0){
		for (Map.Entry<Route,Integer> entry : predictedTracks.entrySet())
		{
		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		        
		    }
		  
		}
		
		
		predictedTrack = maxEntry.getKey();
		frequencyOfPredictedTrack = maxEntry.getValue();
		
		}

		if(predictedTrack.size()>0){
		System.out.println("The most matched route is:");
		System.out.println(predictedTrack+" This route has been recorded by "+frequencyOfPredictedTrack+" times.");}
		else{
			System.out.println("No matched route is found.");
		}
		return predictedTrack;
		
		
	}
	
	
	//Get a list of predicted routes.
	public Map<Route,Integer> getPredictedTracks() throws SQLException{
		Map<Route, Integer> routesCompareTo = this.genRoute.getRefinedRoutes();

		Map<Route,Integer> predictedTracks = new TreeMap<>();
		//prediction algorithm. Match currentLoc with all coor in routesCompareTo.
		
		MatchRoute matchRoute = new MatchRoute(this.currentLoc);
		
		Map<Route,Integer> tempMatchedRoutes = new TreeMap<Route,Integer>();
		
		for(Map.Entry<Route, Integer> entry:routesCompareTo.entrySet()){
			Route routeCompareTo = entry.getKey();
			Integer frequency = entry.getValue();

			if (matchRoute.allWithinRoute(routeCompareTo, MATCH_threshold)) {
				tempMatchedRoutes.put(routeCompareTo,frequency);
//				System.out.println(entry);
				}
			}
		
		if(!tempMatchedRoutes.isEmpty()){
			
		System.out.println("In all, "+tempMatchedRoutes.size()+ " routes are found matched.");	
		
		
		//the predicted Track must start with the first item in currentLoc.
		
		predictedTracks = getQualifiedRoutes(tempMatchedRoutes);
		}

		if(predictedTracks.size()>0){
//		System.out.println("Qualified tracks are: ");
//		System.out.println(new PrintRefinedRoutes<Route, Integer>(predictedTracks));
			}
		else{
			System.out.println("No matched routes are found.");
		}
		return predictedTracks;
	}
	
	
	//Make sure routes start with current locations
	private Map<Route,Integer> getQualifiedRoutes(Map<Route,Integer> tempMatchedRoutes){
		Map<Route,Integer> qualifiedRoutes = new TreeMap<>();
		
		for(Map.Entry<Route,Integer> entry: tempMatchedRoutes.entrySet()){
			Route rt = entry.getKey();
			
			int j;
			for(j=0;j<rt.size();j++) {
//				System.out.print(j+": "+currentLoc[0].getDistanceFrom(rt.getRoute().get(j))+", ");
				if(currentLoc[0].withinThreshold(rt.getRoute().get(j), MATCH_threshold)) {
					
					break;
				}
				
			}
			for(int i=0;i<j;i++) rt.getRoute().remove(0);
			
			Route  temprt= new Route();
			temprt.getRoute().addAll(rt.getRoute());
//			System.out.println("After Pruned: "+temprt);
			qualifiedRoutes.put(temprt,entry.getValue());
		}
		
		return qualifiedRoutes;
	}
	
	@SuppressWarnings("hiding")
	public static <Route,Integer>
	SortedSet<Map.Entry<Route,Integer>> entriesSortedByValues(Map<Route,Integer> routes) {
	    SortedSet<Map.Entry<Route,Integer>> sortedEntries = new TreeSet<Map.Entry<Route,Integer>>(
	        new Comparator<Map.Entry<Route,Integer>>() {
	            @Override public int compare(Map.Entry<Route,Integer> e1, Map.Entry<Route,Integer> e2) {
	                Integer i = e1.getValue();
	                Integer j = e2.getValue();
	            	return  ((java.lang.Integer) j).compareTo((java.lang.Integer) i);
	            }
	        }
	    );
	    sortedEntries.addAll(routes.entrySet());
	    return sortedEntries;
	}
	
}
