package com.mitnick.presentacion.controladores;

import java.util.List;

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
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Controller("reporteMovimientosController")
public class ReporteMovimientosController extends BaseController {

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
		ultimoPanelMostrado = reporteMovimientosPanel;
		reporteDetalleMovimientosPanel.setVisible(false);
		reporteMovimientosPanel.setVisible(true);
		reporteMovimientosPanel.actualizarPantalla();
	}
	
	public void exportarMovimientosProducto(List<MovimientoProductoDto> movimientos) {
		reporteServicio.exportarMovimientosAgrupadosPorProducto(movimientos);
	}
	
	public void exportarDetalleMovimientoProducto(List<MovimientoDto> movimientos, ProductoDto producto, String stockOriginal, String stockFinal) {
		reporteServicio.exportarMovimientosDeProducto(movimientos, producto, stockOriginal, stockFinal);
	}

	public void mostrarMovimientosPanel() {
		logger.info("Mostrando el panel de movimientos");
		int index = getReporteMovimientosPanel().getTable().getSelectedRow();
		reporteDetalleMovimientosPanel.setProducto(getReporteMovimientosPanel().getModel().getMovimiento(index).getProducto());
		reporteDetalleMovimientosPanel.setFechaInicio(getReporteMovimientosPanel().getFechaInicio());
		reporteDetalleMovimientosPanel.setFechaFin(getReporteMovimientosPanel().getFechaFinal());
		reporteDetalleMovimientosPanel.setStockOriginal(getReporteMovimientosPanel().getModel().getMovimiento(index).getStockOriginal());
		reporteDetalleMovimientosPanel.setStockFinal(getReporteMovimientosPanel().getModel().getMovimiento(index).getStockFinal());
		reporteMovimientosPanel.setVisible(false);
		ultimoPanelMostrado = reporteDetalleMovimientosPanel;
		reporteDetalleMovimientosPanel.setVisible(true);
		reporteDetalleMovimientosPanel.actualizarPantalla();
	}

	public void mostrarCompraSugerida(ReporteMovimientosDto dto) {
		logger.info("Mostrando el reporte de compra sugerida");
		reporteServicio.exportarCompraSugerida(dto);
	}	
	
	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(
			ReporteMovimientosDto filtro) {
		logger.info("Consultando movimientos de productos por filtro");

		List<MovimientoProductoDto> resultado = null;
		try {
			resultado = getReporteServicio().reporteMovimientosAgrupadosPorProducto(filtro);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		// chequeo si se encontro o no algo en la busqueda
		if (resultado == null || resultado.isEmpty())
			throw new PresentationException("error.venta.producto.noExiste");

		return resultado;
	}

	public List<MovimientoDto> reporteMovimientosDeProducto(ReporteDetalleMovimientosDto filtro) {
		logger.info("Consultando movimientos de productos por filtro");

		List<MovimientoDto> resultado = null;
		try {
			resultado = getReporteServicio().reporteMovimientosDeProducto(filtro);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		// chequeo si se encontro o no algo en la busqueda
		if (resultado == null || resultado.isEmpty())
			throw new PresentationException("error.venta.producto.noExiste");

		return resultado;
	}


	public void consultarVentaPorArticulo(ReporteMovimientosDto dto) {
		try {
			getReporteServicio().consultarVentaPorArticulo(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<MarcaDto> obtenerMarcas() {
		try {
			return productoServicio.obtenerMarcas();
		}
		catch (BusinessException e) {
			throw new PresentationException(e.getMessage(),	"Hubo un error al intentar obtener las marcas");
		}
	}

	public List<TipoDto> obtenerTipos() {
		try {
			return productoServicio.obtenerTipos();
		} 
		catch (BusinessException e) {
			throw new PresentationException(e.getMessage(),	"Hubo un error al intentar obtener los tipos");
		}
	}
	
	public IReportesServicio getReporteServicio() {
		return reporteServicio;
	}

	public ReporteMovimientosView getReporteMovimientosView() {
		return reporteMovimientosView;
	}

	public ReporteMovimientosPanel getReporteMovimientosPanel() {
		return reporteMovimientosPanel;
	}

	public IProductoServicio getProductoServicio() {
		return productoServicio;
	}

	public ReporteDetalleMovimientosPanel getReporteDetalleMovimientosPanel() {
		return reporteDetalleMovimientosPanel;
	}

	@Override
	public void mostrarUltimoPanelMostrado() {
		// TODO Auto-generated method stub
	}

}
