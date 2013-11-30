package com.enda.usertrackprediction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Route implements Comparable<Route>{
	private List<Coordinate> route;
	
	public Route(){
		route = new ArrayList<Coordinate>();
	}
	

	
	public List<Coordinate> getRoute(){
		return route;
	}
	
	public Coordinate[] toCoordinates(){
		return this.route.toArray(new Coordinate[0]);
	}

	public void add(Coordinate currentLoc) {
		// TODO Auto-generated method stub
		this.route.add(currentLoc);
	}
	
	public boolean addAll(int index, List<Coordinate> list){
		return this.route.addAll(index,list);
	}

	@Override
	public String toString(){
		Iterator<Coordinate> it = this.route.iterator();
		StringBuilder  returned = new StringBuilder();
//		while(it.hasNext())
//		{
//		    Coordinate c = it.next();
//		    returned.append(c.toString());
//		    
//	
//		}
		if(this.route.size()>1){
		returned.append(this.route.get(0));
		returned.append("-->");
		returned.append(this.route.get(this.route.size()-1));
		returned.append("\r\nThere are "+this.getNumOfCoordinates()+" coordinates in this route. Overall distance is "+this.getOverallDistance()+" km.");
		}
		else{
			returned.append(this.route.get(0));
			returned.append("\r\nThere is only one coordinate in this route.");	
		}
		
		return returned.toString();
		
	}
	
	
	
	

	public int size() {
		// TODO Auto-generated method stub
		return this.route.size();
	}

	@Override
	public int compareTo(Route o) {
		// TODO Auto-generated method stub
		double compareX = this.route.get(0).getX()- o.route.get(0).getX();
		double compareY = this.route.get(0).getY()- o.route.get(0).getY();
		
		if (compareX > 0 || (compareX == 0 && compareY > 0)) return 1;
		else if(compareX ==0 && compareY ==0) return 0;
		else return -1;

	}
	
	public double getOverallDistance(){
		Iterator<Coordinate> it = this.route.iterator();
		double sum = 0;
		if(!this.route.isEmpty()){
		Coordinate currentCoor;
		Coordinate previousCoor = this.route.get(0);
		
		while(it.hasNext()){
			currentCoor = it.next();
			sum+=currentCoor.getDistanceFrom(previousCoor);
			previousCoor = currentCoor;
		}
		}
		return sum;
	}
	
	public int getNumOfCoordinates(){
		return this.route.size();
	}
	
	
	public double computeDistanceFrom(Route r){
		if(this.getOverallDistance()>r.getOverallDistance())
			return 0;
		
		double distance=0;
		int index;
		if(this.getNumOfCoordinates()<r.getNumOfCoordinates()){
			index = this.getNumOfCoordinates();
		}
		else{
			index = r.getNumOfCoordinates();
		}
		
		for(int i=0;i<index;i++){
			distance+= this.route.get(i).getDistanceFrom(r.getRoute().get(i));
		}
		
		return distance;
		
	}
}
	

