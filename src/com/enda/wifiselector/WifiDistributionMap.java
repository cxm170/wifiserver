package com.enda.wifiselector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.enda.mysql.QueryWifi;
import com.enda.usertrackprediction.Coordinate;

public class WifiDistributionMap {
	
	
	public static List<Wifi> getWifiMap(Coordinate[] coordinates) throws SQLException{
		List<Wifi> qualifiedWifi = new ArrayList<>();
		QueryWifi queryWifi = new QueryWifi();
		List<Wifi> wifiLocations = queryWifi.getWifi();
		Iterator<Wifi> it = wifiLocations.iterator();
		boolean isQualified = true;
		while(it.hasNext()){
			Wifi wifi = it.next();
			//Qualified Wifi should cover all coordinates reported.
			for(int i=0;i<coordinates.length;i++){
				if(wifi.getDistanceFrom(coordinates[i])>(wifi.getRadius()/1000)){
					isQualified = false;
					break;}
			}
			
			if(isQualified){
				qualifiedWifi.add(wifi);
			}
		}

		return qualifiedWifi;
	}

}
