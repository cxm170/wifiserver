package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Map;

import com.enda.util.PrintRefinedRoutes;

public class DemoCollectTraceAndGenerateRoutes {
	public static void main(String [] args) throws SQLException, InterruptedException{
		User user = new User("Inman");
		
//		CollectTrace collecttrace = new CollectTrace(user);
//		Coordinate currentLoc = new Coordinate();
//		
////		double xcor = 22.303639;
////
////		double ycor = 114.179835;
//		
//		double xcor = 22.306170;
//		double ycor = 114.187281;
//		
//		boolean check = true;
//		if (!check){
//		for(int i=1;i<1000;i++){
//			double r = Math.random();
//            if      (r < 0.25) {			
//            	xcor = xcor + 0.000031 * (1-r);
//			ycor = ycor + 0.000183 * (1-r);}
//            else if (r < 0.50) {			
//            	xcor = xcor - 0.000031 * 2* r;
//			ycor = ycor - 0.000183 * 2 *r;}
//            else if (r < 0.75) {			
//            	xcor = xcor + 0.000031 * r;
//			ycor = ycor + 0.000183 * r;}
//            else if (r < 1.00) {			
//            	xcor = xcor - 0.000031 * r;
//			ycor = ycor - 0.000183 * r;}
//			
//
		//write a random walk algorithm
		//retrieve the coordinate at different time interval
		
		
		
//		currentLoc.setX(xcor); 
//		currentLoc.setY(ycor);
//		collecttrace.storeTrace(currentLoc);
//		Thread.sleep(1000);
//		}
//		}
		
		
		GenRoute genRoute = new GenRoute(user);
		//Map<TimestampSlot,Route> generatedRoutes = genRoute.getGeneratedRoutes();
		Map<Route,Integer> refinedRoutes = genRoute.getRefinedRoutes();
		
//		if (!generatedRoutes.isEmpty()){
//		System.out.println("The generated routes for "+user +" are:");
//		System.out.println(new PrintGeneratedRoutes<TimestampSlot,Route>(generatedRoutes));
//		System.out.println("The size of generatedRoute is:");
//		System.out.println(generatedRoutes.size());
//		}
		
		if( !refinedRoutes.isEmpty()){
			System.out.println("The refined routes for "+user +" are:");
			System.out.println(new PrintRefinedRoutes<Route,Integer>(refinedRoutes));
//			System.out.println("The size of refinedRoute is:");
//			System.out.println(refinedRoutes.size());
		}
		
	
	}
}
