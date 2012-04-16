package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.util.EntityDTOParser;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.VentaDto;

public class ReportesServicio extends ServicioBase<Movimiento, MovimientoDto> implements IReportesServicio {
	
	@Autowired
	protected IMovimientoDao movimientoDao;
	
	@Autowired
	protected IVentaDAO ventaDao;
	
	@Autowired
	protected EntityDTOParser<Venta, VentaDto> entityDTOParserVenta;
	
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
	
	@Transactional(readOnly=true)
	@Override
	public List<VentaDto> reporteVentas(ReporteVentaDto filtro) {
		List<VentaDto> ventas = new ArrayList<VentaDto>();
		try{
			ventas.addAll(entityDTOParserVenta.getDtosFromEntities(ventaDao.findByFiltro(filtro)));
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
