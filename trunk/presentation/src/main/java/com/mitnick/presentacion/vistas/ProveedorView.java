package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ProveedorController;
import com.mitnick.utils.anotaciones.View;

@View("proveedorView")
public class ProveedorView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProveedorController proveedorController;
	
	private boolean initialized = false;

	public ProveedorView () {
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
				this.add( proveedorController.getProveedorPanel(), BorderLayout.CENTER );
				this.add( proveedorController.getProveedorNuevoPanel(), BorderLayout.CENTER );
				initialized = true;
			}
			if(proveedorController.getUltimoPanelMostrado() != null)
				proveedorController.getUltimoPanelMostrado().setVisible(aFlag);
		}
		super.setVisible(aFlag);
	}

}  