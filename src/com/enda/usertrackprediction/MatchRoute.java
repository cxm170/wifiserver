package com.enda.usertrackprediction;

import java.util.Iterator;

public class MatchRoute {
	
	//currentLoc[0] is current location
	//currentLoc[1] is the latest location from current location
	//currentLoc[2] is the second latest location from current location
	//and so on.
	private Coordinate[] locs;
	
	public MatchRoute(){
		
	}
	
	public MatchRoute(Coordinate[] locs){
		this.locs=locs;
	}
	
	public boolean isWithinRoute(Coordinate loc, Route r, double threshold){
		Iterator<Coordinate> it = r.getRoute().iterator();
		while(it.hasNext()){
			Coordinate coor = it.next();
			if(loc.withinThreshold(coor, threshold)) return true;
		}
		return false;
		}
	
	public boolean allWithinRoute(Route r, double threshold){
			int i = locs.length - 1;
			Iterator<Coordinate> it = r.getRoute().iterator();
			while(it.hasNext()){
				Coordinate coor = it.next();
				if(locs[i].withinThreshold(coor, threshold)) i--;
				if(i==-1) break;
			}
			
			
//			if (!this.isWithinRoute(this.locs[i], r, threshold)) {
////				System.out.println("This route does not match" + r);
//				return false;
//			}
		
		if(i==-1)
			return true;
		else
			return false;
	}

	
}
