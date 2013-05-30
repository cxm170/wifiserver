package com.enda.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectMySql {
	private String userName;
	private String password;
	private String dbms, serverName,dbName;
	private int portNumber;
	
	public ConnectMySql(String dbms, String serverName,  String dbName,int portNumber, String userName, String password){
		 this.dbms = dbms;
		 this.serverName = serverName;
		 this.portNumber = portNumber;
		 this.dbName = dbName;
		 this.userName = userName;
		 this.password = password;
	}
	
	public Connection getConnection() throws SQLException {
		
	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.userName);
	    connectionProps.put("password", this.password);

	    if (this.dbms.equals("mysql")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms + "://" +
	                   this.serverName +
	                   ":" + this.portNumber + "/" + this.dbName,
	                   connectionProps);
	   
	    System.out.println("Connected to mysql database");
	    }
	    else {
	    System.out.println("Fail to connect to mysql");	
	    }
	    return conn;
	}
}
