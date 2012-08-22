package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ReportesController;
import com.mitnick.utils.anotaciones.View;

@View("reportesView")
public class ReportesView extends BaseView {

private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportesController reportesController;
	
	private boolean initialized = false;

	public ReportesView () {
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
				this.add( reportesController.getReportesPanel(), BorderLayout.CENTER );
				initialized = true;
			}
			if(reportesController.getUltimoPanelMostrado() != null)
				reportesController.getUltimoPanelMostrado().setVisible(aFlag);
		}
		super.setVisible(aFlag);
	}

	public ReportesController getReportesController() {
		return reportesController;
	}

	public void setReportesController(ReportesController reportesController) {
		this.reportesController = reportesController;
	}

	
	
}
