package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.ClienteDto;

public class ClienteServicio extends ServicioBase implements IClienteServicio {

	IClienteDao clienteDao;

	@Override
	public ClienteDto altaCliente(ClienteDto clienteDto) {
		Cliente cliente = getClienteFromClienteDto(clienteDto);
		validarCliente(cliente);
		cliente = clienteDao.save(cliente);
		clienteDto.setId(cliente.getId());
		return clienteDto;
	}

	@Override
	public void modificarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			logger.error("Se invoca la modificacion de un cliente que no existe en la base de datos ya que no se brinda el ID");
			throw new BusinessException("No se puede modificar el cliente");
		}

		Cliente cliente = getClienteFromClienteDto(clienteDto);
		validarCliente(cliente);
		clienteDao.saveOrUpdate(cliente);
	}

	@Override
	public void eliminarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			logger.error("Se invoca la modificacion de un cliente que no existe en la base de datos ya que no se brinda el ID");
			throw new BusinessException("No se puede modificar el cliente");
		}
		clienteDao.remove(clienteDto.getId());
	}

	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		List<Cliente> clientes = clienteDao.findByDocumentoNombreApellido(filtro.getDocumento(), filtro.getNombre(), filtro.getApellido());
		
		List<ClienteDto> resultado = new ArrayList<ClienteDto>();
		for (int i = 0; i < clientes.size(); i++) {
			resultado.add(getClienteDtoFromClienteD(clientes.get(i)));			
		}
		return resultado;
	}

	// TODO
	private void validarCliente(Cliente cliente) {

	}

	// TODO
	private Cliente getClienteFromClienteDto(ClienteDto clienteDto) {
		Cliente cliente = new Cliente();

		return cliente;
	}

	// TODO
	private ClienteDto getClienteDtoFromClienteD(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();

		return clienteDto;
	}

	public IClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(IClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

}
