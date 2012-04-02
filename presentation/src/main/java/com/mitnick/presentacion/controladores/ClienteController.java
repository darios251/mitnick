package com.mitnick.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.presentacion.vistas.ClienteView;
import com.mitnick.presentacion.vistas.paneles.ClientePanel;

@Controller("clienteController")
public class ClienteController extends BaseController {
	
	@Autowired
	private ClienteView clienteView;
	
	@Autowired
	private ClientePanel clientePanel;
	
	public ClienteController() {
		
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
		clientePanel.setVisible(false);
	}
	
	public void mostrarClienteNuevoPanel() {

	}

	public void agregarCliente(String codigo) {

	}
}
