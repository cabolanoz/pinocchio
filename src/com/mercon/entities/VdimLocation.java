package com.mercon.entities;

// Generated Mar 10, 2014 12:36:18 PM by Hibernate Tools 3.4.0.CR1

/**
 * VdimLocation generated by hbm2java
 */
public class VdimLocation implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7437210007240494434L;
	
	private VdimLocationId id;

	public VdimLocation() {
	}

	public VdimLocation(VdimLocationId id) {
		this.id = id;
	}

	public VdimLocationId getId() {
		return this.id;
	}

	public void setId(VdimLocationId id) {
		this.id = id;
	}

}
