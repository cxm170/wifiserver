package com.enda.usertrackprediction;

import java.awt.Canvas;

public class DrawPredictedRoute {
	Canvas can;
	Route route;
	
	public DrawPredictedRoute(Route route){
		can = new Canvas();
		can.setSize(550,550);
		this.route = route;
	}
	
	public void drawRoute(){
		Coordinate[] coors = route.toCoordinates();
		
	}
	

}
