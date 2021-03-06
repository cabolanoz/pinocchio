package com.mercon.entities;

// Generated 09-24-2012 03:30:27 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * VdimUnitOfMeasureId generated by hbm2java
 */
public class VdimUnitOfMeasureId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codUom;
	private short indUomType;
	private Double factUom;
	private String nameUom;
	private short indPriceUomPeriod;
	private short indRestrictedAction;
	private short indPackagingUom;
	private short indNoteNumStatus;
	private Date dtLastModify;
	private int numPersonModify;
	private int numLock;
	private double factUomIso;
	private short indSourceType;
	private short indDimesionlessUom;

	public VdimUnitOfMeasureId() {
	}

	public VdimUnitOfMeasureId(String codUom, short indUomType,
			short indPriceUomPeriod, short indRestrictedAction,
			short indPackagingUom, short indNoteNumStatus, Date dtLastModify,
			int numPersonModify, int numLock, double factUomIso,
			short indSourceType, short indDimesionlessUom) {
		this.codUom = codUom;
		this.indUomType = indUomType;
		this.indPriceUomPeriod = indPriceUomPeriod;
		this.indRestrictedAction = indRestrictedAction;
		this.indPackagingUom = indPackagingUom;
		this.indNoteNumStatus = indNoteNumStatus;
		this.dtLastModify = dtLastModify;
		this.numPersonModify = numPersonModify;
		this.numLock = numLock;
		this.factUomIso = factUomIso;
		this.indSourceType = indSourceType;
		this.indDimesionlessUom = indDimesionlessUom;
	}

	public VdimUnitOfMeasureId(String codUom, short indUomType, Double factUom,
			String nameUom, short indPriceUomPeriod, short indRestrictedAction,
			short indPackagingUom, short indNoteNumStatus, Date dtLastModify,
			int numPersonModify, int numLock, double factUomIso,
			short indSourceType, short indDimesionlessUom) {
		this.codUom = codUom;
		this.indUomType = indUomType;
		this.factUom = factUom;
		this.nameUom = nameUom;
		this.indPriceUomPeriod = indPriceUomPeriod;
		this.indRestrictedAction = indRestrictedAction;
		this.indPackagingUom = indPackagingUom;
		this.indNoteNumStatus = indNoteNumStatus;
		this.dtLastModify = dtLastModify;
		this.numPersonModify = numPersonModify;
		this.numLock = numLock;
		this.factUomIso = factUomIso;
		this.indSourceType = indSourceType;
		this.indDimesionlessUom = indDimesionlessUom;
	}

	public String getCodUom() {
		return this.codUom;
	}

	public void setCodUom(String codUom) {
		this.codUom = codUom;
	}

	public short getIndUomType() {
		return this.indUomType;
	}

	public void setIndUomType(short indUomType) {
		this.indUomType = indUomType;
	}

	public Double getFactUom() {
		return this.factUom;
	}

	public void setFactUom(Double factUom) {
		this.factUom = factUom;
	}

	public String getNameUom() {
		return this.nameUom;
	}

	public void setNameUom(String nameUom) {
		this.nameUom = nameUom;
	}

	public short getIndPriceUomPeriod() {
		return this.indPriceUomPeriod;
	}

	public void setIndPriceUomPeriod(short indPriceUomPeriod) {
		this.indPriceUomPeriod = indPriceUomPeriod;
	}

	public short getIndRestrictedAction() {
		return this.indRestrictedAction;
	}

	public void setIndRestrictedAction(short indRestrictedAction) {
		this.indRestrictedAction = indRestrictedAction;
	}

	public short getIndPackagingUom() {
		return this.indPackagingUom;
	}

	public void setIndPackagingUom(short indPackagingUom) {
		this.indPackagingUom = indPackagingUom;
	}

	public short getIndNoteNumStatus() {
		return this.indNoteNumStatus;
	}

	public void setIndNoteNumStatus(short indNoteNumStatus) {
		this.indNoteNumStatus = indNoteNumStatus;
	}

	public Date getDtLastModify() {
		return this.dtLastModify;
	}

	public void setDtLastModify(Date dtLastModify) {
		this.dtLastModify = dtLastModify;
	}

	public int getNumPersonModify() {
		return this.numPersonModify;
	}

	public void setNumPersonModify(int numPersonModify) {
		this.numPersonModify = numPersonModify;
	}

	public int getNumLock() {
		return this.numLock;
	}

	public void setNumLock(int numLock) {
		this.numLock = numLock;
	}

	public double getFactUomIso() {
		return this.factUomIso;
	}

	public void setFactUomIso(double factUomIso) {
		this.factUomIso = factUomIso;
	}

	public short getIndSourceType() {
		return this.indSourceType;
	}

	public void setIndSourceType(short indSourceType) {
		this.indSourceType = indSourceType;
	}

	public short getIndDimesionlessUom() {
		return this.indDimesionlessUom;
	}

	public void setIndDimesionlessUom(short indDimesionlessUom) {
		this.indDimesionlessUom = indDimesionlessUom;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VdimUnitOfMeasureId))
			return false;
		VdimUnitOfMeasureId castOther = (VdimUnitOfMeasureId) other;

		return ((this.getCodUom() == castOther.getCodUom()) || (this
				.getCodUom() != null && castOther.getCodUom() != null && this
				.getCodUom().equals(castOther.getCodUom())))
				&& (this.getIndUomType() == castOther.getIndUomType())
				&& ((this.getFactUom() == castOther.getFactUom()) || (this
						.getFactUom() != null && castOther.getFactUom() != null && this
						.getFactUom().equals(castOther.getFactUom())))
				&& ((this.getNameUom() == castOther.getNameUom()) || (this
						.getNameUom() != null && castOther.getNameUom() != null && this
						.getNameUom().equals(castOther.getNameUom())))
				&& (this.getIndPriceUomPeriod() == castOther
						.getIndPriceUomPeriod())
				&& (this.getIndRestrictedAction() == castOther
						.getIndRestrictedAction())
				&& (this.getIndPackagingUom() == castOther.getIndPackagingUom())
				&& (this.getIndNoteNumStatus() == castOther
						.getIndNoteNumStatus())
				&& ((this.getDtLastModify() == castOther.getDtLastModify()) || (this
						.getDtLastModify() != null
						&& castOther.getDtLastModify() != null && this
						.getDtLastModify().equals(castOther.getDtLastModify())))
				&& (this.getNumPersonModify() == castOther.getNumPersonModify())
				&& (this.getNumLock() == castOther.getNumLock())
				&& (this.getFactUomIso() == castOther.getFactUomIso())
				&& (this.getIndSourceType() == castOther.getIndSourceType())
				&& (this.getIndDimesionlessUom() == castOther
						.getIndDimesionlessUom());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCodUom() == null ? 0 : this.getCodUom().hashCode());
		result = 37 * result + this.getIndUomType();
		result = 37 * result
				+ (getFactUom() == null ? 0 : this.getFactUom().hashCode());
		result = 37 * result
				+ (getNameUom() == null ? 0 : this.getNameUom().hashCode());
		result = 37 * result + this.getIndPriceUomPeriod();
		result = 37 * result + this.getIndRestrictedAction();
		result = 37 * result + this.getIndPackagingUom();
		result = 37 * result + this.getIndNoteNumStatus();
		result = 37
				* result
				+ (getDtLastModify() == null ? 0 : this.getDtLastModify()
						.hashCode());
		result = 37 * result + this.getNumPersonModify();
		result = 37 * result + this.getNumLock();
		result = 37 * result + (int) this.getFactUomIso();
		result = 37 * result + this.getIndSourceType();
		result = 37 * result + this.getIndDimesionlessUom();
		return result;
	}

}
