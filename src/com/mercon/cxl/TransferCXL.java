package com.mercon.cxl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
import com.mercon.entities.VbuildDrawTag;
import com.mercon.helper.SOAPHelper;
import com.mercon.util.DefaultConstants;

public class TransferCXL {

	private static TransferCXL instance;
	
	private UserBean user;
	
	private Transfer transfer;
	
	public static TransferCXL sharedInstance() {
		if (instance == null) {
			instance = new TransferCXL();
		}
		
		return instance;
	}
	
	public int saveFromTradeToVessel() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance;' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqPutTransfer'>");
		sb.append("    <sender>TriplePointClient</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <OPS_TRANSFER>");
		sb.append("        <equipment_num>" + this.transfer.getFromMovement().getEquipmentNum() + "</equipment_num>");
		sb.append("        <transfer_num>0</transfer_num>");
		sb.append("        <load_discharge_ind>0</load_discharge_ind>");
		sb.append("        <from_to_type_ind>0</from_to_type_ind>");
		sb.append("        <delivery_active_status_ind>1</delivery_active_status_ind>");
		sb.append("        <from_type_ind>0</from_type_ind>");
		sb.append("        <to_type_ind>2</to_type_ind>");
		sb.append("        <location_num>" + this.transfer.getLocation().getNumLocation() + "</location_num>");
		sb.append("        <transfer_comm_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</transfer_comm_dt>");
		sb.append("        <transfer_comp_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</transfer_comp_dt>");
		sb.append("        <application_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</application_dt>");
		sb.append("        <delivery_status_ind>0</delivery_status_ind>");
		sb.append("        <import_type_ind>0</import_type_ind>");
		sb.append("        <obligation_num>" + this.transfer.getFromObligation().getId().getObligationNum() + "</obligation_num>");
		sb.append("        <from_qty>" + this.transfer.getFromScheduledQty() + "</from_qty>");
		sb.append("        <from_uom_cd><![CDATA[" + this.transfer.getFromObligation().getId().getContractUomCd() + "]]></from_uom_cd>");
		sb.append("        <to_equipment_num>" + this.transfer.getToLevel().getNumEquipment() + "</to_equipment_num>");
		sb.append("        <to_cargo_num>" + this.transfer.getToLevel().getNumCargo() + "</to_cargo_num>");
		//sb.append("        <to_qty>" + new DecimalFormat("#.######").format((this.transfer.getFromTrade().getTradeQty().doubleValue() * this.transfer.getFromConversionFactor())) + "</to_qty>");
		sb.append("        <to_qty>" + new DecimalFormat("#.######").format(this.transfer.getToCargoQty()) + "</to_qty>");
		sb.append("        <to_uom_cd><![CDATA[" + this.transfer.getToLevel().getQtyUomCod().getCodUom() + "]]></to_uom_cd>");
		sb.append("        <uom_conv_factor>" + this.transfer.getFromConversionFactor() + "</uom_conv_factor>");
		sb.append("        <uom_conv_factor_ind>0</uom_conv_factor_ind>");
		sb.append("        <transfer_at_ind>1</transfer_at_ind>");
		sb.append("        <trade_sched_qty>" + this.transfer.getFromScheduledQty() + "</trade_sched_qty>");
		sb.append("        <trade_invoice_uom_cd><![CDATA[" + this.transfer.getFromTrade().getPriceUomCd() + "]]></trade_invoice_uom_cd>");
		sb.append("        <gain_loss_qty>0</gain_loss_qty>");
		sb.append("        <converted_qty>" + new DecimalFormat("#.######").format(this.transfer.getToCargoQty()) + "</converted_qty>");
		sb.append("        <transfer_uom><![CDATA[" + this.transfer.getToLevel().getCodQtyUom() + "]]></transfer_uom>");
		sb.append("        <effective_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getEffectiveDt()) + "</effective_dt>");
		sb.append("        <from_customs_duty_status_ind>-1</from_customs_duty_status_ind>");
		sb.append("        <to_customs_duty_status_ind>-1</to_customs_duty_status_ind>");
		sb.append("        <bl_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getBlDate()) + "</bl_dt>");
		sb.append("		   <bl_num_cd><![CDATA[" + this.transfer.getBlNumber() + "]]></bl_num_cd>");
		sb.append("        <conf_warning_ind>0</conf_warning_ind>");
		sb.append("        <from_uom_cd2><![CDATA[" + this.transfer.getFromTrade().getContractUomCd() + "]]></from_uom_cd2>");
		sb.append("        <transfer_uom2><![CDATA[" + this.transfer.getToLevel().getQtyUomCod().getCodUom() + "]]></transfer_uom2>");
		sb.append("        <to_uom_cd2><![CDATA[" + this.transfer.getToLevel().getQtyUomCod().getCodUom() + "]]></to_uom_cd2>");
		sb.append("        <deliv_uom_cd><![CDATA[" + this.transfer.getFromTrade().getContractUomCd() + "]]></deliv_uom_cd>");
		sb.append("        <deliv_uom_cd2><![CDATA[" + this.transfer.getFromTrade().getContractUomCd() + "]]></deliv_uom_cd2>");
		sb.append("        <duplicate_build_ind>2</duplicate_build_ind>");
		sb.append("        <from_inv_owner_company_num>" + this.transfer.getFromTrade().getInternalCompanyNum() + "</from_inv_owner_company_num>");
		sb.append("        <in_storage_title_transfer_ind>0</in_storage_title_transfer_ind>");
		sb.append("        <OPS_OBLIGATION_DETAIL>");
		sb.append("          <title_transfer_loc_num>" + this.transfer.getFromObligation().getId().getLocationNum() + "</title_transfer_loc_num>");
		sb.append("          <title_transfer_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</title_transfer_dt>");
		sb.append("          <cross_conv_factor>" + this.transfer.getFromObligation().getId().getBblMtFactor() + "</cross_conv_factor>");
		sb.append("          <bbl_mt_factor>" + this.transfer.getFromObligation().getId().getBblMtFactor() + "</bbl_mt_factor>");
		sb.append("          <obligation_type_ind>" + this.transfer.getFromObligation().getId().getObligationTypeInd() + "</obligation_type_ind>");
		sb.append("          <override_ind>0</override_ind>");
		sb.append("          <trade_price>" + this.transfer.getFromObligation().getId().getPrice() + "</trade_price>");
		sb.append("          <trade_price_curr_cd><![CDATA[" + this.transfer.getFromObligation().getId().getPriceCurrCd() + "]]></trade_price_curr_cd>");
		sb.append("          <l_trade_price_curr_cd><![CDATA[" + this.transfer.getFromObligation().getId().getPriceCurrCd() + "]]></l_trade_price_curr_cd>");
		sb.append("          <trade_price_uom_cd><![CDATA[" + this.transfer.getFromObligation().getId().getPriceUomCd() + "]]></trade_price_uom_cd>");
		sb.append("          <l_trade_price_uom_cd><![CDATA[" + this.transfer.getFromObligation().getId().getPriceUomCd() + "]]></l_trade_price_uom_cd>");
		sb.append("          <title_transfer_event_cd><![CDATA[Bill of Lading]]></title_transfer_event_cd>");
		sb.append("          <tosyncbldttldt>1</tosyncbldttldt>");
		sb.append("          <settlement_curr_cd><![CDATA[" + this.transfer.getFromObligation().getId().getSettlementCurrCd() + "]]></settlement_curr_cd>");
		sb.append("          <override_fx_ind>1</override_fx_ind>");
		sb.append("        </OPS_OBLIGATION_DETAIL>");
		
		for (VbuildDrawTag tagId : this.transfer.getTags()) {
			sb.append("        <OPS_BUILD_DRAW_TAG>");
			sb.append("          <build_draw_num>-1</build_draw_num>");
			sb.append("          <equipment_num>" + this.transfer.getToLevel().getNumEquipment() + "</equipment_num>");
			sb.append("          <cargo_num>" + this.transfer.getToLevel().getNumCargo() + "</cargo_num>");
			sb.append("          <tag_type_cd><![CDATA[" + tagId.getId().getTagTypeCd() + "]]></tag_type_cd>");
			sb.append("          <tag_type_ind>0</tag_type_ind>");
			sb.append("          <tag_value1><![CDATA[" + tagId.getId().getTagValue1() + "]]></tag_value1>");
			sb.append("          <tag_value2><![CDATA[" + tagId.getId().getTagValue2() + "]]></tag_value2>");
			if (tagId.getId().getTagValue3() != null) {
				sb.append("			 <tag_value3><![CDATA[" + tagId.getId().getTagValue3() + "]]></tag_value3>");
			}
			if (tagId.getId().getTagValue4() != null) {
				sb.append("			 <tag_value4><![CDATA[" + tagId.getId().getTagValue4() + "]]></tag_value4>");
			}
			if (tagId.getId().getTagValue7() != null) {
				sb.append("			 <tag_value7><![CDATA[" + tagId.getId().getTagValue7() + "]]></tag_value7>");
			}
	        if (tagId.getId().getTagValue8() != null) {
	        	sb.append("			 <tag_value8><![CDATA[" + tagId.getId().getTagValue8() + "]]></tag_value8>");
	        }
			sb.append("          <tag_qty_uom_cd><![CDATA[" + tagId.getId().getTagQtyUomCd() + "]]></tag_qty_uom_cd>");
			sb.append("          <tag_qty>" + tagId.getId().getTagQty() + "</tag_qty>");
			sb.append("          <validate_reqd_soft_ind>1</validate_reqd_soft_ind>");
			sb.append("			 <ref_bd_tag_num><![CDATA[]]></ref_bd_tag_num>");
			sb.append("        </OPS_BUILD_DRAW_TAG>");
		}
		
		sb.append("      </OPS_TRANSFER>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.TRANSFER_SERVICE);
		soapHelper.setMethod(DefaultConstants.SAVE_TRANSFER_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String transferNumber = getTagValue(xmlResponse, "transfer_num");
			
			if (transferNumber != null) {
				return Integer.parseInt(transferNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int saveFromVesselToStorage() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqPutTransfer'>");
		sb.append("    <sender>TriplePointClient</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <OPS_TRANSFER>");
		sb.append("        <equipment_num>" + this.transfer.getFromMovement().getEquipmentNum() + "</equipment_num>");
		sb.append("        <transfer_num>0</transfer_num>");
		sb.append("        <load_discharge_ind>1</load_discharge_ind>");
		sb.append("        <from_to_type_ind>1</from_to_type_ind>");
		sb.append("        <delivery_active_status_ind>1</delivery_active_status_ind>");
		sb.append("        <from_type_ind>2</from_type_ind>");
		sb.append("        <to_type_ind>1</to_type_ind>");
		sb.append("        <location_num>" + this.transfer.getLocation().getNumLocation() + "</location_num>");
		sb.append("        <transfer_comm_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</transfer_comm_dt>");
		sb.append("        <transfer_comp_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</transfer_comp_dt>");
		sb.append("        <application_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</application_dt>");
		sb.append("        <delivery_status_ind>0</delivery_status_ind>");
		sb.append("        <import_type_ind>0</import_type_ind>");
		sb.append("        <from_equipment_num>" + this.transfer.getFromMovement().getEquipmentNum() + "</from_equipment_num>");
		sb.append("        <from_cargo_num>" + this.transfer.getFromLevel().getNumCargo() + "</from_cargo_num>");
		sb.append("        <from_qty>" + this.transfer.getFromCargoQty() + "</from_qty>");
		sb.append("        <from_uom_cd><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></from_uom_cd>");
		sb.append("        <to_equipment_num>" + this.transfer.getToStorage().getNumEquipment() + "</to_equipment_num>");
		sb.append("        <to_cargo_num>" + this.transfer.getToLevel().getNumCargo() + "</to_cargo_num>");
		sb.append("        <to_qty>" + this.transfer.getToStorageQty() + "</to_qty>");
		sb.append("        <to_uom_cd><![CDATA[" + this.transfer.getToLevel().getCodQtyUom() + "]]></to_uom_cd>");
		sb.append("        <uom_conv_factor>1</uom_conv_factor>");
		sb.append("        <uom_conv_factor_ind>0</uom_conv_factor_ind>");
		sb.append("        <transfer_at_ind>3</transfer_at_ind>");
		sb.append("        <gain_loss_qty>0</gain_loss_qty>");
		sb.append("        <converted_qty>" + new DecimalFormat("#.######").format(this.transfer.getToStorageQty()) + "</converted_qty>");
		sb.append("        <transfer_uom><![CDATA[" + this.transfer.getToLevel().getCodQtyUom() + "]]></transfer_uom>");
		sb.append("        <effective_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getEffectiveDt()) + "</effective_dt>");
		sb.append("        <from_customs_duty_status_ind>-1</from_customs_duty_status_ind>");
		sb.append("        <to_customs_duty_status_ind>-1</to_customs_duty_status_ind>");
		sb.append("        <bl_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getBlDate() == null ? this.transfer.getCommencementDt() : this.transfer.getBlDate()) + "</bl_dt>");
		sb.append("        <conf_warning_ind>0</conf_warning_ind>");
		sb.append("        <from_uom_cd2><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></from_uom_cd2>");
		sb.append("        <transfer_uom2><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></transfer_uom2>");
		sb.append("        <to_uom_cd2><![CDATA[" + this.transfer.getToLevel().getCodQtyUom() + "]]></to_uom_cd2>");
		sb.append("        <duplicate_build_ind>2</duplicate_build_ind>");
		sb.append("        <in_storage_title_transfer_ind>0</in_storage_title_transfer_ind>");
		sb.append("        <OPS_OBLIGATION_DETAIL>");
		sb.append("          <override_ind>0</override_ind>");
		sb.append("          <override_fx_ind>1</override_fx_ind>");
		sb.append("        </OPS_OBLIGATION_DETAIL>");
		sb.append("      </OPS_TRANSFER>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.TRANSFER_SERVICE);
		soapHelper.setMethod(DefaultConstants.SAVE_TRANSFER_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String transferNumber = getTagValue(xmlResponse, "transfer_num");
			
			if (transferNumber != null) {
				return Integer.parseInt(transferNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int saveFromVesselToTrade() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqPutTransfer'>");
		sb.append("    <sender>TriplePointClient</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <OPS_TRANSFER>");
		sb.append("        <equipment_num>" + this.transfer.getFromLevel().getNumEquipment() + "</equipment_num>");
		sb.append("        <transfer_num>0</transfer_num>");
		sb.append("        <load_discharge_ind>1</load_discharge_ind>");
		sb.append("        <from_to_type_ind>0</from_to_type_ind>");
		sb.append("        <delivery_active_status_ind>1</delivery_active_status_ind>");
		sb.append("        <from_type_ind>2</from_type_ind>");
		sb.append("        <to_type_ind>0</to_type_ind>");
		sb.append("        <location_num>" + this.transfer.getLocation().getNumLocation() + "</location_num>");
		sb.append("        <transfer_comm_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</transfer_comm_dt>");
		sb.append("        <transfer_comp_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</transfer_comp_dt>");
		sb.append("        <application_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</application_dt>");
		sb.append("        <delivery_status_ind>0</delivery_status_ind>");
		sb.append("        <import_type_ind>0</import_type_ind>");
		sb.append("        <obligation_num>" + this.transfer.getToObligation().getId().getObligationNum() + "</obligation_num>");
		sb.append("        <from_equipment_num>" + this.transfer.getFromLevel().getNumEquipment() + "</from_equipment_num>");
		sb.append("        <from_cargo_num>" + this.transfer.getFromLevel().getNumCargo() + "</from_cargo_num>");
		sb.append("        <from_qty>" + this.transfer.getFromCargoQty() + "</from_qty>");
		sb.append("        <from_uom_cd><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></from_uom_cd>");
//		<from_uom_cd><![CDATA[60kbg]]></from_uom_cd>
		sb.append("        <to_qty>" + this.transfer.getToScheduledQty() + "</to_qty>");
		sb.append("        <to_uom_cd><![CDATA[" + this.transfer.getToObligation().getId().getContractUomCd() + "]]></to_uom_cd>");
		sb.append("        <uom_conv_factor>1</uom_conv_factor>");
		sb.append("        <uom_conv_factor_ind>0</uom_conv_factor_ind>");
		sb.append("        <transfer_at_ind>3</transfer_at_ind>");
		sb.append("        <trade_sched_qty>" + this.transfer.getToScheduledQty() + "</trade_sched_qty>");
		sb.append("        <trade_invoice_uom_cd><![CDATA[" + this.transfer.getToTrade().getPriceUomCd() + "]]></trade_invoice_uom_cd>");
		sb.append("		   <gain_loss_qty>0</gain_loss_qty>");
        sb.append("		   <converted_qty>" + new DecimalFormat("#.######").format(this.transfer.getFromCargoQty()) + "</converted_qty>");
		sb.append("        <transfer_uom><![CDATA[" + this.transfer.getFromLevel().getCodQtyUom() + "]]></transfer_uom>");
		sb.append("        <effective_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getEffectiveDt()) + "</effective_dt>");
		sb.append("		   <from_customs_duty_status_ind>-1</from_customs_duty_status_ind>");
		sb.append("        <to_customs_duty_status_ind>-1</to_customs_duty_status_ind>");
		sb.append("        <bl_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getBlDate() == null ? this.transfer.getCommencementDt() : this.transfer.getBlDate()) + "</bl_dt>");
		sb.append("        <conf_warning_ind>0</conf_warning_ind>");
		sb.append("		   <from_uom_cd2><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></from_uom_cd2>");
		sb.append("        <transfer_uom2><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></transfer_uom2>");
		sb.append("        <to_uom_cd2><![CDATA[" + this.transfer.getToTrade().getContractUomCd() + "]]></to_uom_cd2>");
		sb.append("        <deliv_uom_cd><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></deliv_uom_cd>");
		sb.append("        <deliv_uom_cd2><![CDATA[" + this.transfer.getFromLevel().getQtyUomCod().getCodUom() + "]]></deliv_uom_cd2>");
		sb.append("        <duplicate_build_ind>2</duplicate_build_ind>");
		sb.append("        <to_inv_owner_company_num>" + this.transfer.getToTrade().getInternalCompanyNum() + "</to_inv_owner_company_num>");
		sb.append("        <in_storage_title_transfer_ind>0</in_storage_title_transfer_ind>");
		sb.append("        <OPS_OBLIGATION_DETAIL>");
		sb.append("          <title_transfer_loc_num>" + this.transfer.getToObligation().getId().getLocationNum() + "</title_transfer_loc_num>");
		sb.append("          <title_transfer_dt>" + new SimpleDateFormat("yyyyMMdd").format(this.transfer.getCommencementDt()) + "</title_transfer_dt>");
		sb.append("          <cross_conv_factor>" + this.transfer.getToObligation().getId().getBblMtFactor() + "</cross_conv_factor>");
		sb.append("          <bbl_mt_factor>" + this.transfer.getToObligation().getId().getBblMtFactor() + "</bbl_mt_factor>");
		sb.append("          <obligation_type_ind>" + this.transfer.getToObligation().getId().getObligationTypeInd() + "</obligation_type_ind>");
		sb.append("          <override_ind>0</override_ind>");
		sb.append("          <trade_price>" + this.transfer.getToObligation().getId().getPrice() + "</trade_price>");
		sb.append("          <trade_price_curr_cd><![CDATA[" + this.transfer.getToObligation().getId().getPriceCurrCd() + "]]></trade_price_curr_cd>");
		sb.append("          <l_trade_price_curr_cd><![CDATA[" + this.transfer.getToObligation().getId().getPriceCurrCd() + "]]></l_trade_price_curr_cd>");
		sb.append("          <trade_price_uom_cd><![CDATA[" + this.transfer.getToObligation().getId().getPriceUomCd() + "]]></trade_price_uom_cd>");
		sb.append("          <l_trade_price_uom_cd><![CDATA[" + this.transfer.getToObligation().getId().getPriceUomCd() + "]]></l_trade_price_uom_cd>");
		sb.append("          <title_transfer_event_cd><![CDATA[Bill of Lading]]></title_transfer_event_cd>");
		sb.append("          <tosyncbldttldt>1</tosyncbldttldt>");
		sb.append("          <settlement_curr_cd><![CDATA[" + this.transfer.getToObligation().getId().getSettlementCurrCd() + "]]></settlement_curr_cd>");
		sb.append("          <override_fx_ind>1</override_fx_ind>");
		sb.append("        </OPS_OBLIGATION_DETAIL>");
		sb.append("      </OPS_TRANSFER>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.TRANSFER_SERVICE);
		soapHelper.setMethod(DefaultConstants.SAVE_TRANSFER_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String transferNumber = getTagValue(xmlResponse, "transfer_num");
			
			if (transferNumber != null) {
				return Integer.parseInt(transferNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int voidTransfer() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqCancelTransfer'>");
		sb.append("    <sender>{EBDFAFC6-439A-4737-857F-FE811A29816F}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <CancelTransfer>");
		sb.append("        <transfer_num><![CDATA[" + this.transfer.getTransferNum() + "]]></transfer_num>");
		sb.append("      </CancelTransfer>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.TRANSFER_SERVICE);
		soapHelper.setMethod(DefaultConstants.DELETE_TRANSFER_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String transferNumber = getTagValue(xmlResponse, "transfer_num");
			
			if (transferNumber != null) {
				return Integer.parseInt(transferNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int breakAllocation(int buildDrawNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqCancelBuildDrawMatch'>");
		sb.append("    <sender>{21CE353B-A158-4E30-9A32-979A6E6147A6}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <CancelBuildDrawMatch>");
		sb.append("        <bd_match_num><![CDATA[" + buildDrawNumber + "]]></bd_match_num>");
		sb.append("      </CancelBuildDrawMatch>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.TRANSFER_SERVICE);
		soapHelper.setMethod(DefaultConstants.DELETE_TAG_ALLOCATION_METHOD);
		
		try {
			String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
			String bdMatchNumber = getTagValue(xmlResponse, "bd_match_num");
			
			if (bdMatchNumber != null) {
				return Integer.parseInt(bdMatchNumber);
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

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

}
