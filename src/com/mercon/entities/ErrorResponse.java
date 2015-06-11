package com.mercon.entities;

public class ErrorResponse  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private String internalError;
	private String possibleSolution;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getInternalError() {
		return internalError;
	}
	public void setInternalError(String internalError) {
		this.internalError = internalError;
	}
	public String getPossibleSolution() {
		return possibleSolution;
	}
	public void setPossibleSolution(String possibleSolution) {
		this.possibleSolution = possibleSolution;
	}
}
