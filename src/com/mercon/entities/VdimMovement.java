package com.mercon.entities;

import java.io.Serializable;

public class VdimMovement implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -131218193527047602L;

	private VdimMovementId id;
	
	public VdimMovement() { }
	
	public VdimMovement(VdimMovementId id) {
		this.id = id;
	}

	public VdimMovementId getId() {
		return id;
	}

	public void setId(VdimMovementId id) {
		this.id = id;
	}
	
}
