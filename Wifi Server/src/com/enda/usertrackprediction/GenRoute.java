package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class GenRoute {
	
	private Map<TimestampSlot, Route> generatedRoutes = new TreeMap<TimestampSlot,Route>();
	private Map<Route,Integer> refinedRoutes = new TreeMap<Route,Integer>();
	private ProcessTrace processTrace = new ProcessTrace();
	
	public GenRoute(User user) throws SQLException{
		 this.processTrace.setUser(user);
		generatedRoutes = processTrace.generateListOfRoutes();
		
	}
	
	public Map<TimestampSlot, Route> getGeneratedRoutes(){
				
		return this.generatedRoutes;
	}
	
	
	//Only invoked, generatedRoutes will be refined, which causes large amounts of computation.
	public Map<Route,Integer> getRefinedRoutes(){
		refinedRoutes = processTrace.refineListOfRoutes(generatedRoutes);
		return this.refinedRoutes;
	}
}
