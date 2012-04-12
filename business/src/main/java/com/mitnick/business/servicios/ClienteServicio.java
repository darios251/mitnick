package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;

@Service("clienteServicio")
public class ClienteServicio extends ServicioBase implements IClienteServicio {

	@Autowired
	protected IClienteDao clienteDao;

	@Transactional
	@Override
	public ClienteDto altaCliente(ClienteDto clienteDto) {
		validar(clienteDto);
		try {
			Cliente cliente = entityDTOParser.getEntityFromDto(clienteDto);
			cliente = clienteDao.saveOrUpdate(cliente);
			clienteDto.setId(cliente.getId());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return clienteDto;
	}

	@Transactional
	@Override
	public void modificarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			throw new BusinessException("error.clienteServicio.id.nulo", "Se invoca la modificacion de un cliente que no existe en la base de datos ya que no se brinda el ID");
		}

		validar(clienteDto);
		try {
			Cliente cliente = entityDTOParser.getEntityFromDto(clienteDto);
			clienteDao.saveOrUpdate(cliente);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
	}

	@Transactional
	@Override
	public void eliminarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			throw new BusinessException("error.clienteServicio.id.nulo", "Se invoca la eliminación de un cliente que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			clienteDao.remove(clienteDto.getId());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		List<ClienteDto> resultado = new ArrayList<ClienteDto>();
		try {
			for (Cliente cliente:clienteDao.findByFiltro(filtro))
				resultado.add(entityDTOParser.getDtoFromEntity(cliente));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}

	private void validar(ClienteDto clienteDto) {
		if (Validator.isBlankOrNull(clienteDto.getApellido()))
			throw new BusinessException("error.clienteServicio.apellido.nulo");	
		if (Validator.isBlankOrNull(clienteDto.getNombre()))
			throw new BusinessException("error.clienteServicio.nombre.nulo");		
		if (Validator.isBlankOrNull(clienteDto.getTelefono()))
			throw new BusinessException("error.clienteServicio.telefono.nulo");		
		if (Validator.isNull(clienteDto.getDireccion()))
			throw new BusinessException("error.clienteServicio.direccion.nulo");		
		if (Validator.isBlankOrNull(clienteDto.getDireccion().getDomicilio()))
			throw new BusinessException("error.clienteServicio.direccion.nulo");		
		if (Validator.isNull(clienteDto.getDireccion().getCiudad()))
			throw new BusinessException("error.clienteServicio.direccion.nulo");		
	}

	public void setClienteDao(IClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

}
