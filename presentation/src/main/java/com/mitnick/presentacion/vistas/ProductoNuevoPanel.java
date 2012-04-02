package com.mitnick.presentacion.vistas;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;

@Panel("productoNuevoPanel")
public class ProductoNuevoPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProductoController productoController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtStock;

	private JComboBox cmbTipo;

	private JComboBox cmbMarca;

	public ProductoNuevoPanel() {
		setSize(new Dimension(815, 470));
		setLayout(null);
		
		JLabel lblCdigo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.codigo"));
		lblCdigo.setBounds(92, 25, 60, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(162, 25, 86, 20);
		add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblDescripcin = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.descripcion"));
		lblDescripcin.setBounds(92, 56, 60, 20);
		add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(162, 56, 326, 20);
		add(txtDescripcion);
		
		JLabel lblTipo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.tipo"));
		lblTipo.setBounds(92, 87, 60, 20);
		add(lblTipo);
		
		cmbTipo = new JComboBox();
		cmbTipo.setBounds(162, 87, 86, 20);
		add(cmbTipo);
		
		JLabel lblMarca = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.marca"));
		lblMarca.setBounds(352, 87, 40, 20);
		add(lblMarca);
		
		cmbMarca = new JComboBox();
		cmbMarca.setBounds(402, 87, 86, 20);
		add(cmbMarca);
		
		JLabel lblPrecio = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.precio"));
		lblPrecio.setBounds(92, 118, 60, 20);
		add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(162, 118, 86, 20);
		add(txtPrecio);
		
		JLabel lblStock = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stock"));
		lblStock.setBounds(352, 118, 40, 20);
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
				productoController.mostrarProductosPanel();
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
				productoController.mostrarProductosPanel();
			}
		});
		btnCancelar.setBounds(618, 56, 60, 60);
		add(btnCancelar);
	}

	@Override
	protected void limpiarCamposPantalla() {
		
	}
	@Override
	protected void initializeComponents() {
		
	}
	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
	
}
