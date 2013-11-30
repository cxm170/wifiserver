package com.enda.usertrackprediction;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TrackAdjust {

	private  Map<Route,Integer> adjustedTracks;
	private final static  double WALKING_SPEED = 1.4; //unit:m/s
	
	public TrackAdjust(){

		adjustedTracks = new TreeMap<>();
		
	}
	
	//Unit: datasize->KB, bandwidth->KB/s
	public  Map<Route,Integer> adjustTrack(Map<Route, Integer> predictedRoutes, double datasize, double bandwidth){
		double time = datasize/bandwidth;
		
		double distance = time * WALKING_SPEED /1000; //unit:km
//		System.out.println(distance);
		
		double sum = 0;
		Route adjustedTrack = new Route();
		boolean newRoute;
		
		for(Map.Entry<Route, Integer> entry: predictedRoutes.entrySet()){
			Iterator<Coordinate> it = entry.getKey().getRoute().iterator();
			Coordinate previousCoor = entry.getKey().getRoute().get(0);
			
			newRoute = false;
			
			while(it.hasNext()){
				Coordinate currentCoor = it.next();

				adjustedTrack.getRoute().add(currentCoor);
				sum = sum + currentCoor.getDistanceFrom(previousCoor);
				previousCoor = currentCoor;
				
//				System.out.println(adjustedTrack);
				if (sum >= distance) {
					adjustedTracks.put(adjustedTrack, entry.getValue());
					adjustedTrack = new Route();
					sum = 0;
					newRoute = true;
//					System.out.println("test");
					break;
				}
			}
			if(newRoute == false) {
				//The expected distance is longer than the predicted track's distance. So the track is renounced.
//				adjustedTracks.put(adjustedTrack, entry.getValue());
			
			adjustedTrack = new Route();
			sum = 0;
			}
		}
		

		
		return adjustedTracks;
	}
	
	
}
