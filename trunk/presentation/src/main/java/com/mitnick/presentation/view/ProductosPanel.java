package com.mitnick.presentation.view;

import java.awt.Component;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableRowSorter;

import com.mitnick.presentation.model.ProductoTableModel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

public class ProductosPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;

	
	
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JComboBox cbxTipo;
	private JComboBox cbxMarca;
	private JLabel lblMarca;
	private JButton btnBuscar;
	private Component lblTipo;
	private JLabel lblDescripcin;
	private JLabel lblCdigo;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JLabel lblArtculos;
	private TableRowSorter<ProductoTableModel> sorter;
	private ProductoTableModel model;
	
	public ProductosPanel(String titulo) {
		setLayout(null);
		this.title = titulo;
		setSize(new Dimension(815, 470));
		
		lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(125, 15, 60, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(200, 15, 110, 20);
		add(txtCodigo);
		
		lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setBounds(330, 15, 60, 20);
		add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(420, 15, 110, 20);
		add(txtDescripcion);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(125, 55, 90, 20);
		add(lblTipo);
		
		cbxTipo = new JComboBox();
		cbxTipo.setBounds(200, 55, 110, 20);
		add(cbxTipo);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(570, 15, 60, 60);
		add(btnBuscar);
		
		cbxMarca = new JComboBox();
		cbxMarca.setBounds(420, 55, 110, 20);
		add(cbxMarca);
		
		lblMarca = new JLabel("Marca");
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
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(735, 115, 60, 60);
		add(btnNuevo);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(735, 255, 60, 60);
		add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(735, 185, 60, 60);
		add(btnEliminar);
		
		lblArtculos = new JLabel("Art\u00EDculos");
		lblArtculos.setBounds(163, 132, 65, 14);
		add(lblArtculos);
		
		JLabel lblProductos = new JLabel("Productos");
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
	public String getTitle() {
		return title;
	}

	@Override
	protected void limpiarCamposPantalla() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeComponents() {
		// TODO Auto-generated method stub
		
	}
}
