package Protocolbuff.Util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString().toLowerCase();
    }
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static String get32MD5(String src) {
        if (isEmpty(src)) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(src.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
//            if (LogManager.isLoggable()) {
//                LogManager.e("TAG", "", e);
//            }
        }
        return "";
    }

    public static String get16MD5(String src) {
        String md5 = get32MD5(src);
        if (isEmpty(md5) || md5.length() != 32) {
            return md5;
        }
        return md5.substring(8, 24);
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    public static String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[3 * 1024 * 1024];
        int numRead;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
