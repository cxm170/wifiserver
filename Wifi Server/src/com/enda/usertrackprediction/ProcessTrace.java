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

	private Map<TimestampSlot,Route> generatedRoutes = new TreeMap<TimestampSlot,Route>();
	private Map<Route,Integer> refinedRoutes = new TreeMap<Route,Integer>();
	private User user;
	
	//metric unit: kilometer. The bigger this value, the less the num of generated routes will be.
	private final double DISTANCE_threshold = 0.018;
	
	//metric unit: kilometer. The bigger this value, the less the num of refined routes will be.
	private final double MERGE_threshold =  0.01;
	
	//The max Num of Coordinates in each route
	private final int NUM_COORDINDATE = 5;

	
	
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
		
		//Sort generatedRoutes on the length of Route in each entry
		//Bisection. Half part. Half part.
		//recursive bisection
		//pool the routes left. Frequency automatically increases as its route joins other route.
		
		for(Map.Entry<TimestampSlot,Route> entry:filteredRoutes.entrySet()){
			boolean whetherFind = false;
			Route processedRoute = entry.getValue();
			MergeRedundantRoutes mergeRedundantRoutes = new MergeRedundantRoutes();
			if(!refinedRoutes.isEmpty()){
			for(Map.Entry<Route, Integer> e:refinedRoutes.entrySet()){
				Route refinedRoute = e.getKey();
				int count = e.getValue().intValue();
				if(mergeRedundantRoutes.isSubSetOf(processedRoute, refinedRoute,MERGE_threshold)){
					this.refinedRoutes.put(refinedRoute, new Integer(count+1));
					whetherFind = true;
					break;}
					}
			}
			if(whetherFind == false) this.refinedRoutes.put(processedRoute, new Integer(1));
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
			if (!currentLoc.withinThreshold(previousLoc, DISTANCE_threshold)){

				this.generatedRoutes.put(timeSlot,route);
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


		this.generatedRoutes.put(timeSlot,route);
		
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
		return storedLoc;
		
	}

}
