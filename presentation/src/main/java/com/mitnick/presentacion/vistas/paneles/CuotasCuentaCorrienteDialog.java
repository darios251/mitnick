package com.mitnick.presentacion.vistas.paneles;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mitnick.presentacion.modelos.CuentaCorrienteTableModel;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.CuotaDto;

public class CuotasCuentaCorrienteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private CuentaCorrienteTableModel model;
	private JScrollPane scrollPane;
	private JButton btnAceptar;
	
	public CuotasCuentaCorrienteDialog(JFrame frame, List<CuotaDto> cuotasDto) {
		super(frame, true);
		getContentPane().setLayout(null);
		setSize(600, 400);
		
		setLocationRelativeTo(null);
		
		getContentPane().add(getScrollPane());
		getContentPane().add(getBtnAceptar());
		
		getModel().setCuotas(cuotasDto);
		setVisible(true);
	}
	
	public JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("cuentaCorrienteDialog.aceptar"));
			btnAceptar.setBounds(400, 300, 100, 25);
		}
		
		return btnAceptar;
	}
	
	public JTable getTable() {
		if(table == null) {
			table = new JTable(getModel());
			table.setBounds(200, 400, 200, 28);
		}
		return table;
	}
	
	public CuentaCorrienteTableModel getModel() {
		if(model == null) {
			model = new CuentaCorrienteTableModel();
		}
		return model;
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(20, 20, 516, 143);
		}
		return scrollPane;
	}
}
