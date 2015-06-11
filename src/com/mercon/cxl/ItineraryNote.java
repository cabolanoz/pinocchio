package com.mercon.cxl;

import java.io.Serializable;

import com.mercon.entities.VgenNoteId;

public class ItineraryNote implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4593551488162031785L;
	
	private VgenNoteId genNoteId;
	
	public ItineraryNote() { }

	public VgenNoteId getGenNoteId() {
		return genNoteId;
	}

	public void setGenNoteId(VgenNoteId genNoteId) {
		this.genNoteId = genNoteId;
	}
	
}
