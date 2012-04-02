package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.utils.anotaciones.View;

@View("productoView")
public class ProductoView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private String titulo = null;
	
	@Autowired
	private ProductoController productoController;

	public ProductoView () {
		this.titulo = "colocar titulo";
		initializeComponents();
	}
	
	@Override
	protected void initializeComponents () {
		this.setSize( new Dimension(815, 470) );
		
		this.setLayout( new BorderLayout() );
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			this.add( productoController.getProductoPanel(), BorderLayout.CENTER );
			this.add( productoController.getProductoNuevoPanel(), BorderLayout.CENTER );
			productoController.mostrarProductosPanel();
		}
		super.setVisible(aFlag);
	}

	public void setTitle( String title ) {
		
		this.titulo = title;
	}

	@Override
	protected void limpiarCamposPantalla() {
		
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
}  