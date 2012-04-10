package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cliente;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;

public interface IClienteDao extends GenericDao<Cliente, Long>{

	public List<Cliente> findByDocumento(String documento);
	
	public List<Cliente> findByFiltro(ConsultaClienteDto filtro);
	
	public Cliente saveOrUpdate(Cliente cliente);
}
