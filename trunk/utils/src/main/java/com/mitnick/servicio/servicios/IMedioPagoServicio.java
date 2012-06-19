package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.utils.dtos.MedioPagoDto;

public interface IMedioPagoServicio {

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<MedioPagoDto> obtenerMediosPagos();
}
