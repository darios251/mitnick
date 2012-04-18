package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cliente;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;

public interface IClienteDao extends GenericDao<Cliente, Long>{

	List<Cliente> findByDocumento(String documento);
	
	List<Cliente> findByFiltro(ConsultaClienteDto filtro);
	
	Cliente saveOrUpdate(Cliente cliente);
}