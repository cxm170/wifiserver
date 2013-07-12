package com.enda.wifiselector;

import java.sql.SQLException;

import com.enda.mysql.RegisterWifi;

public class DemoRegisterNewWifi {
	public static void main(String [] args) throws SQLException{
		Wifi newwifi = new Wifi("PolyuWLAN",22.3044967,114.1807311);
		
		new RegisterWifi().registerWifi(newwifi);
	}

}
