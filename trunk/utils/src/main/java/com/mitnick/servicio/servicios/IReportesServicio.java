package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.ProductoDto;

public interface IReportesServicio {
	
	public static int TRANSACCIONAL = 0;
	public static int DIARIO = 1;
	public static int MENSUAL = 2;
	public static int ANUAL = 3;
	
	
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
	 * Obtiene el detalle de ingresos de pagos para la fecha pasada por parámetro.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void reporteIngresos(ReportesDto filtro, int tipo);
	
	/**
	 * Obtiene el detalle de estado de cuentas entre las fechas pasadas por parámetro.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarEstadoCuentas(ReportesDto filtro);
	
	/**
	 * Obtiene el detalle de cobros de cuentas corrientes y ventas contado.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarListadoDeControl(ReportesDto filtro);
	
	/**
	 * Obtiene el detalle de cobros de cuentas corrientes.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarListadoDeRecibos(ReportesDto filtro);
	
		
	/**
	 * Obtiene el detalle de ventas por articulo.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarVentaPorArticulo(ReporteMovimientosDto filtro);
		
	/**
	 * Obtiene el movimiento de stock de los productos.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void exportarMovimientosAgrupadosPorProducto(List<MovimientoProductoDto> movimientos) ;
	
	/**
	 * Obtiene el detalle de movimiento de stock de un producto.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void exportarMovimientosDeProducto(List<MovimientoDto> movimientos, ProductoDto producto, String stockOriginal, String stockFinal);
	
	/**
	 * Obtiene el detalle de compra sugerida para los productos seleccionados.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void exportarCompraSugerida(ReporteMovimientosDto dto); 
	
	/**
	 * Obtiene el detalle de facturacion A y B - corte Z.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void reporteFacturas(ReportesDto filtro);

	/**
	 * Obtiene el estado de cuenta de todos los clientes.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void consultarEstadoCuentasPorCliente(ReportesDto filtro);
	
	/**
	 * Obtiene el reporte de caja.
	 * @param filtro
	 * @return
	 */
	@Secured(value={"ROLE_ADMIN"})
	public void reporteCaja(ReportesDto filtro);
	
	/**
	 * Obtiene el reporte de caja generado por el cajero, no pide password.
	 * @param filtro
	 * @return
	 */
	public void reporteCajero(ReportesDto filtro);
	
	/**
	 * Obtiene un duplicado del comprobante original de la transacci�n.
	 * @param nroTrx
	 */
	public void consultarTransaccion(String nroTrx);
 
}
