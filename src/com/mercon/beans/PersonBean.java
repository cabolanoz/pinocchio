package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.PersonDao;
import com.mercon.entities.VdimPersonId;

@ManagedBean(name = "person")
@ViewScoped
public class PersonBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5024947656050338847L;

	@ManagedProperty("#{personService}")
	private PersonDao personDao;
	
	private List<VdimPersonId> lstPersons;
	
	public PersonBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading persons */
		if (lstPersons == null || lstPersons.isEmpty() || lstPersons.size() <= 0) {
			try {
				lstPersons = personDao.getAllOperators();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public List<VdimPersonId> getLstPersons() {
		return lstPersons;
	}

	public void setLstPersons(List<VdimPersonId> lstPersons) {
		this.lstPersons = lstPersons;
	}
	
}
