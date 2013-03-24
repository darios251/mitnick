package com.mitnick.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitnick.servicio.servicios.ICierreZServicio;
import com.mitnick.utils.dtos.ConfiguracionImpresoraDto;
import com.mitnick.utils.dtos.VentaDto;

@Component(value="printerService")
public class PrinterService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ICierreZServicio cierreZServicio;
	
	@SuppressWarnings("deprecation")
	public boolean imprimirTicket(VentaDto venta) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirTicketFactura(VentaDto venta) {
		venta.setNumeroTicket(String.valueOf(System.currentTimeMillis()));
		venta.setTipoTicket("A");
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirNotaCredito(VentaDto venta) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirCierreZ() {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirCierreX() {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirInformeJornada() {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean configurarImpresora(ConfiguracionImpresoraDto configuracion) {		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public ConfiguracionImpresoraDto getInfoConfiguracion() {
		ConfiguracionImpresoraDto configuracion = new ConfiguracionImpresoraDto();
		return configuracion;
	}
	
	@SuppressWarnings("deprecation")
	public boolean getInfoTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean getInfoNotaCredito(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean getInfoTicket(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean cancelarTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean cancelarNotaCredito(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean cancelarTicket(VentaDto venta, boolean useCurrentConnection) {
		return true;
	}
	
	
}
