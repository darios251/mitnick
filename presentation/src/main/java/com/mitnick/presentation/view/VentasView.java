package com.mitnick.presentation.view;

import java.awt.BorderLayout;

import com.mitnick.presentation.controller.VentasController;


public class VentasView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private String titulo = null;
	
	private VentasController ventasController;


	public VentasView (String titulo) {
		
		this.centrarVentana( null );
		this.titulo = titulo;
		initializeComponents();
	}
	
	@Override
	protected void initializeComponents () {
		
		this.setSize( 503, 471 );
		
		this.setLayout( new BorderLayout() );
//		this.add( ventasPanel, BorderLayout.CENTER );
		
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			this.add( ventasController.getVentasPanel(), BorderLayout.CENTER );
			this.add( ventasController.getBuscarProdcutoPanel(), BorderLayout.CENTER );
			ventasController.mostrarVentasPanel();
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

	public void setVentasController(VentasController ventasController) {
		this.ventasController = ventasController;
	}

}  