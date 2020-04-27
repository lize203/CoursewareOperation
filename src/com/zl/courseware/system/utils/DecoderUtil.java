package com.zl.courseware.system.utils;

import java.nio.charset.Charset;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  byte 计算
 * @author z
 *
 */
public class DecoderUtil
{
	 /** 
     * BASE64解密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String decryptBASE64(String key) throws Exception {  
         return new String((new BASE64Decoder()).decodeBuffer(key))+" ";  
     }  
   
     /** 
      * BASE64加密 
       *   BASE64的加密解密是双向的，可以求反解。
     * @param key 
      * @return 
      * @throws Exception 
     */  
    public static String encryptBASE64(String str) throws Exception {  
    	byte[] key = str.getBytes();
        return (new BASE64Encoder()).encodeBuffer(key)+" ";  
     }
    private static final Charset charset = Charset.forName("UTF-8");
    
    
    public static void main(String[] args) throws Exception
    {
//	    String encryption = encryptBASE64("/cwfiles/你111.zip"); 
//	    System.out.println(encryption.replace(" ", ""));
//	    String decryption = decryptBASE64(encryption); 
//	    System.out.println(decryption);
    	
    	
    	String binary = stringToAscii("/cwfiles/你111.zip");
    	System.out.println(binary);
    	
    	System.out.println("-----------------------------------------------");
    	String binStrToChar = asciiToString(binary);
    	System.out.println(binStrToChar);

    	
    }
    
    
    /**
     * convert String to Ascii
     * @author z
     * @param str
     * @return
     */
    public static String stringToAscii(String str)
    {
    	StringBuffer sb = new StringBuffer();
    	char[] charArray = str.toCharArray();
    	for (int i = 0; i < charArray.length; i++)
        {
	        if(i != charArray.length-1)
	        {
	        	sb.append((int)charArray[i]).append(",");
	        }else{
	        	sb.append((int)charArray[i]);
	        }
        }
    	return sb.toString();
    }
    /**
     * convert Ascii to String
     * @author z
     * @param asciiStr  ascii - type-  string
     * @return
     */
    public static String asciiToString(String asciiStr){

    	StringBuffer sb = new StringBuffer();
    	String[] split = asciiStr.split(",");
    	for (int i = 0; i < split.length; i++)
        {
	        sb.append((char)Integer.parseInt(split[i]));
        }
    	return sb.toString();
    }
    
}
