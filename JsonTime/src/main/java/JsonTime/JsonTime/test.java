package JsonTime.JsonTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import domain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {

	public static void main(String[] args) throws Exception {
//		String json = "{\"data_id\":\"867215041781358#867215041781358#577adb17-ae54-4719-bcf2-3fee75e9720e#4c87b056-9814-45ff-8618-f3aa3fc0fd15\",\"env\":{\"a_code\":\"\",\"acc_type\":\"-1\",\"app_id\":\"A0105\",\"app_package\":\"com.autoai.car\",\"app_type\":\"\",\"app_ver\":\"V1.0.19\",\"brand\":\"alps\",\"c_code\":\"\",\"c_mem_size\":\"641\",\"c_store_size\":\"8482\",\"carrier\":\"-1\",\"che_name\":\"四维智联后装\",\"che_type\":\"1\",\"contact_type\":\"\",\"country\":\"\",\"cpu\":\"arm64-v8a\",\"d_record_ts\":\"1565193603086\",\"d_start_ts\":\"1565058756627\",\"d_stop_ts\":\"1565193483160\",\"device_id\":\"867215041781358\",\"device_model\":\"D9A\",\"device_type\":\"2\",\"device_ver\":\"V1.0.84\",\"factory\":\"\",\"imei\":\"867215041781358\",\"imsi\":\"460065127028020\",\"keep_alive\":\"\",\"language\":\"\",\"link_start_ts\":\"\",\"link_stop_ts\":\"\",\"net_type\":\"1\",\"os_type\":\"1\",\"os_ver\":\"23\",\"p_code\":\"\",\"pre_server_ts\":\"\",\"screen_h\":\"600\",\"screen_orient\":\"90\",\"screen_ratio\":\"600*1024\",\"screen_size\":\"8.059777\",\"screen_w\":\"1024\",\"t_mem_size\":\"1880\",\"t_store_size\":\"10884\",\"user_id\":\"577adb17-ae54-4719-bcf2-3fee75e9720e\",\"user_name\":\"\",\"ver_code\":\"1019\",\"vin\":\"867215041781358\"},\"ext\":\"\",\"gen_ts\":\"1565193603086\",\"src_kafka_topic\":\"hz_md_parsed\",\"track\":[{\"access\":\"\",\"app_record_ts\":\"1565193603086\",\"app_start_ts\":\"1565058754921\",\"app_stop_ts\":\"\",\"err\":[],\"evt\":[],\"first_day\":\"0\",\"first_use\":\"0\",\"gps\":[],\"is_first\":\"0\",\"is_update\":\"0\",\"page\":[],\"session_id\":\"1565059490636\"}]}";
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		String value = getValue(jsonObject);
		Map<String, User> map = new HashMap<String, User>();
		User u = new User();
		String key = "didaidappstartts";
		map.put(key, u);
		System.out.println(map.get(key));
	}

	
//	格式化时间
	public static String getMtime(long time) throws Exception{
		Date date = new Date(time);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tmp = sdf.format(date);
//		System.out.println("MSec = "+tmp);
		return tmp;
	}
	
//	JSON取值
	public static String getValue(JSONObject jsonObject) throws Exception, Exception {
		String str = "null";
		String env = jsonObject.getString("env");
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("track"));
		Object[] stemp = jsonArray.toArray();
//		System.out.println(track);
		JSONObject envObj = JSONObject.fromObject(env); 
		JSONObject trackObj = jsonObject.fromObject(stemp[0]);	
		String did = envObj.getString("imei");
		String aid = envObj.getString("app_id");
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
			dstarttsdate = getMtime(Long.parseLong(dstartts));
		}
		if(dstopts.length() == 0) {
			dstopts = "null";
			dstoptsdate = "null";
		}else {
			dstoptsdate = getMtime(Long.parseLong(dstopts));
		}
		if(astartts.length() == 0) {
			astartts = "null";
			astarttsdate = "null";
		}else {
			astarttsdate = getMtime(Long.parseLong(astartts));
		}
		if(astopts.length() == 0) {
			astopts = "null";
			astoptsdate = "null";
		}else {
			astoptsdate = getMtime(Long.parseLong(astopts));
		}
		if(gents.length() == 0) {
			gents = "null";
			gentsdate = "null";
		}else {
			gentsdate = getMtime(Long.parseLong(gents));
		}
//		System.out.println(did+" "+aid+" "+astartts+" "+astopts+" "+dstartts+" "+dstopts+" "+gents
//				+" "+astarttsdate+" "+astoptsdate+" "+dstarttsdate+" "+dstarttsdate+" "+gentsdate);
		str = did+" "+aid+" "+astartts+" "+astopts+" "+dstartts+" "+dstopts+" "+gents
				+" "+astarttsdate+" "+astoptsdate+" "+dstarttsdate+" "+dstarttsdate+" "+gentsdate;
		return str;
	}
}
