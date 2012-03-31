package com.mitnick.presentation.controller;

import org.apache.log4j.Logger;

import com.mitnick.presentation.view.ProductoNuevoPanel;
import com.mitnick.presentation.view.ProductosPanel;
import com.mitnick.presentation.view.ProductosView;

public class ProductosController extends BaseController {
	
	private static Logger logger = Logger.getLogger(ProductosController.class);
	
	
	private ProductosView productosView;
	private ProductosPanel productosPanel;
	private ProductoNuevoPanel productoNuevoPanel;
	
	public ProductosController() {
		
	}

	
	
	public ProductosView getProductosView() {
		return productosView;
	}



	public void setProductosView(ProductosView productosView) {
		this.productosView = productosView;
	}



	public ProductosPanel getProductosPanel() {
		return productosPanel;
	}



	public void setProductosPanel(ProductosPanel productosPanel) {
		this.productosPanel = productosPanel;
	}



	public ProductoNuevoPanel getProductoNuevoPanel() {
		return productoNuevoPanel;
	}



	public void setProductoNuevoPanel(ProductoNuevoPanel productoNuevoPanel) {
		this.productoNuevoPanel = productoNuevoPanel;
	}



	public void mostrarProductoNuevoPanel() {
		logger.info("Mostrando el panel de producto nuevo");
		productosPanel.setVisible(false);
		productoNuevoPanel.setVisible(true);
	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		productoNuevoPanel.setVisible(false);
		productosPanel.setVisible(true);
		
	}

	
}
