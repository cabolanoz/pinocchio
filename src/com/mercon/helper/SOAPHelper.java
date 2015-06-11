package com.mercon.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.mercon.util.Util;

public class SOAPHelper {

	private static SOAPHelper soapHelper = null;
	
	private Util util = Util.sharedInstance();
	
	public String service = "";
	public String method  = "";
	
	public static SOAPHelper sharedInstance() {
		if (soapHelper == null) {
			soapHelper = new SOAPHelper();
		}
		
		return soapHelper;
	}

	 public String sendRequest(String xmlRequest, boolean hasSession, String session) throws Exception {
		 String xmlResponse = null;
		 
		 try {
			 SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
			 SOAPConnection connection = connectionFactory.createConnection();

			 URL endPoint = new URL(hasSession ? this.util.getServiceURI(session) : this.util.getServiceURI());
		
			 SOAPMessage message = this.createMessage("msgstr", xmlRequest, false);
			 
			 
			 String filename = "soaprequest_" + (new SimpleDateFormat("yyyyMMddHmmss")).format(new Date()) + ".xml";

			 File myFile = new File(filename);
			 if (!myFile.exists()) {
				 myFile.createNewFile();
			 }

			 FileOutputStream output = new FileOutputStream(myFile, false);
			 message.writeTo(output);
			 
			 SOAPMessage response = connection.call(message, endPoint);
		  
			 xmlResponse = this.getXMLFromResponse(response);
		 } catch (Exception e) {
			 throw new Exception(e.getMessage(), e.getCause());
		 }
		 
		 return xmlResponse;
	 }
	 
	 private SOAPMessage createMessage(String nodeName, String xml, boolean response) throws Exception {
		 MessageFactory msgFactory = MessageFactory.newInstance();
		 
		 SOAPMessage message = msgFactory.createMessage();
		 
		 SOAPPart part = message.getSOAPPart();
		 
		 SOAPEnvelope envelope = part.getEnvelope();
		 envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
		 envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");

		 SOAPBody body = envelope.getBody();

		 SOAPElement element1 = body.addChildElement(envelope.createName("ns1:" + method + (response ? "Response" : "")));
		 element1.addAttribute(envelope.createName("xmlns:ns1"), "urn:" + service);
		 element1.addAttribute(envelope.createName("SOAP-ENV:encodingStyle"), "http://schemas.xmlsoap.org/soap/encoding/");

		 SOAPElement element2 = element1.addChildElement(envelope.createName(nodeName));
		 element2.addAttribute(envelope.createName("xsi:type"), "xsd:string");
		 element2.addTextNode(xml);

		 if (!response) {
			 message.saveChanges();
		 }

		 return message;
	 }
	 
	 private String getXMLFromResponse(SOAPMessage message) throws Exception {
		 String xmlResponse = null;
		 
		 try {
			 SOAPPart part = message.getSOAPPart();
			 SOAPEnvelope envelope = part.getEnvelope();
			 SOAPElement element = null;
			 SOAPBody body = envelope.getBody();

			 if (body.hasFault()) {
				 throw new Exception(body.getFault().getFaultString());
			 } else {
				 Iterator<?> iterator = body.getChildElements();
				 
				 while (iterator.hasNext()) {
					 element = (SOAPElement) iterator.next();

					 Name name = element.getElementName();
					 
					 if (name.getQualifiedName().startsWith("ns1")) {
						 break;
					 }
				 }

				 iterator = element.getChildElements(envelope.createName("return"));
				 
				 while (iterator.hasNext()) {
					 element = (SOAPElement) iterator.next();

					 xmlResponse = element.getValue();

					 break;
				 }
			 }
		 } catch (Exception e) {
			 throw new Exception(e.getMessage(), e.getCause());
		 }

		 return xmlResponse;
	 }

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	 
}
