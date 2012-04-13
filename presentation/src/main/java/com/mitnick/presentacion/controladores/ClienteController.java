package com.mitnick.presentacion.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ClienteView;
import com.mitnick.presentacion.vistas.paneles.ClientePanel;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;

@Controller("clienteController")
public class ClienteController extends BaseController {
	
	@Autowired
	protected ClienteView clienteView;
	
	@Autowired
	protected ClientePanel clientePanel;
	
	@Autowired
	private IClienteServicio clienteServicio;
	
	public ClienteController() {
		
	}
	
	public List<ClienteDto> obtenerClientesByFilter(ConsultaClienteDto filtroDto) {
		logger.debug("Entrando al método consultarClienteByFilter con :" + filtroDto);
		
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
		
		if(Validator.isEmptyOrNull(clientes))
			throw new PresentationException("error.cliente.consulta.clientes.null");
		
		logger.debug("Saliendo del método consultarClienteByFilter");
		
		return clientes;
	}
	
	public void agregarCliente(String codigo) {

	}

	public ClienteView getClienteView() {
		return clienteView;
	}

	public void setClienteView(ClienteView clienteView) {
		this.clienteView = clienteView;
	}

	public ClientePanel getClientePanel() {
		return clientePanel;
	}

	public void setClientePanel(ClientePanel clientePanel) {
		this.clientePanel = clientePanel;
	}

	public void mostrarClientePanel() {
		clientePanel.setVisible(true);
		clientePanel.actualizarPantalla();
	}
	
	public void mostrarClienteNuevoPanel() {

	}
	
	protected IClienteServicio getClienteServicio() {
		return clienteServicio;
	}

	public void setClienteServicio(IClienteServicio clienteServicio) {
		this.clienteServicio = clienteServicio;
	}
	
}
