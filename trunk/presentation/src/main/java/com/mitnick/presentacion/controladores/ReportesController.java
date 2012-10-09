package com.mitnick.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.presentacion.vistas.ReportesView;
import com.mitnick.presentacion.vistas.paneles.BasePanel;
import com.mitnick.presentacion.vistas.paneles.ReportesPanel;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReportesDto;

@Controller("reportesController")
public class ReportesController extends BaseController {
	
	@Autowired
	private	IReportesServicio reportesServicio;
	
	@Autowired
	private ReportesView reportesView;
	@Autowired
	private ReportesPanel reportesPanel;
	

	public void reporteIngresos(ReportesDto dto) {
		try {
			getReportesServicio().reporteIngresos(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}

	public void reporteIngresosAgrupados(ReportesDto dto) {
		try {
			getReportesServicio().reporteIngresosAgrupados(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	} 
	
	
	public void consultarEstadoCuentas(ReportesDto dto) {
		try {
			getReportesServicio().consultarEstadoCuentas(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void consultarListadoDeControl(ReportesDto dto) {
		try {
			getReportesServicio().consultarListadoDeControl(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void consultarVentaPorArticulo(ReportesDto dto) {
		try {
			getReportesServicio().consultarVentaPorArticulo(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void consultarVentasAnual(ReportesDto dto) {
		try {
			getReportesServicio().reporteIngresosAnual(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void consultarStockArticulo(ReportesDto dto) {
		try {
			getReportesServicio().consultarStockArticulo(dto);
		}
		catch(BusinessException e) {
			e.printStackTrace();
		}
	}	
	
	public void mostrarReportesPanel() {
		logger.info("Mostrando el panel de movimientos");
		reportesPanel.setVisible(true);
		ultimoPanelMostrado = reportesPanel;
		reportesPanel.actualizarPantalla();
	}
	
	public IReportesServicio getReportesServicio() {
		return reportesServicio;
	}

	public void setReportesServicio(IReportesServicio reportesServicio) {
		this.reportesServicio = reportesServicio;
	}
	
	public BasePanel getUltimoPanelMostrado() {
		return ultimoPanelMostrado;
	}
	
	public void setUltimoPanelMostrado(BasePanel ultimoPanelMostrado) {
		this.ultimoPanelMostrado = ultimoPanelMostrado;
	}


	public ReportesView getReportesView() {
		return reportesView;
	}


	public void setReportesView(ReportesView reportesView) {
		this.reportesView = reportesView;
	}


	public ReportesPanel getReportesPanel() {
		return reportesPanel;
	}


	public void setReportesPanel(ReportesPanel reportesPanel) {
		this.reportesPanel = reportesPanel;
	}
	
	

}
