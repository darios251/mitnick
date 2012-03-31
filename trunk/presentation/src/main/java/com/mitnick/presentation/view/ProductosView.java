package com.mitnick.presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.mitnick.presentation.controller.ProductosController;


public class ProductosView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private String titulo = null;
	
	private ProductosController productosController;


	public ProductosView (String titulo) {
		
		this.titulo = titulo;
		initializeComponents();
	}
	
	@Override
	protected void initializeComponents () {
		
		this.setSize( new Dimension(815, 470) );
		
		this.setLayout( new BorderLayout() );
		
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			this.add( productosController.getProductosPanel(), BorderLayout.CENTER );
			this.add( productosController.getProductoNuevoPanel(), BorderLayout.CENTER );
			productosController.mostrarProductosPanel();
		}
		super.setVisible(aFlag);
	}

	public String getTitle () {
		
		return titulo;
	}

	public void setTitle( String title ) {
		
		this.titulo = title;
	}

	@Override
	protected void limpiarCamposPantalla() {
		
	}

	public void setProductosController(ProductosController productosController) {
		this.productosController = productosController;
	}



}  