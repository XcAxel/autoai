package MakeJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		map.put("111", "222");
		map.put("333", "444");
		list.add(map);
		JSONArray jsonlist=JSONArray.fromObject(list);
		JSONObject jsonmap = JSONObject.fromObject(map);
		System.out.println(jsonlist.toString());
		System.out.println("aaaa");
		System.out.println(jsonmap.toString());
		
		String tmp = "device_id=iugswfi，hqwoigf，iwuhfsoiqw，shigdew；app_id=iwiuhf；vin=a64646";
		if(tmp.contains("；")) {
			String t1 = tmp.replace("；", ";");
			System.out.println(tmp);
			System.out.println(t1);
		}
		if(tmp.contains("，")) {
			System.out.println("#######################");
			String t1 = tmp.replace("#", "&");
			System.out.println(tmp);
			System.out.println(t1);
			System.out.println("#######################");
		}
		String[] sp1 = tmp.split(";");
		System.out.println("Len = "+sp1.length);
		System.out.println(sp1[0]);
		

	}

}
