package com.utils;

public class BytesUtil {
	 /**  
	    * byte数组中取short数值，本方法适用于(低位在后，高位在前)的顺序。 
	    */  
	public static short bytesToShort(byte[] bytes, int offset) {  
	    return (short)(((bytes[offset+2] & 0xFF)<<8)  
	            |(bytes[offset+3] & 0xFF));  
	}
	/**  
	 * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。 
	 */  
	public static int bytesToInt(byte[] bytes, int offset) {  
		return ((bytes[offset] & 0xFF)<<24)  
				|((bytes[offset+1] & 0xFF)<<16)  
				|((bytes[offset+2] & 0xFF)<<8)  
				|(bytes[offset+3] & 0xFF);  
	}
	/**  
	 * byte数组中取long数值，本方法适用于(低位在后，高位在前)的顺序。 
	 */  
	public static long bytesToLong(byte[] bytes, int offset) {  
		return ((bytes[offset] & 0xFFl)<<56)  
				|((bytes[offset+1] & 0xFF)<<48)  
				|((bytes[offset+2] & 0xFF)<<40)  
				|((bytes[offset+3] & 0xFF)<<32)
				|((bytes[offset+3] & 0xFF)<<24)
				|((bytes[offset+1] & 0xFF)<<16)  
				|((bytes[offset+2] & 0xFF)<<8)  
				|(bytes[offset+3] & 0xFF);
	}
}
