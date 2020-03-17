package MakeJson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) throws Exception {
//		String path = "Source/";
//		String basepath = "Baseinfo/";
//		String basename = basepath +"baseinfo.txt";
//		String[] tmp = {};
//		File f = new File(path+basename);
//		FileReader fr = new FileReader(f);
//		BufferedReader br = new BufferedReader(fr);
//		String s = br.readLine();
//		while(s != null) {
//			System.out.println(s);
//			s = br.readLine();
////			if(s!=null && s.contains("portrait1=")) {
////				tmp = s.split(",");
////			}
//		}
		
//		for(String stm : tmp) {
//			System.out.println(stm);
//		}
		
		String tmp = "1580818281829";
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i <= 10; i++) {
			sb.append(" "+i+" "+tmp);
		}
//		String[] tmp1 = tmp.split(";");
		System.out.println(sb);
//		System.out.println(tmp.substring(tmp.length(),-3));
		
		
	}

}
