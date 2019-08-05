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
import com.alibaba.fastjson.JSONObject;
import Domian.PBResultBean;
import Domian.Protov1;
import Domian.Protov2;
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
		String topic = "gw_wedata_data_test";
		String path = "Source/Kafka/";
		String outputPath = "Source/device_id_solo.txt";
		String allJoutputPath = "Source/AllJson_solo.txt";
//		主程序
		int sum = 0;
		Util.FileInitializeKafkaDir(path);
		Util.FileInitialize(outputPath);
		File file = new File(path);
		List<String> filelist = Util.getFileName(file);
		FileReader fr = null;
		BufferedReader br = null;
//		选择
//		String select = Util.selectETLInput();		
//		记录device_id.
		FileWriter fw = new FileWriter(outputPath);
		BufferedWriter bw = new BufferedWriter(fw);
		String device_id = "";
//		记录JSON串.
		FileWriter fw2 = new FileWriter(allJoutputPath);
		BufferedWriter bw2 = new BufferedWriter(fw2);
		String json = "";
		String jver = "";
//		Kafka配置
		Properties props = new Properties();
//		 props.put("bootstrap.servers", "192.168.147.120:9092,192.168.147.121:9092,192.168.147.122:9092");
		 props.put("bootstrap.servers", "kafka1:9092,kafka2:9092,kafka3:9092");
		 //		 props.put("bootstrap.servers", "LocalHost:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 
		 Protov1 pv1 = new Protov1();
		 Protov2 pv2 = new Protov2();
		 PBResultBean pbrb = new PBResultBean();
		 byte[] bytes = {};
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
				if(s.contains("=")){
					String[] tmp = s.split("=");
					if(tmp.length == 3) {
						device_id = tmp[0];
						json = tmp[1];
						jver = tmp[2];
						bw.write(device_id);
						bw.flush();
						bw.newLine();
						bw2.write(json + "=" + jver);
						bw2.flush();
						bw2.newLine();
					}else {
						json = tmp[0];
						jver = tmp[1];
						net.sf.json.JSONObject js = net.sf.json.JSONObject.fromObject(json);
						net.sf.json.JSONObject jstmp = (net.sf.json.JSONObject) js.get("env");
						device_id = jstmp.get("device_id").toString();
						bw.write(device_id);
						bw.flush();
						bw.newLine();
						bw2.write(json + "=" + jver);
						bw2.flush();
						bw2.newLine();
					}
				}else {
					
				}
//				System.out.println("print current Json : --> " + json);
				if(flag) {
//					switch(select) {
					switch(jver) {
					case "V1.4.5":
//					case "TSPP":
//						For ProtocolBuff V1 Json
//						System.out.println(json);
						bytes = pv1.genPbV1Data(json);
						pbrb.setData(bytes);
						pbrb.setParams("1.0");
						json = JSONObject.toJSONString(pbrb);
						producer.send(new ProducerRecord<String, String>(topic, json));
					break;
					case "V2.4.5":
//						For ProtocolBuff V2 Json
						bytes = pv2.genPbV1Data(json);
						pbrb.setData(bytes);
						pbrb.setParams("2.0");
						json = JSONObject.toJSONString(pbrb);
						producer.send(new ProducerRecord<String, String>(topic, json));
					break;
					default:
						producer.send(new ProducerRecord<String, String>(topic, device_id, json));
					break;
					}
				}
				sum += 1;
				s = br.readLine();
//				System.out.println();
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
