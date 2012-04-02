package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.presentacion.vistas.PagoPanel;
import com.mitnick.presentacion.vistas.VentaPanel;
import com.mitnick.presentacion.vistas.VentaView;
import com.mitnick.presentacion.vistas.paneles.BuscarProductoPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;

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
	private IProductoServicio productoServicio;
	
	public VentaController() {
		
	}

	public VentaView getVentaView() {
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
		
/*
		List<ProductoDto> productos = new ArrayList<ProductoDto>(productoServicio.consultaProducto(filtro));
		
		VentaDto venta;
		if(productos.isEmpty()) {
			venta = iVentaServicio.agregarProducto(productos.get(0), venta);
			ventasPanel.getModel().addProductosVentas(venta.getProductos());
		}
		else {
			throw new Exception("No se encontro el producto");
		}
*/
		
// *********  Borrar cuando este implementado 	productoServicio.consultaProducto() ********	
		List<ProductoVentaDto> productos = new ArrayList<ProductoVentaDto>();
		
		ProductoVentaDto productoVenta = new ProductoVentaDto();
		ProductoDto producto = new ProductoDto();
		
		producto.setCodigo(codigo);
		producto.setDescripcion("Descripcion del producto " + producto.getCodigo() );
		BigDecimal precio = new BigDecimal(new Double((Math.random()*300)+10));
		producto.setPrecio(precio);
		productoVenta.setProducto(producto);
		
		productoVenta.setCantidad(1);
		BigDecimal precioTotal = producto.getPrecio().multiply(new BigDecimal(productoVenta.getCantidad()));
		productoVenta.setPrecioTotal(precioTotal);
		
		productos.add(productoVenta);
// **************************************************		
		
		ventaPanel.getModel().addProductosVentas(productos);
	}


	public void quitarProductoVentaDto(ProductoVentaDto productoVentaDto) {
		logger.info("Quitando elemento de la tabla");
		ventaPanel.getModel().removeProductoVenta(productoVentaDto);
		logger.info("Recalculando totales");
	}
}