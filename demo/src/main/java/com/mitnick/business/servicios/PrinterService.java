package com.mitnick.business.servicios;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IEmpresaDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Empresa;
import com.mitnick.servicio.servicios.ICierreZServicio;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CierreZDto;
import com.mitnick.utils.dtos.ConfiguracionImpresoraDto;
import com.mitnick.utils.dtos.VentaDto;

@Component(value="printerService")
public class PrinterService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ICierreZServicio cierreZServicio;
	
	@Autowired
	private IVentaServicio ventaServicio;
	
	@Autowired
	private IEmpresaDao empresaDao;
	
	
	@Autowired
	protected IVentaDAO ventaDao;
	
	public boolean imprimirTicket(VentaDto venta) {
		return true;
	}
	
	public boolean imprimirTicketFactura(VentaDto venta) {
		try{		
			Empresa empresa = empresaDao.getEmpresa();
			int nroFactActual = empresa.getNumeroFacturaActual();			
			String nroTRX = venta.getNumeroTicket();			
			if (Validator.isBlankOrNull(nroTRX)){
				nroFactActual = nroFactActual + 1;
				nroTRX = Integer.toString(nroFactActual);
				empresa.setNumeroFacturaActual(nroFactActual);
				empresaDao.saveOrUpdate(empresa);				
			}			
					
			venta.setNumeroTicket(String.valueOf(nroFactActual));
			
			ventaServicio.generarReporteFactura(venta, true);
		} catch (Exception e1) {
			throw new PersistenceException("error.reporte.factura.Cliente","Error al generar la factura del cliente.",e1);
		}	
		return true;
	}
	
	public boolean imprimirNotaCredito(VentaDto venta) {
		return true;
	}
	
	public boolean imprimirCierreZ() {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		int month = calendario.get(Calendar.DAY_OF_YEAR);
		
		String cierreNro = String.valueOf(month);		
		
		CierreZDto cierreZ = new CierreZDto();
		cierreZ.setNumero(cierreNro);
		cierreZ.setFecha(new Date());
		cierreZ.setNumeroCaja(PropertiesManager.getPropertyAsInteger("application.caja.numero"));
		cierreZServicio.guardarCierre(cierreZ);
		return true;
	}
	
	public boolean imprimirCierreX() {
		return true;
	}
	
	public boolean imprimirInformeJornada() {
		return true;
	}
	
	public boolean configurarImpresora(ConfiguracionImpresoraDto configuracion) {		
		return true;
	}
	
	public ConfiguracionImpresoraDto getInfoConfiguracion() {
		ConfiguracionImpresoraDto configuracion = new ConfiguracionImpresoraDto();
		return configuracion;
	}
	
	public boolean getInfoTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	public boolean getInfoNotaCredito(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	public boolean getInfoTicket(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	public boolean cancelarTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	public boolean cancelarNotaCredito(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	public boolean cancelarTicket(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	
}
