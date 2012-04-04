package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.utils.PropertiesManager;
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
	private JLabel lblPagosRealizados;
	private JLabel lbl_ST;
	private JLabel lblSubtotal;
	private JLabel lbl_T;
	private JLabel lblTotal;
	private JLabel lbl_TP;
	private JLabel lblTotalPagado;
	private JButton btnAgregar;
	private JLabel lblMonto;
	private JComboBox cbxMedioPago;
	private JLabel lblMedioPago;

	public PagoPanel() {
		setLayout(null);
		setSize(new Dimension(815, 470));
		
		
		
		
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}

	

	@Override
	protected void initializeComponents() {
		lblMedioPago = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.medioPago"));
		lblMedioPago.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMedioPago.setBounds(75, 35, 115, 20);
		add(lblMedioPago);
		
		cbxMedioPago = new JComboBox();
		cbxMedioPago.setBounds(200, 35, 110, 20);
		add(cbxMedioPago);
		
		lblMonto = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.monto"));
		lblMonto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMonto.setBounds(330, 35, 80, 20);
		add(lblMonto);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(420, 35, 110, 20);
		add(txtMonto);
		txtMonto.setColumns(10);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 467, 315);
		add(scrollPane);
		
		btnFinalizar = new JButton(PropertiesManager.getProperty("pagoPanel.button.finalizar"));
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventaController.mostrarVentasPanel();
			}
		});
		btnFinalizar.setBounds(724, 370, 60, 60);
		add(btnFinalizar);
		
		lblPagosRealizados = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.pagosRealizados"));
		lblPagosRealizados.setBounds(268, 124, 88, 20);
		add(lblPagosRealizados);
		
		lbl_ST = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.subtotal"));
		lbl_ST.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_ST.setBounds(542, 176, 88, 20);
		add(lbl_ST);
		
		lblSubtotal = new JLabel();
		lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubtotal.setBounds(650, 176, 109, 20);
		add(lblSubtotal);
		
		lbl_T = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.total"));
		lbl_T.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_T.setBounds(542, 207, 88, 20);
		add(lbl_T);
		
		lblTotal = new JLabel();
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(650, 207, 109, 20);
		add(lblTotal);
		
		lbl_TP = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalPagado"));
		lbl_TP.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_TP.setBounds(542, 238, 88, 20);
		add(lbl_TP);
		
		lblTotalPagado = new JLabel();
		lblTotalPagado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPagado.setBounds(650, 238, 109, 20);
		add(lblTotalPagado);
		
		btnAgregar = new JButton(PropertiesManager.getProperty("pagoPanel.boton.agregar"));
		btnAgregar.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.agregar"));
		
		btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
		btnAgregar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAgregar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAgregar.setBounds(570, 15, 60, 60);
		add(btnAgregar);
	}
	
	@Override
	public void limpiarCamposPantalla() {
		
	}
	
}
