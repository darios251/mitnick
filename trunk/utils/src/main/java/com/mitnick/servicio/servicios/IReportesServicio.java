package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
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
	
	/**
	 * Obtiene el detalle de ingresos de pagos para la fecha pasada por parámetro, agrupados por fecha.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void reporteIngresosAgrupados(ReportesDto filtro);
	
	/**
	 * Obtiene el detalle de estado de cuentas entre las fechas pasadas por parámetro.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarEstadoCuentas(ReportesDto filtro);
	
	/**
	 * Obtiene el detalle de cobros de cuentas corrientes.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarListadoDeControl(ReportesDto filtro);
		
	/**
	 * Obtiene el detalle de ventas por articulo.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarVentaPorArticulo(ReportesDto filtro);
	
	/**
	 * Obtiene el detalle de stock disponible de los articulos.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarStockArticulo(ReportesDto filtro);	
		

}
