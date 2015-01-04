/*jadclipse*/// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   Utility.java

package sdk;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utility
{

    public Utility()
    {
    }

    public static byte[] convert2HexArray(String hexString)
    {
        int len = hexString.length() / 2;
        char chars[] = hexString.toCharArray();
        String hexes[] = new String[len];
        byte bytes[] = new byte[len];
        int i = 0;
        for(int j = 0; j < len; j++)
        {
            hexes[j] = (new StringBuilder()).append("").append(chars[i]).append(chars[i + 1]).toString();
            bytes[j] = (byte)Integer.parseInt(hexes[j], 16);
            i += 2;
        }

        return bytes;
    }

    public static String bytes2HexString(byte b[], int count)
    {
        String ret = "";
        for(int i = 0; i < count; i++)
        {
            String hex = Integer.toHexString(b[i] & 255);
            if(hex.length() == 1)
                hex = (new StringBuilder()).append('0').append(hex).toString();
            ret = (new StringBuilder()).append(ret).append(hex.toUpperCase()).toString();
        }

        return ret;
    }

    public static String bytes2HexString(byte b[])
    {
        String ret = "";
        for(int i = 0; i < b.length; i++)
        {
            String hex = Integer.toHexString(b[i] & 255);
            if(hex.length() == 1)
                hex = (new StringBuilder()).append('0').append(hex).toString();
            ret = (new StringBuilder()).append(ret).append(hex.toUpperCase()).toString();
        }

        return ret;
    }

    public static byte BYTE(int i)
    {
        return (byte)i;
    }

    public static boolean isHexString(String str, int bits)
    {
        String patten = (new StringBuilder()).append("[abcdefABCDEF0123456789]{").append(bits).append("}").toString();
        return str.matches(patten);
    }

    public static boolean isHexString(String str)
    {
        String patten = "[abcdefABCDEF0123456789]{1,}";
        return str.matches(patten);
    }

    public static boolean isNumber(String str)
    {
        String patten = "[-]{0,1}[0123456789]{0,}";
        return str.matches(patten);
    }

    public static boolean isValiadTimeString(String dateString)
    {
        timeFormat.setLenient(false);
        try
        {
            timeFormat.parse(dateString);
        }
        catch(Exception ex)
        {
            Logger.getLogger("sdk.Utility").log(Level.SEVERE, null, ex);
            LOG.info((new StringBuilder()).append(dateString).append(" bad format").toString());
            return false;
        }
        LOG.info(dateString);
        return true;
    }

    public static void main(String args[])
    {
        timeFormat.setLenient(false);
        try
        {
            Date date = timeFormat.parse("2012/11/0a 12:34:07");
            System.err.println(date.toLocaleString());
        }
        catch(Exception ex)
        {
            Logger.getLogger("sdk.Utility").log(Level.SEVERE, null, ex);
        }
        System.err.println(isValiadTimeString("1984/10/13 19:45:40"));
    }

    private static final Logger LOG = Logger.getLogger("sdk.Utility");
    public static String timeFormatString;
    public static SimpleDateFormat timeFormat;

    static 
    {
        timeFormatString = "yyyy/MM/dd HH:mm:ss";
        timeFormat = new SimpleDateFormat(timeFormatString);
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: F:\workspaces\workspace teaching core java\ReaderAPI\ImportedClasses/sdk/Utility.class
	Total time: 44 ms
	Jad reported messages/errors:
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/