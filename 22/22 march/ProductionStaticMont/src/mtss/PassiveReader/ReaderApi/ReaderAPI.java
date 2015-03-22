package mtss.PassiveReader.ReaderApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import org.apache.log4j.Logger;

import com.mtss.logs.LogsMtss;

import mtss.PassiveReader.ReaderApi.sdk.SerialConnection;
import mtss.PassiveReader.ReaderApi.sdk.SerialParameters;
import mtss.PassiveReader.ReaderApi.sdk.Utility;


public class ReaderAPI
{
	 /* Get actual class name to be printed on */
	  static Logger log = LogsMtss.getLogger(ReaderAPI.class.getName());

    public class MyTask extends TimerTask
    {
        public void run()
        {
            log.debug("\u5F00\u59CB\u8FD0\u884C");
        }

        final ReaderAPI this$0;

        public MyTask()
        {
            super();
            this$0 = ReaderAPI.this;
        }
    }


    public ReaderAPI()
    {
    }

    public static void main(String args[])
    {
       /* int hScanner[] = new int[1];
        writeLog("", " Starting... ");
        int iRet = Net_ConnectScanner(hScanner, "192.168.0.103", 1969, "192.168.0.71", 5555);
        if(0 == iRet)
        {
            iRet = Net_AutoMode(hScanner[0], 0);
            short HandVer[] = new short[1];
            short SoftVer[] = new short[1];
            iRet = Net_GetReaderVersion(hScanner[0], HandVer, SoftVer);
            byte ReaderID[] = {
                65, 66, 67, 68, 69, 70, 71, 72, 73, 80
            };
            iRet = Net_SetReaderID(hScanner[0], ReaderID);
            iRet = Net_GetReaderID(hScanner[0], ReaderID);
            iRet = _OK;
        }
        running = true;
        startRun();
        Scanner sc = new Scanner(System.in);
        log.debug("\u6309\u4EFB\u610F\u952E+\u56DE\u8F66\u9000\u51FA");
        sc.next();
        iRet = Net_DisconnectScanner();
        log.debug("End of main!!!\n");
        running = false;*/
    }

    public static void PrintTest()
    {
        log.debug("This is a test!\n");
    }

    public int ConnectScanner(int hScanner[], String szPort, int nBaudRate[])
    {
        int res;
        Enumeration portList;
        res = -1;
        String driverName = "com.sun.comm.Win32Driver";
        try
        {
            CommDriver commdriver = (CommDriver)Class.forName(driverName).newInstance();
            commdriver.initialize();
        }
        catch(ClassNotFoundException ex) { }
        catch(InstantiationException ex) { }
        catch(IllegalAccessException ex) { }
        portList = CommPortIdentifier.getPortIdentifiers();
        
        if(!portList.hasMoreElements())
            return 129; //break; /* Loop/switch isn't completed */
        
        CommPortIdentifier portId = (CommPortIdentifier)portList.nextElement();
        
        if(portId.getPortType() != 1) {
        	log.debug("MISSING_BLOCK_LABEL_210");
            return 210;//break;
        }
        if(!portId.getName().equals(szPort)){
        	log.debug("MISSING_BLOCK_LABEL_203");
        	return 203;//break;
        }
        
        SerialParameters parameters = new SerialParameters(portId.getName(), 115200, 0, 0, 8, 1, 0);
        connection = new SerialConnection(portId, parameters, null);
        try {
			connection.openConnection();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    res = 129;
            return res;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    res = 130;
            return res;
        } catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    res = 131;
            return res;
        } catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    res = 132;
            return res;
        }
        hScanner[0] = 1;
        res = AutoMode(hScanner[0], 0, 0);
        if(res == 0) {
        	log.debug("MISSING_BLOCK_LABEL_162");
        	return 162; //break ;
        }
        DisconnectScanner(hScanner[0]);
        res = 130;
        return res;
        
        /*try {
            nBaudRate[0] = 0;
            res = 0;
        }
        catch(PortInUseException ex) {
            res = 129;
            return res;
        }
        catch(IOException ex) {
            res = 130;
            return res;
        }
        catch(TooManyListenersException ex) {
            res = 131;
            return res;
        }
        catch(UnsupportedCommOperationException ex) {
            res = 132;
            return res;
        }
        break;  Loop/switch isn't completed 
        res = 253;
        continue;  Loop/switch isn't completed 
        res = 254;
    	*/
        //if(true) goto _L2; else goto _L1
        //_L1:
        //return res;
    }

    public int DisconnectScanner(int hScanner)
    {
        int res = 0;
        L1:
        try
        {
            connection.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of DisconnectScanner:\n");
            res = -1;
        }
        
        return res;
    }

    private boolean Packet(int hScanner, byte cmd[], byte return_data[])
    {
        boolean fRes;
        byte writepk[];
        byte readpk[];
        fRes = false;
        writepk = new byte[MAX_PACKET_LEN];
        readpk = new byte[MAX_PACKET_LEN];
        int BufLen = cmd[0] + 2;
        if(BufLen > MAX_PACKET_LEN)
            return false;
        byte checksum;
        writepk[0] = 64;
        checksum = 64;
        int i;
        for(i = 0; i < cmd[0]; i++)
        {
            writepk[i + 1] = cmd[i];
            checksum += writepk[i + 1];
        }

        checksum = (byte)(~checksum);
        checksum++;
        writepk[i + 1] = checksum;
        if(!SerialBuffer(hScanner, writepk, readpk, BufLen))
            return false;
        BufLen = readpk[1] + 2;
        if(BufLen > MAX_PACKET_LEN)
            return false;
        checksum = 0;
        for(i = 0; i < BufLen; i++)
            checksum += readpk[i];

        if(checksum != 0)
            return false;
        try
        {
            Calendar cd = Calendar.getInstance();
            String year = addZero(cd.get(1));
            String month = addZero(cd.get(2) + 1);
            String day = addZero(cd.get(5));
            String hour = addZero(cd.get(11));
            String min = addZero(cd.get(12));
            String sec = addZero(cd.get(13));
            String mil = addZero3(cd.get(14));
            String strOutput = (new StringBuilder()).append("\u3010").append(year).append(month).append(day).append(" ").append(hour).append(":").append(min).append(":").append(sec).append(".").append(mil).append("\u3011--- ###################################################").toString();
            writeLog("", strOutput);
            if(writepk[2] == readpk[2])
                switch(readpk[0])
                {
                default:
                    break;

                case -16: 
                    return_data[0] = -16;
                    if(BufLen - 4 > 0)
                        if(readpk[2] == 22 || readpk[2] == 87)
                            System.arraycopy(readpk, 1, return_data, 1, BufLen - 2);
                        else
                            System.arraycopy(readpk, 3, return_data, 1, BufLen - 4);
                    fRes = true;
                    break;

                case -12: 
                    return_data[0] = -12;
                    return_data[1] = readpk[3];
                    fRes = true;
                    break;
                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Packet:\n");
            fRes = false;
        }
        return fRes;
    }

    private boolean SerialBuffer(int hScanner, byte lpPutBuf[], byte lpGetBuf[], int nBufLen)
    {
        boolean fRes = false;
        Calendar cd;
        InputStream inStream;
        byte lpRecvTemp[];
        int i, j;
        long timev, lnow, lnew;
        int count;
        String year = "", month = "", day = "", hour = "", min = "", sec = "", mil = "", strOutput = "";
        byte bufferTmp1[];
        byte bufferTmp2[];
        byte chTemp[] = new byte[nBufLen];
        byte lpSndTemp[] = new byte[nBufLen * 4];
        System.arraycopy(lpPutBuf, 0, chTemp, 0, nBufLen);
        Bcd2AscEx(lpSndTemp, chTemp, nBufLen * 2);
        String snd = null;
		try {
			snd = new String(lpSndTemp, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        cd = Calendar.getInstance();
        year = addZero(cd.get(1));
        month = addZero(cd.get(2) + 1);
        day = addZero(cd.get(5));
        hour = addZero(cd.get(11));
        min = addZero(cd.get(12));
        sec = addZero(cd.get(13));
        mil = addZero3(cd.get(14));
        strOutput = (new StringBuilder()).append("\u3010").append(year).append(month).append(day).append(" ").append(hour).append(":").append(min).append(":").append(sec).append(".").append(mil).append("\u3011--- SenB[").append(addZero4(nBufLen)).append("]:").append(snd.trim()).toString();
        writeLog("", strOutput);
        log.debug(strOutput);
        try {
			connection.sendData(chTemp);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        inStream = connection.getIs();
        int iRecvLen = 0;
        lpRecvTemp = new byte[1024];
        i = 0;
        j = 0;
        int available = 0;
        timev = 2000L;
        lnow = System.currentTimeMillis();
        lnew = System.currentTimeMillis();
        count = 0;
        int readCount = 0;
        bufferTmp1 = new byte[5];
        bufferTmp2 = new byte[32];
        j = 0;
        
		while(true){
	        if(j >= 4){
	        	log.debug("MISSING_BLOCK_LABEL_568");
	            break ;
	        }
	        lnew = System.currentTimeMillis();
	        if(lnew - lnow <= timev){
	        	log.debug("MISSING_BLOCK_LABEL_539");
	            break;
	        }
	        // Make all the parameters empty here. So that they can be reused.
	        year = ""; month = ""; day = ""; hour = ""; min = ""; sec = ""; mil = ""; strOutput = "";
	        
	        cd = Calendar.getInstance();
	        year = addZero(cd.get(1));
	        month = addZero(cd.get(2) + 1);
	        day = addZero(cd.get(5));
	        hour = addZero(cd.get(11));
	        min = addZero(cd.get(12));
	        sec = addZero(cd.get(13));
	        mil = addZero3(cd.get(14));
	        strOutput = (new StringBuilder()).append("\u3010").append(year).append(month).append(day).append(" ").append(hour).append(":").append(min).append(":").append(sec).append(".").append(mil).append("\u3011--- Timout!!!-->recOri:[").append(j).append("]").append(Utility.bytes2HexString(bufferTmp1)).toString();
	        writeLog("", strOutput);
	        log.debug(strOutput);
	        //return false;
	        try {
				available = inStream.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        if(available > 0)
	        {
	            try {
					inStream.read(bufferTmp1, j, 1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            j++;
	        }
		}

        int head;
        strOutput = "";
        
        if(j >= 4)
        {
            Calendar cd2 = Calendar.getInstance();
            String yearx = addZero(cd2.get(1));
            String monthx = addZero(cd2.get(2) + 1);
            String dayx = addZero(cd2.get(5));
            String hourx = addZero(cd2.get(11));
            String minx = addZero(cd2.get(12));
            String secx = addZero(cd2.get(13));
            String milx = addZero3(cd2.get(14));
            strOutput = (new StringBuilder()).append("\u3010").append(yearx).append(monthx).append(dayx).append(" ").append(hourx).append(":").append(minx).append(":").append(secx).append(".").append(milx).append("\u3011--- recx:[").append(j).append("]").append(Utility.bytes2HexString(bufferTmp1)).toString();
            writeLog("", strOutput);
            log.debug(strOutput);
        }
        head = bufferTmp1[0];
        
        if(-16 == (byte)head || -12 == (byte)head){
            log.debug("MISSING_BLOCK_LABEL_994");//break;
        }
        Calendar cd3 = Calendar.getInstance();
        String yearx = addZero(cd3.get(1));
        String monthx = addZero(cd3.get(2) + 1);
        String dayx = addZero(cd3.get(5));
        String hourx = addZero(cd3.get(11));
        String minx = addZero(cd3.get(12));
        String secx = addZero(cd3.get(13));
        String milx = addZero3(cd3.get(14));
        strOutput = (new StringBuilder()).append("\u3010").append(yearx).append(monthx).append(dayx).append(" ").append(hourx).append(":").append(minx).append(":").append(secx).append(".").append(milx).append("\u3011--- rec3:[").append(j).append("]").append(Utility.bytes2HexString(bufferTmp1)).toString();
        writeLog("", strOutput);
        log.debug(strOutput);
        //return false;
        
        bufferTmp2 = new byte[1];
        
        int iRevc = 4;
        int len = bufferTmp1[1];
        if(-16 != (byte)head && -12 != (byte)head){
            log.debug("MISSING_BLOCK_LABEL_1839"); //break;
        }
        count = (2 + len) - iRevc;
        if(count > 0)
            bufferTmp2 = new byte[count];
        i = 0;
        
		while(true) {
		    // Make all the parameters empty here. So that they can be reused.
	        yearx = ""; monthx = ""; dayx = ""; hourx = ""; minx = ""; secx = ""; milx = ""; strOutput = "";
	    
			if(i >= count) {
	            log.debug("MISSING_BLOCK_LABEL_1299");
				break;
			}
	        lnew = System.currentTimeMillis();
	        if(lnew - lnow <= timev) {
	        	log.debug("MISSING_BLOCK_LABEL_1270");
	            break ;
	        }
	        Calendar cd1 = Calendar.getInstance();
	        yearx = addZero(cd1.get(1));
	        monthx = addZero(cd1.get(2) + 1);
	        dayx = addZero(cd1.get(5));
	        hourx = addZero(cd1.get(11));
	        minx = addZero(cd1.get(12));
	        secx = addZero(cd1.get(13));
	        milx = addZero3(cd1.get(14));
	        strOutput = (new StringBuilder()).append("\u3010").append(yearx).append(monthx).append(dayx).append(" ").append(hourx).append(":").append(minx).append(":").append(secx).append(".").append(milx).append("\u3011--- Timout!!!-->recOrm:[").append(j).append("]").append(Utility.bytes2HexString(bufferTmp1)).toString();
	        writeLog("", strOutput);
	        log.debug(strOutput);
	        //return false;
	        try {
				available = inStream.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        if(available > 0)
	        {
	            try {
					inStream.read(bufferTmp2, i, 1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            i++;
	        }
		}

        // Make all the parameters empty here. So that they can be reused.
        yearx = ""; monthx = ""; dayx = ""; hourx = ""; minx = ""; secx = ""; milx = ""; strOutput = "";
        
        if(count > 0)
        {
            Calendar cd1 = Calendar.getInstance();
            yearx = addZero(cd1.get(1));
            monthx = addZero(cd1.get(2) + 1);
            dayx = addZero(cd1.get(5));
            hourx = addZero(cd1.get(11));
            minx = addZero(cd1.get(12));
            secx = addZero(cd1.get(13));
            milx = addZero3(cd1.get(14));
            strOutput = (new StringBuilder()).append("\u3010").append(yearx).append(monthx).append(dayx).append(" ").append(hourx).append(":").append(minx).append(":").append(secx).append(".").append(milx).append("\u3011--- recy:[").append(j).append("]").append(Utility.bytes2HexString(bufferTmp2)).toString();
            writeLog("", strOutput);
            log.debug(strOutput);
        }
        iRecvLen = 4 + bufferTmp2.length;
        byte buffer[] = new byte[4 + bufferTmp2.length];
        int kk = 0;
        int ii = 0;
        for(ii = 0; ii < 4; ii++)
        {
            buffer[ii] = bufferTmp1[kk];
            lpGetBuf[ii] = buffer[ii];
            kk++;
        }

        kk = 0;
        for(; ii < 4 + bufferTmp2.length; ii++)
        {
            buffer[ii] = bufferTmp2[kk];
            lpGetBuf[ii] = buffer[ii];
            kk++;
        }

        // Make all the parameters empty here. So that they can be reused.
        yearx = ""; monthx = ""; dayx = ""; hourx = ""; minx = ""; secx = ""; milx = ""; strOutput = "";
        
        try {
	        Bcd2AscEx(lpRecvTemp, buffer, iRecvLen * 2);
	        String rev = new String(lpRecvTemp, "UTF-8");
	        Calendar cdx = Calendar.getInstance();
	        yearx = addZero(cd.get(1));
	        monthx = addZero(cd.get(2) + 1);
	        dayx = addZero(cd.get(5));
	        hourx = addZero(cd.get(11));
	        minx = addZero(cd.get(12));
	        secx = addZero(cd.get(13));
	        milx = addZero3(cd.get(14));
	        strOutput = (new StringBuilder()).append("\u3010").append(yearx).append(monthx).append(dayx).append(" ").append(hourx).append(":").append(minx).append(":").append(secx).append(".").append(milx).append("\u3011--- RecB[").append(addZero4(iRecvLen)).append("]:").append(rev.trim()).toString();
	        writeLog("", strOutput);
	        log.debug(strOutput);
	        fRes = true;
	        return fRes; //added
	        //break MISSING_BLOCK_LABEL_1863;
	        
	        //fRes = false;
	        //break MISSING_BLOCK_LABEL_1863;
    	} catch(Exception e){
	        e.printStackTrace();
	        log.debug("Exception of SocketBuffer:\n");
	        fRes = false;
	        return fRes;
        }
    }

    public int GetSerialData()
    {
        int available = 0;
        byte bufferTmp1[] = new byte[1];
        try
        {
            InputStream inStream = connection.getIs();
            available = inStream.available();
            if(available > 0)
                inStream.read(bufferTmp1, 0, 1);
            else
                bufferTmp1[0] = 0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetSerialData:\n");
        }
        return bufferTmp1[0];
    }

    public int SetBaudRate(int hScanner, int nBaudRate, int Address)
    {
        int BaudRate[];
        byte put[];
        byte get[];
        int res = _OK;
        BaudRate = (new int[] {
            600, 1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200
        });
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i;
        for(i = 0; i < 9 && nBaudRate != BaudRate[i]; i++);
        if(i > 8)
            return _baudrate_error;
        if(Address == 0)
        {
            put[0] = 3;
            put[1] = 1;
            put[2] = (byte)i;
            put[3] = 0;
        } else
        {
            put[0] = 4;
            put[1] = 1;
            put[2] = (byte)Address;
            put[3] = (byte)i;
            put[4] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetBaudRate:\n");
            res = -1;
        }
        return res;
    }

    public int GetReaderVersion(int hScanner, short wHardVer[], short wSoftVer[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 2;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 2;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address == 0)
            {
                wHardVer[0] = (short)(get[2] * 256 + get[3]);
                wSoftVer[0] = (short)(get[4] * 256 + get[5]);
            } else
            {
                wHardVer[0] = (short)(get[1] * 256 + get[2]);
                wSoftVer[0] = (short)(get[3] * 256 + get[4]);
            }
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int SetRelay(int hScanner, int Relay, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 3;
            put[1] = 3;
            put[2] = (byte)Relay;
            put[3] = 0;
        } else
        {
            put[0] = 4;
            put[1] = 3;
            put[2] = (byte)Address;
            put[3] = (byte)Relay;
            put[4] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetRelay:\n");
            res = -1;
        }
        return res;
    }

    public int SetOutputPower(int hScanner, int nPower1, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 3;
            put[1] = 4;
            put[2] = (byte)nPower1;
            put[3] = 0;
        } else
        {
            put[0] = 4;
            put[1] = 4;
            put[2] = (byte)Address;
            put[3] = (byte)nPower1;
            put[4] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetOutputPower:\n");
            res = -1;
        }
        return res;
    }

    public int SetFrequency(int hScanner, int Min_Frequency, int Max_Frequency, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 4;
            put[1] = 5;
            put[2] = (byte)Min_Frequency;
            put[3] = (byte)Max_Frequency;
            put[4] = 0;
        } else {
            put[0] = 5;
            put[1] = 5;
            put[2] = (byte)Address;
            put[3] = (byte)Min_Frequency;
            put[4] = (byte)Max_Frequency;
            put[5] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetFrequency:\n");
            res = -1;
        }
        return res;
    }

    public int GetFrequencyRange(int hScanner, byte Frequency[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = -121;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = -121;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address != 0)
                System.arraycopy(get, 2, Frequency, 0, 1);
            else
                System.arraycopy(get, 1, Frequency, 0, 1);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetFrequencyRange:\n");
            res = -1;
        }
        return res;
    }

    public int SetFrequencyEx(int hScanner, byte Frequency[], int Address)
    {
        byte put[];
        byte get[];
        int iCnt = 0;
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        //int iCnt;
        if(Address == 0)
        {
            put[0] = 8;
            put[1] = 5;
            iCnt = 2;
        } else
        {
            put[0] = 9;
            put[1] = 5;
            put[2] = (byte)Address;
            iCnt = 3;
        }
        if(1 <= Frequency[0] && Frequency[0] <= 3)
        {
            put[iCnt++] = Frequency[0];
            put[iCnt++] = Frequency[1];
            put[iCnt++] = Frequency[2];
            put[iCnt++] = 0;
            put[iCnt++] = 0;
            put[iCnt++] = 0;
            put[iCnt++] = 0;
        } else
        if(4 == Frequency[0])
        {
            put[iCnt++] = Frequency[0];
            put[iCnt++] = Frequency[1];
            put[iCnt++] = Frequency[2];
            put[iCnt++] = Frequency[3];
            put[iCnt++] = Frequency[4];
            put[iCnt++] = Frequency[5];
            put[iCnt++] = 0;
        } else
        {
            put[iCnt++] = Frequency[0];
            put[iCnt++] = Frequency[1];
            put[iCnt++] = Frequency[2];
            put[iCnt++] = 0;
            put[iCnt++] = 0;
            put[iCnt++] = 0;
            put[iCnt++] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetFrequencyEx:\n");
            res = -1;
        }
        return res;
    }

    public int GetFrequencyRangeEx(int hScanner, byte Frequency[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = -121;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = -121;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address == 0)
                System.arraycopy(get, 1, Frequency, 0, 6);
            else
                System.arraycopy(get, 2, Frequency, 0, 6);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetFrequencyRangeEx:\n");
            res = -1;
        }
        return res;
    }

    public int ReadBasicParam(int hScanner, byte pParam[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 6;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 6;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address != 0)
                System.arraycopy(get, 2, pParam, 0, 32);
            else
                System.arraycopy(get, 1, pParam, 0, 32);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int WriteBasicParam(int hScanner, byte pParam[], int Address)
    {
        byte put[];
        byte get[];
        int n;
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        n = 32;
        if(Address == 0)
        {
            put[0] = (byte)(2 + n);
            put[1] = 9;
            System.arraycopy(pParam, 0, put, 2, n);
            put[2 + n] = 0;
        } else
        {
            put[0] = (byte)(3 + n);
            put[1] = 9;
            put[2] = (byte)Address;
            System.arraycopy(pParam, 0, put, 3, n);
            put[3 + n] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of WriteBasicParam:\n");
            res = -1;
        }
        return res;
    }

    public int ReadAutoParam(int hScanner, byte pParam[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 20;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 20;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address != 0)
                System.arraycopy(get, 2, pParam, 0, 32);
            else
                System.arraycopy(get, 1, pParam, 0, 32);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int WriteAutoParam(int hScanner, byte pParam[], int Address)
    {
        byte put[];
        byte get[];
        int n;
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        n = 32;
        if(Address == 0)
        {
            put[0] = (byte)(2 + n);
            put[1] = 19;
            System.arraycopy(pParam, 0, put, 2, n);
            put[2 + n] = 0;
        } else
        {
            put[0] = (byte)(3 + n);
            put[1] = 19;
            put[2] = (byte)Address;
            System.arraycopy(pParam, 0, put, 3, n);
            put[3 + n] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of WriteAutoParam:\n");
            res = -1;
        }
        return res;
    }

    public int ReadFactoryParameter(int hScanner, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 13;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 13;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of ReadFactoryParameter:\n");
            res = -1;
        }
        return res;
    }

    public int SetAntenna(int hScanner, int Antenna, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 3;
            put[1] = 10;
            put[2] = (byte)Antenna;
            put[3] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 10;
            put[2] = (byte)Address;
            put[3] = (byte)Antenna;
            put[4] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetAntenna:\n");
            res = -1;
        }
        return res;
    }

    public int Reboot(int hScanner, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 14;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 14;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Reboot:\n");
            res = -1;
        }
        return res;
    }

    public int AutoMode(int hScanner, int Mode, int Address)
    {
        int res;
        byte put[];
        byte get[];
        res = 0;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0) {
            put[0] = 3;
            put[1] = 15;
            put[2] = (byte)Mode;
            put[3] = 0;
        } else {
            put[0] = 4;
            put[1] = 15;
            put[2] = (byte)Address;
            put[3] = (byte)Mode;
            put[4] = 0;
        }
        try {
	        if(!Packet(hScanner, put, get))
	            return 134;
	        
	        if(get[0] == -12)
	            return get[1];
        } catch (Exception e) {
        //break MISSING_BLOCK_LABEL_122;
        e.printStackTrace();
        log.debug("Exception of Net_AutoMode:\n");
        res = -1;
    	}
        return res;
    }

    public int SetReaderTime(int hScanner, byte time[], int Address)
    {
        byte put[];
        byte get[];
        int n;
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        n = 6;
        if(Address == 0)
        {
            put[0] = (byte)(2 + n);
            put[1] = 17;
            System.arraycopy(time, 0, put, 2, n);
            put[2 + n] = 0;
        } else
        {
            put[0] = (byte)(3 + n);
            put[1] = 17;
            put[2] = (byte)Address;
            System.arraycopy(time, 0, put, 3, n);
            put[3 + n] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetReaderTime:\n");
            res = -1;
        }
        return res;
    }

    public int GetReaderTime(int hScanner, byte time[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 18;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 18;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address == 0)
                System.arraycopy(get, 1, time, 0, 6);
            else
                System.arraycopy(get, 2, time, 0, 6);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetReaderTime:\n");
            res = -1;
        }
        return res;
    }

    public int SetReportFilter(int hScanner, int ptr, int len, byte mask[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            if(len == 0)
            {
                put[0] = 6;
                put[1] = 21;
                put[2] = (byte)(ptr >> 8);
                put[3] = (byte)ptr;
                put[4] = 0;
                put[5] = 0;
                put[6] = 0;
            } else
            {
                int m;
                if(len % 8 == 0)
                    m = len / 8;
                else
                    m = len / 8 + 1;
                put[0] = (byte)(6 + m);
                put[1] = 21;
                put[2] = (byte)(ptr >> 8);
                put[3] = (byte)ptr;
                put[4] = (byte)(len >> 8);
                put[5] = (byte)len;
                int i;
                for(i = 0; i < m; i++)
                    put[6 + i] = mask[i];

                put[6 + i] = 0;
            }
        } else
        if(len == 0)
        {
            put[0] = 7;
            put[1] = 21;
            put[2] = (byte)Address;
            put[3] = (byte)(ptr >> 8);
            put[4] = (byte)ptr;
            put[5] = 0;
            put[6] = 0;
            put[7] = 0;
        } else
        {
            int m;
            if(len % 8 == 0)
                m = len / 8;
            else
                m = len / 8 + 1;
            put[0] = (byte)(7 + m);
            put[1] = 21;
            put[2] = (byte)Address;
            put[3] = (byte)(ptr >> 8);
            put[4] = (byte)ptr;
            put[5] = (byte)(len >> 8);
            put[6] = (byte)len;
            int i;
            for(i = 0; i < m; i++)
                put[7 + i] = mask[i];

            put[7 + i] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetReportFilter:\n");
            res = -1;
        }
        return res;
    }

    public int GetReportFilter(int hScanner, int ptr[], int len[], byte mask[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 22;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 22;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(get[1] == 4 || get[1] == 5)
            {
                ptr[0] = 0;
                len[0] = 0;
                mask[0] = 0;
            } else
            if(Address == 0)
            {
                ptr[0] = (byte)(get[3] * 256 + get[4]);
                len[0] = (byte)(get[5] * 256 + get[6]);
                System.arraycopy(get, 7, mask, 0, get[1] - 6);
            } else
            {
                ptr[0] = (byte)(get[4] * 256 + get[5]);
                len[0] = (byte)(get[6] * 256 + get[7]);
                System.arraycopy(get, 8, mask, 0, get[1] - 7);
            }
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetReportFilter:\n");
            res = -1;
        }
        return res;
    }

    public int SetReaderID(int hScanner, byte ReaderID[], int Address)
    {
        byte put[];
        byte get[];
        int n;
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        n = 10;
        if(Address == 0)
        {
            put[0] = (byte)(2 + n);
            put[1] = -117;
            System.arraycopy(ReaderID, 0, put, 2, n);
            put[2 + n] = 0;
        } else
        {
            put[0] = (byte)(3 + n);
            put[1] = -117;
            put[2] = (byte)Address;
            System.arraycopy(ReaderID, 0, put, 3, n);
            put[3 + n] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SetReaderID:\n");
            res = -1;
        }
        return res;
    }

    public int GetReaderID(int hScanner, byte ReaderID[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = -116;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = -116;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address == 0)
                System.arraycopy(get, 1, ReaderID, 0, 10);
            else
                System.arraycopy(get, 2, ReaderID, 0, 10);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetReaderID:\n");
            res = -1;
        }
        return res;
    }

    public int GetModuleVer(int hScanner, byte bModuleVer[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = 24;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = 24;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _comm_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            if(Address == 0)
                System.arraycopy(get, 1, bModuleVer, 0, 2);
            else
                System.arraycopy(get, 2, bModuleVer, 0, 2);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of GetModuleVer:\n");
            res = -1;
        }
        return res;
    }

    private int EPC1G2_ListTagID(int hScanner, int mem, int ptr, int len, byte mask[], byte btID[], int nCounter[], int IDlen[], 
            int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int m;
        if(len == 0)
        {
            m = 0;
        } else
        {
            m = len / 8;
            if(len % 8 != 0)
                m++;
        }
        if(Address == 0)
        {
            put[0] = (byte)(m + 6);
            put[1] = -18;
            put[2] = (byte)mem;
            put[3] = (byte)(ptr >> 8);
            put[4] = (byte)ptr;
            put[5] = (byte)len;
            int i;
            for(i = 0; i < m; i++)
                put[6 + i] = mask[i];

            put[6 + i] = 0;
        } else
        {
            put[0] = (byte)(m + 7);
            put[1] = -18;
            put[2] = (byte)Address;
            put[3] = (byte)mem;
            put[4] = (byte)(ptr >> 8);
            put[5] = (byte)ptr;
            put[6] = (byte)len;
            int i;
            for(i = 0; i < m; i++)
                put[7 + i] = mask[i];

            put[7 + i] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            nCounter[0] = get[1];
            int last = 2;
            if(Address == 0)
            {
                nCounter[0] = get[1];
                last = 2;
            } else
            {
                nCounter[0] = get[2];
                last = 3;
            }
            if(nCounter[0] <= 8)
            {
                for(int i = 0; i < nCounter[0]; i++)
                {
                    int next = get[last] * 2 + 1;
                    if(Address == 0)
                        System.arraycopy(get, last, btID, last - 2, next);
                    else
                        System.arraycopy(get, last, btID, last - 3, next);
                    last += next;
                }

            } else
            {
                for(int i = 0; i < 8; i++)
                {
                    int next = get[last] * 2 + 1;
                    if(Address == 0)
                        System.arraycopy(get, last, btID, last - 2, next);
                    else
                        System.arraycopy(get, last, btID, last - 3, next);
                    last += next;
                }

            }
            if(Address == 0)
                IDlen[0] = last - 2;
            else
                IDlen[0] = last - 3;
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_ListTagID:\n");
            res = -1;
        }
        return res;
    }

    private int EPC1G2_GetIDList(int hScanner, byte btID[], int stNum, int nCounter, int IDlen[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 4;
            put[1] = -19;
            put[2] = (byte)stNum;
            put[3] = (byte)nCounter;
            put[4] = 0;
        } else
        {
            put[0] = 5;
            put[1] = -19;
            put[2] = (byte)Address;
            put[3] = (byte)stNum;
            put[4] = (byte)nCounter;
            put[5] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            int k = 0;
            int last;
            if(Address == 0)
            {
                last = 2;
                k = 0;
            } else
            {
                last = 3;
                k = 1;
            }
            for(int i = 0; i < nCounter; i++)
            {
                int next = get[last] * 2 + 1;
                System.arraycopy(get, last, btID, last - 2 - k, next);
                last += next;
            }

            if(Address == 0)
                IDlen[0] = last - 2;
            else
                IDlen[0] = last - 3;
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_GetIDList:\n");
            res = -1;
        }
        return res;
    }

    public int EPC1G2_ReadLabelID(int hScanner, int mem, int ptr, int len, byte mask[], byte IDBuffer[], int nCounter[], int Address)
    {
        int res = _OK;
        int i;
        int j;
        int k;
        int l[];
        int IDlen[];
        byte lpbuffer[];
        i = 0;
        j = 0;
        k = 0;
        l = new int[2];
        IDlen = new int[2];
        lpbuffer = new byte[1024];
        nCounter[0] = 0;
        res = EPC1G2_ListTagID(hScanner, mem, ptr, len, mask, IDBuffer, nCounter, IDlen, Address);
        
        if(res != _OK)
            return res;
        
        //if(nCounter[0] <= 8)
        //    break MISSING_BLOCK_LABEL_188;
        if(nCounter[0] <= 8)
        	log.debug("MISSING_BLOCK_LABEL_188");
        
        j = nCounter[0] / 8;
        k = nCounter[0] - j * 8;
        i = 1;
        
        while(true) {
	        if(i >= j) //MISSING_BLOCK_LABEL_153
	            break;
	        res = EPC1G2_GetIDList(hScanner, lpbuffer, i * 8, 8, l, Address);
	        if(res != _OK)
	            return res;
	        IDlen[0] += l[0];
	        i++;
        }
        //goto _L1
          
        if(k == 0) //MISSING_BLOCK_LABEL_188
            log.debug("MISSING_BLOCK_LABEL_188");//break;
        
        res = EPC1G2_GetIDList(hScanner, lpbuffer, j * 8, k, l, Address);
        if(res != _OK)
            return res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_ReadLabelID:\n");
            res = -1;
        }
        return res;
    }

    public int EPC1G2_ReadWordBlock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int ptr, int len, byte Data[], byte AccessPassword[], 
            int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 10);
            put[1] = -20;
            put[2] = (byte)EPC_WORD;
            
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 3] = (byte)mem;
            put[EPC_WORD * 2 + 4] = (byte)ptr;
            put[EPC_WORD * 2 + 5] = (byte)len;
            for(i = 0; i < 4; i++)
                put[EPC_WORD * 2 + 6 + i] = AccessPassword[i];

            put[EPC_WORD * 2 + 6 + i] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 11);
            put[1] = -20;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 4] = (byte)mem;
            put[EPC_WORD * 2 + 5] = (byte)ptr;
            put[EPC_WORD * 2 + 6] = (byte)len;
            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 7 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 7 + i1] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            if(Address == 0)
                System.arraycopy(get, 1, Data, 0, len * 2);
            else
                System.arraycopy(get, 2, Data, 0, len * 2);
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_ReadWordBlock:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_WriteWordBlock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int ptr, int len, byte Data[], byte AccessPassword[], 
            int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + len * 2 + 10);
            put[1] = -21;
            put[2] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 3] = (byte)mem;
            put[EPC_WORD * 2 + 4] = (byte)ptr;
            put[EPC_WORD * 2 + 5] = (byte)len;
            for(i1 = 0; i1 < len * 2; i1++)
                put[EPC_WORD * 2 + 6 + i1] = Data[i1];

            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + len * 2 + 6 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + len * 2 + 6 + i1] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + len * 2 + 11);
            put[1] = -21;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 4] = (byte)mem;
            put[EPC_WORD * 2 + 5] = (byte)ptr;
            put[EPC_WORD * 2 + 6] = (byte)len;
            for(i1 = 0; i1 < len * 2; i1++)
                put[EPC_WORD * 2 + 7 + i1] = Data[i1];

            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + len * 2 + 7 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + len * 2 + 7 + i1] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_WriteWordBlock:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_SetLock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int Lock, byte AccessPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 9);
            put[1] = -22;
            put[2] = (byte)EPC_WORD;
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 3] = (byte)mem;
            put[EPC_WORD * 2 + 4] = (byte)Lock;
            for(int i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 5 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 9] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 10);
            put[1] = -22;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 4] = (byte)mem;
            put[EPC_WORD * 2 + 5] = (byte)Lock;
            for(int i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 6 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 10] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_SetLock:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_EraseBlock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int ptr, int len, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 6);
            put[1] = -23;
            put[2] = (byte)EPC_WORD;
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 3] = (byte)mem;
            put[EPC_WORD * 2 + 4] = (byte)ptr;
            put[EPC_WORD * 2 + 5] = (byte)len;
            put[EPC_WORD * 2 + 6] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 7);
            put[1] = -23;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 4] = (byte)mem;
            put[EPC_WORD * 2 + 5] = (byte)ptr;
            put[EPC_WORD * 2 + 6] = (byte)len;
            put[EPC_WORD * 2 + 7] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_EraseBlock:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_KillTag(int hScanner, int EPC_WORD, byte IDBuffer[], byte KillPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 7);
            put[1] = -24;
            put[2] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 3 + i1] = KillPassword[i1];

            put[EPC_WORD * 2 + 3 + i1] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 8);
            put[1] = -24;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 4 + i1] = KillPassword[i1];

            put[EPC_WORD * 2 + 4 + i1] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_KillTag:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_WriteEPC(int hScanner, int len, byte Data[], byte AccessPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(7 + len * 2);
            put[1] = -25;
            put[2] = (byte)len;
            int i1;
            for(i1 = 0; i1 < len * 2; i1++)
                put[3 + i1] = Data[i1];

            for(i1 = 0; i1 < 4; i1++)
                put[len * 2 + 3 + i1] = AccessPassword[i1];

            put[len * 2 + 3 + i1] = 0;
        } else
        {
            put[0] = (byte)(8 + len * 2);
            put[1] = -25;
            put[2] = (byte)Address;
            put[3] = (byte)len;
            int i1;
            for(i1 = 0; i1 < len * 2; i1++)
                put[4 + i1] = Data[i1];

            for(i1 = 0; i1 < 4; i1++)
                put[len * 2 + 4 + i1] = AccessPassword[i1];

            put[len * 2 + 4 + i1] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_WriteEPC:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_BlockLock(int hScanner, int EPC_WORD, byte IDBuffer[], int ptr, byte AccessPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 8);
            put[1] = -26;
            put[2] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[3 + i1] = (byte)ptr;
            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 4 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 4 + i1] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 9);
            put[1] = -26;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[4 + i1] = (byte)ptr;
            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 5 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 5 + i1] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_BlockLock:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_ChangeEas(int hScanner, int EPC_WORD, byte IDBuffer[], int State, byte AccessPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 8);
            put[1] = -27;
            put[2] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[3 + i1] = (byte)State;
            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 4 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 4 + i1] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 9);
            put[1] = -27;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            int i1;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[4 + i1] = (byte)State;
            for(i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 5 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 5 + i1] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_ChangeEas:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_EasAlarm(int hScanner, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = -28;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = -28;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_EasAlarm:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_ReadProtect(int hScanner, byte AccessPassword[], int EPC_WORD, byte IDBuffer[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 7);
            put[1] = -29;
            int i1;
            for(i1 = 0; i1 < 4; i1++)
                put[2 + i1] = AccessPassword[i1];

            put[2 + i1] = (byte)EPC_WORD;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[7 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 7] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 8);
            put[1] = -29;
            put[2] = (byte)Address;
            int i1;
            for(i1 = 0; i1 < 4; i1++)
                put[3 + i1] = AccessPassword[i1];

            put[3 + i1] = (byte)EPC_WORD;
            for(i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[8 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 8] = 0;
        }
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ReadProtect:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_RStreadProtect(int hScanner, byte AccessPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = 6;
            put[1] = -30;
            for(int i1 = 0; i1 < 4; i1++)
                put[2 + i1] = AccessPassword[i1];

            put[6] = 0;
        } else
        {
            put[0] = 7;
            put[1] = -30;
            put[2] = (byte)Address;
            for(int i1 = 0; i1 < 4; i1++)
                put[3 + i1] = AccessPassword[i1];

            put[7] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_RStreadProtect:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_BlockReadLock(int hScanner, int EPC_WORD, byte IDBuffer[], int Lock, byte AccessPassword[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        if(Address == 0)
        {
            put[0] = (byte)(EPC_WORD * 2 + 8);
            put[1] = -31;
            put[2] = (byte)EPC_WORD;
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[3 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 3] = (byte)Lock;
            for(int i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 4 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 8] = 0;
        } else
        {
            put[0] = (byte)(EPC_WORD * 2 + 9);
            put[1] = -31;
            put[2] = (byte)Address;
            put[3] = (byte)EPC_WORD;
            for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
                put[4 + i1] = IDBuffer[i1];

            put[EPC_WORD * 2 + 4] = (byte)Lock;
            for(int i1 = 0; i1 < 4; i1++)
                put[EPC_WORD * 2 + 5 + i1] = AccessPassword[i1];

            put[EPC_WORD * 2 + 9] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_BlockReadLock:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_ReadEPCandData(int hScanner, byte EPC_WORD[], byte IDBuffer[], int mem, int ptr, int len, byte Data[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 5;
        put[1] = -32;
        put[2] = (byte)mem;
        put[3] = (byte)ptr;
        put[4] = (byte)len;
        put[5] = 0;
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            EPC_WORD[0] = get[1];
            System.arraycopy(get, 2, IDBuffer, 0, get[1] * 2);
            System.arraycopy(get, 2 + get[1] * 2, Data, 0, len * 2);
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_ReadEPCandData:\n");
            res1 = -1;
        }
        return res1;
    }

    public int EPC1G2_DetectTag(int hScanner, int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        if(Address == 0)
        {
            put[0] = 2;
            put[1] = -17;
            put[2] = 0;
        } else
        {
            put[0] = 3;
            put[1] = -17;
            put[2] = (byte)Address;
            put[3] = 0;
        }
        if(!Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1;
        try
        {
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of EPC1G2_DetectTag:\n");
            res1 = -1;
        }
        return res1;
    }

    public DatagramChannel GetDatagramChannel()
    {
        return gchannel;
    }

    public DatagramSocket GetDatagramSocket()
    {
        return gsocket;
    }

    public int Net_ConnectScanner(int hScanner[], String nTargetAddress, int nTargetPort, String nHostAddress, int nHostPort)
    {
        int i = 0;
        int res;
        try {
			gchannel = DatagramChannel.open();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
        gsocket = gchannel.socket();
        try {
			gchannel.configureBlocking(false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
        String SsTemp[] = nHostAddress.split("\\.");
        byte bs[] = {
            (byte)Integer.parseInt(SsTemp[0]), (byte)Integer.parseInt(SsTemp[1]), (byte)Integer.parseInt(SsTemp[2]), (byte)Integer.parseInt(SsTemp[3])
        };
        InetAddress InHostaddress = null;
		try {
			InHostaddress = InetAddress.getByAddress(bs);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		log.debug("InHostaddress:"+InHostaddress+"  , nHostPort:"+nHostPort);
        SocketAddress saHost = new InetSocketAddress(InHostaddress, nHostPort);
        if(!gsocket.isBound() && !bound)
        {
        	log.debug(saHost);
            try {
            	gsocket.setReuseAddress(true);
				gsocket.bind(saHost);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 129;
			}
            bound = true;
        }
        gstrTargetAddress = nTargetAddress;
        gTargetPort = nTargetPort;
        
        res = Net_AutoMode(hScanner[0], 0);
        
        if(res == 0) {
            log.debug("MISSING_BLOCK_LABEL_164");//break;
        } else {
	        Net_DisconnectScanner();
	        res = 130;
	        return res;
        }
        try
        {
            res = 0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_ConnectScanner:\n");
            res = -1;
            gchannel = null;
        }
        return res;
        
    }

    public int Net_DisconnectScanner()
    {
    	if(null == gchannel)
            return 1;
        try
        {
            gchannel.close();
            if(!gsocket.isClosed())
            {
                gsocket.close();
                gsocket = null;
                bound = false;
            }
            gchannel = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_DisconnectScanner:\n");
        }
        return 0;
    }

    public int Net_AutoMode(int hScanner, int Mode)
    {
        int res;
        byte put[];
        byte get[];
        res = 0;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 3;
        put[1] = 15;
        put[2] = (byte)Mode;
        put[3] = 0;
        try{
	        if(!Net_Packet(hScanner, put, get)){
	            return 134;
	        }
	        if(get[0] == -12){
	            return get[1];
	        }
        } catch(Exception e){
	        //break ;
	        e.printStackTrace();
	        log.debug("Exception of Net_AutoMode:\n");
	        res = -1;
	        return res;
        }
		return res;
    }

    private boolean Net_Packet(int hScanner, byte lpSendBuf[], byte lpReceiveBuf[])
    {
        boolean fRes;
        byte SendTemp[];
        byte ReceiveTemp[];
        fRes = false;
        SendTemp = new byte[MAX_PACKET_LEN];
        ReceiveTemp = new byte[MAX_PACKET_LEN];
        int BufLen = lpSendBuf[0] + 2;
        if(BufLen > MAX_PACKET_LEN)
            return false;
        byte checksum;
        SendTemp[0] = 64;
        checksum = 64;
        int i;
        for(i = 0; i < lpSendBuf[0]; i++)
        {
            SendTemp[i + 1] = lpSendBuf[i];
            checksum += SendTemp[i + 1];
        }

        checksum = (byte)(~checksum);
        checksum++;
        SendTemp[i + 1] = checksum;
        if(!SocketBuffer(hScanner, SendTemp, ReceiveTemp, BufLen))
            return false;
        BufLen = ReceiveTemp[1] + 2;
        if(BufLen > MAX_PACKET_LEN)
            return false;
        checksum = 0;
        for(int i1 = 0; i1 < BufLen; i1++)
            checksum += ReceiveTemp[i1];

        if(checksum != 0)
            return false;
        try
        {
            Calendar cd = Calendar.getInstance();
            String year = addZero(cd.get(1));
            String month = addZero(cd.get(2) + 1);
            String day = addZero(cd.get(5));
            String hour = addZero(cd.get(11));
            String min = addZero(cd.get(12));
            String sec = addZero(cd.get(13));
            String mil = addZero3(cd.get(14));
            String strOutput = (new StringBuilder()).append("\u3010").append(year).append(month).append(day).append(" ").append(hour).append(":").append(min).append(":").append(sec).append(".").append(mil).append("\u3011--- ===================================================").toString();
            writeLog("", strOutput);
            if(ReceiveTemp[2] == SendTemp[2])
                switch(ReceiveTemp[0])
                {
                default:
                    break;

                case -16: 
                    lpReceiveBuf[0] = -16;
                    if(BufLen - 4 > 0)
                        if(ReceiveTemp[2] == 22 || ReceiveTemp[2] == 87)
                            System.arraycopy(ReceiveTemp, 1, lpReceiveBuf, 1, BufLen - 2);
                        else
                            System.arraycopy(ReceiveTemp, 3, lpReceiveBuf, 1, BufLen - 4);
                    fRes = true;
                    break;

                case -12: 
                    lpReceiveBuf[0] = -12;
                    lpReceiveBuf[1] = ReceiveTemp[3];
                    fRes = true;
                    break;
                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_Packet:\n");
            fRes = false;
        }
        return fRes;
    }

    private boolean SocketBuffer(int hScanner, byte lpPutBuf[], byte lpGetBuf[], int nBufLen)
    {
        boolean fRes = false;
        try
        {
            DatagramChannel channel = gchannel;
            byte chTemp[] = new byte[nBufLen];
            byte lpSndTemp[] = new byte[nBufLen * 4];
            System.arraycopy(lpPutBuf, 0, chTemp, 0, nBufLen);
            ByteBuffer sendBuf = ByteBuffer.allocate(chTemp.length);
            sendBuf.put(chTemp);
            sendBuf.flip();
            String SsTemp[] = gstrTargetAddress.split("\\.");
            byte bs[] = {
                (byte)Integer.parseInt(SsTemp[0]), (byte)Integer.parseInt(SsTemp[1]), (byte)Integer.parseInt(SsTemp[2]), (byte)Integer.parseInt(SsTemp[3])
            };
            InetAddress address = InetAddress.getByAddress(bs);
            SocketAddress sa = new InetSocketAddress(address, gTargetPort);
            Bcd2AscEx(lpSndTemp, chTemp, nBufLen * 2);
            String snd = new String(lpSndTemp, "UTF-8");
            Calendar cd = Calendar.getInstance();
            String year = addZero(cd.get(1));
            String month = addZero(cd.get(2) + 1);
            String day = addZero(cd.get(5));
            String hour = addZero(cd.get(11));
            String min = addZero(cd.get(12));
            String sec = addZero(cd.get(13));
            String mil = addZero3(cd.get(14));
            String strOutput = (new StringBuilder()).append("\u3010").append(year).append(month).append(day).append(" ").append(hour).append(":").append(min).append(":").append(sec).append(".").append(mil).append("\u3011--- SenA[").append(addZero4(nBufLen)).append("]:").append(snd.trim()).toString();
            writeLog("", strOutput);
            log.debug(strOutput);
            if(channel != null) {
			    int iNum = channel.send(sendBuf, sa);
			    Selector selector = null;
			    boolean selectorOk = true;
			    try {
			    	selector = Selector.open();
			    } catch(Exception e){
			    	e.printStackTrace();
			    	selectorOk = false;
			    }
			    if(channel.isOpen() && selectorOk) {
			        channel.register(selector, 1);
			        int iRecvLen = 0;
			        ByteBuffer byteBuffer = ByteBuffer.allocate(65535);
			        byte lpRecvTemp[] = new byte[1024];
			        int eventsCount = selector.select(TimeOut);
			        if(eventsCount > 0)
			        {
			            Set selectedKeys = selector.selectedKeys();
			            Iterator iterator = selectedKeys.iterator();
			            do
			            {
			                if(!iterator.hasNext())
			                    break;
			                SelectionKey sk = (SelectionKey)iterator.next();
			                iterator.remove();
			                if(sk.isReadable())
			                {
			                    DatagramChannel datagramChannel = (DatagramChannel)sk.channel();
			                    byteBuffer.clear();
			                    SocketAddress sb = datagramChannel.receive(byteBuffer);
			                    byteBuffer.flip();
			                    iRecvLen = byteBuffer.limit();
			                    java.nio.CharBuffer charBuffer = Charset.forName("UTF-8").decode(byteBuffer);
			                    System.arraycopy(byteBuffer.array(), 0, lpGetBuf, 0, iRecvLen);
			                    Bcd2AscEx(lpRecvTemp, byteBuffer.array(), iRecvLen * 2);
			                    String rev = new String(lpRecvTemp, "UTF-8");
			                    Calendar cdx = Calendar.getInstance();
			                    String yearx = addZero(cdx.get(1));
			                    String monthx = addZero(cdx.get(2) + 1);
			                    String dayx = addZero(cdx.get(5));
			                    String hourx = addZero(cdx.get(11));
			                    String minx = addZero(cdx.get(12));
			                    String secx = addZero(cdx.get(13));
			                    String milx = addZero3(cdx.get(14));
			                    strOutput = (new StringBuilder()).append("\u3010").append(yearx).append(monthx).append(dayx).append(" ").append(hourx).append(":").append(minx).append(":").append(secx).append(".").append(milx).append("\u3011--- RecA[").append(addZero4(iRecvLen)).append("]:").append(rev.trim()).toString();
			                    writeLog("", strOutput);
			                    log.debug(strOutput);
			                    byteBuffer.clear();
			                    fRes = true;
			                }
			            } while(true);
			        }
			    } else {
			    	log.debug("Sorry, channel is closed currently.");
			   }
            } else {
            	log.debug("Channel is null !!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of SocketBuffer:\n");
            fRes = false;
        }
        return fRes;
    }

    public int Net_SetBaudRate(int hScanner, int nBaudRate)
    {
        int res;
        byte put[];
        byte get[];
        int BaudRate[];
        res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        BaudRate = (new int[] {
            600, 1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200
        });
        int i;
        for(i = 0; i < 9 && nBaudRate != BaudRate[i]; i++);
        if(i > 8)
            return _baudrate_error;
        put[0] = 3;
        put[1] = 15;
        put[2] = (byte)i;
        put[3] = 0;
        try {
	        if(!Net_Packet(hScanner, put, get))
	            return _net_error;
	        if(get[0] == -12)
	            return get[1];
	        
	        //break MISSING_BLOCK_LABEL_183;
    	}
        catch(Exception e) {
	        e.printStackTrace();
	        log.debug("Exception of Net_SetBaudRate:\n");
	        res = -1;
	        return res;
        }
		return res;
    }

    public int Net_GetReaderVersion(int hScanner, short wHardVer[], short wSoftVer[])
    {
        byte put[];
        byte get[];
        //int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 2;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res1 = _OK;
        try
        {
            wHardVer[0] = (short)(get[1] * 256 + get[2]);
            wSoftVer[0] = (short)(get[3] * 256 + get[4]);
            res1 = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res1 = -1;
        }
        return res1;
    }

    public int Net_ReadBasicParam(int hScanner, byte pParam[])
    {
        byte put[];
        byte get[];
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 6;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res = _OK;
        try
        {
            System.arraycopy(get, 1, pParam, 0, 32);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int Net_ReadAutoParam(int hScanner, byte pParam[])
    {
        byte put[];
        byte get[];
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 20;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res = _OK;
        try
        {
            System.arraycopy(get, 1, pParam, 0, 32);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int Net_SetAntenna(int hScanner, int Antenna)
    {
        byte put[];
        byte get[];
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 3;
        put[1] = 10;
        put[2] = (byte)Antenna;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        int res = _OK;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_SetAntenna:\n");
            res = -1;
        }
        return res;
    }

    public int Net_SetReaderID(int hScanner, byte ReaderID[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 12;
        put[1] = -117;
        put[2] = 0;
        int i = 0;
        for(i = 0; i < 10; i++)
            put[2 + i] = ReaderID[i];

        put[2 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int Net_GetReaderID(int hScanner, byte ReaderID[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = -116;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        try
        {
            System.arraycopy(get, 1, ReaderID, 0, 10);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderVersion:\n");
            res = -1;
        }
        return res;
    }

    public int Net_GetModuleVer(int hScanner, byte bModuleVer[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 24;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        try
        {
            System.arraycopy(get, 1, bModuleVer, 0, 2);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetModuleVer:\n");
            res = -1;
        }
        return res;
    }

    public int Net_SetReaderNetwork(int hScanner, byte IP_Address[], int Port, byte Mask[], byte Gateway[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        //int i = 0;
        put[0] = 16;
        put[1] = 48;
        for(int i = 0; i < 4; i++)
        {
            put[2 + i] = IP_Address[i];
            put[8 + i] = Mask[i];
            put[12 + i] = Gateway[i];
        }

        put[6] = (byte)(Port >> 8);
        put[7] = (byte)Port;
        put[16] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_SetReaderNetwork:\n");
            res = -1;
        }
        return res;
    }

    public int Net_GetReaderNetwork(int hScanner, byte IP_Address[], int Port[], byte Mask[], byte Gateway[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 49;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        try
        {
            System.arraycopy(get, 1, IP_Address, 0, 4);
            Port[0] = (get[5] << 8) + get[6];
            System.arraycopy(get, 7, Mask, 0, 4);
            System.arraycopy(get, 11, Gateway, 0, 4);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderNetwork:\n");
            res = -1;
        }
        return res;
    }

    public int Net_SetReaderMAC(int hScanner, byte MAC[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        //int i = 0;
        put[0] = 8;
        put[1] = 50;
        int i;
        for(i = 0; i < 6; i++)
            put[2 + i] = MAC[i];

        put[2 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_SetReaderMAC:\n");
            res = -1;
        }
        return res;
    }

    public int Net_GetReaderMAC(int hScanner, byte MAC[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 51;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            System.arraycopy(get, 1, MAC, 0, 6);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetReaderMAC:\n");
            res = -1;
        }
        return res;
    }

    public int Net_SetRelay(int hScanner, int relay)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 3;
        put[1] = 3;
        put[2] = (byte)relay;
        put[3] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetRelay:\n");
            res = -1;
        }
        return res;
    }

    public int Net_GetRelay(int hScanner, int relay[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = 11;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            System.arraycopy(get, 1, relay, 0, 1);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_GetRelay:\n");
            res = -1;
        }
        return res;
    }

    private int Net_EPC1G2_ListTagID(int hScanner, int mem, int ptr, int len, byte mask[], byte btID[], int nCounter[], int IDlen[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int m;
        if(len == 0)
        {
            m = 0;
        } else
        {
            m = len / 8;
            if(len % 8 != 0)
                m++;
        }
        put[0] = (byte)(m + 6);
        put[1] = -18;
        put[2] = (byte)mem;
        put[3] = (byte)(ptr >> 8);
        put[4] = (byte)ptr;
        put[5] = (byte)len;
        int i;
        for(i = 0; i < m; i++)
            put[6 + i] = mask[i];

        put[6 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            nCounter[0] = get[1];
            int last = 2;
            if(nCounter[0] <= 8)
            {
                for(int i1 = 0; i1 < nCounter[0]; i1++)
                {
                    int next = get[last] * 2 + 1;
                    System.arraycopy(get, last, btID, last - 2, next);
                    last += next;
                }

            } else
            {
                for(int i1 = 0; i1 < 8; i1++)
                {
                    int next = get[last] * 2 + 1;
                    System.arraycopy(get, last, btID, last - 2, next);
                    last += next;
                }

            }
            IDlen[0] = last - 2;
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ListTagID:\n");
            res = -1;
        }
        return res;
    }

    private int Net_EPC1G2_GetIDList(int hScanner, byte btID[], int stNum, int nCounter, int IDlen[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 4;
        put[1] = -19;
        put[2] = (byte)stNum;
        put[3] = (byte)nCounter;
        put[4] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            int last = 2;
            for(int i = 0; i < nCounter; i++)
            {
                int next = get[last] * 2 + 1;
                System.arraycopy(get, last, btID, last - 2, next);
                last += next;
            }

            IDlen[0] = last - 2;
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_GetIDList:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_ReadLabelID(int hScanner, int mem, int ptr, int len, byte mask[], byte IDBuffer[], int nCounter[])
    {
        int res = _OK;
        int i;
        int j;
        int k;
        int l[];
        int IDlen[];
        byte lpbuffer[];
        i = 0;
        j = 0;
        k = 0;
        l = new int[2];
        IDlen = new int[2];
        lpbuffer = new byte[1024];
        nCounter[0] = 0;
        res = Net_EPC1G2_ListTagID(hScanner, mem, ptr, len, mask, IDBuffer, nCounter, IDlen);
        if(res != _OK)
            return res;
        
        if(nCounter[0] <= 8) {
        	log.debug("MISSING_BLOCK_LABEL_182");
            //break ;
        }
        j = nCounter[0] / 8;
        k = nCounter[0] - j * 8;
        i = 1;
        //_L1:
        while(true){
	        if(i >= j)
	            break; // MISSING_BLOCK_LABEL_149
	        res = Net_EPC1G2_GetIDList(hScanner, lpbuffer, i * 8, 8, l);
	        if(res != _OK)
	            return res;
	        IDlen[0] += l[0];
	        i++;
        }
        //  goto _L1
        
        if(k == 0){
        	log.debug("MISSING_BLOCK_LABEL_182");
            //break;// MISSING_BLOCK_LABEL_182
        }
        res = Net_EPC1G2_GetIDList(hScanner, lpbuffer, j * 8, k, l);
        if(res != _OK)
            return res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ReadLabelID:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_ReadWordBlock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int ptr, int len, byte Data[], byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 10);
        put[1] = -20;
        put[2] = (byte)EPC_WORD;
        
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        put[EPC_WORD * 2 + 3] = (byte)mem;
        put[EPC_WORD * 2 + 4] = (byte)ptr;
        put[EPC_WORD * 2 + 5] = (byte)len;
        for(int i1 = 0; i1 < 4; i1++)
            put[EPC_WORD * 2 + 6 + i1] = AccessPassword[i1];

        put[EPC_WORD * 2 + 6 + i] = 0;
        
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        
        if(get[0] == -12)
            return get[1];
        

        try
        {
            System.arraycopy(get, 1, Data, 0, len * 2);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ReadWordBlock:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_WriteWordBlock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int ptr, int len, byte Data[], byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + len * 2 + 10);
        put[1] = -21;
        put[2] = (byte)EPC_WORD;

        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        put[EPC_WORD * 2 + 3] = (byte)mem;
        put[EPC_WORD * 2 + 4] = (byte)ptr;
        put[EPC_WORD * 2 + 5] = (byte)len;
        for(int i1 = 0; i1 < len * 2; i1++)
            put[EPC_WORD * 2 + 6 + i1] = Data[i1];

        for(int i1 = 0; i1 < 4; i1++)
            put[EPC_WORD * 2 + len * 2 + 6 + i1] = AccessPassword[i1];

        put[EPC_WORD * 2 + len * 2 + 6 + i] = 0;
        
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_WriteWordBlock:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_SetLock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int Lock, byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 9);
        put[1] = -22;
        put[2] = (byte)EPC_WORD;
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        put[EPC_WORD * 2 + 3] = (byte)mem;
        put[EPC_WORD * 2 + 4] = (byte)Lock;
        for(int i1 = 0; i1 < 4; i1++)
            put[EPC_WORD * 2 + 5 + i1] = AccessPassword[i1];

        put[EPC_WORD * 2 + 9] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_SetLock:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_EraseBlock(int hScanner, int EPC_WORD, byte IDBuffer[], int mem, int ptr, int len)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 6);
        put[1] = -23;
        put[2] = (byte)EPC_WORD;
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        put[EPC_WORD * 2 + 3] = (byte)mem;
        put[EPC_WORD * 2 + 4] = (byte)ptr;
        put[EPC_WORD * 2 + 5] = (byte)len;
        put[EPC_WORD * 2 + 6] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_EraseBlock:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_KillTag(int hScanner, int EPC_WORD, byte IDBuffer[], byte KillPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 7);
        put[1] = -24;
        put[2] = (byte)EPC_WORD;
        
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        for(int i1 = 0; i1 < 4; i1++)
            put[EPC_WORD * 2 + 3 + i1] = KillPassword[i1];

        put[EPC_WORD * 2 + 3 + i] = 0;
        
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];

        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_KillTag:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_WriteEPC(int hScanner, int len, byte Data[], byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(7 + len * 2);
        put[1] = -25;
        put[2] = (byte)len;
        //int i;
        for(int i1 = 0; i1 < len * 2; i1++)
            put[3 + i1] = Data[i1];

        for(int i1 = 0; i1 < 4; i1++)
            put[len * 2 + 3 + i1] = AccessPassword[i1];

        put[len * 2 + 3 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_WriteEPC:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_BlockLock(int hScanner, int EPC_WORD, byte IDBuffer[], int ptr, byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 8);
        put[1] = -26;
        put[2] = (byte)EPC_WORD;
        //int i;
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        put[3 + i] = (byte)ptr;
        for(int i1 = 0; i1 < 4; i1++)
            put[EPC_WORD * 2 + 4 + i1] = AccessPassword[i1];

        put[EPC_WORD * 2 + 4 + i] = 0;
        
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_BlockLock:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_ChangeEas(int hScanner, int EPC_WORD, byte IDBuffer[], int State, byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 8);
        put[1] = -27;
        put[2] = (byte)EPC_WORD;
        //int i;
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];
        
        put[3 + i] = (byte)State;
        for(i = 0; i < 4; i++)
            put[EPC_WORD * 2 + 4 + i] = AccessPassword[i];

        put[EPC_WORD * 2 + 4 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ChangeEas:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_EasAlarm(int hScanner)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = -28;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_EasAlarm:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_ReadProtect(int hScanner, byte AccessPassword[], int EPC_WORD, byte IDBuffer[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 7);
        put[1] = -29;
        //int i;
        for(int i1 = 0; i1 < 4; i1++)
            put[2 + i1] = AccessPassword[i1];

        put[2 + i] = (byte)EPC_WORD;
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[7 + i1] = IDBuffer[i1];

        put[EPC_WORD * 2 + 7] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ReadProtect:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_RStreadProtect(int hScanner, byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = 6;
        put[1] = -30;
        for(int i1 = 0; i1 < 4; i1++)
            put[2 + i1] = AccessPassword[i1];

        put[6] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_RStreadProtect:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_ReadEPCandData(int hScanner, byte EPC_WORD[], byte IDBuffer[], int mem, int ptr, int len, byte Data[], int Address)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 5;
        put[1] = -32;
        put[2] = (byte)mem;
        put[3] = (byte)ptr;
        put[4] = (byte)len;
        put[5] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            EPC_WORD[0] = get[1];
            System.arraycopy(get, 2, IDBuffer, 0, get[1] * 2);
            System.arraycopy(get, 2 + get[1] * 2, Data, 0, len * 2);
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_ReadEPCandData:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_BlockReadLock(int hScanner, int EPC_WORD, byte IDBuffer[], int Lock, byte AccessPassword[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = (byte)(EPC_WORD * 2 + 8);
        put[1] = -31;
        put[2] = (byte)EPC_WORD;
        for(int i1 = 0; i1 < EPC_WORD * 2; i1++)
            put[3 + i1] = IDBuffer[i1];

        put[EPC_WORD * 2 + 3] = (byte)Lock;
        for(int i1 = 0; i1 < 4; i1++)
            put[EPC_WORD * 2 + 4 + i1] = AccessPassword[i1];

        put[EPC_WORD * 2 + 8] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_BlockReadLock:\n");
            res = -1;
        }
        return res;
    }

    public int Net_EPC1G2_DetectTag(int hScanner)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 2;
        put[1] = -17;
        put[2] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_EPC1G2_DetectTag:\n");
            res = -1;
        }
        return res;
    }

    public int Net_ChangeFrequency(int hScanner, int fre)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 3;
        put[1] = 12;
        put[2] = (byte)fre;
        put[3] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_ChangeFrequency:\n");
            res = -1;
        }
        return res;
    }

    public int Net_SetHardVersion(int hScanner, int wHardVer, int wHardVer2)
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        put[0] = 4;
        put[1] = -123;
        put[2] = (byte)wHardVer;
        put[3] = (byte)wHardVer2;
        put[4] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_SetHardVersion:\n");
            res = -1;
        }
        return res;
    }

    public int Net_WriteFactoryBasicParam(int hScanner, byte pParm[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = 34;
        put[1] = 12;
        //int i;
        for(int i1 = 0; i1 < 32; i1++)
            put[2 + i1] = pParm[i1];

        put[2 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_WriteFactoryBasicParam:\n");
            res = -1;
        }
        return res;
    }

    public int Net_WriteFactoryAutoParam(int hScanner, byte pParm[])
    {
        byte put[];
        byte get[];
        int res = _OK;
        put = new byte[MAX_PACKET_LEN];
        get = new byte[MAX_PACKET_LEN];
        int i = 0;
        put[0] = 34;
        put[1] = 23;
        //int i;
        for(int i1 = 0; i1 < 32; i1++)
            put[2 + i1] = pParm[i1];

        put[2 + i] = 0;
        if(!Net_Packet(hScanner, put, get))
            return _net_error;
        if(get[0] == -12)
            return get[1];
        //int res;
        try
        {
            res = _OK;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.debug("Exception of Net_WriteFactoryAutoParam:\n");
            res = -1;
        }
        return res;
    }

    public void Bcd2AscEx(byte asc[], byte bcd[], int len)
    {
        int j = (len + len % 2) / 2;
        int k = 3 * j;
        for(int i = 0; i < j; i++)
        {
            asc[3 * i] = (byte)(bcd[i] >> 4 & 15);
            asc[3 * i + 1] = (byte)(bcd[i] & 15);
            asc[3 * i + 2] = 32;
        }

        for(int i = 0; i < k; i++)
        {
            if((i + 1) % 3 == 0)
                continue;
            if(asc[i] > 9)
                asc[i] = (byte)((65 + asc[i]) - 10);
            else
                asc[i] += 48;
        }

        asc[k] = 0;
    }

    public void writeLog(String fileNameHead, String logString)
    {
        try
        {
            String logFilePathName = null;
            Calendar cd = Calendar.getInstance();
            int year = cd.get(1);
            String month = addZero(cd.get(2) + 1);
            String day = addZero(cd.get(5));
            String hour = addZero(cd.get(11));
            String min = addZero(cd.get(12));
            String sec = addZero(cd.get(13));
            String mil = addZero(cd.get(14));
            File fileParentDir = new File("./log");
            if(!fileParentDir.exists())
                fileParentDir.mkdir();
            if(fileNameHead == null || fileNameHead.equals(""))
                logFilePathName = (new StringBuilder()).append("./log/").append(year).append(month).append(day).append(".log").toString();
            else
                logFilePathName = (new StringBuilder()).append("./log/").append(fileNameHead).append(year).append(month).append(day).append(".log").toString();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(logFilePathName, true));
            String time = (new StringBuilder()).append("[").append(year).append(month).append(day).append("-").append(hour).append(":").append(min).append(":").append(sec).append("] ").toString();
            printWriter.println(logString);
            printWriter.flush();
        }
        catch(FileNotFoundException e)
        {
            e.getMessage();
        }
    }

    public String addZero(int i)
    {
        if(i < 10)
        {
            String tmpString = (new StringBuilder()).append("0").append(i).toString();
            return tmpString;
        } else
        {
            return String.valueOf(i);
        }
    }

    public String addZero3(int i)
    {
        if(i < 10)
        {
            String tmpString = (new StringBuilder()).append("00").append(i).toString();
            return tmpString;
        }
        if(i < 100)
        {
            String tmpString = (new StringBuilder()).append("0").append(i).toString();
            return tmpString;
        } else
        {
            return String.valueOf(i);
        }
    }

    public String addZero4(int i)
    {
        if(i < 10)
        {
            String tmpString = (new StringBuilder()).append("000").append(i).toString();
            return tmpString;
        }
        if(i < 100)
        {
            String tmpString = (new StringBuilder()).append("00").append(i).toString();
            return tmpString;
        }
        if(i < 1000)
        {
            String tmpString = (new StringBuilder()).append("0").append(i).toString();
            return tmpString;
        } else
        {
            return String.valueOf(i);
        }
    }

    protected void startRun()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run()
            {
                if(running)
                    log.debug("\u5F00\u59CB\u8FD0\u884C");
                else
                    System.exit(0);
            }

        };
        timer.scheduleAtFixedRate(task, new Date(), 2000L);
    }

    public static int ID_MAX_SIZE_64BIT = 8;
    public static int ID_ATA_MAX_SIZE = 20;
    public static int ID_MAX_SIZE_96BIT = 65;
    public static int MAX_LABELS = 255;
    public static int _OK = 0;
    static int _init_rs232_err = 129;
    static int _no_scanner = 130;
    static int _comm_error = 131;
    static int _baudrate_error = 132;
    static int _init_net_error = 133;
    static int _net_error = 134;
    static int _no_antenna = 1;
    static int _no_label = 2;
    static int _invalid_label = 3;
    static int _less_power = 4;
    static int _write_prot_error = 5;
    static int _check_sum_error = 6;
    static int _parameter_error = 7;
    static int _memory_error = 8;
    static int _password_error = 9;
    static int _killpassword_error = 10;
    static int _nonlicet_command = 11;
    static int _nonlicet_user = 12;
    static int _invalid_command = 30;
    static int _other_error = 31;
    static int _no_cardID_input = 32;
    static int MAX_READER_IP = 500;
    static int MAX_PACKET_LEN = 262;
    static int TimeOut = 2000;
    
/*    static DatagramChannel gchannel = null;
    static DatagramSocket gsocket;
    static String gstrTargetAddress = "192.168.0.71";
    static int gTargetPort = 8080;
    static boolean running = true;
    private static boolean bound = false;
    static SerialConnection connection = null;
*/
    
    // Some parameters need to be instance variables so that we can connect two readers at a time.
    DatagramChannel gchannel = null;
    DatagramSocket gsocket;
    String gstrTargetAddress = "192.168.0.71";
    int gTargetPort = 8080;
    boolean running = true;
    private boolean bound = false;
    SerialConnection connection = null;
    
}


/*
	DECOMPILATION REPORT

	Decompiled from: F:\workspaces\workspace teaching core java\ReaderAPI\ImportedClasses/Reader/ReaderAPI.class
	Total time: 336 ms
	Jad reported messages/errors:
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
Couldn't fully decompile method ConnectScanner
Couldn't resolve all exception handlers in method ConnectScanner
Couldn't resolve all exception handlers in method Packet
Couldn't fully decompile method SerialBuffer
Couldn't resolve all exception handlers in method SerialBuffer
Couldn't resolve all exception handlers in method SetBaudRate
Couldn't resolve all exception handlers in method GetReaderVersion
Couldn't resolve all exception handlers in method SetRelay
Couldn't resolve all exception handlers in method SetOutputPower
Couldn't resolve all exception handlers in method SetFrequency
Couldn't resolve all exception handlers in method GetFrequencyRange
Couldn't resolve all exception handlers in method SetFrequencyEx
Couldn't resolve all exception handlers in method GetFrequencyRangeEx
Couldn't resolve all exception handlers in method ReadBasicParam
Couldn't resolve all exception handlers in method WriteBasicParam
Couldn't resolve all exception handlers in method ReadAutoParam
Couldn't resolve all exception handlers in method WriteAutoParam
Couldn't resolve all exception handlers in method ReadFactoryParameter
Couldn't resolve all exception handlers in method SetAntenna
Couldn't resolve all exception handlers in method Reboot
Couldn't fully decompile method AutoMode
Couldn't resolve all exception handlers in method AutoMode
Couldn't resolve all exception handlers in method SetReaderTime
Couldn't resolve all exception handlers in method GetReaderTime
Couldn't resolve all exception handlers in method SetReportFilter
Couldn't resolve all exception handlers in method GetReportFilter
Couldn't resolve all exception handlers in method SetReaderID
Couldn't resolve all exception handlers in method GetReaderID
Couldn't resolve all exception handlers in method GetModuleVer
Couldn't resolve all exception handlers in method EPC1G2_ListTagID
Couldn't resolve all exception handlers in method EPC1G2_GetIDList
Couldn't fully decompile method EPC1G2_ReadLabelID
Couldn't resolve all exception handlers in method EPC1G2_ReadLabelID
Couldn't resolve all exception handlers in method EPC1G2_ReadWordBlock
Couldn't resolve all exception handlers in method EPC1G2_WriteWordBlock
Couldn't resolve all exception handlers in method EPC1G2_SetLock
Couldn't resolve all exception handlers in method EPC1G2_EraseBlock
Couldn't resolve all exception handlers in method EPC1G2_KillTag
Couldn't resolve all exception handlers in method EPC1G2_WriteEPC
Couldn't resolve all exception handlers in method EPC1G2_BlockLock
Couldn't resolve all exception handlers in method EPC1G2_ChangeEas
Couldn't resolve all exception handlers in method EPC1G2_EasAlarm
Couldn't resolve all exception handlers in method EPC1G2_ReadProtect
Couldn't resolve all exception handlers in method EPC1G2_RStreadProtect
Couldn't resolve all exception handlers in method EPC1G2_BlockReadLock
Couldn't resolve all exception handlers in method EPC1G2_ReadEPCandData
Couldn't resolve all exception handlers in method EPC1G2_DetectTag
Couldn't resolve all exception handlers in method Net_ConnectScanner
Couldn't fully decompile method Net_AutoMode
Couldn't resolve all exception handlers in method Net_AutoMode
Couldn't resolve all exception handlers in method Net_Packet
Couldn't fully decompile method Net_SetBaudRate
Couldn't resolve all exception handlers in method Net_SetBaudRate
Couldn't resolve all exception handlers in method Net_GetReaderVersion
Couldn't resolve all exception handlers in method Net_ReadBasicParam
Couldn't resolve all exception handlers in method Net_ReadAutoParam
Couldn't resolve all exception handlers in method Net_SetAntenna
Couldn't resolve all exception handlers in method Net_SetReaderID
Couldn't resolve all exception handlers in method Net_GetReaderID
Couldn't resolve all exception handlers in method Net_GetModuleVer
Couldn't resolve all exception handlers in method Net_SetReaderNetwork
Couldn't resolve all exception handlers in method Net_GetReaderNetwork
Couldn't resolve all exception handlers in method Net_SetReaderMAC
Couldn't resolve all exception handlers in method Net_GetReaderMAC
Couldn't resolve all exception handlers in method Net_SetRelay
Couldn't resolve all exception handlers in method Net_GetRelay
Couldn't resolve all exception handlers in method Net_EPC1G2_ListTagID
Couldn't resolve all exception handlers in method Net_EPC1G2_GetIDList
Couldn't fully decompile method Net_EPC1G2_ReadLabelID
Couldn't resolve all exception handlers in method Net_EPC1G2_ReadLabelID
Couldn't resolve all exception handlers in method Net_EPC1G2_ReadWordBlock
Couldn't resolve all exception handlers in method Net_EPC1G2_WriteWordBlock
Couldn't resolve all exception handlers in method Net_EPC1G2_SetLock
Couldn't resolve all exception handlers in method Net_EPC1G2_EraseBlock
Couldn't resolve all exception handlers in method Net_EPC1G2_KillTag
Couldn't resolve all exception handlers in method Net_EPC1G2_WriteEPC
Couldn't resolve all exception handlers in method Net_EPC1G2_BlockLock
Couldn't resolve all exception handlers in method Net_EPC1G2_ChangeEas
Couldn't resolve all exception handlers in method Net_EPC1G2_EasAlarm
Couldn't resolve all exception handlers in method Net_EPC1G2_ReadProtect
Couldn't resolve all exception handlers in method Net_EPC1G2_RStreadProtect
Couldn't resolve all exception handlers in method Net_EPC1G2_ReadEPCandData
Couldn't resolve all exception handlers in method Net_EPC1G2_BlockReadLock
Couldn't resolve all exception handlers in method Net_EPC1G2_DetectTag
Couldn't resolve all exception handlers in method Net_ChangeFrequency
Couldn't resolve all exception handlers in method Net_SetHardVersion
Couldn't resolve all exception handlers in method Net_WriteFactoryBasicParam
Couldn't resolve all exception handlers in method Net_WriteFactoryAutoParam
	Exit status: 0
	Caught exceptions:
*/