package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cliente;

public interface IClienteDao extends GenericDao<Cliente, Long>{

	public List<Cliente> findByDocumento(String documento);
	
	public Cliente saveOrUpdate(Cliente cliente);
	
	public List<Cliente> findByDocumentoNombreApellido(String documento, String nombre, String apellido);
}
