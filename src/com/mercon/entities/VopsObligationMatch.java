package com.mercon.entities;

// Generated Jul 2, 2014 5:30:24 PM by Hibernate Tools 3.4.0.CR1

/**
 * VopsObligationMatch generated by hbm2java
 */
public class VopsObligationMatch implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -469060019962664186L;
	
	private VopsObligationMatchId id;

	public VopsObligationMatch() {
	}

	public VopsObligationMatch(VopsObligationMatchId id) {
		this.id = id;
	}

	public VopsObligationMatchId getId() {
		return this.id;
	}

	public void setId(VopsObligationMatchId id) {
		this.id = id;
	}

}
