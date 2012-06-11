package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoVentaDto;

@Panel("detalleProductoPanel")
public class DetalleProductoPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	private VentaController ventaController;
	
	private JLabel lblCantidad;
	private JLabel lblCodigo;
	private JLabel lblCodigoValor;
	
	private JTextField txtCantidad;
	
	private JButton btnAceptar;
	private JButton btnVolver;
	
	private ProductoVentaDto producto;

	@Autowired
	public DetalleProductoPanel(
			@Qualifier("ventaController") VentaController ventaController) {
		this.ventaController = ventaController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public DetalleProductoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 470));

		add(getLblCantidad());
		add(getLblCodigo());
		add(getLblCodigoValor());
		
		add(getTxtCantidad());

		add(getBtnAceptar());
		add(getBtnVolver());

		setFocusTraversalPolicy();
	}

	public JTextField getTxtCantidad() {
		if (txtCantidad == null) {
			txtCantidad = new JTextField();
			txtCantidad.setBounds(320, 87, 110, 20);
			txtCantidad.setColumns(10);
			txtCantidad.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						modificarCantidad();
					}
				}
			});
		}
		return txtCantidad;
	}

	public JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton(
					PropertiesManager
							.getProperty("detalleProductoPanel.boton.aceptar"));
			btnAceptar.setToolTipText(PropertiesManager
					.getProperty("detalleProductoPanel.tooltip.aceptar"));

			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setBounds(470, 50, 60, 60);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));

			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modificarCantidad();
				}
			});
			
		}
		return btnAceptar;
	}

	public JLabel getLblCantidad() {
		if (lblCantidad == null) {
			lblCantidad = new JLabel(
					PropertiesManager
							.getProperty("detalleProductoPanel.etiqueta.cantidad"));
			lblCantidad.setHorizontalAlignment(SwingConstants.LEFT);
			lblCantidad.setBounds(200, 87, 110, 20);
		}
		return lblCantidad;
	}

	public JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton(
					PropertiesManager
							.getProperty("detalleProductoPanel.boton.cancelar"));
			btnVolver.setToolTipText(PropertiesManager
					.getProperty("detalleProductoPanel.tooltip.cancelar"));
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/cancelar.png")));
			
			btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			btnVolver.setBounds(540, 50, 60, 60);
			
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ventaController.mostrarVentasPanel();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVolver;
	}

	public JLabel getLblCodigo() {
		if (lblCodigo == null) {
			lblCodigo = new JLabel(
					PropertiesManager
							.getProperty("detalleProductoPanel.etiqueta.codigo"));
			lblCodigo.setHorizontalAlignment(SwingConstants.LEFT);
			lblCodigo.setBounds(200, 50, 110, 20);
		}
		return lblCodigo;
	}

	public JLabel getLblCodigoValor() {
		if (lblCodigoValor == null) {
			lblCodigoValor = new JLabel("<< codigo >>");
			lblCodigoValor.setHorizontalAlignment(SwingConstants.LEFT);
			lblCodigoValor.setBounds(320, 50, 110, 20);
		}
		return lblCodigoValor;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { txtCantidad }));
	}

	protected void modificarCantidad() {
		try {
			ventaController.modificarCantidad(getProducto(),
					txtCantidad.getText());
			ventaController.mostrarVentasPanel();
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
	}

	public void actualizarPantalla() {
		getTxtCantidad().setText(getProducto().getCantidad() + "");
		getTxtCantidad().requestFocus();
		getLblCodigoValor().setText(getProducto().getProducto().getCodigo());
	}

	public ProductoVentaDto getProducto() {
		if (Validator.isNull(producto))
			throw new PresentationException("error.unknown",
					"El producto es nulo");
		return producto;
	}

	public void setProducto(ProductoVentaDto producto) {
		this.producto = producto;
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtCantidad();
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnAceptar());
	}
}
