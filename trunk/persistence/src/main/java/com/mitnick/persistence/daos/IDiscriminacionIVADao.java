package com.mitnick.persistence.daos;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.DiscriminacionIVA;


public interface IDiscriminacionIVADao extends GenericDao<DiscriminacionIVA, Long>{
	
	DiscriminacionIVA findDiscriminacionIVAporCodigo(String codigo);
}
