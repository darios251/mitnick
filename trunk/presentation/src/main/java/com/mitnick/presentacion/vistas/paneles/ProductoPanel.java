package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
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
		setSize(new Dimension(815, 470));
		
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

	public JComboBox<TipoDto> getCmbTipo() {
		if (cmbTipo == null) {
			MitnickComboBoxModel<TipoDto> modeloTipo = new MitnickComboBoxModel<TipoDto>();
			modeloTipo.addElement(MitnickConstants.tipoTodos);
			modeloTipo.addItems(controller.obtenerTipos());
			cmbTipo = new JComboBox<TipoDto>(modeloTipo);
			cmbTipo.setBounds(200, 55, 110, 20);
		}
		return cmbTipo;
	}

	public JComboBox<MarcaDto> getCmbMarca() {
		if (cmbMarca == null) {
			MitnickComboBoxModel<MarcaDto> modeloMarca = new MitnickComboBoxModel<MarcaDto>();
			modeloMarca.addElement(MitnickConstants.marcaTodos);
			modeloMarca.addItems(controller.obtenerMarcas());
			cmbMarca = new JComboBox<MarcaDto>(modeloMarca);
			cmbMarca.setBounds(420, 55, 110, 20);
		}
		return cmbMarca;
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
}
