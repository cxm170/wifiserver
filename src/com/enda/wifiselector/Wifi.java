package com.enda.wifiselector;

import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.HaversineAlgorithm;

public class Wifi {
	
	private String APname;
	private double xPos;
	private double yPos;
	private int radius;
	
	
	public Wifi(String APname, double x, double y){
		this.APname = APname;
		this.xPos = x;
		this.yPos = y;
		radius = 50; //unit: meter
	}
	
	public String getAPname() {
		return APname;
	}

	public void setAPname(String aPname) {
		APname = aPname;
	}

	public Wifi(String APname,double x, double y, int r)
	{
		this.APname = APname;
		this.xPos =x;
		this.yPos = y;
		this.radius = r;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public String toString(){
		return this.APname+"@<"+this.xPos+","+this.yPos+"> Range: "+radius+" meters";
	}
	
	public double getDistanceFrom(Coordinate target){
		return HaversineAlgorithm.HaversineInKM(this.getxPos(),this.getyPos(),target.getX(),target.getY());
	}
}
