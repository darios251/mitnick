package com.mitnick.presentacion.controladores;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.presentacion.vistas.ProductoNuevoPanel;
import com.mitnick.presentacion.vistas.ProductoPanel;
import com.mitnick.presentacion.vistas.ProductoView;

@Controller("productoController")
public class ProductoController extends BaseController {
	
	private static Logger logger = Logger.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoView productoView;
	@Autowired
	private ProductoPanel productoPanel;
	@Autowired
	private ProductoNuevoPanel productoNuevoPanel;
	
	public ProductoController() {
		
	}
	
	public ProductoView getProductoView() {
		return productoView;
	}

	public void setProductoView(ProductoView productoView) {
		this.productoView = productoView;
	}

	public ProductoPanel getProductoPanel() {
		return productoPanel;
	}

	public void setProductoPanel(ProductoPanel productoPanel) {
		this.productoPanel = productoPanel;
	}

	public ProductoNuevoPanel getProductoNuevoPanel() {
		return productoNuevoPanel;
	}

	public void setProductoNuevoPanel(ProductoNuevoPanel productoNuevoPanel) {
		this.productoNuevoPanel = productoNuevoPanel;
	}

	public void mostrarProductoNuevoPanel() {
		logger.info("Mostrando el panel de producto nuevo");
		productoPanel.setVisible(false);
		productoNuevoPanel.setVisible(true);
	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		productoNuevoPanel.setVisible(false);
		productoPanel.setVisible(true);
		
	}
}