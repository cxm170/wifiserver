package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class GenRoute {
	

	private ProcessTrace processTrace = new ProcessTrace();
	
	public GenRoute(User user) throws SQLException{
		 this.processTrace.setUser(user);
		
		
	}
	
//	public Map<TimestampSlot, Route> getGeneratedRoutes(){
//				
//		return this.generatedRoutes;
//	}
	
	
	//Only invoked, generatedRoutes will be refined, which causes large amounts of computation.
	public Map<Route,Integer> getRefinedRoutes() throws SQLException, ParseException{
		 
		 Map<TimestampSlot, Route> generatedRoutes = processTrace.generateListOfRoutes();
		 Map<Route,Integer> refinedRoutes = processTrace.refineListOfRoutes(generatedRoutes);
		return refinedRoutes;
	}
	
	public Map<TimestampSlot, Route> getGeneratedRoutes() throws SQLException, ParseException{
		Map<TimestampSlot, Route> generatedRoutes = processTrace.generateListOfRoutes();
		return generatedRoutes;
	}
	
}
