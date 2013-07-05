package com.mitnick.presentacion.vistas.paneles;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VendedorController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.VendedorDto;

public class VendedorDialog  extends BaseDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JLabel lblVendedor;
	
	private MitnickComboBoxModel<VendedorDto> modelCmbVendedor;
	private JComboBox<VendedorDto> cmbVendedor;
	
	@Autowired
	private VendedorController vendedorController;
	
	private VendedorDto selected;
	
	private boolean init = false;
	
	public VendedorDialog(JFrame frame, VendedorController vendedorController) {
		super(frame, true);
		
		this.vendedorController = vendedorController;
		getContentPane().setLayout(null);
		setSize(450, 245);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getLblVendedor());
		getContentPane().add(getCmbVendedor());
		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());

		this.setTitle(PropertiesManager.getProperty("dialog.seleccionarVendedor.tittle"));
		
		setVisible(true);
	}

	public JLabel getLblVendedor() {
		if (lblVendedor == null) {
			lblVendedor = new JLabel(PropertiesManager.getProperty("dialog.seleccionarVendedor.label.vendedor"));
			lblVendedor.setBounds(110, 60, 90, 20);
		}
		return lblVendedor;
	}
	
	public JComboBox<VendedorDto> getCmbVendedor() {
		cmbVendedor  = new JComboBox<VendedorDto>(getModelCmbVendedor());
		cmbVendedor.setBounds(200, 60, 110, 20);
		cmbVendedor.insertItemAt(null, 0);
		return cmbVendedor;
	}
	
	public JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("dialog.seleccionarVendedor.button.aceptar"));;
			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
			btnAceptar.setBounds(95, 140, 60, 60);
			
			btnAceptar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						init = true;
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
			btnCancelar = new JButton(PropertiesManager.getProperty("dialog.seleccionarVendedor.button.cancelar"));
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
		if (init){
			selected = (VendedorDto)cmbVendedor.getSelectedItem();
			setVisible(false);
		} else
			init = true;
	}
	
	protected void keyEscape() {
		selected = null;
		this.setVisible(false);
	}

	public VendedorDto getSelected() {
		return selected;
	}
	
	
	public MitnickComboBoxModel<VendedorDto> getModelCmbVendedor() {
		if (modelCmbVendedor == null) {
			modelCmbVendedor = new MitnickComboBoxModel<VendedorDto>();			
		}
		modelCmbVendedor.removeAllElements();
		modelCmbVendedor.addItems(vendedorController.obtenerVendedores());
		return modelCmbVendedor;
	}
}
