package com.mitnick.presentacion.vistas.paneles;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ProductoVentaDto;

public class AgregarDescuentoDialog  extends BaseDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JLabel lblDescuento; 
	private JTextField txtDescuento; 
	private JLabel lblTipoDescuento;
	private JComboBox<String> cmbTipoDescuento;  
	
	private VentaController controller;
	
	public ProductoVentaDto producto = null;
	
	public boolean cancelar=false;
	
	public AgregarDescuentoDialog(JFrame frame, VentaController ventaController, ProductoVentaDto producto) {
		super(frame, true);		
		this.controller = ventaController;
		this.cancelar=false;
		this.producto = producto;
		getContentPane().setLayout(null);
		setSize(450, 245);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getLblDescuento());
		getContentPane().add(getTxtDescuento());
		getContentPane().add(getLblTpoDescuento());
		getContentPane().add(getCmbTipoDescuento());
		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());

		this.setTitle(PropertiesManager.getProperty("dialog.agregarDescuento.tittle"));
		
		setVisible(true);
	}

	public JLabel getLblDescuento() {
		if (lblDescuento == null) {
			lblDescuento = new JLabel(PropertiesManager.getProperty("dialog.agregarDescuento.label.descuento"));
			lblDescuento.setBounds(110, 30, 120, 20);
		}
		return lblDescuento ;
	}
	
	public JLabel getLblTpoDescuento() {
		if (lblTipoDescuento == null) {
			lblTipoDescuento = new JLabel(PropertiesManager.getProperty("dialog.agregarDescuento.label.tipoDescuento"));
			lblTipoDescuento.setBounds(110, 60, 120, 20);
		}
		return lblTipoDescuento ;
	}
	
	public JTextField getTxtDescuento() {
		if (txtDescuento== null) {
			txtDescuento =  new JTextField();	
			txtDescuento.setColumns(10);
			txtDescuento.setBounds(230, 30, 110, 20);
		}
		return txtDescuento;
	}
	
	public JComboBox<String> getCmbTipoDescuento() {
		String[] tipos = {PropertiesManager.getProperty("dialog.agregarDescuento.monto"), PropertiesManager.getProperty("dialog.agregarDescuento.porcentaje")};
		cmbTipoDescuento = new JComboBox<String>(tipos);
		cmbTipoDescuento.setBounds(230, 60, 110, 20);
		cmbTipoDescuento.insertItemAt(null, 0);
		return cmbTipoDescuento;
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
		if (Validator.isBlankOrNull(txtDescuento.getText()))
			return;
		DescuentoDto descuento = new DescuentoDto();
		descuento.setDescuento(new BigDecimal(txtDescuento.getText()));
		if (PropertiesManager.getProperty("dialog.agregarDescuento.monto").equals(cmbTipoDescuento.getSelectedItem().toString()))
			descuento.setTipo(DescuentoDto.MONTO);
		else
			descuento.setTipo(DescuentoDto.PORCENTAJE);
		try {
			controller.agregarDescuento(descuento, VentaManager.getVentaActual(), producto);
		} catch(PresentationException ex) {
			
			mostrarMensaje(ex);
		}
		setVisible(false);
	}
	
	protected void keyEscape() {	
		txtDescuento.setText("");		
		producto = null;
		this.cancelar=true;
		this.setVisible(false);
	}
	
}
