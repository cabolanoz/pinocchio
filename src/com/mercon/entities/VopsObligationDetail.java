package com.mercon.entities;

// Generated Jul 3, 2014 9:18:28 AM by Hibernate Tools 3.4.0.CR1

/**
 * VopsObligationDetail generated by hbm2java
 */
public class VopsObligationDetail implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5135421185570927233L;
	
	private VopsObligationDetailId id;

	public VopsObligationDetail() {
	}

	public VopsObligationDetail(VopsObligationDetailId id) {
		this.id = id;
	}

	public VopsObligationDetailId getId() {
		return this.id;
	}

	public void setId(VopsObligationDetailId id) {
		this.id = id;
	}

}
