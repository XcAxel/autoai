package HiveMVN.HiveMVN;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

import Util.JDBCUtil;

public class main {

	public static void main(String[] args) throws SQLException, InterruptedException {
//		Flie Storage infomation
		String inputfile = "";
		String outputpath = "";
//		TimeRange
		long timerange = 1101010l;
		
//		HIVE information
//		String domain = "master2.test.hadoop.domain";
		String domain = "hiveUAT";
//		String port = "10002";
		String port = "10000";
		String uid = "hadoop";
		String pwd = "";
		String db = "kittx_osp_hz";
		
//		SQL
//		String sql1 = "select count(distinct mac) from bdb.vodplay where partner = 'JS_CMCC_CP' and day = '2019-01-24' limit 10";
		String sql1 = "select 10/5";
//		String sql2 = "select mac from bdb.vodplay where partner = 'JS_CMCC_CP' and day = '2019-01-24' group by mac limit 10";
//		String sql2 = "select 100/2";
		
//		���
		Boolean flag = true;
		
		Connection con=null;
		Statement stat1=null;
//		Statement stat2=null;
		ResultSet rs1=null;
//		ResultSet rs2=null;
		
		con = JDBCUtil.getHiveConnection(domain, port, db, uid, pwd);
		System.out.println("connection is established");
		stat1 = con.createStatement();
		Thread mainth = Thread.currentThread();
//		SqlThread subth1 = new SqlThread(sql2, con);
//		stat2 = con.createStatement();
//		subth1.start();
		System.out.println("Starting SQL1");
		rs1 = stat1.executeQuery(sql1);
//		System.out.println("Starting SQL2");
//		rs2 = stat2.executeQuery(sql2);
		System.out.println("SQL executing finish");
		while(rs1.next()){
			System.out.println("Reading result main");
			int res1 = rs1.getInt(1);
//			System.out.println(decimalFormat.format(fee1));
			System.out.println("Main SQL result is " + res1);
		}
//		while(rs2.next()){
//			System.out.println("Reading rsult2");
//			String res2 = rs2.getString("mac");
////			System.out.println(decimalFormat.format(fee1));
//			System.out.println(res2);
//		}

		System.out.println("Main Thread job is finished");
//		while(flag){
//			if(subth1.fg.equals("true")){
//				flag = false;
//			}
//			mainth.sleep(10000);
//			System.out.println("Waitting SubThread job finish.......");
//		}
//		Map<String, Integer> subresult = subth1.getResult();
//		Set<String> key = subresult.keySet();
//		for(String a : key){
//			System.out.println("Key = " + a);
//			System.out.println("Result = " + subresult.get(a));
//			
//		}
//		
//		JDBCUtil.closeAll(con, stat1, rs1);
//		subth1.stop();
		System.out.println("ALL FINISH");
		
	}

}
