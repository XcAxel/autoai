package Util;

public class util {
	public static double gpsDistance(double lat1, double lng1, double lat2, double lng2){
	    double distance = 0;
	    double lonRes = 102900, latRes = 110000;
	    distance = Math.sqrt( Math.abs( lat1 - lat2 ) * latRes * Math.abs( lat1 - lat2 ) * latRes +
	            Math.abs( lng1 - lng2 ) * lonRes * Math.abs( lng1 - lng2 ) * lonRes );
	    //System.out.println( "两点间距离:" + distance );
	    return distance;
	}
}
