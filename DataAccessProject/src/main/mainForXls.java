package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import Util.JSONUtil;
import Util.Util;
import net.sf.json.JSONObject;


public class mainForXls {

	public static void main(String[] args) throws Exception {
//		基本配置
		String path = "Source/";
		String basepath = "Baseinfo/";
		String origpath = "OriginalData/";
		String resultpath = "ResultData/";
		String errlogpath = "Errlog/";
		String CH141dic = "CH141.txt";
		String CHB0731dic = "CHB0731.txt";
		String typefile = "typefile.txt";
		String CH141excel = "CH141.xls";
		String CHB0731excel = "CHB0731.xls";
		String filterresultfile = "filterRes.txt";
		String errlogfile = "errlog.txt";
		String normallogfile = "normallog.csv";
		int exceloffset = 1;
//		结果原文件名称
		String allresultfile = "all.txt";
//		String Resultfilename = "Result.xls";
//		初始化
		HSSFWorkbook workbook ;
		List<String> sheetlist;
		HSSFSheet Sheet;
		Row row = null;
		Map<String, Integer> headinfo;
		String inputexcel;
		String tmpresflie;
		int sheetlistsize = 0;
		String contentindex = "测试用例";
		File f,ferr,fnor;
		FileReader fr;
		BufferedReader br;
		FileWriter fwerr,fwnor;
		BufferedWriter bwerr,bwnor;
		JSONObject jsonObject;
		float mesure = 0;
		int linenum = 1;
		
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
			basemap = Util.getBaseInfo(path+basepath+CH141dic);
			mesure = Float.parseFloat(basemap.get("mesure"));
			bwnor.write("行号,字段名称,ReportTime毫秒,原始值,因子,偏移量,计算结果,后台程序结果,偏差,差异标识");
			bwnor.newLine();
			//			Excel操作滤sheet，取表头
			inputexcel = path+origpath+CH141excel;
			if(Util.checkBank(inputexcel)) {
				workbook = Util.openXlsfile(inputexcel);
				sheetlist = Util.getXlssheetname(workbook, contentindex);
				sheetlistsize = sheetlist.size();
				if(sheetlistsize == 0) {
					System.out.println("!!!!!!!! There are no sheets name with '测试用例' in file : "+ inputexcel +" !!!!!!!!" );
				}else {
					String sheetname = sheetlist.get(0);
					System.out.println("######## Starting Processing Sheet : "+inputexcel+"——"+sheetname+ " ########");
					System.out.println();
					Sheet = Util.getXlssheet(workbook, sheetname); 
//						获取表头内容以及定位
					headinfo = Util.getXlsCellPosition(Sheet, row);
//					Util.printIntegerMap(headinfo);
					if(headinfo.isEmpty()) {
						System.out.println("!!!!!!!! "+ sheetname + " this sheet is empty please delete empty sheets like "
								+ "this one. !!!!!!!!");
						System.out.println();
						break;
					}else {
//						轮询取值方法入口——结果文件JSON转map,reporttime格式化后读取EXCEL定位后行轮询
						f = new File(tmpresflie);
						fr = new FileReader(f);
						br = new BufferedReader(fr);
						String s = br.readLine();
						Map<String, String> tmpmap = new HashMap<String, String>();
						while(s != null) {
							System.out.println("Starting processing line "+linenum);
							jsonObject = JSONObject.fromObject(s);
							jsonmap = JSONUtil.keyIterator(tmpmap, jsonObject);
							list = Util.getXlsCellVal(headinfo, jsonmap, basemap, errmap,normalmap, Sheet, exceloffset,mesure);
//							System.out.println(errmap.isEmpty());
							if(!list.isEmpty()) {
								errmap = list.get(0);
								normalmap = list.get(1);
								if(!errmap.isEmpty()) {
									Set<String> errset = errmap.keySet();
									Iterator<String> iter = errset.iterator();
									bwerr.write("-------- line : " + linenum + " Start --------");
									bwerr.newLine();
									while(iter.hasNext()){
										String str = iter.next();
										bwerr.write("Error msg : " + str + "————————" + errmap.get(str));
										bwerr.newLine();
//										System.out.println("Error msg : " + str + "————————" + errmap.get(str));
									}
									bwerr.write("-------- line : " + linenum + " End --------");
									bwerr.newLine();
									bwerr.flush();
								}
								if(!normalmap.isEmpty()) {
									Set<String> norset = normalmap.keySet();
									Iterator<String> iter = norset.iterator();
//									bwnor.write("-------- line : " + linenum + " Start --------");
//									bwnor.newLine();
									while(iter.hasNext()){
										String str = iter.next();
										bwnor.write(linenum + "," + str + "," + normalmap.get(str));
										bwnor.newLine();
//										System.out.println(str + "————————" + normalmap.get(str));
									}
//									bwnor.write("-------- line : " + linenum + " End --------");
//									bwnor.newLine();
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
						System.out.println("######## "+ sheetname + "'s case is finished. ########");
						System.out.println();
					}
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
			bwnor.write("行号,字段名称,ReportTime毫秒,原始值,因子,偏移量,计算结果,后台程序结果,偏差,差异标识");
			bwnor.newLine();
			//			Excel操作滤sheet，取表头
			inputexcel = path+origpath+CHB0731excel;
			if(Util.checkBank(inputexcel)) {
				workbook = Util.openXlsfile(inputexcel);
				sheetlist = Util.getXlssheetname(workbook, contentindex);
				sheetlistsize = sheetlist.size();
				if(sheetlistsize == 0) {
					System.out.println("!!!!!!!! There are no sheets name with '测试用例' in file : "+ inputexcel +" !!!!!!!!" );
				}else {
					String sheetname = sheetlist.get(0);
					System.out.println("######## Starting Processing Sheet : "+inputexcel+"——"+sheetname+ " ########");
					System.out.println();
					Sheet = Util.getXlssheet(workbook, sheetname); 
//						获取表头内容以及定位
					headinfo = Util.getXlsCellPosition(Sheet, row);
//					Util.printIntegerMap(headinfo);
					if(headinfo.isEmpty()) {
						System.out.println("!!!!!!!! "+ sheetname + " this sheet is empty please delete empty sheets like "
								+ "this one. !!!!!!!!");
						System.out.println();
						break;
					}else {
//						轮询取值方法入口——结果文件JSON转map,reporttime格式化后读取EXCEL定位后行轮询
						f = new File(tmpresflie);
						fr = new FileReader(f);
						br = new BufferedReader(fr);
						String s = br.readLine();
						Map<String, String> tmpmap = new HashMap<String, String>();
						while(s != null) {
							System.out.println("Starting processing line "+linenum);
							jsonObject = JSONObject.fromObject(s);
							jsonmap = JSONUtil.keyIterator(tmpmap, jsonObject);
							list = Util.getXlsCellVal(headinfo, jsonmap, basemap, errmap,normalmap, Sheet, exceloffset,mesure);
//							System.out.println(errmap.isEmpty());
							if(!list.isEmpty()) {
								errmap = list.get(0);
								normalmap = list.get(1);
								if(!errmap.isEmpty()) {
									Set<String> errset = errmap.keySet();
									Iterator<String> iter = errset.iterator();
									bwerr.write("-------- line : " + linenum + " Start --------");
									bwerr.newLine();
									while(iter.hasNext()){
										String str = iter.next();
										bwerr.write("Error msg : " + str + "————————" + errmap.get(str));
										bwerr.newLine();
//										System.out.println("Error msg : " + str + "————————" + errmap.get(str));
									}
									bwerr.write("-------- line : " + linenum + " End --------");
									bwerr.newLine();
									bwerr.flush();
								}
								if(!normalmap.isEmpty()) {
									Set<String> norset = normalmap.keySet();
									Iterator<String> iter = norset.iterator();
//									bwnor.write("-------- line : " + linenum + " Start --------");
//									bwnor.newLine();
									while(iter.hasNext()){
										String str = iter.next();
										bwnor.write(linenum + "," + str + "," + normalmap.get(str));
										bwnor.newLine();
//										System.out.println(str + "————————" + normalmap.get(str));
									}
//									bwnor.write("-------- line : " + linenum + " End --------");
//									bwnor.newLine();
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
						System.out.println("######## "+ sheetname + "'s case is finished. ########");
						System.out.println();
					}
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
