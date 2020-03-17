package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import Util.JSONUtil;
import Util.Util;
import net.sf.json.JSONObject;


public class main {

	public static void main(String[] args) throws Exception {
//		基本配置
		String path = "Source/";
		String basepath = "BaseInfo/";
		String origpath = "OriginalData/";
		String resultpath = "ResultData/";
		String errlogpath = "ResultLog/";
		String CHB141dic = "CHB141.txt";
		String CHB0731dic = "CHB0731.txt";
		String typefile = "typefile.txt";
		String CH141excel = "CHB141.csv";
		String CHB0731excel = "CHB0731.csv";
		String filterresultfile = "filterRes.txt";
		String errlogfile = "Errlog.csv";
		String normallogfile = "Normallog.csv";
//		结果原文件名称
		String allresultfile = "all.txt";
//		String Resultfilename = "Result.xls";
//		初始化
		Map<String, Integer> headinfo = new HashMap<String, Integer>();
		String inputexcel;
		String tmpresflie;
		String headline,errhead,reporttime;
		File f,ferr,fnor;
		FileReader fr;
		BufferedReader br;
		FileWriter fwerr,fwnor;
		BufferedWriter bwerr,bwnor;
		JSONObject jsonObject;
		float mesure = 0;
		int linenum = 0;
		
//		主程序
		String type = "";
		String vin = "";
		Map<String, String> basemap;
		Map<String, String> jsonmap;
		Map<String, String> errmap = new HashMap<String, String>();
		Map<String, String> normalmap = new HashMap<String, String>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		System.out.print("Please input vin : ");
		vin = Util.getInput(sc);
		System.out.println();
		System.out.println("Start get vin type....");
		type = Util.getType(path+basepath+typefile, vin);
		if(type == "0" || type.equals("0")) {
			System.out.println("Get vin type fail,Please input type manually...");
			System.out.println("Please input 1 or 2 to choose CAN type 1 for CH141 and 2 for CH073,CH071");
			System.out.print("Please choose CAN type : ");
			type = Util.getInputType(sc);
		}
		sc.close();
//		结果文件过滤
		System.out.println("Start filte vin...");
		tmpresflie = path+resultpath+filterresultfile;
		Util.filterVin(path+resultpath+allresultfile, tmpresflie, vin);
		System.out.println("End filte vin...");
//		类型选择
		System.out.println("Start run main caculating process");
		Util.FileInitialize(path+errlogpath+errlogfile);
		Util.FileInitialize(path+errlogpath+normallogfile);
		ferr = new File(path+errlogpath+errlogfile);
		fnor = new File(path+errlogpath+normallogfile);
		fwerr = new FileWriter(ferr);
		fwnor = new FileWriter(fnor);
		bwerr = new BufferedWriter(fwerr);
		bwnor = new BufferedWriter(fwnor);
		switch(type) {
		case "1":
//			CH141
//			System.out.println("Is in "+type);
			basemap = Util.getBaseInfo(path+basepath+CHB141dic);
			mesure = Float.parseFloat(basemap.get("mesure"));
			headline = URLDecoder.decode(basemap.get("headline"),"UTF-8");
			errhead = URLDecoder.decode(basemap.get("errhead"),"UTF-8");
			bwnor.write(headline);
			bwerr.write(errhead);
			bwnor.newLine();
			bwerr.newLine();
//			Excel操作滤sheet，取表头
			inputexcel = path+origpath+CH141excel;
			if(Util.checkBank(inputexcel)) {
				headinfo = Util.getCSVheadinfo(inputexcel, headinfo);
				if(headinfo.isEmpty()) {
					System.out.println("!!!!!!!! CSV file without head in file : "+ inputexcel +" !!!!!!!!" );
				}else {
//					轮询取值方法入口——结果文件JSON转map,reporttime格式化后读取EXCEL定位后行轮询
					f = new File(tmpresflie);
					fr = new FileReader(f);
					br = new BufferedReader(fr);
					String s = br.readLine();
					Map<String, String> tmpmap = new HashMap<String, String>();
					while(s != null) {
						System.out.println("Starting processing line "+linenum);
						jsonObject = JSONObject.fromObject(s);
						jsonmap = JSONUtil.keyIterator(tmpmap, jsonObject);
						list = Util.getCSVVal(headinfo, jsonmap, basemap, errmap,normalmap,inputexcel,mesure);
//						System.out.println(errmap.isEmpty());
						if(!list.isEmpty()) {
							errmap = list.get(0);
							normalmap = list.get(1);
							if(!errmap.isEmpty()) {
								reporttime = errmap.get("Report time");
								errmap.remove("Report time");
								Set<String> errset = errmap.keySet();
								Iterator<String> iter = errset.iterator();
//								bwerr.write("-------- line : " + linenum + " Start --------");
//								bwerr.newLine();
								while(iter.hasNext()){
									String str = iter.next();
									bwerr.write(linenum + "," + reporttime + "," + str + "," + errmap.get(str));
									bwerr.newLine();
//									System.out.println("Error msg : " + str + "————————" + errmap.get(str));
								}
//								bwerr.write("-------- line : " + linenum + " End --------");
//								bwerr.newLine();
								bwerr.flush();
							}
							if(!normalmap.isEmpty()) {
								Set<String> norset = normalmap.keySet();
								Iterator<String> iter = norset.iterator();
//								bwnor.write("-------- line : " + linenum + " Start --------");
//								bwnor.newLine();
								while(iter.hasNext()){
									String str = iter.next();
									bwnor.write(linenum + "," + str + "," + normalmap.get(str));
									bwnor.newLine();
//									System.out.println(str + "————————" + normalmap.get(str));
								}
//								bwnor.write("-------- line : " + linenum + " End --------");
//								bwnor.newLine();
								bwnor.flush();
							}
							
						}else {
							System.out.println("List is empty.");
						}
						
						System.out.println("Ending processing line "+linenum);
						linenum++;
						s = br.readLine();
					}
					System.out.println();
					System.out.println("######## All case were finished. ########");
					System.out.println();
				}
			}else {
				System.out.println("Processing closed.");
			}
			bwerr.close();
			bwnor.close();
			fwerr.close();
			fwnor.close();
		break;
		case "2":
//			CH071,CH073
//			System.out.println("Is in "+type);
			basemap = Util.getBaseInfo(path+basepath+CHB0731dic);
			mesure = Float.parseFloat(basemap.get("mesure"));
			headline = URLDecoder.decode(basemap.get("headline"),"UTF-8");
			errhead = URLDecoder.decode(basemap.get("errhead"),"UTF-8");
			bwnor.write(headline);
			bwerr.write(errhead);
			bwnor.newLine();
			bwerr.newLine();
//			Excel操作滤sheet，取表头
			inputexcel = path+origpath+CHB0731excel;
			if(Util.checkBank(inputexcel)) {
				headinfo = Util.getCSVheadinfo(inputexcel, headinfo);
				if(headinfo.isEmpty()) {
					System.out.println("!!!!!!!! CSV file without head in file : "+ inputexcel +" !!!!!!!!" );
				}else {
//					轮询取值方法入口——结果文件JSON转map,reporttime格式化后读取EXCEL定位后行轮询
					f = new File(tmpresflie);
					fr = new FileReader(f);
					br = new BufferedReader(fr);
					String s = br.readLine();
					Map<String, String> tmpmap = new HashMap<String, String>();
					while(s != null) {
						System.out.println("Starting processing line "+linenum);
						jsonObject = JSONObject.fromObject(s);
						jsonmap = JSONUtil.keyIterator(tmpmap, jsonObject);
						list = Util.getCSVVal(headinfo, jsonmap, basemap, errmap,normalmap,inputexcel,mesure);
//						System.out.println(errmap.isEmpty());
						if(!list.isEmpty()) {
							errmap = list.get(0);
							normalmap = list.get(1);
							if(!errmap.isEmpty()) {
								reporttime = errmap.get("Report time");
								errmap.remove("Report time");
								Set<String> errset = errmap.keySet();
								Iterator<String> iter = errset.iterator();
//								bwerr.write("-------- line : " + linenum + " Start --------");
//								bwerr.newLine();
								while(iter.hasNext()){
									String str = iter.next();
//									System.out.println("ERROR:"+str);
									bwerr.write(linenum + "," + reporttime + "," + str + "," + errmap.get(str));
									bwerr.newLine();
//									System.out.println("Error msg : " + str + "————————" + errmap.get(str));
								}
//								bwerr.write("-------- line : " + linenum + " End --------");
//								bwerr.newLine();
								bwerr.flush();
							}
							if(!normalmap.isEmpty()) {
								Set<String> norset = normalmap.keySet();
								Iterator<String> iter = norset.iterator();
//								bwnor.write("-------- line : " + linenum + " Start --------");
//								bwnor.newLine();
								while(iter.hasNext()){
									String str = iter.next();
//									System.out.println("Normal:"+str);
									bwnor.write(linenum + "," + str + "," + normalmap.get(str));
									bwnor.newLine();
//									System.out.println(str + "————————" + normalmap.get(str));
								}
//								bwnor.write("-------- line : " + linenum + " End --------");
//								bwnor.newLine();
								bwnor.flush();
							}
							
						}else {
							System.out.println("List is empty.");
						}
						
						System.out.println("Ending processing line "+linenum);
						linenum++;
						s = br.readLine();
					}
					System.out.println();
					System.out.println("######## All case were finished. ########");
					System.out.println();
				}
			}else {
				System.out.println("Processing closed.");
			}
			bwerr.close();
			bwnor.close();
			fwerr.close();
			fwnor.close();
		break;
		default:
			System.out.println(".Type error.");
		break;
		}
		
	}

}
