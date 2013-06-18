package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
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
public class ClientePanel extends BasePanel<ClienteController> {

	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroDocumento;
	private JButton btnBuscar;
	private JLabel lblNombre;
	private JLabel lblNumeroDocumento;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnVer;
	private JLabel lblClientes;
	private JTextField txtNombre;
	private JButton btnEstadoCuenta;
	private JButton btnCuentaCorriente;
	private JButton btnMovimientos;
	private ClienteTableModel model;
	private TableRowSorter<ClienteTableModel> sorter;

	@Autowired(required = true)
	public ClientePanel(@Qualifier("clienteController") ClienteController clienteController) {
		controller = clienteController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ClientePanel(boolean modoDisenio) {
		initializeComponents();

		throw new PresentationException("error.unknow",	"este constructor es solo parar el plugin de diseño");
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}		
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 550));

		add(getScrollPane());
		
		add(getLblNumeroDocumento());
		add(getLblClientes());
		add(getLblNombre());
		
		add(getTxtNumeroDocumento());
		add(getTxtNombre());
		
		add(getBtnBuscar());
		add(getBtnNuevo());
		add(getBtnModificar());
		add(getBtnEliminar());
		add(getBtnVer());
		add(getBtnEstadoCuenta());
		add(getBtnMovimientos());
		add(getBtnCuentaCorriente());
		
		setFocusTraversalPolicy();
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txtNombre, txtNumeroDocumento, btnBuscar, btnNuevo, btnModificar, btnEliminar, btnEstadoCuenta, btnCuentaCorriente, btnMovimientos }));
	}

	protected void consultarClientes() {
		try {
			ConsultaClienteDto filtroDto = new ConsultaClienteDto();
			filtroDto.setDocumento(txtNumeroDocumento.getText());
			filtroDto.setNombre(txtNombre.getText());

			model.setClientes(controller.obtenerClientesByFilter(filtroDto));
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
			model.setClientes(new ArrayList<ClienteDto>());
		}
	}

	public JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton(PropertiesManager.getProperty("clientePanel.button.buscar.texto"));
			btnBuscar.setToolTipText(PropertiesManager.getProperty("clientePanel.button.buscar.tooltip"));
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar_cliente.png")));

			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(570, 15, 60, 60);

			btnBuscar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					consultarClientes();
					table.requestFocus();
				}
			});
		}
		return btnBuscar;
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
			btnNuevo = new JButton(PropertiesManager.getProperty("productoPanel.button.nuevo.texto"));
			btnNuevo.setToolTipText(PropertiesManager.getProperty("productoPanel.button.nuevo.tooltip"));
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/nuevo_cliente.png")));

			btnNuevo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNuevo.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 55, 60, 60);

			btnNuevo.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					controller.nuevoCliente();
				}
			});
		}
		return btnNuevo;
	}

	public JButton getBtnModificar() {
		if (btnModificar == null) {
			btnModificar = new JButton(PropertiesManager.getProperty("productoPanel.button.modificar.texto"));
			btnModificar.setToolTipText(PropertiesManager.getProperty("productoPanel.button.modificar.tooltip"));
			btnModificar.setIcon(new ImageIcon(this.getClass().getResource("/img/modificar_cliente.png")));

			btnModificar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnModificar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnModificar.setMargin(new Insets(-1, -1, -1, -1));
			btnModificar.setBounds(735, 125, 60, 60);

			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.editarCliente();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnModificar;
	}

	public JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton(PropertiesManager.getProperty("productoPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(PropertiesManager.getProperty("productoPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/eliminar_cliente.png")));

			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 195, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("clientePanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							controller.eliminarCliente();
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

	public JButton getBtnVer() {
		if (btnVer == null) {
			btnVer = new JButton(PropertiesManager.getProperty("clientePanel.button.ver.texto"));
			btnVer.setToolTipText(PropertiesManager.getProperty("clientePanel.button.ver.tooltip"));
			btnVer.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar_cliente.png")));

			btnVer.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVer.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVer.setMargin(new Insets(-1, -1, -1, -1));
			btnVer.setBounds(735, 265, 60, 60);

			btnVer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarCliente();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVer;
	}
	
	public JLabel getLblClientes() {
		if (lblClientes == null) {
			lblClientes = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.clientes"));
			lblClientes.setBounds(25, 90, 70, 20);
		}
		return lblClientes;
	}

	public JLabel getLblNumeroDocumento() {
		if (lblNumeroDocumento == null) {
			lblNumeroDocumento = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.documento"));
			lblNumeroDocumento.setBounds(330, 15, 80, 20);
		}
		return lblNumeroDocumento;
	}
	
	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.nombre"));
			lblNombre.setBounds(125, 15, 70, 20);
		}
		return lblNombre;
	}

	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(200, 15, 110, 20);
		}
		return txtNombre;
	}

	public JTextField getTxtNumeroDocumento() {
		if (txtNumeroDocumento == null) {
			txtNumeroDocumento = new JTextField();
			txtNumeroDocumento.setColumns(10);
			txtNumeroDocumento.setBounds(420, 15, 110, 20);
		}
		return txtNumeroDocumento;
	}
	
	public JButton getBtnEstadoCuenta() {
		if (btnEstadoCuenta == null) {
			btnEstadoCuenta = new JButton(PropertiesManager.getProperty("clientePanel.button.listadoClientes"));
			btnEstadoCuenta.setToolTipText(PropertiesManager.getProperty("clientePanel.button.listadoClientes.tooltip"));
			btnEstadoCuenta.setIcon(new ImageIcon(this.getClass().getResource("/img/pdfIcon.png")));

			btnEstadoCuenta.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEstadoCuenta.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEstadoCuenta.setMargin(new Insets(-1, -1, -1, -1));
			btnEstadoCuenta.setBounds(735, 335, 60, 60);
			btnEstadoCuenta.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						ConsultaClienteDto filtroDto = new ConsultaClienteDto();
						filtroDto.setDocumento(txtNumeroDocumento.getText());
						filtroDto.setNombre(txtNombre.getText());
						controller.cargarReporte(filtroDto);
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnEstadoCuenta;
	}
	
	public JButton getBtnCuentaCorriente() {
		if (btnCuentaCorriente == null) {

			btnCuentaCorriente = new JButton(PropertiesManager.getProperty("clientePanel.button.cuentaCorriente"));
			btnCuentaCorriente.setToolTipText(PropertiesManager.getProperty("clientePanel.button.cuentaCorriente.tooltip"));
			btnCuentaCorriente.setIcon(new ImageIcon(this.getClass().getResource("/img/calculadora.png")));
			btnCuentaCorriente.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCuentaCorriente.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCuentaCorriente.setMargin(new Insets(-1, -1, -1, -1));
			btnCuentaCorriente.setBounds(735, 405, 60, 60);
			btnCuentaCorriente.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						controller.cuentaCorriente();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnCuentaCorriente;
	}

	public JButton getBtnMovimientos() {
		if(btnMovimientos == null) {
			btnMovimientos = new JButton(PropertiesManager.getProperty("pagoPanel.boton.movimientos"));
			btnMovimientos.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.movimientos"));
			btnMovimientos.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnMovimientos.setHorizontalTextPosition(SwingConstants.CENTER);
			btnMovimientos.setMargin(new Insets(-1, -1, -1, -1));
			btnMovimientos.setBounds(735, 475, 60, 60);
			btnMovimientos.setIcon(new ImageIcon(this.getClass().getResource("/img/movimientos.png")));
			btnMovimientos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.reporteMovimientosCliente();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnMovimientos;
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
//		consultarClientes();
	}
	
	@Override
	protected void keyAdd() {
		Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		
		if(focusOwner instanceof JTextField) {
			JTextField textField = (JTextField) focusOwner;
			textField.setText(textField.getText().replaceAll("\\+", ""));
		}
		btnNuevo.doClick();
	}
	
	@Override
	protected void keyMultiply() {
		btnModificar.doClick();
		limpiarCamposPantalla();
	}
	
	@Override
	protected void keySubstract() {
		Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		
		if(focusOwner instanceof JTextField) {
			JTextField textField = (JTextField) focusOwner;
			textField.setText(textField.getText().replaceAll("\\-", ""));
		}
		
		btnEliminar.doClick();
	}
	
	@Override
	protected void keyF1() {
		btnCuentaCorriente.doClick();
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtNombre;
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(this.btnBuscar);
	}
}