package com.enda.usertrackprediction;

public class Coordinate {
	double x;
	double y;
	
	public Coordinate(){
		this.x = 0;
		this.y = 0;
	}
	
	public Coordinate(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(Coordinate c){
		this.x=c.getX();
		this.y=c.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String toString(){
		return "<"+this.x +"," + this.y + ">"; 
	}
	
	public boolean withinThreshold(Coordinate target, double threshold){
		double distance = HaversineAlgorithm.HaversineInKM(this.getX(),this.getY(),target.getX(),target.getY());
//		System.out.println(distance);
		if (distance < threshold) 
			return true;
		else return false;
	}
	
	public double getDistanceFrom(Coordinate target){
		return HaversineAlgorithm.HaversineInKM(this.getX(),this.getY(),target.getX(),target.getY());
	}
	
	
	
}
