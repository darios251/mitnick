package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentasResultadoDTO;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IReportesServicio {
	
	/**
	 * Obtiene para todos los productos el resumen de movimientos para la fecha pasada por parámetro
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(ReporteMovimientosDto filtro);
	
	/**
	 * Obtiene para el producto pasado por parámetro el detalle de movimientos para la fecha pasada por parámetro.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public List<MovimientoDto> reporteMovimientosDeProducto(ReporteDetalleMovimientosDto filtro);
	
	/**
	 * Obtiene el detalle de ventas para la fecha pasada por parámetro.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public List<VentaDto> reporteVentas(ReportesDto filtro);
	
	/**
	 * Obtiene el detalle de ingresos de pagos para la fecha pasada por parámetro.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void reporteIngresos(ReportesDto filtro);

}
