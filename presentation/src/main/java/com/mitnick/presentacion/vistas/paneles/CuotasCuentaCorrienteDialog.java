package com.mitnick.presentacion.vistas.paneles;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mitnick.presentacion.modelos.CuentaCorrienteTableModel;
import com.mitnick.utils.dtos.CuotaDto;

public class CuotasCuentaCorrienteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private CuentaCorrienteTableModel model;
	private JScrollPane scrollPane;
	
	public CuotasCuentaCorrienteDialog(JFrame frame, List<CuotaDto> cuotasDto) {
		super(frame, true);
		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(frame);
		
		add(getScrollPane());
		
		getModel().setCuotas(cuotasDto);
	}
	
	public JTable getTable() {
		if(table == null) {
			table = new JTable(getModel());
			table.setBounds(0, 0, 1, 1);
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
