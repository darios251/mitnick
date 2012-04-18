package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.IDireccionDao;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.util.EntityDTOParser;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ProvinciaDto;

@Service("clienteServicio")
public class ClienteServicio extends ServicioBase<Cliente, ClienteDto> implements IClienteServicio {

	@Autowired
	protected IClienteDao clienteDao;
	
	@Autowired
	protected IProvinciaDao provinciaDao;
	
	@Autowired
	protected ICiudadDao ciudadDao;
	
	@Autowired
	protected IDireccionDao direccionDao;
	
	@Autowired
	protected EntityDTOParser<Provincia, ProvinciaDto> entityDTOParserProvincia;
	
	@Autowired
	protected EntityDTOParser<Ciudad, CiudadDto> entityDTOParserCiudad;

	@Transactional
	@Override
	public ClienteDto guardarCliente(ClienteDto clienteDto) {
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
	public void eliminarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			throw new BusinessException("error.clienteServicio.id.nulo", "Se invoca la eliminación de un cliente que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			Cliente cliente = entityDTOParser.getEntityFromDto(clienteDto);
			cliente.setEliminado(true);
			clienteDao.saveOrUpdate(cliente);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

	}

	@Transactional(readOnly=true)
	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		List<ClienteDto> resultado = new ArrayList<ClienteDto>();
		try {
			resultado = entityDTOParser.getDtosFromEntities(clienteDao.findByFiltro(filtro));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}
	
	@Override
	public List<ProvinciaDto> obtenerProvincias() {
		return entityDTOParserProvincia.getDtosFromEntities(provinciaDao.getAll());
	}
	
	@Override
	public List<CiudadDto> obtenerCiudades(ProvinciaDto provincia) {
		return entityDTOParserCiudad.getDtosFromEntities(ciudadDao.getAll());
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
	
	public void setProvinciaDao(IProvinciaDao provinciaDao) {
		this.provinciaDao = provinciaDao;
	}
	
	public void setCiudadDao(ICiudadDao ciudadDao) {
		this.ciudadDao = ciudadDao;
	}
	
	public void setDireccionDao(IDireccionDao direccionDao) {
		this.direccionDao = direccionDao;
	}

	public void setEntityDTOParserCiudad(
			EntityDTOParser<Ciudad, CiudadDto> entityDTOParserCiudad) {
		this.entityDTOParserCiudad = entityDTOParserCiudad;
	}
	
	public void setEntityDTOParserProvincia(
			EntityDTOParser<Provincia, ProvinciaDto> entityDTOParserProvincia) {
		this.entityDTOParserProvincia = entityDTOParserProvincia;
	}
}