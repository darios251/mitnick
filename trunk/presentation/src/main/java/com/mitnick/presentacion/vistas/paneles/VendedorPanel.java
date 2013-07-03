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
import com.mitnick.presentacion.controladores.VendedorController;
import com.mitnick.presentacion.modelos.VendedorTableModel;
import com.mitnick.servicio.servicios.dtos.ConsultaVendedorDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.VendedorDto;

@Panel("vendedorPanel")
public class VendedorPanel extends BasePanel<VendedorController> {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;
	private JTable table;
	private TableRowSorter<VendedorTableModel> sorter;
	private VendedorTableModel model;
	
	private JTextField txtCodigo;
	private JTextField txtNombre;
	
	private JLabel lblNombre;
	private JLabel lblCdigo;
	private JLabel lblVendedores;
	
	private JButton btnBuscar;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnEliminar;	

	@Autowired
	public VendedorPanel(@Qualifier("vendedorController") VendedorController vendedorController) {
		controller = vendedorController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public VendedorPanel(boolean modoDisenio) {
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
		add(getLblNombre());
		add(getTxtNombre());
		add(getScrollPane());
		add(getBtnBuscar());
		add(getBtnAgregar());
		add(getBtnEditar());
		add(getBtnEliminar());
		add(getLblVendedores());
		
		setFocusTraversalPolicy();
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{txtCodigo, txtNombre, btnBuscar, btnAgregar, btnEditar, btnEliminar}));
	}
	
	protected void consultarVendedores() {
		try {
			ConsultaVendedorDto dto = new ConsultaVendedorDto();
			dto.setCodigo(txtCodigo.getText());
			dto.setNombre(txtNombre.getText());
			getTableModel().setVendedores(controller.getVendedoresByFilter(dto));
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
			getTableModel().setVendedores(new ArrayList<VendedorDto>());
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
			btnBuscar = new JButton(PropertiesManager.getProperty("vendedorPanel.button.buscar.texto"));
			btnBuscar.setToolTipText(PropertiesManager.getProperty("vendedorPanel.button.buscar.tooltip"));
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource(PropertiesManager.getProperty("/img/buscar.png"))));

			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(560, 15, 60, 60);
			
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarVendedores();
				}
			});
		}
		return btnBuscar;
	}

	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(PropertiesManager.getProperty("vendedorPanel.label.nombre"));
			lblNombre.setBounds(330, 15, 80, 20);
		}
		return lblNombre;
	}

	public JLabel getLblCdigo() {
		if (lblCdigo == null) {
			lblCdigo = new JLabel(PropertiesManager.getProperty("vendedorPanel.label.codigo"));
			lblCdigo.setBounds(125, 15, 60, 20);
		}
		return lblCdigo;
	}

	public JButton getBtnAgregar() {
		if (btnAgregar == null) {
			btnAgregar = new JButton(PropertiesManager.getProperty("vendedorPanel.button.agregar.texto"));
			btnAgregar.setToolTipText(PropertiesManager.getProperty("vendedorPanel.button.agregar.tooltip"));
			btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
			
			btnAgregar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAgregar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
			btnAgregar.setBounds(735, 115, 60, 60);
			
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.nuevoVendedor();
				}
			});
		}
		return btnAgregar;
	}

	public JButton getBtnEditar() {
		if (btnEditar == null) {
			
			btnEditar = new JButton(PropertiesManager.getProperty("vendedorPanel.button.editar.texto"));
			btnEditar.setToolTipText(PropertiesManager.getProperty("vendedorPanel.button.editar.tooltip"));
			btnEditar.setIcon(new ImageIcon(this.getClass().getResource("/img/editar.png")));
			
			btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEditar.setMargin(new Insets(-1, -1, -1, -1));
			btnEditar.setBounds(735, 185, 60, 60);

			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.editarVendedor();
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
			btnEliminar = new JButton(PropertiesManager.getProperty("vendedorPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(PropertiesManager.getProperty("vendedorPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			
			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 255, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("vendedorPanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							controller.eliminarVendedor();
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
	
	public JLabel getLblVendedores() {
		if (lblVendedores == null) {
			lblVendedores = new JLabel(PropertiesManager.getProperty("vendedorPanel.label.vededores"));
			lblVendedores.setBounds(25, 88, 94, 20);
		}
		return lblVendedores;
	}

	public JTable getTable() {
		if(table == null) {
			table = new JTable(getTableModel());
			table.setRowSorter(getTableSorter());
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		}
		return table;
	}

	public VendedorTableModel getTableModel() {
		if(model == null) {
			model = new VendedorTableModel();
		}
		return model;
	}
	
	public TableRowSorter<VendedorTableModel> getTableSorter() {
		if(sorter == null) {
			sorter = new TableRowSorter<VendedorTableModel>(getTableModel());
		}
		return sorter;
	}

	@Override
	public void actualizarPantalla() {
		consultarVendedores();
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
