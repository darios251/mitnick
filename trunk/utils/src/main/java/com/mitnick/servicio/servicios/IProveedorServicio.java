package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;
import com.mitnick.utils.dtos.ProveedorDto;

public interface IProveedorServicio {

	@Secured(value={"ROLE_ADMIN"})
	ProveedorDto guardarProveedor(ProveedorDto proveedor);
	
	@Secured(value={"ROLE_ADMIN"})
	void bajaProveedor(ProveedorDto proveedor);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ProveedorDto> obtenerProveedores();
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ProveedorDto> consultaProveedor(ConsultaProveedorDto dto);
}
