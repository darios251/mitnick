package com.mitnick.persistence.daos;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Empresa;


public interface IEmpresaDao extends GenericDao<Empresa, Long>{
	
	public Empresa getEmpresa();
	
	public Empresa saveOrUpdate(Empresa empresa);

}
