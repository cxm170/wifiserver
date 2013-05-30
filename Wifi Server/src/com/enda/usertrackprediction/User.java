package com.enda.usertrackprediction;

public class User {
	private String username;
	
	public User(){
		
	}
	public User(String username){
		this.username = username;
	}
	
	public String getUser(){
		return username;
	}
	
	public void setUser(String username){
		this.username = username;
	}
	
	@Override
	public String toString(){
		return this.username;
	}
}
