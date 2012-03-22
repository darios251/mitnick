package com.mitnick.business.servicios;

import java.util.List;

import com.mitnick.business.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.ClienteDto;

public interface IClienteServicio {

	ClienteDto altaCliente(ClienteDto cliente);
	
	void modificarCliente(ClienteDto cliente);
	
	void eliminarCliente(ClienteDto cliente);
	
	List<ClienteDto> consultarCliente(ConsultaClienteDto filtro);
}
