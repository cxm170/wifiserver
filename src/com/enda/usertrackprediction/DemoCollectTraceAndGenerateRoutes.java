package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import com.enda.util.PrintRefinedRoutes;

public class DemoCollectTraceAndGenerateRoutes {
	public static void main(String [] args) throws SQLException, InterruptedException{
		User user = new User("acli");
		
		CollectTrace collecttrace = new CollectTrace(user);
		Coordinate currentLoc = new Coordinate();
		
//		double xcor = 22.303639;
//
//		double ycor = 114.179835;
		
		double xcor = 22.306170;
		double ycor = 114.187281;
		
		currentLoc.setX(22.303772);
		currentLoc.setY(114.179734);
		
		Coordinate c1 = new Coordinate(22.305098376549246,114.18095496479062);
		Coordinate c2 = new Coordinate(22.303772,114.179734);
		Coordinate c3 = new Coordinate(22.302833,114.178626);
		Coordinate c4 = new Coordinate(22.303582,114.182279);
		
		Random random = new Random();
		int k;
		boolean check = false;
		if (check){
		for(int j=1;j<2000;j++){
			double r = Math.random();
            if      (r < 0.25) {			
//            	xcor = xcor + 0.000031 * (1-r);
//			ycor = ycor + 0.000183 * (1-r);
            	
            	for(k=1;k<random.nextInt(100);k++){
            	c1 = GenerateRandomLocation.getLocation(c1, Math.max(5,random.nextInt(25)));
            	collecttrace.storeTrace(c1);Thread.sleep(1000);
            	}
            	c1 = new Coordinate(22.305098376549246,114.18095496479062);
            	
            	}
            else if (r < 0.50) {		
//            	xcor = xcor - 0.000031 * 2* r;
//			ycor = ycor - 0.000183 * 2 *r;
            	for(k=1;k<random.nextInt(100);k++){
            	c2 = GenerateRandomLocation.getLocation(c2,Math.max(5,random.nextInt(25)));
            	collecttrace.storeTrace(c2);Thread.sleep(1000);
            	}
            	c2 = new Coordinate(22.303772,114.179734);
            	}
            else if (r < 0.75) {			
//            	xcor = xcor + 0.000031 * r;
//			ycor = ycor + 0.000183 * r;
            	for(k=1;k<random.nextInt(100);k++){
            	c3 = GenerateRandomLocation.getLocation(c3, Math.max(5,random.nextInt(25)));
            	collecttrace.storeTrace(c3);Thread.sleep(1000);
            	}
            	c3 = new Coordinate(22.302833,114.178626);
            	}
            else if (r < 1.00) {			
//            	xcor = xcor - 0.000031 * r;
//			ycor = ycor - 0.000183 * r;
            	for(k=1;k<random.nextInt(100);k++){
            	c4 = GenerateRandomLocation.getLocation(c4, Math.max(5,random.nextInt(25)));
            	collecttrace.storeTrace(c4);Thread.sleep(1000);
            	}
            	c4 = new Coordinate(22.303582,114.182279);
			}
			
//
		
		
//		currentLoc.setX(xcor); 
//		currentLoc.setY(ycor);
//		collecttrace.storeTrace(currentLoc);
		
		}
		}
		
		//write a random walk algorithm
				//retrieve the coordinate at different time interval
//				Coordinate c1 = new Coordinate(22.305098376549246,114.18095496479062);
////				Coordinate c1 = new Coordinate(22.303772,114.179734);
////				Coordinate c1 = new Coordinate(22.302833,114.178626);
////				Coordinate c1 = new Coordinate(22.303582,114.182279);
//				
//				int index = 500;
//				Coordinate[] c = new Coordinate[index];
//				c[0] = c1; 
//				int collection_rate = 1; //collection interval = num * 2000 ms
//				int count = 0;
//				for(int i=1;i<index;i++)
//				{
//					c[i] = GenerateRandomLocation.getLocation(c[i-1], 4);
//					Thread.sleep(1000);
//					
//					count ++;
//					System.out.println(c[i]);
////					System.out.println(count+": "+c[i]);
////					if (count == collection_rate) {collecttrace.storeTrace(c[i]);
////					count =0;
////					}
//				}
		

		
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
