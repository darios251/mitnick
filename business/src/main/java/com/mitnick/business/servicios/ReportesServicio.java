package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.VentaDto;

public class ReportesServicio extends ServicioBase implements IReportesServicio {
	
	@Autowired
	protected IMovimientoDao movimientoDao;
	
	protected IVentaDAO ventaDao;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<MovimientoDto> reporteMovimientos(ReporteMovimientosDto filtro) {
		List<MovimientoDto> movimientos = new ArrayList<MovimientoDto>();
		try{
			movimientos.addAll(entityDTOParser.getDtosFromEntities(movimientoDao.findByFiltro(filtro)));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  movimiento", e);
		}
		return movimientos;
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<VentaDto> reporteVentas(ReporteVentaDto filtro) {
		List<VentaDto> ventas = new ArrayList<VentaDto>();
		try{
			ventas.addAll(entityDTOParser.getDtosFromEntities(ventaDao.findByFiltro(filtro)));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		
		return ventas;
		
	}

	public void setMovimientoDao(IMovimientoDao movimientoDao) {
		this.movimientoDao = movimientoDao;
	}


	public void setVentaDao(IVentaDAO ventaDao) {
		this.ventaDao = ventaDao;
	}

}
