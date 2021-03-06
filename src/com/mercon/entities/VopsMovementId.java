package com.mercon.entities;

// Generated Jun 18, 2014 2:27:04 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

/**
 * VopsMovementId generated by hbm2java
 */
public class VopsMovementId implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -3160631118707804860L;

	private int itineraryNum;
	private int equipmentNum;
	private int movementNum;
	private int motNum;
	private VrefMotId motNumId;
	private short motTypeInd;
	private VgenIndicatorId motId;
	private int departLocationNum;
	private VdimLocationId departLocation;
	private int arriveLocationNum;
	private VdimLocationId arriveLocation;
	private Date departDt;
	private Date arriveDt;
	private short movementStatusInd;
	private VgenIndicatorId movementStatus;
	private String predecessorStr;
	private String routeCd;
	private Integer noteNum;
	private String ourRef;
	private String theirRef;
	private short statusInd;
	private Date createDt;
	private Integer createPersonNum;
	private Date lastModifyDt;
	private int modifyPersonNum;
	private int lockNum;
	private Date arrivalLaycanStartDt;
	private Date arrivalLaycanEndDt;
	private Date departureLyacanStartDt;
	private Date departureLaycanEndDt;
	private Integer arriveTerminalNum;
	private Integer departTerminalNum;
	private Short arrivePortCompStatusInd;
	private Short departPortCompStatusInd;
	private Short arrivePortMisApprovInd;
	private Short departPortMisApprovInd;
	private String successorStr;
	private int obligationMatch;
	private int transfer;
	private Date actionCompletionDt;
	private List<VdimCargo> cargos;
	private List<VdimTransfer> transfers;
	
	public VopsMovementId() {
	}

	public VopsMovementId(int itineraryNum, int equipmentNum, int movementNum,
			int motNum, short motTypeInd, int departLocationNum,
			int arriveLocationNum, Date departDt, Date arriveDt,
			short movementStatusInd, String predecessorStr, String routeCd,
			Integer noteNum, String ourRef, String theirRef, short statusInd,
			Date createDt, Integer createPersonNum, Date lastModifyDt,
			int modifyPersonNum, int lockNum, Date arrivalLaycanStartDt,
			Date arrivalLaycanEndDt, Date departureLyacanStartDt,
			Date departureLaycanEndDt, Integer arriveTerminalNum,
			Integer departTerminalNum, Short arrivePortCompStatusInd,
			Short departPortCompStatusInd, Short arrivePortMisApprovInd,
			Short departPortMisApprovInd, String successorStr, int obligationMatch,
			int transfer) {
		this.itineraryNum = itineraryNum;
		this.equipmentNum = equipmentNum;
		this.movementNum = movementNum;
		this.motNum = motNum;
		this.motTypeInd = motTypeInd;
		this.departLocationNum = departLocationNum;
		this.arriveLocationNum = arriveLocationNum;
		this.departDt = departDt;
		this.arriveDt = arriveDt;
		this.movementStatusInd = movementStatusInd;
		this.predecessorStr = predecessorStr;
		this.routeCd = routeCd;
		this.noteNum = noteNum;
		this.ourRef = ourRef;
		this.theirRef = theirRef;
		this.statusInd = statusInd;
		this.createDt = createDt;
		this.createPersonNum = createPersonNum;
		this.lastModifyDt = lastModifyDt;
		this.modifyPersonNum = modifyPersonNum;
		this.lockNum = lockNum;
		this.arrivalLaycanStartDt = arrivalLaycanStartDt;
		this.arrivalLaycanEndDt = arrivalLaycanEndDt;
		this.departureLyacanStartDt = departureLyacanStartDt;
		this.departureLaycanEndDt = departureLaycanEndDt;
		this.arriveTerminalNum = arriveTerminalNum;
		this.departTerminalNum = departTerminalNum;
		this.arrivePortCompStatusInd = arrivePortCompStatusInd;
		this.departPortCompStatusInd = departPortCompStatusInd;
		this.arrivePortMisApprovInd = arrivePortMisApprovInd;
		this.departPortMisApprovInd = departPortMisApprovInd;
		this.successorStr = successorStr;
		this.obligationMatch = obligationMatch;
		this.transfer = transfer;
	}

	public int getItineraryNum() {
		return this.itineraryNum;
	}

	public void setItineraryNum(int itineraryNum) {
		this.itineraryNum = itineraryNum;
	}

	public int getEquipmentNum() {
		return this.equipmentNum;
	}

	public void setEquipmentNum(int equipmentNum) {
		this.equipmentNum = equipmentNum;
	}

	public int getMovementNum() {
		return this.movementNum;
	}

	public void setMovementNum(int movementNum) {
		this.movementNum = movementNum;
	}

	public int getMotNum() {
		return this.motNum;
	}

	public void setMotNum(int motNum) {
		this.motNum = motNum;
	}
	
	public VrefMotId getMotNumId() {
		return motNumId;
	}

	public void setMotNumId(VrefMotId motNumId) {
		this.motNumId = motNumId;
	}

	public VgenIndicatorId getMotId() {
		return motId;
	}

	public void setMotId(VgenIndicatorId motId) {
		this.motId = motId;
	}

	public short getMotTypeInd() {
		return this.motTypeInd;
	}

	public void setMotTypeInd(short motTypeInd) {
		this.motTypeInd = motTypeInd;
	}

	public int getDepartLocationNum() {
		return this.departLocationNum;
	}

	public void setDepartLocationNum(int departLocationNum) {
		this.departLocationNum = departLocationNum;
	}
	
	public VdimLocationId getDepartLocation() {
		return departLocation;
	}

	public void setDepartLocation(VdimLocationId departLocation) {
		this.departLocation = departLocation;
	}

	public int getArriveLocationNum() {
		return this.arriveLocationNum;
	}

	public void setArriveLocationNum(int arriveLocationNum) {
		this.arriveLocationNum = arriveLocationNum;
	}
	
	public VdimLocationId getArriveLocation() {
		return arriveLocation;
	}

	public void setArriveLocation(VdimLocationId arriveLocation) {
		this.arriveLocation = arriveLocation;
	}

	public Date getDepartDt() {
		return this.departDt;
	}

	public void setDepartDt(Date departDt) {
		this.departDt = departDt;
	}

	public Date getArriveDt() {
		return this.arriveDt;
	}

	public void setArriveDt(Date arriveDt) {
		this.arriveDt = arriveDt;
	}

	public short getMovementStatusInd() {
		return this.movementStatusInd;
	}

	public void setMovementStatusInd(short movementStatusInd) {
		this.movementStatusInd = movementStatusInd;
	}

	public VgenIndicatorId getMovementStatus() {
		return movementStatus;
	}

	public void setMovementStatus(VgenIndicatorId movementStatus) {
		this.movementStatus = movementStatus;
	}

	public String getPredecessorStr() {
		return this.predecessorStr;
	}

	public void setPredecessorStr(String predecessorStr) {
		this.predecessorStr = predecessorStr;
	}

	public String getRouteCd() {
		return this.routeCd;
	}

	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	public Integer getNoteNum() {
		return this.noteNum;
	}

	public void setNoteNum(Integer noteNum) {
		this.noteNum = noteNum;
	}

	public String getOurRef() {
		return this.ourRef;
	}

	public void setOurRef(String ourRef) {
		this.ourRef = ourRef;
	}

	public String getTheirRef() {
		return this.theirRef;
	}

	public void setTheirRef(String theirRef) {
		this.theirRef = theirRef;
	}

	public short getStatusInd() {
		return this.statusInd;
	}

	public void setStatusInd(short statusInd) {
		this.statusInd = statusInd;
	}

	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Integer getCreatePersonNum() {
		return this.createPersonNum;
	}

	public void setCreatePersonNum(Integer createPersonNum) {
		this.createPersonNum = createPersonNum;
	}

	public Date getLastModifyDt() {
		return this.lastModifyDt;
	}

	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}

	public int getModifyPersonNum() {
		return this.modifyPersonNum;
	}

	public void setModifyPersonNum(int modifyPersonNum) {
		this.modifyPersonNum = modifyPersonNum;
	}

	public int getLockNum() {
		return this.lockNum;
	}

	public void setLockNum(int lockNum) {
		this.lockNum = lockNum;
	}

	public Date getArrivalLaycanStartDt() {
		return this.arrivalLaycanStartDt;
	}

	public void setArrivalLaycanStartDt(Date arrivalLaycanStartDt) {
		this.arrivalLaycanStartDt = arrivalLaycanStartDt;
	}

	public Date getArrivalLaycanEndDt() {
		return this.arrivalLaycanEndDt;
	}

	public void setArrivalLaycanEndDt(Date arrivalLaycanEndDt) {
		this.arrivalLaycanEndDt = arrivalLaycanEndDt;
	}

	public Date getDepartureLyacanStartDt() {
		return this.departureLyacanStartDt;
	}

	public void setDepartureLyacanStartDt(Date departureLyacanStartDt) {
		this.departureLyacanStartDt = departureLyacanStartDt;
	}

	public Date getDepartureLaycanEndDt() {
		return this.departureLaycanEndDt;
	}

	public void setDepartureLaycanEndDt(Date departureLaycanEndDt) {
		this.departureLaycanEndDt = departureLaycanEndDt;
	}

	public Integer getArriveTerminalNum() {
		return this.arriveTerminalNum;
	}

	public void setArriveTerminalNum(Integer arriveTerminalNum) {
		this.arriveTerminalNum = arriveTerminalNum;
	}

	public Integer getDepartTerminalNum() {
		return this.departTerminalNum;
	}

	public void setDepartTerminalNum(Integer departTerminalNum) {
		this.departTerminalNum = departTerminalNum;
	}

	public Short getArrivePortCompStatusInd() {
		return this.arrivePortCompStatusInd;
	}

	public void setArrivePortCompStatusInd(Short arrivePortCompStatusInd) {
		this.arrivePortCompStatusInd = arrivePortCompStatusInd;
	}

	public Short getDepartPortCompStatusInd() {
		return this.departPortCompStatusInd;
	}

	public void setDepartPortCompStatusInd(Short departPortCompStatusInd) {
		this.departPortCompStatusInd = departPortCompStatusInd;
	}

	public Short getArrivePortMisApprovInd() {
		return this.arrivePortMisApprovInd;
	}

	public void setArrivePortMisApprovInd(Short arrivePortMisApprovInd) {
		this.arrivePortMisApprovInd = arrivePortMisApprovInd;
	}

	public Short getDepartPortMisApprovInd() {
		return this.departPortMisApprovInd;
	}

	public void setDepartPortMisApprovInd(Short departPortMisApprovInd) {
		this.departPortMisApprovInd = departPortMisApprovInd;
	}

	public String getSuccessorStr() {
		return this.successorStr;
	}

	public void setSuccessorStr(String successorStr) {
		this.successorStr = successorStr;
	}
	
	public int getObligationMatch() {
		return obligationMatch;
	}

	public void setObligationMatch(int obligationMatch) {
		this.obligationMatch = obligationMatch;
	}
	
	public int getTransfer() {
		return transfer;
	}

	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}

	public Date getActionCompletionDt() {
		return actionCompletionDt;
	}

	public void setActionCompletionDt(Date actionCompletionDt) {
		this.actionCompletionDt = actionCompletionDt;
	}

	public List<VdimCargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<VdimCargo> cargos) {
		this.cargos = cargos;
	}
	
	public List<VdimTransfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<VdimTransfer> transfers) {
		this.transfers = transfers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arrivalLaycanEndDt == null) ? 0 : arrivalLaycanEndDt
						.hashCode());
		result = prime
				* result
				+ ((arrivalLaycanStartDt == null) ? 0 : arrivalLaycanStartDt
						.hashCode());
		result = prime * result
				+ ((arriveDt == null) ? 0 : arriveDt.hashCode());
		result = prime * result
				+ ((arriveLocation == null) ? 0 : arriveLocation.hashCode());
		result = prime * result + arriveLocationNum;
		result = prime
				* result
				+ ((arrivePortCompStatusInd == null) ? 0
						: arrivePortCompStatusInd.hashCode());
		result = prime
				* result
				+ ((arrivePortMisApprovInd == null) ? 0
						: arrivePortMisApprovInd.hashCode());
		result = prime
				* result
				+ ((arriveTerminalNum == null) ? 0 : arriveTerminalNum
						.hashCode());
		result = prime * result + ((cargos == null) ? 0 : cargos.hashCode());
		result = prime * result
				+ ((createDt == null) ? 0 : createDt.hashCode());
		result = prime * result
				+ ((createPersonNum == null) ? 0 : createPersonNum.hashCode());
		result = prime * result
				+ ((departDt == null) ? 0 : departDt.hashCode());
		result = prime * result
				+ ((departLocation == null) ? 0 : departLocation.hashCode());
		result = prime * result + departLocationNum;
		result = prime
				* result
				+ ((departPortCompStatusInd == null) ? 0
						: departPortCompStatusInd.hashCode());
		result = prime
				* result
				+ ((departPortMisApprovInd == null) ? 0
						: departPortMisApprovInd.hashCode());
		result = prime
				* result
				+ ((departTerminalNum == null) ? 0 : departTerminalNum
						.hashCode());
		result = prime
				* result
				+ ((departureLaycanEndDt == null) ? 0 : departureLaycanEndDt
						.hashCode());
		result = prime
				* result
				+ ((departureLyacanStartDt == null) ? 0
						: departureLyacanStartDt.hashCode());
		result = prime * result + equipmentNum;
		result = prime * result + itineraryNum;
		result = prime * result
				+ ((lastModifyDt == null) ? 0 : lastModifyDt.hashCode());
		result = prime * result + lockNum;
		result = prime * result + modifyPersonNum;
		result = prime * result + ((motId == null) ? 0 : motId.hashCode());
		result = prime * result + motNum;
		result = prime * result
				+ ((motNumId == null) ? 0 : motNumId.hashCode());
		result = prime * result + motTypeInd;
		result = prime * result + movementNum;
		result = prime * result
				+ ((movementStatus == null) ? 0 : movementStatus.hashCode());
		result = prime * result + movementStatusInd;
		result = prime * result + ((noteNum == null) ? 0 : noteNum.hashCode());
		result = prime * result + obligationMatch;
		result = prime * result + ((ourRef == null) ? 0 : ourRef.hashCode());
		result = prime * result
				+ ((predecessorStr == null) ? 0 : predecessorStr.hashCode());
		result = prime * result + ((routeCd == null) ? 0 : routeCd.hashCode());
		result = prime * result + statusInd;
		result = prime * result
				+ ((successorStr == null) ? 0 : successorStr.hashCode());
		result = prime * result
				+ ((theirRef == null) ? 0 : theirRef.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VopsMovementId other = (VopsMovementId) obj;
		if (arrivalLaycanEndDt == null) {
			if (other.arrivalLaycanEndDt != null)
				return false;
		} else if (!arrivalLaycanEndDt.equals(other.arrivalLaycanEndDt))
			return false;
		if (arrivalLaycanStartDt == null) {
			if (other.arrivalLaycanStartDt != null)
				return false;
		} else if (!arrivalLaycanStartDt.equals(other.arrivalLaycanStartDt))
			return false;
		if (arriveDt == null) {
			if (other.arriveDt != null)
				return false;
		} else if (!arriveDt.equals(other.arriveDt))
			return false;
		if (arriveLocation == null) {
			if (other.arriveLocation != null)
				return false;
		} else if (!arriveLocation.equals(other.arriveLocation))
			return false;
		if (arriveLocationNum != other.arriveLocationNum)
			return false;
		if (arrivePortCompStatusInd == null) {
			if (other.arrivePortCompStatusInd != null)
				return false;
		} else if (!arrivePortCompStatusInd
				.equals(other.arrivePortCompStatusInd))
			return false;
		if (arrivePortMisApprovInd == null) {
			if (other.arrivePortMisApprovInd != null)
				return false;
		} else if (!arrivePortMisApprovInd.equals(other.arrivePortMisApprovInd))
			return false;
		if (arriveTerminalNum == null) {
			if (other.arriveTerminalNum != null)
				return false;
		} else if (!arriveTerminalNum.equals(other.arriveTerminalNum))
			return false;
		if (cargos == null) {
			if (other.cargos != null)
				return false;
		} else if (!cargos.equals(other.cargos))
			return false;
		if (createDt == null) {
			if (other.createDt != null)
				return false;
		} else if (!createDt.equals(other.createDt))
			return false;
		if (createPersonNum == null) {
			if (other.createPersonNum != null)
				return false;
		} else if (!createPersonNum.equals(other.createPersonNum))
			return false;
		if (departDt == null) {
			if (other.departDt != null)
				return false;
		} else if (!departDt.equals(other.departDt))
			return false;
		if (departLocation == null) {
			if (other.departLocation != null)
				return false;
		} else if (!departLocation.equals(other.departLocation))
			return false;
		if (departLocationNum != other.departLocationNum)
			return false;
		if (departPortCompStatusInd == null) {
			if (other.departPortCompStatusInd != null)
				return false;
		} else if (!departPortCompStatusInd
				.equals(other.departPortCompStatusInd))
			return false;
		if (departPortMisApprovInd == null) {
			if (other.departPortMisApprovInd != null)
				return false;
		} else if (!departPortMisApprovInd.equals(other.departPortMisApprovInd))
			return false;
		if (departTerminalNum == null) {
			if (other.departTerminalNum != null)
				return false;
		} else if (!departTerminalNum.equals(other.departTerminalNum))
			return false;
		if (departureLaycanEndDt == null) {
			if (other.departureLaycanEndDt != null)
				return false;
		} else if (!departureLaycanEndDt.equals(other.departureLaycanEndDt))
			return false;
		if (departureLyacanStartDt == null) {
			if (other.departureLyacanStartDt != null)
				return false;
		} else if (!departureLyacanStartDt.equals(other.departureLyacanStartDt))
			return false;
		if (equipmentNum != other.equipmentNum)
			return false;
		if (itineraryNum != other.itineraryNum)
			return false;
		if (lastModifyDt == null) {
			if (other.lastModifyDt != null)
				return false;
		} else if (!lastModifyDt.equals(other.lastModifyDt))
			return false;
		if (lockNum != other.lockNum)
			return false;
		if (modifyPersonNum != other.modifyPersonNum)
			return false;
		if (motId == null) {
			if (other.motId != null)
				return false;
		} else if (!motId.equals(other.motId))
			return false;
		if (motNum != other.motNum)
			return false;
		if (motNumId == null) {
			if (other.motNumId != null)
				return false;
		} else if (!motNumId.equals(other.motNumId))
			return false;
		if (motTypeInd != other.motTypeInd)
			return false;
		if (movementNum != other.movementNum)
			return false;
		if (movementStatus == null) {
			if (other.movementStatus != null)
				return false;
		} else if (!movementStatus.equals(other.movementStatus))
			return false;
		if (movementStatusInd != other.movementStatusInd)
			return false;
		if (noteNum == null) {
			if (other.noteNum != null)
				return false;
		} else if (!noteNum.equals(other.noteNum))
			return false;
		if (obligationMatch != other.obligationMatch)
			return false;
		if (ourRef == null) {
			if (other.ourRef != null)
				return false;
		} else if (!ourRef.equals(other.ourRef))
			return false;
		if (predecessorStr == null) {
			if (other.predecessorStr != null)
				return false;
		} else if (!predecessorStr.equals(other.predecessorStr))
			return false;
		if (routeCd == null) {
			if (other.routeCd != null)
				return false;
		} else if (!routeCd.equals(other.routeCd))
			return false;
		if (statusInd != other.statusInd)
			return false;
		if (successorStr == null) {
			if (other.successorStr != null)
				return false;
		} else if (!successorStr.equals(other.successorStr))
			return false;
		if (theirRef == null) {
			if (other.theirRef != null)
				return false;
		} else if (!theirRef.equals(other.theirRef))
			return false;
		return true;
	}
	
}
