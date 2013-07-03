package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Vendedor;
import com.mitnick.servicio.servicios.dtos.ConsultaVendedorDto;

public interface IVendedorDao extends GenericDao<Vendedor, Long> {

	public Vendedor findById(Long id);

	List<Vendedor> findByFiltro(ConsultaVendedorDto filtro);

	Vendedor saveOrUpdate(Vendedor vendedor);

	List<Vendedor> getAllNoEliminado();

}
