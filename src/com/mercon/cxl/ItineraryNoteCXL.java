package com.mercon.cxl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mercon.beans.UserBean;
import com.mercon.helper.SOAPHelper;
import com.mercon.util.DefaultConstants;
import com.mercon.util.Util;

public class ItineraryNoteCXL {

	private static ItineraryNoteCXL itineraryNoteCXL = null;
	
	private UserBean user;
	
	private ItineraryNote itineraryNote;
	
	private Util util = Util.sharedInstance();
	
	public static ItineraryNoteCXL sharedInstance() {
		if (itineraryNoteCXL == null) {
			itineraryNoteCXL = new ItineraryNoteCXL();
		}
		
		return itineraryNoteCXL;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public ItineraryNote getItineraryNote() {
		return itineraryNote;
	}

	public void setItineraryNote(ItineraryNote itineraryNote) {
		this.itineraryNote = itineraryNote;
	}
	
	public int save() {
		StringBuilder sb = new StringBuilder();
		sb.append("<Message messageid=\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "\">");
		sb.append("  <Entity name=\"GEN_NOTE\">");
		sb.append("    <Property name=\"note_type_ind\" value=\"9\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"note_preview\" value=\"" + this.util.sanitizeText(this.itineraryNote.getGenNoteId().getNotePreview()) + "\" type=\"STRING\"/>");
		sb.append("    <Property name=\"tablename\" value=\"" + DefaultConstants.ITINERARY_TABLE_NAME + "\" type=\"STRING\"/>");
		sb.append("    <Property name=\"keyname\" value=\"" + DefaultConstants.ITINERARY_KEY_NAME + "\" type=\"STRING\"/>");
		sb.append("    <Property name=\"keyvalue\" value=\"" + itineraryNote.getGenNoteId().getKeyvalue() + "\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"created_on_dt\" value=\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "\" type=\"DATE\"/>");
		sb.append("    <Property name=\"last_modify_dt\" value=\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "\" type=\"DATE\"/>");
		sb.append("    <Property name=\"note_text\" value=\"" + this.util.convertStringToHex(this.util.sanitizeText(this.itineraryNote.getGenNoteId().getNoteText())) + "\" type=\"STRING\"/>");
		sb.append("  </Entity>");
		sb.append("</Message>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.NOTE_SERVICE);
		soapHelper.setMethod(DefaultConstants.SAVE_NOTE_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			
			String noteNumber = getTagValue(xmlResponse, "note_num");
			
			if (noteNumber != null) {
				return Integer.parseInt(noteNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int update() {
		StringBuilder sb = new StringBuilder();
		sb.append("<Message messageid=\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "\">");
		sb.append("  <Entity name=\"GEN_NOTE\">");
		sb.append("    <Property name=\"note_num\" value=\"" + this.itineraryNote.getGenNoteId().getNoteNum() + "\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"note_sub_num\" value=\"" + this.itineraryNote.getGenNoteId().getNoteSubNum() + "\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"note_type_ind\" value=\"9\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"note_preview\" value=\"" + this.util.sanitizeText(this.itineraryNote.getGenNoteId().getNotePreview()) + "\" type=\"STRING\"/>");
		sb.append("    <Property name=\"tablename\" value=\"" + DefaultConstants.ITINERARY_TABLE_NAME + "\" type=\"STRING\"/>");
		sb.append("    <Property name=\"keyname\" value=\"" + DefaultConstants.ITINERARY_KEY_NAME + "\" type=\"STRING\"/>");
		sb.append("    <Property name=\"keyvalue\" value=\"" + this.itineraryNote.getGenNoteId().getKeyvalue() + "\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"created_on_dt\" value=\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(this.itineraryNote.getGenNoteId().getCreatedOnDt()) + "\" type=\"DATE\"/>");
		sb.append("    <Property name=\"creator_person_num\" value=\"ADMIN AdminUser\" type=\"STRING\"/>");
		sb.append("    <Property name=\"last_modify_dt\" value=\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "\" type=\"DATE\"/>");
		sb.append("    <Property name=\"modify_person_num\" value=\"ADMIN AdminUser\" type=\"STRING\"/>");
		sb.append("    <Property name=\"lock_num\" value=\"" + this.itineraryNote.getGenNoteId().getLockNum() + "\" type=\"INTEGER\"/>");
		sb.append("    <Property name=\"note_text\" value=\"" + this.util.convertStringToHex(this.util.sanitizeText(this.itineraryNote.getGenNoteId().getNoteText())) + "\" type=\"STRING\"/>");
		sb.append("  </Entity>");
		sb.append("</Message>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.NOTE_SERVICE);
		soapHelper.setMethod(DefaultConstants.UPDATE_NOTE_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String noteNumber = getTagValue(xmlResponse, "note_num");
			
			if (noteNumber != null) {
				return Integer.parseInt(noteNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private String getTagValue(String xmlResponse, String tagName) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		Document document = null;
    	try {
			document = documentBuilder.parse(new ByteArrayInputStream(xmlResponse.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (SAXException saxe) {
			saxe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
    	NodeList nodeList = document.getElementsByTagName("*");
    	for (int i = 0; i <= nodeList.getLength(); i++) {
    		Element element = (Element) nodeList.item(i);
    		
    		if (element.hasAttributes()) {
    			if (tagName.equals(element.getAttribute("name"))) {
    				return element.getAttribute("value");
    			}
    		}
    	}
    	
		return null;
	}
	
}
