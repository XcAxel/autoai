package Util;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {
//	JSONµÝ¹é
	public static Map<String, String> keyIterator(Map<String, String> jsonmap,JSONObject jsonObject) throws Exception {
		JSONObject tmpjsonObject = null;
		Iterator<String> keys = jsonObject.keys();
		String key,value;
		while (keys.hasNext()){
		   key = String.valueOf(keys.next());
		   value = jsonObject.getString(key).trim();
		   if(key == "reportTime" || key.equals("reportTime")) {
			   jsonmap.put(key.toLowerCase(), Util.timeFormat(value));
			   jsonmap.put(key.toLowerCase()+"ms", value);
		   }else {
			   if(value.startsWith("{") && value.endsWith("}")) {
	//			   System.out.println("ÄÚ²ã  £¡£¡£¡£¡KEY : " + key +"====== VALUE : " + value);
				   tmpjsonObject = JSONObject.fromObject(value);
				   jsonmap.put(key.toLowerCase(), value);
				   keyIterator(jsonmap,tmpjsonObject);
			   }else if(value.startsWith("[") && value.endsWith("]")) {
	//			   System.out.println("ÄÚ²ã £¡£¡£¡£¡ KEY : " + key +"====== VALUE : " + value);
				   JSONArray jsonArray = JSONArray.fromObject(value);
				   Object[] stemp = jsonArray.toArray();
				   for(int i = 0; i < stemp.length;i++) {
					   tmpjsonObject = JSONObject.fromObject(stemp[i]);
					   jsonmap.put(key.toLowerCase(), value);
					   keyIterator(jsonmap,tmpjsonObject);
				   }
			   }else {
	//			   System.out.println("×îÖÕ²ã£¡£¡KEY : " + key +"====== VALUE : " + value);
				   jsonmap.put(key.toLowerCase(), value);
			   }			   
		   }
		}
		return jsonmap;
	}
}
