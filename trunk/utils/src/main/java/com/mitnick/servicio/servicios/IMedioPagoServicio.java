package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.utils.dtos.MedioPagoDto;

public interface IMedioPagoServicio {

	List<MedioPagoDto> obtenerMediosPagos();
}
