package com.mercon.entities;
// default package
// Generated Mar 21, 2013 2:08:14 PM by Hibernate Tools 4.0.0

/**
 * VdimPerson generated by hbm2java
 */
public class VdimPerson implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VdimPersonId id;

	public VdimPerson() {
	}

	public VdimPerson(VdimPersonId id) {
		this.id = id;
	}

	public VdimPersonId getId() {
		return this.id;
	}

	public void setId(VdimPersonId id) {
		this.id = id;
	}

}