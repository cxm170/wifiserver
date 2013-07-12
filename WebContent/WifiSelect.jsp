<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*,com.enda.usertrackprediction.*,com.enda.servlet.*" %>
<!DOCTYPE html>

<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Simple Polylines</title>

<style type="text/css">
	html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}

#map-canvas, #map_canvas {
  height: 100%;
}

@media print {
  html, body {
    height: auto;
  }

  #map-canvas, #map_canvas {
    height: 650px;
  }
}

#panel {
  position: absolute;
  top: 5px;
  left: 50%;
  margin-left: -180px;
  z-index: 5;
  background-color: #fff;
  padding: 5px;
  border: 1px solid #999;
}

	</style>





    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
function initialize() {
    <% Route predictedRoute= (Route)request.getAttribute("route");
    Coordinate[] coors = predictedRoute.toCoordinates();%>
	
	  var flightPlanCoordinates = [
	                         

								<% for(int i=0;i<coors.length;i++){ 
	                             	  out.println("new google.maps.LatLng("+coors[i].getX()+","+coors[i].getY()+"),");
	                             	  
	                               }%>
	                             	  
	                                        
	                           ];
	var centerX = <% out.println(coors[0].getX());%>;
	var centerY = <% out.println(coors[0].getY());%>;
	
	
  var myLatLng = new google.maps.LatLng(centerX, centerY);
  var mapOptions = {
    zoom: 17,
    center: myLatLng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);


  var flightPath = new google.maps.Polyline({
    path: flightPlanCoordinates,
    strokeColor: '#FF0000',
    strokeOpacity: 1.0,
    strokeWeight: 2
  });

  flightPath.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
  </head>
  <body>
    <div id="map-canvas"></div>
  </body>
</html>