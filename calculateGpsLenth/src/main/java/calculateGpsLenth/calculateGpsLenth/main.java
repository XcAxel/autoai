package calculateGpsLenth.calculateGpsLenth;

import java.text.DecimalFormat;

import Util.util;

/**
 * Hello world!
 *
 */
public class main{
    public static void main( String[] args ){
    	double lat1 = 116.95400;
    	double lon1 = 39.95400;
    	double lat2 = 116.95300;
    	double lon2 = 39.95300;
    	double res = util.gpsDistance(lat1, lon1, lat2, lon2);
    	DecimalFormat df=new DecimalFormat("0.00");
    	String route = df.format(res);
    	System.out.println(route);
        
    }
}
