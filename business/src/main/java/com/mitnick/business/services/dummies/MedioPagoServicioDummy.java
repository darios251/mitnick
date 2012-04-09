package com.mitnick.business.services.dummies;

import java.util.ArrayList;
import java.util.List;

import com.mitnick.business.services.ServicioBase;
import com.mitnick.servicio.servicios.IMedioPagoServicio;
import com.mitnick.utils.dtos.MedioPagoDto;

public class MedioPagoServicioDummy extends ServicioBase implements
		IMedioPagoServicio {

	@Override
	public List<MedioPagoDto> obtenerMediosPagos() {
		List<MedioPagoDto> mediosPago = new ArrayList<MedioPagoDto>();
		
		MedioPagoDto medioPago = new MedioPagoDto();
		medioPago.setId(1l);
		medioPago.setCodigo("1");
		medioPago.setDescripcion("Efectivo");
		
		mediosPago.add(medioPago);
		
		medioPago = new MedioPagoDto();
		medioPago.setId(2l);
		medioPago.setCodigo("2");
		medioPago.setDescripcion("Tarjeta D�bito");
		
		mediosPago.add(medioPago);
		
		medioPago = new MedioPagoDto();
		medioPago.setId(3l);
		medioPago.setCodigo("3");
		medioPago.setDescripcion("Tarjeta Cr�dito");
		
		mediosPago.add(medioPago);
		
		return mediosPago;
	}

}
