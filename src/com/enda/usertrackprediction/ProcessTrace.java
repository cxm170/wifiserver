package com.enda.usertrackprediction;

/**  
 * ProcessTrace: implements aggregation algorithms to process traces for each user, 
 * and store them in database servers. Table with columns of user name, route, 
 * latest visited time, frequency
 */

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;

import com.enda.mysql.QueryCoordinate;
import com.enda.util.*;

public class ProcessTrace {

	
	
	private User user;
	
	//metric unit: kilometer. The bigger this value, the less the num of generated routes will be.
	//Distance_threshold is used to differentiate different routes. 
	private final double DISTANCE_THRESHOLD = 0.15    ;
	
	
	//The min Num of Coordinates in each route
	private final int NUM_COORDINDATE = 20;
	
	//metric unit: kilometer. The bigger this value, the less the num of refined routes will be.
	private final double MERGE_THRESHOLD =  0.01;
	


	
	
	public ProcessTrace(){
		
	}
	
	public ProcessTrace(User user) {
				 this.user = user;
	}

	public void setUser(User user){
		this.user=user;
	}
	
//Compress redundant routes in generateListOfRoutes, routes refinement follows rules:
//Rule 1: Delete routes with insufficient coordinates. A distance DISTANCE should be given.
//Rule 2: Any routes, if found as sub-routes of other routes, shall be incorporated into their larger ones.
//Rule 3: Different routes may share a certain route with identical path. Thus, frequency is attached with
//these routes, in order to facilitate route choosing.
//Rule 4: Any two coordinates within a distance of NEAR_DIS shall be considered one coordinate. 
	public Map<Route,Integer> refineListOfRoutes(Map<TimestampSlot,Route> generatedRoutes){
		
		//filter short routes in generatedRoutes.
		Map<Route,Integer> refinedRoutes = new TreeMap<Route,Integer>();
		Map<TimestampSlot,Route> filteredRoutes = new TreeMap<TimestampSlot,Route>();
		
		for(Map.Entry<TimestampSlot,Route> entry:generatedRoutes.entrySet()){
			TimestampSlot timeslot = entry.getKey();
			Route unprocessedRoute = entry.getValue();
			
			//only routes with coordinates more than NUM_COORDINDATE will be selected.
			if(unprocessedRoute.size()>NUM_COORDINDATE){
				filteredRoutes.put(timeslot,unprocessedRoute);
			}
			
			
		}
		
		if (!filteredRoutes.isEmpty()){
			//System.out.println("The filtered routes for "+user +" are:");
			//System.out.println(new PrintGeneratedRoutes<TimestampSlot,Route>(filteredRoutes));
			System.out.print("The size of filteredRoutes is: ");
			System.out.println(filteredRoutes.size()+".");

			}
		
		//Generate refinedRoutes.
		//Frequency automatically increases as its route joins other route.
		
		boolean whetherFind;
		
		for(Map.Entry<TimestampSlot,Route> entry:filteredRoutes.entrySet()){
			 whetherFind = false;
			Route processedRoute = entry.getValue();
			MergeRedundantRoutes mergeRedundantRoutes = new MergeRedundantRoutes();
			if(!refinedRoutes.isEmpty()){
				int count;
			for(Map.Entry<Route, Integer> e:refinedRoutes.entrySet()){
				Route refinedRoute = e.getKey();
				count = e.getValue().intValue();
				//If refinedRoute contains processedRoute, true, then count + 1 for the refinedRoute
				if(mergeRedundantRoutes.isSubSetOf(processedRoute, refinedRoute,MERGE_THRESHOLD)){
					refinedRoutes.put(refinedRoute, new Integer(count+1));
					whetherFind = true;
					break;}
					
			//if processRoute contains refinedRoute, true, then remove refinedRoute, and add processedRoute with count+1
			if(mergeRedundantRoutes.isSubSetOf(refinedRoute,processedRoute, MERGE_THRESHOLD)){
				refinedRoutes.remove(refinedRoute);
				refinedRoutes.put(processedRoute, new Integer(count+1));
				whetherFind = true;
				break;}
			
				}
			}
			//If processRoute doesn't contain any refindedRoute, or any refinedRoute doesn't contain processRoute, add processRoute.
			if(whetherFind == false) refinedRoutes.put(processedRoute, new Integer(1));
		}
		

		if( !refinedRoutes.isEmpty()){
			//System.out.println("The refined routes for "+user +" are:");
			//System.out.println(new PrintRefinedRoutes<Route,Integer>(refinedRoute));
			System.out.print("The size of refinedRoute is: ");
			System.out.println(refinedRoutes.size()+".");
		}
		
		return refinedRoutes;
	}
	
	
	
	
// generate list of routes for target users. These routes are not compressed yet at this stage.	
	public Map<TimestampSlot,Route> generateListOfRoutes() throws SQLException {
		Map<TimestampSlot,Route> generatedRoutes = new TreeMap<TimestampSlot,Route>();
		Map<Timestamp,Coordinate> allLoc = new TreeMap<Timestamp,Coordinate>();
		
		//Invoke retrieveCoordinate(), and get all relevant coordinates
		allLoc = this.retrieveCoordinate();

		//check whether the database server has any records for this user.
		if (!allLoc.isEmpty()) {
		//iterate through all coordinates returned from database
		Map.Entry<Timestamp, Coordinate> firstEntry = ((TreeMap<Timestamp, Coordinate>) allLoc).firstEntry();
		Coordinate previousLoc = firstEntry.getValue();
		Timestamp previousDatetime = firstEntry.getKey();
		Route route = new Route();
		TimestampSlot timeSlot = new TimestampSlot(previousDatetime,previousDatetime);

		for(Map.Entry<Timestamp, Coordinate> entry:allLoc.entrySet()){
			
		
			Coordinate currentLoc = entry.getValue();
			Timestamp currentDatetime = entry.getKey();
			
			//If currentLoc is too far away from previousLoc, the route is considered reaching the end.
			if (!currentLoc.withinThreshold(previousLoc, DISTANCE_THRESHOLD)){

				generatedRoutes.put(timeSlot,route);
				previousDatetime = currentDatetime;
				previousLoc = currentLoc;
				//new route is referenced.
				route = new Route();
				route.add(currentLoc);
				//new timeSlot is referenced.
				timeSlot =new TimestampSlot(previousDatetime, currentDatetime);

			}
			//if  currentLoc is near previousLoc, it will be added into current route.
			else{
			route.add(currentLoc);
			
			previousLoc = currentLoc;
			timeSlot.setCurrentTime(currentDatetime);


			}
			
		}


		generatedRoutes.put(timeSlot,route);
		
		if (!generatedRoutes.isEmpty()){
//			System.out.println("The generated routes for "+user +" are:");
//			System.out.println(new PrintGeneratedRoutes<TimestampSlot,Route>(generatedRoutes));
			System.out.print("The size of generatedRoute is: ");
			System.out.println(generatedRoutes.size()+".");}
		
		return generatedRoutes;}
		
		//If allLoc has nothing, return routes of null.
		else{
			System.out.println("No traces are found for this user.");
			return generatedRoutes;
		}
	}
	
	//retrieve all the coordinates and their corresponding timstamp for the target user
	public Map<Timestamp,Coordinate> retrieveCoordinate() throws SQLException{
		Map<Timestamp,Coordinate> storedLoc = new TreeMap<Timestamp,Coordinate>();
		QueryCoordinate queryCoordinate = new QueryCoordinate();
		storedLoc = queryCoordinate.queryLoc(this.user);
		System.out.println("The number of recorded coordinates is " + storedLoc.size()+".");
		
		System.out.println("DISTANCE_THRESHOLD = "+DISTANCE_THRESHOLD);

		System.out.println("NUMBER_COORDINATES = "+NUM_COORDINDATE );
		System.out.println("MERGE_THRESHOLD = "+ MERGE_THRESHOLD);
		boolean countCoor = false;
		
		if(countCoor){
		
		Coordinate currentCoordinate = null;
		
		Coordinate c1 = new Coordinate(22.305098376549246,114.18095496479062);
		Coordinate c2 = new Coordinate(22.303772,114.179734);
		Coordinate c3 = new Coordinate(22.302833,114.178626);
		Coordinate c4 = new Coordinate(22.303582,114.182279);
		
		Coordinate[] c = {c1,c2,c3,c4};
		
		int count1=0,count2=0,count3=0,count4=0;
		
	
		
		int flag,i;
		double distance;
		
		for(Map.Entry<Timestamp, Coordinate> entry:storedLoc.entrySet()){
			currentCoordinate = entry.getValue();
			
			flag=0;
			distance = currentCoordinate.getDistanceFrom(c[0]);
			
			for(i=1;i<4;i++){
				if(distance > currentCoordinate.getDistanceFrom(c[i])){
					flag = i;
					distance = currentCoordinate.getDistanceFrom(c[i]);
				}
			}
			
			switch(flag){
			case 0: count1++; break;
			case 1: count2++; break;
			case 2: count3++; break;
			case 3: count4++; break;
			default:break;
			}
			

		}
	
		
		
		System.out.println("c1: "+count1);
		System.out.println("c2: "+count2);
		System.out.println("c3: "+count3);
		System.out.println("c4: "+count4);
		int all = count1+count2+count3+count4;
		System.out.println("all: "+all);}
		
		return storedLoc;
		
	}

}
