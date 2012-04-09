package com.mitnick.business.services.dummies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mitnick.business.services.ServicioBase;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.ClienteDto;

public class ClienteServicioDummy extends ServicioBase implements
		IClienteServicio {

	@Override
	public ClienteDto altaCliente(ClienteDto cliente) {
		cliente.setId((long)Math.random());
		return cliente;
	}

	@Override
	public void modificarCliente(ClienteDto cliente) {
		return;
	}

	@Override
	public void eliminarCliente(ClienteDto cliente) {
		return;
	}

	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		ClienteDto cliente = new ClienteDto();
		cliente.setId(1l);
		cliente.setApellido(filtro.getApellido());
		cliente.setNombre(filtro.getNombre());
		cliente.setDocumento(filtro.getDocumento());
		cliente.setEmail("email@test.com");
		cliente.setFechaNacimiento(new Date());
		cliente.setTelefono("342444567");
		
		List<ClienteDto> clientes = new ArrayList<ClienteDto>();
		clientes.add(cliente);
		return clientes;
	}

}
