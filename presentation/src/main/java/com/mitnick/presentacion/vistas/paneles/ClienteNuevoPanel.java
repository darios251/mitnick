package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.BaseController;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.vistas.formatters.EmailFormatter;
import com.mitnick.utils.AllowBlankMaskFormatter;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ProvinciaDto;
import com.mitnick.utils.dtos.TipoCompradorDto;

@Panel("clienteNuevoPanel")
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ClienteNuevoPanel extends BasePanel<ClienteController> implements KeyEventDispatcher {

	private static final long serialVersionUID = 1L;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private JLabel lblTipoComprador;
	private JComboBox<TipoCompradorDto> cmbTipoComprador;
	
	private JTextField txtActividad;
	private JLabel lblErrorTxtActividad;
	
	private JTextField txtNombre;
	private JLabel lblErrorTxtNombre;
	
	private JTextField txtDocumento;
	private JLabel lblErrorTxtDocumento;
	
	private JFormattedTextField txtCuit;
	private JLabel lblErrorTxtCuit;

	private ClienteDto cliente;
	private JTextField txtTelefono;
	private JLabel lblErrorTxtTelefono;
	
	private JTextField txtFechaNacimiento;
	private JLabel lblErrorTxtFechaNacimiento;
	
	private JFormattedTextField txtEmail;
	private JLabel lblErrorTxtEmail;
	
	private JTextField txtDomicilio;
	private JLabel lblErrorTxtDomicilio;
	
	private JTextField txtCodigoPostal;
	private JLabel lblErrorTxtCodigoPostal;

	private JComboBox<CiudadDto> cmbCiudad;
	private JLabel lblErrorCmbCiudad;
	
	private JComboBox<ProvinciaDto> cmbProvincia;
	private JLabel lblErrorCmbProvincia;

	private BasePanel<? extends BaseController> panelRetorno;

	private Component lblCiudad;

	private Component lblProvincia;

	private JLabel lblCodigoPostal;

	private JLabel lblDomicilio;

	private JLabel lblFechaNacimiento;

	private JLabel lblEmail;

	private JLabel lblTelefono;

	private JLabel lblCuit;

	private JLabel lblDocumento;

	private JLabel lblNombre;

	private JLabel lblActividad;
	
	/**
	 * @throws Exception
	 * @wbp.parser.constructor
	 */
	public ClienteNuevoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Autowired(required = true)
	public ClienteNuevoPanel(@Qualifier("clienteController") ClienteController clienteController) {
		controller = clienteController;		
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		cleanErrors();
		try {
			cmbCiudad.setSelectedIndex(0);
			cmbProvincia.setSelectedIndex(0);
			cmbTipoComprador.setSelectedIndex(0);
		} catch (Exception e) {
		}
		
		controller.cleanFields();
	}
	
	@Override
	protected void initializeComponents() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		setSize(new Dimension(815, 470));
		setLayout(null);

		add(getLblActividad());
		add(getLblNombre());
		add(getLblDocumento());
		add(getLblCuit());
		add(getLblTelefono());
		add(getLblEmail());
		add(getLblFechaNacimiento());
		add(getLblDomicilio());
		add(getLblCodigoPostal());
		add(getLblProvincia());
		add(getLblCiudad());
		
		add(getTxtActividad());
		add(getTxtNombre());
		add(getTxtDocumento());
		add(getTxtCuit());
		add(getTxtTelefono());
		add(getTxtEmail());
		add(getTxtFechaNacimiento());
		add(getTxtDomicilio());
		add(getTxtCodigoPostal());

		add(getCmbProvincia());
		add(getCmbCiudad());

		add(getLblTipoComprador());
		add(getCmbTipoComprador());
		
		add(getBtnAceptar());
		add(getBtnCancelar());
		
		add(getLblErrorTxtActividad());
		add(getLblErrorTxtNombre());
		add(getLblErrorTxtDocumento());
		add(getLblErrorTxtCuit());
		add(getLblErrorTxtTelefono());
		add(getLblErrorTxtEmail());
		add(getLblErrorTxtFechaNacimiento());
		add(getLblErrorTxtDomicilio());
		add(getLblErrorTxtCodigoPostal());
		add(getLblErrorCmbProvincia());
		add(getLblErrorCmbCiudad());
	}

	public Component getLblCiudad() {
		if(lblCiudad == null) {
			lblCiudad = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.ciudad"));
			lblCiudad.setBounds(392, 197, 94, 20);
			
		}
		return lblCiudad;
	}

	public Component getLblProvincia() {
		if(lblProvincia == null) {
			lblProvincia = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.provincia"));
			lblProvincia.setBounds(392, 157, 94, 20);
		}
		return lblProvincia;
	}

	public JLabel getLblCodigoPostal() {
		if(lblCodigoPostal == null){
			lblCodigoPostal = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.codigoPostal"));
			lblCodigoPostal.setBounds(392, 118, 94, 20);
		}
		return lblCodigoPostal;
	}

	public JLabel getLblDomicilio() {
		if(lblDomicilio == null) {
			lblDomicilio = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.domicilio"));
			lblDomicilio.setBounds(392, 75, 94, 20);
		}
		return lblDomicilio;
	}

	public JLabel getLblFechaNacimiento() {
		if(lblFechaNacimiento == null) {
			lblFechaNacimiento = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.fechaNacimiento"));
			lblFechaNacimiento.setBounds(57, 351, 94, 20);
		}
		return lblFechaNacimiento;
	}

	public JLabel getLblEmail() {
		if(lblEmail == null) {
			lblEmail = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.email"));
			lblEmail.setBounds(57, 306, 94, 20);
		}
		return lblEmail;
	}

	public JLabel getLblTelefono() {
		if(lblTelefono == null) {
			lblTelefono = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.telefono"));
			lblTelefono.setBounds(57, 259, 94, 20);
		}
		return lblTelefono;
	}

	public JLabel getLblCuit() {
		if(lblCuit == null) {
			lblCuit = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.cuit"));
			lblCuit.setBounds(57, 213, 94, 20);
		}
		return lblCuit;
	}

	public JLabel getLblDocumento() {
		if(lblDocumento == null) {
			lblDocumento = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.documento"));
			lblDocumento.setBounds(57, 167, 94, 20);
		}
		return lblDocumento;
	}

	public JLabel getLblNombre() {
		if(lblNombre == null) {
			lblNombre = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.nombre"));
			lblNombre.setBounds(57, 122, 94, 20);
		}
		return lblNombre;
	}

	public JLabel getLblActividad() {
		if(lblActividad == null) {
			lblActividad = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.actividad"));
			lblActividad.setBounds(57, 75, 94, 20);
			
		}
		return lblActividad;
	}

	public JLabel getLblTipoComprador() {
		if (lblTipoComprador == null) {
			lblTipoComprador = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.tipoComprador"));
			lblTipoComprador.setBounds(392, 237, 94, 20);
		}
		return lblTipoComprador;
	}

	public JComboBox<TipoCompradorDto> getCmbTipoComprador() {
		if (cmbTipoComprador == null) {
			cmbTipoComprador = new JComboBox<TipoCompradorDto>();
			cmbTipoComprador.setBounds(496, 237, 200, 23);
			
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.CONSUMIDOR_FINAL, MitnickConstants.TipoComprador.CONSUMIDOR_FINAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL, MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL, MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.EXENTO, MitnickConstants.TipoComprador.EXENTO_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.MONOTRIBUTISTA, MitnickConstants.TipoComprador.MONOTRIBUTISTA_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL, MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.NO_CATEGORIZADO, MitnickConstants.TipoComprador.NO_CATEGORIZADO_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.NO_RESPONSABLE, MitnickConstants.TipoComprador.NO_RESPONSABLE_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO, MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO_DESC));
						
		}
		return cmbTipoComprador;
	}
	
	public JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("clienteNuevoPanel.boton.aceptar"));
			// btnAceptar.setToolTipText(PropertiesManager.getProperty("clienteNuevoPanel.tooltip.aceptar"));

			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));

			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agregarCliente();
				}
			});
			btnAceptar.setBounds(465, 293, 60, 60);
			btnAceptar.setFocusable(false);

		}
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton(PropertiesManager.getProperty("clienteNuevoPanel.boton.cancelar"));

			// btnAceptar.setToolTipText(PropertiesManager.getProperty("clienteNuevoPanel.tooltip.cancelar"));

			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCancelar.setMargin(new Insets(-1, -1, -1, -1));

			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpiarCamposPantalla();
					retornarLLamador();
				}
			});
			btnCancelar.setBounds(541, 293, 60, 60);
			btnCancelar.setFocusable(false);
		}		
		return btnCancelar;
	}

	public JTextField getTxtActividad() {
		if (txtActividad == null) {
			txtActividad = new JTextField();
			txtActividad.setBounds(161, 75, 105, 20);
			txtActividad.setColumns(10);
		}
		return txtActividad;
	}
	
	public JLabel getLblErrorTxtActividad() {
		if (lblErrorTxtActividad == null) {
			lblErrorTxtActividad = new JLabel("");
			lblErrorTxtActividad.setBounds(57, 97, 300, 14);
		}
		return lblErrorTxtActividad;
	}
	
	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(161, 122, 220, 20);
		}
		return txtNombre;
	}
	
	public JLabel getLblErrorTxtNombre() {
		if (lblErrorTxtNombre == null) {
			lblErrorTxtNombre = new JLabel("");
			lblErrorTxtNombre.setBounds(57, 142, 300, 14);
		}
		return lblErrorTxtNombre;
	}

	public JTextField getTxtDocumento() {
		if (txtDocumento == null) {
			txtDocumento = new JTextField();
			txtDocumento.setColumns(10);
			txtDocumento.setBounds(161, 167, 105, 20);
		}
		return txtDocumento;
	}
	
	public JLabel getLblErrorTxtDocumento() {
		if (lblErrorTxtDocumento == null) {
			lblErrorTxtDocumento = new JLabel("");
			lblErrorTxtDocumento.setBounds(57, 187, 300, 14);
		}
		return lblErrorTxtDocumento;
	}

	public JTextField getTxtCuit() {
		if (txtCuit == null) {
			try {
				txtCuit = new JFormattedTextField(new AllowBlankMaskFormatter(MitnickConstants.CUIT_MASKFORMAT));
				txtCuit.setColumns(10);
				txtCuit.setBounds(161, 213, 105, 20);
			} catch (ParseException e) {
			}
		}
		return txtCuit;
	}
	
	public JLabel getLblErrorTxtCuit() {
		if (lblErrorTxtCuit == null) {
			lblErrorTxtCuit = new JLabel("");
			lblErrorTxtCuit.setBounds(57, 233, 300, 14);
		}
		return lblErrorTxtCuit;
	}

	public JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(161, 259, 105, 20);
		}
		return txtTelefono;
	}
	
	public JLabel getLblErrorTxtTelefono() {
		if (lblErrorTxtTelefono == null) {
			lblErrorTxtTelefono = new JLabel("");
			lblErrorTxtTelefono.setBounds(57, 279, 300, 14);
		}
		return lblErrorTxtTelefono;
	}

	public JTextField getTxtFechaNacimiento() {
		if (txtFechaNacimiento == null) {
			try {
				txtFechaNacimiento = new JFormattedTextField(new AllowBlankMaskFormatter(MitnickConstants.DATE_MASKFORMAT)); 						
				txtFechaNacimiento.setColumns(10);
				txtFechaNacimiento.setBounds(161, 351, 105, 20);
			} catch (ParseException e) {}
		}
		return txtFechaNacimiento;
	}
	
	public JLabel getLblErrorTxtFechaNacimiento() {
		if (lblErrorTxtFechaNacimiento == null) {
			lblErrorTxtFechaNacimiento = new JLabel("");
			lblErrorTxtFechaNacimiento.setBounds(57, 371, 300, 14);
		}
		return lblErrorTxtFechaNacimiento;
	}

	public JTextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new JFormattedTextField();
			EmailFormatter emailFormatter = new EmailFormatter(txtEmail);
			DefaultFormatterFactory dff = new DefaultFormatterFactory(emailFormatter);
			txtEmail.setFormatterFactory(dff);
			txtEmail.setColumns(10);
			txtEmail.setBounds(161, 306, 175, 20);		
		}
		return txtEmail;
	}
	
	public JLabel getLblErrorTxtEmail() {
		if (lblErrorTxtEmail == null) {
			lblErrorTxtEmail = new JLabel("");
			lblErrorTxtEmail.setBounds(57, 326, 300, 14);
		}
		return lblErrorTxtEmail;
	}

	public JTextField getTxtDomicilio() {
		if (txtDomicilio == null) {
			txtDomicilio = new JTextField();
			txtDomicilio.setColumns(10);
			txtDomicilio.setBounds(496, 75, 105, 20);
		}
		return txtDomicilio;
	}
	
	public JLabel getLblErrorTxtDomicilio() {
		if (lblErrorTxtDomicilio == null) {
			lblErrorTxtDomicilio = new JLabel("");
			lblErrorTxtDomicilio.setBounds(392, 95, 300, 14);
		}
		return lblErrorTxtDomicilio;
	}

	public JTextField getTxtCodigoPostal() {
		if (txtCodigoPostal == null) {
			txtCodigoPostal = new JTextField();
			txtCodigoPostal.setColumns(10);
			txtCodigoPostal.setBounds(496, 118, 105, 20);
		}
		return txtCodigoPostal;
	}
	
	public JLabel getLblErrorTxtCodigoPostal() {
		if (lblErrorTxtCodigoPostal == null) {
			lblErrorTxtCodigoPostal = new JLabel("");
			lblErrorTxtCodigoPostal.setBounds(392, 138, 300, 14);
		}
		return lblErrorTxtCodigoPostal;
	}

	public JComboBox<CiudadDto> getCmbCiudad() {
		if (cmbCiudad == null) {
			cmbCiudad = new JComboBox<CiudadDto>();
			cmbCiudad.setBounds(496, 197, 105, 20);
			MitnickComboBoxModel<CiudadDto> ciudadModel = new MitnickComboBoxModel<CiudadDto>();
			cmbCiudad.setModel(ciudadModel);
		}
		return cmbCiudad;
	}
	
	public JLabel getLblErrorCmbCiudad() {
		if (lblErrorCmbCiudad == null) {
			lblErrorCmbCiudad = new JLabel("");
			lblErrorCmbCiudad.setBounds(392, 217, 300, 14);
		}
		return lblErrorCmbCiudad;
	}

	public JComboBox<ProvinciaDto> getCmbProvincia() {
		if (cmbProvincia == null) {
			cmbProvincia = new JComboBox<ProvinciaDto>();
			cmbProvincia.setBounds(496, 157, 105, 20);
			MitnickComboBoxModel<ProvinciaDto> provinciaModel = new MitnickComboBoxModel<ProvinciaDto>();
			provinciaModel.addItems(controller.obtenerProvincias());
			cmbProvincia.setModel(provinciaModel);
			cmbProvincia.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cmbCiudad.removeAllItems();
					try {
						List<CiudadDto> ciudades = controller.obtenerCiudadesPorProvincia((ProvinciaDto) cmbProvincia.getSelectedItem());
						if (Validator.isNotNull(ciudades))
							((MitnickComboBoxModel<CiudadDto>) cmbCiudad.getModel()).addItems(ciudades);
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return cmbProvincia;
	}
	
	public JLabel getLblErrorCmbProvincia() {
		if (lblErrorCmbProvincia == null) {
			lblErrorCmbProvincia = new JLabel("");
			lblErrorCmbProvincia.setBounds(392, 177, 300, 14);
		}
		return lblErrorCmbProvincia;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtActividad, txtNombre, txtDocumento,
						txtCuit, txtTelefono, txtEmail, txtFechaNacimiento,	txtDomicilio, txtCodigoPostal, cmbProvincia, cmbCiudad,
						btnAceptar, btnCancelar}));
	}

	protected void agregarCliente() {
		try {
			String tipoComprador = null;
			if (Validator.isNotNull(cmbTipoComprador) && Validator.isNotNull(cmbTipoComprador.getSelectedItem()))
				tipoComprador = ((TipoCompradorDto)cmbTipoComprador.getSelectedItem()).getTipoComprador();
			cliente = controller.guardarCliente(cliente, txtActividad.getText(), txtNombre.getText(), txtDocumento.getText(),
					txtCuit.getText(), txtTelefono.getText(), txtEmail.getText(), txtFechaNacimiento.getText(),
					txtDomicilio.getText(), txtCodigoPostal.getText(), (CiudadDto) cmbCiudad.getSelectedItem(), tipoComprador);
			limpiarCamposPantalla();
			retornarLLamador();
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	private void retornarLLamador() {
		if (panelRetorno == null)
			controller.mostrarClientePanel();
		else {
			this.setVisible(false);
			if (panelRetorno instanceof VentaClientePanel){
				((VentaClientePanel)panelRetorno).setTipoComprador(cliente);
			}
			panelRetorno.setVisible(true);
		}
	}

	public void setCliente(ClienteDto clienteDto) {
		this.cliente = clienteDto;
	}

	public void setEditable(boolean editable){
		txtActividad.setEnabled(editable);
		txtNombre.setEnabled(editable);
		txtDocumento.setEnabled(editable);
		txtCuit.setEnabled(editable);
		txtTelefono.setEnabled(editable);
		txtEmail.setEnabled(editable);
		txtFechaNacimiento.setEnabled(editable);
		txtDomicilio.setEnabled(editable);
		txtCodigoPostal.setEnabled(editable);
		cmbProvincia.setEnabled(editable);
		cmbCiudad.setEnabled(editable);
		cmbTipoComprador.setEnabled(editable);
		btnAceptar.setEnabled(editable);
	}
	
	@Override
	public void actualizarPantalla() {
		if (Validator.isNotNull(txtActividad)) {
			txtActividad.requestFocus();
		}
		if (Validator.isNotNull(cliente)) {
			if (Validator.isNotNull(txtActividad) && Validator.isNotBlankOrNull(cliente.getActividad()))
				txtActividad.setText(cliente.getActividad());
			if (Validator.isNotNull(txtNombre) && Validator.isNotBlankOrNull(cliente.getNombre()))
				txtNombre.setText(cliente.getNombre().trim());
			if (Validator.isNotNull(txtDocumento) && Validator.isNotBlankOrNull(cliente.getDocumento()))
				txtDocumento.setText(cliente.getDocumento());
			if (Validator.isNotNull(txtCuit) && Validator.isNotBlankOrNull(cliente.getCuit()))
				txtCuit.setText(cliente.getCuit());
			if(Validator.isNotNull(txtTelefono) && Validator.isNotNull(cliente.getTelefono()))
				txtTelefono.setText(cliente.getTelefono());
			if (Validator.isNotNull(txtEmail) && Validator.isNotBlankOrNull(cliente.getEmail()))
				txtEmail.setText(cliente.getEmail());
			if (Validator.isNotNull(txtFechaNacimiento)	&& Validator.isNotNull(cliente.getFechaNacimiento()))
				txtFechaNacimiento.setText(cliente.getFechaNacimiento());
			if (Validator.isNotNull(txtDomicilio) && Validator.isNotNull(cliente.getDireccion()) && Validator.isNotBlankOrNull(cliente.getDireccion().getDomicilio()))
				txtDomicilio.setText(cliente.getDireccion().getDomicilio().trim());
			if (Validator.isNotNull(txtCodigoPostal) && Validator.isNotNull(cliente.getDireccion())	&& Validator.isNotBlankOrNull(cliente.getDireccion().getCodigoPostal()))
				txtCodigoPostal.setText(cliente.getDireccion().getCodigoPostal());
			if (Validator.isNotNull(cmbProvincia) && Validator.isNotNull(cliente.getDireccion()) && Validator.isNotNull(cliente.getDireccion().getCiudad()))
				cmbProvincia.getModel().setSelectedItem(cliente.getDireccion().getCiudad().getPrinvincia());
			else
				cmbProvincia.setSelectedItem(null);
			if (Validator.isNotNull(cmbCiudad) && Validator.isNotNull(cliente.getDireccion()))
				cmbCiudad.getModel().setSelectedItem(cliente.getDireccion().getCiudad());
			else
				cmbCiudad.setSelectedItem(null);
			if (Validator.isNotNull(cmbTipoComprador) && Validator.isNotNull(cliente.getTipoComprador())){				
				cmbTipoComprador.getModel().setSelectedItem(TipoCompradorDto.getTipoCompradorDto(cliente.getTipoComprador()));
			}				
			else
				cmbTipoComprador.setSelectedItem(null);
		} else {
			cmbTipoComprador.setSelectedItem(null);
			cmbProvincia.setSelectedItem(null);
			cmbCiudad.setSelectedItem(null);
		}
		this.setEditable(true);			
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtActividad;
	}
	
	@Override
	protected void keyDownArrow() {
		if (txtActividad.hasFocus()) 
			txtNombre.requestFocus();
		else if (txtNombre.hasFocus())
	        txtDocumento.requestFocus();
		else if (txtDocumento.hasFocus())
		    txtCuit.requestFocus();
		else if (txtCuit.hasFocus())
		    txtTelefono.requestFocus();
		else if (txtTelefono.hasFocus())
	        txtEmail.requestFocus();
		else if (txtEmail.hasFocus())
	        txtFechaNacimiento.requestFocus();
		else if (txtFechaNacimiento.hasFocus())
	        txtDomicilio.requestFocus();
		else if (txtDomicilio.hasFocus())
	        txtCodigoPostal.requestFocus();
		else if (txtCodigoPostal.hasFocus())
	        cmbProvincia.requestFocus();
	}
	
	@Override
	protected void keyUpArrow() {
		if (txtNombre.hasFocus())
			txtActividad.requestFocus();
		else if (txtDocumento.hasFocus())
			txtNombre.requestFocus();
		else if (txtCuit.hasFocus())
			txtDocumento.requestFocus();
		else if (txtTelefono.hasFocus())
			txtCuit.requestFocus();
		else if (txtEmail.hasFocus())
			txtTelefono.requestFocus();
		else if (txtFechaNacimiento.hasFocus())
			txtEmail.requestFocus();
		else if (txtDomicilio.hasFocus())
			txtFechaNacimiento.requestFocus();
		else if (txtCodigoPostal.hasFocus())
			txtDomicilio.requestFocus();		
	}
	
	@Override
	protected void keyAdd() {
		btnAceptar.doClick();
	}
	
	@Override
	protected void keyPageDown() {
		btnAceptar.doClick();
	}
	
	@Override
	protected void keyEscape() {
		btnCancelar.doClick();
	}

	public BasePanel<? extends BaseController> getPanelRetorno() {
		return panelRetorno;
	}

	public void setPanelRetorno(BasePanel<? extends BaseController> panelRetorno) {
		this.panelRetorno = panelRetorno;
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnAceptar());
	}
}
