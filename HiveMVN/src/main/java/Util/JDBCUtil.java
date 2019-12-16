package Util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
//import org.apache.hadoop.hive.service.*;
//import com.alibaba.fastjson.JSONObject;


public class JDBCUtil {
	
	/**
	 * �ر���Դ
	 * @param con
	 * @param stat
	 * @param rs
	 */
	public static void closeAll(Connection con,Statement stat,ResultSet rs){
		try {
			if(rs!=null)rs.close();
			if(stat!=null)stat.close();
			if(con!=null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
	
	public static void closestaters(Statement stat,ResultSet rs){
		try {
			if(rs!=null)rs.close();
			if(stat!=null)stat.close();
//			if(con!=null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
	
	/**
	 * ��ȡDB����
	 * @return
	 * @throws SQLException 
	 */
//	获取HIVE连接对象connection
	public static Connection getHiveConnection(String domain,String port,String db,String uid,String pwd) throws SQLException{
//		String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
		String driverName = "org.apache.hive.jdbc.HiveDriver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url="jdbc:hive2://"+domain+":"+port+"/"+db;
		System.out.println(url);
		return DriverManager.getConnection(url,uid,pwd);
	}
	
//	
	public static String getEndTime(String ts,long timerange){
		String time = "";
		String tmpts = ts.concat("000");
		long millsec = Long.parseLong(tmpts) - timerange;
		GregorianCalendar gc = new GregorianCalendar(); 
		gc.setTimeInMillis(millsec);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = formatter.format(gc.getTime());
		return time;
	}
	
//	转换时间
	public static String getStartTime(String ts){
		String time = "";
		String tmpts = ts.concat("000");
		long millsec = Long.parseLong(tmpts);
		GregorianCalendar gc = new GregorianCalendar(); 
		gc.setTimeInMillis(millsec);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = formatter.format(gc.getTime());
		return time;
	}

//	获取文件列表
	public static List<String> getFileName(File file){
		List<String> list = new ArrayList<String>();
		File[] tmp = file.listFiles();
		for (int i = 0; i < tmp.length;i++){
			list.add(tmp[i].getName());
		}
		return list;
	}
	
//	文件初始化
	public static void FileInitialize(String path) throws Exception{
		File f = new File(path);
		if(f.exists()){
			f.delete();
			f.createNewFile();
		}else{
			f.createNewFile();
		}
	}
	
//	删除文件
	public static void Removefile(String path) throws Exception{
		File f = new File(path);
		f.deleteOnExit();
	}
	

}
