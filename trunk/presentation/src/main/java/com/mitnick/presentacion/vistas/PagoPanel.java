package com.mitnick.presentacion.vistas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.utils.anotaciones.Panel;

@Panel("pagoPanel")
public class PagoPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VentaController ventaController;
	
	private JTextField txtMonto;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnFinalizar;
	private Component lblPagosRealizados;
	private JLabel lbl_ST;
	private JLabel lblSubtotal;
	private JLabel lbl_T;
	private JLabel lblTotal;
	private JLabel lbl_TP;
	private JLabel lblTotalPagado;
	private JButton btnAgregar;
	private JLabel lblMonto;
	private JComboBox cbxCtaCte;
	private Component lblEfectivo;
	private JComboBox cbxContado;
	private Component lblTarjeta;

	public PagoPanel() {
		setLayout(null);
		
		lblTarjeta = new JLabel("Contado");
		lblTarjeta.setBounds(25, 48, 62, 20);
		add(lblTarjeta);
		
		cbxContado = new JComboBox();
		cbxContado.setBounds(25, 68, 94, 20);
		cbxContado.addItem("Efectivo");
		cbxContado.addItem("Tarjeta");
		add(cbxContado);
		
		lblEfectivo = new JLabel("Cuenta Corriente");
		lblEfectivo.setBounds(140, 48, 100, 20);
		add(lblEfectivo);
		
		cbxCtaCte = new JComboBox();
		cbxCtaCte.setBounds(140, 68, 94, 20);
		cbxCtaCte.addItem("Efectivo");
		cbxCtaCte.addItem("Tarjeta");
		add(cbxCtaCte);
		
		lblMonto = new JLabel("Monto");
		lblMonto.setBounds(25, 102, 46, 20);
		add(lblMonto);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(25, 124, 86, 20);
		add(txtMonto);
		txtMonto.setColumns(10);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(268, 48, 261, 239);
		add(scrollPane);
		
		btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventaController.mostrarVentasPanel();
			}
		});
		btnFinalizar.setBounds(440, 298, 89, 23);
		add(btnFinalizar);
		
		lblPagosRealizados = new JLabel("Pagos Realizados");
		lblPagosRealizados.setBounds(268, 31, 88, 20);
		add(lblPagosRealizados);
		
		lbl_ST = new JLabel("Subtotal:");
		lbl_ST.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_ST.setBounds(25, 213, 88, 20);
		add(lbl_ST);
		
		lblSubtotal = new JLabel("<<subtotal>>");
		lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubtotal.setBounds(120, 216, 86, 14);
		add(lblSubtotal);
		
		lbl_T = new JLabel("Total:");
		lbl_T.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_T.setBounds(25, 239, 88, 20);
		add(lbl_T);
		
		lblTotal = new JLabel("<<total>>");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(120, 242, 86, 14);
		add(lblTotal);
		
		lbl_TP = new JLabel("Total Pagado:");
		lbl_TP.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_TP.setBounds(25, 267, 88, 20);
		add(lbl_TP);
		
		lblTotalPagado = new JLabel("<<total pagado>>");
		lblTotalPagado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPagado.setBounds(120, 270, 86, 14);
		add(lblTotalPagado);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(140, 123, 89, 23);
		add(btnAgregar);
		
		
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}

	@Override
	public void limpiarCamposPantalla() {
		
	}

	@Override
	protected void initializeComponents() {
		
	}
	
}
