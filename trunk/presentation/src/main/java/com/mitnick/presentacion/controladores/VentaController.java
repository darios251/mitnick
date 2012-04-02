package com.mitnick.presentacion.controladores;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.presentacion.vistas.PagoPanel;
import com.mitnick.presentacion.vistas.VentaPanel;
import com.mitnick.presentacion.vistas.VentaView;
import com.mitnick.presentacion.vistas.paneles.BuscarProductoPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Controller("ventaController")
public class VentaController extends BaseController {

	private static Logger logger = Logger.getLogger(VentaController.class);
	
	@Autowired
	private VentaView ventaView;
	@Autowired
	private VentaPanel ventaPanel;
	@Autowired
	private PagoPanel pagoPanel;
	@Autowired
	private BuscarProductoPanel buscarProductoPanel;
	
	@Autowired
	private	IVentaServicio ventaServicio;
	
	@Autowired
	private IProductoServicio productoServicio;
	
	// venta actual
	private VentaDto venta;
	
	public VentaController() {
		
	}

	public VentaView getVentaView() {
		venta = new VentaDto();
		return ventaView;
	}

	public void setVentaView(VentaView ventaView) {
		this.ventaView = ventaView;
	}

	public VentaPanel getVentaPanel() {
		return ventaPanel;
	}

	public void setVentaPanel(VentaPanel ventaPanel) {
		this.ventaPanel = ventaPanel;
	}

	public PagoPanel getPagoPanel() {
		return pagoPanel;
	}

	public void setPagoPanel(PagoPanel pagoPanel) {
		this.pagoPanel = pagoPanel;
	}

	public BuscarProductoPanel getBuscarProductoPanel() {
		return buscarProductoPanel;
	}

	public void setBuscarProductoPanel(BuscarProductoPanel buscarProductoPanel) {
		this.buscarProductoPanel = buscarProductoPanel;
	}

	public void mostrarBuscarArticuloPanel() {
		ventaPanel.setVisible(false);
		pagoPanel.setVisible(false);
		buscarProductoPanel.setVisible(true);
	}
	
	public void mostrarVentasPanel() {
		buscarProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		ventaPanel.setVisible(true);
	}
	
	public void mostrarPagosPanel() {
		buscarProductoPanel.setVisible(false);
		ventaPanel.setVisible(false);
		pagoPanel.setVisible(true);
	}
	
	public void limpiarVenta() {
		ventaPanel.limpiarCamposPantalla();
		buscarProductoPanel.limpiarCamposPantalla();
		pagoPanel.limpiarCamposPantalla();
	}

	public void agregarProducto(String codigo) {
		ConsultaProductoDto filtro = new ConsultaProductoDto();
		filtro.setCodigo(codigo);
		
		List<ProductoDto> productos = new ArrayList<ProductoDto>(productoServicio.consultaProducto(filtro));
		
		if(!productos.isEmpty()) {
			venta = ventaServicio.agregarProducto(productos.get(0), venta);
			ventaPanel.getModel().setProductosVenta(venta.getProductos());
		}
		else {
			throw new PresentationException("No se encontro el producto");
		}
	}


	public void quitarProductoVentaDto(ProductoVentaDto productoVentaDto) {
		logger.info("Quitando elemento de la tabla");
		ventaPanel.getModel().removeProductoVenta(productoVentaDto);
		logger.info("Recalculando totales");
	}

	public void setVentaServicio(IVentaServicio ventaServicio) {
		this.ventaServicio = ventaServicio;
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}
}