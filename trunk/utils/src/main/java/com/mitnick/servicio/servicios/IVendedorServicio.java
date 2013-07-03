package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ConsultaVendedorDto;
import com.mitnick.utils.dtos.VendedorDto;

public interface IVendedorServicio {
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public List<VendedorDto> consultaVendedor(ConsultaVendedorDto dto);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	public List<VendedorDto> obtenerVendedores();

	@Secured(value={"ROLE_ADMIN"})
	public VendedorDto guardarVendedor(VendedorDto dto);
	
	@Secured(value={"ROLE_ADMIN"})
	public void bajaVendedor(VendedorDto dto);

}
