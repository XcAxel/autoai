package JsonProducer.JsonProducer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import Util.Util;

/**
 * 	大批量造串，随机创造指定总条数以及正确条数的数据包，目前正确的条数是2种。
 * 	Kafka标称开关，代表造完直接打数据只kafka集群中，关闭则只落盘，然后可以通过手动打kafka程序
 * 	再次将数据打入磁盘中。
 * 	输入：Source/rightIOS.txt，rightAndroid.txt，wrong.txt
 * 	输出：Source/Result.txt
 * 	手动kafka文件夹：Source/Kafka/
 *  static-node1:9092,static-node2:9092,static-node3:9092    
 *  Topic:test12345
 * 
 * */

public class main {

	public static void main(String[] args) throws Exception {
//		Kafka发送标称
		boolean flag = true;
//		boolean flag = false;
		String topic = "ods-lcp-ulogparsed-gw";
		String server = "xdatanode-01:9092,xdatanode-02:9092,xdatanode-03:9092";
//		总条数，总正确条数
		long totalnum = 2000000l;
		long totalrightnum = 500000l;
//		long totalnum = 30l;
//		long totalrightnum = 10l;
//		计数初始化
		long writerightIOSnum = 0;
		long writerightAndroidnum = 0;
		long writerightnum = 0;
		int randomrange = 10;
//		int randomjudgenum = 4;
//		输入输出
		String inputpath = "Source/";
		String rightIOSfile = "rightIOS.txt";
		String rightAndroidfile = "rightAndroid.txt";
		String wrongfile = "wrong.txt";
		String resultfile = "Result.txt";
		String rightIOSpath = inputpath+rightIOSfile;
		String rightAndroidpath = inputpath+rightAndroidfile;
		String wrongpath = inputpath+wrongfile;
		String resultpath = inputpath+resultfile;
		Util.checkDir(inputpath);
		Util.checkInputFile(rightIOSpath);
		Util.checkInputFile(rightAndroidpath);
		Util.checkInputFile(wrongpath);
		Util.FileInitialize(resultpath);
		String rightIOSjson = Util.judgeStr(Util.readLine(rightIOSpath)+"");
		String rightAndroidjson = Util.judgeStr(Util.readLine(rightAndroidpath)+"");
		String wrongjson = Util.judgeStr(Util.readLine(wrongpath)+"");
//		String rightIOSjson = "IOS";
//		String rightAndroidjson = "Andorid";
//		String wrongjson = "Wrong";
//		Kafka配置
		Properties props = new Properties();
//		 props.put("bootstrap.servers", "192.168.147.120:9092,192.168.147.121:9092,192.168.147.122:9092");
		 props.put("bootstrap.servers", server);
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 Producer<String, String> producer = new KafkaProducer<>(props);
		FileWriter fw = new FileWriter(resultpath);
		BufferedWriter bw = new BufferedWriter(fw);
		Random random = new Random();
		for(long startnum = 0;startnum < totalnum;startnum++) {
			if(flag) {
				System.out.println("Start Sendding "+(startnum+1)+" line");				
			}else {
				System.out.println("Start Writting "+(startnum+1)+" line");
			}
			int rnum = Util.randomMethod(random, randomrange);
//			System.out.println("Random number is "+rnum);
			if(writerightnum < totalrightnum && totalnum-startnum > totalrightnum) {
				if(rnum == 4 || rnum == 8) {
//					IOS right
//					System.out.println("IOS in");
					bw.write(rightIOSjson);
					bw.newLine();
					bw.flush();
					writerightIOSnum++;
					writerightnum = writerightIOSnum + writerightAndroidnum;
					if(flag) {
						producer.send(new ProducerRecord<String, String>(topic, rightIOSjson));
					}
//					System.out.println("Write number is "+writerightnum);
				}else if(rnum == 1 || rnum == 5) {
//					Android right
//					System.out.println("Android in");
					bw.write(rightAndroidjson);
					bw.newLine();
					bw.flush();
					writerightAndroidnum++;
					writerightnum = writerightIOSnum + writerightAndroidnum;
//					System.out.println("Write number is "+writerightnum);
					if(flag) {
						producer.send(new ProducerRecord<String, String>(topic, rightAndroidjson));
					}
				}else {
//					System.out.println("Else in");
					bw.write(wrongjson);
					bw.newLine();
					bw.flush();
					if(flag) {
						producer.send(new ProducerRecord<String, String>(topic, wrongjson));
					}
				}
			}else if(writerightnum < totalrightnum && totalnum-startnum <= totalrightnum) {
//				System.out.println("Rest in");
				bw.write(rightIOSjson);
				bw.newLine();
//				bw.write(rightAndroidjson);
//				bw.newLine();
				bw.flush();
				writerightIOSnum++;
//				writerightAndroidnum++;
				writerightnum = writerightIOSnum + writerightAndroidnum;
//				System.out.println("Write number is "+writerightnum);
				if(flag) {
					producer.send(new ProducerRecord<String, String>(topic, rightIOSjson));
				}
			}else {
//				System.out.println("Normal in");
				bw.write(wrongjson);
				bw.newLine();
				bw.flush();
				if(flag) {
					producer.send(new ProducerRecord<String, String>(topic, wrongjson));
				}
			}
		}
		bw.close();
		fw.close();
		producer.close();
		if(flag) {
			System.out.println("Sendding Complete");
			System.out.println("Total Send : "+ totalnum + " JSONs" );
			System.out.println("Right Json num is " + totalrightnum);
			System.out.println("Right IOS Json num is " + writerightIOSnum);
			System.out.println("Right Android Json num is " + writerightAndroidnum);
		}else {
			System.out.println("Creatting Complete");
			System.out.println("Total write : "+ totalnum + " JSONs" );
			System.out.println("Right Json num is " + totalrightnum);
			System.out.println("Right IOS Json num is " + writerightIOSnum);
			System.out.println("Right Android Json num is " + writerightAndroidnum);
		}
	}

}
