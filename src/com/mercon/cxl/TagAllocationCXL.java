package com.mercon.cxl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mercon.beans.UserBean;
import com.mercon.helper.SOAPHelper;
import com.mercon.util.DefaultConstants;


public class TagAllocationCXL {

	private static TagAllocationCXL instance;
	
	private UserBean user;
	
	private TagAllocation tagAllocation;
	
	public static TagAllocationCXL sharedInstance() {
		if (instance == null) {
			instance = new TagAllocationCXL();
		}
		
		return instance;
	}
	
	public String save() {
		String lockInfo = this.getBuildDrawCargoDetailLockInfo();
		
		if (!lockInfo.equals("")) {
			this.tagAllocation.setLockInfo(lockInfo);
			
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append("  <mhead name='TPTReqPutBuildDrawMatch'>");
			sb.append("    <sender>{A439F040-79FF-4E60-AA62-E9C57813DB5E}</sender>");
			sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append("  </mhead>");
			sb.append("  <mbody>");
			sb.append("    <data>");
			sb.append("      <PutBuildDrawMatch>");
			sb.append("        <OPS_BUILD_DRAW_MATCH table='1'>");
			sb.append("          <bd_match_num><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getBd_match_num() + "]]></bd_match_num>");
			sb.append("          <equipment_num><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getEquipment_num() + "]]></equipment_num>");
			sb.append("          <cargo_num><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getCargo_num() + "]]></cargo_num>");
			sb.append("          <strategy_num><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getStrategy_num() + "]]></strategy_num>");
			sb.append("          <match_qty><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getMatch_qty() + "]]></match_qty>");
			sb.append("          <bd_allocation_ind><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getBd_allocation_ind() + "]]></bd_allocation_ind>");
			sb.append("          <in_store_transfer_num><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getIn_store_transfer_num() + "]]></in_store_transfer_num>");
			sb.append("          <copy_tag_ind><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getCopy_tag_ind()+"]]></copy_tag_ind>");
			
			//Iterate with build and draws. For build add tag informations
	        sb.append("			<OPS_BUILD_DRAW_MATCH_DETAIL table='2'>");
	        sb.append("				<bd_match_num><![CDATA[" + this.tagAllocation.getBuildDrawMatchDetail().getBd_match_num() + "]]></bd_match_num>");
	        sb.append("				<build_draw_num><![CDATA[" + this.tagAllocation.getBuildDrawMatchDetail().getBuild_draw_num() + "]]></build_draw_num>");
	        sb.append("				<equipment_num><![CDATA[" + this.tagAllocation.getBuildDrawMatchDetail().getEquipment_num() + "]]></equipment_num>");
	        sb.append("				<strategy_num><![CDATA[" + this.tagAllocation.getBuildDrawMatchDetail().getStrategy_num() + "]]></strategy_num>");
	        sb.append("				<build_draw_match_qty><![CDATA[" + this.tagAllocation.getBuildDrawMatchDetail().getBuild_draw_match_qty() + "]]></build_draw_match_qty>");
	        sb.append("				<title_transfer_dt><![CDATA[" + new SimpleDateFormat("yyyyMMddHHmmss").format(this.tagAllocation.getBuildDrawMatchDetail().getTitle_transfer_dt()) + "]]></title_transfer_dt>");
	        sb.append("			</OPS_BUILD_DRAW_MATCH_DETAIL>");
	        
	        //Iterate with tags information
	        for (BuildDrawMatchDetail m : this.tagAllocation.getBuildDrawMatchDetailLst()) {
	        	 sb.append("			<OPS_BUILD_DRAW_MATCH_DETAIL table='2'>");
	             sb.append("				<bd_match_num><![CDATA[" + m.getBd_match_num() + "]]></bd_match_num>");
	             sb.append("				<build_draw_num><![CDATA[" + m.getBuild_draw_num() + "]]></build_draw_num>");
	             sb.append("				<equipment_num><![CDATA[" + m.getEquipment_num() + "]]></equipment_num>");
	             sb.append("				<strategy_num><![CDATA[" + m.getStrategy_num() + "]]></strategy_num>");
	             sb.append("				<build_draw_match_qty><![CDATA[" + m.getBuild_draw_match_qty() + "]]></build_draw_match_qty>");
	             sb.append("				<title_transfer_dt><![CDATA[" + new SimpleDateFormat("yyyyMMddHHmmss").format(m.getTitle_transfer_dt()) + "]]></title_transfer_dt>");

	             for(BuildDrawTag tag : m.getTags()) {
		             sb.append("				<OPS_BUILD_DRAW_TAG table='3'>");
		             sb.append("					<build_draw_num><![CDATA[" + tag.getBuild_draw_num() + "]]></build_draw_num>");
		             sb.append("					<tag_type_cd><![CDATA[Chop Data]]></tag_type_cd>");
		             if (tag.getRef_bd_tag_num() != 0 && tag.getRef_bd_tag_num() != null) {
		            	 sb.append("					<ref_bd_tag_num><![CDATA[" + tag.getRef_bd_tag_num() + "]]></ref_bd_tag_num>");
		             }
		             sb.append("					<bd_tag_num><![CDATA[" + tag.getBd_tag_num() + "]]></bd_tag_num>");
		             sb.append("					<cargo_num><![CDATA[" + tag.getCargo_num() + "]]></cargo_num>");
		             sb.append("					<equipment_num><![CDATA[" + tag.getEquipment_num() + "]]></equipment_num>");
		             sb.append("					<tag_type_ind><![CDATA[" + tag.getTag_type_ind() + "]]></tag_type_ind>");
		             if (tag.getTag_value1() != null && tag.getTag_value1() != "") {
		            	 sb.append("					<tag_value1><![CDATA[" + tag.getTag_value1() + "]]></tag_value1>");
		             }
		             if (tag.getTag_value2() != null && tag.getTag_value2() != "") {
		            	 sb.append("					<tag_value2><![CDATA[" + tag.getTag_value2() + "]]></tag_value2>");
		             }
		             if (tag.getTag_value3() != null && tag.getTag_value3() != "") {
		            	 sb.append("					<tag_value3><![CDATA[" + tag.getTag_value3() + "]]></tag_value3>");
		             }
		             if (tag.getTag_value4() != null && tag.getTag_value4() != "") {
		            	 sb.append("					<tag_value4><![CDATA[" + tag.getTag_value4() + "]]></tag_value4>");
		             }
		             if (tag.getTag_value5() != null && tag.getTag_value5() != "") {
		            	 sb.append("					<tag_value5><![CDATA[" + tag.getTag_value5() + "]]></tag_value5>");
		             }
		             if (tag.getTag_value6() != null && tag.getTag_value6() != "") {
		            	 sb.append("					<tag_value6><![CDATA[" + tag.getTag_value6() + "]]></tag_value6>");
		             }
		             if (tag.getTag_value7() != null && tag.getTag_value7() != "") {
		            	 sb.append("					<tag_value7><![CDATA[" + tag.getTag_value7() + "]]></tag_value7>");
		             }
		             if (tag.getTag_value8() != null && tag.getTag_value8() != ""){
		            	 sb.append("					<tag_value8><![CDATA[" + tag.getTag_value8() + "]]></tag_value8>");
		             }
		             if (tag.getQty_to_allocate() != null && tag.getQty_to_allocate() != 0) {
		            	 sb.append("                    <persist_ind><![CDATA[1]]></persist_ind>");
		            	 sb.append("					<qty_to_alloc><![CDATA[" + tag.getQty_to_allocate() + "]]></qty_to_alloc>");
		             } else {
		            	 sb.append("					<tag_alloc_qty><![CDATA[" + tag.getTag_alloc_qty() + "]]></tag_alloc_qty>");
		             }
		             sb.append("					<tag_qty><![CDATA[" + tag.getTag_qty() + "]]></tag_qty>");
		             sb.append("					<tag_qty_uom_cd><![CDATA[" + tag.getTag_qty_uom_cd() + "]]></tag_qty_uom_cd>");
		             sb.append("					<tag_source_ind><![CDATA[" + tag.getTag_source_ind() + "]]></tag_source_ind>");
		             if (tag.getLot_certificate_num() != null && tag.getLot_certificate_num() != 0) {
		            	 sb.append("					<lot_certificate_num><![CDATA[" + tag.getLot_certificate_num() + "]]></lot_certificate_num>");
		             }
		             if (tag.getTag_loss_gain_adj_qty() != null && tag.getTag_loss_gain_adj_qty() != 0) {
		            	 sb.append("					<tag_loss_gain_adj_qty><![CDATA[" + tag.getTag_loss_gain_adj_qty() + "]]></tag_loss_gain_adj_qty>");
		             }
		             if (tag.getGl_ref_bd_tag_num() != null && tag.getGl_ref_bd_tag_num() != 0) {
		            	 sb.append("					<gl_ref_bd_tag_num><![CDATA[" + tag.getGl_ref_bd_tag_num() + "]]></gl_ref_bd_tag_num>");
		             }
		             if (tag.getSplit_src_tag_num() != null && tag.getSplit_src_tag_num() != 0) {
		            	 sb.append("					<split_src_tag_num><![CDATA[" + tag.getSplit_src_tag_num() + "]]></split_src_tag_num>");
		             }
		             if (tag.getAdj_ref_build_draw_num() != null && tag.getAdj_ref_build_draw_num() != 0) {
		            	 sb.append("					<adj_ref_build_draw_num><![CDATA[" + tag.getAdj_ref_build_draw_num() + "]]></adj_ref_build_draw_num>");
		             }
		             sb.append("					<build_draw_type_ind><![CDATA[" + tag.getBuild_draw_type_ind() + "]]></build_draw_type_ind>");
		             sb.append("					<build_draw_ind><![CDATA[" + tag.getBuild_draw_ind() + "]]></build_draw_ind>");
		             sb.append("					<chop_id><![CDATA[" + tag.getChop_id() + "]]></chop_id>");
		             sb.append("					<tag_adj_qty><![CDATA[" + tag.getTag_adj_qty() + "]]></tag_adj_qty>");
		             sb.append("				</OPS_BUILD_DRAW_TAG>");
	             }
	             sb.append("			</OPS_BUILD_DRAW_MATCH_DETAIL>");
	        }
	        
	        sb.append("		 </OPS_BUILD_DRAW_MATCH>");
	        sb.append("				<LockNumList>");
	        sb.append("					<lockinfo><![CDATA[" + this.tagAllocation.getLockInfo() + "]]></lockinfo>");
	        sb.append("				</LockNumList>");
	        sb.append("      </PutBuildDrawMatch>");
	        sb.append("    </data>");
	        sb.append("  </mbody>");
	        sb.append("</request>");
	        
//	        System.out.println(sb.toString());
	        
	        SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.BUILD_DRAW_SERVICE);
			soapHelper.setMethod(DefaultConstants.SAVE_BUILD_DRAW_METHOD);
			
			try {
				String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
				
				return xmlResponse;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}

	private String getBuildDrawCargoDetailLockInfo() {
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqGetBuildDrawCargoDetails'>");
		sb.append("    <sender>{A439F040-79FF-4E60-AA62-E9C57813DB5E}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <GetBuildDrawCargoDetails>");
		sb.append("        <cargo_num><![CDATA[" + this.tagAllocation.getBuildDrawMatch().getCargo_num() + "]]></cargo_num>");
		sb.append("        <in_store_transfer_num><![CDATA[-1]]></in_store_transfer_num>");
		sb.append("      </GetBuildDrawCargoDetails>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.CARGO_SERVICE);
		soapHelper.setMethod(DefaultConstants.GET_BUILD_DRAW_CARGO_DETAIL_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String lockinfo = getTagValue(xmlResponse, "lockinfo");
			
			return lockinfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
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
		
    	NodeList nodeList = document.getElementsByTagName(tagName);
    	Element element = (Element) nodeList.item(0);
    	Node node = element.getFirstChild();
    	
	    if (node instanceof CharacterData) {
	    	CharacterData cd = (CharacterData) node;
	    	return cd.getData();
	    } 
    	
		return null;
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public TagAllocation getTagAllocation() {
		return tagAllocation;
	}

	public void setTagAllocation(TagAllocation tagAllocation) {
		this.tagAllocation = tagAllocation;
	}
	
}
