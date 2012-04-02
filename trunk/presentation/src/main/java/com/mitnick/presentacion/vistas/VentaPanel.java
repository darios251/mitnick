package com.mitnick.presentacion.vistas;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import com.mitnick.presentacion.modelos.VentaTableModel;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProductoVentaDto;

@Panel("ventaPanel")
public class VentaPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VentaController ventaController;
	
	private JScrollPane scrollPane;
	private JLabel lblTotal;
	private JButton btnPagar;
	private JLabel lblVenta;
	private JButton btnQuitar;
	private JButton btnBuscar;
	private JLabel lblCdigo;
	
	private JTable table;
	private JTextField txtCodigo;

	private VentaTableModel model;
	
	public VentaPanel() {
		setLayout(null);
		setSize(new Dimension(815, 470));
	}

	@Override
	public void limpiarCamposPantalla() {
		txtCodigo.setText("");
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
						catch (IndexOutOfBoundsException exception) {
							if(model.getRowCount() == 0) {
								JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.emptyModel"));
							}
							else {
								JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.noRowSelected"));
							}
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
		
		lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setBounds(330, 35, 60, 20);
		add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					logger.info("Buscando producto ... ");
					if(txtCodigo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.emptyTextCode"));
					}
					else {
						ventaController.agregarProducto(txtCodigo.getText());
						txtCodigo.setText("");
					}
				}
			}
		});
		txtCodigo.setBounds(420, 35, 110, 20);
		txtCodigo.setColumns(10);
		add(txtCodigo);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setToolTipText("Buscar Producto");
		
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
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.setToolTipText("Quitar Producto");
		
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
					int opcion = JOptionPane.showConfirmDialog(scrollPane.getParent(), 
							PropertiesManager.getProperty("ventaPanel.dialog.confirm.quitar"), 
							PropertiesManager.getProperty("dialog.warning.titulo"), 
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
					
					if ( opcion == JOptionPane.YES_OPTION) {
						ventaController.quitarProductoVentaDto(productoVentaDto);	
					}
				}
				catch (IndexOutOfBoundsException exception) {
					if(model.getRowCount() == 0) {
						JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.emptyModel"));
					}
					else {
						JOptionPane.showMessageDialog(scrollPane.getParent(), PropertiesManager.getProperty("ventaPanel.dialog.warning.noRowSelected"));
					}
				}
			}
		});
		btnQuitar.setBounds(735, 115, 60, 60);
		add(btnQuitar);
		
		lblVenta = new JLabel("Venta");
		lblVenta.setBounds(25, 90, 46, 20);
		add(lblVenta);
		
		btnPagar = new JButton("Cobrar");
		btnPagar.setToolTipText("Cobrar");
		
		btnPagar.setIcon(new ImageIcon(this.getClass().getResource("/img/cobrar.png")));
		btnPagar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnPagar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnPagar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventaController.mostrarPagosPanel();
			}
		});
		btnPagar.setBounds(735, 185, 60, 60);
		add(btnPagar);
		
		lblTotal = new JLabel("Total: <<total>>");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(585, 439, 140, 20);
		add(lblTotal);		
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}

	public VentaTableModel getModel() {
		return this.model;
	}
}
