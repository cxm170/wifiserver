package com.enda.usertrackprediction;

public class Coordinate {
	double latitude;
	double longitude;
	
	public Coordinate(){
		this.latitude = 0;
		this.longitude = 0;
	}
	
	public Coordinate(double x, double y){
		this.latitude = x;
		this.longitude = y;
	}
	
	public Coordinate(Coordinate c){
		this.latitude=c.getX();
		this.longitude=c.getY();
	}

	//get latitude
	public double getX() {
		return this.latitude;
	}

	public void setX(double x) {
		this.latitude = x;
	}

	//get longitude
	public double getY() {
		return this.longitude;
	}

	public void setY(double y) {
		this.longitude = y;
	}
	
	@Override
	public String toString(){
		return "<"+this.latitude +"," + this.longitude + ">"; 
	}
	
	public boolean withinThreshold(Coordinate target, double threshold){
		double distance = HaversineAlgorithm.HaversineInKM(this.getX(),this.getY(),target.getX(),target.getY());
//		System.out.println(distance);
		if (distance <= threshold) 
			return true;
		else return false;
	}
	
	public double getDistanceFrom(Coordinate target){
		return HaversineAlgorithm.HaversineInKM(this.getX(),this.getY(),target.getX(),target.getY());
	}
	
	
	
}
