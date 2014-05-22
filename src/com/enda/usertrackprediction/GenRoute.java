package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GenRoute {
	

	private ProcessTraceInList processTrace = new ProcessTraceInList();
	
	public GenRoute(User user) throws SQLException{
		 this.processTrace.setUser(user);
		
		
	}
	
//	public Map<TimestampSlot, Route> getGeneratedRoutes(){
//				
//		return this.generatedRoutes;
//	}
	
	
	//Only invoked, generatedRoutes will be refined, which causes large amounts of computation.
	public Map<Route,Integer> getRefinedRoutes() throws SQLException{
		 
		 List<Route> generatedRoutes = processTrace.generateListOfRoutes();
		 Map<Route,Integer> refinedRoutes = processTrace.refineListOfRoutes(generatedRoutes);
		return refinedRoutes;
	}
	
	public List<Route> getGeneratedRoutes() throws SQLException{
		List<Route> generatedRoutes = processTrace.generateListOfRoutes();
		return generatedRoutes;
	}
	
}
