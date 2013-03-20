package com.mitnick.servicio.servicios;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.utils.dtos.CierreZDto;

public interface ICierreZServicio {

	@Secured(value={"ROLE_ADMIN"})
	void guardarCierre(CierreZDto cierreZ);
	
}
