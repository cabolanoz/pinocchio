package com.mercon.cxl;

import java.util.Date;
import java.util.List;

public class BuildDrawMatchDetail {

	private int bd_match_num;
	private int build_draw_num;
	private int equipment_num;
	private int strategy_num;
	private Double build_draw_match_qty;
	private Date title_transfer_dt;
	private List<BuildDrawTag> tags;

	public int getBd_match_num() {
		return bd_match_num;
	}
	public void setBd_match_num(int bd_match_num) {
		this.bd_match_num = bd_match_num;
	}
	public int getBuild_draw_num() {
		return build_draw_num;
	}
	public void setBuild_draw_num(int build_draw_num) {
		this.build_draw_num = build_draw_num;
	}
	public int getEquipment_num() {
		return equipment_num;
	}
	public void setEquipment_num(int equipment_num) {
		this.equipment_num = equipment_num;
	}
	public int getStrategy_num() {
		return strategy_num;
	}
	public void setStrategy_num(int strategy_num) {
		this.strategy_num = strategy_num;
	}
	public Double getBuild_draw_match_qty() {
		return build_draw_match_qty;
	}
	public void setBuild_draw_match_qty(Double build_draw_match_qty) {
		this.build_draw_match_qty = build_draw_match_qty;
	}
	public Date getTitle_transfer_dt() {
		return title_transfer_dt;
	}
	public void setTitle_transfer_dt(Date title_transfer_dt) {
		this.title_transfer_dt = title_transfer_dt;
	}
	public List<BuildDrawTag> getTags() {
		return tags;
	}
	public void setTags(List<BuildDrawTag> tags) {
		this.tags = tags;
	}
}
