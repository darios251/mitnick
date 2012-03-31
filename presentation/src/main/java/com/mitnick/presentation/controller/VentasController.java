package com.mitnick.presentation.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mitnick.business.servicios.IProductoServicio;
import com.mitnick.business.servicios.dtos.ConsultaProductoDto;
import com.mitnick.presentation.runner.Runner;
import com.mitnick.presentation.view.BuscarProductoPanel;
import com.mitnick.presentation.view.PagosPanel;
import com.mitnick.presentation.view.VentasPanel;
import com.mitnick.presentation.view.VentasView;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;

public class VentasController extends BaseController {

	private static Logger logger = Logger.getLogger(VentasController.class);
	
	private VentasView ventasView;
	private VentasPanel ventasPanel;
	private PagosPanel pagosPanel;
	private BuscarProductoPanel buscarProductoPanel;
	
	private IProductoServicio productoServicio;
	
	public VentasController() {
		
	}

	

	public VentasView getVentasView() {
		return ventasView;
	}



	public void setVentasView(VentasView ventasView) {
		this.ventasView = ventasView;
	}



	public VentasPanel getVentasPanel() {
		return ventasPanel;
	}



	public void setVentasPanel(VentasPanel ventasPanel) {
		this.ventasPanel = ventasPanel;
	}



	public PagosPanel getPagosPanel() {
		return pagosPanel;
	}



	public void setPagosPanel(PagosPanel pagosPanel) {
		this.pagosPanel = pagosPanel;
	}



	public BuscarProductoPanel getBuscarProductoPanel() {
		return buscarProductoPanel;
	}



	public void setBuscarProductoPanel(BuscarProductoPanel buscarProductoPanel) {
		this.buscarProductoPanel = buscarProductoPanel;
	}



	public void mostrarBuscarArticuloPanel() {
		ventasPanel.setVisible(false);
		pagosPanel.setVisible(false);
		buscarProductoPanel.setVisible(true);
	}
	
	public void mostrarVentasPanel() {
		buscarProductoPanel.setVisible(false);
		pagosPanel.setVisible(false);
		ventasPanel.setVisible(true);
	}
	
	public void mostrarPagosPanel() {
		buscarProductoPanel.setVisible(false);
		ventasPanel.setVisible(false);
		pagosPanel.setVisible(true);
	}
	
	public void limpiarVenta() {
		ventasPanel.limpiarCamposPantalla();
		buscarProductoPanel.limpiarCamposPantalla();
		pagosPanel.limpiarCamposPantalla();
	}

	public void agregarProducto(String codigo) {
		ConsultaProductoDto filtro = new ConsultaProductoDto();
		filtro.setCodigo(codigo);
		
		// TODO Auto-generated method stub
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
		
		ventasPanel.getModel().addProductosVentas(productos);
	}


	public void quitarProductoVentaDto(ProductoVentaDto productoVentaDto) {
		logger.info("Quitando elemento de la tabla");
		ventasPanel.getModel().removeProductoVenta(productoVentaDto);
		logger.info("Recalculando totales");
	}
}
