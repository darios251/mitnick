package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Provincia;


public interface ICiudadDao extends GenericDao<Ciudad, Long>{
	List<Ciudad> getByProvincia(Provincia provincia);
	List<Ciudad> getByDescripcion(String descripcion); 
	public List<Ciudad> getByPostal(String postal);
}
