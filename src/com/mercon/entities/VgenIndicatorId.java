package com.mercon.entities;

// Generated Oct 13, 2014 4:04:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * VgenIndicatorId generated by hbm2java
 */
public class VgenIndicatorId implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -6888704949767032086L;

	private String indName;
	private short indValue;
	private String indValueName;
	private int sortSeq;
	private Date lastModifyDt;
	private short defaultInd;

	public VgenIndicatorId() {
	}

	public VgenIndicatorId(String indName, short indValue, int sortSeq,
			Date lastModifyDt, short defaultInd) {
		this.indName = indName;
		this.indValue = indValue;
		this.sortSeq = sortSeq;
		this.lastModifyDt = lastModifyDt;
		this.defaultInd = defaultInd;
	}

	public VgenIndicatorId(String indName, short indValue, String indValueName,
			int sortSeq, Date lastModifyDt, short defaultInd) {
		this.indName = indName;
		this.indValue = indValue;
		this.indValueName = indValueName;
		this.sortSeq = sortSeq;
		this.lastModifyDt = lastModifyDt;
		this.defaultInd = defaultInd;
	}

	public String getIndName() {
		return this.indName;
	}

	public void setIndName(String indName) {
		this.indName = indName;
	}

	public short getIndValue() {
		return this.indValue;
	}

	public void setIndValue(short indValue) {
		this.indValue = indValue;
	}

	public String getIndValueName() {
		return this.indValueName;
	}

	public void setIndValueName(String indValueName) {
		this.indValueName = indValueName;
	}

	public int getSortSeq() {
		return this.sortSeq;
	}

	public void setSortSeq(int sortSeq) {
		this.sortSeq = sortSeq;
	}

	public Date getLastModifyDt() {
		return this.lastModifyDt;
	}

	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}

	public short getDefaultInd() {
		return this.defaultInd;
	}

	public void setDefaultInd(short defaultInd) {
		this.defaultInd = defaultInd;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VgenIndicatorId))
			return false;
		VgenIndicatorId castOther = (VgenIndicatorId) other;

		return ((this.getIndName() == castOther.getIndName()) || (this
				.getIndName() != null && castOther.getIndName() != null && this
				.getIndName().equals(castOther.getIndName())))
				&& (this.getIndValue() == castOther.getIndValue())
				&& ((this.getIndValueName() == castOther.getIndValueName()) || (this
						.getIndValueName() != null
						&& castOther.getIndValueName() != null && this
						.getIndValueName().equals(castOther.getIndValueName())))
				&& (this.getSortSeq() == castOther.getSortSeq())
				&& ((this.getLastModifyDt() == castOther.getLastModifyDt()) || (this
						.getLastModifyDt() != null
						&& castOther.getLastModifyDt() != null && this
						.getLastModifyDt().equals(castOther.getLastModifyDt())))
				&& (this.getDefaultInd() == castOther.getDefaultInd());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIndName() == null ? 0 : this.getIndName().hashCode());
		result = 37 * result + this.getIndValue();
		result = 37
				* result
				+ (getIndValueName() == null ? 0 : this.getIndValueName()
						.hashCode());
		result = 37 * result + this.getSortSeq();
		result = 37
				* result
				+ (getLastModifyDt() == null ? 0 : this.getLastModifyDt()
						.hashCode());
		result = 37 * result + this.getDefaultInd();
		return result;
	}

}
