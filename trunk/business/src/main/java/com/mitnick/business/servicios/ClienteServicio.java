package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.IDireccionDao;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ProvinciaDto;

@SuppressWarnings("rawtypes")
@Service("clienteServicio")
public class ClienteServicio extends ServicioBase implements IClienteServicio {

	@Autowired
	protected IClienteDao clienteDao;
	
	@Autowired
	protected IProvinciaDao provinciaDao;
	
	@Autowired
	protected ICiudadDao ciudadDao;
	
	@Autowired
	protected IDireccionDao direccionDao;
	
	@Transactional
	@Override
	public ClienteDto guardarCliente(ClienteDto clienteDto) {
		@SuppressWarnings("unchecked")
		Cliente cliente = (Cliente) entityDTOParser.getEntityFromDto(clienteDto);
		Set<ConstraintViolation<Cliente>> constraintViolations = entityValidator.validate(cliente);
		
		if(Validator.isNotEmptyOrNull(constraintViolations)) {
			StringBuffer buffer = new StringBuffer();
			for(ConstraintViolation<Cliente> constraint : constraintViolations) {
				buffer.append(constraint.getMessage()).append("\n");
			}
			throw new BusinessException(buffer.toString());
		}
		
		try {
			cliente = clienteDao.saveOrUpdate(cliente);
			clienteDto.setId(cliente.getId());
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar el cliente");
		}
		return clienteDto;
	}

	@Transactional
	@Override
	public void eliminarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			throw new BusinessException("error.clienteServicio.id.nulo", "Se invoca la eliminación de un cliente que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			@SuppressWarnings("unchecked")
			Cliente cliente = (Cliente) entityDTOParser.getEntityFromDto(clienteDto);
			cliente.setEliminado(true);
			clienteDao.saveOrUpdate(cliente);
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar eliminar el cliente");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		List<ClienteDto> resultado = new ArrayList<ClienteDto>();
		try {
			resultado = entityDTOParser.getDtosFromEntities(clienteDao.findByFiltro(filtro));
		}
		catch(PersistenceException e) {
			throw new BusinessException(e, "Error al intentar consultar clientes");
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProvinciaDto> obtenerProvincias() {
		return entityDTOParser.getDtosFromEntities(provinciaDao.getAll());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CiudadDto> obtenerCiudades(ProvinciaDto provincia) {
		return entityDTOParser.getDtosFromEntities(ciudadDao.getByProvincia((Provincia) entityDTOParser.getEntityFromDto(provincia)));
	}

	@Override
	public void cargarReporte() {
		clienteDao.cargarReporte();
	}

}
