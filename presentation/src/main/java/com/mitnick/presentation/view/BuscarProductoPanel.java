package com.mitnick.presentation.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mitnick.presentation.controller.VentasController;
import com.mitnick.utils.PropertiesManager;

public class BuscarProductoPanel extends BaseView {
	private static final long serialVersionUID = 1L;
	
	private VentasController ventasController;
	
	private JScrollPane scrollPane;
	private Component lblCodigo;
	private JLabel lblDescripcion;
	private JButton btnBuscar;
	private JButton btnVolver;
	private JButton btnAceptar;
	
	private JTable table;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JLabel lblProductos;
	
	public BuscarProductoPanel(String titulo) {
		this.title = titulo;
		
		setLayout(null);
		setSize(new Dimension(815, 470));
		
		
		table = new JTable();
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		lblCodigo = new JLabel(PropertiesManager.getProperty("buscarArticuloPanel.etiqueta.codigo"));
		lblCodigo.setBounds(125, 35, 70, 20);
		add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(200, 35, 110, 20);
		add(txtCodigo);
		
		lblDescripcion = new JLabel(PropertiesManager.getProperty("buscarArticuloPanel.etiqueta.descripcion"));
		lblDescripcion.setBounds(330, 35, 70, 20);
		add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(420, 35, 110, 20);
		add(txtDescripcion);
		
		btnBuscar = new JButton(PropertiesManager.getProperty("buscarArticuloPanel.boton.buscar"));
		btnBuscar.setToolTipText(PropertiesManager.getProperty("buscarArticuloPanel.tooltip.buscar"));

		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));
		btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscar.setBounds(570, 15, 60, 60);
		add(btnBuscar);
		
		btnVolver = new JButton(PropertiesManager.getProperty("buscarArticuloPanel.boton.volver"));
		btnVolver.setToolTipText(PropertiesManager.getProperty("buscarArticuloPanel.tooltip.volver"));
		
		btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
		btnVolver.setHorizontalTextPosition( SwingConstants.CENTER );
		btnVolver.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnVolver.setMargin(new Insets(-1, -1, -1, -1));
		
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventasController.mostrarVentasPanel();
			}
		});
		btnVolver.setBounds(735, 185, 60, 60);
		add(btnVolver);
		

		btnAceptar = new JButton(PropertiesManager.getProperty("buscarArticuloPanel.boton.aceptar"));
		btnAceptar.setToolTipText(PropertiesManager.getProperty("buscarArticuloPanel.tooltip.aceptar"));
		
		btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
		btnAceptar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAceptar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventasController.agregarProducto(txtCodigo.getText());
				ventasController.mostrarVentasPanel();
			}
		});
		btnAceptar.setBounds(735, 115, 60, 60);
		add(btnAceptar);
		
		lblProductos = new JLabel(PropertiesManager.getProperty("buscarArticuloPanel.etiqueta.productos"));
		lblProductos.setBounds(25, 90, 70, 20);
		add(lblProductos);
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void limpiarCamposPantalla() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeComponents() {
		// TODO Auto-generated method stub
		
	}

	public void setVentasController(VentasController ventasController) {
		this.ventasController = ventasController;
	}
	
	
}
