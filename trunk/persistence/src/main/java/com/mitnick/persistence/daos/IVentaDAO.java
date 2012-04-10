package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;

public interface IVentaDAO extends GenericDao<Venta, Long>{

	public List<Venta> findByFiltro(ReporteVentaDto filtro);
	
	public Venta saveOrUpdate(Venta venta);
	
}
