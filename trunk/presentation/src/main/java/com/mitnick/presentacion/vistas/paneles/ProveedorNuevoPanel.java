package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ProveedorController;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProveedorDto;

@Panel("proveedorNuevoPanel")
public class ProveedorNuevoPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	private ProveedorController proveedorController;

	private ProveedorDto proveedor;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private JTextField txtCodigo;
	private JLabel lblErrorTxtCodigo;
	private JTextField txtNombre;
	private JLabel lblErrorTxtNombre;

	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JTextField txtTelefono;
	private JLabel lblErrorTxtTelefono;
	private JLabel lblTelefono;

	/**
	 * @throws Exception
	 * @wbp.parser.constructor
	 */
	public ProveedorNuevoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Autowired(required = true)
	public ProveedorNuevoPanel(@Qualifier("proveedorController") ProveedorController proveedorController) {
		this.proveedorController = proveedorController;
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
	}

	@Override
	protected void initializeComponents() {
		setSize(new Dimension(815, 470));
		setLayout(null);

		add(getLblCodigo());
		add(getLblNombre());

		add(getTxtCodigo());
		add(getTxtNombre());
		add(getTxtTelefono());

		add(getBtnAceptar());
		add(getBtnCancelar());
		
		add(getLblNombre());
		add(getLblTelefono());
		add(getLblErrorTxtCodigo());
		add(getLblErrorTxtNombre());
		add(getLblErrorTxtTelefono());
		
		setFocusTraversalPolicy();
	}

	public JLabel getLblCodigo() {
		if (lblCodigo == null) {
			lblCodigo = new JLabel(PropertiesManager.getProperty("proveedorNuevoPanel.etiqueta.codigo"));
			lblCodigo.setBounds(58, 25, 94, 20);
		}
		return lblCodigo;
	}
	
	public JLabel getLblErrorTxtCodigo() {
		if (lblErrorTxtCodigo == null) {
			lblErrorTxtCodigo = new JLabel("");
			lblErrorTxtCodigo.setBounds(58, 50, 300, 14);
		}
		return lblErrorTxtCodigo;
	}

	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(PropertiesManager.getProperty("proveedorNuevoPanel.etiqueta.nombre"));
			lblNombre.setBounds(58, 70, 94, 20);
		}
		return lblNombre;
	}
	
	public JLabel getLblErrorTxtNombre() {
		if (lblErrorTxtNombre == null) {
			lblErrorTxtNombre = new JLabel("");
			lblErrorTxtNombre.setBounds(58, 95, 300, 14);
		}
		return lblErrorTxtNombre;
	}

	public JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("proveedorNuevoPanel.boton.aceptar"));
			// btnAceptar.setToolTipText(PropertiesManager.getProperty("productoNuevoPanel.tooltip.aceptar"));

			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));

			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agregarProveedor();
				}
			});
			btnAceptar.setBounds(548, 56, 60, 60);
		}
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton(PropertiesManager.getProperty("proveedorNuevoPanel.boton.cancelar"));

			// btnAceptar.setToolTipText(PropertiesManager.getProperty("productoNuevoPanel.tooltip.cancelar"));

			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCancelar.setMargin(new Insets(-1, -1, -1, -1));

			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpiarCamposPantalla();
					proveedorController.mostrarProveedorPanel();
				}
			});
			btnCancelar.setBounds(618, 56, 60, 60);
		}
		return btnCancelar;
	}

	public JTextField getTxtCodigo() {
		if (txtCodigo == null) {
			txtCodigo = new JTextField();
			txtCodigo.setBounds(162, 25, 86, 20);
			txtCodigo.setColumns(10);
		}
		return txtCodigo;
	}

	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(162, 70, 86, 20);
		}
		return txtNombre;
	}
	
	public JTextField getTxtTelefono() {
		if(txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(162, 115, 86, 20);
		}
		return txtTelefono;
	}
	
	private JLabel getLblTelefono() {
		if (lblTelefono == null) {
			lblTelefono = new JLabel(PropertiesManager.getProperty("proveedorNuevoPanel.etiqueta.telefono"));
			lblTelefono.setBounds(58, 115, 94, 20);
		}
		return lblTelefono;
	}
	
	public JLabel getLblErrorTxtTelefono() {
		if (lblErrorTxtTelefono == null) {
			lblErrorTxtTelefono = new JLabel("");
			lblErrorTxtTelefono.setBounds(58, 140, 300, 14);
		}
		return lblErrorTxtTelefono;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtCodigo, txtNombre, txtTelefono, btnAceptar, btnCancelar }));
	}

	protected void agregarProveedor() {
		try {
			proveedorController.guardarProveedor(proveedor, getTxtCodigo().getText(), getTxtNombre().getText(), getTxtTelefono().getText());
			limpiarCamposPantalla();
			proveedorController.mostrarProveedorPanel();
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	public void setProveedor(ProveedorDto proveedorDto) {
		this.proveedor = proveedorDto;
	}

	@Override
	public void actualizarPantalla() {
		getTxtCodigo().requestFocus();

		if (Validator.isNotNull(proveedor)) {
			getTxtCodigo().setText(proveedor.getCodigo());
			getTxtNombre().setText(proveedor.getNombre());
			getTxtTelefono().setText(proveedor.getTelefono());
		}
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtCodigo();
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnAceptar());
	}
	
}
