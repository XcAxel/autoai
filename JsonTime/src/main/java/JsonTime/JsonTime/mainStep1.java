package JsonTime.JsonTime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import Util.Util;
import net.sf.json.JSONObject;

/**
 * input:Source/ETLJson.txt  此文件JSON为ETL清洗后结果JSON
 * outputpath:Source/Step1/formatETLJson.txt
 * output format:device_id app_id app_start_ts app_stop_ts device_start_ts device_stop_ts gen_ts
 *  timeLimit app_start_ts可读时间 app_stop_ts可读时间 device_start_ts可读时间 device_stop_ts可读时间 
 *  gen_ts可读时间
 * */

public class mainStep1 {
	public static void main(String[] args) throws Exception {
		String inputpath = "Source/";
		String outputhpath = "Source/Step1/";
		String inputfilename = "ETLJson.txt";
		String inpath = inputpath+inputfilename;
		String outputfilename = "formatETLJson.txt";
		String outpath = outputhpath+outputfilename;
//		判断是否为当天数据。
		String timeRange = "2019-08-08";
		String timeLimit = Util.getMtime(timeRange+" 23:59:59");
		int linenum = 1;
		Util.checkDir(inputpath);
		Util.checkInputFile(inpath);
		Util.FileInitialize(outpath);
		FileReader fr = new FileReader(inpath);
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = new FileWriter(outpath);
		BufferedWriter bw = new BufferedWriter(fw);
		String s = br.readLine();
		while(s != null) {
			System.out.println("------------- line " + linenum + " Startting format -------------");
			System.out.println();
			JSONObject jsonObject = JSONObject.fromObject(s);
			String tmp = Util.getValue(jsonObject,timeLimit);
			bw.write(tmp);
			bw.newLine();
			System.out.println("------------- line " + linenum + " Endding format -------------");
			System.out.println();
			linenum++;
			s = br.readLine();
		}
		bw.close();
		fw.close();
		System.out.println("Total format "+ linenum+ " JSONs");
		System.out.println("Step 1 was finished");

	}

}
