package com.enda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enda.usertrackprediction.CollectTrace;
import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.User;

@WebServlet("/tracestore")
public class TraceStoreServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = req.getParameter("name");
		String x = req.getParameter("x");
		String y = req.getParameter("y");
		
		User user = new User(username);
		double latitude = Double.parseDouble(x);
		double longitude = Double.parseDouble(y);
		
		Coordinate currentLoc = new Coordinate(latitude,longitude);
		
		PrintWriter out = resp.getWriter();
		
	    try {
		// The newInstance() call is a work around for some
		// broken Java implementations
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    }
	    catch (Exception E) {
		out.println("Unable to load driver.");
		E.printStackTrace();
	    }
	    
		try {
			CollectTrace collecttrace = new CollectTrace(user);
			collecttrace.storeTrace(currentLoc);
			out.println(currentLoc+" has been added succesfully.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
	

}
