package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cliente;

public interface IClienteDao extends GenericDao<Cliente, Long>{

	public List<Cliente> findByDocumento(Long documento);
	
}
