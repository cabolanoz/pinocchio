package com.mercon.auth;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.mercon.helper.SOAPHelper;
import com.mercon.util.DefaultConstants;
import com.mercon.util.Util;

public class UserAuth implements DefaultConstants, Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -9130459642432511118L;

	private Util util = new Util();
	
	private String usersession;
	
	public UserAuth() { }
	
	public boolean authenticate(String username, String password) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<Message messageid='0'>");
		sb.append("	<Entity name='REF_LOGIN'>");
	    sb.append("		<Property name='login_cd' value='" + username + "' type='string' />");
	    sb.append("		<Property name='password' value='" + password + "' type='string' />");
	    sb.append("		<Property name='machine_name' value='" + util.getMachineName() + "' type='string' />");
	    sb.append("	</Entity>");
	    sb.append("</Message>");

	    try {
	    	SOAPHelper soapHelper = new SOAPHelper();
	    	soapHelper.setService(DefaultConstants.LOGIN_SERVICE);
	    	soapHelper.setMethod(DefaultConstants.AUTHENTICATE_METHOD);
	    	
	    	String xmlResponse = soapHelper.sendRequest(sb.toString(), false, "");
	    	
	    	// Set the current session obtained after user success authentication
	    	buildUsersession(xmlResponse);
	    	
	    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    	Document document = documentBuilder.parse(new ByteArrayInputStream(xmlResponse.getBytes("UTF-8")));
	    	
	    	Node root = document.getDocumentElement();
	    	
	    	if (root.getNodeName().equals("MessageResponse")) {
	    		NamedNodeMap namedNodeMap = root.getAttributes();
	    		
	    		if (namedNodeMap != null && namedNodeMap.getLength() > 0) {
		    		for (int i = 0; i < namedNodeMap.getLength(); i++) {
		    			Node item = namedNodeMap.item(i);
		    			if (item.getNodeName().equals("status") && item.getNodeValue().equals("SUCCESS")) {
		    				return true;
		    			}
		    		}
		    	}
	    	}
	    } catch (Exception e) {
	    	throw new Exception(e.getMessage(), e.getCause());
	    }
	    
		return false;
	}

	private void buildUsersession(String xmlResponse) throws Exception {
		if (!xmlResponse.equals("")) {
			org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(xmlResponse);
			org.dom4j.Node node = document.selectSingleNode("//MessageResponse");

			if (node.valueOf("@status").compareToIgnoreCase("SUCCESS") == 0) {
				this.setUsersession(node.valueOf("@sessionid"));
			}
	    }
	}
	
	public String getUsersession() {
		return usersession;
	}

	public void setUsersession(String usersession) {
		this.usersession = usersession;
	}
	
}
