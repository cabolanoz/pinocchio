package com.mercon.cxl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mercon.entities.VdimCargoId;
import com.mercon.entities.VdimCompanyId;
import com.mercon.entities.VdimMovementId;
import com.mercon.entities.VdimPersonId;

public class Itinerary implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5779801510076382779L;

	private int itineraryNumber;
	private String itineraryName;
	private VdimCompanyId companyId;
	private VdimPersonId personId;
	private short itineraryStatusInd;
	private short statusInd;
	private Date createDt;
	private Date modifyDt;
	private int modifyPersonId;
	private List<VdimMovementId> movementIds;
	private VdimCargoId cargoId;
	
	public Itinerary() { }
	
	public int getItineraryNumber() {
		return itineraryNumber;
	}

	public void setItineraryNumber(int itineraryNumber) {
		this.itineraryNumber = itineraryNumber;
	}

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
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
	
	public short getItineraryStatusInd() {
		return itineraryStatusInd;
	}

	public void setItineraryStatusInd(short itineraryStatusInd) {
		this.itineraryStatusInd = itineraryStatusInd;
	}

	public short getStatusInd() {
		return statusInd;
	}

	public void setStatusInd(short statusInd) {
		this.statusInd = statusInd;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
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

	public List<VdimMovementId> getMovementIds() {
		return movementIds;
	}

	public void setMovementIds(List<VdimMovementId> movementIds) {
		this.movementIds = movementIds;
	}

	public VdimCargoId getCargoId() {
		return cargoId;
	}

	public void setCargoId(VdimCargoId cargoId) {
		this.cargoId = cargoId;
	}
	
}
