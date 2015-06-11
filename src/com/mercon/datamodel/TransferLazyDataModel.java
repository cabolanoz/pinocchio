package com.mercon.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mercon.entities.VdimTransfer;
import com.mercon.util.DefaultConstants;

public class TransferLazyDataModel extends LazyDataModel<VdimTransfer> implements DefaultConstants {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4294295036599424283L;

	private List<VdimTransfer> datasource;
	
	public TransferLazyDataModel(List<VdimTransfer> datasource) {
		this.datasource = datasource;
	}
	
	@Override
	public VdimTransfer getRowData(String rowKey) {
		for (VdimTransfer transfer : this.datasource) {
			if (String.valueOf(transfer.getId().getNumTransfer()).equals(rowKey)) {
				return transfer;
			}
		}
		
		return null;
	}
	
	@Override
	public Object getRowKey(VdimTransfer transfer) {
		return transfer.getId().getNumTransfer();
	}
	
	@Override
	public List<VdimTransfer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		int dataSize = this.datasource.size();
		
		System.out.println(dataSize);
		
		this.setRowIndex(dataSize);
		
		if (dataSize > pageSize) {
			try {
				return datasource.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException ioobe) {
				return datasource.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return this.datasource;
		}
	}
	
}
