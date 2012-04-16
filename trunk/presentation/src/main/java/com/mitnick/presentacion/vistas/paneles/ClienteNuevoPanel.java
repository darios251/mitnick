package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ProvinciaDto;

import javax.swing.JComboBox;

@Panel("clienteNuevoPanel")
public class ClienteNuevoPanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	
	private ClienteController clienteController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtApellido;
	private JTextField txtNombre;
	private JTextField txtDocumento;
	private JTextField txtCuit;

	private ClienteDto cliente;
	private JTextField txtTelefono;
	private JTextField txtFechaNacimiento;
	private JTextField txtEmail;
	private JTextField txtDomicilio;
	private JTextField txtCodigoPostal;
	
	private JComboBox<CiudadDto> cmbCiudad;
	private JComboBox<ProvinciaDto> cmbProvincia;
	/**
	 * @throws Exception 
	 * @wbp.parser.constructor
	 */
	public ClienteNuevoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}
	
	@Autowired(required=true)
	public ClienteNuevoPanel(@Qualifier("clienteController") ClienteController clienteController) {
		this.clienteController = clienteController;
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if(component instanceof JTextField)
				((JTextField) component).setText("");
		}
		try {
			cmbCiudad.setSelectedIndex(0);
			cmbProvincia.setSelectedIndex(0);
		}
		catch(Exception e){}
		cliente = null;
	}
	
	@Override
	protected void initializeComponents() {
		setSize(new Dimension(815, 470));
		setLayout(null);
		
		JLabel lblApellido = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.apellido"));
		lblApellido.setBounds(57, 75, 94, 20);
		add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(161, 75, 105, 20);
		txtApellido.setColumns(10);
		add(txtApellido);
		
		JLabel lblNombre = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.nombre"));
		lblNombre.setBounds(57, 122, 94, 20);
		add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(161, 122, 105, 20);
		add(txtNombre);
		
		JLabel lblDocumento = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.documento"));
		lblDocumento.setBounds(57, 167, 94, 20);
		add(lblDocumento);
		
		txtDocumento = new JTextField();
		txtDocumento.setColumns(10);
		txtDocumento.setBounds(161, 167, 105, 20);
		add(txtDocumento);
		
		JLabel lblCuit = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.cuit"));
		lblCuit.setBounds(57, 213, 94, 20);
		add(lblCuit);
		
		txtCuit = new JTextField();
		txtCuit.setColumns(10);
		txtCuit.setBounds(161, 213, 105, 20);
		add(txtCuit);
		
		JLabel telefono = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.telefono"));
		telefono.setBounds(57, 259, 94, 20);
		add(telefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(161, 259, 105, 20);
		add(txtTelefono);
		
		JLabel lblEmail = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.email"));
		lblEmail.setBounds(57, 306, 94, 20);
		add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(161, 306, 105, 20);
		add(txtEmail);
		
		JLabel lblFechaNacimiento = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.fechaNacimiento"));
		lblFechaNacimiento.setBounds(57, 351, 94, 20);
		add(lblFechaNacimiento);
		
		txtFechaNacimiento = new JTextField();
		txtFechaNacimiento.setColumns(10);
		txtFechaNacimiento.setBounds(161, 351, 105, 20);
		add(txtFechaNacimiento);
		
		JLabel lblDomicilio = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.domicilio"));
		lblDomicilio.setBounds(392, 75, 94, 20);
		add(lblDomicilio);
		
		txtDomicilio = new JTextField();
		txtDomicilio.setColumns(10);
		txtDomicilio.setBounds(496, 75, 105, 20);
		add(txtDomicilio);
		
		JLabel lblCodigoPostal = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.codigoPostal"));
		lblCodigoPostal.setBounds(392, 118, 94, 20);
		add(lblCodigoPostal);
		
		txtCodigoPostal = new JTextField();
		txtCodigoPostal.setColumns(10);
		txtCodigoPostal.setBounds(496, 118, 105, 20);
		add(txtCodigoPostal);
		
		JLabel lblProvincia = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.provincia"));
		lblProvincia.setBounds(392, 208, 94, 20);
		add(lblProvincia);
		
		cmbProvincia = new JComboBox<ProvinciaDto>();
		cmbProvincia.setBounds(496, 208, 105, 20);
		MitnickComboBoxModel<ProvinciaDto> provinciaModel = new MitnickComboBoxModel<ProvinciaDto>();
		provinciaModel.addItems(clienteController.obtenerProvincias());
		cmbProvincia.setModel(provinciaModel);
		cmbProvincia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbCiudad.removeAllItems();
				try {
					((MitnickComboBoxModel<CiudadDto>)cmbCiudad.getModel()).addItems(clienteController.obtenerCiudadesPorProvincia((ProvinciaDto) cmbProvincia.getSelectedItem()));
				}
				catch(PresentationException ex) {
					mostrarMensaje(ex);
				}
			}
		});
		add(cmbProvincia);
		
		JLabel lblCiudad = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.ciudad"));
		lblCiudad.setBounds(392, 160, 94, 20);
		add(lblCiudad);
		
		cmbCiudad = new JComboBox<CiudadDto>();
		cmbCiudad.setBounds(496, 160, 105, 20);
		MitnickComboBoxModel<CiudadDto> ciudadModel = new MitnickComboBoxModel<CiudadDto>();
		ciudadModel.addItems(clienteController.obtenerCiudadesPorProvincia((ProvinciaDto) cmbProvincia.getSelectedItem()));
		cmbCiudad.setModel(ciudadModel);
		add(cmbCiudad);
		
		btnAceptar = new JButton(PropertiesManager.getProperty("clienteNuevoPanel.boton.aceptar"));
//		btnAceptar.setToolTipText(PropertiesManager.getProperty("clienteNuevoPanel.tooltip.aceptar"));
		
		btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
		btnAceptar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAceptar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarCliente();
			}
		});
		btnAceptar.setBounds(465, 293, 60, 60);
		add(btnAceptar);
		
		btnCancelar = new JButton(PropertiesManager.getProperty("clienteNuevoPanel.boton.cancelar"));
		
//		btnAceptar.setToolTipText(PropertiesManager.getProperty("clienteNuevoPanel.tooltip.cancelar"));
		
		btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
		btnCancelar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnCancelar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnCancelar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCamposPantalla();
				clienteController.mostrarClientePanel();
			}
		});
		btnCancelar.setBounds(541, 293, 60, 60);
		add(btnCancelar);
				
		addKeyListeners();
	}
	
	private void addKeyListeners() {
		for(Component component : this.getComponents()) {
			if(component instanceof JTextField){
				component.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							agregarCliente();
						}
					}
				});
			}
		}
	}

	protected void agregarCliente() {
		try {
			clienteController.guardarCliente(cliente, txtApellido.getText(), txtNombre.getText(), txtDocumento.getText(), txtCuit.getText(),
						txtTelefono.getText(), txtEmail.getText(), txtFechaNacimiento.getText(),
						txtDomicilio.getText(), txtCodigoPostal.getText(), (CiudadDto)cmbCiudad.getSelectedItem());
			limpiarCamposPantalla();
			clienteController.mostrarClientePanel();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	public void setCliente(ClienteDto clienteDto) {
		this.cliente = clienteDto;
	}

	@Override
	public void actualizarPantalla() {
		if(Validator.isNotNull(txtApellido)) {
			txtApellido.requestFocus();
		}
		
		if(Validator.isNotNull(cliente)) {
			if(Validator.isNotNull(txtApellido) && Validator.isNotBlankOrNull(cliente.getApellido()))
				txtApellido.setText(cliente.getApellido());
			if(Validator.isNotNull(txtNombre) && Validator.isNotBlankOrNull(cliente.getNombre()))
				txtNombre.setText(cliente.getNombre());
			if(Validator.isNotNull(txtDocumento) && Validator.isNotBlankOrNull(cliente.getDocumento()))
				txtDocumento.setText(cliente.getDocumento());
			if(Validator.isNotNull(txtCuit) && Validator.isNotBlankOrNull(cliente.getCuit()))
				txtCuit.setText(cliente.getCuit());
			if(Validator.isNotNull(txtEmail) && Validator.isNotBlankOrNull(cliente.getEmail()))
				txtEmail.setText(cliente.getEmail());
			if(Validator.isNotNull(txtFechaNacimiento) && Validator.isNotNull(cliente.getFechaNacimiento()))
				txtFechaNacimiento.setText(new SimpleDateFormat(MitnickConstants.DATE_FORMAT).format(cliente.getFechaNacimiento()));
			if(Validator.isNotNull(txtDomicilio) && Validator.isNotNull(cliente.getDireccion()) && Validator.isNotBlankOrNull(cliente.getDireccion().getDomicilio()))
				txtDomicilio.setText(cliente.getDireccion().getDomicilio());
			if(Validator.isNotNull(txtCodigoPostal) && Validator.isNotNull(cliente.getDireccion()) && Validator.isNotBlankOrNull(cliente.getDireccion().getCodigoPostal()))
				txtCodigoPostal.setText(cliente.getDireccion().getCodigoPostal());
			if(Validator.isNotNull(cmbProvincia) && Validator.isNotNull(cliente.getDireccion()))
				cmbProvincia.setSelectedItem(cliente.getDireccion().getCiudad().getPrinvinciaDto());
			if(Validator.isNotNull(cmbCiudad) && Validator.isNotNull(cliente.getDireccion()))
				cmbProvincia.setSelectedItem(cliente.getDireccion().getCiudad());
		}
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtApellido;
	}
}
