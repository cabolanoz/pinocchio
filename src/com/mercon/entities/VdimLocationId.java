package com.mercon.entities;

// Generated Mar 10, 2014 12:36:18 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * VdimLocationId generated by hbm2java
 */
public class VdimLocationId implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1956623378278726537L;
	
	private int numLocation;
	private String typLocation;
	private int numParentLocation;
	private int numDeliveryParentLocation;
	private String nameLocation;
	private String codLocation;
	private String nameLocationShort;
	private short codCommodityType;
	private Date dtLastModify;
	private int numLastModifyPerson;
	private String stateName;
	private String cityName;

	public VdimLocationId() {
	}

	public VdimLocationId(int numLocation, String typLocation,
			int numParentLocation, int numDeliveryParentLocation,
			String nameLocation, String nameLocationShort,
			short codCommodityType, Date dtLastModify, int numLastModifyPerson,
			String stateName, String cityName) {
		this.numLocation = numLocation;
		this.typLocation = typLocation;
		this.numParentLocation = numParentLocation;
		this.numDeliveryParentLocation = numDeliveryParentLocation;
		this.nameLocation = nameLocation;
		this.nameLocationShort = nameLocationShort;
		this.codCommodityType = codCommodityType;
		this.dtLastModify = dtLastModify;
		this.numLastModifyPerson = numLastModifyPerson;
		this.stateName = stateName;
		this.cityName = cityName;
	}

	public VdimLocationId(int numLocation, String typLocation,
			int numParentLocation, int numDeliveryParentLocation,
			String nameLocation, String codLocation, String nameLocationShort,
			short codCommodityType, Date dtLastModify, int numLastModifyPerson,
			String stateName, String cityName) {
		this.numLocation = numLocation;
		this.typLocation = typLocation;
		this.numParentLocation = numParentLocation;
		this.numDeliveryParentLocation = numDeliveryParentLocation;
		this.nameLocation = nameLocation;
		this.codLocation = codLocation;
		this.nameLocationShort = nameLocationShort;
		this.codCommodityType = codCommodityType;
		this.dtLastModify = dtLastModify;
		this.numLastModifyPerson = numLastModifyPerson;
		this.stateName = stateName;
		this.cityName = cityName;
	}

	public int getNumLocation() {
		return this.numLocation;
	}

	public void setNumLocation(int numLocation) {
		this.numLocation = numLocation;
	}

	public String getTypLocation() {
		return this.typLocation;
	}

	public void setTypLocation(String typLocation) {
		this.typLocation = typLocation;
	}

	public int getNumParentLocation() {
		return this.numParentLocation;
	}

	public void setNumParentLocation(int numParentLocation) {
		this.numParentLocation = numParentLocation;
	}

	public int getNumDeliveryParentLocation() {
		return this.numDeliveryParentLocation;
	}

	public void setNumDeliveryParentLocation(int numDeliveryParentLocation) {
		this.numDeliveryParentLocation = numDeliveryParentLocation;
	}

	public String getNameLocation() {
		return this.nameLocation;
	}

	public void setNameLocation(String nameLocation) {
		this.nameLocation = nameLocation;
	}

	public String getCodLocation() {
		return this.codLocation;
	}

	public void setCodLocation(String codLocation) {
		this.codLocation = codLocation;
	}

	public String getNameLocationShort() {
		return this.nameLocationShort;
	}

	public void setNameLocationShort(String nameLocationShort) {
		this.nameLocationShort = nameLocationShort;
	}

	public short getCodCommodityType() {
		return this.codCommodityType;
	}

	public void setCodCommodityType(short codCommodityType) {
		this.codCommodityType = codCommodityType;
	}

	public Date getDtLastModify() {
		return this.dtLastModify;
	}

	public void setDtLastModify(Date dtLastModify) {
		this.dtLastModify = dtLastModify;
	}

	public int getNumLastModifyPerson() {
		return this.numLastModifyPerson;
	}

	public void setNumLastModifyPerson(int numLastModifyPerson) {
		this.numLastModifyPerson = numLastModifyPerson;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VdimLocationId))
			return false;
		VdimLocationId castOther = (VdimLocationId) other;

		return (this.getNumLocation() == castOther.getNumLocation())
				&& ((this.getTypLocation() == castOther.getTypLocation()) || (this
						.getTypLocation() != null
						&& castOther.getTypLocation() != null && this
						.getTypLocation().equals(castOther.getTypLocation())))
				&& (this.getNumParentLocation() == castOther
						.getNumParentLocation())
				&& (this.getNumDeliveryParentLocation() == castOther
						.getNumDeliveryParentLocation())
				&& ((this.getNameLocation() == castOther.getNameLocation()) || (this
						.getNameLocation() != null
						&& castOther.getNameLocation() != null && this
						.getNameLocation().equals(castOther.getNameLocation())))
				&& ((this.getCodLocation() == castOther.getCodLocation()) || (this
						.getCodLocation() != null
						&& castOther.getCodLocation() != null && this
						.getCodLocation().equals(castOther.getCodLocation())))
				&& ((this.getNameLocationShort() == castOther
						.getNameLocationShort()) || (this
						.getNameLocationShort() != null
						&& castOther.getNameLocationShort() != null && this
						.getNameLocationShort().equals(
								castOther.getNameLocationShort())))
				&& (this.getCodCommodityType() == castOther
						.getCodCommodityType())
				&& ((this.getDtLastModify() == castOther.getDtLastModify()) || (this
						.getDtLastModify() != null
						&& castOther.getDtLastModify() != null && this
						.getDtLastModify().equals(castOther.getDtLastModify())))
				&& (this.getNumLastModifyPerson() == castOther
						.getNumLastModifyPerson())
				&& ((this.getStateName() == castOther.getStateName()) || (this
						.getStateName() != null
						&& castOther.getStateName() != null && this
						.getStateName().equals(castOther.getStateName())))
				&& ((this.getCityName() == castOther.getCityName()) || (this
						.getCityName() != null
						&& castOther.getCityName() != null && this
						.getCityName().equals(castOther.getCityName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getNumLocation();
		result = 37
				* result
				+ (getTypLocation() == null ? 0 : this.getTypLocation()
						.hashCode());
		result = 37 * result + this.getNumParentLocation();
		result = 37 * result + this.getNumDeliveryParentLocation();
		result = 37
				* result
				+ (getNameLocation() == null ? 0 : this.getNameLocation()
						.hashCode());
		result = 37
				* result
				+ (getCodLocation() == null ? 0 : this.getCodLocation()
						.hashCode());
		result = 37
				* result
				+ (getNameLocationShort() == null ? 0 : this
						.getNameLocationShort().hashCode());
		result = 37 * result + this.getCodCommodityType();
		result = 37
				* result
				+ (getDtLastModify() == null ? 0 : this.getDtLastModify()
						.hashCode());
		result = 37 * result + this.getNumLastModifyPerson();
		result = 37 * result
				+ (getStateName() == null ? 0 : this.getStateName().hashCode());
		result = 37 * result
				+ (getCityName() == null ? 0 : this.getCityName().hashCode());
		return result;
	}

}
