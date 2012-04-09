package com.mitnick.presentacion.vistas.paneles;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.presentacion.modelos.VentaTableModel;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoVentaDto;

@Panel("ventaPanel")
public class VentaPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VentaController ventaController;
	
	private JScrollPane scrollPane;
	private JLabel lblTotal;
	private JButton btnPagos;
	private JLabel lblVenta;
	private JButton btnQuitar;
	private JButton btnBuscar;
	private JLabel lblCdigo;
	
	private JTable table;
	private JTextField txtCodigo;

	private VentaTableModel model;

	private JLabel lblTotalValor;

	private JButton btnAceptar;
	private JLabel lblSutotal;
	private JLabel lblSubtotalValor;
	
	public VentaPanel() {
		setLayout(null);
		setSize(new Dimension(815, 470));
	}

	@Override
	public void limpiarCamposPantalla() {
		if(Validator.isNotNull(txtCodigo))
			txtCodigo.setText("");
		if(Validator.isNotNull(getModel()))
			getModel().setProductosVenta(new ArrayList<ProductoVentaDto>());
	}

	@Override
	protected void initializeComponents() {
		model = new VentaTableModel();
		
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evento) {
				if (evento.getClickCount() == 2) {
				      try {
							int index = table.getSelectedRow();
							ProductoVentaDto productoVentaDto = model.getProductosVenta(index);
							
							logger.info("Abrir panel para editar el articulo con codigo " + productoVentaDto.getProducto().getCodigo());
						}
						catch (PresentationException exception) {
							mostrarMensaje(exception);
//							if(model.getRowCount() == 0) {
//								JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.emptyModel"));
//							}
//							else {
//								JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.noRowSelected"));
//							}
						}
				    }
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		lblCdigo = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.codigo"));
		lblCdigo.setBounds(125, 35, 70, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					agregarProducto();
				}
			}
		});
		txtCodigo.setBounds(200, 35, 110, 20);
		txtCodigo.setColumns(10);
		add(txtCodigo);
		
		btnBuscar = new JButton(PropertiesManager.getProperty("ventaPanel.button.buscar"));
		btnBuscar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.buscar"));
		
		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));
		btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventaController.mostrarBuscarArticuloPanel();
			}
		});
		btnBuscar.setBounds(570, 15, 60, 60);
		add(btnBuscar);
		
		btnQuitar = new JButton(PropertiesManager.getProperty("ventaPanel.button.quitar"));
		btnQuitar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.quitar"));
		
		btnQuitar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
		btnQuitar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnQuitar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnQuitar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				logger.info("Quitando producto ... ");

				try {
					int index = table.getSelectedRow();
					ProductoVentaDto productoVentaDto = model.getProductosVenta(index);
					
					int opcion = mostrarMensajeAdvertencia(PropertiesManager.getProperty("ventaPanel.dialog.confirm.quitar"));
					
					if ( opcion == JOptionPane.YES_OPTION) {
						ventaController.quitarProductoVentaDto(productoVentaDto);	
					}
				}
				catch (IndexOutOfBoundsException exception) {
					if(model.getRowCount() == 0) {
						mostrarMensajeError(PropertiesManager.getProperty("ventaPanel.dialog.warning.emptyModel"));
					}
					else {
						mostrarMensajeError(PropertiesManager.getProperty("ventaPanel.dialog.warning.noRowSelected"));
					}
				}
				catch (PresentationException ex) {
					mostrarMensaje(ex);
				}
			}
		});
		btnQuitar.setBounds(735, 115, 60, 60);
		add(btnQuitar);
		
		lblVenta = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.venta"));
		lblVenta.setBounds(25, 90, 46, 20);
		add(lblVenta);
		
		btnPagos = new JButton(PropertiesManager.getProperty("ventaPanel.button.pagos"));
		btnPagos.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.pagos"));
		
		btnPagos.setIcon(new ImageIcon(this.getClass().getResource("/img/cobrar.png")));
		btnPagos.setHorizontalTextPosition( SwingConstants.CENTER );
		btnPagos.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnPagos.setMargin(new Insets(-1, -1, -1, -1));
		
		btnPagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ventaController.mostrarPagosPanel();
				}
				catch(PresentationException ex) {
					mostrarMensaje(ex);
				}
			}
		});
		btnPagos.setBounds(735, 185, 60, 60);
		add(btnPagos);
		
		lblSutotal = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.subtotal"));
		lblSutotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSutotal.setBounds(279, 441, 88, 20);
		add(lblSutotal);
		
		lblSubtotalValor = new JLabel("<< subtotal >>");
		lblSubtotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubtotalValor.setBounds(377, 441, 88, 20);
		add(lblSubtotalValor);
		
		lblTotal = new JLabel(PropertiesManager.getProperty("ventaPanel.etiqueta.total"));
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(475, 441, 88, 20);
		add(lblTotal);		
		
		lblTotalValor = new JLabel("<< total >>");
		lblTotalValor.setBounds(625, 441, 88, 20);
		add(lblTotalValor);
		
		btnAceptar = new JButton(PropertiesManager.getProperty("ventaPanel.button.aceptar"));
		btnAceptar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.aceptar"));
		
		btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
		btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarProducto();
			}
		});
		btnAceptar.setBounds(500, 15, 60, 60);
		add(btnAceptar);
	}

	public void agregarProducto() {
		logger.debug("entrado a agregarProducto");
		try {
			ventaController.agregarProducto(txtCodigo.getText());
			txtCodigo.setText("");
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
		logger.debug("saliendo de agregarProducto");
	}
	
	public void actualizarPantalla() {
		if(Validator.isNotNull(getModel()))
			getModel().setProductosVenta(VentaManager.getVentaActual().getProductos());
		if(Validator.isNotNull(lblTotalValor))
			lblTotalValor.setText(VentaManager.getVentaActual().getTotal().toEngineeringString());
		if(Validator.isNotNull(lblSubtotalValor))
			lblSubtotalValor.setText(VentaManager.getVentaActual().getTotal().toEngineeringString());
	}
	
	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}

	public VentaTableModel getModel() {
		return this.model;
	}
}
