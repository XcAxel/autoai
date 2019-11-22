package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import domain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {
//	JSON递归
	public static void keyIterator(JSONObject jsonObject,BufferedWriter allbw,BufferedWriter tsbw) throws Exception {
		JSONObject tmpjsonObject = null;
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()){
		   String key = String.valueOf(keys.next());
		   String value = jsonObject.getString(key).trim();
		   if(value.startsWith("{") && value.endsWith("}")) {
			   tmpjsonObject = JSONObject.fromObject(value);
			   keyIterator(tmpjsonObject,allbw,tsbw);
		   }else if(value.startsWith("[") && value.endsWith("]")) {
			   JSONArray jsonArray = JSONArray.fromObject(value);
			   Object[] stemp = jsonArray.toArray();
			   for(int i = 0; i < stemp.length;i++) {
				   tmpjsonObject = JSONObject.fromObject(stemp[i]);
				   keyIterator(tmpjsonObject,allbw,tsbw);
			   }
		   }
		   if(!value.startsWith("{") && !value.endsWith("}") 
				   && !value.startsWith("[") && !value.endsWith("]")) {
			   String time = null;
			   if(key.contains("_ts") || key.contains("_time")) {
				   if(value.length() > 0) {
//					   System.out.println(Long.parseLong(value));
					   time = getFormattime(Long.parseLong(value)); 
					   String tmp = key+"="+time;
//					   System.out.println(key + "--------- " + time);
					   tsbw.write(tmp);
					   allbw.write(tmp);
					   tsbw.flush();
					   allbw.flush();
					   tsbw.newLine();
					   allbw.newLine();
				   }
			   }else {
				   String tmp = key+"="+value;
//				   System.out.println(key + "--------- " + value);
				   allbw.write(tmp);
				   allbw.flush();
				   allbw.newLine();
			   }
		   }
		}
	}
	
//	格式化时间
	public static String getFormattime(long time) throws Exception{
		Date date = new Date(time);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String tmp = sdf.format(date).replace(" ", "T");
//		System.out.println("MSec = "+tmp); 
		return tmp;
	}
	
//	检查文件夹
	public static void checkDir(String dirpath) {
		File dir = new File(dirpath);
		if(!dir.exists()) {
			dir.mkdir();
		}
	}
	
//	检查输入文件
	public static void checkInputFile(String filepath) throws Exception {
		File f = new File(filepath);
		if(!f.exists()) {
			f.createNewFile();
		}
	}
	
//	输出临时文件初始化
	public static void FileInitialize(String path) throws Exception{
		File f = new File(path);
		if(f.exists()){
//			String newfilepath = path.replace(".txt", "copy.txt");
//			File newf = new File(newfilepath);
//			newf.createNewFile();
			f.delete();
			f.createNewFile();
		}else{
			f.createNewFile();
		}
	}
	
//	JSON取值
	public static String getValue(JSONObject jsonObject,String timeLimit) throws Exception, Exception {
		String str = "null";
		String aid = "null";
		String env = jsonObject.getString("env");
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("track"));
		Object[] stemp = jsonArray.toArray();
//		System.out.println(track);
		JSONObject envObj = JSONObject.fromObject(env); 
		JSONObject trackObj = jsonObject.fromObject(stemp[0]);	
		String did = envObj.getString("imei");
		try {
			aid = envObj.getString("app_id");
			
		} catch (Exception e) {
			// TODO: handle exception
			aid = trackObj.getString("app_id");
		}
		String dstartts = envObj.getString("d_start_ts");
		String dstarttsdate = "";
		String dstopts = envObj.getString("d_stop_ts");
		String dstoptsdate = "";
		String astartts = trackObj.getString("app_start_ts");
		String astarttsdate = "";
		String astopts = trackObj.getString("app_stop_ts");	
		String astoptsdate = "";
		String gents = jsonObject.getString("gen_ts");
		String gentsdate = "";
//		System.out.println(astopts.length());
		if(dstartts.length() == 0) {
			dstartts = "null";
			dstarttsdate = "null";
		}else {
			dstarttsdate = getFormattime(Long.parseLong(dstartts));
		}
		if(dstopts.length() == 0) {
			dstopts = "null";
			dstoptsdate = "null";
		}else {
			dstoptsdate = getFormattime(Long.parseLong(dstopts));
		}
		if(astartts.length() == 0) {
			astartts = "null";
			astarttsdate = "null";
		}else {
			astarttsdate = getFormattime(Long.parseLong(astartts));
		}
		if(astopts.length() == 0) {
			astopts = "null";
			astoptsdate = "null";
		}else {
			astoptsdate = getFormattime(Long.parseLong(astopts));
		}
		if(gents.length() == 0) {
			gents = "null";
			gentsdate = "null";
		}else {
			gentsdate = getFormattime(Long.parseLong(gents));
		}
//		System.out.println(did+" "+aid+" "+astartts+" "+astopts+" "+dstartts+" "+dstopts+" "+gents
//				+" "+astarttsdate+" "+astoptsdate+" "+dstarttsdate+" "+dstarttsdate+" "+gentsdate);
		str = did+" "+aid+" "+astartts+" "+astopts+" "+dstartts+" "+dstopts+" "+gents + " " +timeLimit
				+" "+astarttsdate+" "+astoptsdate+" "+dstarttsdate+" "+dstarttsdate+" "+gentsdate;
		return str;
	}
	
//	计算时长
	public static Map<String, User> calculateTime(String did,String aid,String astartts, String astopts,String gents){
		Map<String, User> map = new HashMap<String, User>();
		
		return map;
	}
	
//	获取毫秒
	public static String getMtime(String time) throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.parse(time);
		String tmp = date.getTime() + "";
		return tmp;
	}
	
//	App using time method
	public static void appUseTimeMethod(Map<String, User> map,String did,String aid,long appstartts,long appstopts,long gents,long timeLimit,String appstartdate,int Appdiff) {
		String key = did + aid + appstartts;
		String value = map.get(key)+"";
		if(value == "null" || value.equals("null")) {
			User u = new User(did, aid, appstartts, appstopts, gents, timeLimit, appstartdate, Appdiff);
			map.put(key, u);
		}else {
			User tmp = map.get(key);
			long tmpgents = tmp.getGents();
			if(gents-tmpgents > 0) {
				tmp.setGents(gents);
				map.put(key, tmp);
			}
		}
	}
	
//	Dev using time method
	public static void devUseTimeMethod(Map<String, User> map,String did,String aid,long devicestartts,long devicestopts,long gents,long timeLimit,String devicestartdate,String appstartdate,int Devdiff) {
		String key = did +devicestartts;
		String value = map.get(key)+"";
		if(value == "null" || value.equals("null")) {
			User u = new User(did, aid, devicestartts, devicestopts, gents, timeLimit, devicestartdate
					, appstartdate, Devdiff);
			map.put(key, u);
		}
		else {
			User tmp = map.get(key);
			long tmpgents = tmp.getGents();
			if(gents-tmpgents > 0) {
				tmp.setGents(gents);
				map.put(key, tmp);
			}
		}
	}
	
//	交互
	public static String welcomeIndex() {
		String type = "";
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		boolean flag = true;
		while(flag) {
			System.out.println("APP using time please choose ‘1’，Device using time please choose ‘2’ ");
			System.out.print("Please input type : ");
			type = sc.next().trim();
			if(type == "1" || type == "2" || type.equals("1") || type.equals("2")) {
				flag = false;
			}else {
				System.out.println("!!!!!!!! Input error,Please input '1' or '2' !!!!!!!!");
			}
		}
		sc.close();
		return type;
	}
	
//	type转中文
	public static String typeTrans(String type) {
		String ch = "";
		if(type == "1" || type.equals("1")) {
			ch = "App";
		}else {
			ch = "Device";
		}
		return ch;
	}
}
