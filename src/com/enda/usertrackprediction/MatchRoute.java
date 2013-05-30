package com.enda.usertrackprediction;

import java.util.Iterator;

public class MatchRoute {
	
	private Coordinate[] locs;
	
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
		for(int i=0;i<this.locs.length;i++){
			if (!this.isWithinRoute(this.locs[i], r, threshold)) {
//				System.out.println("This route does not match" + r);
				return false;
			}
		}
		return true;
	}

	
}
