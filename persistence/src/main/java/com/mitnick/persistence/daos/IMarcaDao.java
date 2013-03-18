package com.mitnick.persistence.daos;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Marca;

public interface IMarcaDao extends GenericDao<Marca, Long>{
	
	public Marca findById(Long id);

}
