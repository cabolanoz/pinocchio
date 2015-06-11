package com.mercon.entities;
// default package
// Generated Mar 21, 2013 2:08:14 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * VdimPersonId generated by hbm2java
 */
public class VdimPersonId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numperson;
	private String firstname;
	private String lastname;
	private String personinitials;
	private short indjobtitle;
	private int companynum;
	private String codlogin;
	private int companyasignament;
	private short indusertype;
	private short indstatus;
	private Date dtlastmodify;
	private int nummodifyperson;
	private int numlock;

	public VdimPersonId() {
	}

	public VdimPersonId(int numperson, String firstname, String lastname,
			String personinitials, short indjobtitle, short indusertype,
			short indstatus, Date dtlastmodify, int nummodifyperson, int numlock) {
		this.numperson = numperson;
		this.firstname = firstname;
		this.lastname = lastname;
		this.personinitials = personinitials;
		this.indjobtitle = indjobtitle;
		this.indusertype = indusertype;
		this.indstatus = indstatus;
		this.dtlastmodify = dtlastmodify;
		this.nummodifyperson = nummodifyperson;
		this.numlock = numlock;
	}

	public VdimPersonId(int numperson, String firstname, String lastname,
			String personinitials, short indjobtitle, Integer companynum,
			String codlogin, Integer companyasignament, short indusertype,
			short indstatus, Date dtlastmodify, int nummodifyperson, int numlock) {
		this.numperson = numperson;
		this.firstname = firstname;
		this.lastname = lastname;
		this.personinitials = personinitials;
		this.indjobtitle = indjobtitle;
		this.companynum = companynum;
		this.codlogin = codlogin;
		this.companyasignament = companyasignament;
		this.indusertype = indusertype;
		this.indstatus = indstatus;
		this.dtlastmodify = dtlastmodify;
		this.nummodifyperson = nummodifyperson;
		this.numlock = numlock;
	}

	public int getNumperson() {
		return this.numperson;
	}

	public void setNumperson(int numperson) {
		this.numperson = numperson;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPersoninitials() {
		return this.personinitials;
	}

	public void setPersoninitials(String personinitials) {
		this.personinitials = personinitials;
	}

	public short getIndjobtitle() {
		return this.indjobtitle;
	}

	public void setIndjobtitle(short indjobtitle) {
		this.indjobtitle = indjobtitle;
	}

	public Integer getCompanynum() {
		return this.companynum;
	}

	public void setCompanynum(Integer companynum) {
		this.companynum = companynum;
	}

	public String getCodlogin() {
		return this.codlogin;
	}

	public void setCodlogin(String codlogin) {
		this.codlogin = codlogin;
	}

	public Integer getCompanyasignament() {
		return this.companyasignament;
	}

	public void setCompanyasignament(Integer companyasignament) {
		this.companyasignament = companyasignament;
	}

	public short getIndusertype() {
		return this.indusertype;
	}

	public void setIndusertype(short indusertype) {
		this.indusertype = indusertype;
	}

	public short getIndstatus() {
		return this.indstatus;
	}

	public void setIndstatus(short indstatus) {
		this.indstatus = indstatus;
	}

	public Date getDtlastmodify() {
		return this.dtlastmodify;
	}

	public void setDtlastmodify(Date dtlastmodify) {
		this.dtlastmodify = dtlastmodify;
	}

	public int getNummodifyperson() {
		return this.nummodifyperson;
	}

	public void setNummodifyperson(int nummodifyperson) {
		this.nummodifyperson = nummodifyperson;
	}

	public int getNumlock() {
		return this.numlock;
	}

	public void setNumlock(int numlock) {
		this.numlock = numlock;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VdimPersonId))
			return false;
		VdimPersonId castOther = (VdimPersonId) other;

		return (this.getNumperson() == castOther.getNumperson())
				&& ((this.getFirstname() == castOther.getFirstname()) || (this
						.getFirstname() != null
						&& castOther.getFirstname() != null && this
						.getFirstname().equals(castOther.getFirstname())))
				&& ((this.getLastname() == castOther.getLastname()) || (this
						.getLastname() != null
						&& castOther.getLastname() != null && this
						.getLastname().equals(castOther.getLastname())))
				&& ((this.getPersoninitials() == castOther.getPersoninitials()) || (this
						.getPersoninitials() != null
						&& castOther.getPersoninitials() != null && this
						.getPersoninitials().equals(
								castOther.getPersoninitials())))
				&& (this.getIndjobtitle() == castOther.getIndjobtitle())
				&& ((this.getCompanynum() == castOther.getCompanynum()) || (this
						.getCompanynum() != null
						&& castOther.getCompanynum() != null && this
						.getCompanynum().equals(castOther.getCompanynum())))
				&& ((this.getCodlogin() == castOther.getCodlogin()) || (this
						.getCodlogin() != null
						&& castOther.getCodlogin() != null && this
						.getCodlogin().equals(castOther.getCodlogin())))
				&& ((this.getCompanyasignament() == castOther
						.getCompanyasignament()) || (this
						.getCompanyasignament() != null
						&& castOther.getCompanyasignament() != null && this
						.getCompanyasignament().equals(
								castOther.getCompanyasignament())))
				&& (this.getIndusertype() == castOther.getIndusertype())
				&& (this.getIndstatus() == castOther.getIndstatus())
				&& ((this.getDtlastmodify() == castOther.getDtlastmodify()) || (this
						.getDtlastmodify() != null
						&& castOther.getDtlastmodify() != null && this
						.getDtlastmodify().equals(castOther.getDtlastmodify())))
				&& (this.getNummodifyperson() == castOther.getNummodifyperson())
				&& (this.getNumlock() == castOther.getNumlock());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getNumperson();
		result = 37 * result
				+ (getFirstname() == null ? 0 : this.getFirstname().hashCode());
		result = 37 * result
				+ (getLastname() == null ? 0 : this.getLastname().hashCode());
		result = 37
				* result
				+ (getPersoninitials() == null ? 0 : this.getPersoninitials()
						.hashCode());
		result = 37 * result + this.getIndjobtitle();
		result = 37
				* result
				+ (getCompanynum() == null ? 0 : this.getCompanynum()
						.hashCode());
		result = 37 * result
				+ (getCodlogin() == null ? 0 : this.getCodlogin().hashCode());
		result = 37
				* result
				+ (getCompanyasignament() == null ? 0 : this
						.getCompanyasignament().hashCode());
		result = 37 * result + this.getIndusertype();
		result = 37 * result + this.getIndstatus();
		result = 37
				* result
				+ (getDtlastmodify() == null ? 0 : this.getDtlastmodify()
						.hashCode());
		result = 37 * result + this.getNummodifyperson();
		result = 37 * result + this.getNumlock();
		return result;
	}

}
