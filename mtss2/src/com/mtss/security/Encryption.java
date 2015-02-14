package com.mtss.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Encryption {
	
	private static Log log = LogFactory.getLog(Encryption.class.getName());
	public static String getEncryptedPass(String pass) {
	    try {
	    	
	    	log.error("*****Encryption(getEncryptedPass) :Get Encrpted Password***** ");
	        MessageDigest digest = MessageDigest.getInstance("MD5");      
	        return inHexDecimal(digest.digest(pass.getBytes("UTF-8")));
	    } catch (NoSuchAlgorithmException ex) {
	    	log.error("*****Encryption(getEncryptedPass) :***** Error "+ex);
	        throw new RuntimeException("No MD5 implementation? Really?");
	    } catch (UnsupportedEncodingException ex) {
	    	log.error("*****Encryption(getEncryptedPass) :***** Error "+ex);
	        throw new RuntimeException("No UTF-8 encoding? Really?");
	    }
	}
	
	private static String inHexDecimal(byte pass[]){
		StringBuffer sb = new StringBuffer();
        for(int i=0;i<pass.length;i++){
            sb.append(Integer.toHexString(0xff & pass[i]));
        }
        log.error("*****Encryption(inHexDecimal) :Get Encrpted Password *****");
        return sb.toString();
	}
}
