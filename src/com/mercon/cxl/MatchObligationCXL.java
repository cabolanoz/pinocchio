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

public class MatchObligationCXL {

	private static MatchObligationCXL instance;
	
	public static MatchObligationCXL sharedInstance() {
		if (instance == null) {
			instance = new MatchObligationCXL();
		}
		
		return instance;
	}
	
	private UserBean user;
	
	private MatchObligation matchObligation;
	
	public MatchObligationCXL() { }

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public MatchObligation getMatchObligation() {
		return matchObligation;
	}

	public void setMatchObligation(MatchObligation matchObligation) {
		this.matchObligation = matchObligation;
	}
	
	public int save() {
		String emptyMO = getEmptyOpsBackToBack(matchObligation.getTradeInputIdPurchase().getObligationNum(), matchObligation.getTradeInputIdSell().getObligationNum());
		
		if (!emptyMO.trim().equals("")) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append(" <mhead name='TPTReqPutOpsBackToBack'>");
			sb.append("   <sender>{9CE907EF-44FD-452C-A284-749C8BEDB7DB}</sender>");
			sb.append("   <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append(" </mhead>");
			sb.append(" <mbody>");
			sb.append("   <data>");
			sb.append("     <PutOpsBackToBack>");
			sb.append("       <OPS_OBLIGATION_MATCH table='1'>");
			sb.append("         <obligation_match_num><![CDATA[-1]]></obligation_match_num>");
			sb.append("         <match_dt><![CDATA[" + getTagValue(emptyMO, "application_dt") + "]]></match_dt>");
			sb.append("         <mot_type_ind><![CDATA[0]]></mot_type_ind>");
			sb.append("         <delivery_status_ind><![CDATA[0]]></delivery_status_ind>");
			sb.append("         <delivery_active_status_ind><![CDATA[1]]></delivery_active_status_ind>");
			sb.append("         <operator_person_num><![CDATA[" + matchObligation.getPersonId().getNumperson() + "]]></operator_person_num>");
			sb.append("			<title_transfer_location_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getLocation() + "]]></title_transfer_location_num>");
			sb.append("         <cmdty_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCmdtyCd() + "]]></cmdty_cd>");
			sb.append("         <internal_co_num><![CDATA[" + matchObligation.getCompanyId().getNumCompany() + "]]></internal_co_num>");
			sb.append("         <application_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "000000]]></application_dt>");
			sb.append("         <delivery_type_ind><![CDATA[1]]></delivery_type_ind>");
			sb.append("         <match_at_ind><![CDATA[1]]></match_at_ind>");
			sb.append("         <mtm_fwd_curve_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getMtmFwdCurveNum() + "]]></mtm_fwd_curve_num>");
			sb.append("			<match_desc><![CDATA[" + matchObligation.getMatchDescription() +"]]></match_desc>");
			sb.append("         <conf_warning_ind><![CDATA[0]]></conf_warning_ind>");
			sb.append("         <create_invoice_ind><![CDATA[1]]></create_invoice_ind>");
			sb.append("         <OPS_OBLIGATION_DETAIL table='2'>");
			sb.append("           <id><![CDATA[1]]></id>");
			sb.append("           <obligation_num><![CDATA[" + matchObligation.getTradeInputIdSell().getObligationNum() + "]]></obligation_num>");
			sb.append("           <strategy_num><![CDATA[" + matchObligation.getTradeInputIdSell().getStrategyNum() + "]]></strategy_num>");
			sb.append("           <trade_num><![CDATA[" + matchObligation.getTradeInputIdSell().getTradeNum() + "]]></trade_num>");
			sb.append("           <term_section_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getTermSectionCd() + "]]></term_section_cd>");
			sb.append("           <internal_co_num><![CDATA[" + matchObligation.getTradeInputIdSell().getInternalCompanyNum() + "]]></internal_co_num>");
			sb.append("           <delivery_term_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getDeliveryTermCd() + "]]></delivery_term_cd>");
			sb.append("           <term_num><![CDATA[" + matchObligation.getTradeInputIdSell().getTermNum() + "]]></term_num>");
			sb.append("           <buy_sell_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getBuySellInd() + "]]></buy_sell_ind>");
			sb.append("           <cmdty_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getCmdtyCd() + "]]></cmdty_cd>");
			sb.append("           <company_num><![CDATA[" + matchObligation.getTradeInputIdSell().getCompanyNum() + "]]></company_num>");
			sb.append("           <laycan_start_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanStartDt()) + "000000]]></laycan_start_dt>");
			sb.append("           <laycan_end_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanEndDt()) + "000000]]></laycan_end_dt>");
			sb.append("           <contract_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getTradeQty() + "]]></contract_qty>");
			sb.append("           <contract_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></contract_uom_cd>");
			sb.append("           <deliv_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getDelivQty() + "]]></deliv_qty>");
			sb.append("           <deliv_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getDelivUomCd() + "]]></deliv_uom_cd>");
			sb.append("           <open_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getOpenQty() + "]]></open_qty>");
			sb.append("           <price><![CDATA[" + matchObligation.getTradeInputIdSell().getPrice() + "]]></price>");
			sb.append("           <price_curr_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlementCurrCd() + "]]></price_curr_cd>");
			sb.append("           <price_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlementUomCd() + "]]></price_uom_cd>");
			sb.append("           <scheduled_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></scheduled_qty>");
			sb.append("           <scheduled_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></scheduled_qty_uom_cd>");
			sb.append("           <nominal_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></nominal_qty>");
			sb.append("           <nominal_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></nominal_qty_uom_cd>");
			sb.append("           <air_vac_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getAirVacInd() + "]]></air_vac_ind>");
			sb.append("           <cross_conv_factor><![CDATA[1]]></cross_conv_factor>");
			sb.append("           <importer_exporter_ind><![CDATA[0]]></importer_exporter_ind>");
			sb.append("           <title_transfer_loc_num><![CDATA[" + matchObligation.getTradeInputIdSell().getLocation() + "]]></title_transfer_loc_num>");
			sb.append("           <title_transfer_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanEndDt()) + "000000]]></title_transfer_dt>");
			sb.append("           <title_transfer_event_cd><![CDATA[Load End]]></title_transfer_event_cd>");
			sb.append("           <bl_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanEndDt()) + "000000]]></bl_dt>");
			sb.append("           <min_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></min_qty>");
			sb.append("           <max_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></max_qty>");
			sb.append("           <mtm_fwd_curve_num><![CDATA[" + matchObligation.getTradeInputIdSell().getMtmFwdCurveNum() + "]]></mtm_fwd_curve_num>");
			sb.append("           <toSyncBLDtTLDt><![CDATA[1]]></toSyncBLDtTLDt>");
			sb.append("           <customs_duty_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getCustomsDutyStatusInd() + "]]></customs_duty_status_ind>");
			sb.append("           <sch_conf_executed_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getSchConfExecutedInd() + "]]></sch_conf_executed_ind>");
			sb.append("           <override_ind><![CDATA[0]]></override_ind>");
			sb.append("           <settlement_curr_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlementCurrCd() + "]]></settlement_curr_cd>");
			sb.append("           <override_fx_ind><![CDATA[1]]></override_fx_ind>");
			sb.append("           <settle_payment_term_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlePaymentTermCd() + "]]></settle_payment_term_cd>");
			sb.append("           <pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getPricingStatusInd() + "]]></pricing_status_ind>");
			sb.append("           <futures_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getFuturesPricingStatusInd() + "]]></futures_pricing_status_ind>");
			sb.append("           <basis_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getBasisPricingStatusInd() + "]]></basis_pricing_status_ind>");
			sb.append("           <origin_loc_num><![CDATA[" + matchObligation.getTradeInputIdSell().getOriginLocNum() + "]]></origin_loc_num>");
			sb.append("           <location_num><![CDATA[" + matchObligation.getTradeInputIdSell().getLocation() + "]]></location_num>");
			sb.append("           <obligation_type_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getObligationTypeInd() + "]]></obligation_type_ind>");
			sb.append("           <deliv_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></deliv_uom_cd2>");
			sb.append("           <nominal_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></nominal_qty_uom_cd2>");
			sb.append("           <scheduled_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></scheduled_qty_uom_cd2>");
			sb.append("         </OPS_OBLIGATION_DETAIL>");
			sb.append("         <OPS_OBLIGATION_DETAIL table='2'>");
			sb.append("           <id><![CDATA[2]]></id>");
			sb.append("           <obligation_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getObligationNum() + "]]></obligation_num>");
			sb.append("           <strategy_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getStrategyNum() + "]]></strategy_num>");
			sb.append("           <trade_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTradeNum() + "]]></trade_num>");
			sb.append("           <term_section_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTermSectionCd() + "]]></term_section_cd>");
			sb.append("           <internal_co_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getInternalCompanyNum() + "]]></internal_co_num>");
			sb.append("           <delivery_term_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getDeliveryTermCd() + "]]></delivery_term_cd>");
			sb.append("           <term_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTermNum() + "]]></term_num>");
			sb.append("           <buy_sell_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getBuySellInd() + "]]></buy_sell_ind>");
			sb.append("           <cmdty_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCmdtyCd() + "]]></cmdty_cd>");
			sb.append("           <company_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCompanyNum() + "]]></company_num>");
			sb.append("           <laycan_start_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanStartDt()) + "000000]]></laycan_start_dt>");
			sb.append("           <laycan_end_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanEndDt()) + "000000]]></laycan_end_dt>");
			sb.append("           <contract_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTradeQty() + "]]></contract_qty>");
			sb.append("           <contract_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></contract_uom_cd>");
			sb.append("           <deliv_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getDelivQty() + "]]></deliv_qty>");
			sb.append("           <deliv_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getDelivUomCd() + "]]></deliv_uom_cd>");
			sb.append("           <open_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getOpenQty() + "]]></open_qty>");
			sb.append("           <price><![CDATA[" + matchObligation.getTradeInputIdPurchase().getPrice() + "]]></price>");
			sb.append("           <price_curr_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlementCurrCd() + "]]></price_curr_cd>");
			sb.append("           <price_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlementUomCd() + "]]></price_uom_cd>");
			sb.append("           <scheduled_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></scheduled_qty>");
			sb.append("           <scheduled_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></scheduled_qty_uom_cd>");
			sb.append("           <nominal_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></nominal_qty>");
			sb.append("           <nominal_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></nominal_qty_uom_cd>");
			sb.append("           <air_vac_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getAirVacInd() + "]]></air_vac_ind>");
			sb.append("           <cross_conv_factor><![CDATA[1]]></cross_conv_factor>");
			sb.append("           <importer_exporter_ind><![CDATA[0]]></importer_exporter_ind>");
			sb.append("           <title_transfer_loc_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getLocation() + "]]></title_transfer_loc_num>");
			sb.append("           <title_transfer_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanEndDt()) + "000000]]></title_transfer_dt>");
			sb.append("           <title_transfer_event_cd><![CDATA[Bill of Lading]]></title_transfer_event_cd>");
			sb.append("           <min_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></min_qty>");
			sb.append("           <max_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></max_qty>");
			sb.append("           <mtm_fwd_curve_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getMtmFwdCurveNum() + "]]></mtm_fwd_curve_num>");
			sb.append("           <toSyncBLDtTLDt><![CDATA[0]]></toSyncBLDtTLDt>");
			sb.append("           <customs_duty_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCustomsDutyStatusInd() + "]]></customs_duty_status_ind>");
			sb.append("           <sch_conf_executed_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSchConfExecutedInd() + "]]></sch_conf_executed_ind>");
			sb.append("           <override_ind><![CDATA[0]]></override_ind>");
			sb.append("           <settlement_curr_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlementCurrCd() + "]]></settlement_curr_cd>");
			sb.append("           <override_fx_ind><![CDATA[1]]></override_fx_ind>");
			sb.append("           <settle_payment_term_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlePaymentTermCd() + "]]></settle_payment_term_cd>");
			sb.append("           <pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getPricingStatusInd() + "]]></pricing_status_ind>");
			sb.append("           <futures_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getFuturesPricingStatusInd() + "]]></futures_pricing_status_ind>");
			sb.append("           <basis_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getBasisPricingStatusInd() + "]]></basis_pricing_status_ind>");
			sb.append("           <origin_loc_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getOriginLocNum() + "]]></origin_loc_num>");
			sb.append("           <location_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getLocation() + "]]></location_num>");
			sb.append("           <obligation_type_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getObligationTypeInd() + "]]></obligation_type_ind>");
			sb.append("           <deliv_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></deliv_uom_cd2>");
			sb.append("           <nominal_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></nominal_qty_uom_cd2>");
			sb.append("           <scheduled_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></scheduled_qty_uom_cd2>");
			sb.append("         </OPS_OBLIGATION_DETAIL>");
			sb.append("       </OPS_OBLIGATION_MATCH>");
			sb.append("     </PutOpsBackToBack>");
			sb.append("   </data>");
			sb.append(" </mbody>");
			sb.append("</request>");
			
			SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.MATCH_OBLIGATION_SERVICE);
			soapHelper.setMethod(DefaultConstants.SAVE_MATCH_OBLIGATION_METHOD);
			
			try {
				String xmlResponse = soapHelper.sendRequest(sb.toString(), true, user.getUsersession());
				String obligationMatchNumber = getTagValue(xmlResponse, "obligation_match_num");
				
				if (obligationMatchNumber != null) {
					return Integer.parseInt(obligationMatchNumber);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return -1;
	}
	
	private String getEmptyOpsBackToBack(int purchaseObligationNumber, int sellObligationNumber) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version='1.0'?>");
		sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
		sb.append("  <mhead name='TPTReqGetEmptyOpsBackToBack'>");
		sb.append("    <sender>{199E800F-2878-449E-BCF9-3A2053817C2A}</sender>");
		sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
		sb.append("  </mhead>");
		sb.append("  <mbody>");
		sb.append("    <data>");
		sb.append("      <GetEmptyOpsBackToBack>");
		sb.append("        <obligation_num><![CDATA[" + purchaseObligationNumber + "|" + sellObligationNumber + "]]></obligation_num>");
		sb.append("        <delivery_type_ind><![CDATA[1]]></delivery_type_ind>");
		sb.append("      </GetEmptyOpsBackToBack>");
		sb.append("    </data>");
		sb.append("  </mbody>");
		sb.append("</request>");
		
		SOAPHelper soapHelper = SOAPHelper.sharedInstance();
		soapHelper.setService(DefaultConstants.MATCH_OBLIGATION_SERVICE);
		soapHelper.setMethod(DefaultConstants.GET_EMPTY_MATCH_OBLIGATION_METHOD);
		
		try {
			return soapHelper.sendRequest(sb.toString(), true, user.getUsersession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String retrieveByTradeId(int tradeNumber) {
		if (tradeNumber > 0) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append("  <mhead name='TPTReqGetObligationMatchList'>");
			sb.append("    <sender>{08BF721E-82FE-4D77-A790-992A38D05411}</sender>");
			sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append("    <resp_enc>TPT</resp_enc>");
			sb.append("  </mhead>");
			sb.append("  <mbody>");
			sb.append("    <data>");
			sb.append("      <GetObligationMatchList>");
			sb.append("        <trade_num>" + tradeNumber + "</trade_num>");
			sb.append("        <groupby></groupby>");
			sb.append("        <select>");
			sb.append("          <obligation_match_num/>");
			sb.append("          <match_dt/>");
			sb.append("          <match_desc/>");
			sb.append("          <cmdty_cd/>");
			sb.append("          <operator_person_num/>");
			sb.append("          <application_dt/>");
			sb.append("          <internal_co_num/>");
			sb.append("          <delivery_type_ind/>");
			sb.append("          <delivery_status_ind/>");
			sb.append("          <delivery_active_status_ind/>");
			sb.append("          <mot_type_ind/>");
			sb.append("          <mot_num/>");
			sb.append("          <load_location_num/>");
			sb.append("          <discharge_location_num/>");
			sb.append("          <load_port_location_num/>");
			sb.append("          <discharge_port_location_num/>");
			sb.append("          <eta_load_dt/>");
			sb.append("          <eta_discharge_dt/>");
			sb.append("          <completion_load_dt/>");
			sb.append("          <completion_discharge_dt/>");
			sb.append("          <commence_load_dt/>");
			sb.append("          <commence_discharge_dt/>");
			sb.append("          <title_transfer_location_num/>");
			sb.append("          <match_settlement_curr_cd/>");
			sb.append("          <last_modify_dt/>");
			sb.append("          <modify_person_num/>");
			sb.append("          <cmdty_certified_ind/>");
			sb.append("          <pump_dt/>");
			sb.append("        </select>");
			sb.append("      </GetObligationMatchList>");
			sb.append("    </data>");
			sb.append("  </mbody>");
			sb.append("</request>");
			
			SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.MATCH_OBLIGATION_SERVICE);
			soapHelper.setMethod(DefaultConstants.GET_OBLIGATION_MATCH_LIST_METHOD);
			
			try {
				return soapHelper.sendRequest(sb.toString(), true, user.getUsersession());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public int update() {
		if (matchObligation.getMatchObligationNumber() > 0) {
			String lockinfo = getLockInfoByMatch();
			
			if (!lockinfo.equals("")) {
				StringBuilder sb = new StringBuilder();
				
				sb.append("<?xml version='1.0'?>");
				sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
				sb.append("  <mhead name='TPTReqPutOpsBackToBack'>");
				sb.append("    <sender>{CB5CB4F5-3683-447D-9716-405AE1AA8875}</sender>");
				sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
				sb.append("  </mhead>");
				sb.append("  <mbody>");
				sb.append("    <data>");
				sb.append("      <PutOpsBackToBack>");
				sb.append("        <OPS_OBLIGATION_MATCH table='1'>");
				sb.append("          <obligation_match_num><![CDATA[" + matchObligation.getMatchObligationNumber() + "]]></obligation_match_num>");
				sb.append("          <match_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getMatchDt()) + "000000]]></match_dt>");
				sb.append("          <mot_type_ind><![CDATA[0]]></mot_type_ind>");
				sb.append("          <delivery_status_ind><![CDATA[0]]></delivery_status_ind>");
				sb.append("          <delivery_active_status_ind><![CDATA[1]]></delivery_active_status_ind>");
				sb.append("          <operator_person_num><![CDATA[" + matchObligation.getPersonId().getNumperson() + "]]></operator_person_num>");
				sb.append("          <cmdty_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getCmdtyCd() + "]]></cmdty_cd>");
				sb.append("          <internal_co_num><![CDATA[" + matchObligation.getCompanyId().getNumCompany() + "]]></internal_co_num>");
				sb.append("          <application_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "000000]]></application_dt>");
				sb.append("          <modify_person_num><![CDATA[" + matchObligation.getModifyPersonId() + "]]></modify_person_num>");
				sb.append("          <last_modify_dt><![CDATA[" + new SimpleDateFormat("yyyyMMddHHmmss").format(matchObligation.getModifyDt()) + "]]></last_modify_dt>");
				sb.append("          <delivery_type_ind><![CDATA[1]]></delivery_type_ind>");
				sb.append("          <match_at_ind><![CDATA[1]]></match_at_ind>");
				sb.append("          <mtm_fwd_curve_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getMtmFwdCurveNum() + "]]></mtm_fwd_curve_num>");
				sb.append("          <match_desc><![CDATA[" + matchObligation.getMatchDescription() +"]]></match_desc>");
				sb.append("          <conf_warning_ind><![CDATA[0]]></conf_warning_ind>");
				sb.append("          <initiator_ind><![CDATA[-1]]></initiator_ind>");
//				sb.append("          <delivery_company_num><![CDATA[0]]></delivery_company_num>");
//				sb.append("          <receiver_company_num><![CDATA[0]]></receiver_company_num>");
//				sb.append("          <inspector_company_num><![CDATA[0]]></inspector_company_num>");
				sb.append("          <part_cargo_ind><![CDATA[0]]></part_cargo_ind>");
				sb.append("          <ops_fin_hold_ind><![CDATA[0]]></ops_fin_hold_ind>");
				sb.append("			 <mkt_price_lock_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getMatchDt()) + "000000]]></mkt_price_lock_dt>");
				sb.append("			 <mkt_price_lock_dt_ind><![CDATA[1]]></mkt_price_lock_dt_ind>");
				sb.append("          <create_invoice_ind><![CDATA[1]]></create_invoice_ind>");
				sb.append("          <OPS_OBLIGATION_DETAIL table='2'>");
				sb.append("            <id><![CDATA[1]]></id>");
				sb.append("            <obligation_num><![CDATA[" + matchObligation.getTradeInputIdSell().getObligationNum() + "]]></obligation_num>");
				sb.append("            <obligation_detail_num><![CDATA[" + matchObligation.getTradeInputIdSell().getObligationDetailNum() + "]]></obligation_detail_num>");
				sb.append("            <strategy_num><![CDATA[" + matchObligation.getTradeInputIdSell().getStrategyNum() + "]]></strategy_num>");
				sb.append("            <trade_num><![CDATA[" + matchObligation.getTradeInputIdSell().getTradeNum() + "]]></trade_num>");
				sb.append("            <term_section_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getTermSectionCd() + "]]></term_section_cd>");
				sb.append("            <internal_co_num><![CDATA[" + matchObligation.getTradeInputIdSell().getInternalCompanyNum() + "]]></internal_co_num>");
				sb.append("            <delivery_term_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getDeliveryTermCd() + "]]></delivery_term_cd>");
				sb.append("            <term_num><![CDATA[" + matchObligation.getTradeInputIdSell().getTermNum() + "]]></term_num>");
				sb.append("            <buy_sell_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getBuySellInd() + "]]></buy_sell_ind>");
				sb.append("            <cmdty_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getCmdtyCd() + "]]></cmdty_cd>");
				sb.append("            <company_num><![CDATA[" + matchObligation.getTradeInputIdSell().getCompanyNum() + "]]></company_num>");
				sb.append("            <laycan_start_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanStartDt()) + "000000]]></laycan_start_dt>");
				sb.append("            <laycan_end_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanEndDt()) + "000000]]></laycan_end_dt>");
				sb.append("            <contract_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getTradeQty() + "]]></contract_qty>");
				sb.append("            <contract_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></contract_uom_cd>");
				sb.append("            <deliv_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getDelivQty() + "]]></deliv_qty>");
				sb.append("            <deliv_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getDelivUomCd() + "]]></deliv_uom_cd>");
				sb.append("            <open_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getOpenQty() + "]]></open_qty>");
				sb.append("            <price><![CDATA[" + matchObligation.getTradeInputIdSell().getPrice() + "]]></price>");
				sb.append("            <price_curr_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlementCurrCd() + "]]></price_curr_cd>");
				sb.append("            <price_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlementUomCd() + "]]></price_uom_cd>");
				sb.append("            <scheduled_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></scheduled_qty>");
				sb.append("            <scheduled_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></scheduled_qty_uom_cd>");
				sb.append("            <nominal_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></nominal_qty>");
				sb.append("            <nominal_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></nominal_qty_uom_cd>");
				sb.append("            <air_vac_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getAirVacInd() + "]]></air_vac_ind>");
				sb.append("            <cross_conv_factor><![CDATA[1]]></cross_conv_factor>");
				sb.append("            <importer_exporter_ind><![CDATA[0]]></importer_exporter_ind>");
				sb.append("            <title_transfer_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanEndDt()) + "000000]]></title_transfer_dt>");
				sb.append("            <title_transfer_event_cd><![CDATA[Load End]]></title_transfer_event_cd>");
				sb.append("            <bl_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdSell().getLaycanEndDt()) + "000000]]></bl_dt>");
				sb.append("            <min_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></min_qty>");
				sb.append("            <max_qty><![CDATA[" + matchObligation.getTradeInputIdSell().getScheduledQty() + "]]></max_qty>");
				sb.append("            <mtm_fwd_curve_num><![CDATA[" + matchObligation.getTradeInputIdSell().getMtmFwdCurveNum() + "]]></mtm_fwd_curve_num>");
				sb.append("            <toSyncBLDtTLDt><![CDATA[1]]></toSyncBLDtTLDt>");
				sb.append("            <customs_duty_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getCustomsDutyStatusInd() + "]]></customs_duty_status_ind>");
				sb.append("            <sch_conf_executed_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getSchConfExecutedInd() + "]]></sch_conf_executed_ind>");
				sb.append("            <override_ind><![CDATA[0]]></override_ind>");
				sb.append("            <settlement_curr_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlementCurrCd() + "]]></settlement_curr_cd>");
				sb.append("            <override_fx_ind><![CDATA[1]]></override_fx_ind>");
				sb.append("            <settle_payment_term_cd><![CDATA[" + matchObligation.getTradeInputIdSell().getSettlePaymentTermCd() + "]]></settle_payment_term_cd>");
				sb.append("            <pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getPricingStatusInd() + "]]></pricing_status_ind>");
				sb.append("            <futures_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getFuturesPricingStatusInd() + "]]></futures_pricing_status_ind>");
				sb.append("            <basis_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getBasisPricingStatusInd() + "]]></basis_pricing_status_ind>");
				sb.append("            <origin_loc_num><![CDATA[" + matchObligation.getTradeInputIdSell().getOriginLocNum() + "]]></origin_loc_num>");
				sb.append("            <location_num><![CDATA[" + matchObligation.getTradeInputIdSell().getLocation() + "]]></location_num>");
				sb.append("            <obligation_type_ind><![CDATA[" + matchObligation.getTradeInputIdSell().getObligationTypeInd() + "]]></obligation_type_ind>");
				sb.append("            <deliv_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></deliv_uom_cd2>");
				sb.append("            <nominal_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></nominal_qty_uom_cd2>");
				sb.append("            <scheduled_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdSell().getContractUomCd() + "]]></scheduled_qty_uom_cd2>");
				sb.append("          </OPS_OBLIGATION_DETAIL>");
				sb.append("          <OPS_OBLIGATION_DETAIL table='2'>");
				sb.append("            <id><![CDATA[2]]></id>");
				sb.append("            <obligation_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getObligationNum() + "]]></obligation_num>");
				sb.append("            <obligation_detail_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getObligationDetailNum() + "]]></obligation_detail_num>");
				sb.append("            <strategy_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getStrategyNum() + "]]></strategy_num>");
				sb.append("            <trade_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTradeNum() + "]]></trade_num>");
				sb.append("            <term_section_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTermSectionCd() + "]]></term_section_cd>");
				sb.append("            <internal_co_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getInternalCompanyNum() + "]]></internal_co_num>");
				sb.append("            <delivery_term_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getDeliveryTermCd() + "]]></delivery_term_cd>");
				sb.append("            <term_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTermNum() + "]]></term_num>");
				sb.append("            <buy_sell_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getBuySellInd() + "]]></buy_sell_ind>");
				sb.append("            <cmdty_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCmdtyCd() + "]]></cmdty_cd>");
				sb.append("            <company_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCompanyNum() + "]]></company_num>");
				sb.append("            <laycan_start_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanStartDt()) + "000000]]></laycan_start_dt>");
				sb.append("            <laycan_end_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanEndDt()) + "000000]]></laycan_end_dt>");
				sb.append("            <contract_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getTradeQty() + "]]></contract_qty>");
				sb.append("            <contract_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></contract_uom_cd>");
				sb.append("            <deliv_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getDelivQty() + "]]></deliv_qty>");
				sb.append("            <deliv_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getDelivUomCd() + "]]></deliv_uom_cd>");
				sb.append("            <open_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getOpenQty() + "]]></open_qty>");
				sb.append("            <price><![CDATA[" + matchObligation.getTradeInputIdPurchase().getPrice() + "]]></price>");
				sb.append("            <price_curr_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlementCurrCd() + "]]></price_curr_cd>");
				sb.append("            <price_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlementUomCd() + "]]></price_uom_cd>");
				sb.append("            <scheduled_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></scheduled_qty>");
				sb.append("            <scheduled_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></scheduled_qty_uom_cd>");
				sb.append("            <nominal_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></nominal_qty>");
				sb.append("            <nominal_qty_uom_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></nominal_qty_uom_cd>");
				sb.append("            <air_vac_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getAirVacInd() + "]]></air_vac_ind>");
				sb.append("            <cross_conv_factor><![CDATA[1]]></cross_conv_factor>");
				sb.append("            <importer_exporter_ind><![CDATA[0]]></importer_exporter_ind>");
				sb.append("            <title_transfer_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanEndDt()) + "000000]]></title_transfer_dt>");
				sb.append("            <title_transfer_event_cd><![CDATA[Bill of Lading]]></title_transfer_event_cd>");
				sb.append("            <bl_dt><![CDATA[" + new SimpleDateFormat("yyyyMMdd").format(matchObligation.getTradeInputIdPurchase().getLaycanEndDt()) + "000000]]></bl_dt>");
				sb.append("            <min_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></min_qty>");
				sb.append("            <max_qty><![CDATA[" + matchObligation.getTradeInputIdPurchase().getScheduledQty() + "]]></max_qty>");
				sb.append("            <mtm_fwd_curve_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getMtmFwdCurveNum() + "]]></mtm_fwd_curve_num>");
				sb.append("            <toSyncBLDtTLDt><![CDATA[1]]></toSyncBLDtTLDt>");
				sb.append("            <customs_duty_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getCustomsDutyStatusInd() + "]]></customs_duty_status_ind>");
				sb.append("            <sch_conf_executed_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSchConfExecutedInd() + "]]></sch_conf_executed_ind>");
				sb.append("            <override_ind><![CDATA[0]]></override_ind>");
				sb.append("            <settlement_curr_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlementCurrCd() + "]]></settlement_curr_cd>");
				sb.append("            <override_fx_ind><![CDATA[1]]></override_fx_ind>");
				sb.append("            <settle_payment_term_cd><![CDATA[" + matchObligation.getTradeInputIdPurchase().getSettlePaymentTermCd() + "]]></settle_payment_term_cd>");
				sb.append("            <pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getPricingStatusInd() + "]]></pricing_status_ind>");
				sb.append("            <futures_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getFuturesPricingStatusInd() + "]]></futures_pricing_status_ind>");
				sb.append("            <basis_pricing_status_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getBasisPricingStatusInd() + "]]></basis_pricing_status_ind>");
				sb.append("            <origin_loc_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getOriginLocNum() + "]]></origin_loc_num>");
				sb.append("            <location_num><![CDATA[" + matchObligation.getTradeInputIdPurchase().getLocation() + "]]></location_num>");
				sb.append("            <obligation_type_ind><![CDATA[" + matchObligation.getTradeInputIdPurchase().getObligationTypeInd() + "]]></obligation_type_ind>");
				sb.append("            <deliv_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></deliv_uom_cd2>");
				sb.append("            <nominal_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></nominal_qty_uom_cd2>");
				sb.append("            <scheduled_qty_uom_cd2><![CDATA[" + matchObligation.getTradeInputIdPurchase().getContractUomCd() + "]]></scheduled_qty_uom_cd2>");
				sb.append("          </OPS_OBLIGATION_DETAIL>");
				sb.append("        </OPS_OBLIGATION_MATCH>");
				sb.append("        <LockNumList>");
				sb.append("          <lockinfo><![CDATA[" + lockinfo + "]]></lockinfo>");
				sb.append("        </LockNumList>");
				sb.append("      </PutOpsBackToBack>");
				sb.append("    </data>");
				sb.append("  </mbody>");
				sb.append("</request>");
				
				SOAPHelper soapHelper = SOAPHelper.sharedInstance();
				soapHelper.setService(DefaultConstants.MATCH_OBLIGATION_SERVICE);
				soapHelper.setMethod(DefaultConstants.SAVE_MATCH_OBLIGATION_METHOD);

				try {
					String xmlResponse = soapHelper.sendRequest(sb.toString(), true, user.getUsersession());
					String obligationMatchNumber = getTagValue(xmlResponse, "obligation_match_num");
					
					if (obligationMatchNumber != null) {
						return Integer.parseInt(obligationMatchNumber);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return -1;
	}
	
	private String getLockInfoByMatch() {
		if (matchObligation.getMatchObligationNumber() > 0) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append("  <mhead name='TPTReqGetOpsBackToBack'>");
			sb.append("    <sender>{E0553842-034A-4E17-93C0-CEF593388D87}</sender>");
			sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append("  </mhead>");
			sb.append("  <mbody>");
			sb.append("    <data>");
			sb.append("      <GetOpsBackToBack>");
			sb.append("        <obligation_match_num><![CDATA[" + matchObligation.getMatchObligationNumber() + "]]></obligation_match_num>");
			sb.append("      </GetOpsBackToBack>");
			sb.append("    </data>");
			sb.append("  </mbody>");
			sb.append("</request>");
			
			SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.MATCH_OBLIGATION_SERVICE);
			soapHelper.setMethod(DefaultConstants.GET_MATCH_OBLIGATION_METHOD);
			
			try {
				String xmlResponse = soapHelper.sendRequest(sb.toString(), true, user.getUsersession());
				String lockinfo = getTagValue(xmlResponse, "lockinfo");
				
				return lockinfo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public boolean voidOpsBackToBack() {
		if (this.matchObligation.getMatchObligationNumber() > 0) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("<?xml version='1.0'?>");
			sb.append("<request xmlns:xsi='http://www.w3.org/1999/XMLSchema-instance' xsi:noNamespaceSchemaLocation='request.xsd'>");
			sb.append("  <mhead name='TPTReqCancelOpsBackToBack'>");
			sb.append("    <sender>{D83426B2-0381-43B5-9215-5DD6AC6F9CA3}</sender>");
			sb.append("    <sent_dt>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</sent_dt>");
			sb.append("  </mhead>");
			sb.append("  <mbody>");
			sb.append("    <data>");
			sb.append("      <CancelOpsBackToBack>");
			sb.append("        <obligation_match_num><![CDATA[" + this.matchObligation.getMatchObligationNumber() + "]]></obligation_match_num>");
			sb.append("      </CancelOpsBackToBack>");
			sb.append("    </data>");
			sb.append("  </mbody>");
			sb.append("</request>");
			
			SOAPHelper soapHelper = SOAPHelper.sharedInstance();
			soapHelper.setService(DefaultConstants.MATCH_OBLIGATION_SERVICE);
			soapHelper.setMethod(DefaultConstants.DELETE_MATCH_OBLIGATION_METHOD);
			
			try {
				String xmlResponse = soapHelper.sendRequest(sb.toString(), true, this.user.getUsersession());
				String obligationMatchNumber = getTagValue(xmlResponse, "obligation_match_num");
				
				if (obligationMatchNumber != null) {
					if (this.matchObligation.getMatchObligationNumber() == Integer.parseInt(obligationMatchNumber)) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
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
	
}
