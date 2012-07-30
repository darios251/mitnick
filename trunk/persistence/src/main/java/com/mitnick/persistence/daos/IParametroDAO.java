package com.mitnick.persistence.daos;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Parametro;

public interface IParametroDAO extends GenericDao<Parametro, Long>{
	
	Parametro getByName(String name);
	
	
}
