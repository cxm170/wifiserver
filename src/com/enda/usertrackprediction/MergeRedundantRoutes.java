package com.enda.usertrackprediction;

import java.util.Iterator;

public class MergeRedundantRoutes {


		
	public MergeRedundantRoutes(){
		
	}
	
	public boolean isSubSetOf(Route r1, Route r2, double threshold){
		int sizediff = r2.size() - r1.size();
		
		if(sizediff<0) return false;
		
		Iterator<Coordinate> it1 = r1.getRoute().iterator(); 
		int cursor = 0;
		int startIndex = 0;
		int maxIndex =  sizediff;
		int steps = 0;
		boolean success = false;
		while(it1.hasNext()){
			Coordinate coor1 = it1.next();

			for(int i=startIndex;i<=maxIndex;i++){
				Coordinate coor2 = r2.getRoute().get(i);
				cursor = i;
				if(coor1.withinThreshold(coor2, threshold)) {
					success = true;
					break;
				}
			}
			if(success == false) return false;
			steps++;
			startIndex = cursor + 1;
			maxIndex = sizediff + steps;
			
		}
		return true;
		
	}
	
}
