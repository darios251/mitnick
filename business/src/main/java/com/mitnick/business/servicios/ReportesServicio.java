package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.VentaDto;

public class ReportesServicio extends ServicioBase implements IReportesServicio {
	
	@Autowired
	protected IMovimientoDao movimientoDao;
	
	protected IVentaDAO ventaDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<MovimientoDto> reporteMovimientos(ReporteMovimientosDto filtro) {
		List<MovimientoDto> movimientos = new ArrayList<MovimientoDto>();
		try{
			for (Movimiento movimiento : movimientoDao.findByFiltro(filtro)) {
				movimientos.add(entityDTOParser.getDtoFromEntity(movimiento));
			}
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
			for (Venta venta: ventaDao.findByFiltro(filtro)) {
				ventas.add(entityDTOParser.getDtoFromEntity(venta));
			}
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
