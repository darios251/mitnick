package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.utils.anotaciones.View;

@View("clienteView")
public class ClienteView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClienteController clienteController;

	public ClienteView () {
		this.centrarVentana( null );
		initializeComponents();
	}
	
	@Override
	protected void initializeComponents () {
		this.setSize( new Dimension(815, 470) );
		
		this.setLayout( new BorderLayout() );
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			this.add( clienteController.getClientePanel(), BorderLayout.CENTER );
			clienteController.mostrarClientePanel();
		}
		super.setVisible(aFlag);
	}

	@Override
	protected void limpiarCamposPantalla() {
		
	}

	public void setClienteController(ClienteController clienteController) {
		this.clienteController = clienteController;
	}
}  