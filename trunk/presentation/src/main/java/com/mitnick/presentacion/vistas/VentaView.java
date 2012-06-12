package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.utils.anotaciones.View;
@View("ventaView")
public class VentaView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VentaController ventaController;
	

	public VentaView () {
		initializeComponents();
	}
	
	@Override
	protected void initializeComponents () {
		this.setSize( new Dimension(815, 470) );
		this.setLayout( new BorderLayout() );
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			if(!initialized) {
				this.add( ventaController.getVentaPanel(), BorderLayout.CENTER );
				this.add( ventaController.getBuscarProductoPanel(), BorderLayout.CENTER );
				this.add( ventaController.getPagoPanel(), BorderLayout.CENTER );
				this.add( ventaController.getDetalleProductoPanel(), BorderLayout.CENTER );
				this.add( ventaController.getVentaClientePanel(), BorderLayout.CENTER );
				this.add( ventaController.getClienteNuevoPanel(), BorderLayout.CENTER );
				initialized = true;
			}
		}
		super.setVisible(aFlag);
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}
}  