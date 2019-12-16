package HiveMVN.HiveMVN;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import Util.JDBCUtil;



public class SqlThread extends Thread{
	private String sql = "";
	private Connection con = null;
	Statement stat=null;
	ResultSet rs=null;
	public String fg = "false";
	private static Map<String, Integer> resmap = new HashMap<String, Integer>();

	public SqlThread(String sql, Connection con) {
		super();
		this.sql = sql;
		this.con = con;
	}
	
	@Override
	public void run() {
		try {
			stat = con.createStatement();
			System.out.println("Thread Sql is executting");
			rs = stat.executeQuery(sql);
			System.out.println("Reading result thread");
			while(rs.next()){
				String res = rs.getString("mac");
//				System.out.println(decimalFormat.format(fee1));
//				System.out.println(res);
				System.out.println("Put result " + res);
				resmap.put(res, 1);
				
			}
			System.out.println("Thread was finished");
			JDBCUtil.closestaters(stat, rs);
			fg = "true";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Map<String, Integer> getResult(){
		return resmap;
	}
}
