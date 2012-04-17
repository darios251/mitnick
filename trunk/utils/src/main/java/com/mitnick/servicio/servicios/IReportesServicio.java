package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IReportesServicio {
	
	/**
	 * Obtiene para todos los productos el resumen de movimientos para la fecha pasada por parámetro
	 * @param filtro
	 * @return
	 */
	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(ReporteMovimientosDto filtro);
	
	/**
	 * Obtiene para el producto pasado por parámetro el detalle de movimientos para la fecha pasada por parámetro.
	 * @param filtro
	 * @return
	 */
	public List<MovimientoDto> reporteMovimientosDeProducto(ReporteMovimientosDto filtro);
	
	/**
	 * Obtiene el detalle de ventas para la fecha pasada por parámetro.
	 * @param filtro
	 * @return
	 */
	public List<VentaDto> reporteVentas(ReporteVentaDto filtro);

}
