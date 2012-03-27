package com.mitnick.presentation.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mitnick.business.servicios.IProductoServicio;
import com.mitnick.business.servicios.dtos.ConsultaProductoDto;
import com.mitnick.presentation.view.BaseView;
import com.mitnick.presentation.view.VentasPanel;
import com.mitnick.presentation.view.VentasView;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

public class VentasController extends BaseController {

	
	
	private VentasView ventasView;
	private VentasPanel ventasPanel;
	private BaseView buscarProductoPanel;
	private IProductoServicio productoServicio;
	
	public VentasController() {
		
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

	public BaseView getBuscarProdcutoPanel() {
		return buscarProductoPanel;
	}

	public void setBuscarProductoPanel(BaseView buscarProductoPanel) {
		this.buscarProductoPanel = buscarProductoPanel;
	}

	public void mostrarBuscarArticuloPanel() {
		ventasPanel.setVisible(false);
		buscarProductoPanel.setVisible(true);
	}
	
	public void mostrarVentasPanel() {
		ventasPanel.setVisible(true);
		buscarProductoPanel.setVisible(false);
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
		
		producto.setCodigo("1");
		producto.setDescripcion("agregado");
		producto.setPrecio(new BigDecimal(140.50));
		productoVenta.setProducto(producto);
		
		productoVenta.setCantidad(1);
		BigDecimal precioTotal = producto.getPrecio().multiply(new BigDecimal(productoVenta.getCantidad()));
		productoVenta.setPrecioTotal(precioTotal);
		
		productos.add(productoVenta);
// **************************************************		
		
		ventasPanel.getModel().addProductosVentas(productos);
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}
}
