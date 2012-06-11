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
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.ClienteTableModel;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;

@Panel("clientePanel")
public class ClientePanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroDocumento;
	private JTextField txtApellido;
	private JButton btnBuscar;
	private JLabel lblNombre;
	private JLabel lblNumeroDocumento;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JLabel lblClientes;
	private JTextField txtNombre;
	private JLabel lblNmeroCtaCte;
	private JTextField txtNumeroCtaCte;
	private JLabel lblApellido;
	private JButton btnEstadoCuenta;
	private ClienteTableModel model;
	private TableRowSorter<ClienteTableModel> sorter;

	private ClienteController clienteController;

	@Autowired(required = true)
	public ClientePanel(
			@Qualifier("clienteController") ClienteController clienteController) {
		this.clienteController = clienteController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ClientePanel(boolean modoDisenio) {
		initializeComponents();

		throw new PresentationException("error.unknow",
				"este constructor es solo parar el plugin de diseño");
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		model.setClientes(new ArrayList<ClienteDto>());
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 470));

		add(getScrollPane());
		
		add(getLblNumeroDocumento());
		add(getLblApellido());
		add(getLblClientes());
		add(getLblNombre());
		add(getLblNmeroCtaCte());
		
		add(getTxtNumeroDocumento());
		add(getTxtApellido());
		add(getTxtNombre());
		add(getTxtNumeroCtaCte());
		
		add(getBtnBuscar());
		add(getBtnNuevo());
		add(getBtnModificar());
		add(getBtnEliminar());
		add(getBtnEstadoCuenta());

		setFocusTraversalPolicy();
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { txtNumeroDocumento, txtNumeroCtaCte,
						txtApellido, txtNombre }));
	}

	protected void consultarClientes() {
		try {
			ConsultaClienteDto filtroDto = new ConsultaClienteDto();
			filtroDto.setApellido(txtApellido.getText());
			filtroDto.setDocumento(txtNumeroDocumento.getText());
			filtroDto.setNombre(txtNombre.getText());

			model.setClientes(clienteController
					.obtenerClientesByFilter(filtroDto));
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
			model.setClientes(new ArrayList<ClienteDto>());
		}
	}

	public JTextField getTxtNumeroDocumento() {
		if (txtNumeroDocumento == null) {
			txtNumeroDocumento = new JTextField();
			txtNumeroDocumento.setColumns(10);
			txtNumeroDocumento.setBounds(200, 15, 110, 20);
		}
		return txtNumeroDocumento;
	}

	public JTextField getTxtApellido() {
		if (txtApellido == null) {
			txtApellido = new JTextField();
			txtApellido.setColumns(10);
			txtApellido.setBounds(200, 55, 110, 20);
		}
		return txtApellido;
	}

	public JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton(
					PropertiesManager
							.getProperty("clientePanel.button.buscar.texto"));
			btnBuscar.setToolTipText(PropertiesManager
					.getProperty("clientePanel.button.buscar.tooltip"));
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/buscar cliente.png")));

			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(570, 15, 60, 60);

			btnBuscar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					consultarClientes();
				}
			});
		}
		return btnBuscar;
	}

	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(330, 55, 60, 20);
		}
		return lblNombre;
	}

	public JLabel getLblNumeroDocumento() {
		if (lblNumeroDocumento == null) {
			lblNumeroDocumento = new JLabel(
					PropertiesManager
							.getProperty("clientePanel.etiqueta.documento"));
			lblNumeroDocumento.setBounds(125, 15, 70, 20);
		}
		return lblNumeroDocumento;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 115, 700, 315);
		}
		return scrollPane;
	}

	public JButton getBtnNuevo() {
		if (btnNuevo == null) {
			btnNuevo = new JButton(
					PropertiesManager
							.getProperty("productoPanel.button.nuevo.texto"));
			btnNuevo.setToolTipText(PropertiesManager
					.getProperty("productoPanel.button.nuevo.tooltip"));
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/nuevo_cliente.png")));

			btnNuevo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNuevo.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 115, 60, 60);

			btnNuevo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clienteController.mostrarClienteNuevoPanel();
				}
			});
		}
		return btnNuevo;
	}

	public JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton(
					PropertiesManager
							.getProperty("productoPanel.button.modificar.texto"));
			btnModificar.setToolTipText(PropertiesManager
					.getProperty("productoPanel.button.modificar.tooltip"));
			btnModificar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/modificar_cliente.png")));

			btnModificar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnModificar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnModificar.setMargin(new Insets(-1, -1, -1, -1));
			btnModificar.setBounds(735, 185, 60, 60);

			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						clienteController.editarCliente();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
					actualizarPantalla();
				}
			});
		}
		return btnModificar;
	}

	public JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton(
					PropertiesManager
							.getProperty("productoPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(PropertiesManager
					.getProperty("productoPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/eliminar_cliente.png")));

			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 255, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager
							.getProperty("clientePanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							clienteController.eliminarCliente();
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

	public JLabel getLblClientes() {
		if (lblClientes == null) {
			lblClientes = new JLabel("Clientes");
			lblClientes.setBounds(25, 90, 70, 20);
		}
		return lblClientes;
	}

	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(420, 55, 110, 20);
		}
		return txtNombre;
	}

	public JLabel getLblNmeroCtaCte() {
		if (lblNmeroCtaCte == null) {
			lblNmeroCtaCte = new JLabel("Cuenta Cte");
			lblNmeroCtaCte.setBounds(330, 15, 80, 20);
		}
		return lblNmeroCtaCte;
	}

	public JTextField getTxtNumeroCtaCte() {
		if (txtNumeroCtaCte == null) {
			txtNumeroCtaCte = new JTextField();
			txtNumeroCtaCte.setColumns(10);
			txtNumeroCtaCte.setBounds(420, 15, 110, 20);
		}
		return txtNumeroCtaCte;
	}

	public JLabel getLblApellido() {
		if (lblApellido == null) {
			lblApellido = new JLabel(
					PropertiesManager.getProperty("clientePanel.etiqueta.apellido"));
			lblApellido.setBounds(125, 55, 70, 20);
		}
		return lblApellido;
	}

	public JButton getBtnEstadoCuenta() {
		if (btnEstadoCuenta == null) {
			btnEstadoCuenta = new JButton(
					PropertiesManager
							.getProperty("productoPanel.button.estado.texto"));
			btnEstadoCuenta.setToolTipText(PropertiesManager
					.getProperty("productoPanel.button.estado.tooltip"));
			btnEstadoCuenta.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/estado_cuenta.png")));

			btnEstadoCuenta.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEstadoCuenta.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEstadoCuenta.setMargin(new Insets(-1, -1, -1, -1));
			btnEstadoCuenta.setBounds(735, 325, 60, 60);
		}
		return btnEstadoCuenta;
	}

	public TableRowSorter<ClienteTableModel> getSorter() {
		if (sorter == null) {
			sorter = new TableRowSorter<ClienteTableModel>(getModel());
		}
		return sorter;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
			table.setRowSorter(getSorter());
			table.setBounds(0, 0, 1, 1);
		}
		return table;
	}

	public ClienteTableModel getModel() {
		if (model == null) {
			model = new ClienteTableModel();
		}
		return model;
	}

	@Override
	public void actualizarPantalla() {
		if (Validator.isNotNull(txtNumeroDocumento))
			txtNumeroDocumento.requestFocus();
		consultarClientes();
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtApellido;
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(this.btnBuscar);
	}
}