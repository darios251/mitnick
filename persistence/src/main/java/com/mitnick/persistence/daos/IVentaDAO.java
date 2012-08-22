package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReportesDto;

public interface IVentaDAO extends GenericDao<Venta, Long>{

	List<Venta> findByFiltro(ReportesDto filtro);
	
	Venta saveOrUpdate(Venta venta);
	
	void generarFactura(Venta venta);
	
}
