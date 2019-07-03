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
 * MakeJson程序说明
 * 1，在程序根目录中需要创建Source文件夹，并将bank.txt以及用例EXCEL文件都放在此文件夹内。
 * 2，初始化文件说明：bank.txt文件为Json字段中所有K-V的初始值，Json定义中增加或者减少或者改变，对应bank文件中需要按照最新的Json 1.x文档
 * 字段定义维护K-V值。
 * 3，用例文件说明：用例为excel文件，可xls,xlsx格式，格式以及内容需要严格按照规则进行。
 * 4，程序Main中需要维护：基础配置，用例文件，标准文件，表头信息，env内字段，gps内字段，page内字段，err内字段（需要依据Json定义进行维护）
 * 5，程序Util类中需要维护：   Jversion选择方法——chooseJversion；
 *						生成JSON串方法——makeJsonStr中的switch语句中的case值（随chooseJversion返回而变动），case下Json格式的增删改。
 * 6,特殊注意：如果Json任何字段值与渠道，品牌关联的，需要在bank文件中对应字段填写一个可以关联上的值例如device_id，
 * 	如果造长城的数据则bank文件中的device_id填写一个可以对应长城的值。
 * 7,输出造Json串结果文件保存在Source/DataResultFolder/ 命名规则：sheet+func+testpoint+JSONbaseversion.txt
 * 8,模式选择：普通做串选择1，ETL做串选择2，区别为ETL做完串后直接放Kafka中打。
 * 9,Kafka运行后再Source中生成device_id_sheetname.txt,ALLJson_sheetname.txt,两个文件分别记录往Kafka中打的数据的device_id和JSON串。
 * */

public class Main {

	public static void main(String[] args) throws Exception {
//		基本配置
		String path = "Source/";
		String casepath = "DataResultFolder/";
//		用例文件名称
		String defaultfilename = "DataTestCase-ETL.xls";
//		String defaultfilename = "DataTestCase.xlsx";
//		标准文件
		String bankname = "bank.txt";
//		总输出文件写入
		String origoutputname = "origJson.txt";
//		Json生成后Kafka处理标称
//		boolean flag = true;
		boolean flag = false;
		/**
		 * Json基本版本
		 * V1.0表示ETL清洗后的输出标准JSON格式，此格式为后端输入标准格式
		 * V1.4.5表示ETL的输入JSON格式
		 * V1.1表示ETL清洗后的输出标准JSON格式，
		  * 与1.0区别在于vin=device_id值,d_start_ts=app_start_ts,d_end_ts=app_stop_ts，
		 * set device_id,app_start_ts,app_stop_ts值就可以同时变两个字段值，适配深圳项目。
		 * */
		String[] Jbasev = {"V1.0","V1.4.5","V1.1","ETL","TSP","ETLenv-1","ETLenv-2"
				,"ETLenv-3","ETLtrack-1","ETLtrack-2","ETLtrack-3"};
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
		String[] evnstr145 = {"vin","access","device_id","device_type","device_ver","device_model","t_store_size",
				"c_store_size","t_mem_size","c_mem_size","screen_h","screen_w","screen_size","screen_ratio","screen_orient",
				"imsi","imei","cpu","brand","d_start_ts","d_stop_ts","net_type","carrier","os_ver","os_type","user_id",
				"acc_type","che_name","che_type","app_id","app_ver","app_package","p_code","c_code","a_code","ver_code",
				"contact_type"};
		String[] evnstr100 = {"vin","device_id","device_type","device_ver","device_model","t_store_size","c_store_size",
				"t_mem_size","c_mem_size","screen_h","screen_w","screen_size","screen_ratio","screen_orient","imsi",
				"imei","cpu","brand","d_start_ts","d_stop_ts","d_record_ts","net_type","carrier","os_ver","os_type",
				"user_id","acc_type","che_name","che_type","app_id","app_ver","app_package","p_code","c_code","a_code",
				"ver_code","contact_type","pre_server_ts","factory","country","language","user_name","app_type","keep_alive",
				"contact_type","link_start_ts","link_stop_ts"};
//		gps内字段
		String[] gpsstr145 = {"c_lon","c_lat","c_alt","c_direct","c_ts"};
		String[] gpsstr100 = {"c_lon","c_lat","c_alt","c_direct","c_ts"};
//		page内字段
		String[] pagestr145 = {"page_code","p_into_ts","p_out_ts"};
		String[] pagestr100 = {"page_code","p_into_ts","p_out_ts"};
//		err内字段
		String[] errstr100= {"device_id","app_id","session_id","ip_addr","app_ver","error","err_ts"};
		
//		TSP各种字段：
		String[] actor = {"user_id","user_name","mobile","real_name","user_type","nickname","qq",
				"wechat","weibo","email","sex","birthday","education","nation","id_card","age",
				"marry","profession","Interest","nationality","home_address","stay_address",
				"driver_card","first_date","archive_card","allow_car_type","limit_date"};
		String[] decice = {"vin","device_id","device_type","license_plate_num","trip_card",
				"register_date","give_date","brand_model","engine_card","archive_card","limit_num",
				"factory","brand","car_model","car_series","car_color","car_style","use_property",
				"car_type","os_version","os_type","browser","browser_version","manufacturer","imsi",
				"imei","touch_type","screen_ratio","screen_size","screen_height","screen_width",
				"device_start_time","device_stop_time","channel_id","channel_name","channel_type"};
		String[] envtsp = {"ip_address","curr_altitude","curr_longitude","curr_latitude","curr_timestamp",
				"network_type","carrier","contact_begin_time","contact_end_time","contact_type"};
		String[] evttsp = {"org_id","org_name","app_key","app_id","app_name","app_version",
				"app_package_name","app_start_time","app_stop_time","module_id","module_name",
				"sub_module_id","sub_module_name","func_id","func_name","sub_func_id","sub_func_name",
				"page_id","page_name","event_id","event_name","event_type","event_desc","event_time"};
		
//		Kafka 配置信息
		String topic = "gw_wedata_data_test";
		String server = "kafka1:9092,kafka2:9092,kafka3:9092";
		
		
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
		int indexflag = Util.getExcelType(path+filename);
//		System.out.println("Type is "+indexflag);
		Map<String, String> defaultmap = new HashMap<String, String>();
		Map<String, String> exchangemap = new HashMap<String, String>();
		Map<String, Integer> positionmap = new HashMap<String, Integer>();
		ArrayList<String> sheetlist = new ArrayList<String>();
		
//		主程序入口
//		初始化文件夹
		Util.checkDir(path);
		Util.checkDir(path+casepath);
		Util.cleanDRF(path+casepath);
//		Row对象
		Row row = null;
		FileWriter fw = null;
		BufferedWriter orgibw = null;
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		
		System.out.print("'1' for Normal subject, '2' for ETL test \nPlease Choose Test Subject : ");
		String type = sc.next();
		if(type.trim().equals("2") || type.trim() == "2") {
			System.out.println("######## Processing Starting for ETL mode ########");
		}
		if(!Util.checkBank(path+bankname)) {
			indexflag = 10000;
		}
		switch(indexflag) {
			case 0 :
				System.out.println("!!!!!!!! Base configuration : "+path+filename+" wrong input or file does not exist.Please check. !!!!!!!!");
				System.out.println("######## Processing is closed. ########");
			break;
			case 1 :
//				xls
				fw = new FileWriter(path+casepath+origoutputname);
				orgibw = new BufferedWriter(fw);
				HSSFWorkbook hworkbook = Util.openXlsfile(path+filename);
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
										pagestr100, errstr100,Jbasev,isjumpname,isjumpnamenum,isjumpval,tmpisjumpval,actor,decice,envtsp,evttsp);
								System.out.println();
								System.out.println("######## "+ sheetname + "'s case is finished. ########");
								System.out.println();
								if(type.trim().equals("2") || type.trim() == "2") {
									System.out.println("Data of " + sheetname + " was stored with path : " +  kafkapath);
									System.out.print("Please input the topic your want to send : ");
									topic = sc.next().trim();
									KafkaMethod.kafkaProducerMethod(kafkapath,sheetname,topic,server,flag);
								}
							}
						}
						if(type.trim().equals("2") || type.trim() == "2") {
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
				XSSFWorkbook xworkbook = Util.openXlsxfile(path+filename);
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
										pagestr100,errstr100,Jbasev,isjumpname,isjumpnamenum,isjumpval,tmpisjumpval,actor,decice,envtsp,evttsp);
								System.out.println();
								System.out.println("######## "+ sheetname + "'s case is finished. ########");
								System.out.println();
								if(type.trim().equals("2") || type.trim() == "2") {
//									System.out.println("Press any key to Start sendding "+sheetname+"'s case JSON to kafka.\nType 'Q' to exit and countinue create JSON on next sheet : ");
									System.out.println("Data of " + sheetname + " was stored with path : " +  kafkapath);
									System.out.print("Please input the topic your want to send : ");
									topic = sc.next().trim();
									KafkaMethod.kafkaProducerMethod(kafkapath,sheetname,topic,server,flag);
								}
							}
						}
						if(type.trim().equals("2") || type.trim() == "2") {
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
