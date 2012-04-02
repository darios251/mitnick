package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.ClienteDto;

public interface IClienteServicio {

	ClienteDto altaCliente(ClienteDto cliente);
	
	void modificarCliente(ClienteDto cliente);
	
	void eliminarCliente(ClienteDto cliente);
	
	List<ClienteDto> consultarCliente(ConsultaClienteDto filtro);
}
