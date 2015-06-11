package com.mercon.cxl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mercon.entities.VdimCargoId;
import com.mercon.entities.VdimMovementId;
import com.mercon.helper.SOAPHelper;
import com.mercon.util.DefaultConstants;

public class ItineraryCXL {

	private static ItineraryCXL itineraryCXL = null;
	
	private UserBean user;
	
	private Itinerary itinerary;
	
	public static ItineraryCXL sharedInstance() {
		if (itineraryCXL == null) {
			itineraryCXL = new ItineraryCXL();
		}
		
		return itineraryCXL;
	}
	
	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	public int save() {
		int ascCounter = 1;
		int descCounter = this.itinerary.getMovementIds().size();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("	<mhead name='TPTReqPutItinerary'>");
		sb.append("		<sender>{B2B6C882-6E9F-47DA-8F68-CE382A9165F1}</sender>");
		sb.append("		<sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("	</mhead>");
		sb.append("	<mbody>");
		sb.append("		<data>");
		sb.append("			<PutItinerary>");
		sb.append("				<OPS_ITINERARY table='1'>");
		sb.append("					<itinerary_num><![CDATA[-1]]></itinerary_num>");
		sb.append("					<itinerary_name><![CDATA[" + this.itinerary.getItineraryName() + "]]></itinerary_name>");
		sb.append("					<operator_person_num><![CDATA[" + this.itinerary.getPersonId().getNumperson() + "]]></operator_person_num>");
		sb.append("					<itinerary_status_ind><![CDATA[0]]></itinerary_status_ind>");
		sb.append("					<status_ind><![CDATA[1]]></status_ind>");
		sb.append("					<internal_company_num><![CDATA[" + this.itinerary.getCompanyId().getNumCompany() + "]]></internal_company_num>");
		
		for (VdimMovementId movementId : this.itinerary.getMovementIds()) {
			sb.append("					<OPS_EQUIPMENT table='2'>");
			sb.append("						<equipment_num><![CDATA[-1]]></equipment_num>");
			sb.append("						<itinerary_num><![CDATA[-1]]></itinerary_num>");
			sb.append("						<mot_num><![CDATA[" +  movementId.getMotNumId().getMotNum() + "]]></mot_num>");
			sb.append("						<status_ind><![CDATA[1]]></status_ind>");
			sb.append("						<OPS_MOVEMENT table='3'>");
			sb.append("							<key><![CDATA[" + descCounter + "]]></key>");
			sb.append("							<sequence_num><![CDATA[" + ascCounter + "]]></sequence_num>");
			sb.append("							<movement_num><![CDATA[-" + descCounter + "]]></movement_num>");
			sb.append("							<equipment_num><![CDATA[-1]]></equipment_num>");
			sb.append("							<mot_type_ind><![CDATA[" + movementId.getMotTypeInd().getIndValue() + "]]></mot_type_ind>");
			sb.append("							<mot_num><![CDATA[" + movementId.getMotNumId().getMotNum() + "]]></mot_num>");
			sb.append("							<depart_location_num><![CDATA[" + movementId.getDepartLocationNum().getNumLocation() + "]]></depart_location_num>");
			sb.append("							<depart_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(movementId.getDepartDt()) + "000000]]></depart_dt>");
			sb.append("							<duration><![CDATA[" + ((movementId.getArriveDt().getTime() - movementId.getDepartDt().getTime()) / (1000 * 60 * 60 * 24)) + "]]></duration>");
			sb.append("							<arrive_location_num><![CDATA[" + movementId.getArriveLocationNum().getNumLocation() + "]]></arrive_location_num>");
			sb.append("							<arrive_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(movementId.getArriveDt()) + "000000]]></arrive_dt>");
			sb.append("							<movement_status_ind><![CDATA[" + movementId.getMovementStatusInd().getIndValue() + "]]></movement_status_ind>");
			sb.append("							<straight_line_distance><![CDATA[0]]></straight_line_distance>");
			sb.append("							<movement_desc><![CDATA[-" + descCounter + ": " + movementId.getMotTypeInd().getIndValueName() + " " + movementId.getMotNumId().getMotCd() + " (" + movementId.getDepartLocationNum().getNameLocation() + " to " + movementId.getArriveLocationNum().getNameLocation() + ")]]></movement_desc>");
			sb.append("							<color_source><![CDATA[14675437]]></color_source>");
			
			if (movementId.getActionCompletionDt() != null) {
				sb.append("<OPS_ITI_DEFAULT_ACTION_DETAIL table='5'>");
	            sb.append("    <Key><![CDATA[1]]></Key>");
	            sb.append("    <movement_num><![CDATA[-" + descCounter + "]]></movement_num>");
	            sb.append("    <action_seq_num><![CDATA[1]]></action_seq_num>");
	            sb.append("    <itinerary_num><![CDATA[-1]]></itinerary_num>");
	            sb.append("    <action_priority><![CDATA[1]]></action_priority>");
	            sb.append("    <user_person_num><![CDATA[" + this.itinerary.getPersonId().getNumperson() + "]]></user_person_num>");
	            sb.append("    <action_description><![CDATA[SI-]]></action_description>");
	            sb.append("    <action_due_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(movementId.getActionCompletionDt()) + "000000]]></action_due_dt>");
	            sb.append("    <action_completion_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(movementId.getActionCompletionDt()) + "000000]]></action_completion_dt>");
	            sb.append("    <action_completed_ind><![CDATA[1]]></action_completed_ind>");
	            sb.append("    <alert_delivery_ind><![CDATA[0]]></alert_delivery_ind>");
	            sb.append("    <notification_type_ind><![CDATA[0]]></notification_type_ind>");
	            sb.append("</OPS_ITI_DEFAULT_ACTION_DETAIL>");
			}
			
			sb.append("						</OPS_MOVEMENT>");
			sb.append("					</OPS_EQUIPMENT>");
			
			ascCounter++;
			descCounter--;
		}

		sb.append("				</OPS_ITINERARY>");
		sb.append("			</PutItinerary>");
		sb.append("		</data>");
		sb.append("	</mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
		soapHelper.setMethod(DefaultConstants.SAVE_ITINERARY_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String itineraryNumber = getTagValue(xmlResponse, "itinerary_num");
			
			if (itineraryNumber != null) {
				return Integer.parseInt(itineraryNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	public List<Integer> saveCargo(int itineraryNumber) {
		for (VdimMovementId movementId : this.itinerary.getMovementIds()) {
			String xmlItineraryResponse = getXMLResponseByItinerary(itineraryNumber);
			
			int equipmentNumber = Integer.parseInt(getTagValue(xmlItineraryResponse, "OPS_ITINERARY", "OPS_EQUIPMENT", "equipment_num", "mot_num", String.valueOf(movementId.getMotNumId().getMotNum())));
			
			if (equipmentNumber != -1) {
				List<Integer> cargosNumber = new ArrayList<Integer>();
			
				for (VdimCargoId cargoId : movementId.getCargoLst()) {
					StringBuilder sb = new StringBuilder();
					sb.append("<?xml version='1.0'?>");
					sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
					sb.append("  <mhead name='TPTReqPutCargo'>");
					sb.append("    <sender>TriplePointClient</sender>");
					sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
					sb.append("  </mhead>");
					sb.append("  <mbody>");
					sb.append("    <data>");
					sb.append("      <OPS_CARGO>");
					sb.append("        <equipment_num>" + equipmentNumber + "</equipment_num>");
					sb.append("        <cargo_name><![CDATA[" + cargoId.getNameCargo() + "]]></cargo_name>");
					sb.append("        <strategy_num>" + cargoId.getNumStrategy().getNumStrategy() + "</strategy_num>");
					sb.append("        <cmdty_cd><![CDATA[" + cargoId.getCommodityCod().getCodCommodity() + "]]></cmdty_cd>");
					sb.append("        <qty_uom_cd><![CDATA[" + cargoId.getQtyUomCod().getCodUom() + "]]></qty_uom_cd>");
					sb.append("        <price_curr_cd><![CDATA[" + cargoId.getCurrPriceCod().getCodcurr() + "]]></price_curr_cd>");
					sb.append("        <price_uom_cd><![CDATA[" + cargoId.getPrcUomCod().getCodUom() + "]]></price_uom_cd>");
					sb.append("        <valuation_type_ind>2</valuation_type_ind>");
					sb.append("        <mtm_fwd_curve_num>" + cargoId.getMtmCurveNum().getNumdefquote() + "</mtm_fwd_curve_num>");
					sb.append("        <storage_mtm_ind>0</storage_mtm_ind>");
					sb.append("        <capacity>" + cargoId.getCapacity() + "</capacity>");
					sb.append("        <status_ind>1</status_ind>");
					sb.append("        <commingled_ind>0</commingled_ind>");
					sb.append("        <language_cd><![CDATA[en]]></language_cd>");
					sb.append("        <is_cargo_console>1</is_cargo_console>");
					sb.append("        <internal_company_num>" + cargoId.getNumCompany() + "</internal_company_num>");
					sb.append("      </OPS_CARGO>");
					sb.append("    </data>");
					sb.append("  </mbody>");
					sb.append("</request>");
					
					SOAPHelper soapHelper = SOAPHelper.sharedInstance();
					soapHelper.setService(DefaultConstants.CARGO_SERVICE);
					soapHelper.setMethod(DefaultConstants.SAVE_CARGO_METHOD);
					
					try {
						String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
						String cargoNumber = getTagValue(xmlResponse, "cargo_num");
						
						if (cargoNumber != null) {
							cargosNumber.add(Integer.parseInt(cargoNumber));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				return cargosNumber;
			}
		}
		
		return new ArrayList<Integer>();
	}
	
	private String getXMLResponseByItinerary(int itineraryNumber) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqGetItinerary'>");
		sb.append("    <sender>{E10DAB01-64D7-4775-8313-7FE57B80439C}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <GetItinerary>");
		sb.append("        <itinerary_num><![CDATA[" + itineraryNumber + "]]></itinerary_num>");
		sb.append("      </GetItinerary>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
		soapHelper.setMethod(DefaultConstants.GET_ITINERARY_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			
			return xmlResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public int update() {
		if (this.itinerary.getItineraryNumber() > 0) {
			String lockinfo = getItineraryLockInfoByItinerary();
			
			if (!lockinfo.equals("")) {
				int ascCounter = 1;
				int descCounter = this.itinerary.getMovementIds().size();
				
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version='1.0'?>");
				sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
				sb.append("  <mhead name='TPTReqPutItinerary'>");
				sb.append("    <sender>{A439F040-79FF-4E60-AA62-E9C57813DB5E}</sender>");
				sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
				sb.append("  </mhead>");
				sb.append("  <mbody>");
				sb.append("    <data>");
				sb.append("      <PutItinerary>");
				sb.append("        <OPS_ITINERARY table='1'>");
				sb.append("          <itinerary_num><![CDATA[" + this.itinerary.getItineraryNumber() + "]]></itinerary_num>");
				sb.append("          <itinerary_name><![CDATA[" + this.itinerary.getItineraryName() + "]]></itinerary_name>");
				sb.append("          <operator_person_num><![CDATA[" + this.itinerary.getPersonId().getNumperson() + "]]></operator_person_num>");
				sb.append("          <itinerary_status_ind><![CDATA[" + this.itinerary.getItineraryStatusInd() + "]]></itinerary_status_ind>");
				sb.append("          <modify_person_num><![CDATA[" + this.itinerary.getModifyPersonId() + "]]></modify_person_num>");
				sb.append("          <create_dt><![CDATA[" + new SimpleDateFormat("yyyyMMddHHmmss").format(this.itinerary.getCreateDt()) + "]]></create_dt>");
				sb.append("          <last_modify_dt><![CDATA[" + new SimpleDateFormat("yyyyMMddHHmmss").format(this.itinerary.getModifyDt()) + "]]></last_modify_dt>");
				sb.append("          <status_ind><![CDATA[" + this.itinerary.getStatusInd() + "]]></status_ind>");
				sb.append("          <internal_company_num><![CDATA[" + this.itinerary.getCompanyId().getNumCompany() + "]]></internal_company_num>");
				
				for (VdimMovementId dimMovementId : this.itinerary.getMovementIds()) {
			          sb.append("<OPS_EQUIPMENT table='2'>");
			          if (dimMovementId.getMovementNum() > 0) {
			        	  sb.append("  <equipment_num><![CDATA[" + dimMovementId.getEquipmentNum() + "]]></equipment_num>");
			          } else {
			        	  sb.append("  <equipment_num><![CDATA[-" + descCounter + "]]></equipment_num>");
			          }
			          
			          sb.append("  <itinerary_num><![CDATA[" + this.itinerary.getItineraryNumber() + "]]></itinerary_num>");
			          sb.append("  <mot_num><![CDATA[" + dimMovementId.getMotNumId().getMotNum() + "]]></mot_num>");
			          sb.append("  <status_ind><![CDATA[1]]></status_ind>");
			          sb.append("  <OPS_MOVEMENT table='3'>");
			          
			          if (dimMovementId.getMovementNum() > 0) {
			        	  sb.append("    <key><![CDATA[" + ascCounter + "]]></key>");
				          sb.append("    <sequence_num><![CDATA[" + ascCounter + "]]></sequence_num>");
				          sb.append("    <itinerary_num><![CDATA[" + this.itinerary.getItineraryNumber() + "]]></itinerary_num>");
				          sb.append("    <movement_num><![CDATA[" + dimMovementId.getMovementNum() + "]]></movement_num>");
				          sb.append("    <equipment_num><![CDATA[" + dimMovementId.getEquipmentNum() + "]]></equipment_num>");
				          sb.append("    <mot_type_ind><![CDATA[" + dimMovementId.getMotTypeInd().getIndValue() + "]]></mot_type_ind>");
				          sb.append("    <mot_num><![CDATA[" + dimMovementId.getMotNumId().getMotNum() + "]]></mot_num>");
				          sb.append("    <depart_location_num><![CDATA[" + dimMovementId.getDepartLocationNum().getNumLocation() + "]]></depart_location_num>");
				          sb.append("    <depart_port_comp_status_ind><![CDATA[" + dimMovementId.getDepartPortCompStatusInd() + "]]></depart_port_comp_status_ind>");
				          sb.append("    <depart_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(dimMovementId.getDepartDt()) + "000000]]></depart_dt>");
				          sb.append("    <duration><![CDATA[" + ((dimMovementId.getArriveDt().getTime() - dimMovementId.getDepartDt().getTime()) / (1000 * 60 * 60 * 24)) + "]]></duration>");
				          sb.append("    <arrive_location_num><![CDATA[" + dimMovementId.getArriveLocationNum().getNumLocation() + "]]></arrive_location_num>");
				          sb.append("    <arrive_port_comp_status_ind><![CDATA[" + dimMovementId.getArrivePortCompStatusInd() + "]]></arrive_port_comp_status_ind>");
				          sb.append("    <arrive_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(dimMovementId.getArriveDt()) + "000000]]></arrive_dt>");
				          sb.append("    <movement_status_ind><![CDATA[" + dimMovementId.getMovementStatusInd().getIndValue() + "]]></movement_status_ind>");
				          sb.append("    <straight_line_distance><![CDATA[0]]></straight_line_distance>");
				          sb.append("    <movement_desc><![CDATA[" + dimMovementId.getMovementNum() + ": " + dimMovementId.getMotTypeInd().getIndValueName() + " " + dimMovementId.getMotNumId().getMotCd() + " (" + dimMovementId.getDepartLocationNum().getNameLocation() + " to " + dimMovementId.getArriveLocationNum().getNameLocation() + ")]]></movement_desc>");
				          sb.append("    <color_source><![CDATA[15592429]]></color_source>");
				          sb.append("    <transfer_ind><![CDATA[1]]></transfer_ind>");
				          sb.append("    <depart_port_mis_approv_ind><![CDATA[" + dimMovementId.getDepartPortMisApprovInd() + "]]></depart_port_mis_approv_ind>");
				          sb.append("    <arrive_port_mis_approv_ind><![CDATA[" + dimMovementId.getArrivePortMisApprovInd() + "]]></arrive_port_mis_approv_ind>");
			          } else {
			        	  sb.append("	 <key><![CDATA[" + descCounter + "]]></key>");
				          sb.append("	 <sequence_num><![CDATA[" + descCounter + "]]></sequence_num>");
				          sb.append("	 <movement_num><![CDATA[-" + ascCounter + "]]></movement_num>");
				          sb.append("	 <equipment_num><![CDATA[-" + descCounter + "]]></equipment_num>");
				          sb.append("	 <mot_type_ind><![CDATA[" + dimMovementId.getMotTypeInd().getIndValue() + "]]></mot_type_ind>");
				          sb.append("	 <mot_num><![CDATA[" + dimMovementId.getMotNumId().getMotNum() + "]]></mot_num>");
				          sb.append("	 <depart_location_num><![CDATA[" + dimMovementId.getDepartLocationNum().getNumLocation() + "]]></depart_location_num>");
				          sb.append("	 <depart_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(dimMovementId.getDepartDt()) + "000000]]></depart_dt>");
				          sb.append("	 <duration><![CDATA[" + ((dimMovementId.getArriveDt().getTime() - dimMovementId.getDepartDt().getTime()) / (1000 * 60 * 60 * 24)) + "]]></duration>");
				          sb.append("	 <arrive_location_num><![CDATA[" + dimMovementId.getArriveLocationNum().getNumLocation() + "]]></arrive_location_num>");
				          sb.append("	 <arrive_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(dimMovementId.getArriveDt()) + "000000]]></arrive_dt>");
				          sb.append("	 <movement_status_ind><![CDATA[" + dimMovementId.getMovementStatusInd().getIndValue() + "]]></movement_status_ind>");
				          sb.append("	 <straight_line_distance><![CDATA[0]]></straight_line_distance>");
				          sb.append("	 <movement_desc><![CDATA[-" + ascCounter + ": " + dimMovementId.getMotTypeInd().getIndValueName() + " " + dimMovementId.getMotNumId().getMotCd() + " (" + dimMovementId.getDepartLocationNum().getNameLocation() + " to " + dimMovementId.getArriveLocationNum().getNameLocation() + ")]]></movement_desc>");
				          sb.append("	 <color_source><![CDATA[14675437]]></color_source>");
			          }
			          
			          if (dimMovementId.getActionCompletionDt() != null) {
			              sb.append("<OPS_ITI_DEFAULT_ACTION_DETAIL table='5'>");
			              sb.append("  <Key><![CDATA[1]]></Key>");
			              
			              if (dimMovementId.getActionDetailNum() > 0) {
			            	  sb.append("  <action_detail_num><![CDATA[" + dimMovementId.getActionDetailNum() + "]]></action_detail_num>");
			              }
			              
			              if (dimMovementId.getMovementNum() > 0) {
			            	  sb.append("  <movement_num><![CDATA[" + dimMovementId.getMovementNum() + "]]></movement_num>");
			              } else {
			            	  sb.append("  <movement_num><![CDATA[-" + descCounter + "]]></movement_num>");
			              }
			              
			              sb.append("  <action_seq_num><![CDATA[1]]></action_seq_num>");
			              sb.append("  <itinerary_num><![CDATA[" + this.itinerary.getItineraryNumber() + "]]></itinerary_num>");
			              sb.append("  <action_priority><![CDATA[1]]></action_priority>");
			              sb.append("  <user_person_num><![CDATA[" + this.itinerary.getPersonId().getNumperson() + "]]></user_person_num>");
			              sb.append("  <action_description><![CDATA[SI-]]></action_description>");
			              sb.append("  <action_due_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(dimMovementId.getActionCompletionDt()) + "000000]]></action_due_dt>");
			              sb.append("  <action_completion_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(dimMovementId.getActionCompletionDt()) + "000000]]></action_completion_dt>");
			              sb.append("  <action_completed_ind><![CDATA[1]]></action_completed_ind>");
			              sb.append("  <alert_delivery_ind><![CDATA[0]]></alert_delivery_ind>");
			              sb.append("  <notification_type_ind><![CDATA[0]]></notification_type_ind>");
			              sb.append("</OPS_ITI_DEFAULT_ACTION_DETAIL>");
			          }
			          
			          sb.append("  </OPS_MOVEMENT>");
			          sb.append("</OPS_EQUIPMENT>");
			          
			          ascCounter++;
			          descCounter--;
				}
				
				sb.append("        </OPS_ITINERARY>");
				sb.append("        <LockNumList>");
				sb.append("          <lockinfo>" + lockinfo + "</lockinfo>");
				sb.append("        </LockNumList>");
				sb.append("      </PutItinerary>");
				sb.append("    </data>");
				sb.append("  </mbody>");
				sb.append("</request>");

				SOAPHelper soapHelper = SOAPHelper.sharedInstance();
				soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
				soapHelper.setMethod(DefaultConstants.SAVE_ITINERARY_METHOD);

				try {
					String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
					String itineraryNumber = getTagValue(xmlResponse, "itinerary_num");
					
					if (itineraryNumber != null) {
						return Integer.parseInt(itineraryNumber);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return -1;
	}
	
	private String getItineraryLockInfoByItinerary() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqGetItinerary'>");
		sb.append("    <sender>{E10DAB01-64D7-4775-8313-7FE57B80439C}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <GetItinerary>");
		sb.append("        <itinerary_num><![CDATA[" + this.itinerary.getItineraryNumber() + "]]></itinerary_num>");
		sb.append("      </GetItinerary>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
		soapHelper.setMethod(DefaultConstants.GET_ITINERARY_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String lockinfo = getTagValue(xmlResponse, "lockinfo");
			
			return lockinfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public List<Integer> updateCargo(int itineraryNumber) {
		if (this.itinerary.getItineraryNumber() > 0) {
			List<Integer> cargosNumber = new ArrayList<Integer>();
			
			String xmlItineraryResponse = getXMLResponseByItinerary(itineraryNumber);
			
			for (VdimMovementId movementId : this.itinerary.getMovementIds()) {
				String equipmentResponseNumber = getTagValue(xmlItineraryResponse, "OPS_ITINERARY", "OPS_EQUIPMENT", "equipment_num", "mot_num", String.valueOf(movementId.getMotNumId().getMotNum()));
				
				if (equipmentResponseNumber != null) {
					int equipmentNumber = Integer.parseInt(equipmentResponseNumber);
					
					if (equipmentNumber > 0) {
						for (VdimCargoId cargoId : movementId.getCargoLst()) {
							if (cargoId.getNumCargo() > 0) {
								String lockinfo = getCargoLockInfoByEquipment(equipmentNumber);
								
								if (!lockinfo.equals("")) {
									StringBuilder sb = new StringBuilder();
									sb.append("<?xml version='1.0'?>");
									sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
									sb.append("  <mhead name='TPTReqPutCargo'>");
									sb.append("    <sender>TriplePointClient</sender>");
									sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
									sb.append("  </mhead>");
									sb.append("  <mbody>");
									sb.append("    <data>");
									sb.append("      <OPS_CARGO>");
									sb.append("        <equipment_num>" + equipmentNumber + "</equipment_num>");
									sb.append("        <cargo_num>" + cargoId.getNumCargo() + "</cargo_num>");
									sb.append("        <cargo_name><![CDATA[" + cargoId.getNameCargo() + "]]></cargo_name>");
									sb.append("        <strategy_num>" + cargoId.getNumStrategy().getNumStrategy() + "</strategy_num>");
									sb.append("        <cmdty_cd><![CDATA[" + cargoId.getCommodityCod().getCodCommodity() + "]]></cmdty_cd>");
									sb.append("        <qty_uom_cd><![CDATA[" + cargoId.getQtyUomCod().getCodUom() + "]]></qty_uom_cd>");
									sb.append("        <price_curr_cd><![CDATA[" + cargoId.getCurrPriceCod().getCodcurr() + "]]></price_curr_cd>");
									sb.append("        <price_uom_cd><![CDATA[" + cargoId.getPrcUomCod().getCodUom() + "]]></price_uom_cd>");
									sb.append("        <valuation_type_ind>2</valuation_type_ind>");
									sb.append("        <mtm_fwd_curve_num>" + cargoId.getMtmCurveNum().getNumdefquote() + "</mtm_fwd_curve_num>");
									sb.append("        <currentqty>0</currentqty>");
									sb.append("        <storage_mtm_ind>0</storage_mtm_ind>");
									sb.append("        <capacity>" + cargoId.getCapacity() + "</capacity>");
									sb.append("        <status_ind>" + cargoId.getStatusInd() + "</status_ind>");
									sb.append("        <last_modify_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(cargoId.getDtLastModify()) + "</last_modify_dt>");
									sb.append("        <modify_person_num><![CDATA[" + cargoId.getNumLastModifyPerson() + "]]></modify_person_num>");
									sb.append("        <commingled_ind>0</commingled_ind>");
									sb.append("        <language_cd><![CDATA[en]]></language_cd>");
									sb.append("        <customs_duty_status_ind>-1</customs_duty_status_ind>");
									sb.append("        <cargo_locked_ind>0</cargo_locked_ind>");
									sb.append("        <cmdty_certified_ind>0</cmdty_certified_ind>");
									sb.append("        <is_cargo_console>1</is_cargo_console>");
									sb.append("        <internal_company_num>" + cargoId.getNumCompany() + "</internal_company_num>");
									sb.append("        <locknumlist><![CDATA[" + lockinfo + "]]></locknumlist>");
									sb.append("      </OPS_CARGO>");
									sb.append("    </data>");
									sb.append("  </mbody>");
									sb.append("</request>");
									
									SOAPHelper soapHelper = SOAPHelper.sharedInstance();
									soapHelper.setService(DefaultConstants.CARGO_SERVICE);
									soapHelper.setMethod(DefaultConstants.SAVE_CARGO_METHOD);
									
									try {
										String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
										String cargoNumber = getTagValue(xmlResponse, "cargo_num");
										
										if (cargoNumber != null) {
											cargosNumber.add(Integer.parseInt(cargoNumber));
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							} else {
								StringBuilder sb = new StringBuilder();
								sb.append("<?xml version='1.0'?>");
								sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
								sb.append("  <mhead name='TPTReqPutCargo'>");
								sb.append("    <sender>TriplePointClient</sender>");
								sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
								sb.append("  </mhead>");
								sb.append("  <mbody>");
								sb.append("    <data>");
								sb.append("      <OPS_CARGO>");
								sb.append("        <equipment_num>" + equipmentNumber + "</equipment_num>");
								sb.append("        <cargo_name><![CDATA[" + cargoId.getNameCargo() + "]]></cargo_name>");
								sb.append("        <strategy_num>" + cargoId.getNumStrategy().getNumStrategy() + "</strategy_num>");
								sb.append("        <cmdty_cd><![CDATA[" + cargoId.getCommodityCod().getCodCommodity() + "]]></cmdty_cd>");
								sb.append("        <qty_uom_cd><![CDATA[" + cargoId.getQtyUomCod().getCodUom() + "]]></qty_uom_cd>");
								sb.append("        <price_curr_cd><![CDATA[" + cargoId.getCurrPriceCod().getCodcurr() + "]]></price_curr_cd>");
								sb.append("        <price_uom_cd><![CDATA[" + cargoId.getPrcUomCod().getCodUom() + "]]></price_uom_cd>");
								sb.append("        <valuation_type_ind>2</valuation_type_ind>");
								sb.append("        <mtm_fwd_curve_num>" + cargoId.getMtmCurveNum().getNumdefquote() + "</mtm_fwd_curve_num>");
								sb.append("        <storage_mtm_ind>0</storage_mtm_ind>");
								sb.append("        <capacity>" + cargoId.getCapacity() + "</capacity>");
								sb.append("        <status_ind>1</status_ind>");
								sb.append("        <commingled_ind>0</commingled_ind>");
								sb.append("        <language_cd><![CDATA[en]]></language_cd>");
								sb.append("        <is_cargo_console>1</is_cargo_console>");
								sb.append("        <internal_company_num>" + cargoId.getNumCompany() + "</internal_company_num>");
								sb.append("      </OPS_CARGO>");
								sb.append("    </data>");
								sb.append("  </mbody>");
								sb.append("</request>");
								
								SOAPHelper soapHelper = SOAPHelper.sharedInstance();
								soapHelper.setService(DefaultConstants.CARGO_SERVICE);
								soapHelper.setMethod(DefaultConstants.SAVE_CARGO_METHOD);
								
								try {
									String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
									String cargoNumber = getTagValue(xmlResponse, "cargo_num");
									
									if (cargoNumber != null) {
										cargosNumber.add(Integer.parseInt(cargoNumber));
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			
			return cargosNumber;
		}
		
		return null;
	}
	
	private String getCargoLockInfoByEquipment(int equipmentNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqGetCargos'>");
		sb.append("    <sender>{1C5F3BA4-E173-4FDE-A185-84FEC3402149}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <GetCargos>");
		sb.append("        <equipment_num><![CDATA[" + equipmentNumber + "]]></equipment_num>");
		sb.append("      </GetCargos>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.CARGO_SERVICE);
		soapHelper.setMethod(DefaultConstants.GET_CARGO_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String lockinfo = getTagValue(xmlResponse, "LockNumList");
			
			return lockinfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public List<Integer> getMovementByItinerary(int itineraryNumber) {
		List<Integer> movementsNumber = new ArrayList<Integer>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqGetItinerary'>");
		sb.append("    <sender>{E10DAB01-64D7-4775-8313-7FE57B80439C}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <GetItinerary>");
		sb.append("        <itinerary_num><![CDATA[" + itineraryNumber + "]]></itinerary_num>");
		sb.append("      </GetItinerary>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
		soapHelper.setMethod(DefaultConstants.GET_ITINERARY_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			
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
	    	
	    	NodeList nodeList = document.getElementsByTagName("movement_num");
	    	
	    	for (int i = 0; i < nodeList.getLength(); i++) {
	    		Element element = (Element) nodeList.item(i);
	    		Node node = element.getFirstChild();
	    		
	    		if (!movementsNumber.contains(Integer.parseInt(node.getTextContent()))) {
	    			movementsNumber.add(Integer.parseInt(node.getTextContent()));
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return movementsNumber;
	}
	
	public boolean removeMovement() {
		if (this.itinerary.getItineraryNumber() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append("  <mhead name='TPTReqCanDeleteMovement'>");
			sb.append("    <sender>{C4E8E451-5E68-4B5A-9B1F-D265864A75DF}</sender>");
			sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append("  </mhead>");
			sb.append("  <mbody>");
			sb.append("    <data>");
			sb.append("      <CanDeleteMovement>");
			sb.append("        <itinerary_num><![CDATA[" + this.itinerary.getItineraryNumber() + "]]></itinerary_num>");
			sb.append("        <mot_num><![CDATA[" + this.itinerary.getMovementIds().get(0).getMotNumId().getMotNum() + "]]></mot_num>");
			sb.append("        <movement_num><![CDATA[" + this.itinerary.getMovementIds().get(0).getMovementNum() + "]]></movement_num>");
			sb.append("        <depart_location_num><![CDATA[" + this.itinerary.getMovementIds().get(0).getDepartLocationNum().getNumLocation() + "]]></depart_location_num>");
			sb.append("        <arrive_location_num><![CDATA[" + this.itinerary.getMovementIds().get(0).getArriveLocationNum().getNumLocation() + "]]></arrive_location_num>");
			sb.append("      </CanDeleteMovement>");
			sb.append("    </data>");
			sb.append("  </mbody>");
			sb.append("</request>");
			
			SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
			soapHelper.setMethod(DefaultConstants.DELETE_MOVEMENT_METHOD);
			
			try {
				String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
				String candelete = getTagValue(xmlResponse, "canDelete");
				
				if ("true".equals(candelete)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public boolean removeCargo() {
		if (this.itinerary.getCargoId() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append("  <mhead name='TPTReqCancelCargo'>");
			sb.append("    <sender>{9A4EBBEE-F7A7-4945-AE44-2A84BBCE9E84}</sender>");
			sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append("  </mhead>");
			sb.append("  <mbody>");
			sb.append("    <data>");
			sb.append("      <CancelCargo>");
			sb.append("        <cargo_num><![CDATA[" + this.itinerary.getCargoId().getNumCargo() + "]]></cargo_num>");
			sb.append("      </CancelCargo>");
			sb.append("    </data>");
			sb.append("  </mbody>");
			sb.append("</request>");
			
			SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.ITINERARY_SERVICE);
			soapHelper.setMethod(DefaultConstants.DELETE_CARGO_METHOD);
			
			try {
				String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
				String cargoNumber = getTagValue(xmlResponse, "cargo_num");
				
				if (cargoNumber.equals(String.valueOf(this.itinerary.getCargoId().getNumCargo()))) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
//	public String getTransferLockInfoByEquipment(int equipmentNumber) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<?xml version='1.0'?>");
//		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
//		sb.append("  <mhead name='TPTReqGetTransfers'>");
//		sb.append("    <sender>{0FDC9C90-8248-4976-AAB7-53F1B0921565}</sender>");
//		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
//		sb.append("  </mhead>");
//		sb.append("  <mbody>");
//		sb.append("    <data>");
//		sb.append("      <GetTransfers>");
//		sb.append("        <equipment_num><![CDATA[" + equipmentNumber +"]]></equipment_num>");
//		sb.append("      </GetTransfers>");
//		sb.append("    </data>");
//		sb.append("  </mbody>");
//		sb.append("</request>");
//		
//		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
//		soapHelper.setService(DefaultConstants.CARGO_SERVICE);
//		soapHelper.setMethod(DefaultConstants.GET_CARGO_METHOD);
//		
//		try {
//			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
//			String lockinfo = getTagValue(xmlResponse, "LockNumList");
//			
//			return lockinfo;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return "";
//	}
	
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
	
	private String getTagValue(String xmlResponse, String headerTagName, String parentTagName, String tagName, String findTagName, String findValue) {
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
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	NodeList nodeList = document.getElementsByTagName(headerTagName);
    	if (nodeList != null && nodeList.getLength() > 0) {
    		String retTagValue = "";
    		
    		NodeList parentList = nodeList.item(0).getChildNodes();
    		
    		for (int i = 0; i < parentList.getLength(); i++) {
    			Node parentNode = parentList.item(i);
    			
    			if (parentTagName.equals(parentNode.getNodeName())) {
    				NodeList childList = parentNode.getChildNodes();
        			
        			for (int j = 0; j < childList.getLength(); j++) {
        				Node node = childList.item(j);
        				
        				if (tagName.equals(node.getNodeName())) {
        					retTagValue = node.getTextContent();
        				}
        				
        				if (findTagName.equals(node.getNodeName()) && findValue.equals(node.getTextContent())) {
            				return retTagValue;
        				}
        			}
    			}
    		}
    	}
    	
		return null;
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
}
