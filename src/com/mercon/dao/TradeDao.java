package com.mercon.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VtradeHdr;
import com.mercon.entities.VtradeInput;
import com.mercon.entities.VtradeInputId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "tradeService", eager = true)
@ApplicationScoped
public class TradeDao implements DefaultConstants {

	private static TradeDao tradeDao;
	
	private VtradeInputId tradesId;

	public static TradeDao sharedInstance() {
		if (tradeDao == null) {
			tradeDao = new TradeDao();
		}
		
		return tradeDao;
	}
	
	public VtradeInputId getTradeInputByTradeNumber(int tradeNum) {
		ClientRequest request = null;
		ClientResponse<Object> response = null;
		VtradeInput[] p = null;
		request = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Trade/getTradeInput/" + tradeNum);
		request.accept("application/json;charset=UTF-8");

		try {
			response = request.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.getStatus() != 200) {
			ErrorResponse er = (ErrorResponse) response.getEntity(ErrorResponse.class);
			try {
				throw new Exception("Error getting trade Input: " + er.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			p = (VtradeInput[]) response.getEntity(VtradeInput[].class);
			List<VtradeInput> trades = Arrays.asList(p);
			
			if (!trades.isEmpty() && trades.size() > 0) {
				tradesId = trades.get(0).getId();
			}
		}
		
		return tradesId;
	}
	
	public VtradeInputId getTradeByIdAndObligation(int tradeNum, int obligationNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Trade/getTradeInput/" + tradeNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting trade header: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VtradeInput[] tradeInput = (VtradeInput[]) clientResponse.getEntity(VtradeInput[].class);
			
			if (tradeInput != null && tradeInput.length > 0) {
				for (VtradeInput trade : tradeInput) {
					if (trade.getId().getObligationNum() == obligationNum) {
						return trade.getId();
					}
				}
			}
		}
		
		return null;
	}
	
	public VtradeHdr getTradeHdrById(int trade) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Trade/getTradeHdrById/" + trade);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting trade header: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VtradeHdr tradeHdr = (VtradeHdr) clientResponse.getEntity(VtradeHdr.class);
			
			return tradeHdr;
		}
		
		return null;
	}
	
	public List<VtradeHdr> getTradeHdrLikeId(String trade) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Trade/getTradeHdrLikeId/" + trade);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting trade header: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VtradeHdr[] tradeHdr = (VtradeHdr[]) clientResponse.getEntity(VtradeHdr[].class);
			
			return Arrays.asList(tradeHdr);
		}
		
		return new ArrayList<VtradeHdr>();
	}
	
	public VtradeInputId getTradeInputId() {
		return tradesId;
	}
	
}
