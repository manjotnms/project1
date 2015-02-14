/*jadclipse*/// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   SerialConnection.java

package mtss.PassiveReader.ReaderApi.sdk;

import java.io.*;
import java.util.TooManyListenersException;
import java.util.logging.Logger;
import javax.comm.*;

// Referenced classes of package sdk:
//            SerialParameters

public class SerialConnection
{

    public boolean isConnected()
    {
        return connected;
    }

    public void setConnected(boolean connected)
    {
        this.connected = connected;
    }

    public SerialConnection(CommPortIdentifier portId)
    {
        this.portId = portId;
    }

    public SerialConnection(CommPortIdentifier portId, SerialParameters parameters, SerialPortEventListener listener)
    {
        this.portId = portId;
        this.parameters = parameters;
        this.listener = listener;
        connected = false;
    }

    public void openConnection()
        throws PortInUseException, IOException, TooManyListenersException, UnsupportedCommOperationException
    {
        port = (SerialPort)portId.open(parameters.getPortName(), 2000);
        is = port.getInputStream();
        os = port.getOutputStream();
        port.addEventListener(listener);
        port.notifyOnDataAvailable(true);
        port.notifyOnBreakInterrupt(true);
        port.notifyOnCTS(true);
        port.notifyOnCarrierDetect(true);
        port.notifyOnDSR(true);
        port.notifyOnFramingError(true);
        port.notifyOnOutputEmpty(true);
        port.notifyOnOverrunError(true);
        port.notifyOnParityError(true);
        port.notifyOnRingIndicator(true);
        port.setSerialPortParams(parameters.getBaudRate(), parameters.getDatabits(), parameters.getStopbits(), parameters.getParity());
        connected = true;
    }

    public void close()
        throws IOException
    {
        port.close();
    }

    public void sendData(byte cmd[])
        throws IOException
    {
        long start = System.currentTimeMillis();
        int iRet;
        for(iRet = 0; is.available() > 0; iRet = is.read());
        iRet = 0;
        os.write(cmd);
        long end = System.currentTimeMillis();
        LOG.info((new StringBuilder()).append("Send data cost ").append((double)(end - start) / 1000D).toString());
    }

    public OutputStream getOs()
    {
        return os;
    }

    public InputStream getIs()
    {
        return is;
    }

    private static final Logger LOG = Logger.getLogger("sdk.SerialConnection");//sdk.SerialConnection.getName());
    
    CommPortIdentifier portId;
    SerialPort port;
    SerialParameters parameters;
    private OutputStream os;
    private InputStream is;
    SerialPortEventListener listener;
    boolean connected;

}


/*
	DECOMPILATION REPORT

	Decompiled from: F:\workspaces\workspace teaching core java\ReaderAPI\ImportedClasses/sdk/SerialConnection.class
	Total time: 41 ms
	Jad reported messages/errors:
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/
