package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.modelos.VentaTableModel;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoVentaDto;

@Panel("ventaPanel")
public class VentaPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	private VentaController ventaController;

	private JScrollPane scrollPane;
	private JTable table;
	private VentaTableModel model;

	private JLabel lblTotal;
	private JLabel lblVenta;
	private JLabel lblCdigo;
	private JLabel lblTotalValor;
	private JLabel lblSutotal;
	private JLabel lblSubtotalValor;

	private JButton btnContinuar;
	private JButton btnQuitar;
	private JButton btnBuscar;
	private JButton btnAgregar;

	private JTextField txtCodigo;

	/**
	 * @wbp.parser.constructor
	 */
	public VentaPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Autowired
	public VentaPanel(@Qualifier("ventaController") VentaController ventaController) {
		this.ventaController = ventaController;
	}

	@Override
	public void limpiarCamposPantalla() {
		getTxtCodigo().setText("");
		getModel().setProductosVenta(new ArrayList<ProductoVentaDto>());
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 470));

		add(getScrollPane());
		add(getLblCdigo());
		add(getTxtCodigo());
		add(getBtnBuscar());
		add(getBtnQuitar());
		add(getLblVenta());
		add(getBtnContinuar());
		add(getLblSutotal());
		add(getLblSubtotalValor());
		add(getLblTotal());
		add(getLblTotalValor());
		add(getBtnAgregar());
		
		setFocusTraversalPolicy();
	}
	
	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtCodigo}));
	}

	public void agregarProducto() {
		logger.debug("entrado a agregarProducto");
		try {
			ventaController.agregarProducto(getTxtCodigo().getText());
			getTxtCodigo().setText("");
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
		logger.debug("saliendo de agregarProducto");
	}

	public void actualizarPantalla() {
		if(VentaManager.getVentaActual() != null){
			getModel().setProductosVenta(VentaManager.getVentaActual().getProductos());
			getLblTotalValor().setText(	VentaManager.getVentaActual().getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			getLblSubtotalValor().setText(VentaManager.getVentaActual().getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
	}

	public JTable getTable() {
		if(table == null) {
			table = new JTable(getModel());
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evento) {
					if (evento.getClickCount() == 2) {
						try {
							ventaController.mostrarDetalleProductoPanel();
						} catch (PresentationException ex) {
							mostrarMensaje(ex);
						}
					}
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			// table.setFillsViewportHeight(true);
		}
		return table;
	}

	public VentaTableModel getModel() {
		if(model == null) {
			model = new VentaTableModel();
		}
		return model;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 115, 700, 315);
		}
		return scrollPane;
	}

	public JLabel getLblTotal() {
		if (lblTotal == null) {
			lblTotal = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.total"));
			lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotal.setBounds(475, 441, 88, 20);
		}
		return lblTotal;
	}

	public JLabel getLblVenta() {
		if (lblVenta == null) {
			lblVenta = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.venta"));
			lblVenta.setBounds(25, 90, 46, 20);
		}
		return lblVenta;
	}

	public JLabel getLblCdigo() {
		if (lblCdigo == null) {
			lblCdigo = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.codigo"));
			lblCdigo.setBounds(330, 35, 70, 20);
		}
		return lblCdigo;
	}

	public JLabel getLblTotalValor() {
		if (lblTotalValor == null) {
			lblTotalValor = new JLabel("<< total >>");
			lblTotalValor.setBounds(625, 441, 88, 20);
		}
		return lblTotalValor;
	}

	public JLabel getLblSutotal() {
		if (lblSutotal == null) {
			lblSutotal = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.subtotal"));
			lblSutotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSutotal.setBounds(279, 441, 88, 20);
		}
		return lblSutotal;
	}

	public JLabel getLblSubtotalValor() {
		if (lblSubtotalValor == null) {
			lblSubtotalValor = new JLabel("<< subtotal >>");
			lblSubtotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSubtotalValor.setBounds(377, 441, 88, 20);
		}
		return lblSubtotalValor;
	}

	public JButton getBtnContinuar() {
		if (btnContinuar == null) {
			btnContinuar = new JButton(PropertiesManager.getProperty("ventaPanel.button.continuar"));
			btnContinuar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.continuar"));

			btnContinuar.setIcon(new ImageIcon(this.getClass().getResource("/img/continuar.png")));
			btnContinuar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnContinuar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnContinuar.setMargin(new Insets(-1, -1, -1, -1));
			btnContinuar.setBounds(735, 185, 60, 60);

			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ventaController.mostrarClienteVenta();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnContinuar;
	}

	public JButton getBtnQuitar() {
		if (btnQuitar == null) {
			btnQuitar = new JButton(PropertiesManager.getProperty("ventaPanel.button.quitar"));
			btnQuitar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.quitar"));
			btnQuitar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));

			btnQuitar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnQuitar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnQuitar.setMargin(new Insets(-1, -1, -1, -1));
			btnQuitar.setBounds(735, 115, 60, 60);

			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					try {
						int opcion = mostrarMensajeAdvertencia(PropertiesManager.getProperty("ventaPanel.dialog.confirm.quitar"));

						if (opcion == JOptionPane.YES_OPTION) {
							ventaController.quitarProductoVentaDto();
						}
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnQuitar;
	}

	public JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton(PropertiesManager.getProperty("ventaPanel.button.buscar"));
			btnBuscar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.buscar"));
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));

			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(630, 15, 60, 60);

			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ventaController.mostrarBuscarArticuloPanel();
				}
			});
		}
		return btnBuscar;
	}

	public JButton getBtnAgregar() {
		if (btnAgregar == null) {
			btnAgregar = new JButton(
					PropertiesManager.getProperty("ventaPanel.button.agregar"));
			btnAgregar.setToolTipText(PropertiesManager
					.getProperty("ventaPanel.tooltip.agregar"));

			btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
			btnAgregar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAgregar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
			btnAgregar.setBounds(560, 15, 60, 60);

			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agregarProducto();
				}
			});
		}
		return btnAgregar;
	}

	public JTextField getTxtCodigo() {
		if (txtCodigo == null) {
			txtCodigo = new JTextField();
			txtCodigo.setName("codigoVenta");
			txtCodigo.setBounds(420, 35, 110, 20);
			txtCodigo.setColumns(10);
		}
		return txtCodigo;
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtCodigo();
	}
	
	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnAgregar());
	}
}