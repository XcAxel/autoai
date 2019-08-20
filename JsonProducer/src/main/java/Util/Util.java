package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Util {
	
//	格式化时间
	public static String getFormattime(long time) throws Exception{
		Date date = new Date(time);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tmp = sdf.format(date).replace(" ", "T");
//		System.out.println("MSec = "+tmp); 
		return tmp;
	}
	
//	检查文件夹
	public static void checkDir(String dirpath) {
		File dir = new File(dirpath);
		if(!dir.exists()) {
			dir.mkdir();
		}
	}
	
//	检查输入文件
	public static void checkInputFile(String filepath) throws Exception {
		File f = new File(filepath);
		if(!f.exists()) {
			f.createNewFile();
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
	
//	获取毫秒
	public static String getMtime(String time) throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.parse(time);
		String tmp = date.getTime() + "";
		return tmp;
	}
	
//	交互
	public static String welcomeIndex() {
		String type = "";
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		boolean flag = true;
		while(flag) {
			System.out.println("APP using time please choose ‘1’，Device using time please choose ‘2’ ");
			System.out.print("Please input type : ");
			type = sc.next().trim();
			if(type == "1" || type == "2" || type.equals("1") || type.equals("2")) {
				flag = false;
			}else {
				System.out.println("!!!!!!!! Input error,Please input '1' or '2' !!!!!!!!");
			}
		}
		sc.close();
		return type;
	}
	
//	type转中文
	public static String typeTrans(String type) {
		String ch = "";
		if(type == "1" || type.equals("1")) {
			ch = "App";
		}else {
			ch = "Device";
		}
		return ch;
	}
	
//	读一行
	public static String readLine(String path) throws Exception {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();
		br.close();
		fr.close();
		return s;
	}
	
//	随机数
	public static int randomMethod(Random random,int randomrange) {
		return random.nextInt(randomrange);
	}
//	判断是否有值
	public static String judgeStr(String str) {
		String tmp = "null";
//		System.out.println(str);
		if(!str.equals("null") || !(str == "null")) {
			tmp = str;
		}
		return tmp;
	}
//	Kafka文件夹初始化
	public static void FileInitializeKafkaDir(String path) throws Exception{
		File f = new File(path);
		if(!f.exists()){
			f.mkdir();
			System.out.println("!!!!!!!! Kafka Folder does not exists !!!!!!!!");
		}
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
}
