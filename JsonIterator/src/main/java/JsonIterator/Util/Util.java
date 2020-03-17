package JsonIterator.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {
//	JSON递归
	public static void keyIterator(JSONObject jsonObject,BufferedWriter allbw,BufferedWriter tsbw,BufferedWriter linebw) throws Exception {
		JSONObject tmpjsonObject = null;
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()){
		   String key = String.valueOf(keys.next());
		   String value = jsonObject.getString(key).trim();
		   if(value.startsWith("{") && value.endsWith("}")) {
			   tmpjsonObject = JSONObject.fromObject(value);
			   keyIterator(tmpjsonObject,allbw,tsbw,linebw);
		   }else if(value.startsWith("[") && value.endsWith("]")) {
			   JSONArray jsonArray = JSONArray.fromObject(value);
			   Object[] stemp = jsonArray.toArray();
			   for(int i = 0; i < stemp.length;i++) {
				   tmpjsonObject = JSONObject.fromObject(stemp[i]);
				   keyIterator(tmpjsonObject,allbw,tsbw,linebw);
			   }
		   }
		   if(!value.startsWith("{") && !value.endsWith("}") 
				   && !value.startsWith("[") && !value.endsWith("]")) {
			   String time = null;
			   if(key.contains("_ts") || key.contains("_time")) {
				   if(value.length() > 0) {
//					   System.out.println(Long.parseLong(value));
					   time = getMtime(Long.parseLong(value)); 
					   String tmp = key+"="+time;
//					   System.out.println(key + "--------- " + time);
					   tsbw.write(tmp);
					   allbw.write(tmp);
					   linebw.write(tmp+"|");
					   tsbw.flush();
					   allbw.flush();
					   linebw.flush();
					   tsbw.newLine();
					   allbw.newLine();
				   }
			   }else {
				   String tmp = key+"="+value;
//				   System.out.println(key + "--------- " + value);
				   allbw.write(tmp);
				   linebw.write(tmp+"|");
				   linebw.flush();
				   allbw.flush();
				   allbw.newLine();
			   }
		   }
		}
	}
	
//	格式化时间
	public static String getMtime(long time) throws Exception{
		Date date = new Date(time);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tmp = sdf.format(date);
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
	
	
}
