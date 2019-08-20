package JsonIterator.JsonIterator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import net.sf.json.JSONObject;
import JsonIterator.Util.*;

/**
 * JSON格式化输出程序
 *1，原始JSON串复制到Source/origJson.txt中保存。
 *2，运行程序。
 *3，程序输出全部字段到Source/formatJsonAllKV.txt
 *4，程序输出时间字段到Source/formatJsonTs.txt
 * */

public class main {

	public static void main(String[] args) throws Exception {
		String inputpath = "Source/";
		String inputfilename = "origJson.txt";
		String inpath = inputpath+inputfilename;
		String outputAllfilename = "formatJsonAllKV.txt";
		String outputTsfilename = "formatJsonTs.txt";
		String outputAllfileinlinename = "formatJsonAllline.txt";
		String outpathall = inputpath+outputAllfilename;
		String outpathts = inputpath+outputTsfilename;
		String outpathline = inputpath+outputAllfileinlinename;
		int linenum = 1;
		Util.checkDir(inputpath);
		Util.checkInputFile(inpath);
		Util.FileInitialize(outpathall);
		Util.FileInitialize(outpathts);
		Util.FileInitialize(outpathline);
		FileReader fr = new FileReader(inpath);
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw1 = new FileWriter(outpathall);
		BufferedWriter allbw = new BufferedWriter(fw1);
		FileWriter fw2 = new FileWriter(outpathts);
		BufferedWriter tsbw = new BufferedWriter(fw2);
		FileWriter fw3 = new FileWriter(outpathline);
		BufferedWriter linebw = new BufferedWriter(fw3);
		String s = br.readLine();
		while(s != null) {
			System.out.println("------------- line " + linenum + " Startting format -------------");
			System.out.println();
			allbw.write("------------- line " + linenum + " Start -------------");
			tsbw.write("------------- line " + linenum + " Start -------------");
			allbw.newLine();
			tsbw.newLine();
			JSONObject jsonObject = JSONObject.fromObject(s);
			Util.keyIterator(jsonObject,allbw,tsbw,linebw);
			allbw.write("------------- line " + linenum + " End -------------");
			tsbw.write("------------- line " + linenum + " End -------------");
			allbw.newLine();
			tsbw.newLine();
			linebw.newLine();;
			linebw.flush();
			System.out.println("------------- line " + linenum + " Endding format -------------");
			System.out.println();
			linenum++;
			s = br.readLine();
		}
		allbw.close();
		tsbw.close();
		linebw.close();
		fw1.close();
		fw2.close();
		fw3.close();
		System.out.println("Total format "+ linenum+ " JSONs");
	}
}
