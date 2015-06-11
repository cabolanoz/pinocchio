package com.mercon.entities;

import java.io.Serializable;
import java.util.Date;

public class VrefMotId implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 4996649418334092197L;

	private int motNum;
	private String motCd;
	private short motTypeInd;
	private short statusInd;
	private Date lastModifyDt;
	private int modifyPersonNum;
	private int lockNum; 
	
	public VrefMotId() { }

	public VrefMotId(int motNum, String motCd, short motTypeInd,
			short statusInd, Date lastModifyDt, int modifyPersonNum, int lockNum) {
		super();
		this.motNum = motNum;
		this.motCd = motCd;
		this.motTypeInd = motTypeInd;
		this.statusInd = statusInd;
		this.lastModifyDt = lastModifyDt;
		this.modifyPersonNum = modifyPersonNum;
		this.lockNum = lockNum;
	}

	public int getMotNum() {
		return motNum;
	}

	public void setMotNum(int motNum) {
		this.motNum = motNum;
	}

	public String getMotCd() {
		return motCd;
	}

	public void setMotCd(String motCd) {
		this.motCd = motCd;
	}

	public short getMotTypeInd() {
		return motTypeInd;
	}

	public void setMotTypeInd(short motTypeInd) {
		this.motTypeInd = motTypeInd;
	}

	public short getStatusInd() {
		return statusInd;
	}

	public void setStatusInd(short statusInd) {
		this.statusInd = statusInd;
	}

	public Date getLastModifyDt() {
		return lastModifyDt;
	}

	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}

	public int getModifyPersonNum() {
		return modifyPersonNum;
	}

	public void setModifyPersonNum(int modifyPersonNum) {
		this.modifyPersonNum = modifyPersonNum;
	}

	public int getLockNum() {
		return lockNum;
	}

	public void setLockNum(int lockNum) {
		this.lockNum = lockNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastModifyDt == null) ? 0 : lastModifyDt.hashCode());
		result = prime * result + lockNum;
		result = prime * result + modifyPersonNum;
		result = prime * result + ((motCd == null) ? 0 : motCd.hashCode());
		result = prime * result + motNum;
		result = prime * result + motTypeInd;
		result = prime * result + statusInd;
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
		VrefMotId other = (VrefMotId) obj;
		if (lastModifyDt == null) {
			if (other.lastModifyDt != null)
				return false;
		} else if (!lastModifyDt.equals(other.lastModifyDt))
			return false;
		if (lockNum != other.lockNum)
			return false;
		if (modifyPersonNum != other.modifyPersonNum)
			return false;
		if (motCd == null) {
			if (other.motCd != null)
				return false;
		} else if (!motCd.equals(other.motCd))
			return false;
		if (motNum != other.motNum)
			return false;
		if (motTypeInd != other.motTypeInd)
			return false;
		if (statusInd != other.statusInd)
			return false;
		return true;
	}
	
}
