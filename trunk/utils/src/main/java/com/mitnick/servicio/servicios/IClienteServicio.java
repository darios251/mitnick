package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ProvinciaDto;

public interface IClienteServicio {

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	ClienteDto guardarCliente(ClienteDto cliente);
	
	@Secured(value={"ROLE_ADMIN"})
	void eliminarCliente(ClienteDto cliente);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ClienteDto> consultarCliente(ConsultaClienteDto filtro);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<CiudadDto> obtenerCiudades(ProvinciaDto provincia);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ProvinciaDto> obtenerProvincias();

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	void cargarReporte();
}
