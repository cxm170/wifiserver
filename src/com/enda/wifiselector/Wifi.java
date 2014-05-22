package com.enda.wifiselector;

import java.util.List;

import com.enda.usertrackprediction.Coordinate;
import com.enda.usertrackprediction.HaversineAlgorithm;

public class Wifi {
	
	private String APname;
	private double xPos;
	private double yPos;
	private int radius;
	private List<Coordinate> coorsContained;
	private String MAC;
	
	
	public Wifi(){
		
	}
	
	public Wifi(String APname, double x, double y, String MAC){
		this.APname = APname;
		this.xPos = x;
		this.yPos = y;
		radius = 50; //unit: meter
		this.MAC=MAC;
	}
	
	public String getAPname() {
		return APname;
	}

	public void setAPname(String aPname) {
		APname = aPname;
	}

	public Wifi(String APname,double x, double y, int r, String MAC)
	{
		this.APname = APname;
		this.xPos =x;
		this.yPos = y;
		this.radius = r;
		this.MAC=MAC;
	}
	
	public Wifi(String APname, double x, double y, String MAC, List<Coordinate> coorsContained){
		this.APname=APname;
		this.xPos=x;
		this.yPos=y;
		this.coorsContained=coorsContained;
		this.MAC=MAC;
	}

	public String getMAC() {
		return MAC;
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
		return this.APname+"@<"+this.xPos+","+this.yPos+"> Range: "+radius+" meters Coverage:" + this.coorsContained;
	}
	
	public double getDistanceFrom(Coordinate target){
		return HaversineAlgorithm.HaversineInKM(this.getxPos(),this.getyPos(),target.getX(),target.getY());
	}

	public List<Coordinate> getCoorsContained() {
		return coorsContained;
	}

	public void setCoorsContained(List<Coordinate> coorsContained) {
		this.coorsContained = coorsContained;
	}
	
	
}
