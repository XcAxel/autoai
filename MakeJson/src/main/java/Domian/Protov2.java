package Domian;

import com.autoai.wedata.kittx.uds.log.bean.pbv2.LogDataBean;
import com.autoai.wedata.kittx.uds.log.bean.pbv2.OriginDataBean;
import com.google.protobuf.ByteString;
import com.googlecode.protobuf.format.JsonFormat;
import Protocolbuff.Util.GZIPUtils;
import Protocolbuff.Util.MD5Util;
import Util.Util;
import org.javatuples.Pair;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Protov2 {
//    @Test
//    public void test() throws IOException {
//        Protov2 test = new Protov2();
//        byte[] bytes = test.genPbV1Data(jsonStr);
////        FileUtils.writeByteArrayToFile(new File("E:\\sdkbin\\gen\\mygenv1.0.bin"), bytes);
//
//    }

    public byte[] genPbV1Data(String json) throws Exception {
        JsonFormat jf = new JsonFormat();
        OriginDataBean.OriginData.Builder builder = OriginDataBean.OriginData.newBuilder();
        jf.merge(json, builder);
        OriginDataBean.OriginData build = builder.build();
        byte[] byteString = build.toByteArray();
        byte[] compressedBytes = GZIPUtils.compressToGzip(byteString);
        List<ByteString> origList = new ArrayList<ByteString>();
        origList.add(ByteString.copyFrom(compressedBytes));
        Pair<String, String> md5Info = getMd5String(origList);

        LogDataBean.LogData.Builder logDataBuilder = LogDataBean.LogData.newBuilder();
        logDataBuilder.setAppkey("SWZLHZ0D9000000000000000000A0120");
        logDataBuilder.setDataLength(md5Info.getValue0());
        logDataBuilder.addAllOriginData(origList);
        logDataBuilder.setSignature(md5Info.getValue1());
        logDataBuilder.setVersion("v2.0");
        logDataBuilder.setTs(Util.getMtime()+"");

        return logDataBuilder.build().toByteArray();
    }

    private Pair<String, String> getMd5String(List<ByteString> data) {
        int dataLength = 0;
        for (ByteString b : data) {
            dataLength += b.toByteArray().length;
        }
        ByteBuffer buff = ByteBuffer.allocate(dataLength + "UDS_Log".length());
        for (ByteString b : data) {
            buff.put(b.toByteArray());
        }
        buff.put("UDS_Log".getBytes());
        String md5String = MD5Util.getMD5String(buff.array());
        return Pair.with(Integer.toString(dataLength), md5String);
    }

//    private String jsonStr = "{\"ext\":\"\",\"data_id\":\"867215041781051#110B8F5A-26B6-D2ED-F2E3-D581B0F6E809#e81eee5b-edc1-475a-8ab2-01ebf3f7e739#da8c1bbe-ecc4-464f-837d-1266d5d836af\",\"gen_ts\":\"1560497422759\",\"env\":{\"acc_type\":\"-1\",\"access\":\"f795a8a33a7a4cd0a87c88e7b6264fff\",\"device_model\":\"1\",\"t_mem_size\":\"1880\",\"app_package\":\"com.autoai.mixer\",\"device_type\":\"2\",\"screen_orient\":\"90\",\"imsi\":\"460065127028071\",\"c_code\":\"\",\"contact_type\":\"\",\"screen_w\":\"1024\",\"p_code\":\"\",\"ver_code\":\"1\",\"screen_size\":\"8.059777\",\"t_store_size\":\"10884\",\"vin\":\"867215041781051\",\"app_id\":\"A0126\",\"brand\":\"alps\",\"che_type\":\"1\",\"screen_h\":\"600\",\"os_ver\":\"23\",\"a_code\":\"\",\"device_id\":\"110B8F5A-26B6-D2ED-F2E3-D581B0F6E809\",\"d_start_ts\":\"1560497303730\",\"net_type\":\"2\",\"cpu\":\"arm64-v8a\",\"che_name\":\"default\",\"device_ver\":\"1.0\",\"carrier\":\"2\",\"c_mem_size\":\"952\",\"user_id\":\"e81eee5b-edc1-475a-8ab2-01ebf3f7e739\",\"app_ver\":\"1.0\",\"os_type\":\"1\",\"imei\":\"867215041781051\",\"d_stop_ts\":\"\",\"c_store_size\":\"6044\",\"screen_ratio\":\"600*1024\"},\"track\":[{\"evt\":[{\"evt_beg_ts\":\"1560497099320\",\"f_end_ts\":\"1560497302371\",\"m_beg_ts\":\"1560497099316\",\"content_tag\":\"\",\"p_beg_ts\":\"1560497099319\",\"func_id\":\"F0001\",\"evt_detail\":[],\"evt_end_ts\":\"1560497099323\",\"f_beg_ts\":\"1560497099317\",\"page_id\":\"P0012\",\"m_end_ts\":\"1560497099317\",\"module_id\":\"M0001\",\"touch_type\":\"2\",\"p_end_ts\":\"1560497099320\",\"evt_id\":\"E0035\"},{\"evt_beg_ts\":\"1560497099323\",\"f_end_ts\":\"1560497302371\",\"m_beg_ts\":\"1560497099316\",\"content_tag\":\"\",\"p_beg_ts\":\"1560497099319\",\"func_id\":\"F0001\",\"evt_detail\":[],\"evt_end_ts\":\"1560497099325\",\"f_beg_ts\":\"1560497099317\",\"page_id\":\"P0012\",\"m_end_ts\":\"1560497099317\",\"module_id\":\"M0001\",\"touch_type\":\"2\",\"p_end_ts\":\"1560497099320\",\"evt_id\":\"E0030\"},{\"evt_beg_ts\":\"1560497099325\",\"f_end_ts\":\"1560497302371\",\"m_beg_ts\":\"1560497099316\",\"content_tag\":\"\",\"p_beg_ts\":\"1560497099319\",\"func_id\":\"F0001\",\"evt_detail\":[],\"evt_end_ts\":\"1560497302380\",\"f_beg_ts\":\"1560497099317\",\"page_id\":\"P0012\",\"m_end_ts\":\"1560497099317\",\"module_id\":\"M0001\",\"touch_type\":\"2\",\"p_end_ts\":\"1560497099320\",\"evt_id\":\"E0030\"},{\"evt_beg_ts\":\"1560497302381\",\"f_end_ts\":\"1560497324601\",\"m_beg_ts\":\"1560497302371\",\"content_tag\":\"\",\"p_beg_ts\":\"1560497302380\",\"func_id\":\"F0001\",\"evt_detail\":[],\"evt_end_ts\":\"1560497321280\",\"f_beg_ts\":\"1560497302372\",\"page_id\":\"P0001\",\"m_end_ts\":\"1560497302372\",\"module_id\":\"M0001\",\"touch_type\":\"2\",\"p_end_ts\":\"1560497302381\",\"evt_id\":\"E0001\"},{\"evt_beg_ts\":\"1560497321280\",\"f_end_ts\":\"1560497324601\",\"m_beg_ts\":\"1560497302371\",\"content_tag\":\"\",\"p_beg_ts\":\"1560497302380\",\"func_id\":\"F0001\",\"evt_detail\":[],\"evt_end_ts\":\"1560497322811\",\"f_beg_ts\":\"1560497302372\",\"page_id\":\"P0001\",\"m_end_ts\":\"1560497302372\",\"module_id\":\"M0001\",\"touch_type\":\"2\",\"p_end_ts\":\"1560497302381\",\"evt_id\":\"E0001\"}],\"app_stop_ts\":\"1560497602271\",\"is_update\":\"0\",\"is_first\":\"0\",\"session_id\":\"1560498234952\",\"first_use\":\"0\",\"app_start_ts\":\"1560497302271\",\"gps\":[{\"c_direct\":\"0.0\",\"c_lon\":\"113.88109166666666\",\"c_alt\":\"56.6\",\"c_ts\":\"1560497123000\",\"c_lat\":\"22.54937333333333\"},{\"c_direct\":\"0.0\",\"c_lon\":\"113.88100833333334\",\"c_alt\":\"78.4\",\"c_ts\":\"1560497183000\",\"c_lat\":\"22.549413333333337\"},{\"c_direct\":\"0.0\",\"c_lon\":\"113.88110166666665\",\"c_alt\":\"101.6\",\"c_ts\":\"1560497303000\",\"c_lat\":\"22.549298333333333\"},{\"c_direct\":\"0.0\",\"c_lon\":\"113.88110166666665\",\"c_alt\":\"101.6\",\"c_ts\":\"1560497363000\",\"c_lat\":\"22.549298333333333\"}],\"page\":[{\"p_into_ts\":\"1560497099319\",\"page_code\":\"P0012\",\"p_out_ts\":\"1560497099320\"},{\"p_into_ts\":\"1560497099319\",\"page_code\":\"P0012\",\"p_out_ts\":\"1560497099320\"},{\"p_into_ts\":\"1560497099319\",\"page_code\":\"P0012\",\"p_out_ts\":\"1560497099320\"},{\"p_into_ts\":\"1560497302380\",\"page_code\":\"P0001\",\"p_out_ts\":\"1560497302381\"},{\"p_into_ts\":\"1560497302380\",\"page_code\":\"P0001\",\"p_out_ts\":\"1560497302381\"}],\"first_day\":\"0\"}]}";
}
