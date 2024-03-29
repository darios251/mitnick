package com.mitnick.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ReportesView;
import com.mitnick.presentacion.vistas.paneles.ReportesPanel;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.anotaciones.AuthorizationRequired;

@Controller("reportesController")
public class ReportesController extends BaseController {
	
	@Autowired
	private	IReportesServicio reportesServicio;
	
	@Autowired
	private	IVentaServicio ventaServicio;
	
	@Autowired
	private ReportesView reportesView;
	@Autowired
	private ReportesPanel reportesPanel;
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void reporteCaja(ReportesDto dto) {
		try {
			getReportesServicio().reporteCaja(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}

	public void reporteCajero(ReportesDto dto) {
		try {
			getReportesServicio().reporteCajero(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}

	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void reporteIngresos(ReportesDto dto, int tipo) {
		try {
			getReportesServicio().reporteIngresos(dto, tipo);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	public void reporteVendedor(ReportesDto dto) {
		try {
			getReportesServicio().reporteVendedor(dto);
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
	
	@AuthorizationRequired(role = MitnickConstants.Role.ADMIN)
	public void consultarReporteFacturas(ReportesDto dto) {
		try {
			getReportesServicio().reporteFacturas(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	public void consultarTransaccion(String nroTransaccion, String tipo, String factura, int numeroCaja) {
		try {
			ventaServicio.consultarTransaccion(nroTransaccion, tipo, factura, numeroCaja);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
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
