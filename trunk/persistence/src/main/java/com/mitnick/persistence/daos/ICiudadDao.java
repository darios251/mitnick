package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Provincia;


public interface ICiudadDao extends GenericDao<Ciudad, Long>{
	List<Ciudad> getByProvincia(Provincia provincia);
	List<Ciudad> getByDescripcion(String descripcion); 
	List<Ciudad> getByPostal(String postal);
	List<Ciudad> getByProvinciaPostalCode(Provincia provincia, String postalCode);
	List<Ciudad> getByProvinciaDescription(Provincia provincia, String description);
	Ciudad getById(Long id);
}
