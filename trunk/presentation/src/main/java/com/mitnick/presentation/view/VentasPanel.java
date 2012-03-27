package com.mitnick.presentation.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.mitnick.presentation.controller.VentasController;
import com.mitnick.presentation.model.VentaTableModel;

public class VentasPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private VentasController ventasController;
	
	private JScrollPane scrollPane;
	private JLabel lblTotal;
	private Component btnPagar;
	private JLabel lblVenta;
	private Component btnQuitar;
	private JButton btnBuscar;
	private JLabel lblCdigo;
	
	private JTable table;
	private JTextField txtCodigo;

	private VentaTableModel model;
	
	
	public VentasPanel(String titulo) {
		setLayout(null);
		setSize(new Dimension(815, 470));
		this.title = titulo;
		
	}


	@Override
	public String getTitle() {
		return this.title;
	}


	@Override
	protected void limpiarCamposPantalla() {
		txtCodigo.setText("");
	}


	@Override
	protected void initializeComponents() {
		
		model = new VentaTableModel();
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setBounds(125, 35, 60, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					logger.info("Buscando producto ... ");
					ventasController.agregarProducto(txtCodigo.getText());
				}
			}
		});
		txtCodigo.setBounds(200, 35, 110, 20);
		txtCodigo.setColumns(10);
		add(txtCodigo);
		
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventasController.mostrarBuscarArticuloPanel();
			}
		});
		btnBuscar.setBounds(570, 15, 60, 60);
		add(btnBuscar);
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.setBounds(735, 115, 60, 60);
		add(btnQuitar);
		
		lblVenta = new JLabel("Venta");
		lblVenta.setBounds(25, 90, 46, 20);
		add(lblVenta);
		
		btnPagar = new JButton("Pagar");
		btnPagar.setBounds(735, 185, 60, 60);
		add(btnPagar);
		
		lblTotal = new JLabel("Total: <<total>>");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(585, 439, 140, 20);
		add(lblTotal);		
	}


	public void setVentasController(VentasController ventasController) {
		this.ventasController = ventasController;
	}


	public VentaTableModel getModel() {
		return this.model;
	}

	
}
