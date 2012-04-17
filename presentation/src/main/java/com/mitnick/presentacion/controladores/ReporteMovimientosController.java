package com.mitnick.presentacion.controladores;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ReporteMovimientosView;
import com.mitnick.presentacion.vistas.paneles.ReporteDetalleMovimientosPanel;
import com.mitnick.presentacion.vistas.paneles.ReporteMovimientosPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Controller("reporteMovimientosController")
public class ReporteMovimientosController extends BaseController {

	private static Logger logger = Logger
			.getLogger(ReporteMovimientosController.class);

	@Autowired
	private ReporteMovimientosView reporteMovimientosView;
	@Autowired
	private ReporteMovimientosPanel reporteMovimientosPanel;
	@Autowired
	private ReporteDetalleMovimientosPanel reporteDetalleMovimientosPanel;

	@Autowired
	private IReportesServicio reporteServicio;

	@Autowired
	private IProductoServicio productoServicio;

	public ReporteMovimientosController() {

	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		reporteMovimientosPanel.setVisible(false);
		reporteMovimientosPanel.setVisible(true);
		reporteMovimientosPanel.actualizarPantalla();
	}

	public void exportarMovimientos() {
		//TODO: exportar a excel o pdf el reporte de movimientos agrupados por productos
	}
	
	public void mostrarMovimientosPanel() {
		logger.info("Mostrando el panel de movimientos");
		int index = getReporteMovimientosPanel().getTable().getSelectedRow();
		reporteDetalleMovimientosPanel.setProducto(getReporteMovimientosPanel().getTableModel().getMovimiento(index).getProducto());
		reporteDetalleMovimientosPanel.setFechaInicio(getReporteMovimientosPanel().getFechaInicio());
		reporteDetalleMovimientosPanel.setFechaFin(getReporteMovimientosPanel().getFechaFinal());
		reporteMovimientosPanel.setVisible(false);
		reporteDetalleMovimientosPanel.setVisible(true);
		reporteDetalleMovimientosPanel.actualizarPantalla();
	}

	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(
			ReporteMovimientosDto filtro) {
		logger.info("Consultando movimientos de productos por filtro");

		List<MovimientoProductoDto> resultado = null;
		try {
			resultado = getReporteServicio()
					.reporteMovimientosAgrupadosPorProducto(filtro);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		// chequeo si se encontro o no algo en la busqueda
		if (resultado == null || resultado.isEmpty())
			throw new PresentationException("error.venta.producto.noExiste");

		return resultado;
	}

	public List<MovimientoDto> reporteMovimientosDeProducto(
			ReporteDetalleMovimientosDto filtro) {
		logger.info("Consultando movimientos de productos por filtro");

		List<MovimientoDto> resultado = null;
		try {
			resultado = getReporteServicio().reporteMovimientosDeProducto(
					filtro);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		// chequeo si se encontro o no algo en la busqueda
		if (resultado == null || resultado.isEmpty())
			throw new PresentationException("error.venta.producto.noExiste");

		return resultado;
	}

	public List<MarcaDto> obtenerMarcas() {
		try {
			return productoServicio.obtenerMarcas();
		} catch (BusinessException e) {
			throw new PresentationException(e.getMessage(),
					"Hubo un error al intentar obtener las marcas");
		}
	}

	public List<TipoDto> obtenerTipos() {
		try {
			return productoServicio.obtenerTipos();
		} catch (BusinessException e) {
			throw new PresentationException(e.getMessage(),
					"Hubo un error al intentar obtener los tipos");
		}
	}

	public void setReporteServicio(IReportesServicio reporteServicio) {
		this.reporteServicio = reporteServicio;
	}

	public IReportesServicio getReporteServicio() {
		return reporteServicio;
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}

	public ReporteMovimientosView getReporteMovimientosView() {
		return reporteMovimientosView;
	}

	public ReporteMovimientosPanel getReporteMovimientosPanel() {
		return reporteMovimientosPanel;
	}

	public void setReporteMovimientosPanel(
			ReporteMovimientosPanel reporteMovimientosPanel) {
		this.reporteMovimientosPanel = reporteMovimientosPanel;
	}

	public IProductoServicio getProductoServicio() {
		return productoServicio;
	}

	public ReporteDetalleMovimientosPanel getReporteDetalleMovimientosPanel() {
		return reporteDetalleMovimientosPanel;
	}

	public void setReporteDetalleMovimientosPanel(
			ReporteDetalleMovimientosPanel reporteDetalleMovimientosPanel) {
		this.reporteDetalleMovimientosPanel = reporteDetalleMovimientosPanel;
	}

}
