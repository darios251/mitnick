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

import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("productoNuevoPanel")
public class ProductoNuevoPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private ProductoController productoController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtStock;

	private JComboBox<TipoDto> cmbTipo;

	private JComboBox<MarcaDto> cmbMarca;
	
	/**
	 * @throws Exception 
	 * @wbp.parser.constructor
	 */
	public ProductoNuevoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}
	
	@Autowired(required=true)
	public ProductoNuevoPanel(@Qualifier("productoController") ProductoController productoController) {
		this.productoController = productoController;
	}

	@Override
	protected void limpiarCamposPantalla() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtStock.setText("");
		cmbMarca.setSelectedIndex(0);
		cmbTipo.setSelectedIndex(0);
	}
	
	@Override
	protected void initializeComponents() {
		setSize(new Dimension(815, 470));
		setLayout(null);
		
		JLabel lblCdigo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.codigo"));
		lblCdigo.setBounds(58, 25, 94, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(162, 25, 86, 20);
		add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblDescripcin = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.descripcion"));
		lblDescripcin.setBounds(58, 56, 94, 20);
		add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(162, 56, 326, 20);
		add(txtDescripcion);
		
		JLabel lblTipo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.tipo"));
		lblTipo.setBounds(58, 87, 94, 20);
		add(lblTipo);
		
		MitnickComboBoxModel<TipoDto> modeloTipo = new MitnickComboBoxModel<TipoDto>();
		modeloTipo.addItems(productoController.obtenerTipos());
		cmbTipo = new JComboBox<TipoDto>(modeloTipo);
		cmbTipo.setBounds(162, 87, 86, 20);
		add(cmbTipo);
		
		JLabel lblMarca = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.marca"));
		lblMarca.setBounds(319, 87, 73, 20);
		add(lblMarca);
		
		
		MitnickComboBoxModel<MarcaDto> modeloMarca = new MitnickComboBoxModel<MarcaDto>();
		modeloMarca.addItems(productoController.obtenerMarcas());
		cmbMarca = new JComboBox<MarcaDto>(modeloMarca);
		cmbMarca.setBounds(402, 87, 86, 20);
		add(cmbMarca);
		
		JLabel lblPrecio = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.precio"));
		lblPrecio.setBounds(58, 118, 94, 20);
		add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(162, 118, 86, 20);
		add(txtPrecio);
		
		JLabel lblStock = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stock"));
		lblStock.setBounds(319, 118, 73, 20);
		add(lblStock);
		
		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(402, 118, 86, 20);
		add(txtStock);
		
		btnAceptar = new JButton(PropertiesManager.getProperty("productoNuevoPanel.boton.aceptar"));
//		btnAceptar.setToolTipText(PropertiesManager.getProperty("productoNuevoPanel.tooltip.aceptar"));
		
		btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
		btnAceptar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAceptar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarProducto();
			}
		});
		btnAceptar.setBounds(548, 56, 60, 60);
		add(btnAceptar);
		
		btnCancelar = new JButton(PropertiesManager.getProperty("productoNuevoPanel.boton.cancelar"));
		
//		btnAceptar.setToolTipText(PropertiesManager.getProperty("productoNuevoPanel.tooltip.cancelar"));
		
		btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
		btnCancelar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnCancelar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnCancelar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCamposPantalla();
				productoController.mostrarProductosPanel();
			}
		});
		btnCancelar.setBounds(618, 56, 60, 60);
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
			productoController.guardarProducto(txtCodigo.getText(), txtDescripcion.getText(), (TipoDto)cmbTipo.getSelectedItem(), (MarcaDto)cmbMarca.getSelectedItem(), txtStock.getText(), txtPrecio.getText());
			limpiarCamposPantalla();
			productoController.mostrarProductosPanel();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void actualizarPantalla() {
		if(Validator.isNotNull(txtCodigo))
			txtCodigo.requestFocus();
	}
	
}
