package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Util.Util;

public class test {

	public static void main(String[] args) throws Exception {
		String dateString = "Sat Nov 02 17:16:56 CST 2019";
		Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse("Sat Nov 02 17:16:56 CST 2019");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = df.format(date);
        System.out.println(d);
	}
	
	

}
