package com.mercon.entities;

// Generated Aug 7, 2014 12:10:45 PM by Hibernate Tools 3.4.0.CR1

/**
 * VdimTransfer generated by hbm2java
 */
public class VdimTransfer implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 409377274933490373L;

	private VdimTransferId id;

	public VdimTransfer() {
	}

	public VdimTransfer(VdimTransferId id) {
		this.id = id;
	}

	public VdimTransferId getId() {
		return this.id;
	}

	public void setId(VdimTransferId id) {
		this.id = id;
	}

}
