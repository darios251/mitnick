package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ReporteController;
import com.mitnick.utils.anotaciones.View;

@View("reporteMovimientosView")
public class ReporteMovimientosView extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReporteController reporteController;
	
	private boolean initialized = false;

	public ReporteMovimientosView () {
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
				this.add( reporteController.getReporteMovimientosPanel(), BorderLayout.CENTER );
				initialized = true;
			}
		}
		super.setVisible(aFlag);
	}

	public ReporteController getReporteController() {
		return reporteController;
	}

	public void setReporteController(ReporteController reporteController) {
		this.reporteController = reporteController;
	}

	
}  