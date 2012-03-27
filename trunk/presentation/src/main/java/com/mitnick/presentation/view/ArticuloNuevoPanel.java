package com.mitnick.presentation.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ArticuloNuevoPanel extends JPanel {
	
	public ArticuloNuevoPanel() {
		setSize(new Dimension(450, 210));
		setLayout(null);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(25, 25, 60, 20);
		add(lblCdigo);
		
		textField = new JTextField();
		textField.setBounds(95, 25, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(25, 56, 60, 20);
		add(lblDescripcin);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(95, 56, 326, 20);
		add(textField_1);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(25, 87, 60, 20);
		add(lblTipo);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(95, 87, 86, 20);
		add(comboBox);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(285, 87, 40, 20);
		add(lblMarca);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(335, 87, 86, 20);
		add(comboBox_2);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(25, 118, 60, 20);
		add(lblPrecio);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(95, 118, 86, 20);
		add(textField_2);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(285, 118, 40, 20);
		add(lblStock);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(335, 118, 86, 20);
		add(textField_3);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(123, 176, 89, 23);
		add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(236, 176, 89, 23);
		add(btnCancelar);
	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
}
