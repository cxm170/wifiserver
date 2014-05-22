package com.enda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.ProcessTraceInList;
import com.enda.usertrackprediction.Route;
import com.enda.usertrackprediction.TimestampSlot;
import com.enda.usertrackprediction.TrackAdjust;
import com.enda.usertrackprediction.TrackPrediction;
import com.enda.usertrackprediction.User;
import com.enda.util.PrintRoutesOnWeb;
import com.enda.wifiselector.Wifi;
import com.enda.wifiselector.WifiDistributionMap;

@SuppressWarnings("serial")
@WebServlet("/wifiselect")
public class WifiSelectServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = req.getParameter("name");
		String x = req.getParameter("x");
		String y = req.getParameter("y");
		String size = req.getParameter("size"); //unit: KB
		
		User user = new User(username);
		double latitude = Double.parseDouble(x);
		double longitude = Double.parseDouble(y);
		double datasize = Double.parseDouble(size);
		
		Coordinate currentLoc = new Coordinate(latitude,longitude);
		
		//Store the location of the requested user to a static data structure. This data structure
		//temporarily stores history of locations where requests happened.
		
		
		Timestamp datetime = new Timestamp(new java.util.Date().getTime());
		
		
		
		
		TempLocations.add(datetime,currentLoc);
		
		PrintWriter out = resp.getWriter();
		
		
		
		List<Coordinate> temp = new ArrayList<>();
		
		Timestamp temptime = datetime;
		
		TimestampSlot ts = new TimestampSlot();
		
		for(Entry<Timestamp, Coordinate> entry:entriesSortedByValues(TempLocations.locations)){
			Timestamp currentDatetime = entry.getKey();
			ts.setPreviousTime(currentDatetime);
			ts.setCurrentTime(temptime);
			if(ts.getTimeSlotInMillisecond()< 20000){ // add distance filter: if two coordinates have a too long distance, filter out)
				temp.add(entry.getValue());
			}
			else{
				if(TempLocations.locations.size()>50) TempLocations.clear();
				break;
			}
				
			temptime = currentDatetime;
		}
		
		Coordinate[] coordinates =  temp.toArray(new Coordinate[0]);
		
		
		
	    try {
		// The newInstance() call is a work around for some
		// broken Java implementations
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    }
	    catch (Exception E) {
		out.println("Unable to load driver.");
		E.printStackTrace();
	    }
	    
	    // Here, only getWiFiMap, instead of optimal WiFi AP.
	    
		try {
			TrackPrediction trackpredictor = new TrackPrediction(user);
			Map<Route,Integer> predictedRoutes =  trackpredictor.getPredictedTracks(coordinates);
			
			//Pre-define the bandwidth for each cloudlet.
			double bandwidth = 16*128; //unit: KB/s
					
			TrackAdjust trackAdjust = new TrackAdjust();
			
			Map<Route,Integer> adjustedRoutes = trackAdjust.adjustTrack(predictedRoutes, datasize, bandwidth);
			Route predictedRoute = trackpredictor.getPredictedTrack(adjustedRoutes);
			List<Wifi> qualifiedwifi = WifiDistributionMap.getWifiMap(predictedRoute.toCoordinates());
			
			String mode = "map";
			
			if(mode == "map"){
			
//				Coordinate[] coordinate = predictedRoute.toCoordinates();
				ProcessTraceInList pr = new ProcessTraceInList(user);
				List<Route> generatedRoutes = pr.generateListOfRoutes();
				Map<Route,Integer> refinedRoutes = pr.refineListOfRoutes(generatedRoutes);
				
				int count = 0;
				Route index = new Route();
				for(Map.Entry<Route, Integer> entry:refinedRoutes.entrySet()){
					Route rt = entry.getKey();
					if(rt.size()>count) {count = rt.size();
					index=rt;
					}
					
					
				}
				
				
				req.setAttribute("route",index);
				req.getRequestDispatcher("/WifiSelect.jsp").forward(req, resp);

				
				
			}
			if(mode == "route+wifi"){
			
			out.println("<html><body>Temporary Locations: <br>");
			out.println(TempLocations.locations);

			out.println("<br>Most recent reported locations: <br>");
			out.println(temp);
			
			out.println("<br>The predicted routes are: <br>");
			if(adjustedRoutes.size()>0){
				out.println(new PrintRoutesOnWeb<Route,Integer>(adjustedRoutes));}
			else
				out.println("No matched route is found.");
			
			

			

			out.println("<br>The optimal predicted route is: <br>");
			if(predictedRoute.size()>0){
				out.println(predictedRoute);}
			else
				out.println("No matched route is found.");
			
			
			out.println("<br>Qualified WiFi: <br>");
			
			if(predictedRoute.size()>0 && qualifiedwifi.size()>0)
				out.println(qualifiedwifi);
			else out.println("No qualified WiFi found.");
				out.println("</body></html>");
			}
			
			if(mode == "wifi"){
				if(predictedRoute.size()>0 && qualifiedwifi.size()>0)
					out.println(qualifiedwifi);
				else out.println("No qualified WiFi found.");
					out.println("</body></html>");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req,resp);
	}
	
	@SuppressWarnings("hiding")
	static <Timestamp,Coordinate>
	SortedSet<Map.Entry<Timestamp,Coordinate>> entriesSortedByValues(Map<Timestamp, Coordinate> locations) {
	    SortedSet<Map.Entry<Timestamp,Coordinate>> sortedEntries = new TreeSet<Map.Entry<Timestamp,Coordinate>>(
	        new Comparator<Map.Entry<Timestamp,Coordinate>>() {
	            @Override public int compare(Map.Entry<Timestamp,Coordinate> e1, Map.Entry<Timestamp,Coordinate> e2) {
	                return ((java.sql.Timestamp) e2.getKey()).compareTo((java.sql.Timestamp) e1.getKey());
	            }
	        }
	    );
	    sortedEntries.addAll(locations.entrySet());
	    return sortedEntries;
	}

}
