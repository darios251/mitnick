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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ProveedorController;
import com.mitnick.presentacion.modelos.ProveedorTableModel;
import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ProveedorDto;

@Panel("proveedorPanel")
public class ProveedorPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	private ProveedorController proveedorController;

	private JScrollPane scrollPane;
	private JTable table;
	private TableRowSorter<ProveedorTableModel> sorter;
	private ProveedorTableModel model;
	
	private JTextField txtCodigo;
	private JTextField txtNombre;
	
	private JLabel lblDescripcion;
	private JLabel lblCdigo;
	private JLabel lblproveedores;
	
	private JButton btnBuscar;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnVerProductos;

	@Autowired
	public ProveedorPanel(@Qualifier("proveedorController") ProveedorController proveedorController) {
		this.proveedorController = proveedorController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ProveedorPanel(boolean modoDisenio) {
		initializeComponents();
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		try {
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
		add(getTxtNombre());
		add(getScrollPane());
		add(getBtnBuscar());
		add(getBtnAgregar());
		add(getBtnEditar());
		add(getBtnEliminar());
		add(getLblProveedores());
		add(getBtnVerProductos());
		
		setFocusTraversalPolicy();
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{txtCodigo, txtNombre, btnBuscar, btnAgregar, btnEditar, btnEliminar, btnVerProductos}));
	}
	
	protected void consultarProveedores() {
		try {
			ConsultaProveedorDto dto = new ConsultaProveedorDto();
			dto.setCodigo(txtCodigo.getText());
			dto.setNombre(txtNombre.getText());
			getTableModel().setProveedores(proveedorController.getProveedorsByFilter(dto));
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
			getTableModel().setProveedores(new ArrayList<ProveedorDto>());
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

	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(420, 15, 110, 20);
		}
		return txtNombre;
	}

	public JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton(PropertiesManager.getProperty("proveedorPanel.button.buscar.texto"));
			btnBuscar.setToolTipText(PropertiesManager.getProperty("proveedorPanel.button.buscar.tooltip"));
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource(PropertiesManager.getProperty("/img/buscar.png"))));

			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(560, 15, 60, 60);
			
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarProveedores();
				}
			});
		}
		return btnBuscar;
	}

	public JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel(PropertiesManager.getProperty("proveedorPanel.label.nombre"));
			lblDescripcion.setBounds(330, 15, 80, 20);
		}
		return lblDescripcion;
	}

	public JLabel getLblCdigo() {
		if (lblCdigo == null) {
			lblCdigo = new JLabel(PropertiesManager.getProperty("proveedorPanel.label.codigo"));
			lblCdigo.setBounds(125, 15, 60, 20);
		}
		return lblCdigo;
	}

	public JButton getBtnAgregar() {
		if (btnAgregar == null) {
			btnAgregar = new JButton(PropertiesManager.getProperty("proveedorPanel.button.agregar.texto"));
			btnAgregar.setToolTipText(PropertiesManager.getProperty("proveedorPanel.button.agregar.tooltip"));
			btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
			
			btnAgregar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAgregar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
			btnAgregar.setBounds(735, 115, 60, 60);
			
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					proveedorController.nuevoProveedor();
				}
			});
		}
		return btnAgregar;
	}

	public JButton getBtnEditar() {
		if (btnEditar == null) {
			
			btnEditar = new JButton(PropertiesManager.getProperty("proveedorPanel.button.editar.texto"));
			btnEditar.setToolTipText(PropertiesManager.getProperty("proveedorPanel.button.editar.tooltip"));
			btnEditar.setIcon(new ImageIcon(this.getClass().getResource("/img/editar.png")));
			
			btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEditar.setMargin(new Insets(-1, -1, -1, -1));
			btnEditar.setBounds(735, 185, 60, 60);

			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						proveedorController.editarProveedor();
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
			btnEliminar = new JButton(PropertiesManager.getProperty("proveedorPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(PropertiesManager.getProperty("proveedorPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			
			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 255, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("proveedorPanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							proveedorController.eliminarProveedor();
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
	
	public JButton getBtnVerProductos() {
		if(btnVerProductos == null) {
			btnVerProductos = new JButton(PropertiesManager.getProperty("proveedorPanel.button.verProductos.texto"));
			btnVerProductos.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVerProductos.setIcon(new ImageIcon(this.getClass().getResource("/img/verProductos.png")));
			btnVerProductos.setToolTipText(PropertiesManager.getProperty("proveedorPanel.button.verProductos.tooltip"));
			btnVerProductos.setMargin(new Insets(-1, -1, -1, -1));
			btnVerProductos.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVerProductos.setBounds(735, 324, 60, 60);
			
			btnVerProductos.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						proveedorController.verProductosProveedor();
					}catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVerProductos;
	}

	public JLabel getLblProveedores() {
		if (lblproveedores == null) {
			lblproveedores = new JLabel(PropertiesManager.getProperty("proveedorPanel.label.proveedores"));
			lblproveedores.setBounds(25, 88, 94, 20);
		}
		return lblproveedores;
	}

	public JTable getTable() {
		if(table == null) {
			table = new JTable(getTableModel());
			table.setRowSorter(getTableSorter());
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		}
		return table;
	}

	public ProveedorTableModel getTableModel() {
		if(model == null) {
			model = new ProveedorTableModel();
		}
		return model;
	}
	
	public TableRowSorter<ProveedorTableModel> getTableSorter() {
		if(sorter == null) {
			sorter = new TableRowSorter<ProveedorTableModel>(getTableModel());
		}
		return sorter;
	}

	@Override
	public void actualizarPantalla() {
		consultarProveedores();
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
