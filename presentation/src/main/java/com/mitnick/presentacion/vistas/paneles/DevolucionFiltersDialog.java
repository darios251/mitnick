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
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.VentaDto;

public class DevolucionFiltersDialog  extends BaseDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JLabel lblNumeroTransaccion; 
	private JTextField txtNroTrx; 
	private JLabel lblTipoFactura;
	private JComboBox<String> cmbTipoFactura;  
	
	private VentaController controller;
	
	public VentaDto venta = null;
	
	public boolean cancelar=false;
	
	public DevolucionFiltersDialog(JFrame frame, VentaController ventaController) {
		super(frame, true);
		venta = null;
		this.controller = ventaController;
		this.cancelar=false;
		getContentPane().setLayout(null);
		setSize(450, 245);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getLblNumeroTransaccion());
		getContentPane().add(getTxtNroTrx());
		getContentPane().add(getLblTipoFactura());
		getContentPane().add(getCmbTipoFactura());
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
	
	public JLabel getLblTipoFactura() {
		if (lblTipoFactura == null) {
			lblTipoFactura = new JLabel(PropertiesManager.getProperty("dialog.consultarTransacciones.label.facturacion"));
			lblTipoFactura.setBounds(110, 60, 120, 20);
		}
		return lblTipoFactura ;
	}
	
	public JTextField getTxtNroTrx() {
		if (txtNroTrx== null) {
			txtNroTrx =  new JTextField();	
			txtNroTrx.setColumns(10);
			txtNroTrx.setBounds(230, 30, 110, 20);
		}
		return txtNroTrx;
	}
	
	public JComboBox<String> getCmbTipoFactura() {
		String[] tipos = {PropertiesManager.getProperty("dialog.consultarTransacciones.filter.b"), PropertiesManager.getProperty("dialog.consultarTransacciones.filter.a")};
		cmbTipoFactura = new JComboBox<String>(tipos);
		cmbTipoFactura.setBounds(230, 60, 110, 20);
		cmbTipoFactura.insertItemAt(null, 0);
		return cmbTipoFactura;
	}
	
	public JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("dialog.consultarTransacciones.button.aceptar"));;
			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
			btnAceptar.setBounds(95, 140, 60, 60);
			
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

			btnCancelar.setBounds(275, 140, 60, 60);
			btnCancelar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					keyEscape();
				}
			});
		}
		
		return btnCancelar;
	}

	protected void keyIntro() {
		if (Validator.isBlankOrNull(txtNroTrx.getText()))
			return;
		setVisible(false);
		venta = controller.getVentaByNroFacturaTipo(txtNroTrx.getText(), cmbTipoFactura.getSelectedItem().toString());
	}
	
	protected void keyEscape() {	
		txtNroTrx.setText("");
		venta = null;
		this.cancelar=true;
		this.setVisible(false);
	}
	
}
