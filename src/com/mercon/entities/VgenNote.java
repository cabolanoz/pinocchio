package com.mercon.entities;

// Generated Aug 8, 2014 11:06:17 AM by Hibernate Tools 3.4.0.CR1

/**
 * VgenNote generated by hbm2java
 */
public class VgenNote implements java.io.Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -3872204438557970279L;

	private VgenNoteId id;

	public VgenNote() {
	}

	public VgenNote(VgenNoteId id) {
		this.id = id;
	}

	public VgenNoteId getId() {
		return this.id;
	}

	public void setId(VgenNoteId id) {
		this.id = id;
	}

}