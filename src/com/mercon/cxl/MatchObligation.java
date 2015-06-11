package com.mercon.cxl;

import java.io.Serializable;
import java.util.Date;

import com.mercon.entities.VdimCompanyId;
import com.mercon.entities.VdimPersonId;
import com.mercon.entities.VtradeInputId;

public class MatchObligation implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 9096362676645920463L;

	private int matchObligationNumber;
	private Date matchDt;
	private VdimCompanyId companyId;
	private VdimPersonId personId;
	private String matchDescription;
	private VtradeInputId tradeInputIdPurchase;
	private VtradeInputId tradeInputIdSell;
	private Date modifyDt;
	private int modifyPersonId;
	
	public MatchObligation() { }
	
	public int getMatchObligationNumber() {
		return matchObligationNumber;
	}

	public void setMatchObligationNumber(int matchObligationNumber) {
		this.matchObligationNumber = matchObligationNumber;
	}
	
	public Date getMatchDt() {
		return matchDt;
	}

	public void setMatchDt(Date matchDt) {
		this.matchDt = matchDt;
	}

	public VdimCompanyId getCompanyId() {
		return companyId;
	}

	public void setCompanyId(VdimCompanyId companyId) {
		this.companyId = companyId;
	}

	public VdimPersonId getPersonId() {
		return personId;
	}

	public void setPersonId(VdimPersonId personId) {
		this.personId = personId;
	}
	
	public String getMatchDescription() {
		return matchDescription;
	}

	public void setMatchDescription(String matchDescription) {
		this.matchDescription = matchDescription;
	}

	public VtradeInputId getTradeInputIdPurchase() {
		return tradeInputIdPurchase;
	}

	public void setTradeInputIdPurchase(VtradeInputId tradeInputIdPurchase) {
		this.tradeInputIdPurchase = tradeInputIdPurchase;
	}

	public VtradeInputId getTradeInputIdSell() {
		return tradeInputIdSell;
	}

	public void setTradeInputIdSell(VtradeInputId tradeInputIdSell) {
		this.tradeInputIdSell = tradeInputIdSell;
	}

	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public int getModifyPersonId() {
		return modifyPersonId;
	}

	public void setModifyPersonId(int modifyPersonId) {
		this.modifyPersonId = modifyPersonId;
	}
	
}
