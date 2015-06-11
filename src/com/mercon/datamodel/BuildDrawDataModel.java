package com.mercon.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.mercon.entities.VbuildDraw;

public class BuildDrawDataModel extends ListDataModel<VbuildDraw> implements SelectableDataModel<VbuildDraw> {

	private List<VbuildDraw> data;
	
	public BuildDrawDataModel() { }
	
	public BuildDrawDataModel(List<VbuildDraw> data) {
		super(data);
		
		this.data = data;
	}
	
	@Override
	public VbuildDraw getRowData(String rowKey) {
		if (this.data != null) {
			for (VbuildDraw buildDraw : this.data) {
				if (String.valueOf(buildDraw.getId().getBuildDrawNum()).equals(rowKey)) {
					return buildDraw;
				}
			}
		}
		
		return null;
	}

	@Override
	public Object getRowKey(VbuildDraw obj) {
		return String.valueOf(obj.getId().getBuildDrawNum());
	}

	public List<VbuildDraw> getData() {
		return data;
	}

	public void setData(List<VbuildDraw> data) {
		this.data = data;
	}

}
