package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.modelos.ProductoTableModel;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("productoPanel")
public class ProductoPanel extends BasePanel<ProductoController> {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;
	private JTable table;
	private TableRowSorter<ProductoTableModel> sorter;
	private ProductoTableModel model;
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	
	private JComboBox<TipoDto> cmbTipo;
	private JComboBox<MarcaDto> cmbMarca;
	
	private MitnickComboBoxModel<TipoDto> modelCmbTipo;
	private MitnickComboBoxModel<MarcaDto> modelCmbMarca;
	
	private JLabel lblMarca;
	private JLabel lblTipo;
	private JLabel lblDescripcion;
	private JLabel lblCdigo;
	private JLabel lblArtculos;
	private JLabel lblProductos;
	
	private JButton btnBuscar;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnEliminar;


	private JLabel lblNuevoPrecio;
	private JTextField txtNuevoPrecio;
	private JButton btnCambiarPrecio;
	
	@Autowired
	public ProductoPanel(@Qualifier("productoController") ProductoController productoController) {
		controller = productoController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ProductoPanel(boolean modoDisenio) {
		initializeComponents();
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		try {
			getCmbMarca().setSelectedIndex(0);
			getCmbTipo().setSelectedIndex(0);
		} catch (Exception e) {
		}
	}

	@Override
	protected void initializeComponents() {

		setLayout(null);
		setSize(new Dimension(815, 500));
		
		add(getLblCdigo());
		add(getTxtCodigo());
		add(getLblDescripcion());
		add(getTxtDescripcion());
		add(getLblTipo());
		add(getCmbTipo());
		add(getCmbMarca());
		add(getLblMarca());
		add(getScrollPane());
		add(getBtnBuscar());
		add(getBtnAgregar());
		add(getBtnEditar());
		add(getBtnEliminar());
		add(getLblArtculos());
		add(getLblProductos());
		
		add(getLblNuevoPrecio());
		add(getTxtNuevoPrecio());
		add(getBtnCambiarPrecio());
		
		setFocusTraversalPolicy();
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{txtCodigo, txtDescripcion, cmbTipo, cmbMarca, btnBuscar, table , btnAgregar, btnEditar, btnEliminar}));
	}
	
	protected void consultarProductos() {
		try {
			ConsultaProductoDto dto = new ConsultaProductoDto();
			dto.setCodigo(getTxtCodigo().getText());
			dto.setDescripcion(getTxtDescripcion().getText());
			dto.setMarca((MarcaDto) getCmbMarca().getSelectedItem());
			dto.setTipo((TipoDto) getCmbTipo().getSelectedItem());

			getTableModel().setProductos(controller.getProductosByFilter(dto));
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
			getTableModel().setProductos(new ArrayList<ProductoDto>());
		}
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 115, 700, 315);
		}
		return scrollPane;
	}

	public JTextField getTxtCodigo() {
		if (txtCodigo == null) {
			txtCodigo = new JTextField();
			txtCodigo.setColumns(10);
			txtCodigo.setBounds(200, 15, 110, 20);
		}
		return txtCodigo;
	}

	public JTextField getTxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new JTextField();
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(420, 15, 110, 20);
		}
		return txtDescripcion;
	}

	public JComboBox<TipoDto> createCmbTipo() {
		cmbTipo = new JComboBox<TipoDto>(getModelCmbTipo());
		cmbTipo.setBounds(200, 55, 110, 20);
		cmbTipo.insertItemAt(null, 0);
		return cmbTipo;
	}
	
	public JComboBox<TipoDto> getCmbTipo() {
		if (cmbTipo == null) {
			cmbTipo = new JComboBox<TipoDto>(getModelCmbTipo());
			cmbTipo.setBounds(200, 55, 110, 20);
			cmbTipo.insertItemAt(null, 0);
		}
		return cmbTipo;
	}

	public JComboBox<MarcaDto> createCmbMarca() {
		cmbMarca = new JComboBox<MarcaDto>(getModelCmbMarca());
		cmbMarca.setBounds(420, 55, 110, 20);
		cmbMarca.insertItemAt(null, 0);
		return cmbMarca;
	}
	
	public JComboBox<MarcaDto> getCmbMarca() {
		if (cmbMarca == null) {
			cmbMarca = new JComboBox<MarcaDto>(getModelCmbMarca());
			cmbMarca.setBounds(420, 55, 110, 20);
			cmbMarca.insertItemAt(null, 0);
		}
		return cmbMarca;
	}
	
	public MitnickComboBoxModel<TipoDto> getModelCmbTipo() {
		if (modelCmbTipo == null) {
			modelCmbTipo = new MitnickComboBoxModel<TipoDto>();			
		}
		modelCmbTipo.removeAllElements();
		modelCmbTipo.addItems(controller.obtenerTipos());
		return modelCmbTipo;
	}
	
	public MitnickComboBoxModel<MarcaDto> getModelCmbMarca() {
		if (modelCmbMarca == null) {
			modelCmbMarca = new MitnickComboBoxModel<MarcaDto>();			
		}
		modelCmbMarca.removeAllElements();
		modelCmbMarca.addItems(controller.obtenerMarcas());
		return modelCmbMarca;
	}

	public JLabel getLblMarca() {
		if (lblMarca == null) {
			lblMarca = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.marca"));
			lblMarca.setBounds(330, 55, 80, 20);
		}
		return lblMarca;
	}

	public JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton(PropertiesManager.getProperty("productoPanel.button.buscar.texto"));
			btnBuscar.setToolTipText(PropertiesManager.getProperty("productoPanel.button.buscar.tooltip"));
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource(PropertiesManager.getProperty("/img/buscar.png"))));

			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(560, 15, 60, 60);
			
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarProductos();
					table.requestFocus();
				}
			});
		}
		return btnBuscar;
	}

	public JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.tipo"));
			lblTipo.setBounds(125, 55, 60, 20);
		}
		return lblTipo;
	}

	public JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.descripcion"));
			lblDescripcion.setBounds(330, 15, 80, 20);
		}
		return lblDescripcion;
	}

	public JLabel getLblCdigo() {
		if (lblCdigo == null) {
			lblCdigo = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.codigo"));
			lblCdigo.setBounds(125, 15, 60, 20);
		}
		return lblCdigo;
	}

	public JButton getBtnAgregar() {
		if (btnAgregar == null) {
			
			btnAgregar = new JButton(
					PropertiesManager.getProperty("productoPanel.button.agregar.texto"));
			btnAgregar.setToolTipText(
					PropertiesManager.getProperty("productoPanel.button.agregar.tooltip"));
			btnAgregar.setIcon(
					new ImageIcon(this.getClass().getResource("/img/agregar.png")));
			
			btnAgregar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAgregar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
			btnAgregar.setBounds(735, 115, 60, 60);
			
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.altaProducto();
				}
			});
		}
		return btnAgregar;
	}

	public JButton getBtnEditar() {
		if (btnEditar == null) {
			
			btnEditar = new JButton(
					PropertiesManager.getProperty("productoPanel.button.editar.texto"));
			btnEditar.setToolTipText(
					PropertiesManager.getProperty("productoPanel.button.editar.tooltip"));
			btnEditar.setIcon(
					new ImageIcon(this.getClass().getResource("/img/editar.png")));
			
			btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEditar.setMargin(new Insets(-1, -1, -1, -1));
			btnEditar.setBounds(735, 185, 60, 60);

			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.editarProducto();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnEditar;
	}

	public JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton(
					PropertiesManager.getProperty("productoPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(
					PropertiesManager.getProperty("productoPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(
					new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			
			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 255, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(
							PropertiesManager.getProperty("productoPanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							controller.eliminarProducto();
							actualizarPantalla();
						} catch (PresentationException ex) {
							mostrarMensaje(ex);
						}
					}
				}
			});
		}
		return btnEliminar;
	}

	public JLabel getLblArtculos() {
		if (lblArtculos == null) {
			lblArtculos = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.productos"));
			lblArtculos.setBounds(163, 132, 65, 14);
		}
		return lblArtculos;
	}
	
	public JLabel getLblProductos() {
		if (lblProductos == null) {
			lblProductos = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.productos"));
			lblProductos.setBounds(25, 90, 70, 20);
		}
		return lblProductos;
	}

	
	public JTable getTable() {
		if(table == null) {
			table = new JTable(getTableModel());
			table.setRowSorter(getTableSorter());
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			// table.setFillsViewportHeight(true);
		}
		return table;
	}

	public ProductoTableModel getTableModel() {
		if(model == null) {
			model = new ProductoTableModel();
		}
		return model;
	}
	
	public TableRowSorter<ProductoTableModel> getTableSorter() {
		if(sorter == null) {
			sorter = new TableRowSorter<ProductoTableModel>(getTableModel());
		}
		return sorter;
	}

	@Override
	public void actualizarPantalla() {
		limpiarCamposPantalla();
		
		getCmbMarca();
		remove(getCmbMarca());
		add(createCmbMarca());
		
		getCmbTipo();
		remove(getCmbTipo());
		add(createCmbTipo());
		
		getCmbMarca().setSelectedItem(null);
		getCmbTipo().setSelectedItem(null);

		consultarProductos();
	}
	
	@Override
	protected void keyAdd() {
		btnAgregar.doClick();
	}
	
	@Override
	protected void keySubstract() {
		btnEliminar.doClick();
	}
	
	@Override
	protected void keyMultiply() {
		btnEditar.doClick();
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtCodigo();
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnBuscar());
	}
	
	public JLabel getLblNuevoPrecio() {
		if (lblNuevoPrecio == null) {
			lblNuevoPrecio = new JLabel("Nuevo Precio:");
			lblNuevoPrecio.setBounds(25, 450, 70, 20);
		}
		return lblNuevoPrecio;
	}
	
	public JTextField getTxtNuevoPrecio() {
		if (txtNuevoPrecio == null) {
			txtNuevoPrecio = new JTextField();
			txtNuevoPrecio.setColumns(10);
			txtNuevoPrecio.setBounds(150, 450, 50, 20);
		}
		return txtNuevoPrecio;
	}
	
	public JButton getBtnCambiarPrecio() {
		
		if (btnCambiarPrecio == null) {
			btnCambiarPrecio = new JButton("Cambiar Precio");
			btnCambiarPrecio.setToolTipText("Cambiar el precio de los productos que cumplen con el criterio de búsqueda");
			
			btnCambiarPrecio.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCambiarPrecio.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCambiarPrecio.setMargin(new Insets(-1, -1, -1, -1));
			btnCambiarPrecio.setBounds(250, 450, 140, 40);

			btnCambiarPrecio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (Validator.isNumeric(getTxtNuevoPrecio().getText())){
						String precio = getTxtNuevoPrecio().getText();
						precio = precio.replaceAll(",", ".");
						consultarProductos();					
						int opcion = mostrarMensajeConsulta("Está seguro que desea modificar el precio de todos los productos de la lista? Revise la lista.");

						if (opcion == JOptionPane.YES_OPTION) {
							try {
								for (ProductoDto producto: getTableModel().getProductos()){
									String precioCompra = "";
									if (Validator.isNotNull(producto.getPrecioCompra()))
										precioCompra = producto.getPrecioCompra().toString();
									ProductoNuevoDto prod = new ProductoNuevoDto();
									prod.setId(producto.getId());
									controller.guardarProducto(prod, producto.getCodigo(),producto.getDescripcion(), producto.getTipo(), producto.getMarca(), String.valueOf(producto.getStock()), String.valueOf(producto.getStockMinimo()), String.valueOf(producto.getStockCompra()), precio, precioCompra, producto.getProveedor(), true, producto.getTalle());
									consultarProductos();	
									getTxtNuevoPrecio().setText("");
								}
							} catch (PresentationException ex) {
								mostrarMensaje(ex);
							}
						}
					} else {
						mostrarMensajeError("El nuevo precio es requerido. Verifique que sea una valor correcto.");
					}
				}
			});
		}
		return btnCambiarPrecio;
	}
}
