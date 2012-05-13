package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.modelos.ClienteTableModel;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;

@Panel("ventaClientePanel")
public class VentaClientePanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroDocumento;
	private JTextField txtApellido;
	private JButton btnBuscar;
	private JButton btnContinuar;
	private JButton btnAgregarCliente;
	private JLabel lblNombre;
	private JLabel lblNumeroDocumento;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JTextField txtNombre;
	private JLabel lblNmeroCtaCte;
	private JTextField txtNumeroCtaCte;
	private JLabel lblApellido;
	private JButton btnEstadoCuenta;
	private ClienteTableModel model;
	private TableRowSorter<ClienteTableModel> sorter;
	private JCheckBox chkConsumidorFinal;
	
	private VentaController ventaController;
	
	@Autowired(required = true)
	public VentaClientePanel(@Qualifier("ventaController") VentaController ventaController) {
		this.ventaController = ventaController;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public VentaClientePanel(boolean modoDisenio) {
		initializeComponents();
		
		throw new PresentationException("error.unknow", "este constructor es solo parar el plugin de diseño");
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		getChkConsumidorFinal().setSelected(false);
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 470));
		
		add(getScrollPane());
		
		add(getLblNumeroDocumento());
		add(getLblApellido());
		add(getLblNombre());
		add(getLblNmeroCtaCte());
		
		add(getTxtApellido());
		add(getTxtNumeroDocumento());
		add(getTxtNombre());
		add(getTxtNumeroCtaCte());
		
		add(getBtnAgregarCliente());
		add(getBtnContinuar());
		add(getBtnEstadoCuenta());
		add(getBtnBuscar());
		add(getBtnNuevo());
		
		add(getChkConsumidorFinal());
		
		setFocusTraversalPolicy();
	}
	
	
	public JTextField getTxtNumeroDocumento() {
		if (txtNumeroDocumento == null) {
			txtNumeroDocumento = new JTextField();
			txtNumeroDocumento.setColumns(10);
			txtNumeroDocumento.setBounds(200, 11, 110, 20);
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
		if(btnBuscar == null) {
			btnBuscar = new JButton("");
			btnBuscar.setToolTipText("Buscar Cliente");
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar cliente.png")));
			btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(570, 15, 60, 60);
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					consultarClientes();
				}
			});
		}
		return btnBuscar;
	}

	public JButton getBtnContinuar() {
		if (btnContinuar == null) {
			btnContinuar = new JButton(PropertiesManager.getProperty("ventaPanel.button.continuar"));
			btnContinuar.setToolTipText(PropertiesManager.getProperty("ventaPanel.tooltip.continuar"));
			
			btnContinuar.setIcon(new ImageIcon(this.getClass().getResource("/img/continuar.png")));
			btnContinuar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnContinuar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnContinuar.setMargin(new Insets(-1, -1, -1, -1));
			
			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(!isConsumidorFinal()) {
							ventaController.agregarCliente();
							mostrarMensajeInformativo(PropertiesManager.getProperty("ventaClientePanel.cliente.agregar.exito"));
						}
						ventaController.mostrarPagosPanel();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnContinuar.setBounds(735, 257, 60, 60);
		}
		return btnContinuar;
	}

	public JButton getBtnAgregarCliente() {
		if (btnAgregarCliente == null) {
			btnAgregarCliente = new JButton(PropertiesManager.getProperty("ventaClientePanel.button.agregarCliente"));
			btnAgregarCliente.setToolTipText(PropertiesManager.getProperty("ventaClientePanel.tooltip.agregarCliente"));
			
			btnAgregarCliente.setIcon(new ImageIcon(this.getClass().getResource("/img/nuevo_cliente.png")));
			btnAgregarCliente.setHorizontalTextPosition( SwingConstants.CENTER );
			btnAgregarCliente.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnAgregarCliente.setMargin(new Insets(-1, -1, -1, -1));
			btnAgregarCliente.setVisible(false);
			
			btnAgregarCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ventaController.agregarCliente();
						mostrarMensajeInformativo(PropertiesManager.getProperty("ventaClientePanel.cliente.agregar.exito"));
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnAgregarCliente.setBounds(735, 186, 60, 60);
		}
		return btnAgregarCliente;
	}

	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.nombre"));
			lblNombre.setBounds(330, 55, 60, 20);
		}
		return lblNombre;
	}

	public JLabel getLblNumeroDocumento() {
		if (lblNumeroDocumento == null) {
			lblNumeroDocumento = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.documento"));
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
			btnNuevo = new JButton("");
			btnNuevo.setToolTipText("Agregar Cliente");
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/nuevo_cliente.png")));
			btnNuevo.setHorizontalTextPosition( SwingConstants.CENTER );
			btnNuevo.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 115, 60, 60);
			btnNuevo.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					ventaController.mostrarClienteNuevoPanel();
				}
			});
		}
		return btnNuevo;
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
			lblNmeroCtaCte = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.cuentaCorriente"));
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
			lblApellido = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.apellido"));
			lblApellido.setBounds(125, 55, 70, 20);
		}
		return lblApellido;
	}

	public JButton getBtnEstadoCuenta() {
		if (btnEstadoCuenta == null) {
			btnEstadoCuenta = new JButton("");
			btnEstadoCuenta.setToolTipText(PropertiesManager.getProperty("clientePanel.tooltip.estadoCuenta"));
			btnEstadoCuenta.setIcon(new ImageIcon(this.getClass().getResource("/img/estado_cuenta.png")));
			btnEstadoCuenta.setHorizontalTextPosition( SwingConstants.CENTER );
			btnEstadoCuenta.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnEstadoCuenta.setMargin(new Insets(-1, -1, -1, -1));
			btnEstadoCuenta.setBounds(735, 328, 60, 60);
			btnEstadoCuenta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnEstadoCuenta;
	}

	public TableRowSorter<ClienteTableModel> getSorter() {
		if (sorter == null) {
			sorter = new TableRowSorter<ClienteTableModel>(getModel());
		}
		return sorter;
	}

	public JCheckBox getChkConsumidorFinal() {
		if (chkConsumidorFinal == null) {
			chkConsumidorFinal = new JCheckBox(PropertiesManager.getProperty("ventaClientePanel.etiqueta.consumidorFinal"));
			chkConsumidorFinal.setBounds(653, 54, 156, 23);
			chkConsumidorFinal.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						if(chkConsumidorFinal.isSelected())
							ventaController.quitarCliente();
						deshabilitarComponentes();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return chkConsumidorFinal;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{txtNumeroDocumento, txtNumeroCtaCte, txtApellido, txtNombre}));
	}
	
	protected void deshabilitarComponentes() {
		boolean enabled = !getChkConsumidorFinal().isSelected();
				
		getTxtApellido().setEnabled(enabled);
		getTxtNombre().setEnabled(enabled);
		getTxtNumeroCtaCte().setEnabled(enabled);
		getTxtNumeroDocumento().setEnabled(enabled);
		getTable().setVisible(enabled);
		getBtnBuscar().setEnabled(enabled);
		getBtnNuevo().setEnabled(enabled);
		getBtnAgregarCliente().setEnabled(enabled);
		getBtnEstadoCuenta().setEnabled(enabled);
	}

	protected void consultarClientes() {
		try {
			ConsultaClienteDto filtroDto = new ConsultaClienteDto();
			filtroDto.setApellido(getTxtApellido().getText());
			filtroDto.setDocumento(getTxtNumeroDocumento().getText());
			filtroDto.setNombre(getTxtNombre().getText());
			
			getModel().setClientes(ventaController.obtenerClientesByFilter(filtroDto));
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
			getModel().setClientes(new ArrayList<ClienteDto>());
		}
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
		getTxtNumeroDocumento().requestFocus();
		consultarClientes();
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtApellido();
	}

	public boolean isConsumidorFinal() {
		return getChkConsumidorFinal().isSelected();
	}
	
	protected void setDefaultButton() {
		this.getRootPane().setDefaultButton(getBtnBuscar());
	}
}