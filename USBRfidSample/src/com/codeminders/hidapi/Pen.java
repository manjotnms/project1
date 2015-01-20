package com.codeminders.hidapi;

import com.codeminders.hidapi.*;
import java.io.IOException;
import java.lang.InterruptedException;

class Pen{
	private static final long READ_UPDATE_DELAY_MS = 200L;
	static {System.loadLibrary("hidapi-jni");}
	private static final int VENDOR_ID = 0x0e20;
	private static final int PRODUCT_ID = 0x0101;
	HIDManager mgr;
	HIDDevice dev;

	public Pen() throws Exception {
		mgr = new HIDManager() {
			public void deviceAdded(HIDDeviceInfo devInfo) {}
			public void deviceRemoved(HIDDeviceInfo devInfo) {}
		};
		dev = mgr.openById(VENDOR_ID, PRODUCT_ID, null);
		dev.disableBlocking();
	}

	public int versionRequest() throws InterruptedException{
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x01,(byte)0x95});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}

	public byte[] versionRead() throws InterruptedException{
		byte[] buf = new byte[11];
		try{
			dev.read(buf);
		} catch(IOException e){
			return buf;
		}
		return buf;
	}

	public int setOperationMode(byte LED, byte Mode) throws InterruptedException{
		if(( LED > 2 || LED < 0 ) || ( Mode > 2 || Mode < 0 )){
			return 0;
		}
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x04,(byte)0x80,(byte)0xB5,LED,Mode});
		} catch(IOException e){
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

	public byte[] packageRead() throws InterruptedException{
		byte[] buf = new byte[64];
		try{
			dev.read(buf);
		} catch(IOException e){
			return buf;
		}
		return buf;
	}

	public int lostPackage(byte high, byte low) throws InterruptedException{
		int n = 0;
		try{
			n = dev.write(new byte[] {0x02,0x03,(byte)0xB7, high, low});
		} catch(IOException e){
			return 0;
		}
		Thread.sleep(READ_UPDATE_DELAY_MS);
		return n;
	}
}