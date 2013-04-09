package com.mitnick.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ReportesView;
import com.mitnick.presentacion.vistas.paneles.ReportesPanel;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.anotaciones.AuthorizationRequired;

@Controller("reportesController")
public class ReportesController extends BaseController {
	
	@Autowired
	private	IReportesServicio reportesServicio;
	
	@Autowired
	private ReportesView reportesView;
	@Autowired
	private ReportesPanel reportesPanel;
	

	public void reporteIngresos(ReportesDto dto, int tipo) {
		try {
			getReportesServicio().reporteIngresos(dto, tipo);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}

	public void consultarEstadoCuentas(ReportesDto dto) {
		try {
			getReportesServicio().consultarEstadoCuentas(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	public void consultarEstadoCuentasCliente(ReportesDto dto) {
		try {
			getReportesServicio().consultarEstadoCuentasPorCliente(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	public void consultarReporteFacturas(ReportesDto dto) {
		try {
			getReportesServicio().reporteFacturas(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	
	public void consultarListadoDeControl(ReportesDto dto) {
		try {
			getReportesServicio().consultarListadoDeControl(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	public void consultarListadoDeRecibo(ReportesDto dto) {
		try {
			getReportesServicio().consultarListadoDeRecibos(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
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
	
	@Override
	public void mostrarUltimoPanelMostrado() {
		// TODO Auto-generated method stub
	}

}
