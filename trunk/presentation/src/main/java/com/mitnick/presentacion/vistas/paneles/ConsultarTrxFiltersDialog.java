package com.mitnick.presentacion.vistas.paneles;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ReportesController;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;

public class ConsultarTrxFiltersDialog  extends BaseDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JLabel lblNumeroTransaccion; 
	private JTextField txtNroTrx; 
	private JLabel lblTipoTrx;
	private JComboBox<String> cmbTipoTrx;
	private JLabel lblTipoFactura;
	private JComboBox<String> cmbTipoFactura;  
	
	private JLabel lblCaja; 
	private JTextField txtCaja; 
	
	private ReportesController controller;
	
	public ConsultarTrxFiltersDialog(JFrame frame, ReportesController reportesController) {
		super(frame, true);
		this.controller = reportesController;
		
		getContentPane().setLayout(null);
		setSize(450, 300);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getLblNumeroTransaccion());
		getContentPane().add(getTxtNroTrx());
		getContentPane().add(getLblTipoTrx());
		getContentPane().add(getCmbTipoTrx());
		getContentPane().add(getLblTipoFactura());
		getContentPane().add(getCmbTipoFactura());
		getContentPane().add(getLblCaja());
		getContentPane().add(getTxtCaja());		

		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());

		this.setTitle(PropertiesManager.getProperty("dialog.consultarTransacciones.tittle"));
		
		setVisible(true);
	}

	public JLabel getLblNumeroTransaccion() {
		if (lblNumeroTransaccion == null) {
			lblNumeroTransaccion = new JLabel(PropertiesManager.getProperty("dialog.consultarTransacciones.label.nroTransaccion"));
			lblNumeroTransaccion.setBounds(110, 30, 120, 20);
		}
		return lblNumeroTransaccion ;
	}
	
	public JLabel getLblTipoTrx() {
		if (lblTipoTrx == null) {
			lblTipoTrx = new JLabel(PropertiesManager.getProperty("dialog.consultarTransacciones.label.tipoTransaccion"));
			lblTipoTrx.setBounds(110, 60, 120, 20);
		}
		return lblTipoTrx ;
	}
	
	public JLabel getLblTipoFactura() {
		if (lblTipoFactura == null) {
			lblTipoFactura = new JLabel(PropertiesManager.getProperty("dialog.consultarTransacciones.label.facturacion"));
			lblTipoFactura.setBounds(110, 90, 120, 20);
		}
		return lblTipoFactura ;
	}
	
	public JLabel getLblCaja() {
		if (lblCaja == null) {
			lblCaja = new JLabel(PropertiesManager.getProperty("dialog.consultarTransacciones.label.caja"));
			lblCaja.setBounds(110, 120, 120, 20);
		}
		return lblCaja;
	}
	
	public JTextField getTxtNroTrx() {
		if (txtNroTrx== null) {
			txtNroTrx =  new JTextField();	
			txtNroTrx.setColumns(10);
			txtNroTrx.setBounds(230, 30, 110, 20);
		}
		return txtNroTrx;
	}
	
	public JComboBox<String> getCmbTipoTrx() {
		String[] tipos = {PropertiesManager.getProperty("dialog.consultarTransacciones.filter.venta"), PropertiesManager.getProperty("dialog.consultarTransacciones.filter.devolucion")};
		cmbTipoTrx = new JComboBox<String>(tipos);
		cmbTipoTrx.setBounds(230, 60, 110, 20);
		cmbTipoTrx.insertItemAt(null, 0);
		return cmbTipoTrx;
	}
	
	public JComboBox<String> getCmbTipoFactura() {
		String[] tipos = {PropertiesManager.getProperty("dialog.consultarTransacciones.filter.b"), PropertiesManager.getProperty("dialog.consultarTransacciones.filter.a"), PropertiesManager.getProperty("dialog.consultarTransacciones.filter.none")};
		cmbTipoFactura = new JComboBox<String>(tipos);
		cmbTipoFactura.setBounds(230, 90, 110, 20);
		cmbTipoFactura.insertItemAt(null, 0);
		return cmbTipoFactura;
	}
	
	public JTextField getTxtCaja() {
		if (txtCaja == null) {
			txtCaja =  new JTextField();	
			txtCaja.setColumns(10);
			txtCaja.setBounds(230, 120, 110, 20);
		}
		txtCaja.setText(PropertiesManager.getPropertyAsInteger("application.caja.numero").toString());
		return txtCaja;
	}
	
	public JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("dialog.consultarTransacciones.button.aceptar"));;
			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
			btnAceptar.setBounds(95, 160, 60, 60);
			
			btnAceptar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						keyIntro();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		if(btnCancelar == null) {
			btnCancelar = new JButton(PropertiesManager.getProperty("dialog.consultarTransacciones.button.cancelar"));
			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCancelar.setMargin(new Insets(-1, -1, -1, -1));

			btnCancelar.setBounds(275, 160, 60, 60);
			btnCancelar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					keyEscape();
				}
			});
		}
		
		return btnCancelar;
	}

	protected void keyIntro() {
		setVisible(false);
		int numeroCaja = 0;
		if (Validator.isBlankOrNull(txtCaja.getText()))
			numeroCaja = PropertiesManager.getPropertyAsInteger("application.caja.numero");
		else {
			try {
				numeroCaja = Integer.parseInt(txtCaja.getText());
			} catch (NumberFormatException e) {
				numeroCaja = PropertiesManager.getPropertyAsInteger("application.caja.numero");
			}
		}
		controller.consultarTransaccion(txtNroTrx.getText(), cmbTipoTrx.getSelectedItem().toString(), cmbTipoFactura.getSelectedItem().toString(), numeroCaja);
	}
	
	protected void keyEscape() {	
		txtNroTrx.setText("");
		this.setVisible(false);
	}
	
}
