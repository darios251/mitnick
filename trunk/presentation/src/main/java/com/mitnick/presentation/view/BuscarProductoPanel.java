package com.mitnick.presentation.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mitnick.presentation.controller.VentasController;

public class BuscarProductoPanel extends BaseView {
	private static final long serialVersionUID = 1L;
	
	private VentasController ventasController;
	
	private JScrollPane scrollPane;
	private Component lblCodigo;
	private JLabel lblDescripcion;
	private JButton btnBuscar;
	private JButton btnCancelar;
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
		
		lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(125, 35, 70, 20);
		add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(200, 35, 110, 20);
		add(txtCodigo);
		
		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(330, 35, 70, 20);
		add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(420, 35, 110, 20);
		add(txtDescripcion);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setToolTipText("Buscar Producto");
		btnBuscar.setBounds(570, 15, 60, 60);
		add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventasController.mostrarVentasPanel();
			}
		});
		btnCancelar.setBounds(735, 185, 60, 60);
		add(btnCancelar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setToolTipText("Agregar producto a la venta");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventasController.agregarProducto(txtCodigo.getText());
				ventasController.mostrarVentasPanel();
			}
		});
		btnAceptar.setBounds(735, 115, 60, 60);
		add(btnAceptar);
		
		lblProductos = new JLabel("Productos");
		lblProductos.setBounds(25, 90, 70, 20);
		add(lblProductos);
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	protected void limpiarCamposPantalla() {
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
