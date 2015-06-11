package com.mercon.entities;

import java.io.Serializable;

public class VrefMot implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 4799284966821344817L;

	private VrefMotId id;
	
	public VrefMot() { }

	public VrefMot(VrefMotId id) {
		this.id = id;
	}
	
	public VrefMotId getId() {
		return id;
	}

	public void setId(VrefMotId id) {
		this.id = id;
	}
	
}
