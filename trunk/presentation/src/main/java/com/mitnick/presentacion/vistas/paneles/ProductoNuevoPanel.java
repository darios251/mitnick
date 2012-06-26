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
public class ProductoNuevoPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	private ProductoController productoController;
	private ProveedorController proveedorController;

	private ProductoNuevoDto producto;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JTextField txtPrecioVenta;
	private JTextField txtStock;

	private JComboBox<TipoDto> cmbTipo;
	private MitnickComboBoxModel<TipoDto> modelCmbTipo;
	private JComboBox<MarcaDto> cmbMarca;
	private MitnickComboBoxModel<MarcaDto> modelCmbMarca;

	private JLabel lblCodigo;
	private JLabel lblDescripcin;
	private JLabel lblTipo;
	private JLabel lblMarca;
	private JLabel lblPrecioVenta;
	private JLabel lblPrecioCompra;
	private JLabel lblStock;
	private JTextField txtPrecioCompra;
	private JLabel lblStockMinimo;
	private JTextField txtStockMinimo;
	private JLabel lblStockCompra;
	private JTextField txtStockCompra;
	private JLabel lblProveedores;
	private JComboBox<ProveedorDto> cmbProveedor;
	private MitnickComboBoxModel<ProveedorDto> modelCmbProveedor;
	
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
		this.productoController = productoController;
		this.proveedorController = proveedorController;
	}

	@Override
	protected void limpiarCamposPantalla() {
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
		add(getLblDescripcin());
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

	public JLabel getLblDescripcin() {
		if (lblDescripcin == null) {
			lblDescripcin = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.descripcion"));
			lblDescripcin.setBounds(58, 56, 94, 20);
		}
		return lblDescripcin;
	}

	public JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.tipo"));
			lblTipo.setBounds(58, 87, 94, 20);
		}
		return lblTipo;
	}

	public JLabel getLblMarca() {
		if (lblMarca == null) {
			lblMarca = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.marca"));
			lblMarca.setBounds(319, 87, 94, 20);
		}
		return lblMarca;
	}

	public JLabel getLblPrecioVenta() {
		if (lblPrecioVenta == null) {
			lblPrecioVenta = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.precioVenta"));
			lblPrecioVenta.setBounds(58, 118, 94, 20);
		}
		return lblPrecioVenta;
	}
	
	public JLabel getLblPrecioCompra() {
		if (lblPrecioCompra == null) {
			lblPrecioCompra = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.precioCompra"));
			lblPrecioCompra.setBounds(58, 149, 94, 20);
		}
		return lblPrecioCompra;
	}

	public JLabel getLblStock() {
		if (lblStock == null) {
			lblStock = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stock"));
			lblStock.setBounds(319, 118, 94, 20);
		}
		return lblStock;
	}
	
	public JLabel getLblStockMinimo() {
		if (lblStockMinimo == null) {
			lblStockMinimo = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stockMinimo"));
			lblStockMinimo.setBounds(319, 149, 94, 20);
		}
		return lblStockMinimo;
	}
	
	public JLabel getLblStockCompra() {
		if (lblStockCompra == null) {
			lblStockCompra = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.stockCompra"));
			lblStockCompra.setBounds(319, 180, 94, 20);
		}
		return lblStockCompra;
	}
	
	public JLabel getLblProveedores() {
		lblProveedores = new JLabel(PropertiesManager.getProperty("productoNuevoPanel.etiqueta.proveedores"));
		lblProveedores.setBounds(58, 180, 94, 20);
		return lblProveedores;
	}

	public MitnickComboBoxModel<TipoDto> getModelCmbTipo() {
		if (modelCmbTipo == null) {
			modelCmbTipo = new MitnickComboBoxModel<TipoDto>();
			modelCmbTipo.addItems(productoController.obtenerTipos());
		}
		return modelCmbTipo;
	}

	public MitnickComboBoxModel<MarcaDto> getModelCmbMarca() {
		if (modelCmbMarca == null) {
			modelCmbMarca = new MitnickComboBoxModel<MarcaDto>();
			modelCmbMarca.addItems(productoController.obtenerMarcas());
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
					limpiarCamposPantalla();
					productoController.mostrarProductosPanel();
				}
			});
			btnCancelar.setBounds(618, 56, 60, 60);
		}
		return btnCancelar;
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
			txtDescripcion.setBounds(162, 56, 352, 20);
		}
		return txtDescripcion;
	}

	public JTextField getTxtPrecioVenta() {
		if (txtPrecioVenta == null) {
			txtPrecioVenta = new JTextField();
			txtPrecioVenta.setColumns(10);
			txtPrecioVenta.setBounds(162, 118, 102, 20);
		}
		return txtPrecioVenta;
	}
	
	public JTextField getTxtPrecioCompra() {
		if (txtPrecioCompra == null) {
			txtPrecioCompra = new JTextField();
			txtPrecioCompra.setColumns(10);
			txtPrecioCompra.setBounds(162, 149, 102, 20);
		}
		return txtPrecioCompra;
	}

	public JTextField getTxtStock() {
		if (txtStock == null) {
			txtStock = new JTextField();
			txtStock.setColumns(10);
			txtStock.setBounds(412, 118, 102, 20);
		}
		return txtStock;
	}
	
	public JTextField getTxtStockMinimo() {
		if (txtStockMinimo == null) {
			txtStockMinimo = new JTextField();
			txtStockMinimo.setColumns(10);
			txtStockMinimo.setBounds(412, 149, 102, 20);
		}
		return txtStockMinimo;
	}
	
	public JTextField getTxtStockCompra() {
		if (txtStockCompra == null) {
			txtStockCompra = new JTextField();
			txtStockCompra.setColumns(10);
			txtStockCompra.setBounds(412, 180, 102, 20);
		}
		return txtStockCompra;
	}

	public JComboBox<TipoDto> getCmbTipo() {
		if (cmbTipo == null) {
			cmbTipo = new JComboBox<TipoDto>(getModelCmbTipo());
			cmbTipo.setBounds(162, 87, 102, 20);
		}
		return cmbTipo;
	}

	public JComboBox<MarcaDto> getCmbMarca() {
		if (cmbMarca == null) {
			cmbMarca = new JComboBox<MarcaDto>(getModelCmbMarca());
			cmbMarca.setBounds(412, 87, 102, 20);
		}
		return cmbMarca;
	}
	
	public JComboBox<ProveedorDto> getCmbProveedores() {
		if (cmbProveedor == null) {
			cmbProveedor = new JComboBox<ProveedorDto>(getModelCmbProveedores());
			cmbProveedor.setBounds(162, 180, 102, 20);
		}
		return cmbProveedor;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtCodigo, txtDescripcion, cmbTipo, cmbMarca,	txtPrecioVenta, txtPrecioCompra, cmbProveedor,
				txtStock, txtStockMinimo, txtStockCompra, btnAceptar, btnCancelar }));
	}

	protected void agregarProducto() {
		try {
			productoController.guardarProducto(producto, getTxtCodigo().getText(), getTxtDescripcion().getText(),
					(TipoDto) getCmbTipo().getSelectedItem(), (MarcaDto) getCmbMarca().getSelectedItem(), getTxtStock().getText(),
					getTxtStockMinimo().getText(), getTxtStockCompra().getText(), getTxtPrecioVenta().getText(),
					getTxtPrecioCompra().getText(), (ProveedorDto)getCmbProveedores().getSelectedItem());
			limpiarCamposPantalla();
			productoController.mostrarProductosPanel();
		} catch (PresentationException ex) {
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
			getTxtPrecioVenta().setText(producto.getPrecioVenta());
			getTxtPrecioCompra().setText(producto.getPrecioCompra());
			getTxtStock().setText(producto.getStock());
			getTxtStockMinimo().setText(producto.getStockMinimo());
			getTxtStockCompra().setText(producto.getStockCompra());
			if (getCmbMarca().getModel().getSize() > 0)
				getCmbMarca().setSelectedItem(producto.getMarca());
			if (getCmbTipo().getModel().getSize() > 0)
				getCmbMarca().setSelectedItem(producto.getTipo());
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
}
