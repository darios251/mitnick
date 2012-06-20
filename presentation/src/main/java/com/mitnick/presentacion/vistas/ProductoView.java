package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.utils.anotaciones.View;

@View("productoView")
public class ProductoView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProductoController productoController;
	
	private boolean initialized = false;

	public ProductoView () {
		initializeComponents();
	}
	
	@Override
	protected void initializeComponents () {
		this.setSize( new Dimension(815, 470) );
		
		this.setLayout( new BorderLayout() );
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			if(!initialized ){
				this.add( productoController.getProductoPanel(), BorderLayout.CENTER );
				this.add( productoController.getProductoNuevoPanel(), BorderLayout.CENTER );
				initialized = true;
			}
			if(productoController.getUltimoPanelMostrado() != null)
				productoController.getUltimoPanelMostrado().setVisible(aFlag);
		}
		super.setVisible(aFlag);
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
}  