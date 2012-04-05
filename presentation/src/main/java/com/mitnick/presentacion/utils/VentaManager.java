package com.mitnick.presentacion.utils;

import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.utils.dtos.VentaDto;

public class VentaManager {

	private static VentaDto ventaActual;
	
	public static VentaDto crearNuevaVenta() {
		ventaActual = new VentaDto();
		return ventaActual;
	}
	
	public static void eliminarVenta() {
		ventaActual = null;
	}
	
	public static void setVentaActual(VentaDto venta) {
		ventaActual = venta;
	}
	
	public static VentaDto getVentaActual() {
		if(ventaActual == null)
			throw new PresentationException("error.venta.ventaActual.null");
		return ventaActual;
	}
}
