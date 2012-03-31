package com.mitnick.presentation.controller;

import com.mitnick.presentation.view.ClientesPanel;
import com.mitnick.presentation.view.ClientesView;

public class ClientesController extends BaseController {

	
	
	private ClientesView clientesView;
	private ClientesPanel clientesPanel;
	
	
	public ClientesController() {
		
	}

	public ClientesView getClientesView() {
		return clientesView;
	}

	public void setClientesView(ClientesView clientesView) {
		this.clientesView = clientesView;
	}

	public ClientesPanel getClientesPanel() {
		return clientesPanel;
	}

	public void setClientesPanel(ClientesPanel clientesPanel) {
		this.clientesPanel = clientesPanel;
	}

	public void mostrarClientesPanel() {
		clientesPanel.setVisible(false);
	}
	
	public void mostrarClienteNuevoPanel() {

	}

	public void agregarCliente(String codigo) {

	}
}
