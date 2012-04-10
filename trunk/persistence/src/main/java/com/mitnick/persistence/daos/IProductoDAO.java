package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;

public interface IProductoDAO extends GenericDao<Producto, Long>{
	
	public List<Producto> getAll();
	
	public List<Producto> findStockByFiltro(ConsultaStockDto filtro);
	
	public Producto saveOrUpdate(Producto producto);

	public List<Producto> findByFiltro(ConsultaProductoDto filtro);

}
