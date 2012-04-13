package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("clienteNuevoPanel")
public class ClienteNuevoPanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	
	private ClienteController clienteController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtStock;

	private JComboBox<TipoDto> cmbTipo;

	private JComboBox<MarcaDto> cmbMarca;
	
	private ProductoDto producto;
	
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
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtStock.setText("");
		cmbMarca.setSelectedIndex(0);
		cmbTipo.setSelectedIndex(0);
		producto = null;
	}
	
	@Override
	protected void initializeComponents() {
		setSize(new Dimension(815, 470));
		setLayout(null);
		
		JLabel lblCdigo = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.codigo"));
		lblCdigo.setBounds(58, 25, 94, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(162, 25, 105, 20);
		add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblDescripcin = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.descripcion"));
		lblDescripcin.setBounds(319, 25, 94, 20);
		add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(448, 25, 105, 20);
		add(txtDescripcion);
		
		JLabel lblPrecio = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.precio"));
		lblPrecio.setBounds(58, 72, 94, 20);
		add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(162, 72, 105, 20);
		add(txtPrecio);
		
		JLabel lblStock = new JLabel(PropertiesManager.getProperty("clienteNuevoPanel.etiqueta.stock"));
		lblStock.setBounds(319, 72, 73, 20);
		add(lblStock);
		
		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(448, 72, 105, 20);
		add(txtStock);
		
		btnAceptar = new JButton(PropertiesManager.getProperty("clienteNuevoPanel.boton.aceptar"));
//		btnAceptar.setToolTipText(PropertiesManager.getProperty("clienteNuevoPanel.tooltip.aceptar"));
		
		btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
		btnAceptar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAceptar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarProducto();
			}
		});
		btnAceptar.setBounds(277, 272, 60, 60);
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
		btnCancelar.setBounds(353, 272, 60, 60);
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
							agregarProducto();
						}
					}
				});
			}
		}
	}

	protected void agregarProducto() {
		try {
			//clienteController.(producto, txtCodigo.getText(), txtDescripcion.getText(), (TipoDto)cmbTipo.getSelectedItem(), (MarcaDto)cmbMarca.getSelectedItem(), txtStock.getText(), txtPrecio.getText());
			limpiarCamposPantalla();
			clienteController.mostrarClientePanel();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	public void setProducto(ProductoDto productoDto) {
		this.producto = productoDto;
	}

	@Override
	public void actualizarPantalla() {
		if(Validator.isNotNull(txtCodigo)) {
			txtCodigo.requestFocus();
		}
		
		if(Validator.isNotNull(producto)) {
			if(Validator.isNotNull(txtCodigo))
				txtCodigo.setText(producto.getCodigo());
			if(Validator.isNotNull(txtDescripcion))
				txtDescripcion.setText(producto.getDescripcion());
			if(Validator.isNotNull(txtPrecio))
				txtPrecio.setText(producto.getPrecio().toString());
			if(Validator.isNotNull(txtStock))
				txtStock.setText(producto.getStock() + "");
			if(Validator.isNotNull(cmbMarca) && cmbMarca.getModel().getSize() > 0)
				cmbMarca.setSelectedItem(producto.getMarca());
			if(Validator.isNotNull(cmbTipo) && cmbTipo.getModel().getSize() > 0)
				cmbMarca.setSelectedItem(producto.getTipo());
		}
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtCodigo;
	}
	
}
