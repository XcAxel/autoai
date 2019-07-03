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
 * 
 * KafkaMethod程序说明
 * 1，此方法用户测试ETL。
 * 2，MakeJSON程序运行完毕后，按照每个Sheet也生成的JSON串进行往Kafka中打。
 * 3，程序会在Source文件目录下生成device_id_sheetname.txt,ALLJson_sheetname.txt,两个文件分别记录往Kafka中打的数据的device_id和JSON串。
 * 4，后续完善protocolBuff。
 * 
 * */

public class KafkaMethod {
	public static void kafkaProducerMethod(String path,String sheetname,String topic,String server,boolean flag) throws Exception {
		String outputPath = "Source/device_id_"+sheetname+".txt";
		String allJoutputPath = "Source/AllJson_"+sheetname+".txt";
		int sum = 0;
		Util.FileInitializeKafkaDir(path);
		Util.FileInitialize(outputPath);
		File file = new File(path);
		List<String> filelist = Util.getFileName(file);
		FileReader fr = null;
		BufferedReader br = null;
//		记录device_id.
		FileWriter fw = new FileWriter(outputPath);
		BufferedWriter bw = new BufferedWriter(fw);
//		记录JSON串.
		FileWriter fw2 = new FileWriter(allJoutputPath);
		BufferedWriter bw2 = new BufferedWriter(fw2);
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
				String[] tmp = s.split("=");
				String device_id = tmp[0];
				String json = tmp[1];
				bw.write(device_id);
				bw.flush();
				bw.newLine();
				bw2.write(json);
				bw2.flush();
				bw2.newLine();
//				System.out.println("print current Json : --> " + json);
				if(flag) {
					producer.send(new ProducerRecord<String, String>(topic, device_id, json));					
				}
				sum += 1;
				s = br.readLine();
			}
		}
	 	bw.close();
	 	bw2.close();
	 	fw.close();
	 	fw2.close();
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
