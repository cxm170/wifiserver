package com.enda.usertrackprediction;

import java.sql.Timestamp;

public class TimestampSlot implements Comparable<TimestampSlot>{
	private Timestamp previousTime = new Timestamp(0);
	private Timestamp currentTime = new Timestamp(0);
	
	public TimestampSlot(){
		
	}
	
	public TimestampSlot(Timestamp previousTime, Timestamp currentTime){
		this.previousTime = previousTime;
		this.currentTime = currentTime;
	}
	
	@Override
	public String toString(){
		if (this.previousTime.getTime()!=this.currentTime.getTime())
		return "FROM \"" + this.previousTime.toString()+"\" TO \""+this.currentTime.toString() + "\"";
		else return "\"" +this.currentTime.toString()+ "\"";
	}
	
	public long getTimeSlotInMillisecond(){
		return this.currentTime.getTime()-this.previousTime.getTime();
	}
	
	public void setPreviousTime(Timestamp previousTime){
		this.previousTime=previousTime;
	}
	
	public void setCurrentTime(Timestamp currentTime){
		this.currentTime = currentTime;
	}
	
	public void setTimeSlot(Timestamp previousTime, Timestamp currentTime){
		this.previousTime = previousTime;
		this.currentTime = currentTime;
	}

	public Timestamp getPreviousTime() {
		return previousTime;
	}

	public Timestamp getCurrentTime() {
		return currentTime;
	}
	



	@Override
	public int compareTo(TimestampSlot t) {
		// TODO Auto-generated method stub
		if (this.previousTime.getTime()>t.previousTime.getTime()) 
			return 1;
		else if(this.previousTime.getTime()==t.previousTime.getTime())
			return 0;
		else return -1;
	}
}
