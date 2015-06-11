package com.mercon.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.mercon.entities.VbuildDrawTag;

public class BuildDrawTagDataModel extends ListDataModel<VbuildDrawTag> implements SelectableDataModel<VbuildDrawTag> {

	private List<VbuildDrawTag> data;
	
	public BuildDrawTagDataModel() { }
	
	public BuildDrawTagDataModel(List<VbuildDrawTag> data) {
		super(data);
		
		this.data = data;
	}
	
	@Override
	public VbuildDrawTag getRowData(String rowKey) {
		if (this.data != null) {
			for (VbuildDrawTag buildDrawTag : this.data) {
				if (String.valueOf(buildDrawTag.getId().getBdTagNum()).equals(rowKey)) {
					return buildDrawTag;
				}
			}
		}
		
		return null;
	}

	@Override
	public Object getRowKey(VbuildDrawTag obj) {
		return String.valueOf(obj.getId().getBdTagNum());
	}

}
