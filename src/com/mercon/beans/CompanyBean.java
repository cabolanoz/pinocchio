package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.CompanyDao;
import com.mercon.entities.VdimCompanyId;

@ManagedBean(name = "company")
@ViewScoped
public class CompanyBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5405786861799004112L;

	@ManagedProperty("#{companyService}")
	private CompanyDao companyDao;
	
	private List<VdimCompanyId> lstCompanies;
	
	public CompanyBean() { }

	@PostConstruct
	public void init() {
		/* Loading companies */
		if (lstCompanies == null || lstCompanies.isEmpty() || lstCompanies.size() <= 0) {
			try {
				lstCompanies = companyDao.getInternalCompanies();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public List<VdimCompanyId> getLstCompanies() {
		return lstCompanies;
	}

	public void setLstCompanies(List<VdimCompanyId> lstCompanies) {
		this.lstCompanies = lstCompanies;
	}
	
}
