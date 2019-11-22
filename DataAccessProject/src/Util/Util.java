package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import net.sf.json.JSONObject;

public class Util {
	
	public static String getInputType(Scanner sc) {
		String input = "";
		boolean flag = true;
//		input = sc.next().toLowerCase().trim();
		while(flag) {
			input = sc.next().trim();
			if(input != "1" && !input.equals("1") && input != "2" && !input.equals("2")) {
//				System.out.println(input.length());
				System.out.print("Please type 1 or 2 :");
			}else {
				flag = false;
				break;
			}
		}
		return input;
	}
	
	public static String getInput(Scanner sc) {
		String input = "";
		input = sc.next().trim();
		return input;
	}
	
	public static void filterVin(String inputpath,String outputpath,String vin) throws Exception {
		FileReader fr = new FileReader(inputpath);
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter(outputpath);
		BufferedWriter bw = new BufferedWriter(fw);
		FileInitialize(outputpath);
		String s = br.readLine();
		while(s != null) {
			if(s.contains(vin)) {
				bw.write(s);
				bw.newLine();
				bw.flush();
			}
			s = br.readLine();
		}
		bw.close();
		fw.close();
		br.close();
		fr.close();
	}
	
//	�����ʱ�ļ���ʼ��
	public static void FileInitialize(String path) throws Exception{
		File f = new File(path);
		if(f.exists()){
			f.delete();
			f.createNewFile();
		}else{
			f.createNewFile();
		}
	}
	
//	��ȡbaseinfo�ļ�
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
						map.put(tmp[0].toLowerCase(), tmp[1]);
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
	
//	У��Bank�ļ��Ƿ����
	public static boolean checkBank(String path) {
		boolean flag = true;
		File f = new File(path);
		if(!f.exists()) {
			System.out.println("!!!!!!!! " +path+ " was missing .Please check missing file, and put the right file in right path !!!!!!!!");
			flag = false;
		}
		return flag;
	}
	
//	���ڸ�ʽ��
	public static String timeFormat(String str) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(Long.parseLong(str));
		return simpleDateFormat.format(date);
	}
	
//	Xls�ļ�������ȡ
	public static HSSFWorkbook openXlsfile(String path) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		return workbook;
	}
	
//	Xls��ȡsheetname
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
	
//	Xls��ȡsheet����
	public static HSSFSheet getXlssheet(HSSFWorkbook workbook,String sheetname) {
		HSSFSheet Sheet = workbook.getSheet(sheetname);
		return Sheet;
	}
	
//	Xls��ȡ����Ϣ
	public static Map<String, Integer> getXlsCellPosition(HSSFSheet tarsheet,Row row) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int frownum = tarsheet.getFirstRowNum();
		int erownum = tarsheet.getLastRowNum();
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
		    	map.put(cellVelue.trim().toLowerCase(), i);
		    }
		}
//		flag = Util.printIntegerMap(map);
//		if(!flag) {
//			map.clear();
//		}
		return map;
	}
//	Xls��ѯ�л�ȡĿ����ֵ	
	public static List<Map<String, String>> getXlsCellVal(Map<String, Integer> headinfo,Map<String, String> jsonmap,
			Map<String, String> basemap,Map<String, String> errmap,Map<String, String> normalmap,HSSFSheet Sheet,
			int exceloffset,float mesure) 
			throws Exception {
	
	List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	int frownum = headinfo.get("frownum");
	int erownum = headinfo.get("erownum");
	int timepos = headinfo.get("ʱ��");
	boolean flag = false;
	String jsonkey,excindex,dic;
	Row row;
	DecimalFormat df=new DecimalFormat("0.00");
//  �ж�ȡʱ��ƥ��
//	��ѵ��ȡ
	for(int i = frownum+exceloffset ; i <= erownum; i++) {
		row = Sheet.getRow(i);
		String exceltime = Util.UStypeCovert(row.getCell(timepos).getDateCellValue()+"");
//		System.out.println(exceltime);
		if(exceltime == jsonmap.get("reporttime") || exceltime.equals(jsonmap.get("reporttime"))) {
			jsonmap.remove("reporttime");
			jsonmap.remove("vin");
			Set<String> jsonkeyset = jsonmap.keySet();
			Iterator<String> it = jsonkeyset.iterator();
			while(it.hasNext()) {
				jsonkey = it.next();
				if(jsonkey.contains("valid")) {
//					JSON Key����Valid
					excindex = headinfo.get(jsonkey)+"";
					if(excindex != "null" && !excindex.equals("null")) {
//						ֱ��ȡֵ����ȡ��,�ֵ��ȡfactor��offset
						dic = basemap.get(jsonkey)+"";
						if(dic != "null" && !dic.equals("null")) {
//							�ʵ���ڲ�ѯֵ
							String exclvalue = (row.getCell(Integer.parseInt(excindex)).getNumericCellValue()+"").trim();
							if(Util.typeCovert(exclvalue)) {
								String[] tmp = dic.split(",");
								float factor = Float.parseFloat(tmp[0]);
								float offset = Float.parseFloat(tmp[1]);
								float jsonResvalue = Float.parseFloat(jsonmap.get(jsonkey));
								float excOrigvalue = Float.parseFloat(exclvalue);
								float exeRes = excOrigvalue*factor+offset;
								String diff = df.format(exeRes-jsonResvalue);
								flag = Util.mesureGap(diff, mesure);
//								normalmap.put("reporttime", exceltime);
//								out: reporttimems,Origvalue,factor,offset,cucalateres,excres,differ,flag
								normalmap.put(jsonkey, jsonmap.get("reporttimems")+","+excOrigvalue + ","+ factor + "," + offset + "," + exeRes + "," + jsonResvalue + "," + diff + "," + flag );
//								System.out.println("Formula : " + excOrigvalue + " * "+ factor + " + " + offset + " = " + exeRes + " ;  EXCEL result is : " + jsonResvalue + " Differ is " + (exeRes-jsonResvalue));
							}else {
								errmap.put("Report time", exceltime);
								errmap.put(jsonkey + "in Excel ", "Key in excel but no value. ");
							}
							
						}else {
//							�ʵ䲻���ڲ�ѯֵ
							errmap.put("Report time", exceltime);
							errmap.put(jsonkey, "Dictionary can not found factor and offset.");
						}
						
					}else {
//						ȡ����ֵ
						errmap.put("Report time", exceltime);
						errmap.put(jsonkey, "Excel can not found key.");
					}
//					else {
////						ֱ��ȡֵȡ������ʱ��ȥ��valid��ȡ
//						String jsonkeysplit = jsonkey.split("valid")[0];
//						excindex = headinfo.get(jsonkeysplit)+"";
//						if(excindex != "null" || !excindex.equals("null")) {
////							ȥ��validȡ��ֵ
//							dic = basemap.get(jsonkeysplit)+"";
//							if(dic != "null" || !dic.equals("null")) {
////								�ʵ���ڲ�ѯֵ
//								String[] tmp = dic.split(",");
//								float factor = Float.parseFloat(tmp[0]);
//								float offset = Float.parseFloat(tmp[1]);
//								float jsonResvalue = Float.parseFloat(jsonmap.get(jsonkeysplit));
//								float excOrigvalue = Float.parseFloat(row.getCell(Integer.parseInt(excindex)).getStringCellValue().trim());
//								float exeRes = excOrigvalue*factor+offset;
//								System.out.println("Formula : " + excOrigvalue + " * "+ factor + " + " + offset + " = " + exeRes + " ;  EXCEL result is : " + jsonResvalue + " Differ is " + (exeRes-jsonResvalue));
//							}else {
////								�ʵ䲻���ڲ�ѯֵ
//								errmap.put("Report time", exceltime);
//								errmap.put(jsonkey+" delete valid", "Delete valid Dictionary still can not found factor and offset.");
//							}
//						}else {
////							ȥ��valid��Ȼȡ����ֵ
//							errmap.put("Report time", exceltime);
//							errmap.put(jsonkey+" delete valid", "Delete valid excel still can not found value.");
//						}
//					}
				}else if(jsonkey != "reporttimems" && !jsonkey.equals("reporttimems")){
//					JSON Key����valid
					excindex = headinfo.get(jsonkey)+"";
//					System.out.println(excindex != "null");
//					System.out.println(excindex.equals("null"));
					if(excindex != "null" && !excindex.equals("null")) {
//						ֱ��ȡ����ȡ��ֵ
						dic = basemap.get(jsonkey)+"";
						if(dic != "null" && !dic.equals("null")) {
//							�ʵ���ڲ�ѯֵ
							String exclvalue = (row.getCell(Integer.parseInt(excindex)).getNumericCellValue()+"").trim();
							if(Util.typeCovert(exclvalue)) {
								String[] tmp = dic.split(",");
								float factor = Float.parseFloat(tmp[0]);
								float offset = Float.parseFloat(tmp[1]);
								float jsonResvalue = Float.parseFloat(jsonmap.get(jsonkey));
								float excOrigvalue = Float.parseFloat(exclvalue);
								float exeRes = excOrigvalue*factor+offset;
								String diff = df.format(exeRes-jsonResvalue);
								flag = Util.mesureGap(diff, mesure);
//								normalmap.put("reporttime", exceltime);
//								out: reporttimems,Origvalue,factor,offset,cucalateres,excres,differ,flag
								normalmap.put(jsonkey, jsonmap.get("reporttimems")+","+excOrigvalue + ","+ factor + "," + offset + "," + exeRes + "," + jsonResvalue + "," + diff + "," + flag );
//								System.out.println("Formula : " + excOrigvalue + " * "+ factor + " + " + offset + " = " + exeRes + " ;  EXCEL result is : " + jsonResvalue + " Differ is " + (exeRes-jsonResvalue));
							}else {
								errmap.put("Report time", exceltime);
								errmap.put(jsonkey + "in Excel ", "Key in excel but no value. ");
							}
						}else {
//							�ʵ䲻���ڲ�ѯֵ
							errmap.put("Report time", exceltime);
							errmap.put(jsonkey, "Dictionary can not found factor and offset.");
						}
					}else {
//						ֱ��ȡȡ����ֵ
						errmap.put("Report time", exceltime);
						errmap.put(jsonkey, "Excel can not found key.");
					}
				}
			}
		}
	}
	list.add(errmap);
	list.add(normalmap);
	return list;
}
	
	
//	CSV��ѯ�л�ȡĿ����ֵ	
	public static List<Map<String, String>> getCSVVal(Map<String, Integer> headinfo,Map<String, String> jsonmap,
			Map<String, String> basemap,Map<String, String> errmap,Map<String, String> normalmap,
			String inputpath,float mesure) 
			throws Exception {
	
	List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	int timepos = headinfo.get("ʱ��");
	boolean flag = false;
	String jsonkey,dic,CSVtime,excindex;
	DecimalFormat df=new DecimalFormat("0.00");
	File f = new File(inputpath);
	FileReader fr = new FileReader(f);
	BufferedReader br = new BufferedReader(fr);
	String line = br.readLine();
	String[] linesplit;
//  �ж�ȡʱ��ƥ��
//	��ѵ��ȡ
	while(line!=null) {
		if(!line.contains("ʱ��")) {
			linesplit = line.split(",");
			CSVtime = linesplit[timepos].replaceAll("-", "/");
//			System.out.println(CSVtime);
//			System.out.println(jsonmap.get("reporttime"));
			if(CSVtime == jsonmap.get("reporttime") || CSVtime.equals(jsonmap.get("reporttime"))) {
				jsonmap.remove("reporttime");
				jsonmap.remove("vin");
				Set<String> jsonkeyset = jsonmap.keySet();
				Iterator<String> it = jsonkeyset.iterator();
				while(it.hasNext()){
					jsonkey = it.next();
//					System.out.println(jsonkey);
					if(jsonkey.contains("valid")) {
//						JSON Key����Valid
						excindex = headinfo.get(jsonkey)+"";
//						System.out.println(excindex);
						if(excindex != "null" && !excindex.equals("null")) {
//							ֱ��ȡֵ����ȡ��,�ֵ��ȡfactor��offset
							dic = basemap.get(jsonkey)+"";
							if(dic != "null" && !dic.equals("null")) {
//								�ʵ���ڲ�ѯֵ
								String exclvalue = linesplit[Integer.parseInt(excindex)].trim();
								if(Util.typeCovert(exclvalue)) {
									String[] tmp = dic.split(",");
									float factor = Float.parseFloat(tmp[0]);
									float offset = Float.parseFloat(tmp[1]);
									float jsonResvalue = Float.parseFloat(jsonmap.get(jsonkey));
									float excOrigvalue = Float.parseFloat(exclvalue);
									float exeRes = excOrigvalue*factor+offset;
									String diff = df.format(exeRes-jsonResvalue);
									flag = Util.mesureGap(diff, mesure);
//									normalmap.put("reporttime", exceltime);
//									out: reporttimems,Origvalue,factor,offset,cucalateres,excres,differ,flag
									normalmap.put(jsonkey, jsonmap.get("reporttimems")+","+excOrigvalue + ","+ factor + "," + offset + "," + exeRes + "," + jsonResvalue + "," + diff + "," + flag );
//									System.out.println("Formula : " + excOrigvalue + " * "+ factor + " + " + offset + " = " + exeRes + " ;  EXCEL result is : " + jsonResvalue + " Differ is " + (exeRes-jsonResvalue));
								}else {
									errmap.put("Report time", CSVtime);
									errmap.put(jsonkey + "in Excel ", "Key in excel but no value. ");
								}
								
							}else {
//								�ʵ䲻���ڲ�ѯֵ
								errmap.put("Report time", CSVtime);
								errmap.put(jsonkey, "Dictionary can not found factor and offset.");
							}
							
						}else {
//							ȡ����ֵ
							errmap.put("Report time", linesplit[timepos]);
							errmap.put(jsonkey, "Excel can not found key.");
						}
					}else if(jsonkey != "reporttimems" && !jsonkey.equals("reporttimems")){
//						JSON Key����valid
//						System.out.println(jsonkey);
//						System.out.println(headinfo.get(jsonkey));
						excindex = headinfo.get(jsonkey)+"";
//						System.out.println(excindex != "null");
//						System.out.println(excindex.equals("null"));
						if(excindex != "null" && !excindex.equals("null")) {
//							ֱ��ȡ����ȡ��ֵ
							dic = basemap.get(jsonkey)+"";
//							System.out.println(jsonkey);
//							System.out.println(dic);
							if(dic != "null" && !dic.equals("null")) {
//								�ʵ���ڲ�ѯֵ
								String exclvalue = (linesplit[Integer.parseInt(excindex)]).trim();
								if(Util.typeCovert(exclvalue)) {
									String[] tmp = dic.split(",");
									float factor = Float.parseFloat(tmp[0]);
									float offset = Float.parseFloat(tmp[1]);
									float jsonResvalue = Float.parseFloat(jsonmap.get(jsonkey));
									float excOrigvalue = Float.parseFloat(exclvalue);
									float exeRes = excOrigvalue*factor+offset;
									String diff = df.format(exeRes-jsonResvalue);
									flag = Util.mesureGap(diff, mesure);
//									normalmap.put("reporttime", exceltime);
//									out: reporttimems,Origvalue,factor,offset,cucalateres,excres,differ,flag
									normalmap.put(jsonkey, jsonmap.get("reporttimems")+","+excOrigvalue + ","+ factor + "," + offset + "," + exeRes + "," + jsonResvalue + "," + diff + "," + flag );
//									System.out.println("Formula : " + excOrigvalue + " * "+ factor + " + " + offset + " = " + exeRes + " ;  EXCEL result is : " + jsonResvalue + " Differ is " + (exeRes-jsonResvalue));
								}else {
									errmap.put("Report time", CSVtime);
									errmap.put(jsonkey + "in Excel ", "Key in excel but no value. ");
								}
							}else {
//								�ʵ䲻���ڲ�ѯֵ
								errmap.put("Report time", CSVtime);
								errmap.put(jsonkey, "Dictionary can not found factor and offset.");
							}
						}else {
//							ֱ��ȡȡ����ֵ
							errmap.put("Report time", CSVtime);
							errmap.put(jsonkey, "Excel can not found key.");
						}
					}
				}
			}
		}
		line = br.readLine();
	}
	list.add(errmap);
	list.add(normalmap);
	return list;
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
//	��ӡStirng map
	public static boolean printStringMap(Map<String, String> map) {
		Set<String> keyset = map.keySet();
		boolean flag = true;
		for(String k : keyset) {
			System.out.println("Key is " + k +"   Error msg is " + map.get(k));
		}
		return flag;
	}
	
//	У������
	public static boolean typeCovert(String value) {
		boolean flag = true;
		try {
			Float.parseFloat(value);
		}catch(Exception e) {
			flag = false;
		}
		return flag;
	}
	
//	дerror
	public static Map<String, String> errmsg(String key,Map<String, String> errormap) {
			errormap.put(key, "Key in excel but no value. ");
		return errormap;
	}
	
//	����vin��ȡtype
	public static String getType(String path,String vin) throws Exception {
		String type = "0";
		boolean flag = Util.checkBank(path);
		Map<String, String> jsonmap = new HashMap<String, String>();
		if(flag) {
			File file = new File(path);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while(s != null) {
				if(s.contains(vin)) {
					JSONObject jso = JSONObject.fromObject(s);
					jsonmap = JSONUtil.keyIterator(jsonmap, jso);
//					Util.printStringMap(jsonmap);
					if(jsonmap.get("canSignalType".toLowerCase()).contains("CH141")) {
						type = "1";
					}else if(jsonmap.get("canSignalType".toLowerCase()).contains("CHB073") || jsonmap.get("canSignalType").contains("CHB071")) {
						type = "2";
					}
					s = null;
				}else {
					s = br.readLine();
				}
			}
		}	
		return type;
	}
	
//	����ʱ��ת��
	public static String UStypeCovert(String ustime) throws Exception {
		Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(ustime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String d = df.format(date);
        return d;
	}
	
//	��ֵ�жϸ���������ж�
	public static boolean mesureGap(String diff,float mesure) {
		float tmp = Float.parseFloat(diff);
		if(tmp-mesure != 0) {
			return false;
		}else {
			return true;
		}
	}
	
//	��ȡCSV��ͷ
	public static Map<String, Integer> getCSVheadinfo(String path,Map<String, Integer> headinfo) throws Exception{
		File f = new File(path);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();
		if(s.contains("VIN,ʱ��")) {
			String[] tmp = s.split(",");
			if(tmp.length > 0) {
				for(int i = 0; i < tmp.length; i++) {
					headinfo.put(tmp[i].toLowerCase(), i);
				}
			}
		}else {
			headinfo.clear();
			System.out.println("CSV without head.");
		}
		
		return headinfo;
	}
	
}
