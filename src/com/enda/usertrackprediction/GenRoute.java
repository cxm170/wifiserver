package com.enda.usertrackprediction;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class GenRoute {
	

	private ProcessTrace processTrace = new ProcessTrace();
	
	private Map<TimestampSlot, Route> generatedRoutes;
	
	public GenRoute(User user) throws SQLException, ParseException{
		 this.processTrace.setUser(user);
		 processTrace.setDISTANCE_THRESHOLD(0.1);
		 processTrace.setNUM_COORDINDATE(3);
		 processTrace.setMERGE_THRESHOLD(0.1);
		 this.generatedRoutes = processTrace.generateListOfRoutes();
		
		
	}
	
//	public Map<TimestampSlot, Route> getGeneratedRoutes(){
//				
//		return this.generatedRoutes;
//	}
	
	
	//Only invoked, generatedRoutes will be refined, which causes large amounts of computation.
	public Map<Route,Integer> getRefinedRoutes() throws SQLException, ParseException{
		 
		 
		 Map<Route,Integer> refinedRoutes = processTrace.refineListOfRoutes(generatedRoutes);
		return refinedRoutes;
	}
	
	public Map<TimestampSlot, Route> getGeneratedRoutes() throws SQLException, ParseException{
		
		return this.generatedRoutes;
	}
	
}
