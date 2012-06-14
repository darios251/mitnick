package com.mitnick.persistence.daos;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Configuracion;

public interface IConfiguracionDAO extends GenericDao<Configuracion, Long>{
	
	Configuracion getConfiguracion();
}
