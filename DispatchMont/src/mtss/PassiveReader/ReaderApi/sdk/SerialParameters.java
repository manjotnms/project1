/*jadclipse*/// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   SerialParameters.java

package mtss.PassiveReader.ReaderApi.sdk;


public class SerialParameters
{

    public SerialParameters()
    {
        this("", 9600, 0, 0, 8, 1, 0);
    }

    public SerialParameters(String portName, int baudRate, int flowControlIn, int flowControlOut, int databits, int stopbits, int parity)
    {
        this.portName = portName;
        this.baudRate = baudRate;
        this.flowControlIn = flowControlIn;
        this.flowControlOut = flowControlOut;
        this.databits = databits;
        this.stopbits = stopbits;
        this.parity = parity;
    }

    public void setPortName(String portName)
    {
        this.portName = portName;
    }

    public String getPortName()
    {
        return portName;
    }

    public void setBaudRate(int baudRate)
    {
        this.baudRate = baudRate;
    }

    public void setBaudRate(String baudRate)
    {
        this.baudRate = Integer.parseInt(baudRate);
    }

    public int getBaudRate()
    {
        return baudRate;
    }

    public String getBaudRateString()
    {
        return Integer.toString(baudRate);
    }

    public void setFlowControlIn(int flowControlIn)
    {
        this.flowControlIn = flowControlIn;
    }

    public void setFlowControlIn(String flowControlIn)
    {
        this.flowControlIn = stringToFlow(flowControlIn);
    }

    public int getFlowControlIn()
    {
        return flowControlIn;
    }

    public String getFlowControlInString()
    {
        return flowToString(flowControlIn);
    }

    public void setFlowControlOut(int flowControlOut)
    {
        this.flowControlOut = flowControlOut;
    }

    public void setFlowControlOut(String flowControlOut)
    {
        this.flowControlOut = stringToFlow(flowControlOut);
    }

    public int getFlowControlOut()
    {
        return flowControlOut;
    }

    public String getFlowControlOutString()
    {
        return flowToString(flowControlOut);
    }

    public void setDatabits(int databits)
    {
        this.databits = databits;
    }

    public void setDatabits(String databits)
    {
        if(databits.equals("5"))
            this.databits = 5;
        if(databits.equals("6"))
            this.databits = 6;
        if(databits.equals("7"))
            this.databits = 7;
        if(databits.equals("8"))
            this.databits = 8;
    }

    public int getDatabits()
    {
        return databits;
    }

    public String getDatabitsString()
    {
        switch(databits)
        {
        case 5: // '\005'
            return "5";

        case 6: // '\006'
            return "6";

        case 7: // '\007'
            return "7";

        case 8: // '\b'
            return "8";
        }
        return "8";
    }

    public void setStopbits(int stopbits)
    {
        this.stopbits = stopbits;
    }

    public void setStopbits(String stopbits)
    {
        if(stopbits.equals("1"))
            this.stopbits = 1;
        if(stopbits.equals("1.5"))
            this.stopbits = 3;
        if(stopbits.equals("2"))
            this.stopbits = 2;
    }

    public int getStopbits()
    {
        return stopbits;
    }

    public String getStopbitsString()
    {
        switch(stopbits)
        {
        case 1: // '\001'
            return "1";

        case 3: // '\003'
            return "1.5";

        case 2: // '\002'
            return "2";
        }
        return "1";
    }

    public void setParity(int parity)
    {
        this.parity = parity;
    }

    public void setParity(String parity)
    {
        if(parity.equals("None"))
            this.parity = 0;
        if(parity.equals("Even"))
            this.parity = 2;
        if(parity.equals("Odd"))
            this.parity = 1;
    }

    public int getParity()
    {
        return parity;
    }

    public String getParityString()
    {
        switch(parity)
        {
        case 0: // '\0'
            return "None";

        case 2: // '\002'
            return "Even";

        case 1: // '\001'
            return "Odd";
        }
        return "None";
    }

    private int stringToFlow(String flowControl)
    {
        if(flowControl.equals("None"))
            return 0;
        if(flowControl.equals("Xon/Xoff Out"))
            return 8;
        if(flowControl.equals("Xon/Xoff In"))
            return 4;
        if(flowControl.equals("RTS/CTS In"))
            return 1;
        return !flowControl.equals("RTS/CTS Out") ? 0 : 2;
    }

    String flowToString(int flowControl)
    {
        switch(flowControl)
        {
        case 0: // '\0'
            return "None";

        case 8: // '\b'
            return "Xon/Xoff Out";

        case 4: // '\004'
            return "Xon/Xoff In";

        case 1: // '\001'
            return "RTS/CTS In";

        case 2: // '\002'
            return "RTS/CTS Out";

        case 3: // '\003'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        default:
            return "None";
        }
    }

    private String portName;
    private int baudRate;
    private int flowControlIn;
    private int flowControlOut;
    private int databits;
    private int stopbits;
    private int parity;
}


/*
	DECOMPILATION REPORT

	Decompiled from: F:\workspaces\workspace teaching core java\ReaderAPI\ImportedClasses/sdk/SerialParameters.class
	Total time: 42 ms
	Jad reported messages/errors:
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/