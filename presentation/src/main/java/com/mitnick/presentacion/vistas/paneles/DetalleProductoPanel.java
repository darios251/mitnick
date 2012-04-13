package com.mitnick.presentacion.vistas.paneles;

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
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoVentaDto;

@Panel("detalleProductoPanel")
public class DetalleProductoPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private VentaController ventaController;
	
	private JTextField txtCantidad;
	private JButton btnAceptar;
	private JLabel lblCantidad;
	private JButton btnVolver;
	private JLabel lblCodigo;
	private JLabel lblCodigoValor;
	
	private ProductoVentaDto producto;
	
	@Autowired
	public DetalleProductoPanel(@Qualifier("ventaController") VentaController ventaController) {
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
		
		lblCantidad = new JLabel(PropertiesManager.getProperty("detalleProductoPanel.etiqueta.cantidad"));
		lblCantidad.setHorizontalAlignment(SwingConstants.LEFT);
		lblCantidad.setBounds(44, 105, 110, 20);
		add(lblCantidad);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(164, 105, 110, 20);
		txtCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					modificarCantidad();
				}
			}
		});
		add(txtCantidad);
		txtCantidad.setColumns(10);
		
		btnAceptar = new JButton(PropertiesManager.getProperty("detalleProductoPanel.boton.aceptar"));
		btnAceptar.setToolTipText(PropertiesManager.getProperty("detalleProductoPanel.tooltip.aceptar"));
		
		btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
		btnAceptar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAceptar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarCantidad();
			}
		});
		btnAceptar.setBounds(324, 35, 60, 60);
		add(btnAceptar);
		
		btnVolver = new JButton(PropertiesManager.getProperty("detalleProductoPanel.boton.cancelar"));
		btnVolver.setToolTipText(PropertiesManager.getProperty("detalleProductoPanel.tooltip.cancelar"));
		btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVolver.setMargin(new Insets(-1, -1, -1, -1));
		btnVolver.setBounds(324, 105, 60, 60);
		btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ventaController.mostrarVentasPanel();
				}
				catch(PresentationException ex) {
					mostrarMensaje(ex);
				}
			}
		});
		
		add(btnVolver);
		
		lblCodigo = new JLabel(PropertiesManager.getProperty("detalleProductoPanel.etiqueta.codigo"));
		lblCodigo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigo.setBounds(44, 56, 110, 20);
		add(lblCodigo);
		
		lblCodigoValor = new JLabel("<< codigo >>");
		lblCodigoValor.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigoValor.setBounds(164, 56, 110, 20);
		add(lblCodigoValor);
	}
	
	protected void modificarCantidad() {
		try {
			ventaController.modificarCantidad(getProducto(), txtCantidad.getText());
			ventaController.mostrarVentasPanel();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void limpiarCamposPantalla() {
		if(Validator.isNotNull(txtCantidad))
			txtCantidad.setText("");
	}

	public void actualizarPantalla() {
		if(Validator.isNotNull(txtCantidad)) {
			txtCantidad.setText(getProducto().getCantidad() + "");
			txtCantidad.requestFocus();
		}
		if(Validator.isNotNull(lblCodigoValor)) {
			lblCodigoValor.setText(getProducto().getProducto().getCodigo());
		}
	}

	public ProductoVentaDto getProducto() {
		if(Validator.isNull(producto))
			throw new PresentationException("error.unknown", "El producto es nulo");
		return producto;
	}

	public void setProducto(ProductoVentaDto producto) {
		this.producto = producto;
	}
}
