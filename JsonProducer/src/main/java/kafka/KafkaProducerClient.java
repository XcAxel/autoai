package kafka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import Util.Util;


/**
 * <!-- kafka client -->
    <dependency>
	    <groupId>org.apache.kafka</groupId>
	    <artifactId>kafka-clients</artifactId>
	    <version>1.1.0</version>
	</dependency>
	1，本程序是将生成好的JSON，往Kafka中输入的Producer独立运行程序。
	2，要往Kafka中打的数据存放目录：Sourec\Kafka
	3，运行完后程序在Source下生成device_id_solo.txt,ALLJson_solo.txt,两个文件分别记录往Kafka中打的数据的device_id和JSON串。
	4，选择1-Nomal 打入为ETL非外层Topic，以及ETL后的后端程序的Topic，2-v1，3-v2打入均为ETL外层Topic，打入形式为Protocolbuff压缩后的内容。
 * */

public class KafkaProducerClient {

	public static void main(String[] args) throws Exception {
//		控制是否真实打数据到Kafka的标称
		boolean flag = true;
//		boolean flag = false;
//		kafka配置
		String topic = "test12345";
		String path = "Source/Kafka/";
//		主程序
		int sum = 0;
		Util.FileInitializeKafkaDir(path);
		File file = new File(path);
		List<String> filelist = Util.getFileName(file);
		FileReader fr = null;
		BufferedReader br = null;
//		Kafka配置
		Properties props = new Properties();
//		 props.put("bootstrap.servers", "192.168.147.120:9092,192.168.147.121:9092,192.168.147.122:9092");
		 props.put("bootstrap.servers", "static-node1:9092,static-node2:9092,static-node3:9092");
		 //		 props.put("bootstrap.servers", "LocalHost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 
		 Producer<String, String> producer = new KafkaProducer<>(props);
		 if(flag) {
			 System.out.println("======== Sending data to Kafka server ========");
		 }else {
			 System.out.println("======== Void data to Kafka server ========");
		 }
//		 发送
		 for(int i = 0 ;i < filelist.size(); i++){
			File fileread = new File(path+filelist.get(i));
			System.out.println("Be Reading log =====> " + "“" +filelist.get(i) + "”");
			fr = new FileReader(fileread);
			br = new BufferedReader(fr);
			String s = br.readLine();
			while(s != null){
				if(flag) {
					producer.send(new ProducerRecord<String, String>(topic, s));
				}
				sum += 1;
				s = br.readLine();
//				System.out.println();
			}
		}
		br.close();
		producer.close();
		if(flag) {
			System.out.println("~~~~~~~~ Sending Completion ~~~~~~~~");
			System.out.println("Total : " + sum + " JSONs ware Sended");
			System.out.println();
		 }else {
			System.out.println("~~~~~~~~ Reading Completion ~~~~~~~~");
			System.out.println("Total : " + sum + " JSONs ware Readed");
			System.out.println();
		 }
	}

}
