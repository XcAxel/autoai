package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
			String[] decice,String[] envtsp,String[] evttsp) 
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
		System.out.println("Data file was created complete and save path is : "+newpath);
		System.out.println();
//		
		FileWriter tmpfw = null;
		BufferedWriter tmpbw = null;
		Util.FileInitializeDir(newdirpath);
		Util.FileInitialize(newpath);
		tmpfw = new FileWriter(newpath);
		tmpbw = new BufferedWriter(tmpfw);
//		Bank初始化
//		defaultmap = Util.getBank(path+bankname);
		exchangemap = Util.getBank(path+bankname);
//		
//		轮训获取
	    for(int i = frownum+offset ; i <= erownum; i++) {
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmptestpointval, tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmptestpointval, tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
					System.out.println("Data file was created complete and save path is : "+newpath);
					System.out.println();
					Util.FileInitializeDir(newdirpath);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
			String[] decice,String[] envtsp,String[] evttsp) throws Exception {
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
					System.out.println("Data file was created complete and save path is : "+newpath);
					System.out.println();
					Util.FileInitializeDir(newdirpath);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
												tmpbw.flush();
												tmpbw.newLine();
												
											}
										}
									}else {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										复制写
										for(int l = 0; l <= copytimesval; l++) {
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											复制写
											for(int l = 0; l <= copytimesval; l++) {
												orgibw.write(exchangemap.get("device_id") + "=" + jstr);
												orgibw.flush();
												orgibw.newLine();
												tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									复制写
									for(int l = 0; l <= copytimesval; l++) {
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
											tmpbw.flush();
											tmpbw.newLine();
										}
									}else {
										exchangemap.put(key,value1);
//										造串入口，返回一个串

										String jstr = Util.makeJsonStr(exchangemap, envstr145,envstr100, gpsstr145, gpsstr100, 
												pagestr145, pagestr100, errstr100, sheetname, tmpfuncval, tmptestpointval, 
												tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//										写一次
										orgibw.write(exchangemap.get("device_id") + "=" + jstr);
										orgibw.flush();
										orgibw.newLine();
										tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
													tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//											写一次
											orgibw.write(exchangemap.get("device_id") + "=" + jstr);
											orgibw.flush();
											orgibw.newLine();
											tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
											tmpJversionval,Jbasev,actor,decice,envtsp,evttsp);
//									写一次
									orgibw.write(exchangemap.get("device_id") + "=" + jstr);
									orgibw.flush();
									orgibw.newLine();
									tmpbw.write(exchangemap.get("device_id") + "=" + jstr);
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
			f.delete();
			f.createNewFile();
		}else{
			f.createNewFile();
		}
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
		list = Util.getFileName(f);
		for(int i = 0; i< list.size(); i++) {
			File tmpf = new File(path+list.get(i));
			if(tmpf.isDirectory()) {
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
		map.put("uuid", "15589347789865548352565467532641");
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
//				Jbaseversion = TSP  ETL长城非埋点清洗专用
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
			String[] actor,String[] decice,String[] envtsp,String[] evttsp) {
		String Json = "";
		Map<String, String> envmap145 = new HashMap<String, String>();
		Map<String, String> gpsmap145 = new HashMap<String, String>();
		Map<String, String> pagemap145 = new HashMap<String, String>();
		Map<String, String> evtdetailmap1 = new HashMap<String, String>();
		Map<String, String> evtdetailmap2 = new HashMap<String, String>();
		ArrayList<Map<String, String>> gps145list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> page145list = new ArrayList<Map<String, String>>();

		Map<String, String> envmap100 = new HashMap<String, String>();
		Map<String, String> envmap101 = new HashMap<String, String>();
		Map<String, String> gpsmap100 = new HashMap<String, String>();
		Map<String, String> pagemap100 = new HashMap<String, String>();
		Map<String, String> errmap100 = new HashMap<String, String>();
		Map<String, String> envmaptsp = new HashMap<String, String>();
		Map<String, String> actormaptsp = new HashMap<String, String>();
		Map<String, String> evtmaptsp = new HashMap<String, String>();
		Map<String, String> devmaptsp = new HashMap<String, String>();
		ArrayList<Map<String, String>> gps100list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> page100list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> err100list = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> evtdetaillist = new ArrayList<Map<String, String>>();
		JSONArray jsongps100list = null;
		JSONArray jsonpage100list = null;
		JSONArray jsonerr100list = null;
		JSONObject jsonenvmap145 = null;
		JSONArray jsongps145list = null;
		JSONArray jsonpage145list = null;
		
		JSONObject jsonenvmaptsp = null;
		JSONObject jsonactormaptsp = null;
		JSONObject jsonevtmaptsp = null;
		JSONObject jsondevmaptsp = null;

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
//					if(key == "vin") {
//						envmap100.put(key, exchangemap.get(key));
//					}else if(key == "d_start_ts"){
//						envmap100.put(key, exchangemap.get(key));
//					}else {
//						envmap100.put(key, exchangemap.get(key));		
//					}
				}
				for(int i = 0; i < gpsstr100.length; i++) {
					String key = gpsstr100[i];
//					System.out.println(key);
					gpsmap100.put(key, exchangemap.get(key));
				}
//				Util.printMap(gpsmap100);
				for(int i = 0; i < pagestr100.length; i++) {
					String key = pagestr100[i];
					pagemap100.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < errstr100.length; i++) {
					String key = errstr100[i];
					errmap100.put(key, exchangemap.get(key));
				}
				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
				err100list.add(errmap100);
				JSONObject jsonenvmap100 = JSONObject.fromObject(envmap100);
				jsongps100list=JSONArray.fromObject(gps100list);
				jsonpage100list=JSONArray.fromObject(page100list);
				jsonerr100list=JSONArray.fromObject(err100list);
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
							"\"gps\": "+jsongps100list.toString()+"," +
							"\"err\": "+jsonerr100list.toString()+"," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}";
			break;
			case 2 :
//				V1.4.5
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
				gps145list.add(gpsmap145);
				page145list.add(pagemap145);
				jsonenvmap145 = JSONObject.fromObject(envmap145);
				jsongps145list = JSONArray.fromObject(gps145list);
				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap145.toString()+"\"," +
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
							"\"gps\": " + jsongps145list.toString() +"\","+
							"\"page\": "+jsonpage145list.toString() 
						+"}]"
					+"}";
			break;
			case 3:
//				V1.1
				for(int i = 0; i < envstr100.length; i++) {
					String key = envstr100[i];
//					System.out.println(key);
					if(key == "vin") {
						envmap101.put(key, exchangemap.get("device_id"));
						exchangemap.put(key, exchangemap.get("device_id"));
					}else if(key == "d_start_ts"){
						envmap101.put(key, exchangemap.get("app_start_ts"));
					}else if(key == "d_stop_ts"){
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

				gps100list.add(gpsmap100);
				page100list.add(pagemap100);
				err100list.add(errmap100);
				JSONObject jsonenvmap101 = JSONObject.fromObject(envmap101);
				jsongps100list = JSONArray.fromObject(gps100list);
				jsonpage100list = JSONArray.fromObject(page100list);
				jsonerr100list = JSONArray.fromObject(err100list);
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
							"\"gps\": "+jsongps100list.toString()+"," +
							"\"err\": "+jsonerr100list.toString()+"," +
							"\"page\": "+jsonpage100list.toString() 
						+"}]"
					+"}";
			break;
			case 4 :
//				ETL SDK埋点内层V1.4.5
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
				gps145list.add(gpsmap145);
				page145list.add(pagemap145);
				jsonenvmap145 = JSONObject.fromObject(envmap145);
				jsongps145list = JSONArray.fromObject(gps145list);
				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
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
							"\"gps\": "+jsongps145list.toString()+"," +
							"\"page\": "+jsonpage145list.toString() 
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
//				for(int i = 0; i < envstr145.length; i++) {
//					String key = envstr145[i];
//					envmap145.put(key, exchangemap.get(key));
//				}
				for(int i = 0; i < gpsstr145.length; i++) {
					String key = gpsstr145[i];
					gpsmap145.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr145.length; i++) {
					String key = pagestr145[i];
					pagemap145.put(key, exchangemap.get(key));
				}
				gps145list.add(gpsmap145);
				page145list.add(pagemap145);
				jsongps145list = JSONArray.fromObject(gps145list);
				jsonpage145list = JSONArray.fromObject(page145list);
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
							"\"gps\": "+jsongps145list.toString()+"," +
							"\"page\": "+jsonpage145list.toString() 
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
//				for(int i = 0; i < envstr145.length; i++) {
//					String key = envstr145[i];
//					envmap145.put(key, exchangemap.get(key));
//				}
				for(int i = 0; i < gpsstr145.length; i++) {
					String key = gpsstr145[i];
					gpsmap145.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr145.length; i++) {
					String key = pagestr145[i];
					pagemap145.put(key, exchangemap.get(key));
				}
				gps145list.add(gpsmap145);
				page145list.add(pagemap145);
				jsongps145list = JSONArray.fromObject(gps145list);
				jsonpage145list = JSONArray.fromObject(page145list);
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
							"\"gps\": "+jsongps145list.toString()+"," +
							"\"page\": "+jsonpage145list.toString() 
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
//				for(int i = 0; i < envstr145.length; i++) {
//					String key = envstr145[i];
//					envmap145.put(key, exchangemap.get(key));
//				}
				for(int i = 0; i < gpsstr145.length; i++) {
					String key = gpsstr145[i];
					gpsmap145.put(key, exchangemap.get(key));
				}
				for(int i = 0; i < pagestr145.length; i++) {
					String key = pagestr145[i];
					pagemap145.put(key, exchangemap.get(key));
				}
				gps145list.add(gpsmap145);
				page145list.add(pagemap145);
				jsongps145list = JSONArray.fromObject(gps145list);
				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": {null}," +
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
							"\"gps\": "+jsongps145list.toString()+"," +
							"\"page\": "+jsonpage145list.toString() 
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
				for(int i = 0; i < envstr145.length; i++) {
					String key = envstr145[i];
					envmap145.put(key, exchangemap.get(key));
				}
//				for(int i = 0; i < gpsstr145.length; i++) {
//					String key = gpsstr145[i];
//					gpsmap145.put(key, exchangemap.get(key));
//				}
//				for(int i = 0; i < pagestr145.length; i++) {
//					String key = pagestr145[i];
//					pagemap145.put(key, exchangemap.get(key));
//				}
//				gps145list.add(gpsmap145);
//				page145list.add(pagemap145);
				jsonenvmap145 = JSONObject.fromObject(envmap145);
//				jsongps145list = JSONArray.fromObject(gps145list);
//				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap145.toString()+"," +
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
				for(int i = 0; i < envstr145.length; i++) {
					String key = envstr145[i];
					envmap145.put(key, exchangemap.get(key));
				}
//				for(int i = 0; i < gpsstr145.length; i++) {
//					String key = gpsstr145[i];
//					gpsmap145.put(key, exchangemap.get(key));
//				}
//				for(int i = 0; i < pagestr145.length; i++) {
//					String key = pagestr145[i];
//					pagemap145.put(key, exchangemap.get(key));
//				}
//				gps145list.add(gpsmap145);
//				page145list.add(pagemap145);
				jsonenvmap145 = JSONObject.fromObject(envmap145);
//				jsongps145list = JSONArray.fromObject(gps145list);
//				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap145.toString()+"," +
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
				for(int i = 0; i < envstr145.length; i++) {
					String key = envstr145[i];
					envmap145.put(key, exchangemap.get(key));
				}
//				for(int i = 0; i < gpsstr145.length; i++) {
//					String key = gpsstr145[i];
//					gpsmap145.put(key, exchangemap.get(key));
//				}
//				for(int i = 0; i < pagestr145.length; i++) {
//					String key = pagestr145[i];
//					pagemap145.put(key, exchangemap.get(key));
//				}
//				gps145list.add(gpsmap145);
//				page145list.add(pagemap145);
				jsonenvmap145 = JSONObject.fromObject(envmap145);
//				jsongps145list = JSONArray.fromObject(gps145list);
//				jsonpage145list = JSONArray.fromObject(page145list);
				exchangemap.put("data_id", exchangemap.get("vin")+"#"+exchangemap.get("device_id")+"#"+
						exchangemap.get("user_id")+"#"+exchangemap.get("uuid"));
				Json = "{\"datas\": [{"+
						"\"data_id\": \""+exchangemap.get("data_id")+"\"," +
						"\"ext\": \""+exchangemap.get("ext")+"\"," +
						"\"gen_ts\": \""+exchangemap.get("gen_ts")+"\"," +
						"\"env\": "+jsonenvmap145.toString()+"," +
						"\"track\": [null]"
					+"}"
					+ "],"+
					"\"pbVersion\": \""+exchangemap.get("pbVersion")+"\"," +
					"\"appKey\": \""+exchangemap.get("appKey")+"\"," +
					"\"parsedTs\": \""+exchangemap.get("parsedTs")+"\"" +
					"}";
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
			System.out.println("!!!!!!!! Bank.txt was missing .Please check bank file, and put the bank.txt @ /Source/ !!!!!!!!");
			flag = false;
		}
		return flag;
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
