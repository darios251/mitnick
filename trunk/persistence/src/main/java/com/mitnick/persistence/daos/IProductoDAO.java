package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Producto;

public interface IProductoDAO extends GenericDao<Producto, Long>{
	
	public List<Producto> getAll();
	
	public List<Producto> findByCodigoDescripcion(String codigo, String descripcion);
	
	public List<Producto> findByCodigoDescripcionTipoMarca(String codigo, String descripcion, Long tipo, Long marca);
	
	public Producto saveOrUpdate(Producto producto);

}
