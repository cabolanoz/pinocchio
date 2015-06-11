package com.mercon.cxl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mercon.entities.VbuildDrawTag;
import com.mercon.entities.VdimCargoId;
import com.mercon.entities.VdimLocationId;
import com.mercon.entities.VdimMovementId;
import com.mercon.entities.VdimStorageId;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.entities.VopsObligationHdr;
import com.mercon.entities.VtradeInputId;

public class Transfer implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4587421765234291728L;

	/**
	 * Common variables
	 */
	private int transferNum;
	private VgenIndicatorId fromType;
	private VgenIndicatorId toType;
	private Date effectiveDt;
	private Date commencementDt;
	private VdimLocationId location;
	private String blNumber;
	private Date blDate;
	
	/**
	 * From variables
	 */
	// Trade
	private VtradeInputId fromTrade;
	private BigDecimal fromScheduledQty;
	private VopsObligationHdr fromObligation;
	private VdimMovementId fromMovement;
	private Double fromConversionFactor;
	
	// Storage
	private VdimStorageId fromStorage;
	private VdimCargoId fromLevel;
	private BigDecimal fromCargoQty;
	private VgenIndicatorId fromTransferAt;
	
	/**
	 * To Variables
	 */
	// Storage
	private VdimStorageId toStorage;
	private VdimCargoId toLevel;
	private BigDecimal toCargoQty;
	private BigDecimal toStorageQty;
	private VgenIndicatorId toTransferAt;
	
	// Trade
	private VtradeInputId toTrade;
	private BigDecimal toScheduledQty;
	private VopsObligationHdr toObligation;
	
	/**
	 * Tags
	 */
	private List<VbuildDrawTag> tags;
	
	public Transfer() { }
	
	public int getTransferNum() {
		return transferNum;
	}

	public void setTransferNum(int transferNum) {
		this.transferNum = transferNum;
	}

	public VgenIndicatorId getFromType() {
		return fromType;
	}

	public void setFromType(VgenIndicatorId fromType) {
		this.fromType = fromType;
	}

	public VgenIndicatorId getToType() {
		return toType;
	}

	public void setToType(VgenIndicatorId toType) {
		this.toType = toType;
	}
	
	public Date getEffectiveDt() {
		return effectiveDt;
	}

	public void setEffectiveDt(Date effectiveDt) {
		this.effectiveDt = effectiveDt;
	}

	public Date getCommencementDt() {
		return commencementDt;
	}

	public void setCommencementDt(Date commencementDt) {
		this.commencementDt = commencementDt;
	}

	public VdimLocationId getLocation() {
		return location;
	}

	public void setLocation(VdimLocationId location) {
		this.location = location;
	}
	
	public String getBlNumber() {
		return blNumber;
	}

	public void setBlNumber(String blNumber) {
		this.blNumber = blNumber;
	}

	public Date getBlDate() {
		return blDate;
	}

	public void setBlDate(Date blDate) {
		this.blDate = blDate;
	}

	public VtradeInputId getFromTrade() {
		return fromTrade;
	}

	public void setFromTrade(VtradeInputId fromTrade) {
		this.fromTrade = fromTrade;
	}
	
	public BigDecimal getFromScheduledQty() {
		return fromScheduledQty;
	}

	public void setFromScheduledQty(BigDecimal fromScheduledQty) {
		this.fromScheduledQty = fromScheduledQty;
	}

	public VopsObligationHdr getFromObligation() {
		return fromObligation;
	}

	public void setFromObligation(VopsObligationHdr fromObligation) {
		this.fromObligation = fromObligation;
	}

	public VdimMovementId getFromMovement() {
		return fromMovement;
	}

	public void setFromMovement(VdimMovementId fromMovement) {
		this.fromMovement = fromMovement;
	}
	
	public Double getFromConversionFactor() {
		return fromConversionFactor;
	}

	public void setFromConversionFactor(Double fromConversionFactor) {
		this.fromConversionFactor = fromConversionFactor;
	}
	
	public VdimStorageId getFromStorage() {
		return fromStorage;
	}

	public void setFromStorage(VdimStorageId fromStorage) {
		this.fromStorage = fromStorage;
	}

	public VdimCargoId getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(VdimCargoId fromLevel) {
		this.fromLevel = fromLevel;
	}

	public BigDecimal getFromCargoQty() {
		return fromCargoQty;
	}

	public void setFromCargoQty(BigDecimal fromCargoQty) {
		this.fromCargoQty = fromCargoQty;
	}

	public VgenIndicatorId getFromTransferAt() {
		return fromTransferAt;
	}

	public void setFromTransferAt(VgenIndicatorId fromTransferAt) {
		this.fromTransferAt = fromTransferAt;
	}

	public VdimStorageId getToStorage() {
		return toStorage;
	}

	public void setToStorage(VdimStorageId toStorage) {
		this.toStorage = toStorage;
	}

	public VdimCargoId getToLevel() {
		return toLevel;
	}

	public void setToLevel(VdimCargoId toLevel) {
		this.toLevel = toLevel;
	}
	
	public BigDecimal getToCargoQty() {
		return toCargoQty;
	}

	public void setToCargoQty(BigDecimal toCargoQty) {
		this.toCargoQty = toCargoQty;
	}
	
	public BigDecimal getToStorageQty() {
		return toStorageQty;
	}

	public void setToStorageQty(BigDecimal toStorageQty) {
		this.toStorageQty = toStorageQty;
	}

	public VgenIndicatorId getToTransferAt() {
		return toTransferAt;
	}

	public void setToTransferAt(VgenIndicatorId toTransferAt) {
		this.toTransferAt = toTransferAt;
	}
	
	public VtradeInputId getToTrade() {
		return toTrade;
	}

	public void setToTrade(VtradeInputId toTrade) {
		this.toTrade = toTrade;
	}

	public BigDecimal getToScheduledQty() {
		return toScheduledQty;
	}

	public void setToScheduledQty(BigDecimal toScheduledQty) {
		this.toScheduledQty = toScheduledQty;
	}

	public VopsObligationHdr getToObligation() {
		return toObligation;
	}

	public void setToObligation(VopsObligationHdr toObligation) {
		this.toObligation = toObligation;
	}

	public List<VbuildDrawTag> getTags() {
		return tags;
	}

	public void setTags(List<VbuildDrawTag> tags) {
		this.tags = tags;
	}
	
}
