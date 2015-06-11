package com.mercon.cxl;

import java.util.List;

public class TagAllocation {

	private String lockInfo;
	private BuildDrawMatch buildDrawMatch;
	private BuildDrawMatchDetail buildDrawMatchDetail;
	private List<BuildDrawMatchDetail> buildDrawMatchDetailLst;

	public String getLockInfo() {
		return lockInfo;
	}
	public void setLockInfo(String lockInfo) {
		this.lockInfo = lockInfo;
	}
	public BuildDrawMatch getBuildDrawMatch() {
		return buildDrawMatch;
	}
	public void setBuildDrawMatch(BuildDrawMatch buildDrawMatch) {
		this.buildDrawMatch = buildDrawMatch;
	}
	public BuildDrawMatchDetail getBuildDrawMatchDetail() {
		return buildDrawMatchDetail;
	}
	public void setBuildDrawMatchDetail(BuildDrawMatchDetail buildDrawMatchDetail) {
		this.buildDrawMatchDetail = buildDrawMatchDetail;
	}
	public List<BuildDrawMatchDetail> getBuildDrawMatchDetailLst() {
		return buildDrawMatchDetailLst;
	}
	public void setBuildDrawMatchDetailLst(List<BuildDrawMatchDetail> buildDrawMatchDetailLst) {
		this.buildDrawMatchDetailLst = buildDrawMatchDetailLst;
	}
}
