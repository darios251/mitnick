package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Direccion;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.DireccionDto;
import com.mitnick.utils.dtos.ProvinciaDto;

@Service("clienteServicio")
public class ClienteServicio extends ServicioBase implements IClienteServicio {

	@Autowired
	IClienteDao clienteDao;
	@Autowired
	ICiudadDao ciudadDao;

	@Override
	public ClienteDto altaCliente(ClienteDto clienteDto) {
		validar(clienteDto);
		Cliente cliente = getClienteFromClienteDto(clienteDto);
		try {
			cliente = clienteDao.save(cliente);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		clienteDto.setId(cliente.getId());
		return clienteDto;
	}

	@Override
	public void modificarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			throw new BusinessException("error.clienteServicio.id.nulo", "Se invoca la modificacion de un cliente que no existe en la base de datos ya que no se brinda el ID");
		}

		validar(clienteDto);
		Cliente cliente = getClienteFromClienteDto(clienteDto);

		try {
			clienteDao.saveOrUpdate(cliente);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
	}

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

	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		List<Cliente> clientes = null;
		try {
			clientes = clienteDao.findByFiltro(filtro);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		List<ClienteDto> resultado = new ArrayList<ClienteDto>();
		for (int i = 0; i < clientes.size(); i++) {
			resultado.add(getClienteDtoFromCliente(clientes.get(i)));
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

	private Cliente getClienteFromClienteDto(ClienteDto clienteDto) {
		Cliente cliente = null;
		if (Validator.isNotNull(clienteDto.getId()))
			try {
				cliente = clienteDao.get(clienteDto.getId());
			} catch (Exception e) {
				throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
			}

		else
			cliente = new Cliente();
		
		cliente.setApellido(clienteDto.getApellido());
		cliente.setNombre(clienteDto.getNombre());
		cliente.setCuit(new Long(clienteDto.getCuit()));
		cliente.setDocumento(new Long(clienteDto.getDocumento()));
		cliente.setEmail(clienteDto.getEmail());
		cliente.setFechaNacimiento(clienteDto.getFechaNacimiento());
		cliente.setTelefono(clienteDto.getTelefono());
		Ciudad ciudad = null;
		try {
			ciudad = (Ciudad) ciudadDao.get(new Long(clienteDto
				.getDireccion().getCiudad().getId()));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		Direccion direccion = new Direccion();
		direccion.setCiudad(ciudad);
		direccion.setDomicilio(clienteDto.getDireccion().getDomicilio());
		cliente.setDireccion(direccion);

		return cliente;
	}

	private ClienteDto getClienteDtoFromCliente(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(cliente.getId());
		clienteDto.setApellido(cliente.getApellido());
		clienteDto.setNombre(cliente.getNombre());
		clienteDto.setCuit(cliente.getCuit().toString());
		clienteDto.setDocumento(cliente.getDocumento().toString());
		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setFechaNacimiento(cliente.getFechaNacimiento());
		clienteDto.setTelefono(cliente.getTelefono());

		CiudadDto ciudadDto = new CiudadDto();
		ciudadDto.setDescripcion(cliente.getDireccion().getCiudad()
				.getDescripcion());
		ciudadDto.setId(cliente.getDireccion().getCiudad().getId());
		ProvinciaDto provinciaDto = new ProvinciaDto();
		provinciaDto.setDescripcion(cliente.getDireccion().getCiudad()
				.getProvincia().getDescripcion());
		provinciaDto.setId(cliente.getDireccion().getCiudad().getProvincia()
				.getId());
		ciudadDto.setPrinvinciaDto(provinciaDto);
		String pais = cliente.getDireccion().getCiudad().getProvincia()
				.getPais().getDescripcion();

		DireccionDto direccionDto = new DireccionDto();
		direccionDto.setId(cliente.getDireccion().getId());
		direccionDto.setDomicilio(cliente.getDireccion().getDomicilio());
		direccionDto.setCiudad(ciudadDto);
		direccionDto.setPais(pais);

		return clienteDto;
	}

	public IClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(IClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public ICiudadDao getCiudadDao() {
		return ciudadDao;
	}

	public void setCiudadDao(ICiudadDao ciudadDao) {
		this.ciudadDao = ciudadDao;
	}


}
