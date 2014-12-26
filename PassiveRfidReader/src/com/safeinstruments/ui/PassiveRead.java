package com.safeinstruments.ui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

import sdk.Utility;
import Reader.ReaderAPI;
//import ui.JDialogDemo.MyTimer;
//import ui.JDialogDemo.MyTimerAuto;
//import ui.JDialogDemo.MyTimerSerialAuto;

public class PassiveRead {
	public static void main(String[] args) {
		PassiveRead pr = new PassiveRead();
		if( pr.connectReader() ) {
			System.out.println("Reading starting");
			new PassiveRead().readContinously();
		} else {
			System.out.println("Oops, not getting the connection.");
		}
		
		//System.out.println("Got one EPC number : "+epcNum);
		//System.out.println("Stop Reading");
		
		//new PassiveRead().disconnectReader();
		//System.out.println("Disconnect Reader Done.");
		
	}
	
	// Variables for method : 
	int hScanner[] = new int[1];
	int res;
    //ReaderAPI Reader;
	int iHostPort = 5000; //auto host port
    int m_Port;//Reader Port
    int m_HostPort;//Host Port
    int ConnectMode		=	-1; // -RS232- - -RS485- 
    int ifConnectReader =	0; // 
    int NewConnect		=	0;
    int Address                 =       0;
    
    // Variables for method :
    boolean epcTagGetter = true;
    int nIDEvent = 1; // Events: Read, alarms, 
    int mem=0;//å“ªä¸ªåŒº,EPC,TID,USER,PASSWORD
    int ptr=0;//ä»Žå“ªå„¿å¼€å§‹
    int len=0;//å�–å¤šé•¿
    int EPC_Word=0; //epc length
    int m_antenna_sel;
    byte[] AccessPassword = new byte[4];
    int[] gAntenna = new int[4];//add by mqs 20130710
    int iModAnt;	//é€‰ä¸­çš„ä¸ªæ•°
    int Read_times=0;
    int iPlatform	=	0;	//0---626, 1---218

    byte[][] EPCC1G2_IDBuffer = new byte[ReaderAPI.MAX_LABELS][ReaderAPI.ID_MAX_SIZE_96BIT];

    // 
    byte[] mask = new byte[512];//æŽ©æŽª
    byte[] IDTemp = new byte[512];
    int timer_interval=1000 ;//å®šæ—¶å™¨é—´éš”


    private MyTimer mytimer = new MyTimer();
    boolean running = true;//å®šæ—¶å™¨ä½¿ç”¨

    DefaultListModel hlistModel=null;

    //private MyTimerAuto mytimerAuto=new MyTimerAuto();
    //private MyTimerSerialAuto mytimerSerialAuto=new MyTimerSerialAuto();
    int iTagNumber = 0; // Auto tag
    private boolean bound = false;
    DatagramChannel channel = null;  
    DatagramSocket socket = null;
    
    /**
     * This will continous reading from the reader as needed.
     * 
     */
    public void readContinously() {
    	System.out.println(" readContinously called...");
    	String strTemp;
        int m_LAddress = 0;
        int m_LLen = 0;
        int m_mem = 0;//bank

        // 1. 
        strTemp = "0"; // Address of Tag Data(bit):
        m_LAddress = Integer.parseInt(strTemp);
        if ((m_LAddress<0) || (m_LAddress>2047))
        {
            //MessageBox("Please input Location of Tag Address between 0 and 2047!","Warning",MB_ICONWARNING);
            //JOptionPane.showMessageDialog(this, "Please input Location of Tag Address between 0 and 2047!", "Warning", JOptionPane.ERROR_MESSAGE);
            //jTextField5.grabFocus();
            //jToggleButton1.setSelected(false);
            System.out.println("Please input Location of Tag Address between 0 and 2047!");
            epcTagGetter = false;
            return;
        }

        // 2. 
        strTemp = "0"; // Length of Tag Data(bit):
        m_LLen = Integer.parseInt(strTemp);
        if ((m_LLen<0) || (m_LLen>2048))
        {
            //MessageBox("Please input Length of Tag Data between 0 and 2048!","Warning",MB_ICONWARNING);
            //JOptionPane.showMessageDialog(this, "Please input Length of Tag Data between 0 and 2048!", "Warning", JOptionPane.ERROR_MESSAGE);
            //jTextField6.grabFocus();
            //jToggleButton1.setSelected(false);
            System.out.println("Please input Length of Tag Data between 0 and 2048!");
            epcTagGetter = false;
            return;
		}

        //  
        strTemp = ""; // Tag Data(HEX):
        if ( !Utility.isHexString(strTemp) && !strTemp.isEmpty() ){
            //MessageBox("Please input HEX!","Warning",MB_ICONWARNING);
            //JOptionPane.showMessageDialog(this, "Please input HEX!", "Warning", JOptionPane.ERROR_MESSAGE);
            //jTextField7.grabFocus();
            //jToggleButton1.setSelected(false);
        	System.out.println("Please input HEX!");
        	epcTagGetter = false;
            return;
        }

        mem=m_mem;
        ptr=m_LAddress;
        len=m_LLen;
        // 
        mask = Utility.convert2HexArray(strTemp);

        int i;
        int [] interval = new int[]{10,20,30,50,100,200,500};
        //Read Interval,10ms 20ms 30ms 50ms 100ms 200ms 500ms
        timer_interval = 200 ; // By default 200ms.

        nIDEvent = 1; // 
        
        // List EPC of Tag: Button
        if ( epcTagGetter ){
            System.out.println("jToggleButton_Down!\n");
            
            boolean antennaConfig[] = {true, false, false, false}; // By default only first is set to true, else false. 
            
            // Antenna Configuration.
            int k = 0;
            byte[] bTmpAnt = new byte[4];
            m_antenna_sel=0;
            bTmpAnt[0] = 0;
            bTmpAnt[1] = 0;
            bTmpAnt[2] = 0;
            bTmpAnt[3] = 0;
            gAntenna[0] = 0;
            gAntenna[1] = 0;
            gAntenna[2] = 0;
            gAntenna[3] = 0;
            iModAnt	=	0;
            if(antennaConfig[0])
            {
                    bTmpAnt[0] = 1;
                    iModAnt++;
                    m_antenna_sel += 1;
            }

            if(antennaConfig[1])
            {
                    bTmpAnt[1] = 2;
                    iModAnt++;
                    m_antenna_sel += 2;
            }

            if(antennaConfig[2])
            {
                    bTmpAnt[2] = 4;
                    iModAnt++;
                    m_antenna_sel += 4;
            }

            if(antennaConfig[3])
            {
                    bTmpAnt[3] = 8;
                    iModAnt++;
                    m_antenna_sel += 8;
            }

            for (i = 0; i < 4; i++)
            {
                    if ( bTmpAnt[i] != 0 )
                    {
                            gAntenna[k]	=	bTmpAnt[i];
                            k++;
                    }
            }

		switch(m_antenna_sel)
		{
		case 0:
			// MessageBox("Please choose one antenna at least!","Warning",MB_ICONWARNING);
			// JOptionPane.showMessageDialog(this, "Please choose one antenna at least!", "Warning", JOptionPane.ERROR_MESSAGE);
			System.out.println("Please choose one antenna at least!");
			return;

// 		case 1:
// 		case 2:
// 		case 4:
// 		case 8:
		default:
			res = ReaderAPI.Net_SetAntenna(hScanner[0], m_antenna_sel);
			/*switch(ConnectMode)
			{
			case 0://ç½‘å�£
                            res = ReaderAPI.Net_SetAntenna(hScanner[0], m_antenna_sel);
                            break;
			case 1://RS232
                            res = ReaderAPI.SetAntenna(hScanner[0], m_antenna_sel, Address);
				break;

			}*/
			if (res!=ReaderAPI._OK)
			{
				// MessageBox("SetAntenna Fail!Please try again!","Warning",MB_ICONWARNING);
				// JOptionPane.showMessageDialog(this, "SetAntenna Fail!Please try again!", "Warning", JOptionPane.ERROR_MESSAGE);
				System.out.println("SetAntenna Fail!Please try again!");
				return;
			}
			break;
		}


		//m_ListID.DeleteAllItems();
                //æ¸…é™¤è¡¨æ ¼ä¸­çš„å†…å®¹
                //DefaultTableModel tableModel = (DefaultTableModel)jTable1.get
                //tableModel.setRowCount(0);
                /*((DefaultTableModel) jTable1.getModel()).getDataVector().clear();   //æ¸…é™¤è¡¨æ ¼æ•°æ�®
                ((DefaultTableModel) jTable1.getModel()).fireTableDataChanged();//é€šçŸ¥æ¨¡åž‹æ›´æ–°
                jTable1.updateUI();//åˆ·æ–°è¡¨æ ¼
            */

		 	Read_times=0;
            // å¼€å�¯æ—¶é—´æ ‡å¿—
            running =   true;
            // start mytimer
            mytimer = new MyTimer();
            mytimer.setInterval(timer_interval);
            mytimer.start();
        } else {
            System.out.println("jToggleButton_Up!\n");
            // 
            running =   false;
            //start mytimer
            mytimer.setEnd(true);

/*            //kill tag
            jComboBox2.removeAllItems();

            //å�—çš„è¯»å†™
            jComboBox3.removeAllItems();

            //Set protect for reading or writing
            jComboBox6.removeAllItems();

            //Lock Block for User
            jComboBox4.removeAllItems();

            //Alarm
            jComboBox5.removeAllItems();

            //Read Protect
            jComboBox7.removeAllItems();
*/

            /*
             * SafeInstruments: Previously it is using the JTable(ui) so get the total no of rows from there.
             * But now will get them from List of tag records recorded so far.
             * 
             * */
            
            int k = 0;
			String str;
			byte[] temp = new byte[128];
			int ID_len;
			for(i=0;i<k;i++)
			{

					//strTemp = (String) jTable1.getValueAt(i, 4);
                    strTemp = "";
					EPCC1G2_IDBuffer[i][0] = (byte)Integer.parseInt(strTemp, 16);
                    ID_len = EPCC1G2_IDBuffer[i][0]*2;

                    str="";
                    // 
                    //strTemp = (String) jTable1.getValueAt(i, 1);
                    strTemp = "";
                    temp = Utility.convert2HexArray(strTemp);

                    System.arraycopy(temp, 0, EPCC1G2_IDBuffer[i], 1, ID_len);

                    str =  String.format("%02d.",i+1);
                    str += strTemp;

                    
                    /*//kill tag
                    jComboBox2.addItem(str);

                    //å�—çš„è¯»å†™
                    jComboBox3.addItem(str);

                    //Set protect for reading or writing
                    jComboBox6.addItem(str);

                    //Lock Block for User
                    jComboBox4.addItem(str);

                    //Alarm
                    jComboBox5.addItem(str);

                    //Read Protect
                    jComboBox7.addItem(str);*/

			}

                /*if (i != 0 && i == k){

                    jComboBox2.setSelectedIndex(0);
                    jComboBox3.setSelectedIndex(0);
                    jComboBox6.setSelectedIndex(0);
                    jComboBox4.setSelectedIndex(0);
                    jComboBox5.setSelectedIndex(0);
                    jComboBox7.setSelectedIndex(0);
                }*/
        } // end of else :- Toggle up.
    }
    
	/*
	 * This connects the reader using an ethernet port.
	 * */
	public boolean connectReader() {
		System.out.println("connect reader called...");
        
        /*if ( jRadioButton1.isSelected() ) {
            ConnectMode =   0;
        }else if ( jRadioButton2.isSelected() ) {
            ConnectMode =   1;
        }else {
            return;
        }*/

        int i = 0;
        int[] nBaudRate = new int[1];
        //ReaderAPI.writeLog("", " Starting... ");
        String strHostIp;//host ip
        String strReaderIp;//Readerip
        String strTemp;
        String strComm;

        strHostIp   =   "192.168.0.200";//Host IP
        strTemp     =   "60084";//host port
        m_HostPort  =   Integer.parseInt(strTemp);

        strReaderIp   =   "192.168.0.100";//Reader IP
        strTemp     =   "1969";//Reader port
        m_Port  =   Integer.parseInt(strTemp);

        //strComm = jComboBox_ports.getSelectedItem().toString();

        //iRet = ReaderAPI.Net_ConnectScanner(hScanner, "192.168.0.103", 1969, "192.168.0.71", 5555);
        res = ReaderAPI.Net_ConnectScanner(hScanner, strReaderIp, m_Port, strHostIp, m_HostPort);
        
        /*switch (ConnectMode){
            case 0: //ç½‘å�£
                //iRet = ReaderAPI.Net_ConnectScanner(hScanner, "192.168.0.103", 1969, "192.168.0.71", 5555);
                res = ReaderAPI.Net_ConnectScanner(hScanner, strReaderIp, m_Port, strHostIp, m_HostPort);
                break;
            case 1: //ä¸²å�£
                //res = ReaderAPI.ConnectScanner(hScanner, "COM7", nBaudRate);
                res = ReaderAPI.ConnectScanner(hScanner, strComm, nBaudRate);
                break;
        }*/
        if (res==ReaderAPI._OK){
            
        	short[] HandVer = new short[1];
            short[] SoftVer = new short[1];

            for (i = 0; i < 5; i++)
		{
                    /*//å�–å“ªä¸ªç‰ˆæœ¬
                    switch(ConnectMode)
                    {
                    case 0://ç½‘å�£
*/                        res = ReaderAPI.Net_GetReaderVersion(hScanner[0], HandVer, SoftVer);
                    /*        break;
                    case 1://RS232
                        res = ReaderAPI.GetReaderVersion(hScanner[0], HandVer, SoftVer, Address);
                            break;

                    }
*/
                    if ( res == ReaderAPI._OK )
                    {
                            break;
                    }

		}
		if (res!=ReaderAPI._OK)
		{
			//MessageBox("Connect Reader Fail!(Version)","Warning",MB_ICONWARNING);
			//JOptionPane.showMessageDialog(this, "Connect Reader Fail!(Version)", "Warning", JOptionPane.ERROR_MESSAGE);
			//è°ƒå…³é—­
			System.out.println("Connect Reader Fail!");
			return false;
		}


            byte [] gBasicParam = new byte[32];
            for (i = 0; i < 5; i++)
            {
                //å�–åŸºæœ¬å�‚æ•°
            	res=ReaderAPI.Net_ReadBasicParam(hScanner[0], gBasicParam);
                /*switch(ConnectMode)
                {
                case 0://ç½‘å�£
                    res=ReaderAPI.Net_ReadBasicParam(hScanner[0], gBasicParam);
                        break;
                case 1://RS232
                    res=ReaderAPI.ReadBasicParam(hScanner[0], gBasicParam, Address);
                        break;

                }*/

                if ( res == ReaderAPI._OK )
                {
                        break;
                }


            }
            if (res!=ReaderAPI._OK)
            {

                    //MessageBox("Connect Reader Fail!(BasicParam)","Warring",MB_ICONWARNING);
                    //JOptionPane.showMessageDialog(this, "Connect Reader Fail!(BasicParam)", "Warning", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Connect Reader Fail!");
                    //è°ƒå…³é—­
                    return false;
            }
            
            byte [] gAutoParam = new byte[32];
            for (i = 0; i < 5; i++)
            {
            	res=ReaderAPI.Net_ReadAutoParam(hScanner[0], gAutoParam);
                /*//å�–åŸºæœ¬å�‚æ•°
                switch(ConnectMode)
                {
                case 0://ç½‘å�£
                    res=ReaderAPI.Net_ReadAutoParam(hScanner[0], gAutoParam);
                        break;
                case 1://RS232
                    res=ReaderAPI.ReadAutoParam(hScanner[0], gAutoParam, Address);
                        break;

                }*/

                if ( res == ReaderAPI._OK )
                {
                        iHostPort = (int)(((int)gAutoParam[21])<<8)+(int)gAutoParam[22]; //auto host port
                        break;
                }


            }
            if (res!=ReaderAPI._OK)
            {

                    //MessageBox("Connect Reader Fail!(BasicParam)","Warring",MB_ICONWARNING);
                    //JOptionPane.showMessageDialog(this, "Connect Reader Fail!(BasicParam)", "Warning", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Connect Reader Fail!");
            		//è°ƒå…³é—­
                    return false;
            }
            
            
            
            ifConnectReader=1;
            NewConnect=1;

            // è¿žæŽ¥æŒ‰é’®å’Œæ–­å¼€æŒ‰é’®
            // jButton_connect.setEnabled(false);
            // jButton_disconnect.setEnabled(true);

            //MessageBox("Connect Reader Success!","Notice",MB_ICONINFORMATION);
            //JOptionPane.showMessageDialog(this, "Connect Reader Success!", "Notice", JOptionPane.INFORMATION_MESSAGE);
            //jTabbedPane.setSelectedComponent(jPanel_ISO18000_6C);
            System.out.println("Connect Reader Success!");
            return true;
            //æ›´æ–°å¤–è´­çŠ¶æ€�
            //UpdateStatus();
        
        }else {
            // JOptionPane.showMessageDialog(this, "Connect Reader Fail!", "Warning", JOptionPane.ERROR_MESSAGE);
        	System.out.println("Connect Reader Fail !");
        	return false;
        }
	} // End of method connectReader().
	
	
	/**
	 * SafeInstruments: Class is MyClass. It is non static inner class of PassiveRead. 
	 * 
	 */

	class MyTimer extends Thread
	{
		private boolean endtime = false;
	    private boolean run = false;
	    private int iInterval   =   1000;
	
	    private byte[] ReaderID = new byte[10];
	    @Override
	    public void run()
		{
	        DefaultListModel listModel=null;
	        listModel = new DefaultListModel();
	        for(;;)
	        {
	            System.out.println("ThreadRun!"); //åœ¨è¿™å†™ä½ è¦�è°ƒç”¨çš„æ–¹æ³•
	            try{
	            int res = -1;
	            int be_antenna	=	0;
	            int i,j,k,ID_len=0,ID_len_temp=0;
	            String str = "",str_temp;
	            String strTemp = null;
	            byte[] temp = new byte[64*2];
	            byte[] DB = new byte[128];
	            byte[] IDBuffer = new byte[30*256];
	            int[] nCounter = new int[2];
	
	            boolean bFlag = false;
	            //////////////////////////////////////////////////////////////////////////
	            //add by mqs 20130710 æ–°æ–¹æ³•
	            int itmpAnt	=	0;
	
	            itmpAnt	=	Read_times % iModAnt;
	
	            itmpAnt = gAntenna[itmpAnt];
	
	            Read_times++;
	
	            if ( 1 == iPlatform )//0---626, 1---218
	            {
	                    //218ä¸ºäº†æ��é«˜è¯»æ ‡ç­¾é€Ÿåº¦ï¼Œä¸�ç”¨åˆ‡æ�¢å¤©çº¿ï¼ŒåŠ ä¸€ä¸ªå‘½ä»¤ï¼Œä¸‹é�¢è‡ªåŠ¨åˆ‡æ�¢å¤©çº¿ï¼Œç¼–è¯‘   å�¦åˆ™æ— ï¼Œç”¨å®�æŽ§åˆ¶,add by mqs 20130910
	            }
	            else
	            {
	                    bFlag = true;
	            }
	            if ( bFlag )
	            {
	            		res=ReaderAPI.Net_SetAntenna(hScanner[0], itmpAnt);
	                    /*switch(ConnectMode)
	                    {
	                    case 0://ç½‘å�£
	                        res=ReaderAPI.Net_SetAntenna(hScanner[0], itmpAnt);
	                            break;
	                    case 1://RS232
	                        res=ReaderAPI.SetAntenna(hScanner[0], itmpAnt, Address);
	                            break;
	
	                    }*/
	                    //æœ¬æ�¥å¼€å§‹çš„æ—¶å€™ï¼Œå�ªRS485 sleep ä¸€ä¸‹ã€‚å�¯ç›¸åˆ°218æ“�ä½œR2000æ¨¡å�—ï¼Œæ‰€ä»¥æ”¹æˆ�å¦‚ä¸‹
	                    //if ( 2 == ConnectMode )
	                    if ( 1 == iPlatform )//0---626, 1---218
	                    {
	                            //Sleep(ReadRS485SleepTime);
	                    }
	                    else if ( 2 == ConnectMode )
	                    {
	                            //Sleep(ReadRS485SleepTime);
	                    }
	                    if ( ReaderAPI._OK != res )
	                    {
	                            return;//ç»§ç»­ä¸‹ä¸€è½®å¤©çº¿
	                    }
	            }
	            //////////////////////////////////////////////////////////////////////////
	            switch(nIDEvent) {
	                case 1://list to tag
	                	res=ReaderAPI.Net_EPC1G2_ReadLabelID(hScanner[0],mem,ptr,len,mask,IDBuffer,nCounter);
	/*                    switch(ConnectMode)
	                    {
	                    case 0://ç½‘å�£
	                        res=ReaderAPI.Net_EPC1G2_ReadLabelID(hScanner[0],mem,ptr,len,mask,IDBuffer,nCounter);
	                            break;
	                    case 1://RS232
	                        res=ReaderAPI.EPC1G2_ReadLabelID(hScanner[0],mem,ptr,len,mask,IDBuffer,nCounter, Address);
	                            break;
	
	                    }*/
	                    k = 0; //jTable1.getRowCount();
	                    LinkedList<EpcEntry> result = null;
	                    if (res==ReaderAPI._OK)
	                    {
	                        // 
	                        if ( nCounter[0] > 8 ){
	                            i = nCounter[0];
	                        }
	
	                        for(i=0;i<nCounter[0];i++) {
	                            if (IDBuffer[ID_len]>32)
	                            {
	                                    nCounter[0]=0;
	                                    break;
	                            }
	                            ID_len_temp=IDBuffer[ID_len]*2+1;//1word=16bit
	                            // memcpy(EPCC1G2_IDBuffer[i], &IDBuffer[ID_len], ID_len_temp);
	                            System.arraycopy(IDBuffer, ID_len, EPCC1G2_IDBuffer[i], 0, ID_len_temp);
	                            ID_len+=ID_len_temp;
	                        }
	
	                        // é”‹é¸£å™¨å“�ä¸€ä¸‹ï¼Œçœ‹javaå¦‚ä½•å®žçŽ°ï¼ŒçŽ°åœ¨æ²¡æ—¶é—´æŸ¥
	                        if (nCounter[0]>0){
	                            //MessageBeep(-1);
	                        }
	
	
	                        for(i=0;i<nCounter[0];i++) {

	                            str="";
	                            ID_len=EPCC1G2_IDBuffer[i][0]*2;
	                            System.arraycopy(EPCC1G2_IDBuffer[i], 1, temp, 0, ID_len);
	
	                            //å°†å­—èŠ‚è½¬16è¿›åˆ¶å­—ç¬¦ä¸² 0x31 0x32 ===> "3132"
	                            str = Utility.bytes2HexString(temp, ID_len);

	                            // EPC
	                            for(j=0; j<k; j++)
	                            {
	                                // 
	                                // strTemp = (String) jTable1.getValueAt(j, 4);
	                                strTemp = "";
	                            	ID_len_temp = (int)Integer.parseInt(strTemp, 16);
	                                ID_len_temp *= 2;

	                                if (ID_len == ID_len_temp){
	                                	//strTemp = (String) jTable1.getValueAt(j, 1);
	                                	strTemp = "";
	                                	str_temp=strTemp;
	                                    if(str.equals(str_temp))
	                                    {
	                                            //i = 0;
	                                            break;
	                                    }
	                                }
	                            }
	                            
	                            result = new LinkedList<EpcEntry>();
	                            //DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
	                            //æ–°åŠ ä¸€è¡Œæ•°æ�®åˆ°è¡¨æ ¼ä¸­
	                            if(j == k){
	                                //åº�å�·
	                                String strID = String.format("%d", k+1);//0
	                                String strEPC = str;                    //1
	                                String strSuccess = "1";                //2
	                                String strTimes = "1";                  //3
	                                String strLLen = String.format("%02X", EPCC1G2_IDBuffer[i][0]);//4
	                                
	                                EpcEntry epcEntry = new EpcEntry();
	                                epcEntry.setStrEPC(strEPC);
	                                epcEntry.setStrID(strID);
	                                epcEntry.setStrLLen(strLLen);
	                                epcEntry.setStrSuccess(strSuccess);
	                                epcEntry.setStrTimes(strTimes);
	                                result.add(epcEntry);
	                                
	                                /*model.addRow(new Object[]{
	                                strID,          //0
	                                strEPC,         //1
	                                strSuccess,     //2
	                                strTimes,       //3
	                                strLLen         //4
	                                });*/
	                                //k++;
	                            }
	                            else
	                            {
	                                //å…ˆå�–å‡ºå½“å‰�è¡Œ
	                                //strTemp = (String) jTable1.getValueAt(j, 2);
	                                i = Integer.parseInt(strTemp);
	                                strTemp = String.valueOf(i+1);

	                                //jTable1.setValueAt(strTemp, j, 2);
	
	                            }
	
	                        }
	
	                    }
	                    
	                    // Print logic for Epc Enteries.
	                    if(result!=null) {
		                    for(EpcEntry s : result){
		                    	System.out.println(s.getStrEPC()+"  "+s.getStrID()+"  "+s.getStrLLen()+"  "+s.getStrSuccess()+"  "+s.getStrTimes());
		                    	
		                    	setEnd(true);
		                    	break;
		                    	// Got one epc number.
		                    }
	                    }
	                    /*for(i=0;i<k;i++) {
	                            //m_ListID.SetItemText(i,3,itoa(Read_times,temp,10));
	                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
	                        strTemp = String.valueOf(Read_times);
	                        //jTable1.setValueAt(strTemp, i, 3);
	                    }*/
	
	                    break;
	
	                    // SafeInstruments: For time being blocking these below cases.
	                    
/*	                case 2://å�—è¯»
	                	res=ReaderAPI.Net_EPC1G2_ReadWordBlock(hScanner[0],EPC_Word,IDTemp,mem,ptr,len,DB,AccessPassword);
	                    switch(ConnectMode)
	                    {
	                        case 0://ç½‘å�£
	                            res=ReaderAPI.Net_EPC1G2_ReadWordBlock(hScanner[0],EPC_Word,IDTemp,mem,ptr,len,DB,AccessPassword);
	                            break;
	                        case 1://RS232
	                            res=ReaderAPI.EPC1G2_ReadWordBlock(hScanner[0],EPC_Word,IDTemp,mem,ptr,len,DB,AccessPassword, Address);
	                            break;
	                    }
	                    if (res==ReaderAPI._OK)
	                    {
				str="";
				for(i=0;i<len*2;i++)
				{
					strTemp = String.format("%02X",DB[i]);
					str    += strTemp;
				}
	
	
	
				//m_ListData.AddString(str);
				//m_ListData.SetCurSel(m_ListData.GetCount()-1);
	                    }
	                    else
	                    {
	                        str="Read Fail!";
	                        //m_ListData.AddString(str);
	                    }
	                    //å½“JListçš„é¡¹é›†å�ˆä¸ºç©ºæ—¶
	                    //if(0 == jList1.getModel().getSize())
	                    {
	                        //æ–°å»ºä¸€ä¸ªé»˜è®¤é¡¹é›†å�ˆ
	
	                        //æ“�ä½œè¿™ä¸ªé›†å�ˆ
	                        listModel.add(listModel.getSize(), str);
	                        //å°†è¿™ä¸ªé›†å�ˆæ·»åŠ åˆ°JListä¸­
	                        jList1.setModel(listModel);
	                        //jList1.setSelectedValue(nIDEvent, bFlag);
	
	                        jList1.setSelectedIndex(listModel.getSize()-1);
	                        //int iListS=jList1.getSelectedIndex();
	                        //Rectangle rect=jList1.getCellBounds(iListS, iListS);
	                        //jList1.scrollRectToVisible(rect);
	                    }
	                    //JListçš„é¡¹ä¸�ä¸ºç©ºæ—¶
	                    //else
	                    //{
	                        //ä»ŽJListä¸­èŽ·å¾—è¿™ä¸ªé›†å�ˆ,è½¬æ�¢ä¸ºé»˜è®¤é¡¹é›†å�ˆç±»åž‹
	                    //    DefaultListModel listModel= (DefaultListModel) jList1.getModel();
	                        //è¿½åŠ å…ƒç´ 
	                    //    listModel.add(listModel.getSize(), str);
	                        //å°†è¿™ä¸ªé›†å�ˆæ·»åŠ åˆ°JListä¸­
	                    //    jList1.setModel(listModel);
	                    //}
	
	
	
	
	                    break;*/
	
	               /* case 3:
	                	res=ReaderAPI.Net_EPC1G2_EasAlarm(hScanner[0]);
                        
	                    switch(ConnectMode)
	                    {
	                    case 0://ç½‘å�£
	                        res=ReaderAPI.Net_EPC1G2_EasAlarm(hScanner[0]);
	                            break;
	                    case 1://RS232
	                        res=ReaderAPI.EPC1G2_EasAlarm(hScanner[0], Address);
	                            break;
	
	
	                    }
	                    
	                    String name="icon1.JPG";
	                    ImageIcon icon=new ImageIcon(name);  
	                        
	                    if(res==ReaderAPI._OK)
	                    {
	                            //MessageBeep(-1);
	                            //new ShowImage("").setVisible(true);
	                        
	                        //è¿™ä¸ªæ˜¯å¼ºåˆ¶ç¼©æ”¾åˆ°ä¸Žç»„ä»¶(Label)å¤§å°�ç›¸å�Œ   
	                        //icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), getHeight()-25, Image.SCALE_DEFAULT));  
	                        //è¿™ä¸ªæ˜¯æŒ‰ç­‰æ¯”ç¼©æ”¾   
	                        //              icon=new ImageIcon(icon.getImage().getScaledInstance(reImgWidth, reImgHeight, Image.SCALE_DEFAULT));   
	                        jLabel19.setIcon(icon);  
	                        jLabel19.setHorizontalAlignment(SwingConstants.CENTER);  
	
	                        
	                    }
	
	                    Thread.sleep(500);
	                    
	                    name="icon2.JPG";
	                    icon=new ImageIcon(name);  
	                    //è¿™ä¸ªæ˜¯å¼ºåˆ¶ç¼©æ”¾åˆ°ä¸Žç»„ä»¶(Label)å¤§å°�ç›¸å�Œ   
	                    //icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), getHeight()-25, Image.SCALE_DEFAULT));  
	                    //è¿™ä¸ªæ˜¯æŒ‰ç­‰æ¯”ç¼©æ”¾   
	                    //              icon=new ImageIcon(icon.getImage().getScaledInstance(reImgWidth, reImgHeight, Image.SCALE_DEFAULT));   
	                    jLabel19.setIcon(icon);  
	                    jLabel19.setHorizontalAlignment(SwingConstants.CENTER);
	
	
	                    break;*/
	
	                case 4:
	                    break;
	            }
	            
	
	            ///
	
	            if (endtime){
	                run = false;
	                break;
	            }
	
	
	
	            //only sleep 1s per.
	               Thread.sleep(iInterval);
	            }
	            catch (InterruptedException ex)
	            {
	                System.out.println(ex);
	            }
	
	            run = true;
	
	        } // End of ;; for loop.
	    } // end of run method.
	    
	    public void setEnd(boolean t)
	    {
	        endtime=t;
	    }
	    public void setInterval(int time)
	    {
	        iInterval=time;
	    }
	    public boolean getRun()
	    {
	        return run;
	    }
	
	    private void ShowImage(String ImgFile){
	        //super("ShowImage");
	        Image image =null;
	        try{
	            image=ImageIO.read(new File(ImgFile));
	        } catch(IOException ex){
	        }
	        //JLabel label =new JLabel(new ImageIcon(image));
	        //add(label);
	        //setDefaultCloseOperation(EXIT_ON_CLOSE);
	        //pack();
	    }
} // End of class MyTimer.

/**
 * Class: MyTimerAuto
 * @author gurpreet
 *
 */
/*
class MyTimerAuto extends Thread
{
    private boolean endtime=false;

    
    @Override
    public void run()
    {
        Selector selector = null;  
        try {  
             
            
            if ( !socket.isBound() && !bound ){
                            socket.bind(new InetSocketAddress(iHostPort));//test:7777 
                            bound = true;
                        }

            selector = Selector.open();  
            channel.register(selector, SelectionKey.OP_READ);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

        ByteBuffer byteBuffer = ByteBuffer.allocate(65536);  
        while (true) {  
            try {  
                if (endtime){
                
                break;
                }
                
                int eventsCount = selector.select(1000);  
                if (eventsCount > 0) {  
                    Set selectedKeys = selector.selectedKeys();  
                    Iterator iterator = selectedKeys.iterator();  
                    while (iterator.hasNext()) {  
                        SelectionKey sk = (SelectionKey) iterator.next();  
                        iterator.remove();  
                        if (sk.isReadable()) {
                            DatagramChannel datagramChannel = (DatagramChannel) sk.channel();  
                            SocketAddress sa = datagramChannel.receive(byteBuffer);  
                            byteBuffer.flip();
                            
                            // ByteBuffer � CharBuffer.
                            CharBuffer charBuffer = Charset.defaultCharset().decode(byteBuffer);  
                            System.out.println("Receive message:"+ charBuffer.toString());  
                            
                            iTagNumber += 1; // auto tags
                            jLabelAutoTestTagNumValue.setText(""+iTagNumber);
                            jTextAreaAuto.append(charBuffer.toString());
                            
                            jTextAreaAuto.setCaretPosition(jTextAreaAuto.getText().length());
                            byteBuffer.clear();
                        }  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } 
            
            
            
            
            
            
    
    
        DatagramChannel channel = null;
        DatagramSocket socket = null;
                
        try{
            System.out.println("ThreadRun Start!"); //åœ¨è¿™å†™ä½ è¦�è°ƒç”¨çš„æ–¹æ³•
        
                channel = DatagramChannel.open();
		socket = channel.socket();
                // è®¾ç½®ä¸ºé�žé˜»å¡žæ¨¡å¼�
		channel.configureBlocking(false);
                        
                // è‹¥å¸Œæœ›æ˜¯å…¨å�Œå·¥æ¨¡å¼�ï¼Œåˆ™å�¯åŠ¨ä¸€ä¸ªç›‘å�¬ç«¯å�£ï¼Œæ‰¿æ‹…æœ�åŠ¡å™¨çš„è�Œè´£
			// è‹¥ä¸�èƒ½ç»‘å®šåˆ°æŒ‡å®šç«¯å�£ï¼Œåˆ™æŠ›å‡ºSocketException
			
			InetAddress InHostaddress = InetAddress.getLocalHost();
			SocketAddress saHost = new InetSocketAddress(InHostaddress,
					7777);
                    if ( !socket.isBound()  ){
                            socket.bind(saHost);
                            //bound = true;
                        }
                    
        }catch(IOException ex){
                System.out.println("Run Error!");
            }
                    
        for(;;)
        {

            if (endtime){
                
                break;
            }

            try{
            
                //System.out.println("ThreadRun!"); //åœ¨è¿™å†™ä½ è¦�è°ƒç”¨çš„æ–¹æ³•
                    
                int iRecvLen = 0;
                ByteBuffer byteBuffer = ByteBuffer.allocate(65535);
                byteBuffer.clear();
                SocketAddress sb = channel.receive(byteBuffer);
                
                byteBuffer.flip();
                iRecvLen = byteBuffer.limit();
                System.out.println("\niRecvLen="+iRecvLen);
                
                    
            }catch(IOException ex){
                
            }
            
        }


        //try {  
        //    channel.close();
        //    socket.close();
        //} catch (Exception e) {  
        //    e.printStackTrace();  
        //}  

    }
    
    
    public void setEnd(boolean t)
    {
        endtime=t;
    }
    
 }*/
    

/*class MyTimerSerialAuto extends Thread
{
    private boolean endtime=false;

    
    @Override
    public void run()
    {
        
        int iChar = 0;
        while (true) {  
            try {  
                if (endtime){
                
                break;
                }
                
                iChar = ReaderAPI.GetSerialData();
                if ( 0 != iChar ){
                    //jLabelAutoTestTagNumValue.setText(""+iTagNumber);
                    // æµ‹è¯•ï¼šé€šè¿‡å°†æ”¶åˆ°çš„ByteBufferé¦–å…ˆé€šè¿‡ç¼ºçœ�çš„ç¼–ç �è§£ç �æˆ�CharBuffer å†�è¾“å‡º   
                            //CharBuffer charBuffer = Charset.defaultCharset()  
                            //        .decode() 
                            //System.out.println("receive message:"  
                            //        + charBuffer.toString());  
                    
                    byte[] bytes = new byte[1];
                    bytes[0] = (byte)iChar;
                    if ( 0 == iChar ){
                        
                    }
                    else
                    {
                        if ( 0x0A == iChar ){
                            iTagNumber += 1;//autoä¸‹çš„tagçš„ä¸ªæ•°
                            jLabelAutoTestTagNumValue.setText(""+iTagNumber);
                        }
                        
                        String isoString = new String(bytes, "ISO-8859-1");

                        //String s = String.valueOf( iChar); // å…¶ä¸­ value ä¸ºä»»æ„�ä¸€ç§�æ•°å­—ç±»åž‹ã€‚
                        jTextAreaAuto.append(isoString);
                            
                        jTextAreaAuto.setCaretPosition(jTextAreaAuto.getText().length());
                        
                    }
                    
                }
                        
            } catch (Exception e) {  
                e.printStackTrace();  
            } 
            
        }
    }
    
    
    public void setEnd(boolean t)
    {
        endtime=t;
    }  
 }	*/

}
