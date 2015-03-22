package mtss.PassiveReader.sdk;

//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 字符串，字节数�?�之间转�?�的帮助类
 */
public class Utility {
    private static final Logger LOG = Logger.getLogger(Utility.class.getName());

    public static String timeFormatString = "yyyy/MM/dd HH:mm:ss";
    public static SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatString);
    /**
     * @param hexString
     * @return
     */
    public static byte[] convert2HexArray(String hexString) {
        int len = hexString.length() / 2;
        char[] chars = hexString.toCharArray();
        String[] hexes = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i = i + 2, j++) {
            hexes[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexes[j], 16);
        }

        return bytes;
    }

    /**
     * @param b 
     * @param count convert number
     * @return
     */
    public static String bytes2HexString(byte[] b, int count) {
        String ret = "";
        for (int i = 0; i < count; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static byte BYTE(int i) {
        return (byte) i;
    }

    /**
     * check whether the str is a hex str
     *
     * @param str str
     * @param bits bits
     * @return true or false
     */
    public static boolean isHexString(String str, int bits) {
        String patten = "[abcdefABCDEF0123456789]{" + bits + "}";
        if (str.matches(patten)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isHexString(String str) {
        String patten = "[abcdefABCDEF0123456789]{1,}";
        if (str.matches(patten)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(String str) {
        String patten = "[-]{0,1}[0123456789]{0,}";
        return str.matches(patten);
    }
    
    public static boolean isValiadTimeString(String dateString) {
        timeFormat.setLenient(false);
        try {
            timeFormat.parse(dateString);
        } catch (Exception ex) {
            /*Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);*/
            LOG.info(dateString +" bad format");
            return false;
        }
        LOG.info(dateString);
        return true;
    }
    
    public static void main(String[]args) {
        //SimpleDateFormat format = new SimpleDateFormat(dateFormatString);
        timeFormat.setLenient(false);
        try {
            Date date = timeFormat.parse("2012/11/0a 12:34:07");
            //format = new SimpleDateFormat(dateFormatString, Locale.getDefault())
            System.err.println(date.toLocaleString());
        } catch (Exception ex) {
           /* Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);*/
        LOG.info(ex +" bad time format");
        }
        
        System.err.println(isValiadTimeString("1984/10/13 19:45:40"));
    }
}
