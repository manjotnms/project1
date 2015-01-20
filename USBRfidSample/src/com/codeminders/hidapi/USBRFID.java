package com.codeminders.hidapi;

import com.codeminders.hidapi.*;

import java.io.IOException;

/**
 * proof of concept:
 * - download javahidapi into subdirectory "javahidapi" and compile it
 * - compile using "javac -cp ./javahidapi/bin PenTest"
 * - run using "java -cp ./javahidapi/bin:. PenTest"
 **/

public class USBRFID {
	static HIDManager mgr = null;
	static HIDDevice dev = null;
    private static final long READ_UPDATE_DELAY_MS = 200L;
    static {
        System.loadLibrary("hidapi-jni-64");
        ClassPathLibraryLoader.loadNativeHIDLibrary();//add this line
    }

    static final int VENDOR_ID = 1155;//0x0e20;
    static final int PRODUCT_ID = 22352;//0x0101;
        
    /**
     * @param args input strings value.
     */
    public static void main(String[] args) throws Exception {
    	
        mgr = new HIDManager() {
            public void deviceAdded(HIDDeviceInfo devInfo) {}
            public void deviceRemoved(HIDDeviceInfo devInfo) {}
        };
        dev = mgr.openById(VENDOR_ID, PRODUCT_ID, null);
        dev.disableBlocking();
        //byte ad=1;
        //System.out.println("setOperationMode((byte)1,(byte)1):"+setOperationMode(ad,ad));
        // Send a Feature Report to the device
        /*        byte buf[] = new byte[10];
        int res=0;
    	buf[0] = 0x2; // First byte is report number
    	buf[1] = (byte) 0xa0;
    	buf[2] = 0x0a;
    	res = dev.sendFeatureReport(buf);
    	//res = hid_send_feature_report(handle, buf, 17);

    	// Read a Feature Report from the device
    	buf[0] = 0x2;
    	res = dev.getFeatureReport(buf);
    	//res = hid_get_feature_report(handle, buf, sizeof(buf));

    	// Print out the returned buffer.
    	System.out.println("Feature Report\n   ");
    	for (int i = 0; i < res; i++)
    		System.out.printf("%02hhx ", buf[i]);

    	// Set the hid_read() function to be non-blocking.
    	dev.disableBlocking();
    	//hid_set_nonblocking(handle, 1);

    	// Send an Output report to toggle the LED (cmd 0x80)
    	buf[0] = 1; // First byte is report number
    	buf[1] = (byte)0x80;
    	res = dev.write(buf);
    	//res = hid_write(handle, buf, 65);

    	// Send an Output report to request the state (cmd 0x81)
    	buf[1] = (byte) 0x81;
    	res = dev.write(buf);
    	//hid_write(handle, buf, 65);

    	// Read requested state
    	res = dev.read(buf);
    	//res = hid_read(handle, buf, 65);
        
        
        System.out.println(versionRequest());
        while(true) {
        	//byte aaa[] = versionRead();
        	System.out.println(buf);
        	//Thread.sleep(1000);
        }*/
        //dev.enableBlocking();
        //dev.write(new byte[] {0x02,0x01,(byte)0x95});
        //dev.write(new byte[] {0,6,'H'});
        System.out.println(versionRequest());
        Thread.sleep(READ_UPDATE_DELAY_MS);
        System.out.println(dev);
        //int n = 0;
        byte[] buf = new byte[11];
    	while(true) {
        	//int n = dev.read(buf);
        	//int n = dev.readTimeout(buf,1000);
        	//System.out.println("--[]--");
        	/*for (int i = 0; i < n; i++) {
              System.out.println("---"+buf[i]);
            }*/
        	//System.out.printf("%02hhx ", versionRead());
        	System.out.println(packageRead());
        	
        	//Thread.sleep(1000);
            //System.out.println("Reading finished...");
            //System.exit(0);
        }
    }
    
	public static int versionRequest() throws InterruptedException {
		int n = 0;
		/*byte mask [] = new byte[512];
		byte len = (byte) (mask.length + 5);
		byte command = (byte) 0xEE;
		byte fixed_Starting = (byte) 0x40;
		int mem = 1;
		byte[] temp = { (byte) 0x00, fixed_Starting, len, command, 
						 (byte) mem, (byte) mask.length }; //, (byte) 0x00, (byte) 0x00 };
		// add the mask.
		int count = 0;
		for (int i = temp.length; i < (mask.length + temp.length) ; i++)
		{
			temp[i] = mask[count];
			count++;
		}
		// add the checksum
		temp[temp.length] = (byte) 0xB8;
		*/
		/*byte[] temp = new byte[] { (byte)0x04, (byte)0x00, (byte)0x33, (byte)0x01, 
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 };*/
		/*			    new byte[] { (epc number, (byte)0x80, (byte)0x33, (byte)0x01, 
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 };*/
		// 00  40H  m+6  EEH
		// memaddr  LEN  Mask  CheckSum
		
		//byte[] temp = new byte[] { (byte)0x00, (byte)0x40, (byte)0x02, (byte)0x06, (byte)0xB8 };		
		byte[] temp = new byte[] {0x00,0x02,0x01,(byte)0x95};
		try{
			n = dev.write(temp);
		} catch(IOException e){
			e.printStackTrace();
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}
	
	public static byte[] versionRead() throws InterruptedException {
		byte[] buf = new byte[11];
		try{
			dev.read(buf);
		} catch(IOException e){
			return buf;
		}
		return buf;
	}

	public static int setOperationMode(byte LED, byte Mode) throws InterruptedException{
		if(( LED > 2 || LED < 0 ) || ( Mode > 2 || Mode < 0 )){
			return 0;
		}
		int n = 0;
		try {
			System.out.println("write started");
			n = dev.write("1".getBytes());
			System.out.println("write done..."+n);
		} catch(IOException e){
			e.printStackTrace();
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public int setScaleAndOrientation(byte Scale, byte Orientation) throws InterruptedException{
		if(( Scale > 9 || Scale < 0 ) || ( Orientation > 2 || Orientation < 0 )){
			return 0;
		}
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x04,(byte)0x80,(byte)0xB6,Scale,Orientation});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public int eraseMemory() throws InterruptedException{
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x01,(byte)0xB0});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public int requestUpload() throws InterruptedException{
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x01,(byte)0xB5});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public byte[] numberOfPackagesRead() throws InterruptedException{
		byte[] buf = new byte[9];
		try{
			dev.read(buf);
		} catch(IOException e){
			return buf;
		}
		return buf;
	}

	public int acknowledgePackage() throws InterruptedException{
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x01,(byte)0xB6});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public int nacknowledgePackage() throws InterruptedException{
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x01,(byte)0xB7});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public static byte[] packageRead() throws InterruptedException{
		byte[] buf = new byte[64];
		try{
			dev.read(buf);
			
		} catch(IOException e){
			return buf;
		}
		return buf;
	}

}
