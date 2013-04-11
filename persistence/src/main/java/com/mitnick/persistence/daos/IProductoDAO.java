package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;

public interface IProductoDAO extends GenericDao<Producto, Long>{
	
	public List<Producto> getAll();
	
	public Producto saveOrUpdate(Producto producto);

	public List<Producto> findByFiltro(ConsultaProductoDto filtro);	

	public Producto findByCode(String codigo);
	
	public Producto findByStartCode(String codigo);
	
}
