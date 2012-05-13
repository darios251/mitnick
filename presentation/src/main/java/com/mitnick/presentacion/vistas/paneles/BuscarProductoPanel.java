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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.modelos.ProductoTableModel;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoDto;

@Panel("buscarProductoPanel")
public class BuscarProductoPanel extends BasePanel {
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
		
		add(getScrollPane());
		
		add(getLblDescripcion());
		add(getLblCodigo());
		add(getLblProductos());
		
		add(getTxtCodigo());
		add(getTxtDescripcion());
		
		add(getBtnBuscar());
		add(getBtnVolver());
		add(getBtnAgregar());
		
		setFocusTraversalPolicy();
	}
	
	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 115, 700, 315);
		}
		return scrollPane;
	}

	public Component getLblCodigo() {
		if(lblCodigo == null) {
			lblCodigo = new JLabel(PropertiesManager.getProperty("buscarProductoPanel.etiqueta.codigo"));
			lblCodigo.setBounds(125, 35, 70, 20);
		}
		return lblCodigo;
	}

	public JLabel getLblDescripcion() {
		if(lblDescripcion == null) {
			lblDescripcion = new JLabel(PropertiesManager.getProperty("buscarProductoPanel.etiqueta.descripcion"));
			lblDescripcion.setBounds(330, 35, 70, 20);
		}
		return lblDescripcion;
	}

	public JButton getBtnBuscar() {
		if(btnBuscar == null){
			btnBuscar = new JButton(PropertiesManager.getProperty("buscarProductoPanel.boton.buscar"));
			btnBuscar.setToolTipText(PropertiesManager.getProperty("buscarProductoPanel.tooltip.buscar"));

			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));
			btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						buscarProducto();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnBuscar.setBounds(570, 15, 60, 60);
		}
			
		return btnBuscar;
	}

	public JButton getBtnVolver() {
		if(btnVolver == null) {
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
		}
		return btnVolver;
	}

	public JButton getBtnAgregar() {
		if(btnAgregar == null) {
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
		}
		return btnAgregar;
	}

	public JTable getTable() {
		if(table == null) {
			table = new JTable(getModel());
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
//	        table.setFillsViewportHeight(true);
		}
		return table;
	}

	public JTextField getTxtCodigo() {
		if(txtCodigo == null) {
			txtCodigo = new JTextField();
//			txtCodigo.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					try {
//						buscarProducto();
//					}
//					catch(PresentationException ex) {
//						mostrarMensaje(ex);
//					}
//				}
//			});
			txtCodigo.setColumns(10);
			txtCodigo.setBounds(200, 35, 110, 20);
		}
		return txtCodigo;
	}

	public JTextField getTxtDescripcion() {
		if(txtDescripcion == null) {
			txtDescripcion = new JTextField();
//			txtDescripcion.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					try {
//						buscarProducto();
//					}
//					catch(PresentationException ex) {
//						mostrarMensaje(ex);
//					}
//				}
//			});
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(420, 35, 110, 20);
		}
		return txtDescripcion;
	}

	public JLabel getLblProductos() {
		if(lblProductos == null) {
			lblProductos = new JLabel(PropertiesManager.getProperty("buscarProductoPanel.etiqueta.productos"));
			lblProductos.setBounds(25, 90, 70, 20);
		}
		return lblProductos;
	}

	public ProductoTableModel getModel() {
		if(model == null) {
			model = new ProductoTableModel();
		}
		return model;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{txtCodigo, txtDescripcion}));
	}

	private void buscarProducto() {
		ConsultaProductoDto dto = new ConsultaProductoDto();
		dto.setCodigo(txtCodigo.getText());
		dto.setDescripcion(txtDescripcion.getText());
	
		model.setProductos(	productoController.getProductosByFilter(dto) );
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
	
	@Override
	public void actualizarPantalla() {
		
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtCodigo;
	}

	public void setVentasController(VentaController ventasController) {
		this.ventasController = ventasController;
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}
	protected void setDefaultButton() {
		this.getRootPane().setDefaultButton(getBtnBuscar());
	}
}