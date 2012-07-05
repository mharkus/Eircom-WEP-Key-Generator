package com.mlst.eircomkg;

public class KeyGenerator {

    private final static int NETOPIA_MAC_ADDR = 0x000fcc;
    private final static int NETOPIA_SERIAL_START = 0x01000000;
    private final static String MAGIC_WORD = "Although your world wonders me, ";
    
    private final static String[] NUMBERWORDS = {
	"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"
    };
    
    public static String generateWEPKey(String ssid) throws Exception {
	// make sure there are no spaces in between
	ssid = ssid.replaceAll(" ", "");
	int macAddress = convertToHex(ssid) ^ NETOPIA_MAC_ADDR;
	int serialNumber = macAddress + NETOPIA_SERIAL_START;
	String serialWord = convertSerialNumberToWord(serialNumber);
	String SHA1Value = SHA1Utility.SHA1(serialWord);
	String wepKey = SHA1Value.substring(0, 26);
	return wepKey;
    }
    
    private static int convertToHex(String ssid){
	int dec = Integer.parseInt(ssid, 8);
	return Integer.parseInt(Integer.toHexString(dec), 16);
	
    }
    
    private static String convertSerialNumberToWord(int serialNumber){
	char[] characters = String.valueOf(serialNumber).toCharArray();
	
	StringBuilder builder = new StringBuilder();
	for(char c: characters){
	    builder.append(NUMBERWORDS[Integer.parseInt(c+"")]);
	}
	builder.append(MAGIC_WORD);
	return builder.toString();
    }
}
