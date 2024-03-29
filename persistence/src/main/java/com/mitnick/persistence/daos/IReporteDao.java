package com.mitnick.persistence.daos;

import java.io.Serializable;
import java.util.List;

import org.appfuse.dao.GenericDao;
import org.appfuse.model.BaseObject;

import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.servicio.servicios.dtos.ReporteCompraSugeridaDTO;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;

public interface IReporteDao extends GenericDao<BaseObject, Serializable> {
	
	public List<ProductoVenta> consultarVentaPorArticulo(ReporteMovimientosDto filtro) ;
	
	public List<ReporteCompraSugeridaDTO> consultarCompraSugerida(ReporteMovimientosDto dto) ;

}
