package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Util {
//	Xls文件类型方法
//	Xls文件创建读取
	public static HSSFWorkbook openXlsfile(String path) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		return workbook;
	}
	
//	Xls获取sheetname
	public static ArrayList<String> getXlssheetname(HSSFWorkbook workbook, String contentindex) {
		int num = workbook.getNumberOfSheets();
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < num;i++) {
			String sheetname = workbook.getSheetName(i).trim();
			if(sheetname.contains(contentindex)) {
				list.add(sheetname);
			}
		}
		return list;
	}
	
//	Xls获取sheet对象
	public static HSSFSheet getXlssheet(HSSFWorkbook workbook,String sheetname) {
		HSSFSheet Sheet = workbook.getSheet(sheetname);
		return Sheet;
	}
	
//	Xls获取列信息
	public static Map<String, Integer> getXlsCellPosition(HSSFSheet tarsheet,Row row,String funcname,String testpointname,
			String inputcontentname,String iscopy,String copytimes,String Jversion,String isjumpname) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int frownum = tarsheet.getFirstRowNum();
		int erownum = tarsheet.getLastRowNum();
		boolean flag = true;
		if(erownum <= 0) {
			map.clear();
		}else {
			map.put("frownum", frownum);
			map.put("erownum", erownum);	
			row = tarsheet.getRow(frownum);
			int startCell = row.getFirstCellNum();
			int endCell = row.getLastCellNum();
			for(int i = startCell ; i < endCell; i++) {
		    	String cellVelue = row.getCell(i).getStringCellValue();
//		    	System.out.println("++++++"+cellVelue+"++++++");
		    	if(cellVelue == funcname || cellVelue.equals(funcname)) {
		    		map.put(funcname, i);
		    	}
//		    	if(cellVelue == subfuncname || cellVelue.equals(subfuncname)) {
//		    		map.put(subfuncname, i);
//		    	}
		    	if(cellVelue == testpointname || cellVelue.equals(testpointname)) {
		    		map.put(testpointname, i);
		    	}
		    	if(cellVelue == isjumpname || cellVelue.equals(isjumpname)) {
		    		map.put(isjumpname, i);
		    	}
		    	if(cellVelue == inputcontentname || cellVelue.equals(inputcontentname)) {
		    		map.put(inputcontentname, i);
		    	}
		    	if(cellVelue == iscopy || cellVelue.equals(iscopy)) {
		    		map.put(iscopy, i);
		    	}
		    	if(cellVelue == copytimes || cellVelue.equals(copytimes)) {
		    		map.put(copytimes, i);
		    	}
		    	if(cellVelue == Jversion || cellVelue.equals(Jversion)) {
		    		map.put(Jversion, i);
		    	}
		    }
		}
		flag = Util.printIntegerMap(map);
		if(!flag) {
			map.clear();
		}
		return map;
	}
	
//	Xls轮询行获取目标列值
	public static String getXlsCellValAndWrite
			(String bankname,String sheetname,HSSFSheet tarSheet,Row row,Map<String, Integer> positionmap,int offset,
			int frownum,int erownum,int funcnamenumnum,int testpointnum,
			int inputcontentnum,int iscopynum,int copytimesnum,int Jversionnum,String funcnameval,
			String testpointval,String Jversionval,String tmpfuncval,String tmptestpointval,String tmpJversionval,
			String funcname,String testpointname,String inputcontentname,
			String iscopy,String copytimes,String Jversion,String path,String casepath,BufferedWriter orgibw,
			Map<String, String> defaultmap,Map<String, String> exchangemap,String[] envstr145,String[] envstr100,
			String[] gpsstr145,String[] gpsstr100,String[] pagestr145,String[] pagestr100,String[] errstr100,String[] Jbasev,
			String isjumpname,int isjumpnamenum,String isjumpval,String tmpisjumpval,String[] actor,
			String[] decice,String[] envtsp,String[] evttsp,String[] portrait1,String[] portrait2,
			String[] IntelligentScenario,long ts,long tsplus,String[] evnstr200) 
					throws Exception {
		frownum = positionmap.get("frownum");
		erownum = positionmap.get("erownum");
		funcnamenumnum = positionmap.get(funcname);
		testpointnum = positionmap.get(testpointname);
		isjumpnamenum = positionmap.get(isjumpname);
		inputcontentnum = positionmap.get(inputcontentname);
		iscopynum = positionmap.get(iscopy);
		copytimesnum = positionmap.get(copytimes);
		Jversionnum = positionmap.get(Jversion);

//		首次获取非表头功能模块，子功能模块，测试点列的cell Value
		row = tarSheet.getRow(frownum+offset);
		tmpfuncval = funcnameval = Util.replaceEintoC(row.getCell(funcnamenumnum).getStringCellValue().trim());
//		tmpsubfuncval = subfuncnameval = row.getCell(subfunnamenum).getStringCellValue();
		tmptestpointval = testpointval = Util.replaceEintoC(row.getCell(testpointnum).getStringCellValue().trim());
		tmpisjumpval = isjumpval = row.getCell(isjumpnamenum).getStringCellValue().trim();
		tmpJversionval = Jversionval = Util.replaceCintoE(row.getCell(Jversionnum).getStringCellValue().trim());
//		System.out.println(tmpJversionval+"\\\\\\\\"+Jversionval);
//		首次建立文件夹
		String newdirpath = path+casepath+sheetname+"/";
		String newpath = newdirpath+funcnameval+"_"+testpointval+"_"+Jversionval+".txt";
//		String chkpath = Util.checkFileNname(newpath);
//		System.out.println("======="+chkpath+"=======");
//		if(chkpath != "null") {
//			System.out.println("========in========");
//			newpath = chkpath;
//		}
////		
//		System.out.println("======="+newpath+"=======");
		FileWriter tmpfw = null;
		BufferedWriter tmpbw = null;
		Util.FileInitializeDir(newdirpath);
		Util.FileInitialize(newpath);
		System.out.println("Data file was created complete and save path is : "+newpath);
		System.out.println();
		tmpfw = new FileWriter(newpath);
		tmpbw = new BufferedWriter(tmpfw);
//		Bank初始化
//		defaultmap = Util.getBank(path+bankname);
		exchangemap = Util.getBank(path+bankname);
//		
//		轮训获取
	    for(int i = frownum+offset ; i <= erownum; i++) {
	    	long teresult = ts+tsplus*(i-1);
	    	row = tarSheet.getRow(i);
	    	tmpfuncval = Util.replaceEintoC(row.getCell(funcnamenumnum).getStringCellValue().trim());
//			tmpsubfuncval = row.getCell(subfunnamenum).getStringCellValue();
			tmptestpointval = Util.replaceEintoC(row.getCell(testpointnum).getStringCellValue().trim());
			tmpisjumpval = row.getCell(isjumpnamenum).getStringCellValue().trim();
//			System.out.println(tmpisjumpval);
//			String dataUniqueIDval = row.getCell(dataUniqueIDnum).getStringCellValue();
			String inputcontentvalorig = row.getCell(inputcontentnum).getStringCellValue();
			String inputcontentval = Util.replaceCintoE(inputcontentvalorig);
			String iscopyval = row.getCell(iscopynum).getStringCellValue().trim();
			int copytimesval = (int) row.getCell(copytimesnum).getNumericCellValue();
			tmpJversionval = Util.replaceCintoE(row.getCell(Jversionnum).getStringCellValue().trim());

			if(tmpfuncval.length() == 0) {
				tmpfuncval = funcnameval;
			}
			if(tmptestpointval.length() == 0) {
				tmptestpointval = testpointval;
			}
			if(tmpJversionval.length() == 0) {
				tmpJversionval = Jversionval;
			}
			if(tmpisjumpval.length() == 0) {
				tmpisjumpval = "Y";
			}
			
//			System.out.println("2:"+tmpfuncval+tmptestpointval+tmpJversionval+tmpisjumpval);
			
			if(tmpisjumpval.toLowerCase().equals("n") || tmpisjumpval.toLowerCase() == "n") {
//				
				if((tmpfuncval+tmptestpointval+tmpJversionval).equals(funcnameval+testpointval+Jversionval) 
						|| (tmpfuncval+tmptestpointval+tmpJversionval) == (funcnameval+testpointval+Jversionval)) {

//					判断是否复制
					if(iscopyval.toLowerCase().equals("y") || iscopyval.toLowerCase() == "y") {
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断是否存在=
						if(inputcontentval.contains("=")) {
							if(inputcontentval.contains(";")) {
//								k1=v1,v2,v3;k2=v2;k3=v3
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j != 0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
											
										}
									}else {
//										第一位置
										if(str1[j].contains(",")) {
											String[] str0 = str1[0].split("=");
											key = str0[0].trim();
											valuearr = str0[1].split(",");
										}else {
//											k1=v1,k2=v2
											String[] str0 = str1[0].split("=");
											if(str0.length > 1) {
												key = str0[0].trim();
												value1 = str0[1];
											}else {							
												key = str0[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);

//											造串入口，返回一个串
											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
//												System.out.println("Util : "+exchangemap.get("app_start_ts"));	
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
								}else {
									if(flag) {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
											
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
								}
								
							}else if(inputcontentval.contains(",")) {
//								k1=v1,v2,v3
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串
//											System.out.println("Copy trd JV: "+tmpJversionval);
											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, 
													gpsstr100, pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, 
													tmptestpointval, tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
								}
							}else {
//								k1=v1
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag) {
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
//										System.out.println("Util : "+exchangemap.get("app_start_ts"));	
									}else {							
										exchangemap.put(str[0],"");
									}
									
//									造串入口，返回一个串
//									System.out.println("Copy fos JV: "+tmpJversionval);
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
										
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
								
							}
						}else {
//							inputcontentval不存在=，为错误输入
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval + " !!!!!!!!");
							System.out.println();
						}
					}else {  
						//非复制逻辑
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断=
						if(inputcontentval.contains("=")) {
							if(inputcontentval.contains(";")) {
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j !=0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0], exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
										}
									}else {
										if(str1[0].contains(",")) {
											String[] str3 = str1[0].trim().split("=");
											key = str3[0].trim();
											valuearr = str3[1].split(",");
										}else {
											String[] str3 = str1[0].split("=");
											if(str3.length > 1) {
												key = str3[0].trim();
												value1 = str3[1];
											}else {							
												key = str3[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k< valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串
//											System.out.println("UnCopy st JV: "+tmpJversionval);
											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
								}else {
									if(flag) {
										exchangemap.put(key,value1);
//										造串入口，返回一个串
//										System.out.println("UnCopy sec JV: "+tmpJversionval);
										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else if(inputcontentval.contains(",")) {
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k< valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串
//											System.out.println("UnCopy trd JV: "+tmpJversionval);
											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, 
													gpsstr100, pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, 
													tmptestpointval, tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,
													portrait1,portrait2,IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag){
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}
									
//									造串入口，返回一个串
//									System.out.println("UnCopy fos JV: "+tmpJversionval);
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									tmpbw.flush();
									tmpbw.newLine();
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}							
								
							}
						}else {
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
						
					}
				}else { 
					//功能模块或者测试点变更
//					System.out.println("行判断T"+tmpfuncval+tmptestpointval+tmpJversionval+"00000000"+funcnameval+testpointval+Jversionval);
					String tmp = tmpfuncval;
					funcnameval = tmp;
					tmp = tmptestpointval;
					testpointval = tmp;
					tmp = tmpJversionval;
					Jversionval = tmp;
					
					tmpbw.close();
					tmpfw.close();
//					System.out.println("Else Func in "+tmpisjumpval);
					newdirpath = path+casepath+sheetname+"/";
					newpath = newdirpath+tmpfuncval+"_"+tmptestpointval+"_"+tmpJversionval+".txt";
					
					String chkpath = Util.checkFileNname(newpath);
//					System.out.println("======="+chkpath+"=======");
					if(chkpath != "null") {
//						System.out.println("========in========");
						newpath = chkpath;
					}
//					
//					System.out.println("======="+newpath+"=======");
					Util.FileInitializeDir(newdirpath);
					Util.FileInitialize(newpath);
					System.out.println("Data file was created complete and save path is : "+newpath);
					System.out.println();
					tmpfw = new FileWriter(newpath);
					tmpbw = new BufferedWriter(tmpfw);
					if(iscopyval.toLowerCase().equals("y") || iscopyval.toLowerCase() == "y") {
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断=
						if(inputcontentval.contains("=")) {
							if(inputcontentval.trim().contains(";")) {
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j !=0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag){
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
											
										}
									}else {
										if(str1[j].contains(",")) {
											String[] str0 = str1[0].split("=");
											key = str0[0].trim();
											valuearr = str0[1].split(",");
										}else {
											String[] str0 = str1[0].split("=");
											if(str0.length > 1) {
												key = str0[0].trim();
												value1 = str0[1];
											}else {							
												key = str0[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串
//											System.out.println("funcp st JV: "+tmpJversionval);
											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}else {
									if(flag) {
										exchangemap.put(key,value1);
//										造串入口，返回一个串
//										System.out.println("funcp sec JV: "+tmpJversionval);
										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
											
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
								}
								
							}else if(inputcontentval.contains(",")) {
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								if(valuearr.length > 0) {
									boolean flag = Util.checkKey(key, exchangemap);
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag) {
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}					
//									造串入口，返回一个串
//									System.out.println("funcp fos JV: "+tmpJversionval);
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
								
							}
						}else {
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
					}else {  
//						非复制逻辑
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断"="
						if(inputcontentval.contains("=")) {
							if(inputcontentval.contains(";")) {
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j !=0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
										}
									}else {
										if(str1[j].contains(",")) {
											String[] str0 = str1[0].split("=");
											key = str0[0].trim();
											valuearr = str0[1].split(",");
										}else {
											String[] str0 = str1[0].split("=");
											if(str0.length > 1) {
												key = str0[0].trim();
												value1 = str0[1];
											}else {							
												key = str0[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,
													portrait2,IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}else {
									if(flag) {
										exchangemap.put(key,value1);
//										造串入口，返回一个串
//										System.out.println("fununcp sec JV: "+tmpJversionval);
										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else if(inputcontentval.contains(",")) {
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag) {
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}
//									造串入口，返回一个串
//									System.out.println("fununcp fos JV: "+tmpJversionval);
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									tmpbw.flush();
									tmpbw.newLine();
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
							}
						}else {
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
					}
				}
			}else {
//				exchangemap = defaultmap;
				System.out.println("Case : "+sheetname+"_"+tmpfuncval+"_"+tmptestpointval+"_"+tmpJversionval + " is jumped.");
//				System.out.println("行判断T"+tmpfuncval+tmptestpointval+tmpJversionval+"00000000"+funcnameval+testpointval+Jversionval);
			}
			
			
//		    	一行结束,重置交换map,重置default值。
			funcnameval = tmpfuncval;
			testpointval = tmptestpointval;
			Jversionval = tmpJversionval;
			exchangemap = Util.getBank(path+bankname);
	    }
	    if(tmpbw != null) {
	    	tmpbw.close();
	    	tmpfw.close();
	    }
		return newdirpath;
	}
	
//	Xls轮询行获取目标列值方法结束

//	Xlsx文件类型方法
//	Xlsx文件创建读取
	public static XSSFWorkbook openXlsxfile(String path) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		return workbook;
	}
	
//	Xlsx获取sheetname
	public static ArrayList<String> getXlsxsheetname(XSSFWorkbook workbook,String contentindex) {
		int num = workbook.getNumberOfSheets();
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < num;i++) {
			String sheetname = workbook.getSheetName(i).trim();
			if(sheetname.contains(contentindex)){				
				list.add(sheetname);
			}
		}
		return list;
	}

//	Xlsx获取sheet对象
	public static XSSFSheet getXlsxsheet(XSSFWorkbook workbook,String sheetname) {
		XSSFSheet Sheet = workbook.getSheet(sheetname);
		return Sheet;
	}
//	Xlsx获取列信息
	public static Map<String, Integer> getXlsxCellPosition(XSSFSheet tarsheet,Row row,String funcname,String testpointname,
			String inputcontentname,String iscopy,String copytimes,String Jversion,String isjumpname) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int frownum = tarsheet.getFirstRowNum();
		int erownum = tarsheet.getLastRowNum();
//		判断空表
		if(erownum <= 0) {
			map.clear();
		}else {
			map.put("frownum", frownum);
			map.put("erownum", erownum);	
			row = tarsheet.getRow(frownum);
			int startCell = row.getFirstCellNum();
			int endCell = row.getLastCellNum();
			for(int i = startCell ; i < endCell; i++) {
		    	String cellVelue = row.getCell(i).getStringCellValue();
//		    	System.out.println("++++++"+cellVelue+"++++++");
		    	if(cellVelue == funcname || cellVelue.equals(funcname)) {
		    		map.put(funcname, i);
		    	}
		    	if(cellVelue == testpointname || cellVelue.equals(testpointname)) {
		    		map.put(testpointname, i);
		    	}
		    	if(cellVelue == isjumpname || cellVelue.equals(isjumpname)) {
		    		map.put(isjumpname, i);
		    	}
		    	if(cellVelue == inputcontentname || cellVelue.equals(inputcontentname)) {
		    		map.put(inputcontentname, i);
		    	}
		    	if(cellVelue == iscopy || cellVelue.equals(iscopy)) {
		    		map.put(iscopy, i);
		    	}
		    	if(cellVelue == copytimes || cellVelue.equals(copytimes)) {
		    		map.put(copytimes, i);
		    	}
		    	if(cellVelue == Jversion || cellVelue.equals(Jversion)) {
		    		map.put(Jversion, i);
		    	}
		    }
		}
		return map;
	}
	
//	Xlsx轮询行获取目标列值
	public static String getXlsxCellValAndWrite
			(String bankname,String sheetname,XSSFSheet tarSheet,Row row,Map<String, Integer> positionmap,int offset,
			int frownum,int erownum,int funcnamenumnum,int testpointnum,
			int inputcontentnum,int iscopynum,int copytimesnum,int Jversionnum,String funcnameval,
			String testpointval,String Jversionval,String tmpfuncval,String tmptestpointval,String tmpJversionval,
			String funcname,String testpointname,String inputcontentname,
			String iscopy,String copytimes,String Jversion,String path,String casepath,BufferedWriter orgibw,
			Map<String, String> defaultmap,Map<String, String> exchangemap,String[] envstr145,String[] envstr100,
			String[] gpsstr145,String[] gpsstr100,String[] pagestr145,String[] pagestr100,String[] errstr100,String[] Jbasev,
			String isjumpname,int isjumpnamenum,String isjumpval,String tmpisjumpval,String[] actor,
			String[] decice,String[] envtsp,String[] evttsp,String[] portrait1,String[] portrait2,
			String[] IntelligentScenario,long ts,long tsplus,String[] evnstr200) throws Exception {
		frownum = positionmap.get("frownum");
		erownum = positionmap.get("erownum");
		funcnamenumnum = positionmap.get(funcname);
		testpointnum = positionmap.get(testpointname);
//		System.out.println(positionmap.get(isjumpname)+"");
		isjumpnamenum = positionmap.get(isjumpname);
		inputcontentnum = positionmap.get(inputcontentname);
		iscopynum = positionmap.get(iscopy);
		copytimesnum = positionmap.get(copytimes);
		Jversionnum = positionmap.get(Jversion);
//		System.out.println("testpointnum  =  "+testpointnum);
//		subfunnamenum = positionmap.get(subfuncname);
//		dataUniqueIDnum = positionmap.get(dataUniqueIDname);
//		首次获取非表头功能模块，子功能模块，测试点列的cell Value
		row = tarSheet.getRow(frownum+offset);
		tmpfuncval = funcnameval = Util.replaceEintoC(row.getCell(funcnamenumnum).getStringCellValue().trim());
//		tmpsubfuncval = subfuncnameval = row.getCell(subfunnamenum).getStringCellValue();
		tmptestpointval = testpointval = Util.replaceEintoC(row.getCell(testpointnum).getStringCellValue().trim());
		tmpisjumpval = isjumpval = row.getCell(isjumpnamenum).getStringCellValue().trim();
		tmpJversionval = Jversionval = Util.replaceCintoE(row.getCell(Jversionnum).getStringCellValue().trim());
		String newdirpath = path+casepath+sheetname+"/";
		String newpath = newdirpath+funcnameval+"_"+testpointval+"_"+Jversionval+".txt";
		System.out.println("Data file was created complete and save path is : "+newpath);
		System.out.println();
		FileWriter tmpfw = null;
		BufferedWriter tmpbw = null;
		Util.FileInitializeDir(newdirpath);
		Util.FileInitialize(newpath);
		tmpfw = new FileWriter(newpath);
		tmpbw = new BufferedWriter(tmpfw);
//		Bank初始化
//		defaultmap = Util.getBank(path+bankname);
		exchangemap = Util.getBank(path+bankname);
//		轮训获取
	    for(int i = frownum+offset ; i <= erownum; i++) {
	    	long teresult = ts+tsplus*(i-1);
	    	row = tarSheet.getRow(i);
	    	tmpfuncval = Util.replaceEintoC(row.getCell(funcnamenumnum).getStringCellValue().trim());
//			tmpsubfuncval = row.getCell(subfunnamenum).getStringCellValue();
			tmptestpointval = Util.replaceEintoC(row.getCell(testpointnum).getStringCellValue().trim());
//			String dataUniqueIDval = row.getCell(dataUniqueIDnum).getStringCellValue();
			String inputcontentvalorig = row.getCell(inputcontentnum).getStringCellValue();
			String inputcontentval = Util.replaceCintoE(inputcontentvalorig);
			String iscopyval = row.getCell(iscopynum).getStringCellValue().trim();
			int copytimesval = (int) row.getCell(copytimesnum).getNumericCellValue();
			tmpJversionval = Util.replaceCintoE(row.getCell(Jversionnum).getStringCellValue().trim());
			tmpisjumpval = row.getCell(isjumpnamenum).getStringCellValue().trim();
			
			if(tmpfuncval.length() == 0) {
				tmpfuncval = funcnameval;
			}
			if(tmptestpointval.length() == 0) {
				tmptestpointval = testpointval;
			}
			if(tmpJversionval.length() == 0) {
				tmpJversionval = Jversionval;
			}
			if(tmpisjumpval.length() == 0) {
				tmpisjumpval = isjumpval;
			}
			
			if(tmpisjumpval.toLowerCase().equals("n") || tmpisjumpval.toLowerCase() == "n") {
				if((tmpfuncval+tmptestpointval+tmpJversionval).equals(funcnameval+testpointval+Jversionval) 
						|| (tmpfuncval+tmptestpointval+tmpJversionval) == (funcnameval+testpointval+Jversionval)) {
//					判断是否复制
					if(iscopyval.toLowerCase().equals("y") || iscopyval.toLowerCase() == "y") {
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断是否存在=
						if(inputcontentval.contains("=")) {
							if(inputcontentval.contains(";")) {
//								k1=v1|v2|v3,k2=v2,k3=v3
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j != 0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
											
										}
									}else {
//										第一位置
										if(str1[j].contains(",")) {
											String[] str0 = str1[0].split("=");
											key = str0[0].trim();
											valuearr = str0[1].split(",");
										}else {
//											k1=v1,k2=v2
											String[] str0 = str1[0].split("=");
											if(str0.length > 1) {
												key = str0[0].trim();
												value1 = str0[1];
											}else {							
												key = str0[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(valuearr.length > 0) {
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
								}else {
									if(flag) {
										exchangemap.put(key,value1);
//										造串入口，返回一个串
										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
											
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else if(inputcontentval.contains(",")) {
//								k1=v1|v2|v3
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								if(valuearr.length > 0) {
									boolean flag = Util.checkKey(key, exchangemap);
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
//								k1=v1
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag) {
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}
//									造串入口，返回一个串
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
										
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
								
							}
						}else {
//							inputcontentval不存在=，为错误输入
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
					}else {  
						//非复制逻辑
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断=
						if(inputcontentval.contains("=")) {
							if(inputcontentval.contains(";")) {
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j !=0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
											
										}
									}else {
										if(str1[0].contains(",")) {
											String[] str3 = str1[0].trim().split("=");
											key = str3[0].trim();
											valuearr = str3[1].split(",");
										}else {
											String[] str3 = str1[0].split("=");
											if(str3.length > 1) {
												key = str3[0].trim();
												value1 = str3[1];
											}else {							
												key = str3[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(flag){
									if(valuearr.length > 0) {
										for(int k = 0; k< valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ key
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ key
											+ "' was not executed !!!!!!!!");
								}
								
							}else if(inputcontentval.contains(",")) {
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								if(valuearr.length > 0) {
									boolean flag = Util.checkKey(key, exchangemap);
									if(flag) {
										for(int k = 0; k< valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串
											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag){
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}
//									造串入口，返回一个串
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									tmpbw.flush();
									tmpbw.newLine();
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
								
	
							}
						}else {
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
						
					}
				}else { 
					//功能模块或者测试点变更
					String tmp = tmpfuncval;
					funcnameval = tmp;
					tmpbw.close();
					tmpfw.close();
					newdirpath = path+casepath+sheetname+"/";
					newpath = newdirpath+tmpfuncval+"_"+tmptestpointval+"_"+tmpJversionval+".txt";
					String chkpath = Util.checkFileNname(newpath);
//					System.out.println("======="+chkpath+"=======");
					if(chkpath != "null") {
//						System.out.println("========in========");
						newpath = chkpath;
					}
//					
//					System.out.println("======="+newpath+"=======");
					Util.FileInitializeDir(newdirpath);
					Util.FileInitialize(newpath);
					System.out.println("Data file was created complete and save path is : "+newpath);
					System.out.println();
					tmpfw = new FileWriter(newpath);
					tmpbw = new BufferedWriter(tmpfw);
					if(iscopyval.toLowerCase().equals("y") || iscopyval.toLowerCase() == "y") {
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断=
						if(inputcontentval.contains("=")) {
							if(inputcontentval.trim().contains(";")) {
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j !=0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
											
										}
									}else {
										if(str1[j].contains(",")) {
											String[] str0 = str1[0].split("=");
											key = str0[0].trim();
											valuearr = str0[1].split(",");
										}else {
											String[] str0 = str1[0].split("=");
											if(str0.length > 1) {
												key = str0[0].trim();
												value1 = str0[1];
											}else {							
												key = str0[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(flag){
									if(valuearr.length > 0) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
											
										}
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ key
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ key
											+ "' was not executed !!!!!!!!");
								}
								
							}else if(inputcontentval.contains(",")) {
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								if(valuearr.length > 0) {
									boolean flag = Util.checkKey(key, exchangemap);
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag) {
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}
//									造串入口，返回一个串
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
										
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
							}
						}else {
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
					}else {  
//						非复制逻辑
						String key = "";
						String[] valuearr = {};
						String value1 = "";
//						先判断"="
						if(inputcontentval.contains("=")) {
							if(inputcontentval.contains(";")) {
								String[] str1 = inputcontentval.split(";");
								for(int j = 0; j < str1.length; j++) {
									if(j !=0 ) {
										if(str1[j].length() > 0) {
											String[] str2 = str1[j].split("=");
											boolean flag = Util.checkKey(str2[0].trim(), exchangemap);
											if(flag) {
												if(str2.length > 1) {
													exchangemap.put(str2[0].trim(),str2[1]);
												}else {							
													exchangemap.put(str2[0].trim(),"");
												}
											}else {
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' Key is not in bank list , Please check !!!!!!!!");
												System.out.println("!!!!!!!! " 
														+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
														+ str2[0]
														+ "' was not executed !!!!!!!!");
											}
											
										}
									}else {
										if(str1[j].contains(",")) {
											String[] str0 = str1[0].split("=");
											key = str0[0].trim();
											valuearr = str0[1].split(",");
										}else {
											String[] str0 = str1[0].split("=");
											if(str0.length > 1) {
												key = str0[0].trim();
												value1 = str0[1];
											}else {							
												key = str0[0].trim();
												value1 = "";
											}
										}
									}
								}
								boolean flag = Util.checkKey(key, exchangemap);
								if(flag){
									if(valuearr.length > 0) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
												IntelligentScenario,teresult,evnstr200);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
										tmpbw.flush();
										tmpbw.newLine();
									}
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ key
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ key
											+ "' was not executed !!!!!!!!");
								}
								
							}else if(inputcontentval.contains(",")) {
								String[] str4 = inputcontentval.split("=");
								if(str4.length > 1) {
									key = str4[0].trim();
									valuearr = str4[1].split(",");
								}
								if(valuearr.length > 0) {
									boolean flag = Util.checkKey(key, exchangemap);
									if(flag) {
										for(int k = 0; k < valuearr.length; k++) {
											exchangemap.put(key, valuearr[k]);
//											造串入口，返回一个串

											String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
													pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
													IntelligentScenario,teresult,evnstr200);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' Key is not in bank list , Please check !!!!!!!!");
										System.out.println("!!!!!!!! " 
												+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
												+ key
												+ "' was not executed !!!!!!!!");
									}
									
								}
								
							}else {
								String[] str = inputcontentval.split("=");
								boolean flag = Util.checkKey(str[0], exchangemap);
								if(flag) {
									if(str.length > 1) {
										exchangemap.put(str[0],str[1]);
									}else {							
										exchangemap.put(str[0],"");
									}
//									造串入口，返回一个串
									String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
											pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp,portrait1,portrait2,
											IntelligentScenario,teresult,evnstr200);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr + "=" + tmpJversionval);
									tmpbw.flush();
									tmpbw.newLine();
								}else {
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' Key is not in bank list , Please check !!!!!!!!");
									System.out.println("!!!!!!!! " 
											+ sheetname + "_" + tmpfuncval + "_" + tmptestpointval + "—— '"
											+ str[0]
											+ "' was not executed !!!!!!!!");
								}
								
							}
						}else {
							System.out.println("!!!!!!!! Test case input conditions error by missing '=' or empty row, "
									+ "Please check case : " + sheetname + "_" + tmpfuncval + "_" + tmptestpointval+" !!!!!!!!");
							System.out.println();
						}
					}
				}
			}else {
				System.out.println("Case : "+sheetname+"_"+tmpfuncval+"_"+tmptestpointval+"_"+tmpJversionval + " is jumped.");
			}

//	    	一行结束,重置交换map,重置default值。
			funcnameval = tmpfuncval;
			testpointval = tmptestpointval;
			Jversionval = tmpJversionval;
			exchangemap = Util.getBank(path+bankname);
	    }
	    if(tmpbw != null) {
	    	tmpbw.close();
	    	tmpfw.close();
	    }
		return newdirpath;
	}
//	Xlsx轮询行获取目标列值方法结束
	
//	公共方法
//	检查文件夹
	public static void checkDir(String dirpath) {
		File dir = new File(dirpath);
		if(!dir.exists()) {
			dir.mkdir();
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
	
//	判断文件名是否重复
	public static String checkFileNname(String path) {
		String newP = "null";
		File oldf = new File(path);
		if(oldf.exists()) {
			newP = path.replace(".txt", "copy.txt");
		}
		return newP;
	}
	
//	文件夹初始化
	public static void FileInitializeDir(String path) throws Exception{
		File f = new File(path);
		if(!f.exists()){
			f.mkdir();
		}
	}
	
//	清空DataResultFolder
	public static void cleanDRF(String path) {
		List<String> list = new ArrayList<String>();
		File f = new File(path);
		list = Util.getFolderName(f);
		for(int i = 0; i< list.size(); i++) {
			File tmpf = new File(path+list.get(i));
//			System.out.println(tmpf);
			if(tmpf.isDirectory()) {
//				System.out.println(tmpf);
				Util.deleteFiles(tmpf);
				tmpf.delete();
			}
		}
	}
	
//	删除文件
	public static void deleteFiles(File path) {
		List<String> list = new ArrayList<String>();
		list = Util.getFileName(path);
		for(int i = 0; i< list.size(); i++) {
			File tmpf = new File(path+"/"+list.get(i));
//			System.out.println(tmpf.getPath());
			tmpf.delete();
		}
	}
	
//	Kafka文件夹初始化
	public static void FileInitializeKafkaDir(String path) throws Exception{
		File f = new File(path);
		if(!f.exists()){
			f.mkdir();
			System.out.println("!!!!!!!! Kafka Folder does not exists !!!!!!!!");
		}
	}
	
//  读取bank文件
	public static Map<String, String> getBank(String path) throws Exception {
		int daygap = 86400000;//24h
		int reco = 9000000;//2.5h
		int duration = 3600000;//1h
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		Map<String, String> map = new HashMap<String, String>();
		String s = br.readLine();
		while(s != null) {
//			System.out.println(s);
			if(s.contains("=")) {
				String[] tmp = s.split("=");
				if(tmp.length == 2) {
					map.put(tmp[0], tmp[1]);
				}else if(tmp.length == 1) {
					if(tmp[0].equals("session_id") || tmp[0] == "session_id") {
						map.put(tmp[0], "");
					}else {
						map.put(tmp[0], "DefaultVal");
					}
				}
			}
			s = br.readLine();
		}
		long startts = Util.getMtime()-daygap;
		long durts = startts+duration;
		long stopts = startts+reco;
//		d_start_ts,d_stop_ts,d_record_ts,app_start_ts,app_stop_ts,app_record_ts,c_ts,p_into_ts,p_out_ts,m_beg_ts,
//		m_end_ts,f_beg_ts,f_end_ts,p_beg_ts,p_end_ts,evt_beg_ts,evt_end_ts,gen_ts
		map.put("pre_server_ts", durts+"");
		map.put("err_ts", durts+"");
		map.put("d_start_ts", startts+"");
		map.put("d_stop_ts", stopts+"");
		map.put("d_record_ts", stopts+"");
		map.put("app_start_ts", startts+"");
		map.put("app_stop_ts", stopts+"");
		map.put("app_record_ts", stopts+"");
		map.put("link_start_ts", startts+"");
		map.put("link_stop_ts", stopts+"");
		map.put("c_ts", stopts+"");
		map.put("p_into_ts", startts+"");
		map.put("p_out_ts", stopts+"");
		map.put("m_beg_ts", startts+"");
		map.put("m_end_ts", stopts+"");
		map.put("f_beg_ts", startts+"");
		map.put("f_end_ts", stopts+"");
		map.put("p_beg_ts", startts+"");
		map.put("p_end_ts", stopts+"");
		map.put("evt_beg_ts", startts+"");
		map.put("evt_end_ts", stopts+"");
		map.put("gen_ts", stopts+"");
		map.put("parsedTs", stopts+"");
		map.put("device_start_time", startts+"");
		map.put("device_stop_time", stopts+"");
		map.put("app_start_time", startts+"");
		map.put("app_stop_time", stopts+"");
		map.put("curr_timestamp", durts+"");
		map.put("contact_begin_time", startts+"");
		map.put("contact_end_time", stopts+"");
		map.put("data_gen_time", startts+"");
//		map.put("uuid", "15589347789865548352565467532641");
		map.put("reportTime", startts+"");
//		"data_id": "取值及格式： VIN(17)#DeviceID(32)#UserID(32)#UUID(32)
		String data_id = map.get("vin")+"#"+map.get("device_id")+"#"+map.get("user_id")+"#"+map.get("uuid");
		map.put("data_id", data_id);
		String tdate_id = map.get("data_id").replaceAll("#", ":");
		map.put("tdata_id", tdate_id);
		br.close();
		fr.close();
		return map;
	}
//	判断EXCEL类型
	public static int getExcelType(String pathname) {
		int type = 0;
//		System.out.println(pathname);
		File f = new File(pathname);
//		System.out.println("In file type mothod");
		if(f.exists()) {
			if(pathname.contains(".xlsx")) {
				type = 2;
			}else if(pathname.contains(".xls")) {
				type = 1;
			}
		}
		return type;
	}
	
//	获取毫秒
	public static long getMtime() throws Exception{
		Date date = new Date();
		long msec = date.getTime();
		return msec;
	}
	
//	Jversion选择
	public static int chooseJversion(String Jversion,String[] Jbasev) {
		int type = 0;
		if(Jbasev.length > 0) {
//			System.out.println(Jversion+"=========="+Jbasev[0]);
			if(Jversion.equals(Jbasev[0]) || Jversion == Jbasev[0]) {
//				Jbaseversion = V1.0
				type = 1;
			}
			if(Jversion.equals(Jbasev[1]) || Jversion == Jbasev[1]) {
//				Jbaseversion = V1.4.5
				type = 2;
			}
			if(Jversion.equals(Jbasev[2]) || Jversion == Jbasev[2]) {
//				Jbaseversion = V1.1  专供深圳项目
				type = 3;
			}
			if(Jversion.equals(Jbasev[3]) || Jversion == Jbasev[3]) {
//				Jbaseversion = ETL  ETL SDK埋点清洗专用
				type = 4;
			}
			if(Jversion.equals(Jbasev[4]) || Jversion == Jbasev[4]) {
//				Jbaseversion = TSP ETL长城非埋点清洗专用
				type = 5;
			}
			if(Jversion.equals(Jbasev[5]) || Jversion == Jbasev[5]) {
//				Jbaseversion = ETLenv-1  ETL SDK env字段为空
				type = 6;
			}
			if(Jversion.equals(Jbasev[6]) || Jversion == Jbasev[6]) {
//				Jbaseversion = ETLenv-2  ETL SDK env字段为空格
				type = 7;
			}
			if(Jversion.equals(Jbasev[7]) || Jversion == Jbasev[7]) {
//				Jbaseversion = ETLenv-3  ETL SDK env字段为null
				type = 8;
			}
			if(Jversion.equals(Jbasev[8]) || Jversion == Jbasev[8]) {
//				Jbaseversion = ETLtrack-1  ETL SDK tarck字段为空
				type = 9;
			}
			if(Jversion.equals(Jbasev[9]) || Jversion == Jbasev[9]) {
//				Jbaseversion = ETLtarck-2  ETL SDK tarck字段为空格
				type = 10;
			}
			if(Jversion.equals(Jbasev[10]) || Jversion == Jbasev[10]) {
//				Jbaseversion = ETLtarck-3  ETL SDK tarck字段为null
				type = 11;
			}
			if(Jversion.equals(Jbasev[11]) || Jversion == Jbasev[11]) {
//				portrait用户画像使用
				type = 12;
			}
			if(Jversion.equals(Jbasev[12]) || Jversion == Jbasev[12]) {
//				IntelligentScenario情景智能使用
				type = 13;
			}
			if(Jversion.equals(Jbasev[13]) || Jversion == Jbasev[13]) {
//				Jbaseversion = ETL2.0为2.0JSON ETL清洗使用
				type = 14;
			}
			if(Jversion.equals(Jbasev[14]) || Jversion == Jbasev[14]) {
//				Jbaseversion = ETL2.6为2.6JSON ETL清洗使用
				type = 15;
			}
			if(Jversion.equals(Jbasev[15]) || Jversion == Jbasev[15]) {
//				Jbaseversion = V2.6为2.6JSON PB格式使用
				type = 16;
			}
			if(Jversion.equals(Jbasev[16]) || Jversion == Jbasev[16]) {
//				Jbaseversion = V2.0为2.0JSON PB格式使用
				type = 17;
			}
		}else {
			System.out.println("!!!!!!!! JSON base Version setting is not correct please check Main.java —— "
					+ "String[] Jbasev. !!!!!!!!");
			System.out.println();
		}
		
		return type;
	}
	
//	生成JSON串
	public static String makeJsonStr(Map<String, String> exchangemap,String[] envstr145,String[] envstr100,
			String[] gpsstr145,String[] gpsstr100,String[] pagestr145,String[] pagestr100,String[] errstr100,
			String sheetname,String tmpfuncval,String tmptestpointval,String tmpJversionval,String[] Jbasev,
			String[] actor,String[] decice,String[] envtsp,String[] evttsp,String[] portrait1,String[] portrait2,
			String[] IntelligentScenario,long teresult,String[] evnstr200) {
		String Json = "";
		Map<String, String> envmap145 = new HashMap<String, String>();
		Map<String, String> gpsmap145 = new HashMap<String, String>();
		Map<String, String> pagemap145 = new HashMap<String, String>();
		Map<String, String> evtdetailmap1 = new HashMap<String, String>();
		Map<String, String> evtdetailmap2 = new HashMap<String, String>();
		Map<String, String> portraitmap = new HashMap<String, String>();
//		ArrayList<Map<String, String>> gps145list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> page145list = new ArrayList<Map<String, String>>();

		Map<String, String> envmap100 = new HashMap<String, String>();
		Map<String, String> envmap101 = new HashMap<String, String>();
		Map<String, String> envmap200 = new HashMap<String, String>();
		Map<String, String> gpsmap100 = new HashMap<String, String>();
		Map<String, String> pagemap100 = new HashMap<String, String>();
		Map<String, String> errmap100 = new HashMap<String, String>();
		Map<String, String> envmaptsp = new HashMap<String, String>();
		Map<String, String> actormaptsp = new HashMap<String, String>();
		Map<String, String> evtmaptsp = new HashMap<String, String>();
		Map<String, String> devmaptsp = new HashMap<String, String>();
		Map<String, String> IntelligentScenariomap = new HashMap<String, String>();
//		ArrayList<Map<String, String>> gps100list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> page100list = new ArrayList<Map<String, String>>();
//		ArrayList<Map<String, String>> err100list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> evtdetaillist = new ArrayList<Map<String, String>>();
//		JSONArray jsongps100list = null;
		JSONArray jsonpage100list = null;
//		JSONArray jsonerr100list = null;
		JSONObject jsonenvmap145 = null; 
		JSONObject jsonenvmap200 = null; 
		JSONObject jsonerrmap100 = null; 
//		JSONArray jsongps145list = null;
		JSONArray jsonpage145list = null;
		JSONObject jsonenvmap100 = null;
		JSONObject jsongpsmap100 = null;
		JSONObject jsongpsmap145 = null;
		JSONObject jsonenvmaptsp = null;
		JSONObject jsonactormaptsp = null;
		JSONObject jsonevtmaptsp = null;
		JSONObject jsondevmaptsp = null;
		JSONObject jsonIntelligentScenariomap = null;
		JSONObject jsonenvmap101 = null;
		String tmp = null;

		evtdetailmap1.put("item_name", exchangemap.get("item_name"));
		evtdetailmap1.put("item_value", exchangemap.get("item_value"));
		evtdetailmap2.put("item_name", exchangemap.get("item_name1"));
		evtdetailmap2.put("item_value", exchangemap.get("item_value1"));
		
		evtdetaillist.add(evtdetailmap1);
		evtdetaillist.add(evtdetailmap2);
		JSONArray jsonevtdetaillist=JSONArray.fromObject(evtdetaillist);
		int Jtype = Util.chooseJversion(tmpJversionval,Jbasev);
		switch(Jtype) {
			case 0 :
				System.out.println("!!!!!!!! Jversion not found please check " + sheetname +"——"+ tmpfuncval +"——"+ 
			tmptestpointval +"——"+ "'s Jversion column value. !!!!!!!!");
				System.out.println();
			break;
			case 1:
//				V1.0
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
//					System.out.println(key);
					envmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
//				err100list.add(errmap100);
				jsonenvmap100 = JSONObject.fromObject(envmap100);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsonpage100list=JSONArray.fromObject(page100list);
//				jsonerr100list=JSONArray.fromObject(err100list);
				jsonerrmap100 = JSONObject.fromObject(errmap100);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap100.toString()+"," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"access\": \""+exchangemap.get("access")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"app_record_ts\": \""+exchangemap.get("app_record_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": ["+jsongpsmap100.toString()+"]," +
							"\"err\": ["+jsonerrmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}";
			break;
			case 2 :
//				V1.4.5  PB格式
				for(int i = 0; i < envstr145.length; i++) {
					String key = envstr145[i];
					envmap145.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr145.length; i++) {
					String key = gpsstr145[i];
					gpsmap145.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr145.length; i++) {
					String key = pagestr145[i];
					pagemap145.put(key, exchangemap.get(key));
				}
//				gps145list.add(gpsmap145);
				page145list.add(pagemap145);
				jsonenvmap145 = JSONObject.fromObject(envmap145);
				jsongpsmap145 = JSONObject.fromObject(gpsmap145);
//				jsongps145list = JSONArray.fromObject(gps145list);
				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap145.toString()+"," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
//								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\","+
								"\"evt_detail\": "+jsonevtdetaillist.toString()
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": [" + jsongpsmap145.toString() +"],"+
							"\"page\": "+jsonpage145list.toString() 
						+"}]"
					+"}";
			break;
			case 3:
//				V1.1
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
					if(key == "vin" || key.equals("vin")) {
						envmap101.put(key, exchangemap.get("device_id"));
						exchangemap.put(key, exchangemap.get("device_id"));
					}else if(key == "d_start_ts" || key.equals("d_start_ts")){
						envmap101.put(key, exchangemap.get("app_start_ts"));
					}else if(key == "d_stop_ts" || key.equals("d_stop_ts")){
						envmap101.put(key, exchangemap.get("app_stop_ts"));
					}else {
						envmap101.put(key, exchangemap.get(key));				
					}
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));

//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
//				err100list.add(errmap100);
				jsonenvmap101 = JSONObject.fromObject(envmap101);
				jsongpsmap100 = JSONObject.fromObject(errmap100);
//				jsongps100list = JSONArray.fromObject(gps100list);
				jsonpage100list = JSONArray.fromObject(page100list);
//				jsonerr100list = JSONArray.fromObject(err100list);
				jsonerrmap100 = JSONObject.fromObject(errmap100);
//				System.out.println(jsonenvmap101.toString());
				Json = "{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap101.toString()+"," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"access\": \""+exchangemap.get("access")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"app_record_ts\": \""+exchangemap.get("app_record_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": ["+jsongpsmap100.toString()+"]," +
							"\"err\": ["+jsonerrmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}";
			break;
			case 4 :
//				ETL SDK埋点内层V1.1
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
//					System.out.println(key);
					envmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
				jsonenvmap100 = JSONObject.fromObject(envmap100);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsonpage100list=JSONArray.fromObject(page100list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap100.toString()+"," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": ["+jsongpsmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 5:
//				ETL 长城TSP数据  格式为TSP格式
				for(int i = 0; i < actor.length; i++) {
					String key = actor[i];
					actormaptsp.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < decice.length; i++) {
					String key = decice[i];
					devmaptsp.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < envtsp.length; i++) {
					String key = envtsp[i];
					envmaptsp.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < evttsp.length; i++) {
					String key = evttsp[i];
					evtmaptsp.put(key, exchangemap.get(key));
				}
				jsonactormaptsp = JSONObject.fromObject(actormaptsp);
				jsondevmaptsp = JSONObject.fromObject(devmaptsp);
				jsonenvmaptsp = JSONObject.fromObject(envmaptsp);
				jsonevtmaptsp = JSONObject.fromObject(evtmaptsp);
				exchangemap.put("data_id", exchangemap.get("vin")+":"+exchangemap.get("device_id")+":"+
						exchangemap.get("user_id")+":"+exchangemap.get("uuid"));
				Json = "{"+
						"\"sign\": \""+exchangemap.get("sign")+"\"," +
						"\"id\": \""+exchangemap.get("id")+"\"," +
						"\"data\": {" +
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"actor\": "+jsonactormaptsp.toString()+"," +
						"\"device\": "+jsondevmaptsp.toString()+"," +
						"\"thing\": {"+
							"\"env\": ["+jsonenvmaptsp.toString()+"]," +
							"\"evt\": ["+jsonevtmaptsp.toString() +"]"
						+"},"+
						"\"attach\": \""+exchangemap.get("attach")+"\"," +
						"\"data_gen_time\": \""+exchangemap.get("data_gen_time")+"\"" 
						+"}"
					+"}";
			break;
			case 6:
//				ETL env为空
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
				jsonpage100list=JSONArray.fromObject(page100list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": {}," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": ["+jsongpsmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 7:
//				ETL env为空格
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsonpage100list=JSONArray.fromObject(page100list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": { }," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": ["+jsongpsmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 8:
//				ETL env为null
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsonpage100list=JSONArray.fromObject(page100list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": null," +
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"f_end_ts\": \""+exchangemap.get("f_end_ts")+"\"," +
								"\"m_beg_ts\": \""+exchangemap.get("m_beg_ts")+"\"," +
//								"\"content_tag\": \""+exchangemap.get("content_tag")+"\"," +
								"\"p_beg_ts\": \""+exchangemap.get("p_beg_ts")+"\"," +
								"\"func_id\": \""+exchangemap.get("func_id")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"f_beg_ts\": \""+exchangemap.get("f_beg_ts")+"\"," +
								"\"page_id\": \""+exchangemap.get("page_id")+"\"," +
								"\"m_end_ts\": \""+exchangemap.get("m_end_ts")+"\"," +
								"\"module_id\": \""+exchangemap.get("module_id")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"p_end_ts\": \""+exchangemap.get("p_end_ts")+"\"," +
								"\"evt_id\": \""+exchangemap.get("evt_id") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"gps\": ["+jsongpsmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 9:
//				ETL tarck为空
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
//					System.out.println(key);
					envmap100.put(key, exchangemap.get(key));
				}
				jsonenvmap100 = JSONObject.fromObject(envmap100);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap100.toString()+"," +
						"\"track\": []"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 10:
//				ETL tarck为空格
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
//					System.out.println(key);
					envmap100.put(key, exchangemap.get(key));
				}
				jsonenvmap100 = JSONObject.fromObject(envmap100);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap100.toString()+"," +
						"\"track\": [ ]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 11:
//				ETL tarck为null
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
//					System.out.println(key);
					envmap100.put(key, exchangemap.get(key));
				}
				jsonenvmap100 = JSONObject.fromObject(envmap100);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap100.toString()+"," +
						"\"track\": null"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 12:
				portraitmap = Util.genportraitmap(portrait1, portrait2, exchangemap);
//				Util.printMap(portraitmap);
				JSONObject jportraitmap = JSONObject.fromObject(portraitmap);
				Json = jportraitmap.toString();
			break;
			case 13:
//				IntelligentScenario情景智能拼接使用。
				for(int i = 0; i < IntelligentScenario.length; i++) {
					String key = IntelligentScenario[i];
					if(key.equals("reportTime") || key == "reportTime") {
//						System.out.println("Fri in + " + exchangemap.get(key));
						if(exchangemap.get(key).endsWith("001")) {
//							System.out.println("001 in");
							IntelligentScenariomap.put(key, exchangemap.get(key));
						}else {				
//							System.out.println("else in");
							IntelligentScenariomap.put(key, teresult+"");
						}
					}else {						
						IntelligentScenariomap.put(key, exchangemap.get(key));
					}
//					System.out.println(key);
//					System.out.println(exchangemap.get(key));
				}
				jsonIntelligentScenariomap = JSONObject.fromObject(IntelligentScenariomap);
				Json = jsonIntelligentScenariomap.toString();
			break;
			case 14:
//				2.0JSON ETL清洗拼接使用。
				for(int i = 0; i < evnstr200.length; i++) {
					String key = evnstr200[i];
//					System.out.println(key);
					envmap200.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
//				err100list.add(errmap100);
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
				jsonpage100list=JSONArray.fromObject(page100list);
//				jsonerr100list=JSONArray.fromObject(err100list);
				jsonenvmap200 = JSONObject.fromObject(envmap200);
				jsonerrmap100 = JSONObject.fromObject(errmap100);
//				System.out.println(jsonenvmap200.toString());
				tmp = jsonenvmap200.toString().split("}")[0];
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
//				System.out.println(jsonerrmap100.toString());
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+tmp+",\"gps\": [" +jsongpsmap100.toString()+"]},"+
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"evt_code\": \""+exchangemap.get("evt_code") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"app_id\": \""+exchangemap.get("app_id")+"\"," +
							"\"app_ver\": \""+exchangemap.get("app_ver")+"\"," +
							"\"app_package\": \""+exchangemap.get("app_package")+"\"," +
							"\"ver_code\": \""+exchangemap.get("ver_code")+"\"," +
							"\"app_type\": \""+exchangemap.get("app_type")+"\"," +
							"\"che_name\": \""+exchangemap.get("che_name")+"\"," +
							"\"che_type\": \""+exchangemap.get("che_type")+"\"," +
							"\"keep_alive\": \""+exchangemap.get("keep_alive")+"\"," +
							"\"user_id\": \""+exchangemap.get("user_id")+"\"," +
							"\"access\": \""+exchangemap.get("access")+"\"," +
							"\"acc_type\": \""+exchangemap.get("acc_type")+"\"," +
							"\"c_store_size\": \""+exchangemap.get("c_store_size")+"\"," +
							"\"c_mem_size\": \""+exchangemap.get("c_mem_size")+"\"," +
							"\"user_name\": \""+exchangemap.get("user_name")+"\"," +
							"\"err\": ["+jsonerrmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 15:
//				2.6JSON ETL清洗拼接使用。与2.0区别在于多一个status字段，其他不变。
				for(int i = 0; i < evnstr200.length; i++) {
					String key = evnstr200[i];
//					System.out.println(key);
					envmap200.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
//				err100list.add(errmap100);
//				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
//				jsongps100list=JSONArray.fromObject(gps100list);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
				jsonpage100list=JSONArray.fromObject(page100list);
//				jsonerr100list=JSONArray.fromObject(err100list);
				jsonenvmap200 = JSONObject.fromObject(envmap200);
				jsonerrmap100 = JSONObject.fromObject(errmap100);
//				System.out.println(jsonenvmap200.toString());
				tmp = jsonenvmap200.toString().split("}")[0];
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"status\": \""+exchangemap.get("status")+"\"," + 
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+tmp+",\"gps\": [" +jsongpsmap100.toString()+"]},"+
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"evt_code\": \""+exchangemap.get("evt_code") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"app_id\": \""+exchangemap.get("app_id")+"\"," +
							"\"app_ver\": \""+exchangemap.get("app_ver")+"\"," +
							"\"app_package\": \""+exchangemap.get("app_package")+"\"," +
							"\"ver_code\": \""+exchangemap.get("ver_code")+"\"," +
							"\"app_type\": \""+exchangemap.get("app_type")+"\"," +
							"\"che_name\": \""+exchangemap.get("che_name")+"\"," +
							"\"che_type\": \""+exchangemap.get("che_type")+"\"," +
							"\"keep_alive\": \""+exchangemap.get("keep_alive")+"\"," +
							"\"user_id\": \""+exchangemap.get("user_id")+"\"," +
							"\"access\": \""+exchangemap.get("access")+"\"," +
							"\"acc_type\": \""+exchangemap.get("acc_type")+"\"," +
							"\"c_store_size\": \""+exchangemap.get("c_store_size")+"\"," +
							"\"c_mem_size\": \""+exchangemap.get("c_mem_size")+"\"," +
							"\"user_name\": \""+exchangemap.get("user_name")+"\"," +
							"\"err\": ["+jsonerrmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," + 
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
			break;
			case 16:
//				V2.6JSON 用于生成原始V2.6PB埋点数据，。
				for(int i = 0; i < evnstr200.length; i++) {
					String key = evnstr200[i];
//					System.out.println(key);
					envmap200.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
				page100list.add(pagemap100);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
				jsonpage100list=JSONArray.fromObject(page100list);
				jsonenvmap200 = JSONObject.fromObject(envmap200);
				jsonerrmap100 = JSONObject.fromObject(errmap100);
				tmp = jsonenvmap200.toString().split("}")[0];
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"status\": \""+exchangemap.get("status")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+tmp+",\"gps\": [" +jsongpsmap100.toString()+"]},"+
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"evt_code\": \""+exchangemap.get("evt_code") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"app_id\": \""+exchangemap.get("app_id")+"\"," +
							"\"app_ver\": \""+exchangemap.get("app_ver")+"\"," +
							"\"app_package\": \""+exchangemap.get("app_package")+"\"," +
							"\"ver_code\": \""+exchangemap.get("ver_code")+"\"," +
							"\"app_type\": \""+exchangemap.get("app_type")+"\"," +
							"\"che_name\": \""+exchangemap.get("che_name")+"\"," +
							"\"che_type\": \""+exchangemap.get("che_type")+"\"," +
							"\"keep_alive\": \""+exchangemap.get("keep_alive")+"\"," +
							"\"user_id\": \""+exchangemap.get("user_id")+"\"," +
							"\"access\": \""+exchangemap.get("access")+"\"," +
							"\"acc_type\": \""+exchangemap.get("acc_type")+"\"," +
							"\"c_store_size\": \""+exchangemap.get("c_store_size")+"\"," +
							"\"c_mem_size\": \""+exchangemap.get("c_mem_size")+"\"," +
							"\"user_name\": \""+exchangemap.get("user_name")+"\"," +
							"\"err\": ["+jsonerrmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}";
			break;
			case 17:
//				V2.0JSON 用于生成原始V2.0PB埋点数据，。
				for(int i = 0; i < evnstr200.length; i++) {
					String key = evnstr200[i];
//					System.out.println(key);
					envmap200.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
				page100list.add(pagemap100);
				jsongpsmap100 = JSONObject.fromObject(gpsmap100);
				jsonpage100list=JSONArray.fromObject(page100list);
				jsonenvmap200 = JSONObject.fromObject(envmap200);
				jsonerrmap100 = JSONObject.fromObject(errmap100);
				tmp = jsonenvmap200.toString().split("}")[0];
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
//						"\"status\": \""+exchangemap.get("status")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+tmp+",\"gps\": [" +jsongpsmap100.toString()+"]},"+
						"\"track\": [{"+
							"\"evt\": [{"+
								"\"evt_beg_ts\": \""+exchangemap.get("evt_beg_ts")+"\"," +
								"\"evt_detail\": "+jsonevtdetaillist.toString()+"," +
								"\"evt_end_ts\": \""+exchangemap.get("evt_end_ts")+"\"," +
								"\"touch_type\": \""+exchangemap.get("touch_type")+"\"," +
								"\"evt_code\": \""+exchangemap.get("evt_code") +"\""
							+"}],"+
							"\"app_stop_ts\": \""+exchangemap.get("app_stop_ts")+"\"," +
							"\"is_update\": \""+exchangemap.get("is_update")+"\"," +
							"\"is_first\": \""+exchangemap.get("is_first")+"\"," +
							"\"session_id\": \""+exchangemap.get("session_id")+"\","  +
							"\"first_use\": \""+exchangemap.get("first_use")+"\","  +
							"\"app_start_ts\": \""+exchangemap.get("app_start_ts")+"\"," +
							"\"first_day\": \""+exchangemap.get("first_day")+"\"," +
							"\"app_id\": \""+exchangemap.get("app_id")+"\"," +
							"\"app_ver\": \""+exchangemap.get("app_ver")+"\"," +
							"\"app_package\": \""+exchangemap.get("app_package")+"\"," +
							"\"ver_code\": \""+exchangemap.get("ver_code")+"\"," +
							"\"app_type\": \""+exchangemap.get("app_type")+"\"," +
							"\"che_name\": \""+exchangemap.get("che_name")+"\"," +
							"\"che_type\": \""+exchangemap.get("che_type")+"\"," +
							"\"keep_alive\": \""+exchangemap.get("keep_alive")+"\"," +
							"\"user_id\": \""+exchangemap.get("user_id")+"\"," +
							"\"access\": \""+exchangemap.get("access")+"\"," +
							"\"acc_type\": \""+exchangemap.get("acc_type")+"\"," +
							"\"c_store_size\": \""+exchangemap.get("c_store_size")+"\"," +
							"\"c_mem_size\": \""+exchangemap.get("c_mem_size")+"\"," +
							"\"user_name\": \""+exchangemap.get("user_name")+"\"," +
							"\"err\": ["+jsonerrmap100.toString()+"]," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}";
			break;
			default:
				System.out.println("!!!!!!!! Jversion not found please check " + sheetname +"——"+ tmpfuncval +"——"+ 
			tmptestpointval + "'s Jversion column value. !!!!!!!!");
				System.out.println();
			break;
		}
		return Json;
	}
	
//	替换中文字符
	public static String replaceCintoE(String str) {
		String step1 = Util.replaceCSemicolon(str);
		String step2 = Util.replaceCComma(step1);
		String step3 = Util.replaceCPoint(step2);
		return step3;
	}
	
//	替换英文字符
	public static String replaceEintoC(String str) {

		String step1 = str.replaceAll("\\\\", "_")
				.replaceAll("/", "_").replaceAll(":", "")
				.replaceAll("\\*", "").replaceAll("\\?", "？")
				.replaceAll("\"", "'").replaceAll("<", "《")
				.replaceAll(">", "》").replaceAll("\\|", "—");
		return step1;
	}
	
//	替换中文；到;
	public static String replaceCSemicolon(String str) {
		String tmp = str.replace("；", ";");
		return tmp;
	}
	
	
//	替换中文，到,
	public static String replaceCComma(String str) {
		String tmp = str.replace("，", ",");
		return tmp;
	}

	
//	替换中文。到.
	public static String replaceCPoint(String str) {
		String tmp = str.replace("。", ".");
		return tmp;
	}
	
//	获取用户输入
	public static String getInput(){
		String input = "";
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		input = sc.next();
		sc.close();
		return input;
	}
	
//	自定义输入文件
	public static String changeTestCaseFileName(String defaultfilename,String[] args) {
		String tmp = "";
		
		if(args.length > 0){
			tmp = args[0];
		}else{
			tmp = defaultfilename;
		}
		return tmp;
	}
	
	
//	获取文件名称
	public static List<String> getFileName(File file){
		List<String> list = new ArrayList<String>();
//		System.out.println(list.toString());
		File[] tmp = file.listFiles();
		for (int i = 0; i < tmp.length;i++){
			if(tmp[i].isFile() || tmp[i].length() > 0) {
				list.add(tmp[i].getName());
			}
		}
		return list;
	}
	
//	获取文件夹名称
	public static List<String> getFolderName(File file){
		List<String> list = new ArrayList<String>();
//		System.out.println(list.toString());
		File[] tmp = file.listFiles();
		for (int i = 0; i < tmp.length;i++){
			if(tmp[i].isDirectory()) {
				list.add(tmp[i].getName());
			}
		}
		return list;
	}
	
//	判断自定义K-V中K是否不存在
	public static boolean checkKey(String key,Map<String, String> exchangemap) {
		boolean flag = true;
		if(key != "data_id" && key != "session_id") {
			String tmp = exchangemap.get(key)+"";
//			System.out.println(tmp);
			if(tmp.toLowerCase().equals("null") || tmp.toLowerCase() == "null") {
				flag = false;
			}
		}
		return flag;
	}
	
//	校验Bank文件是否存在
	public static boolean checkBank(String path) {
		boolean flag = true;
		File f = new File(path);
		if(!f.exists()) {
			System.out.println("!!!!!!!! " +path+ " was missing .Please check missing file, and put the right file in right path !!!!!!!!");
			flag = false;
		}
		return flag;
	}
	
//	读取baseinfo文件
	public static Map<String, String> getBaseInfo(String path) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		boolean flag = Util.checkBank(path);
		if(flag) {
			File file = new File(path);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while(s != null) {
//				System.out.println(s);
				if(s.contains("=")) {
					String[] tmp = s.split("=");
					if(tmp.length == 2) {
						map.put(tmp[0], tmp[1]);
					}else{
						System.out.println(tmp[0] + " Key is without Value, Please check");
					}
				}
				s = br.readLine();
			}
			
		}else {
			System.out.println("!!!!!!!! Please check " + path + " !!!!!!!!");
		}
		
		return map;
	}
	
//	选择ETL输入类型
	public static String selectETLInput() {
		boolean flag = true;
		String s = "";
		while(flag){
			System.out.println("1 for Nomal Json , 2 for ProtocolBuff V1 Json ,"
					+ " 3 for ProtocolBuff V2 Json");
			System.out.print("Please select ETL input type : ");
			s = Util.getInput().trim();
			if(s.contentEquals("1") || s.contentEquals("2") || 
					s.contentEquals("3") || s == "1" || 
					s == "2" || s == "3" ) {
				flag = false;
			}else {
				System.out.println("Please input 1,2,3 to countinue...");
			}
		}
		return s;
	}
	
//	portraitmap生成
	public static Map<String, String> genportraitmap(String[] portrait1,String[] portrait2,Map<String, String> exchangemap) {
		Map<String, String> portraitmap = new HashMap<String, String>();
		if(portrait1.length > 0) {
			for(String s : portrait1) {
				portraitmap.put(s, exchangemap.get(s));
			}
		}else{
			System.out.println("!!!!!!!! portrait1 is incorrect please check baseinfo.txt");
		}
		
		if(portrait2.length > 0) {
			for(String s : portrait2) {
				portraitmap.put(s, exchangemap.get(s));
			}
		}else{
			System.out.println("!!!!!!!! portrait2 is incorrect please check baseinfo.txt");
		}
		
		return portraitmap;
	}
	
//	测试方法：
	public static boolean printMap(Map<String, String> map) {
		Set<String> keyset = map.keySet();
		boolean flag = true;
		for(String k : keyset) {
//			System.out.println("Key = " + k +"   Val = " + map.get(k));
			if((map.get(k)+"").equals("null") || (map.get(k)+"") == "null") {
				flag = false;
			}
		}
		return flag;
	}
	public static boolean printIntegerMap(Map<String, Integer> map) {
		Set<String> keyset = map.keySet();
		boolean flag = true;
		for(String k : keyset) {
//			System.out.println("Key = " + k +"   Val = " + map.get(k));
			if((map.get(k)+"").equals("null") || (map.get(k)+"") == "null") {
				flag = false;
			}
		}
		return flag;
	}
}
