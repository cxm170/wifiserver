package com.enda.usertrackprediction;

import java.util.Iterator;

public class MergeRedundantRoutes {


		
	public MergeRedundantRoutes(){
		
	}
	
	public boolean isSubSetOf(Route r1, Route r2, double threshold){
		int sizediff = r2.size() - r1.size();
		
		if(sizediff<0) return false;
		
		Iterator<Coordinate> it1 = r1.getRoute().iterator(); 
		while(it1.hasNext()){
			Coordinate coor1 = it1.next();
			int cursor = 0;
			int startIndex = 0;
			int maxIndex = sizediff+1;
			int steps = 0;
			for(int i=startIndex;i<=maxIndex;i++){
				Coordinate coor2 = r2.getRoute().get(i);
				cursor = i;
				if(coor1.withinThreshold(coor2, threshold)) break;
				steps++;
			}
			if(cursor==maxIndex) return false;
			startIndex = cursor + 1;
			maxIndex = startIndex + sizediff - steps;
			
		}
		return true;
		
	}
	
}
