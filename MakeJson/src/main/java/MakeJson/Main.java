package MakeJson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Util.Util;
import kafka.KafkaMethod;

/**
 * 
 * MakeJson.main.java程序说明
 * 1，在程序根目录中需要创建Source文件夹，Case文件夹（存放EXCEL用例），Baseinfo文件夹（用于存放基本信息，便于维护）Kafka文件夹。
 * 2，初始化文件说明：bank.txt文件为Json字段中所有K-V的初始值，Json定义中增加或者减少或者改变，对应bank文件中需要按照最新的Json 1.x文档
 * 字段定义维护K-V值。baseinfo.txt中为各个Json中需要的字段
 * 3，用例文件说明：用例为excel文件，可xls,xlsx格式，格式以及内容需要严格按照规则进行。
 * 4，需要维护：程序Main中基础配置，表头信息，文件夹中用例文件，标准文件（bank.txt），env内字段，gps内字段，page内字段，err内字段（baseinfo.txt需要依据Json定义进行维护）
 * 5，程序Util类中需要维护：   Jversion选择方法——chooseJversion；
 *						生成JSON串方法——makeJsonStr中的switch语句中的case值（随chooseJversion返回而变动），case下Json格式的增删改。
 * 6,特殊注意：如果Json任何字段值与渠道，品牌关联的，需要在bank文件中对应字段填写一个可以关联上的值例如device_id，
 * 	如果造长城的数据则bank文件中的device_id填写一个可以对应长城的值。
 * 7,输出造Json串结果文件保存在Source/DataResultFolder/ 命名规则：sheet+func+testpoint+JSONbaseversion.txt
 * 8,模式选择：普通做串选择1，ETL做串选择2，区别为ETL做完串后直接放Kafka中打。
 * 9,Kafka运行后再Source中生成device_id_sheetname.txt,ALLJson_sheetname.txt,两个文件分别记录往Kafka中打的数据的device_id和JSON串。
 *10,情景智能用例如不设置reportTime，则在程序中有初始时间戳，且每条间隔2分钟，如需自己设置，则时间结尾用001表示。
 * */

public class Main {

	public static void main(String[] args) throws Exception {
//		基本配置
		String path = "Source/";
		String basepath = "Baseinfo/";
		String casepath = "DataResultFolder/";
		String caseSpath = "Case/";
//		用例文件名称
//		String defaultfilename = "DataTestCase-Nomal.xls";
		String defaultfilename = "DataTestCase-ETL.xls";
//		String defaultfilename = "DataTestCase.xlsx";
//		情景智能时间间隔计数
		int count = 1;
		
//		标准文件
		String bankname = basepath + "bank.txt";
		String basename = basepath +"baseinfo.txt";
//		总输出文件写入
		String origoutputname = "origJson.txt";
//		Json生成后Kafka处理标称
		boolean flag = true;
//		boolean flag = false;
		
		Map<String, String> basemap = Util.getBaseInfo(path+basename);
//		Json基本版本获取
		String[] Jbasev = basemap.get("Jbasev").split(",");
//		查找用例列名称以及表头占行数量
		String funcname = "功能模块";
		String testpointname = "测试点";
		String isjumpname = "是否跳过";
		String inputcontentname = "输入数据";
		String iscopy = "复制";
		String copytimes = "复制次数";
		String Jversion = "JSON基版本";
		String contentindex = "测试用例";
		int offset = 2;
//		env内字段
		String[] evnstr145 = basemap.get("evnstr145").split(",");
		String[] evnstr100 = basemap.get("evnstr100").split(",");
//		gps内字段
		String[] gpsstr145 = basemap.get("gpsstr145").split(",");
		String[] gpsstr100 = basemap.get("gpsstr100").split(",");
//		page内字段
		String[] pagestr145 = basemap.get("pagestr145").split(",");
		String[] pagestr100 = basemap.get("pagestr100").split(",");
//		err内字段
		String[] errstr100= basemap.get("errstr100").split(",");
		
//		TSP各种字段：
		String[] actor = basemap.get("actor").split(",");
		String[] decice = basemap.get("decice").split(",");
		String[] envtsp = basemap.get("envtsp").split(",");
		String[] evttsp = basemap.get("evttsp").split(",");
		
//		用户画像各种字段：
		String[] portrait1 = basemap.get("portrait1").split(",");
		String[] portrait2 = basemap.get("portrait2").split(",");

//		情景智能字段：
		String[] IntelligentScenario = basemap.get("IntelligentScenario").split(",");
//		时间戳
		long ts = Long.parseLong(basemap.get("startTS"));
		long tsplus = Long.parseLong(basemap.get("timeinterval"));
//		Kafka 配置信息
		String topic = basemap.get("topic");
//		System.out.println("TOPIC     :      "+topic);
		String server = basemap.get("server");
		
		
//		查找用例列数字初始化
		int frownum = 0;
		int erownum = 0;
		int funcnamenumnum = 0;
		int testpointnum = 0;
		int isjumpnamenum = 0;
		int inputcontentnum = 0;
		int iscopynum = 0;
		int copytimesnum = 0;
		int Jversionnum = 0;
//		查找用例列内容初始化
		String funcnameval = "";
		String testpointval = "";
		String isjumpval = "";
		String Jversionval = "";
		

//		交互临时命名
		String filename = Util.changeTestCaseFileName(defaultfilename, args);;
		String tmpfuncval = funcnameval;
		String tmptestpointval = testpointval;
		String tmpisjumpval = isjumpval;
		
		String tmpJversionval = Jversionval;
		int sheetlistsize = 0;
		int indexflag = Util.getExcelType(path+caseSpath+filename);
//		System.out.println("Type is "+indexflag);
		Map<String, String> defaultmap = new HashMap<String, String>();
		Map<String, String> exchangemap = new HashMap<String, String>();
		Map<String, Integer> positionmap = new HashMap<String, Integer>();
		ArrayList<String> sheetlist = new ArrayList<String>();
		
//		主程序入口
//		初始化文件夹
		Util.checkDir(path);
		Util.checkDir(path+basepath);
		Util.checkDir(path+casepath);
		Util.checkDir(path+caseSpath);
		Util.cleanDRF(path+casepath);
//		Row对象
		Row row = null;
		FileWriter fw = null;
		BufferedWriter orgibw = null;
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		boolean flagchoose = true;
		String type = "";
		while(flagchoose) {
			System.out.print("'1' for Normal subject, '2' for ETL test \nPlease Choose Test Subject : ");
			type = sc.next().trim();
			if(type.equals("1") || type.equals("2") || type == "1" || type == "2") {
				if(type.equals("2") || type == "2") {
					System.out.println("######## Processing Starting for ETL mode ########");
				}
				flagchoose = false;
				break;
			}else {
				System.out.println("!!!!!!!! Wrong input chooses ,Please input the right number !!!!!!!!");
			}
		}
		
		if(!Util.checkBank(path+bankname)) {
			indexflag = 10000;
		}
		switch(indexflag) {
			case 0 :
				System.out.println("!!!!!!!! Base configuration : "+path+caseSpath+filename+" wrong input or file does not exist.Please check. !!!!!!!!");
				System.out.println("######## Processing is closed. ########");
			break;
			case 1 :
//				xls
				fw = new FileWriter(path+casepath+origoutputname);
				orgibw = new BufferedWriter(fw);
				HSSFWorkbook hworkbook = Util.openXlsfile(path+caseSpath+filename);
//				需要筛选sheet中含有测试用例文字的sheet页
				sheetlist = Util.getXlssheetname(hworkbook,contentindex);
				sheetlistsize = sheetlist.size();
				if(sheetlistsize == 0) {
					System.out.println("!!!!!!!! There are no sheets name with '测试用例' in file : "+ filename +" !!!!!!!!" );
				}else {
					for(int i = 0;i < sheetlistsize; i++) {
						String sheetname = sheetlist.get(i);
						System.out.println("######## Starting Processing Sheet : "+filename+"\\"+sheetname+ " ########");
						System.out.println();
						HSSFSheet tarsheet = Util.getXlssheet(hworkbook, sheetname); 
						positionmap = Util.getXlsCellPosition(tarsheet, row, funcname, testpointname, inputcontentname, iscopy,
								copytimes, Jversion,isjumpname);
//						Util.printIntegerMap(positionmap);
						if(positionmap.isEmpty()) {
							System.out.println("!!!!!!!! "+ sheetname + " this sheet is empty please delete empty sheets like "
									+ "this one. !!!!!!!!");
							System.out.println();
							break;
						}else {
	//						判断map中是否存在funcname，testpointname列
							String tmpfuncname = positionmap.get(funcname)+"";
							String tmptestpointname = positionmap.get(testpointname)+"";
							if(tmpfuncname.equals("null") || tmptestpointname.equals("null")
									|| tmpfuncname == "null" || tmptestpointname == "null") {
								System.out.println("!!!!!!!! "+sheetname + "can not found column : " + funcname+ " or " + 
									testpointname + "please check this sheet. !!!!!!!!");
								System.out.println();
							}else {
		//						轮询取值方法入口
								String kafkapath = Util.getXlsCellValAndWrite(bankname,sheetname, tarsheet, row, positionmap, offset, frownum, erownum, 
										funcnamenumnum, testpointnum, inputcontentnum, iscopynum, copytimesnum, Jversionnum,
										funcnameval, testpointval, Jversionval, tmpfuncval, tmptestpointval, tmpJversionval, 
										funcname, testpointname, inputcontentname, iscopy, copytimes, Jversion, path,casepath, 
										orgibw, defaultmap, exchangemap, evnstr145, evnstr100, gpsstr145, gpsstr100, pagestr145, 
										pagestr100, errstr100,Jbasev,isjumpname,isjumpnamenum,isjumpval,tmpisjumpval,actor,
										decice,envtsp,evttsp,portrait1,portrait2,IntelligentScenario,ts,tsplus);
								System.out.println();
								System.out.println("######## "+ sheetname + "'s case is finished. ########");
								System.out.println();
								if(type.equals("2") || type == "2") {
									System.out.println("Data of " + sheetname + " was stored with path : " +  kafkapath);
									System.out.println("Type Q to use default topic '"+topic+"' or input the new topic that you want to send : ");
									String input = sc.next().trim();
									if(input.toLowerCase().equals("q") || input.toLowerCase() == "q") {
										System.out.println("Sending data to "+topic);
										KafkaMethod.kafkaProducerMethod(kafkapath,sheetname,topic,server,flag);										
									}else {
										System.out.println("Sending data to "+input);
										KafkaMethod.kafkaProducerMethod(kafkapath,sheetname,input,server,flag);
									}
								}
							}
						}
						if(type.equals("2") || type == "2") {
							if(i != sheetlistsize-1) {
								System.out.println("Press anykey to countinue start processing next sheet.");	
								sc.next();
							}
						}
					}
				}
				System.out.println();
				System.out.println("######## All cases are finished. ########");
				System.out.println();
				sc.close();
			break;
			case 2 :
//				xlsx
				fw = new FileWriter(path+casepath+origoutputname);
				orgibw = new BufferedWriter(fw);
				XSSFWorkbook xworkbook = Util.openXlsxfile(path+caseSpath+filename);
				sheetlist = Util.getXlsxsheetname(xworkbook,contentindex);
				sheetlistsize = sheetlist.size();
				if(sheetlistsize == 0) {
					System.out.println("!!!!!!!! There are no sheets name with '测试用例' in file : "+ filename+" !!!!!!!!" );
				}else {
					for(int i = 0;i < sheetlistsize;i++) {
						String sheetname = sheetlist.get(i);
						System.out.println("######## Starting Processing Sheet : "+filename+"\\"+sheetname+ " ########");
						XSSFSheet tarsheet = Util.getXlsxsheet(xworkbook, sheetname); 
						positionmap = Util.getXlsxCellPosition(tarsheet, row, funcname, testpointname, inputcontentname, iscopy, 
								copytimes, Jversion,isjumpname);
//						Util.printIntegerMap(positionmap);
						if(positionmap.isEmpty()) {
							System.out.println("!!!!!!!! "+ sheetname + " this sheet is empty please delete empty sheets like "
									+ "this one. !!!!!!!!");
							break;
						}else {
	//						判断map中是否存在funcname，testpointname列
							String tmpfuncname = positionmap.get(funcname)+"";
							String tmptestpointname = positionmap.get(testpointname)+"";
							if(tmpfuncname.equals("null") || tmptestpointname.equals("null")
									|| tmpfuncname == "null" || tmptestpointname == "null") {
								System.out.println("!!!!!!!! "+ sheetname + "can not found column : " + funcname+ " or " + 
									testpointname + "please check this sheet. !!!!!!!!");
							}else {
		//						轮询取值方法入口
								String kafkapath = Util.getXlsxCellValAndWrite(bankname,sheetname, tarsheet, row, positionmap, offset, frownum, erownum, 
										funcnamenumnum, testpointnum, inputcontentnum, iscopynum, copytimesnum, Jversionnum, 
										funcnameval, testpointval, Jversionval, tmpfuncval, tmptestpointval, tmpJversionval, 
										funcname, testpointname, inputcontentname, iscopy, copytimes, Jversion, path,casepath, 
										orgibw, defaultmap, exchangemap, evnstr145,evnstr100, gpsstr145,gpsstr100, pagestr145,
										pagestr100,errstr100,Jbasev,isjumpname,isjumpnamenum,isjumpval,tmpisjumpval,actor,
										decice,envtsp,evttsp,portrait1,portrait2,IntelligentScenario,ts,tsplus);
								System.out.println();
								System.out.println("######## "+ sheetname + "'s case is finished. ########");
								System.out.println();
								if(type.equals("2") || type == "2") {
//									System.out.println("Press any key to Start sendding "+sheetname+"'s case JSON to kafka.\nType 'Q' to exit and countinue create JSON on next sheet : ");
									System.out.println("Data of " + sheetname + " was stored with path : " +  kafkapath);
									System.out.print("Please input the topic your want to send : ");
									topic = sc.next().trim();
									KafkaMethod.kafkaProducerMethod(kafkapath,sheetname,topic,server,flag);
								}
							}
						}
						if(type.equals("2") || type == "2") {
							if(i != sheetlistsize-1) {
								System.out.println("Press anykey to countinue start processing next sheet.");	
								sc.next();
							}
						}
					}
				}
				System.out.println();
				System.out.println("######## All cases ware finished. ########");
				System.out.println();
			break;
			default:
				System.out.println("!!!!!!!! Input file is missing, Please check !!!!!!!!");
			break;
		}
		sc.close();
	}

}
