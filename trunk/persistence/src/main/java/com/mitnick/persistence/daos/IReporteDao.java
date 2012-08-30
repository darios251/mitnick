package com.mitnick.persistence.daos;

import java.io.Serializable;
import java.util.List;

import org.appfuse.dao.GenericDao;
import org.appfuse.model.BaseObject;

import com.mitnick.servicio.servicios.dtos.ReporteVentaArticuloDTO;
import com.mitnick.servicio.servicios.dtos.ReportesDto;

public interface IReporteDao extends GenericDao<BaseObject, Serializable> {
	
	public List<ReporteVentaArticuloDTO> consultarVentaPorArticulo(ReportesDto filtro) ;

}
