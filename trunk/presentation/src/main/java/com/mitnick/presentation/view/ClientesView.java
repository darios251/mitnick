package com.mitnick.presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.mitnick.presentation.controller.ClientesController;


public class ClientesView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private String titulo = null;
	
	private ClientesController clientesController;


	public ClientesView (String titulo) {
		this.centrarVentana( null );
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
			this.add( clientesController.getClientesPanel(), BorderLayout.CENTER );
			clientesController.mostrarClientesPanel();
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

	public void setClientesController(ClientesController clientesController) {
		this.clientesController = clientesController;
	}


}  