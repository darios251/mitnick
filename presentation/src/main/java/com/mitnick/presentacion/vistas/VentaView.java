package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.utils.anotaciones.View;
@View("ventaView")
public class VentaView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private String titulo = null;
	
	@Autowired
	private VentaController ventaController;

	public VentaView () {
		this.centrarVentana( null );
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
			this.add( ventaController.getVentaPanel(), BorderLayout.CENTER );
			this.add( ventaController.getBuscarProductoPanel(), BorderLayout.CENTER );
			this.add( ventaController.getPagoPanel(), BorderLayout.CENTER );
			ventaController.mostrarVentasPanel();
		}
		super.setVisible(aFlag);
	}

	public void setTitle( String title ) {
		
		this.titulo = title;
	}

	@Override
	protected void limpiarCamposPantalla() {
		
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}
}  