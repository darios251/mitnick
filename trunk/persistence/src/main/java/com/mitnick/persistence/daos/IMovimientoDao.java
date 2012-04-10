package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;

public interface IMovimientoDao extends GenericDao<Movimiento, Long>{
	
	public List<Movimiento> findByFiltro(ReporteMovimientosDto filtro);
	
	public Movimiento saveOrUpdate(Movimiento movimiento);

}
