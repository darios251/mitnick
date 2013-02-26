package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;

public interface IMovimientoDao extends GenericDao<Movimiento, Long>{
	
	public List<Movimiento> findByFiltro(ReporteDetalleMovimientosDto filtro);
	
	public List<Movimiento> findByFiltro(ReporteMovimientosDto filtro);
	
	public Movimiento saveOrUpdate(Movimiento movimiento);
	
	public void removeAll(Long idProducto);
}
