package JsonTime.JsonTime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import Util.Util;
import domain.User;


/**
 * input:Source/Step1/formatETLJson.txt   Step 1 运行结果文件
 * outputpath:Source/Step2/TimeResult.txt   正确的结果文件 
 * outputpath:Source/Step2/Error.txt    日期有问题的JSON
 * output format for app: device_id App_id App_start_ts duration
 * output format for dev: device_id device_start_ts duration
 * */

public class mainStep2 {

	public static void main(String[] args) throws Exception {
		String inputpath = "Source/Step1/";
		String outputpath = "Source/Step2/";
		String inputfilename = "formatETLJson.txt";
		String inpath = inputpath+inputfilename;		
		String type = Util.welcomeIndex();
		String ch = Util.typeTrans(type);
		String outputResultfilename = "TimeResult"+ch+".txt";
		String outputErrorfilename = "Error"+ch+".txt";
		String outpath = outputpath+outputResultfilename;
		String outpatherr = outputpath+outputErrorfilename;
//		过滤数据使用
		String timeRange = "2019-08-08";
//		5 mins * 3 = 15 mins 补填数据使用
		final int Appdiff = 30000 * 3;
//		5 mins * 6 = 30 mins 补填数据使用
		final int Devdiff = 30000 * 6;
		int linenum = 1;
		Util.checkDir(inputpath);
		Util.checkDir(outputpath);
		Util.checkInputFile(inpath);
		Util.FileInitialize(outpath);
		Util.FileInitialize(outpatherr);
		FileReader fr = new FileReader(inpath);
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter(outpath);
		BufferedWriter bw = new BufferedWriter(fw);
		FileWriter fwerr = new FileWriter(outpatherr);
		BufferedWriter errbw = new BufferedWriter(fwerr);
		Map<String, User> resultmap = new HashMap<String, User>();
		
		switch(Integer.parseInt(type)) {
		case 1:
//			APP使用时长
			String sa = br.readLine();
			while(sa != null) {
				System.out.println("------------- line " + linenum + " Start App Using time Calculating -------------");
				System.out.println();
				String[] str = sa.split(" ");
				String did = str[0];
				String aid = str[1];
				String astarttsstr = str[2];
				long appstartts = 0;
				String astoptsstr = str[3].trim();
				long appstopts = 0;
//				String dstarttsstr = str[4];
//				long dstartts = 0;
//				String dstoptsstr = str[5];
//				long dstopts = 0;
				String gentsstr = str[6];
				long gents = 0;
				String timeLimitstr = str[7];
				long timeLimit = 0;
				String appstartdate = str[8];
				String astoptsdate = str[9];
//				String dstarttsdate = str[10];
//				String dstoptsdate = str[11];
				String gentsdate = str[12];
				if(!appstartdate.contains(timeRange) || !gentsdate.contains(timeRange)) {
					errbw.write("------------- Time Range Error : "+ sa);
					errbw.newLine();
					errbw.flush();
				}else {
					if(astarttsstr != "null" || !astarttsstr.equals("null")) {
						appstartts = Long.parseLong(astarttsstr);
					}
					if(astoptsstr == "null" || astoptsstr.equals("null") || astoptsstr.length() == 4) {
//						System.out.println(astoptsstr+"------"+astoptsstr.length());
						appstopts = 0;
					}else {
						appstopts = Long.parseLong(astoptsstr);						
					}
					if(gentsstr != "null" || !gentsstr.equals("null")) {
						gents = Long.parseLong(gentsstr);
					}
					if(timeLimitstr != "null" || !timeLimitstr.equals("null")) {
						timeLimit = Long.parseLong(timeLimitstr);
					}
					Util.appUseTimeMethod(resultmap, did, aid, appstartts, appstopts, gents, timeLimit
							, appstartdate, Appdiff);
				}
				System.out.println("------------- line " + linenum + " Finish App Using time Calculating -------------");
				System.out.println();
				linenum++;
				sa = br.readLine();
			}
			Set<String> aset = resultmap.keySet();
			System.out.println("Start writting result......");
			for(String tmp : aset) {
				bw.write(resultmap.get(tmp).ApptoString());
				bw.newLine();
				bw.flush();
			}
			System.out.println("Finish writting result......");
			System.out.println("Total finish App calculating "+ linenum+ " Datas");
			resultmap.clear();
			bw.close();
			errbw.close();
			fw.close();
			fwerr.close();
		break;
		case 2:
//			车载端使用时长
			String sd = br.readLine();
			while(sd != null) {
				System.out.println("------------- line " + linenum + " Start Device Using time Calculating -------------");
				System.out.println();
				String[] str = sd.split(" ");
				String did = str[0];
				String aid = str[1];
				String dstarttsstr = str[4];
				long dstartts = 0;
				String dstoptsstr = str[5];
				long dstopts = 0;
				String gentsstr = str[6];
				long gents = 0;
				String timeLimitstr = str[7];
				long timeLimit = 0;
				String appstartdate = str[8];
				String dstarttsdate = str[10];
				String gentsdate = str[12];
				if(!dstarttsdate.contains(timeRange) || !gentsdate.contains(timeRange)) {
					errbw.write("------------- Time Range Error : "+ sd);
					errbw.newLine();
					errbw.flush();
				}else {
					if(dstarttsstr != "null" || !dstarttsstr.equals("null")) {
						dstartts = Long.parseLong(dstarttsstr);
					}
					if(dstoptsstr == "null" || dstoptsstr.equals("null") || dstoptsstr.length() == 4) {
						dstopts = 0;
					}else {						
						dstopts = Long.parseLong(dstoptsstr);
					}
					if(gentsstr != "null" || !gentsstr.equals("null")) {
						gents = Long.parseLong(gentsstr);
					}
					if(timeLimitstr != "null" || !timeLimitstr.equals("null")) {
						timeLimit = Long.parseLong(timeLimitstr);
					}
					Util.devUseTimeMethod(resultmap, did, aid, dstartts, dstopts, gents, timeLimit
							, dstarttsdate, appstartdate, Devdiff);;
				}
				System.out.println("------------- line " + linenum + " Finish Device Using time Calculating -------------");
				System.out.println();
				linenum++;
				sd = br.readLine();
			}
			Set<String> dset = resultmap.keySet();
			System.out.println("Start writting result......");
			for(String tmp : dset) {
//				System.out.println(tmp);
				bw.write(resultmap.get(tmp).DevicetoString());
				bw.newLine();
				bw.flush();
			}
			System.out.println("Finish writting result......");
			System.out.println("Total finish Device calculating "+ linenum+ " Datas");
			resultmap.clear();
			bw.close();
			errbw.close();
			fw.close();
			fwerr.close();
		break;
		}
		
		System.out.println("Total finish "+ linenum+ " JSONs");

	}

}
