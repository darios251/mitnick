package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.controladores.ProveedorController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.ProveedorDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("productoNuevoPanel")
public class ProductoNuevoPanel extends BasePanel<ProductoController> {

	private static final long serialVersionUID = 1L;

	private ProveedorController proveedorController;

	private ProductoNuevoDto producto;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private JTextField txtCodigo;
	private JLabel lblErrorTxtCodigo;
	
	private JTextField txtTalle;
	
	private JTextField txtDescripcion;
	private JLabel lblErrorTxtDescripcion;
	
	private JTextField txtPrecioVenta;
	private JLabel lblErrorTxtPrecioVenta;
	
	private JTextField txtStock;
	private JLabel lblErrorTxtStock;

	private JComboBox<TipoDto> cmbTipo;
	private JLabel lblErrorCmbTipo;
	
	private MitnickComboBoxModel<TipoDto> modelCmbTipo;
	private JComboBox<MarcaDto> cmbMarca;
	private JLabel lblErrorCmbMarca;
	
	private MitnickComboBoxModel<MarcaDto> modelCmbMarca;

	private JLabel lblCodigo;
	private JLabel lblDescripcion;
	private JLabel lblTipo;
	private JLabel lblMarca;
	private JLabel lblPrecioVenta;
	private JLabel lblPrecioCompra;
	private JLabel lblStock;
	private JLabel lblTalle;
	
	private JTextField txtPrecioCompra;
	private JLabel lblErrorTxtPrecioCompra;
	
	private JLabel lblStockMinimo;
	
	private JTextField txtStockMinimo;
	private JLabel lblErrorTxtStockMinimo;
	
	private JLabel lblStockCompra;
	private JTextField txtStockCompra;
	private JLabel lblErrorTxtStockCompra;
	
	private JLabel lblProveedores;
	private JComboBox<ProveedorDto> cmbProveedor;
	private JLabel lblErrorCmbProveedor;
	
	private MitnickComboBoxModel<ProveedorDto> modelCmbProveedor;
	
	private boolean confirmado = false;
	
	/**
	 * @throws Exception
	 * @wbp.parser.constructor
	 */
	public ProductoNuevoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Autowired(required = true)
	public ProductoNuevoPanel(@Qualifier("productoController") ProductoController productoController,
			@Qualifier("proveedorController") ProveedorController proveedorController) {
		controller = productoController;
		this.proveedorController = proveedorController;
	}

	@Override
	protected void limpiarCamposPantalla() {
		setConfirmado(false);
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		try {
			cmbMarca.setSelectedIndex(0);
			cmbTipo.setSelectedIndex(0);
		} catch (Exception e) {
		}
	}

	@Override
	protected void initializeComponents() {
		setSize(new Dimension(815, 470));
		setLayout(null);

		add(getLblCodigo());
		add(getLblDescripcion());
		add(getLblTipo());
		add(getLblMarca());
		add(getLblPrecioVenta());
		add(getLblPrecioCompra());
		add(getLblStock());

		add(getTxtCodigo());
		add(getTxtDescripcion());
		add(getTxtPrecioVenta());
		add(getTxtPrecioCompra());
		add(getTxtStock());

		add(getCmbTipo());
		add(getCmbMarca());

		add(getBtnAceptar());
		add(getBtnCancelar());
		add(getLblStockMinimo());
		add(getTxtStockMinimo());
		add(getLblStockCompra());
		add(getTxtStockCompra());
		
		add(getLblTalle());
		add(getTxtTalle());
		
		add(getLblErrorCmbMarca());
		add(getLblErrorCmbProveedor());
		add(getLblErrorCmbTipo());
		add(getLblErrorTxtCodigo());
		add(getLblErrorTxtDescripcion());
		add(getLblErrorTxtPrecioCompra());
		add(getLblErrorTxtPrecioVenta());
		add(getLblErrorTxtStock());
		add(getLblErrorTxtStockCompra());
		add(getLblErrorTxtStockMinimo());
		
		add(getLblProveedores());
		add(getCmbProveedores());
		
		setFocusTraversalPolicy();
	}

	public JLabel getLblCodigo() {
		if (lblCodigo == null) {
			lblCodigo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.codigo"));
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

	public JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.descripcion"));
			lblDescripcion.setBounds(58, 70, 94, 20);
		}
		return lblDescripcion;
	}
	
	public JLabel getLblErrorTxtDescripcion() {
		if (lblErrorTxtDescripcion == null) {
			lblErrorTxtDescripcion = new JLabel("");
			lblErrorTxtDescripcion.setBounds(58, 95, 300, 14);
		}
		return lblErrorTxtDescripcion;
	}

	public JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.tipo"));
			lblTipo.setBounds(58, 115, 94, 20);
		}
		return lblTipo;
	}
	
	public JLabel getLblErrorCmbTipo() {
		if (lblErrorCmbTipo == null) {
			lblErrorCmbTipo = new JLabel("");
			lblErrorCmbTipo.setBounds(58, 140, 300, 14);
		}
		return lblErrorCmbTipo;
	}
	
	public JLabel getLblMarca() {
		if (lblMarca == null) {
			lblMarca = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.marca"));
			lblMarca.setBounds(319, 115, 94, 20);
		}
		return lblMarca;
	}
	
	public JLabel getLblErrorCmbMarca() {
		if (lblErrorCmbMarca == null) {
			lblErrorCmbMarca = new JLabel("");
			lblErrorCmbMarca.setBounds(58, 140, 300, 14);
		}
		return lblErrorCmbMarca;
	}

	public JLabel getLblPrecioVenta() {
		if (lblPrecioVenta == null) {
			lblPrecioVenta = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.precioVenta"));
			lblPrecioVenta.setBounds(58, 160, 94, 20);
		}
		return lblPrecioVenta;
	}
	
	public JLabel getLblErrorTxtPrecioVenta() {
		if (lblErrorTxtPrecioVenta == null) {
			lblErrorTxtPrecioVenta = new JLabel("");
			lblErrorTxtPrecioVenta.setBounds(58, 185, 300, 14);
		}
		return lblErrorTxtPrecioVenta;
	}
	
	public JLabel getLblPrecioCompra() {
		if (lblPrecioCompra == null) {
			lblPrecioCompra = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.precioCompra"));
			lblPrecioCompra.setBounds(58, 205, 94, 20);
		}
		return lblPrecioCompra;
	}
	
	public JLabel getLblErrorTxtPrecioCompra() {
		if (lblErrorTxtPrecioCompra == null) {
			lblErrorTxtPrecioCompra = new JLabel("");
			lblErrorTxtPrecioCompra.setBounds(58, 230, 300, 14);
		}
		return lblErrorTxtPrecioCompra;
	}

	public JLabel getLblStock() {
		if (lblStock == null) {
			lblStock = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stock"));
			lblStock.setBounds(319, 160, 94, 20);
		}
		return lblStock;
	}
	
	public JLabel getLblErrorTxtStock() {
		if (lblErrorTxtStock == null) {
			lblErrorTxtStock = new JLabel("");
			lblErrorTxtStock.setBounds(319, 185, 300, 14);
		}
		return lblErrorTxtStock;
	}
	
	public JLabel getLblStockMinimo() {
		if (lblStockMinimo == null) {
			lblStockMinimo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stockMinimo"));
			lblStockMinimo.setBounds(319, 205, 94, 20);
		}
		return lblStockMinimo;
	}
	
	public JLabel getLblErrorTxtStockMinimo() {
		if (lblErrorTxtStockMinimo == null) {
			lblErrorTxtStockMinimo = new JLabel("");
			lblErrorTxtStockMinimo.setBounds(319, 230, 300, 14);
		}
		return lblErrorTxtStockMinimo;
	}
	
	public JLabel getLblStockCompra() {
		if (lblStockCompra == null) {
			lblStockCompra = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stockCompra"));
			lblStockCompra.setBounds(319, 250, 94, 20);
		}
		return lblStockCompra;
	}
	
	public JLabel getLblTalle() {
		if (lblTalle == null) {
			lblTalle = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.talle"));
			lblTalle.setBounds(319, 295, 94, 20);
		}
		return lblTalle;
	}
	
	public JLabel getLblErrorTxtStockCompra() {
		if (lblErrorTxtStockCompra == null) {
			lblErrorTxtStockCompra = new JLabel("");
			lblErrorTxtStockCompra.setBounds(319, 275, 300, 14);
		}
		return lblErrorTxtStockCompra;
	}
	
	public JLabel getLblProveedores() {
		lblProveedores = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.proveedores"));
		lblProveedores.setBounds(58, 250, 94, 20);
		return lblProveedores;
	}
	
	public JLabel getLblErrorCmbProveedor() {
		if (lblErrorCmbProveedor == null) {
			lblErrorCmbProveedor = new JLabel("");
			lblErrorCmbProveedor.setBounds(58, 275, 300, 14);
		}
		return lblErrorCmbProveedor;
	}

	public MitnickComboBoxModel<TipoDto> getModelCmbTipo() {
		if (modelCmbTipo == null) {
			modelCmbTipo = new MitnickComboBoxModel<TipoDto>();
			modelCmbTipo.addItems(controller.obtenerTipos());
		}
		return modelCmbTipo;
	}
	
	public MitnickComboBoxModel<MarcaDto> getModelCmbMarca() {
		if (modelCmbMarca == null) {
			modelCmbMarca = new MitnickComboBoxModel<MarcaDto>();
			modelCmbMarca.addItems(controller.obtenerMarcas());
		}
		return modelCmbMarca;
	}
	
	public MitnickComboBoxModel<ProveedorDto> getModelCmbProveedores() {
		if (modelCmbProveedor == null) {
			modelCmbProveedor = new MitnickComboBoxModel<ProveedorDto>();
			modelCmbProveedor.addItems(proveedorController.obtenerProveedores());
		}
		return modelCmbProveedor;
	}

	public JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("productoNuevoPanel.boton.aceptar"));
			// btnAceptar.setToolTipText(PropertiesManager.getProperty("productoNuevoPanel.tooltip.aceptar"));

			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));

			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agregarProducto();
				}
			});
			btnAceptar.setBounds(548, 56, 60, 60);
		}
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton(PropertiesManager.getProperty("productoNuevoPanel.boton.cancelar"));

			// btnAceptar.setToolTipText(PropertiesManager.getProperty("productoNuevoPanel.tooltip.cancelar"));

			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCancelar.setMargin(new Insets(-1, -1, -1, -1));

			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					volverPanelAnterior();
				}
			});
			btnCancelar.setBounds(618, 56, 60, 60);
		}
		return btnCancelar;
	}

	protected void volverPanelAnterior() {
		limpiarCamposPantalla();
		controller.mostrarProductosPanel();
	}
	
	@Override
	protected void keyEscape() {
		volverPanelAnterior();
	}

	public JTextField getTxtCodigo() {
		if (txtCodigo == null) {
			txtCodigo = new JTextField();
			txtCodigo.setBounds(162, 25, 102, 20);
			txtCodigo.setColumns(10);
		}
		return txtCodigo;
	}

	public JTextField getTxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new JTextField();
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(162, 70, 352, 20);
		}
		return txtDescripcion;
	}

	public JTextField getTxtPrecioVenta() {
		if (txtPrecioVenta == null) {
			txtPrecioVenta = new JTextField();
			txtPrecioVenta.setColumns(10);
			txtPrecioVenta.setBounds(162, 160, 102, 20);
		}
		return txtPrecioVenta;
	}
	
	public JTextField getTxtPrecioCompra() {
		if (txtPrecioCompra == null) {
			txtPrecioCompra = new JTextField();
			txtPrecioCompra.setColumns(10);
			txtPrecioCompra.setBounds(162, 205, 102, 20);
		}
		return txtPrecioCompra;
	}

	public JTextField getTxtStock() {
		if (txtStock == null) {
			txtStock = new JTextField();
			txtStock.setColumns(10);
			txtStock.setBounds(412, 160, 102, 20);
		}
		return txtStock;
	}
	
	public JTextField getTxtStockMinimo() {
		if (txtStockMinimo == null) {
			txtStockMinimo = new JTextField();
			txtStockMinimo.setColumns(10);
			txtStockMinimo.setBounds(412, 205, 102, 20);
		}
		return txtStockMinimo;
	}
	
	public JTextField getTxtStockCompra() {
		if (txtStockCompra == null) {
			txtStockCompra = new JTextField();
			txtStockCompra.setColumns(10);
			txtStockCompra.setBounds(412, 250, 102, 20);
		}
		return txtStockCompra;
	}
	
	public JTextField getTxtTalle() {
		if (txtTalle == null) {
			txtTalle = new JTextField();
			txtTalle.setColumns(10);
			txtTalle.setBounds(412, 295, 102, 20);
		}
		return txtTalle;
	}

	public JComboBox<TipoDto> getCmbTipo() {
		if (cmbTipo == null) {
			cmbTipo = new JComboBox<TipoDto>(getModelCmbTipo());
			cmbTipo.setBounds(162, 115, 102, 20);
		}
		return cmbTipo;
	}

	public JComboBox<MarcaDto> getCmbMarca() {
		if (cmbMarca == null) {
			cmbMarca = new JComboBox<MarcaDto>(getModelCmbMarca());
			cmbMarca.setBounds(412, 115, 102, 20);
		}
		return cmbMarca;
	}
	
	public JComboBox<ProveedorDto> getCmbProveedores() {
		if (cmbProveedor == null) {
			cmbProveedor = new JComboBox<ProveedorDto>(getModelCmbProveedores());
			cmbProveedor.setBounds(162, 250, 102, 20);
		}
		return cmbProveedor;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtCodigo, txtDescripcion, cmbTipo, cmbMarca,	txtPrecioVenta, txtPrecioCompra, cmbProveedor,
				txtStock, txtStockMinimo, txtStockCompra, txtTalle, btnAceptar, btnCancelar }));
	}

	protected void agregarProducto() {
		try {
			controller.guardarProducto(producto, getTxtCodigo().getText(), getTxtDescripcion().getText(),
					(TipoDto) getCmbTipo().getSelectedItem(), (MarcaDto) getCmbMarca().getSelectedItem(), getTxtStock().getText(),
					getTxtStockMinimo().getText(), getTxtStockCompra().getText(), getTxtPrecioVenta().getText(),
					getTxtPrecioCompra().getText(), (ProveedorDto)getCmbProveedores().getSelectedItem(), isConfirmado(), getTxtTalle().getText());
			limpiarCamposPantalla();
			controller.mostrarProductosPanel();
		} catch (PresentationException ex) {
			if (ex.getMessage().equals("producto.edit.max.cantidad")){
				int opcion = mostrarMensajeConsulta(
						PropertiesManager.getProperty("productoPanel.dialog.confirm.edit"));
				if (opcion == JOptionPane.YES_OPTION) {
					setConfirmado(true);
					agregarProducto();
				}
			} else
				mostrarMensaje(ex);
		}
	}

	public void setProducto(ProductoNuevoDto productoDto) {
		this.producto = productoDto;
	}

	@Override
	public void actualizarPantalla() {
		getTxtCodigo().requestFocus();

		if (Validator.isNotNull(producto)) {
			getTxtCodigo().setText(producto.getCodigo());
			getTxtDescripcion().setText(producto.getDescripcion());
			
			BigDecimal precioFinal = new BigDecimal(producto.getPrecioVenta());
			BigDecimal imp = new BigDecimal(producto.getIva());
			precioFinal = precioFinal.add(imp);
			getTxtPrecioVenta().setText(precioFinal.toString());
			getTxtPrecioCompra().setText(producto.getPrecioCompra());
			getTxtStock().setText(producto.getStock());
			getTxtStockMinimo().setText(producto.getStockMinimo());
			getTxtStockCompra().setText(producto.getStockCompra());
			getTxtTalle().setText(producto.getTalle());
			if (getCmbMarca().getModel().getSize() > 0)
				getCmbMarca().setSelectedItem(producto.getMarca());
			if (getCmbTipo().getModel().getSize() > 0)
				getCmbTipo().setSelectedItem(producto.getTipo());
			if (getCmbProveedores().getModel().getSize() > 0)
				getCmbProveedores().setSelectedItem(producto.getProveedor());
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

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
}
