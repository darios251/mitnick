package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.modelos.ClienteTableModel;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.TipoCompradorDto;

@Panel("ventaClientePanel")
public class VentaClientePanel extends BasePanel<VentaController> {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroDocumento;
	private JButton btnBuscar;
	private JButton btnVolver;
	private JButton btnContinuar;
	private JLabel lblNombre;
	private JLabel lblNumeroDocumento;
	private JLabel lblTeclasAccesoRapido;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JTextField txtNombre;
	private JLabel lblNmeroCtaCte;
	private JTextField txtNumeroCtaCte;
	private ClienteTableModel model;
	private TableRowSorter<ClienteTableModel> sorter;
	private JLabel lblTipoComprador;
	private JComboBox<TipoCompradorDto> cmbTipoComprador;
	
	@Autowired(required = true)
	public VentaClientePanel(@Qualifier("ventaController") VentaController ventaController) {
		controller = ventaController;
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
		getCmbTipoComprador().setSelectedIndex(0);
		deshabilitarComponentes();
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 570));
		
		add(getScrollPane());
		
		add(getLblNumeroDocumento());
		add(getLblNombre());
		add(getLblNmeroCtaCte());
		
		add(getTxtNumeroDocumento());
		add(getTxtNombre());
		add(getTxtNumeroCtaCte());
		add(getBtnContinuar());
		add(getBtnBuscar());
		add(getBtnNuevo());
		add(getBtnVolver());
		
		add(getLblTipoComprador());
		add(getCmbTipoComprador());
		add(getLblTeclasAccesoRapido());
		
		setFocusTraversalPolicy();
	}
	
	
	public JTextField getTxtNumeroDocumento() {
		if (txtNumeroDocumento == null) {
			txtNumeroDocumento = new JTextField();
			txtNumeroDocumento.setColumns(10);
			txtNumeroDocumento.setBounds(170, 55, 110, 20);
		}
		return txtNumeroDocumento;
	}

	public JButton getBtnBuscar() {
		if(btnBuscar == null) {
			btnBuscar = new JButton("");
			btnBuscar.setToolTipText("Buscar Cliente");
			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar_cliente.png")));
			btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
			btnBuscar.setBounds(665, 15, 60, 60);
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
					continuar();
				}
			});
			btnContinuar.setBounds(735, 231, 60, 60);
		}
		return btnContinuar;
	}

	protected void continuar() {
		try {
			TipoCompradorDto tipoComprador = (TipoCompradorDto) cmbTipoComprador.getSelectedItem();
			if(tipoComprador.getTipoComprador() != MitnickConstants.TipoComprador.CONSUMIDOR_FINAL) {
				controller.agregarCliente();
				
				boolean mostrarMsg = PropertiesManager.getPropertyAsBoolean("application.mensajeInformativo.venta.clienteOK");
				if (mostrarMsg)
					mostrarMensajeInformativo(PropertiesManager.getProperty("ventaClientePanel.cliente.agregar.exito"));
			}
			if (VentaManager.getVentaActual().getTipo()==MitnickConstants.VENTA)
				controller.mostrarPagosPanel();
			else {
				BigDecimal deuda = controller.obtenerSaldoDeudorCliente();
				BigDecimal devolucion = VentaManager.getVentaActual().getTotal();
				
				int option = JOptionPane.CANCEL_OPTION;
				if (deuda.compareTo(new BigDecimal(0))>0) 
					option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("ventaPanel.devolucion.notaCredito.deuda", new Object[] {devolucion, deuda}), "Informaci�n", JOptionPane.OK_CANCEL_OPTION);	
				else
					option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("ventaPanel.devolucion.notaCredito", new Object[] {devolucion, devolucion}), "Informaci�n", JOptionPane.OK_CANCEL_OPTION);
				
				if (option != JOptionPane.CANCEL_OPTION)					
					controller.finalizarVenta();
			}
				
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.nombre"));
			lblNombre.setBounds(403, 55, 118, 20);
		}
		return lblNombre;
	}

	public JLabel getLblNumeroDocumento() {
		if (lblNumeroDocumento == null) {
			lblNumeroDocumento = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.documento"));
			lblNumeroDocumento.setBounds(25, 55, 85, 20);
		}
		return lblNumeroDocumento;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 109, 700, 315);
		}
		return scrollPane;
	}
	
	public JLabel getLblTeclasAccesoRapido() {
		if (lblTeclasAccesoRapido == null) {
			lblTeclasAccesoRapido = new JLabel("F3: Buscar | F5: Siguiente | +: Nuevo Cliente | Esc: Volver");
			lblTeclasAccesoRapido.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTeclasAccesoRapido.setBounds(40, 550, 300, 20);
		}
		return lblTeclasAccesoRapido;
	}

	public JButton getBtnNuevo() {
		if (btnNuevo == null) {
			btnNuevo = new JButton("");
			btnNuevo.setToolTipText("Agregar Cliente");
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/nuevo_cliente.png")));
			btnNuevo.setHorizontalTextPosition( SwingConstants.CENTER );
			btnNuevo.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 130, 60, 60);
			btnNuevo.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					controller.mostrarClienteNuevoPanel();
				}
			});
		}
		return btnNuevo;
	}

	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(533, 55, 110, 20);
		}
		return txtNombre;
	}

	public JLabel getLblNmeroCtaCte() {
		if (lblNmeroCtaCte == null) {
			lblNmeroCtaCte = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.cuentaCorriente"));
			lblNmeroCtaCte.setBounds(403, 15, 118, 20);
		}
		return lblNmeroCtaCte;
	}

	public JTextField getTxtNumeroCtaCte() {
		if (txtNumeroCtaCte == null) {
			txtNumeroCtaCte = new JTextField();
			txtNumeroCtaCte.setColumns(10);
			txtNumeroCtaCte.setBounds(533, 15, 110, 20);
		}
		return txtNumeroCtaCte;
	}

	public JButton getBtnVolver() {
		if(btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.volver"));
			btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			btnVolver.setBounds(735, 333, 60, 60);
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarVentasPanel();
					}
					catch(PresentationException ex) {
						
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVolver;
	}
	
	public TableRowSorter<ClienteTableModel> getSorter() {
		if (sorter == null) {
			sorter = new TableRowSorter<ClienteTableModel>(getModel());
		}
		return sorter;
	}
	
	public JLabel getLblTipoComprador() {
		if (lblTipoComprador == null) {
			lblTipoComprador = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.tipoComprador"));
			lblTipoComprador.setBounds(25, 15, 85, 23);
		}
		return lblTipoComprador;
	}

	public JComboBox<TipoCompradorDto> getCmbTipoComprador() {
		if (cmbTipoComprador == null) {
			cmbTipoComprador = new JComboBox<TipoCompradorDto>();// PropertiesManager.getProperty("ventaClientePanel.etiqueta.responsableInscripto")
			cmbTipoComprador.setBounds(170, 15, 200, 23);
			
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.CONSUMIDOR_FINAL, MitnickConstants.TipoComprador.CONSUMIDOR_FINAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL, MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL, MitnickConstants.TipoComprador.CONTRIBUYENTE_EVENTUAL_SOCIAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.EXENTO, MitnickConstants.TipoComprador.EXENTO_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.MONOTRIBUTISTA, MitnickConstants.TipoComprador.MONOTRIBUTISTA_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL, MitnickConstants.TipoComprador.MONOTRIBUTISTA_SOCIAL_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.NO_CATEGORIZADO, MitnickConstants.TipoComprador.NO_CATEGORIZADO_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.NO_RESPONSABLE, MitnickConstants.TipoComprador.NO_RESPONSABLE_DESC));
			cmbTipoComprador.addItem(new TipoCompradorDto(MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO, MitnickConstants.TipoComprador.RESPONSABLE_INSCRIPTO_DESC));
			
			cmbTipoComprador.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						controller.setTipoResponsable((TipoCompradorDto) cmbTipoComprador.getSelectedItem());
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return cmbTipoComprador;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{cmbTipoComprador, txtNumeroDocumento, txtNumeroCtaCte, txtNombre, table, btnBuscar, btnNuevo, btnContinuar, btnContinuar}));
	}
	
	protected void deshabilitarComponentes() {
		boolean enabled = true;//!getCmbTipoComprador().isSelected();
				
		getTxtNombre().setEnabled(enabled);
		getTxtNumeroCtaCte().setEnabled(enabled);
		getTxtNumeroDocumento().setEnabled(enabled);
		getTable().setVisible(enabled);
		getBtnBuscar().setEnabled(enabled);
		getBtnNuevo().setEnabled(enabled);
	}

	protected void consultarClientes() {
		try {
			ConsultaClienteDto filtroDto = new ConsultaClienteDto();
			filtroDto.setDocumento(getTxtNumeroDocumento().getText());
			filtroDto.setNombre(getTxtNombre().getText());
			
			getModel().setClientes(controller.obtenerClientesByFilter(filtroDto));
			table.requestFocus();
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
		consultarClientes();
		getCmbTipoComprador().requestFocus();
	}
		
	public void actualizarPantallaDevolucion() {
		try {
			List<ClienteDto> clientes = new ArrayList<ClienteDto>();
			clientes.add(VentaManager.getVentaActual().getCliente());
			getModel().setClientes(clientes);
			table.requestFocus();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
			getModel().setClientes(new ArrayList<ClienteDto>());
		}
		getBtnContinuar().requestFocus();
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getCmbTipoComprador();
	}

	public boolean isConsumidorFinal() {
		return MitnickConstants.TipoComprador.CONSUMIDOR_FINAL.equals(((TipoCompradorDto)getCmbTipoComprador().getSelectedItem()).getTipoComprador());
	}
	
	@Override
	protected void keyF3() {
		consultarClientes();
	}
	
	@Override
	protected void keyAdd() {
		btnNuevo.doClick();
	}
	
	@Override
	protected void keyF5() {
		btnContinuar.doClick();
	}
	
	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnBuscar());
	}
}