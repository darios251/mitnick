package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Proveedor;
import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;

public interface IProveedorDAO extends GenericDao<Proveedor, Long>{
	
	List<Proveedor> getAll();
	
	Proveedor saveOrUpdate(Proveedor producto);

	List<Proveedor> findByFiltro(ConsultaProveedorDto filtro);
	
	List<Proveedor> getAllNoEliminado();

}
