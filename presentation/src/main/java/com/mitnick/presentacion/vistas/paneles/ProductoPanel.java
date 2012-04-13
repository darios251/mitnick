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
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("productoPanel")
public class ProductoPanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	
	private ProductoController productoController;
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JComboBox<TipoDto> cmbTipo;
	private JComboBox<MarcaDto> cmbMarca;
	private JLabel lblMarca;
	private JButton btnBuscar;
	private Component lblTipo;
	private JLabel lblDescripcin;
	private JLabel lblCdigo;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JLabel lblArtculos;
	private TableRowSorter<ProductoTableModel> sorter;
	private ProductoTableModel model;
	private JButton btnMovimientos;
	
	@Autowired
	public ProductoPanel(@Qualifier ("productoController") ProductoController productoController) {
		this.productoController = productoController;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ProductoPanel(boolean modoDisenio) {
		initializeComponents();
	}

	

	@Override
	protected void limpiarCamposPantalla() {
		
	}

	@Override
	protected void initializeComponents() {

		setLayout(null);
		setSize(new Dimension(815, 470));
		
		lblCdigo = new JLabel(PropertiesManager.getProperty("productoPanel.label.codigo"));
		lblCdigo.setBounds(125, 15, 60, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(200, 15, 110, 20);
		add(txtCodigo);
		
		lblDescripcin = new JLabel(PropertiesManager.getProperty("productoPanel.label.descripcion"));
		lblDescripcin.setBounds(330, 15, 60, 20);
		add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(420, 15, 110, 20);
		add(txtDescripcion);
		
		lblTipo = new JLabel(PropertiesManager.getProperty("productoPanel.label.tipo"));
		lblTipo.setBounds(125, 55, 90, 20);
		add(lblTipo);
		
		MitnickComboBoxModel<TipoDto> modeloTipo = new MitnickComboBoxModel<TipoDto>();
		modeloTipo.addItems(productoController.obtenerTipos());
		cmbTipo = new JComboBox<TipoDto>(modeloTipo);
		cmbTipo.setBounds(200, 55, 110, 20);
		add(cmbTipo);
		
		MitnickComboBoxModel<MarcaDto> modeloMarca = new MitnickComboBoxModel<MarcaDto>();
		modeloMarca.addItems(productoController.obtenerMarcas());
		cmbMarca = new JComboBox<MarcaDto>(modeloMarca);
		cmbMarca.setBounds(420, 55, 110, 20);
		add(cmbMarca);
		
		lblMarca = new JLabel(PropertiesManager.getProperty("productoPanel.label.marca"));
		lblMarca.setBounds(330, 55, 60, 20);
		add(lblMarca);
		
		// Creo una tabla con un sorter
		model = new ProductoTableModel();
        sorter = new TableRowSorter<ProductoTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		btnBuscar = new JButton();
		btnBuscar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.buscarProducto"));
		
		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));
		btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				consultarProductos();
			}
		});
		btnBuscar.setBounds(560, 15, 60, 60);
		add(btnBuscar);
		
		btnAgregar = new JButton();
		btnAgregar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.agregarProducto"));
		
		btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
		btnAgregar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAgregar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productoController.mostrarProductoNuevoPanel();
			}
		});
		btnAgregar.setBounds(735, 115, 60, 60);
		add(btnAgregar);
		
		btnEditar = new JButton();
		btnEditar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.modificarProducto"));
		
		btnEditar.setIcon(new ImageIcon(this.getClass().getResource("/img/editar.png")));
		btnEditar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnEditar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnEditar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					productoController.editarProducto();
				}
				catch(PresentationException ex) {
					mostrarMensaje(ex);
				}
				actualizarPantalla();
			}
		});
		btnEditar.setBounds(735, 185, 60, 60);
		add(btnEditar);
		
		btnEliminar = new JButton();
		btnEliminar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.eliminarProducto"));
		
		btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
		btnEliminar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnEliminar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("productoPanel.dialog.confirm.eliminar"));
				
				if ( opcion == JOptionPane.YES_OPTION) {
					try {
						productoController.eliminarProducto();
						actualizarPantalla();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
	        }
		});
		btnEliminar.setBounds(735, 255, 60, 60);
		add(btnEliminar);
		
		btnMovimientos = new JButton();
		btnMovimientos.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.detallesMovimientos"));
		
		btnMovimientos.setIcon(new ImageIcon(this.getClass().getResource("/img/movimientos.png")));
		btnMovimientos.setHorizontalTextPosition( SwingConstants.CENTER );
		btnMovimientos.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnMovimientos.setMargin(new Insets(-1, -1, -1, -1));
		
		btnMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMovimientos.setBounds(735, 325, 60, 60);
		add(btnMovimientos);
		
		lblArtculos = new JLabel(PropertiesManager.getProperty("productoPanel.label.productos"));
		lblArtculos.setBounds(163, 132, 65, 14);
		add(lblArtculos);
		
		JLabel lblProductos = new JLabel(PropertiesManager.getProperty("productoPanel.label.productos"));
		lblProductos.setBounds(25, 90, 70, 20);
		add(lblProductos);
	
	}
	
	protected void consultarProductos() {
		try {
			ConsultaProductoDto dto = new ConsultaProductoDto();
			dto.setCodigo(txtCodigo.getText());
			dto.setDescripcion(txtDescripcion.getText());
			dto.setMarca((MarcaDto) cmbMarca.getSelectedItem());
			dto.setTipo((TipoDto) cmbTipo.getSelectedItem());
			
			model.setProductos(productoController.getProductosByFilter(dto));
		}
		catch (PresentationException ex) {
			mostrarMensaje(ex);
			model.setProductos(new ArrayList<ProductoDto>());
		}
	}

	public JTable getTable() {
		return table;
	}
	
	public ProductoTableModel getTableModel() {
		return model;
	}
	
	@Override
	public void actualizarPantalla() {
		consultarProductos();
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtCodigo; 
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
}
