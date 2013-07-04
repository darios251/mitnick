package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.modelos.PagoTableModel;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.presentacion.vistas.controles.JTabbedPaneConBoton;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CreditoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;

@Panel("pagoPanel")
public class PagoPanel extends BasePanel<VentaController> implements KeyEventDispatcher {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;
	private JTable table;
	private PagoTableModel model;

	private JLabel lblPagosRealizados;
	private JLabel lbl_ST;
	private JLabel lblSubtotal;
	private JLabel lbl_T;
	private JLabel lblTotal;
	private JLabel lbl_TP;
	private JLabel lblTotalPagado;
	private JLabel lblSubtotalValor;
	private JLabel lblTotalValor;
	private JLabel lblTotalPagadoValor;
	private JLabel lblMonto;
	private JLabel lblMedioPago;
	private JLabel lblAPagarValor;
	private JLabel lblTotalAPagar;
	private JLabel lblTeclasAccesoRapido;
	
	private JLabel lblDescuentos;
	private JLabel lblDescuentosValor;

	private JComboBox<MedioPagoDto> cmbMedioPago;

	private JTextField txtMonto;

	private JButton btnAgregar;
	private JButton btnVolver;
	private JButton btnQuitar;

	private JPanel pnlCliente;
	private JLabel lblNombre;
	private JLabel lblDni;

	private JLabel lblConsumidorFinal;
	private JLabel lblCliente;

	private boolean cuentaCorriente = false;

	@Autowired
	public PagoPanel(@Qualifier("ventaController") VentaController ventaController) {
		controller = ventaController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public PagoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Override
	protected void initializeComponents() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		setLayout(null);
		setSize(new Dimension(815, 570));

		add(getLblMedioPago());
		add(getCmbMedioPago());
		add(getLblMonto());
		add(getTxtMonto());
		add(getScrollPane());
		add(getLblPagosRealizados());
		add(getLbl_ST());
		add(getLblSubtotal());
		add(getLbl_T());
		add(getLblTotal());
		add(getLbl_TP());
		add(getLblTotalPagado());
		add(getLblTotalAPagar());
		add(getBtnAgregar());
		add(getBtnQuitar());
		add(getBtnVolver());
		add(getLblSubtotalValor());
		add(getLblTotalValor());
		add(getLblTotalPagadoValor());
		add(getLblAPagarValor());
		add(getLblConsumidorFinal());
		add(getPnlCliente());
		add(getLblCliente());
		add(getLblTeclasAccesoRapido());
		if (Validator.isNotNull(PropertiesManager.getPropertyAsBoolean("application.discount")) && PropertiesManager.getPropertyAsBoolean("application.discount").booleanValue()) {
			add(getLblDescuentos());
			add(getLblDescuentosValor());
		}

		setFocusTraversalPolicy();
	}

	private JLabel getLblCliente() {
		if (lblCliente == null) {
			lblCliente = new JLabel("Cliente:");
			lblCliente.setBounds(113, 36, 46, 14);
		}
		return lblCliente;
	}
	
	public JLabel getLblTeclasAccesoRapido() {
		if (lblTeclasAccesoRapido == null) {
			lblTeclasAccesoRapido = new JLabel("Enter: Agregar Pago | Esc: Volver");
			lblTeclasAccesoRapido.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTeclasAccesoRapido.setBounds(40, 550, 180, 20);
		}
		return lblTeclasAccesoRapido;
	}

	public JTextField getTxtMonto() {
		if (txtMonto == null) {
			txtMonto = new JTextField();
			txtMonto.setBounds(44, 176, 110, 20);
			txtMonto.setColumns(10);
		}
		return txtMonto;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
			table.setBounds(0, 0, 1, 1);
		}
		return table;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(269, 115, 516, 143);
		}
		return scrollPane;
	}

	public JLabel getLblPagosRealizados() {
		if (lblPagosRealizados == null) {
			lblPagosRealizados = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.pagosRealizados"));
			lblPagosRealizados.setBounds(269, 86, 141, 20);
		}
		return lblPagosRealizados;
	}

	public JLabel getLbl_ST() {
		if (lbl_ST == null) {
			lbl_ST = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.subtotal"));
			lbl_ST.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_ST.setBounds(269, 300, 88, 20);
		}
		return lbl_ST;
	}

	public JLabel getLblSubtotal() {
		if (lblSubtotal == null) {
			lblSubtotal = new JLabel();
			lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSubtotal.setBounds(650, 176, 109, 20);
		}
		return lblSubtotal;
	}

	public JLabel getLbl_T() {
		if (lbl_T == null) {
			lbl_T = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.total"));
			lbl_T.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_T.setBounds(269, 380, 88, 20);
		}
		return lbl_T;
	}

	public JLabel getLblTotal() {
		if (lblTotal == null) {
			lblTotal = new JLabel();
			lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotal.setBounds(650, 207, 109, 20);
		}
		return lblTotal;
	}

	public JLabel getLbl_TP() {
		if (lbl_TP == null) {
			lbl_TP = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalPagado"));
			lbl_TP.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_TP.setBounds(269, 420, 88, 20);
		}
		return lbl_TP;
	}

	public JLabel getLblTotalPagado() {
		if (lblTotalPagado == null) {
			lblTotalPagado = new JLabel();
			lblTotalPagado.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalPagado.setBounds(269, 270, 109, 20);
		}
		return lblTotalPagado;
	}

	public JLabel getLblSubtotalValor() {
		if (lblSubtotalValor == null) {
			lblSubtotalValor = new JLabel();
			lblSubtotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSubtotalValor.setBounds(396, 300, 88, 20);
		}
		return lblSubtotalValor;
	}

	public JLabel getLblTotalValor() {
		if (lblTotalValor == null) {
			lblTotalValor = new JLabel();
			lblTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalValor.setBounds(396, 380, 88, 20);
		}
		return lblTotalValor;
	}

	public JLabel getLblTotalPagadoValor() {
		if (lblTotalPagadoValor == null) {
			lblTotalPagadoValor = new JLabel();
			lblTotalPagadoValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalPagadoValor.setBounds(396, 420, 88, 20);
		}
		return lblTotalPagadoValor;
	}

	public JButton getBtnAgregar() {
		if (btnAgregar == null) {
			btnAgregar = new JButton(PropertiesManager.getProperty("pagoPanel.boton.agregar"));
			btnAgregar.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.agregar"));

			btnAgregar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/agregar.png")));
			btnAgregar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAgregar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAgregar.setMargin(new Insets(-1, -1, -1, -1));

			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						agregarPago();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnAgregar.setBounds(188, 117, 60, 60);
		}
		return btnAgregar;
	}

	public JLabel getLblMonto() {
		if (lblMonto == null) {
			lblMonto = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.monto"));
			lblMonto.setHorizontalAlignment(SwingConstants.LEFT);
			lblMonto.setBounds(44, 148, 110, 20);
		}
		return lblMonto;
	}

	public JComboBox<MedioPagoDto> getCmbMedioPago() {
		if (cmbMedioPago == null) {
			cmbMedioPago = new JComboBox<MedioPagoDto>(new MitnickComboBoxModel<MedioPagoDto>());
			cmbMedioPago.setBounds(44, 117, 110, 20);
		}
		return cmbMedioPago;
	}

	public JLabel getLblMedioPago() {
		if (lblMedioPago == null) {
			lblMedioPago = new JLabel(PropertiesManager
							.getProperty("pagoPanel.etiqueta.medioPago"));
			lblMedioPago.setHorizontalAlignment(SwingConstants.LEFT);
			lblMedioPago.setBounds(44, 86, 115, 20);
		}
		return lblMedioPago;
	}

	public JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton(
					PropertiesManager.getProperty("pagoPanel.boton.volver"));
			btnVolver.setToolTipText(PropertiesManager
					.getProperty("pagoPanel.tooltip.volver"));
			btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			btnVolver.setBounds(680, 364, 60, 60);
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/volver.png")));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						((VentaController) controller).mostrarClienteVenta();
					} catch (PresentationException ex) {

						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVolver;
	}

	public JButton getBtnQuitar() {
		if (btnQuitar == null) {
			btnQuitar = new JButton(
					PropertiesManager.getProperty("pagoPanel.button.quitar"));
			btnQuitar.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.quitar"));

			btnQuitar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnQuitar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnQuitar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnQuitar.setMargin(new Insets(-1, -1, -1, -1));

			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					logger.info("Quitando pago ... ");

					try {
						int index = table.getSelectedRow();
						PagoDto pagoDto = model.getPago(index);

						int opcion = mostrarMensajeAdvertencia(PropertiesManager.getProperty("pagoPanel.dialog.confirm.quitar"));

						if (opcion == JOptionPane.YES_OPTION) {
							((VentaController) controller).quitarPago(pagoDto);
						}
					} catch (IndexOutOfBoundsException exception) {
						if (model.getRowCount() == 0) {
							mostrarMensajeError(PropertiesManager.getProperty("error.pagoPanel.pagos.vacio"));
						} else {
							mostrarMensajeError(PropertiesManager.getProperty("error.pagoPanel.pago.noSeleccionado"));
						}
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnQuitar.setBounds(188, 186, 60, 60);
		}
		return btnQuitar;
	}

	public JLabel getLblAPagarValor() {
		if (lblAPagarValor == null) {
			lblAPagarValor = new JLabel();
			lblAPagarValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAPagarValor.setBounds(396, 460, 88, 20);
		}
		return lblAPagarValor;
	}

	public JLabel getLblTotalAPagar() {
		if (lblTotalAPagar == null) {
			lblTotalAPagar = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalAPagar"));
			lblTotalAPagar.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalAPagar.setBounds(269, 460, 88, 20);
		}
		return lblTotalAPagar;
	}

	public JLabel getLblDescuentos() {
		if (lblDescuentos == null) {
			lblDescuentos = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.descuentos"));
			lblDescuentos.setHorizontalAlignment(SwingConstants.RIGHT);
			lblDescuentos.setBounds(269, 340, 88, 20);
		}
		return lblDescuentos;
	}
	public JLabel getLblDescuentosValor() {
		if (lblDescuentosValor == null) {
			lblDescuentosValor = new JLabel();
			lblDescuentosValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblDescuentosValor.setBounds(396, 340, 88, 20);
		}
		return lblDescuentosValor;
	}
	
	public PagoTableModel getModel() {
		if (model == null) {
			model = new PagoTableModel();
		}
		return model;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { cmbMedioPago, txtMonto }));
	}

	protected void agregarPago() {
		try {
			MedioPagoDto pago = (MedioPagoDto) cmbMedioPago.getSelectedItem();
			if (pago.isCuentaCorriente()) {
				String cuotas = JOptionPane.showInputDialog(PropertiesManager.getProperty("pagoPanel.cuentaCorriente.cantidadCuotas"));
				if (cuotas!=null && !cuotas.equals("")){
					List<CuotaDto> cuotasDto = ((VentaController) controller).getCuotas(cuotas, txtMonto.getText());
					CuotasCuentaCorrienteDialog cuotasDialog = new CuotasCuentaCorrienteDialog((JFrame) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent(), cuotasDto, txtMonto.getText());
					if (cuotasDialog.aceptar) {
						((VentaController) controller).guardarCuotas(cuotasDialog.getModel().getCuotas());
						((VentaController) controller).agregarPago(pago, cuotasDialog.getMonto(), null);
					} 
				}
			}
			else if (pago.isNotaCredito()) {
				String nroNC = JOptionPane.showInputDialog(PropertiesManager.getProperty("pagoPanel.notaCredito.numeroTicket"));
				if (nroNC == null)
					return;
				CreditoDto credito = ((VentaController) controller).obtenerCredito(nroNC);
				if (credito == null){
					int option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("pagoPanel.notaCredito.noExiste"), "Error", JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.CANCEL_OPTION)
						return;						
				} else {
					BigDecimal disponible = credito.getDisponible(); 
					if (disponible.compareTo(new BigDecimal(txtMonto.getText()))<0){
						int option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("pagoPanel.notaCredito.disponibleMenorAlTotal", new Object[]{credito.getDisponible()}), "Error", JOptionPane.OK_CANCEL_OPTION);
						if (option == JOptionPane.CANCEL_OPTION)
							return;
					}
					//si se uso credito se debe modificar la nota de credito o romper si ya no hay credito disponible
					BigDecimal nuevoDisponible = disponible.subtract(new BigDecimal(txtMonto.getText()));
					if (nuevoDisponible.compareTo(new BigDecimal(0))<=0)
						JOptionPane.showMessageDialog((java.awt.Component) null, PropertiesManager.getProperty("ventaPanel.venta.notaCredito.romper"));
					else
						JOptionPane.showMessageDialog((java.awt.Component) null, PropertiesManager.getProperty("ventaPanel.venta.notaCredito.disponible", new Object[] {nuevoDisponible}));
				}
				((VentaController) controller).agregarPago(pago, txtMonto.getText(), nroNC);
				
			} else
				((VentaController) controller).agregarPago(pago, txtMonto.getText(), null);
			//foco en monto
			txtMonto.requestFocus();			
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void limpiarCamposPantalla() {
		if (Validator.isNotNull(txtMonto))
			txtMonto.setText("");
		if (Validator.isNotNull(model))
			model.setPagos(new ArrayList<PagoDto>());
	}

	public void actualizarPantalla() {
		if (Validator.isNotNull(lblSubtotalValor))
			lblSubtotalValor.setText(VentaManager.getVentaActual().getSubTotal().toString());
		if (Validator.isNotNull(lblTotalValor))
			lblTotalValor.setText(VentaManager.getVentaActual().getTotal().toString());
		if (Validator.isNotNull(lblTotalPagadoValor))
			lblTotalPagadoValor.setText(VentaManager.getVentaActual().getTotalPagado().toString());
		if (Validator.isNotNull(lblAPagarValor))
			lblAPagarValor.setText(VentaManager.getVentaActual().getFaltaPagar().toString());
		if (Validator.isNotNull(PropertiesManager.getPropertyAsBoolean("application.discount")) && PropertiesManager.getPropertyAsBoolean("application.discount").booleanValue()) {
			lblDescuentosValor.setText("-".concat(VentaManager.getVentaActual().getDescuentoTotal().toString()));
		}
		if (Validator.isNotNull(cmbMedioPago)) {
			List<MedioPagoDto> medioPagoList = new ArrayList<MedioPagoDto>();

			try {
				medioPagoList = ((VentaController) controller).getAllMedioPago();
			} catch (BusinessException e) {
				;
			}

			cmbMedioPago.setModel(new MitnickComboBoxModel<MedioPagoDto>());
			((MitnickComboBoxModel<MedioPagoDto>) cmbMedioPago.getModel()).addItems(medioPagoList);
		}
		if (Validator.isNotNull(model)) {
			model.setPagos(VentaManager.getVentaActual().getPagos());
		}
		if (Validator.isNotNull(txtMonto)) {
			txtMonto.setText(VentaManager.getVentaActual().getFaltaPagar().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			txtMonto.requestFocus();
			txtMonto.setSelectionStart(0);
			txtMonto.setSelectionEnd(txtMonto.getText().length());
		}

		if (Validator.isNotNull(VentaManager.getVentaActual().getCliente())) {
			ClienteDto cliente = VentaManager.getVentaActual().getCliente();

			getLblNombre().setText(cliente.getNombre());
			getLblDni().setText(cliente.getDocumento());

			getPnlCliente().setVisible(true);
			getLblConsumidorFinal().setVisible(false);
		} else {
			getLblConsumidorFinal().setVisible(true);
			getPnlCliente().setVisible(false);
		}
	}

	
	public void finalizarVenta() {
		try {
			if (VentaManager.getVentaActual().isVenta()){
				boolean mostrarMsg = PropertiesManager.getPropertyAsBoolean("application.mensajeInformativo.venta.vuelto");
				if (mostrarMsg)
					mostrarMensajeInformativo(PropertiesManager.getProperty("pagoPanel.finalizarVenta.exito", new Object[] { VentaManager.getVentaActual().getVuelto().toString() }));
				((VentaController) controller).crearNuevaVenta(MitnickConstants.VENTA);				
				((VentaController) controller).mostrarVentasPanel();
			} else {
				BigDecimal deuda = controller.obtenerSaldoDeudorCliente();
				BigDecimal devolucionTotal = VentaManager.getVentaActual().getTotal();
				int option = JOptionPane.CANCEL_OPTION;
				if (Validator.isMoreThanZero(deuda) && Validator.isMoreThanZero(devolucionTotal)) {
					option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("ventaPanel.devolucion.notaCredito.cuentaCorriente"), "Información", JOptionPane.YES_NO_OPTION);	
					if (option == JOptionPane.OK_OPTION){
						//se pagan cuotas pendientes del cliente con la nota de crédito
						((VentaController) controller).pagarCuotasNC();
						if (deuda.compareTo(devolucionTotal)>=0) {
							deuda = deuda.subtract(devolucionTotal);
							JOptionPane.showMessageDialog((java.awt.Component) null, PropertiesManager.getProperty("ventaPanel.devolucion.notaCredito.saldo", new Object[] {devolucionTotal, deuda}));
						} 
					}
				}
				
				((VentaController) controller).crearNuevaVenta(MitnickConstants.VENTA);
				((VentaController) controller).mostrarVentasPanel();
					
				JTabbedPaneConBoton jTabbedPaneConBoton = ((VentaController) controller).getPrincipalView().jTabbedPaneConBoton;
				jTabbedPaneConBoton.remove(jTabbedPaneConBoton.getSelectedIndex());				
			}
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtMonto;
	}

	public int mostrarMensajeReintentar() {
		Object[] options = {
				PropertiesManager.getProperty("dialog.error.reintentar"),
				PropertiesManager.getProperty("dialog.error.cancelarVenta") };

		return JOptionPane.showOptionDialog(currentView, PropertiesManager.getProperty("dialog.error.MensajeReintentar"), PropertiesManager.getProperty("dialog.error.titulo"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	}

	public int mostrarMensajeReintentar(String errorMessage) {
		Object[] options = {
				PropertiesManager.getProperty("dialog.error.reintentar"),
				PropertiesManager.getProperty("dialog.error.cancelarVenta") };

		return JOptionPane.showOptionDialog(currentView,
				PropertiesManager.getProperty("dialog.error.MensajeReintentar")
						+ "\n" + errorMessage,
				PropertiesManager.getProperty("dialog.error.titulo"),
				JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}

	public JPanel getPnlCliente() {
		if (pnlCliente == null) {
			pnlCliente = new JPanel();
			pnlCliente.setLayout(null);
			pnlCliente.setBounds(188, 23, 335, 37);
			pnlCliente.add(getLblNombre());
			pnlCliente.add(getLblDni());
		}
		return pnlCliente;
	}

	public JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("<<Nombre>>");
			lblNombre.setBounds(10, 11, 182, 14);
			getPnlCliente().add(lblNombre);
		}
		return lblNombre;
	}

	public JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("<<DNI>>");
			lblDni.setBounds(143, 11, 182, 14);
			getPnlCliente().add(lblDni);
		}
		return lblDni;
	}

	public JLabel getLblConsumidorFinal() {
		if (lblConsumidorFinal == null) {
			lblConsumidorFinal = new JLabel("Consumidor Final");
			lblConsumidorFinal.setBounds(188, 36, 115, 14);
		}
		return lblConsumidorFinal;
	}

	protected void setDefaultButton() {
		if (Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnAgregar());
	}
	
	@Override
	protected void keyEscape() {
		btnVolver.doClick();
	}

	public boolean isCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(boolean cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
}
