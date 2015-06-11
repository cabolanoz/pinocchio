package com.mercon.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Util implements DefaultConstants {

	private static Util util = null;
	
	private String session = "";
	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	private String preffix = "7b5c727466315c616e73695c616e7369637067313235325c64656666305c6465666c616e67313033337b5c666f6e7474626c7b5c66305c666e696c5c666368617273657430205461686f6d613b7d7b5c66315c666e696c205461686f6d613b7d7d0d0a5c766965776b696e64345c7563315c706172645c66305c6673313620";
	private String suffix = "5c6631200d0a5c706172207d";
	
	public static Util sharedInstance() {
		if (util == null) {
			util = new Util();
		}
		
		return util;
	}
	
	public String getServiceURI() {
		return (IS_PRODUCTION ? PRODUCTION_CXL_HOST : DEVELOPER_CXL_HOST) + "/RPCRouterServletSession" + this.session;
	}
	
	public String getServiceURI(String session) {
		return (IS_PRODUCTION ? PRODUCTION_CXL_HOST : DEVELOPER_CXL_HOST) + "/RPCRouterServletSession" + session;
	}
	
	public String getMachineName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException uhe) {
			return "?";
		}
	}

	public String sanitizeText(String text) {
		text = text.replace("<p>", "");
		text = text.replace("</p>", "");
		text = text.replace("<span>", "");
		text = text.replace("</span>", "");
		text = text.replace("<span style=\"font-size: 10pt;\">", "");
		text = text.replace("<br />", " ");
		text = text.replace("&", "&amp;");
		text = text.replace("\"", "&quot;");
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace("\r", " ");
		text = text.replace("%", "/");
		text = text.replace("&lt;br&gt;------------------------------&lt;br&gt;", "");
		text = text.replace("<br>------------------------------<br>", "");
		
		return text;
	}
	
	public String convertStringToHex(String text) {
		byte[] buf = text.getBytes();
		
		char[] chars = new char[2 * buf.length];
		
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		
		return (preffix.toUpperCase() + "" + new String(chars).toUpperCase() + "" + suffix.toUpperCase());
	}
	
}
