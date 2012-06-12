package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ProvinciaDto;

public interface IClienteServicio {

	ClienteDto guardarCliente(ClienteDto cliente);
	
	void eliminarCliente(ClienteDto cliente);
	
	List<ClienteDto> consultarCliente(ConsultaClienteDto filtro);
	
	List<CiudadDto> obtenerCiudades(ProvinciaDto provincia);
	
	List<ProvinciaDto> obtenerProvincias();

	void cargarReporte();
}
