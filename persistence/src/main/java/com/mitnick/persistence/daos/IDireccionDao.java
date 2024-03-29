package com.mitnick.persistence.daos;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Direccion;

public interface IDireccionDao extends GenericDao<Direccion, Long>{
	
	Direccion saveOrUpdate(Direccion direccion);
}
