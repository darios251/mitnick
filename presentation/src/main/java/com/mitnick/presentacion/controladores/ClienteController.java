package com.mitnick.presentacion.controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ClienteView;
import com.mitnick.presentacion.vistas.paneles.ClienteNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ClientePanel;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.DireccionDto;
import com.mitnick.utils.dtos.ProvinciaDto;

@Controller("clienteController")
public class ClienteController extends BaseController {
	
	@Autowired protected ClienteView clienteView;
	
	@Autowired protected ClientePanel clientePanel;
	
	@Autowired private ClienteNuevoPanel clienteNuevoPanel;
	
	@Autowired private IClienteServicio clienteServicio;
	
	public ClienteController() {
		
	}
	
	public List<ClienteDto> obtenerClientesByFilter(ConsultaClienteDto filtroDto) {
		logger.debug("Entrando al método obtenerClientesByFilter con :" + filtroDto);
		
		if(Validator.isNull(filtroDto))
			throw new PresentationException("error.unknown", "El filtro para la consulta de clientes no puede ser nulo");
		if(!Validator.isBlankOrNull(filtroDto.getDocumento()) && !Validator.isDocumentNumber(filtroDto.getDocumento()))
			throw new PresentationException("error.cliente.consulta.documento.format");
		
		List<ClienteDto> clientes = null;
		try {
			clientes = clienteServicio.consultarCliente(filtroDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		if(Validator.isEmptyOrNull(clientes))
			throw new PresentationException("error.cliente.consulta.clientes.null");
		
		logger.debug("Saliendo del método consultarClienteByFilter");
		
		return clientes;
	}
	
	public List<ProvinciaDto> obtenerProvincias() {
		List<ProvinciaDto> provincias = null;
		
		try {
			provincias = clienteServicio.obtenerProvincias();
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		if(Validator.isEmptyOrNull(provincias))
			throw new PresentationException("error.cliente.nuevo.provincias.null");
		
		return provincias;
	}
	
	public List<CiudadDto> obtenerCiudadesPorProvincia(ProvinciaDto provincia) {
		List<CiudadDto> ciudades = null;
		
		try {
			ciudades = clienteServicio.obtenerCiudades(provincia);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		if(Validator.isNull(ciudades))
			throw new PresentationException("error.cliente.nuevo.ciudades.null");
		
		return ciudades;
	}

	public void mostrarClientePanel() {
		clienteNuevoPanel.setVisible(false);
		clientePanel.setVisible(true);
	}
	
	public void mostrarClienteNuevoPanel() {
		clientePanel.setVisible(false);
		clienteNuevoPanel.setVisible(true);
		clienteNuevoPanel.actualizarPantalla();
	}
	
	public void guardarCliente(ClienteDto cliente, String apellido, String nombre, String documento,
			String cuit, String telefono, String email, String fechaNacimiento, String domicilio, String codigoPostal, CiudadDto ciudad) {
		if(Validator.isNull(cliente))
			cliente = new ClienteDto();
		
		cliente.setApellido(apellido);
		cliente.setNombre(nombre);
		cliente.setDocumento(documento);
		cliente.setCuit(cuit);
		cliente.setEmail(email);
		try {
			if(Validator.isNotBlankOrNull(fechaNacimiento))
				cliente.setFechaNacimiento(new SimpleDateFormat(MitnickConstants.DATE_FORMAT).parse(fechaNacimiento));
		} catch (ParseException e) {
			throw new PresentationException("error.cliente.nuevo.fechaNacimiento.format");
		}
		cliente.setTelefono(telefono);
		if(cliente.getDireccion() == null)
			cliente.setDireccion(new DireccionDto());
		cliente.getDireccion().setDomicilio(domicilio);
		cliente.getDireccion().setCodigoPostal(codigoPostal);
		cliente.getDireccion().setCiudad(ciudad);
		
		try {
			clienteServicio.guardarCliente(cliente);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}
	
	public void nuevoCliente() {
		clienteNuevoPanel.setCliente(null);
		mostrarClienteNuevoPanel();
	}

	public void editarCliente() {
		ClienteDto clienteDto = null;
		try {
			int index = getClientePanel().getTable().getSelectedRow();
			clienteDto = getClientePanel().getModel().getCliente(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.clientePanel.clientes.editar.vacio");
			}
			else {
				throw new PresentationException("error.clientePanel.cliente.editar.noSeleccionado");
			}
		}
		
		try {
			clienteNuevoPanel.setCliente(clienteDto);
			mostrarClienteNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el cliente");
		}
	}

	public void eliminarCliente() {
		ClienteDto clienteDto = null;
		try {
			int index = getClientePanel().getTable().getSelectedRow();
			clienteDto = getClientePanel().getModel().getCliente(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.clientePanel.clientes.vacio");
			}
			else {
				throw new PresentationException("error.clientePanel.cliente.noSeleccionado");
			}
		}
		
		try {
			getClienteServicio().eliminarCliente(clienteDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el cliente");
		}
	}

	public void cargarReporte() {
		clienteServicio.cargarReporte();		
	}
	
	protected IClienteServicio getClienteServicio() {
		return clienteServicio;
	}
	
	public ClienteView getClienteView() {
		return clienteView;
	}

	public ClientePanel getClientePanel() {
		return clientePanel;
	}

	public ClienteNuevoPanel getClienteNuevoPanel() {
		return clienteNuevoPanel;
	}
}
