package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.VendedorController;
import com.mitnick.utils.anotaciones.View;

@View("vendedorView")
public class VendedorView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VendedorController vendedorController;
	
	private boolean initialized = false;

	public VendedorView() {
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
				this.add( vendedorController.getVendedorPanel(), BorderLayout.CENTER );
				this.add( vendedorController.getVendedorNuevoPanel(), BorderLayout.CENTER );
				initialized = true;
			}
			if(vendedorController.getUltimoPanelMostrado() != null)
				vendedorController.getUltimoPanelMostrado().setVisible(aFlag);
		}
		super.setVisible(aFlag);
	}

}  