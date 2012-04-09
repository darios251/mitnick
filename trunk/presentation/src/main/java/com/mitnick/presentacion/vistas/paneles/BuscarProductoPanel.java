package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.presentacion.modelos.ProductoTableModel;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoDto;

@Panel("buscarProductoPanel")
public class BuscarProductoPanel extends BaseView {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VentaController ventasController;
	
	@Autowired
	private ProductoController productoController;
	
	private JScrollPane scrollPane;
	private Component lblCodigo;
	private JLabel lblDescripcion;
	private JButton btnBuscar;
	private JButton btnVolver;
	private JButton btnAgregar;
	
	private JTable table;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JLabel lblProductos;

	private ProductoTableModel model;
	
	public BuscarProductoPanel() {
		setLayout(null);
		setSize(new Dimension(815, 470));
		
		// Creo una tabla con un sorter
		model = new ProductoTableModel();
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		lblCodigo = new JLabel(PropertiesManager.getProperty("buscarProductoPanel.etiqueta.codigo"));
		lblCodigo.setBounds(125, 35, 70, 20);
		add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(200, 35, 110, 20);
		add(txtCodigo);
		
		lblDescripcion = new JLabel(PropertiesManager.getProperty("buscarProductoPanel.etiqueta.descripcion"));
		lblDescripcion.setBounds(330, 35, 70, 20);
		add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(420, 35, 110, 20);
		add(txtDescripcion);
		
		btnBuscar = new JButton(PropertiesManager.getProperty("buscarProductoPanel.boton.buscar"));
		btnBuscar.setToolTipText(PropertiesManager.getProperty("buscarProductoPanel.tooltip.buscar"));

		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));
		btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultaProductoDto dto = new ConsultaProductoDto();
				dto.setCodigo(txtCodigo.getText());
				dto.setDescripcion(txtDescripcion.getText());

				try {
					model.setProductos(	productoController.getProductosByFilter(dto) );
				}
				catch(PresentationException ex) {
					mostrarMensaje(ex);
				}
			}
		});
		btnBuscar.setBounds(570, 15, 60, 60);
		add(btnBuscar);
		
		btnVolver = new JButton(PropertiesManager.getProperty("buscarProductoPanel.boton.volver"));
		btnVolver.setToolTipText(PropertiesManager.getProperty("buscarProductoPanel.tooltip.volver"));
		
		btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
		btnVolver.setHorizontalTextPosition( SwingConstants.CENTER );
		btnVolver.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnVolver.setMargin(new Insets(-1, -1, -1, -1));
		
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventasController.mostrarVentasPanel();
			}
		});
		btnVolver.setBounds(735, 185, 60, 60);
		add(btnVolver);

		btnAgregar = new JButton(PropertiesManager.getProperty("buscarProductoPanel.boton.agregar"));
		btnAgregar.setToolTipText(PropertiesManager.getProperty("buscarProductoPanel.tooltip.agregar"));
		
		btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
		btnAgregar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnAgregar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int index = table.getSelectedRow();
					ProductoDto productoDto = model.getProducto(index);
					
					ventasController.agregarProducto(productoDto.getCodigo());
					ventasController.mostrarVentasPanel();	
				}
				catch (IndexOutOfBoundsException exception) {
					if(model.getRowCount() == 0) {
						mostrarMensajeError("buscarProductoPanel.dialog.warning.emptyModel");
					}
					else {
						mostrarMensajeError("buscarProductoPanel.dialog.warning.noRowSelected");
					}
				}
			}
		});
		btnAgregar.setBounds(735, 115, 60, 60);
		add(btnAgregar);
		
		lblProductos = new JLabel(PropertiesManager.getProperty("buscarProductoPanel.etiqueta.productos"));
		lblProductos.setBounds(25, 90, 70, 20);
		add(lblProductos);
	}

	@Override
	public void limpiarCamposPantalla() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		model.setProductos(new ArrayList<ProductoDto>());
	}

	@Override
	protected void initializeComponents() {
		
	}

	public void setVentasController(VentaController ventasController) {
		this.ventasController = ventasController;
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
	
}