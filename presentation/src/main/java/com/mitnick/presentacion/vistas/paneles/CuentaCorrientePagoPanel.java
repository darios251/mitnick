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
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.modelos.PagoTableModel;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CreditoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;

@Panel("cuentaCorrientePagoPanel")
public class CuentaCorrientePagoPanel extends BasePanel<ClienteController> implements KeyEventDispatcher {
	
	private static final long serialVersionUID = 1L;
	
	private ClienteDto cliente;
	private List<CuotaDto> cuotas;
	
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
	
	private JComboBox<MedioPagoDto> cmbMedioPago;
	
	private JTextField txtMonto;
	
	private JButton btnAgregar;
	private JButton btnVolver;
	private JButton btnQuitar;
	
	private JButton btnFinalizar;
	
	private JPanel pnlCliente;
	private JLabel lblNombre;
	private JLabel lblDni;

	private JLabel lblConsumidorFinal;
	private JLabel lblCliente;
	
	@Autowired
	public CuentaCorrientePagoPanel(@Qualifier("clienteController") ClienteController clienteController) {
		controller = clienteController;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public CuentaCorrientePagoPanel(boolean modoDisenio) throws Exception {
		// Este contructor solo se utiliza para que funcione el plugin
		initializeComponents();
		throw new Exception("Este constructor no debe ser utilizado");
	}

	@Override
	protected void initializeComponents() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		setLayout(null);
		setSize(new Dimension(815, 470));
		
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
		add(getBtnFinalizar());
		
		setFocusTraversalPolicy();
	}
	
	private JLabel getLblCliente() {
		if(lblCliente == null) {
			lblCliente = new JLabel("Cliente:");
			lblCliente.setBounds(113, 36, 46, 14);
		}
		return lblCliente;
	}

	public JTextField getTxtMonto() {
		if(txtMonto == null) {
			txtMonto = new JTextField();
			txtMonto.setBounds(44, 176, 110, 20);
			txtMonto.setColumns(10);
		}
		return txtMonto;
	}

	public JTable getTable() {
		if(table == null) {
			table = new JTable(getModel());
			table.setBounds(0, 0, 1, 1);
		}
		return table;
	}

	public JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(269, 115, 516, 143);
		}
		return scrollPane;
	}

	public JLabel getLblPagosRealizados() {
		if(lblPagosRealizados == null) {
			lblPagosRealizados = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.pagosRealizados"));
			lblPagosRealizados.setBounds(269, 86, 141, 20);
		}
		return lblPagosRealizados;
	}

	public JLabel getLbl_ST() {
		if(lbl_ST == null) {
			lbl_ST = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.subtotal"));
			lbl_ST.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_ST.setBounds(269, 300, 88, 20);
		}
		return lbl_ST;
	}

	public JLabel getLblSubtotal() {
		if(lblSubtotal == null) {
			lblSubtotal = new JLabel();
			lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSubtotal.setBounds(650, 176, 109, 20);
		}
		return lblSubtotal;
	}

	public JLabel getLbl_T() {
		if(lbl_T == null) {
			lbl_T = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.total"));
			lbl_T.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_T.setBounds(269, 340, 88, 20);
		}
		return lbl_T;
	}

	public JLabel getLblTotal() {
		if(lblTotal == null) {
			lblTotal = new JLabel();
			lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotal.setBounds(650, 207, 109, 20);
		}
		return lblTotal;
	}

	public JLabel getLbl_TP() {
		if(lbl_TP == null) {
			lbl_TP = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalPagado"));
			lbl_TP.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_TP.setBounds(269, 380, 88, 20);
		}
		return lbl_TP;
	}

	public JLabel getLblTotalPagado() {
		if(lblTotalPagado == null) {
			lblTotalPagado = new JLabel();
			lblTotalPagado.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalPagado.setBounds(269, 270, 109, 20);
		}
		return lblTotalPagado;
	}

	public JLabel getLblSubtotalValor() {
		if(lblSubtotalValor == null) {
			lblSubtotalValor = new JLabel();
			lblSubtotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSubtotalValor.setBounds(396, 300, 88, 20);
		}
		return lblSubtotalValor;
	}

	public JLabel getLblTotalValor() {
		if(lblTotalValor == null) {
			lblTotalValor = new JLabel();
			lblTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalValor.setBounds(396, 340, 88, 20);
		}
		return lblTotalValor;
	}

	public JLabel getLblTotalPagadoValor() {
		if(lblTotalPagadoValor == null) {
			lblTotalPagadoValor = new JLabel();
			lblTotalPagadoValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalPagadoValor.setBounds(396, 380, 88, 20);
		}
		return lblTotalPagadoValor;
	}

	public JButton getBtnAgregar() {
		if(btnAgregar == null) {
			btnAgregar = new JButton(PropertiesManager.getProperty("pagoPanel.boton.agregar"));
			btnAgregar.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.agregar"));
			
			btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));
			btnAgregar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnAgregar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnAgregar.setMargin(new Insets(-1, -1, -1, -1));
			
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						agregarPago();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnAgregar.setBounds(188, 117, 60, 60);
		}
		return btnAgregar;
	}

	public JLabel getLblMonto() {
		if(lblMonto == null) {
			lblMonto = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.monto"));
			lblMonto.setHorizontalAlignment(SwingConstants.LEFT);
			lblMonto.setBounds(44, 148, 110, 20);
		}
		return lblMonto;
	}

	public JComboBox<MedioPagoDto> getCmbMedioPago() {
		if(cmbMedioPago == null) {
			cmbMedioPago = new JComboBox<MedioPagoDto>(new MitnickComboBoxModel<MedioPagoDto>());
			cmbMedioPago.setBounds(44, 117, 110, 20);
		}
		return cmbMedioPago;
	}

	public JLabel getLblMedioPago() {
		if(lblMedioPago == null) {
			lblMedioPago = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.medioPago"));
			lblMedioPago.setHorizontalAlignment(SwingConstants.LEFT);
			lblMedioPago.setBounds(44, 86, 115, 20);
		}
		return lblMedioPago;
	}

	public JButton getBtnVolver() {
		if(btnVolver == null) {
			btnVolver = new JButton(PropertiesManager.getProperty("pagoPanel.boton.volver"));
			btnVolver.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.volver"));
			btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			btnVolver.setBounds(680, 290, 60, 60);
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarCuentaCorrientePanel();
					}
					catch(PresentationException ex) {
						
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVolver;
	}
	
	public JButton getBtnFinalizar() {
		if(btnFinalizar == null) {
			btnFinalizar = new JButton(PropertiesManager.getProperty("pagoPanel.boton.finalizar"));
			btnFinalizar.setToolTipText(PropertiesManager.getProperty("pagoPanel.boton.finalizar"));
			btnFinalizar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnFinalizar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnFinalizar.setMargin(new Insets(-1, -1, -1, -1));
			btnFinalizar.setBounds(680, 364, 60, 60);
			btnFinalizar.setIcon(new ImageIcon(this.getClass().getResource("/img/print.png")));
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.finalizarPagoCuotaParcial();
					}
					catch(PresentationException ex) {
						
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnFinalizar;
	}
	
	public JButton getBtnQuitar() {
		if(btnQuitar == null) {
			btnQuitar = new JButton(PropertiesManager.getProperty("pagoPanel.button.quitar"));
			btnQuitar.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.quitar"));
			
			btnQuitar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnQuitar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnQuitar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnQuitar.setMargin(new Insets(-1, -1, -1, -1));
			
			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					logger.info("Quitando pago ... ");

					try {
						int index = table.getSelectedRow();
						PagoDto pagoDto = model.getPago(index);
						
						int opcion = mostrarMensajeAdvertencia(PropertiesManager.getProperty("pagoPanel.dialog.confirm.quitar"));
						
						if ( opcion == JOptionPane.YES_OPTION) {
							controller.quitarPago(pagoDto);	
						}
					}
					catch (IndexOutOfBoundsException exception) {
						if(model.getRowCount() == 0) {
							mostrarMensajeError(PropertiesManager.getProperty("error.pagoPanel.pagos.vacio"));
						}
						else {
							mostrarMensajeError(PropertiesManager.getProperty("error.pagoPanel.pago.noSeleccionado"));
						}
					}
					catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
			btnQuitar.setBounds(188, 186, 60, 60);
		}
		return btnQuitar;
	}

	public JLabel getLblAPagarValor() {
		if(lblAPagarValor == null) {
			lblAPagarValor = new JLabel();
			lblAPagarValor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAPagarValor.setBounds(396, 420, 88, 20);
		}
		return lblAPagarValor;
	}

	public JLabel getLblTotalAPagar() {
		if(lblTotalAPagar == null) {
			lblTotalAPagar = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalAPagar"));
			lblTotalAPagar.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalAPagar.setBounds(269, 420, 88, 20);
		}
		return lblTotalAPagar;
	}

	public PagoTableModel getModel() {
		if(model == null) {
			model = new PagoTableModel();
		}
		return model;
	}
	
	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{cmbMedioPago,txtMonto}));
	}
	
	protected void agregarPago() {
		try {
			MedioPagoDto pago = (MedioPagoDto)cmbMedioPago.getSelectedItem();
			
			if (pago.isNotaCredito()) {
				String nroNC = JOptionPane.showInputDialog(PropertiesManager.getProperty("pagoPanel.notaCredito.numeroTicket"));
				if (nroNC == null)
					return;
				CreditoDto credito = controller.obtenerCredito(nroNC);
				if (credito == null){
					int option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("pagoPanel.notaCredito.noExiste"), "Error", JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.CANCEL_OPTION)
						return;						
				} else {
					
					if (credito.getDisponible().compareTo(new BigDecimal(txtMonto.getText()))<0){
						int option = JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("pagoPanel.notaCredito.disponibleMenorAlTotal", new Object[]{credito.getDisponible()}), "Error", JOptionPane.OK_CANCEL_OPTION);
						if (option == JOptionPane.CANCEL_OPTION)
							return;
					}
								
					controller.agregarPago(pago, txtMonto.getText(), nroNC);
				}
			} else
				controller.agregarPago(pago, txtMonto.getText(), null);
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void limpiarCamposPantalla() {
		if(Validator.isNotNull(txtMonto))
			txtMonto.setText("");
		if(Validator.isNotNull(model))
			model.setPagos(new ArrayList<PagoDto>());
	}

	public void actualizarPantalla() {
		if (cuotas!=null){
			BigDecimal total = new BigDecimal(0);
			BigDecimal subtotal = new BigDecimal(0);
			BigDecimal pagado = new BigDecimal(0);
			BigDecimal aPagar = new BigDecimal(0);
			List<PagoDto> pagos = new ArrayList<PagoDto>();
			for (int i = 0; i < cuotas.size(); i++) {
				CuotaDto cuota = cuotas.get(i);
				total = total.add(cuota.getTotal());
				subtotal = subtotal.add(cuota.getTotal());
				pagado = pagado.add(cuota.getTotalPagado());
				aPagar = aPagar.add(cuota.getFaltaPagar());
				pagos.addAll(cuota.getPagos());
			}
			
			if(Validator.isNotNull(lblSubtotalValor)) 
				lblSubtotalValor.setText(subtotal.toString());
			if(Validator.isNotNull(lblTotalValor))
				lblTotalValor.setText(total.toString());
			if(Validator.isNotNull(lblTotalPagadoValor))
				lblTotalPagadoValor.setText(pagado.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			if(Validator.isNotNull(lblAPagarValor))
				lblAPagarValor.setText(aPagar.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			if(Validator.isNotNull(cmbMedioPago)) {
				List<MedioPagoDto> medioPagoList = new ArrayList<MedioPagoDto>();
				
				try {
					medioPagoList = controller.getMediosPagoCuentaCorriente();
				} catch(BusinessException e) {
					mostrarMensaje(new PresentationException(e));
				} 
				
				cmbMedioPago.setModel(new MitnickComboBoxModel<MedioPagoDto>());
				((MitnickComboBoxModel<MedioPagoDto>)cmbMedioPago.getModel()).addItems(medioPagoList);
			}
			if(Validator.isNotNull(model)) {
				model.setPagos(pagos);			
			}
			if(Validator.isNotNull(txtMonto)) {
				txtMonto.setText(aPagar.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				txtMonto.requestFocus();
				txtMonto.setSelectionStart(0);
				txtMonto.setSelectionEnd(txtMonto.getText().length());
			}
		}

		
		
		if(Validator.isNotNull(cliente)){
			
			getLblNombre().setText(cliente.getNombre());
			getLblDni().setText(cliente.getDocumento());
			
			getPnlCliente().setVisible(true);
			getLblConsumidorFinal().setVisible(false);
		}		
	}
	
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtMonto;
	}
	
	public int mostrarMensajeReintentar() {
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.reintentar" ), PropertiesManager.getProperty( "dialog.error.cancelarVenta" )  };
	     
	     return JOptionPane.showOptionDialog( currentView, PropertiesManager.getProperty( "dialog.error.MensajeReintentar" ), PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}

	public JPanel getPnlCliente() {
		if(pnlCliente == null) {
			pnlCliente = new JPanel();
			pnlCliente.setLayout(null);
			pnlCliente.setBounds(188, 23, 335, 37);
			pnlCliente.add(getLblNombre());
			pnlCliente.add(getLblDni());
		}
		return pnlCliente;
	}

	public JLabel getLblNombre() {
		if(lblNombre == null) {
			lblNombre = new JLabel("<<Nombre>>");
			lblNombre.setBounds(10, 11, 182, 14);
			getPnlCliente().add(lblNombre);
		}
		return lblNombre;
	}
	
	public JLabel getLblDni() {
		if(lblDni == null) {
			lblDni = new JLabel("<<DNI>>");
			lblDni.setBounds(143, 11, 182, 14);
			getPnlCliente().add(lblDni);
		}
		return lblDni;
	}
	
	public JLabel getLblConsumidorFinal() {
		if(lblConsumidorFinal == null) {
			lblConsumidorFinal = new JLabel("Consumidor Final");
			lblConsumidorFinal.setBounds(188, 36, 115, 14);
		}
		return lblConsumidorFinal;
	}
	
	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnAgregar());
	}

	public void finalizarPagos() {
		try {
			mostrarMensajeInformativo(PropertiesManager.getProperty("pagoPanel.finalizarPago.exito", new Object[]{ "0.00" }));
			controller.actualizarCuotas(getCliente());
			controller.mostrarCuentaCorrientePanel();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public List<CuotaDto> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotaDto> cuotas) {
		this.cuotas = cuotas;
	}

	@Override
	protected void keyF5() {
		controller.finalizarPagoCuotaParcial();
	}
}
