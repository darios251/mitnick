package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.modelos.ProductoTableModel;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("productoPanel")
public class ProductoPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProductoController productoController;
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JComboBox<TipoDto> cbxTipo;
	private JComboBox<MarcaDto> cbxMarca;
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
	
	public ProductoPanel() {
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
		
		cbxTipo = new JComboBox<TipoDto>();
		cbxTipo.setBounds(200, 55, 110, 20);
		add(cbxTipo);
		
		cbxMarca = new JComboBox<MarcaDto>();
		cbxMarca.setBounds(420, 55, 110, 20);
		add(cbxMarca);
		
		lblMarca = new JLabel(PropertiesManager.getProperty("productoPanel.label.marca"));
		lblMarca.setBounds(330, 55, 60, 20);
		add(lblMarca);
		
		// Creo una tabla con un sorter
		model = new ProductoTableModel();
		model.setProductos(getProductos());
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
			public void actionPerformed(ActionEvent e) {
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

	private List<ProductoDto> getProductos() {
		List<ProductoDto> productos = new ArrayList<ProductoDto>();
		
		ProductoDto p1 = new ProductoDto();
		TipoDto tipo1 = new TipoDto();
		MarcaDto marca1 = new MarcaDto();
		
		p1.setCodigo("1");
		p1.setDescripcion("Pantalon de vestir");
		tipo1.setDescripcion("Pantalon");
		p1.setTipo(tipo1);
		p1.setTalle("L");
		marca1.setDescripcion("Wrangler");
		p1.setMarca(marca1);
		p1.setPrecio(new BigDecimal(120.50));
		
		productos.add(p1);
		
		ProductoDto p2 = new ProductoDto();
		TipoDto tipo2 = new TipoDto();
		MarcaDto marca2 = new MarcaDto();
		
		p2.setCodigo("2");
		p2.setDescripcion("Camisa manga larga");
		tipo2.setDescripcion("Camisa");
		p2.setTipo(tipo2);
		p2.setTalle("XL");
		marca2.setDescripcion("Polo");
		p2.setMarca(marca2);
		p2.setPrecio(new BigDecimal(80.00));
		
		productos.add(p2);
		
		return productos;
	}

	@Override
	protected void limpiarCamposPantalla() {
		
	}

	@Override
	protected void initializeComponents() {
		
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
}
