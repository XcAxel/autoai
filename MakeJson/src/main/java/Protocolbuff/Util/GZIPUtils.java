package Protocolbuff.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>
 * 时间： 2019/1/124
 * <p>
 * 描述：
 * <p>
 * 作者： 王永浩
 * <p>
 **/
public class GZIPUtils {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {

            System.out.println("gzip compress error.");
        }
        return out.toByteArray();
    }

    public static byte[] compressToGzip(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(data);
            gzip.close();
        } catch (IOException e) {

        	System.out.println("gzip compress error.");
        }
        return out.toByteArray();
    }

    /**
     * GZIP解压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = ungzip.read(buffer, 0, 1024)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
        	System.out.println("gzip uncompress error.");
        }

        return out.toByteArray();
    }

    /**
     * Gzip解压数据
     *
     * @param data
     * @return
     */
    public static byte[] decompressForGzip(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            gzip.close();
            in.close();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
        	System.out.println("gzip uncompress error.");
        }
        return null;
    }

    /**
     * @param bytes
     * @return
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }

    /**
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
        	System.out.println("gzip uncompress to string error.");
        }
        return null;
    }


}
