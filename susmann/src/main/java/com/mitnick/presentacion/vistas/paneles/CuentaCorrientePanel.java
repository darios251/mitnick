package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.CuotaTableModel;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;

@Panel("cuentaCorrientePanel")
public class CuentaCorrientePanel extends BasePanel<ClienteController> {
	
	private JPanel pnlCliente;
	private JLabel lblNombre;
	private JLabel lblDomicilio;
	private JLabel lblTelefono;
	
	private JLabel lblCliente;
	
	private JLabel lblTotal;
	
	private static final long serialVersionUID = 1L;
	
	private List<CuotaDto> cuotas;
	private ClienteDto cliente;

	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnPagar;
	private JButton btnCancelarComprobante;
	private JButton btnVolver;
	private CuotaTableModel model;
	
	@Autowired(required = true)
	public CuentaCorrientePanel(@Qualifier("clienteController") ClienteController clienteController) {
		controller = clienteController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public CuentaCorrientePanel(boolean modoDisenio) {
		initializeComponents();

		throw new PresentationException("error.unknow",	"este constructor es solo parar el plugin de diseño");
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		model.setCuotas(new ArrayList<CuotaDto>());
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(850, 550));

		add(getScrollPane());
		
		add(getBtnNuevo());
		add(getBtnEliminar());
		add(getBtnModificar());
		add(getBtnPagar());
		add(getBtnCancelarComprobante());
		add(getBtnVolver());
		
		add(getPnlCliente());		
		
		add(getLblTotal());
		add(getLblTotalOriginal()); add(getBtnConfirmar()); //agregado para susmann
		setFocusTraversalPolicy();
	}

	
	
	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { getBtnNuevo() }));
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
			btnNuevo = new JButton(PropertiesManager.getProperty("cuentaPanel.button.nuevo.texto"));
			btnNuevo.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.nuevo.tooltip"));
			btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/agregar.png")));

			btnNuevo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNuevo.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
			btnNuevo.setBounds(735, 86, 60, 60);

			btnNuevo.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {		
					nuevaCuota();					
				}
			});
		}
		return btnNuevo;
	}
	
	public JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton(PropertiesManager.getProperty("cuentaPanel.button.eliminar.texto"));
			btnEliminar.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.eliminar.tooltip"));
			btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));

			btnEliminar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
			btnEliminar.setBounds(735, 157, 60, 60);

			btnEliminar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int opcion = mostrarMensajeConsulta(PropertiesManager.getProperty("cuentaPanel.dialog.confirm.eliminar"));

					if (opcion == JOptionPane.YES_OPTION) {
						try {
							controller.eliminarCuota();
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
	
	public JButton getBtnModificar() {
		if (btnEditar == null) {
			btnEditar = new JButton("Modificar");
			btnEditar.setToolTipText("Modificar");
			btnEditar.setIcon(new ImageIcon(this.getClass().getResource("/img/editar.png")));

			btnEditar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnEditar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnEditar.setMargin(new Insets(-1, -1, -1, -1));
			btnEditar.setBounds(735, 228, 60, 60);

			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						editarCuota();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnEditar;
	}
	
	public JButton getBtnPagar() {
		if (btnPagar == null) {
			btnPagar = new JButton(PropertiesManager.getProperty("cuentaPanel.button.pagar.texto"));
			btnPagar.setToolTipText(PropertiesManager.getProperty("cuentaPanel.button.pagar.tooltip"));
			btnPagar.setIcon(new ImageIcon(this.getClass().getResource("/img/payment-icon.png")));

			btnPagar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnPagar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnPagar.setMargin(new Insets(-1, -1, -1, -1));
			btnPagar.setBounds(735, 299, 60, 60);

			btnPagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarCuentaCorrientePagoPanel();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
					
				}
			});
		}
		return btnPagar;
	}
	
	public JButton getBtnCancelarComprobante() {
		if(btnCancelarComprobante == null) {
			btnCancelarComprobante = new JButton(PropertiesManager.getProperty("pagoPanel.boton.cancelarComprobante"));
			btnCancelarComprobante.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.cancelarComprobante"));
			btnCancelarComprobante.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCancelarComprobante.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCancelarComprobante.setMargin(new Insets(-1, -1, -1, -1));
			btnCancelarComprobante.setBounds(735, 370, 60, 60);
			btnCancelarComprobante.setIcon(new ImageIcon(this.getClass().getResource("/img/remove-ticket.png")));
			btnCancelarComprobante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String nroComprobante = JOptionPane.showInputDialog(PropertiesManager.getProperty("clientePanel.cuentaCorriente.messageQuery.cancelar.nroComprobante"));
						if (Validator.isNotBlankOrNull(nroComprobante)){
							if (controller.cancelarComprobante(nroComprobante)){
								mostrarMensajeInformativo(PropertiesManager.getProperty("clientePanel.cuentaCorriente.messageQuery.cancelar.nroComprobante.ok"));
								actualizarPantalla();
							}
						}
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnCancelarComprobante;
	}
	
	public JButton getBtnVolver() {
		if(btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.volver"));
			btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			btnVolver.setBounds(735, 441, 60, 60);
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.mostrarClientePanel();
					}
					catch(PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return btnVolver;
	}
	
	public void nuevaCuota(){
		CuotaDto cuotaDto = new CuotaDto();
		cuotaDto.setClienteDto(getCliente());
		actualizarCuotas(cuotaDto);
	}

	private void actualizarCuotas(CuotaDto cuotaDto){
		new NuevaCuotaDialog((JFrame) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent(), getCliente(), cuotaDto, this.controller);
		actualizarPantalla();
	}
	
	private void editarCuota(){
		try {
			int index = getTable().getSelectedRow();
			CuotaDto cuotaDto = getModel().getCuota(index);
			actualizarCuotas(cuotaDto);
		} catch (IndexOutOfBoundsException exception) {
				throw new PresentationException("error.cuentaPanel.cuota.noSeleccionado");	
		}
			
	}
	
	public JPanel getPnlCliente() {
		if(pnlCliente == null) {
			pnlCliente = new JPanel();
			pnlCliente.setLayout(null);
			pnlCliente.setBounds(20, 23, 800, 39);
			pnlCliente.add(getLblCliente());
			pnlCliente.add(getLblNombre());
			pnlCliente.add(getLblDomicilio());
			pnlCliente.add(getLblTelefono());
		}
		return pnlCliente;
	}
	
	private JLabel getLblCliente() {
		if(lblCliente == null) {
			lblCliente = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.cliente"));
			lblCliente.setBounds(10, 11, 50, 14);	
		}
		return lblCliente;
	}
	
	public JLabel getLblNombre() {
		if(lblNombre == null) {
			lblNombre = new JLabel(cliente.getNombre());
			lblNombre.setBounds(50, 11, 251, 14);			
		}
		return lblNombre;
	}
	
	public JLabel getLblTelefono() {
		if(lblTelefono == null) {
			if (Validator.isNotBlankOrNull(cliente.getTelefono()))
				lblTelefono = new JLabel("("+cliente.getTelefono()+")");
			else
				lblTelefono = new JLabel("");
			lblTelefono.setBounds(250, 11, 200, 14);
		}
		return lblTelefono;
	}
	
	public JLabel getLblDomicilio() {
		if(lblDomicilio == null) {
			if (Validator.isNotNull(cliente.getDireccion()))
				lblDomicilio = new JLabel(cliente.getDireccion().getDomicilio());
			else
				lblDomicilio = new JLabel("");
			lblDomicilio.setBounds(450, 11, 300, 14);	
		}
		return lblDomicilio;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
			table.setBounds(0, 0, 1, 1);
		}
		return table;
	}

	public CuotaTableModel getModel() {
		if (model == null) {
			model = new CuotaTableModel();
		}
		return model;
	}

	private JLabel getLblTotal() {
		if(lblTotal == null) {
			lblTotal = new JLabel(PropertiesManager.getProperty("cuentaCorrientePanel.total.cuotas", new Object[]{new BigDecimal(0)}));
			lblTotal.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
			lblTotal.setBounds(580, 441, 130, 60);	
		}
		return lblTotal;
	}
	
	@Override
	public void actualizarPantalla() {	
		controller.actualizarCuotas(getCliente());
		
		lblNombre.setText(cliente.getNombre());
		
		if (Validator.isNotNull(cliente.getDireccion()))
			lblDomicilio.setText(cliente.getDireccion().getDomicilio());
		else
			lblDomicilio.setText("");
		
		if (Validator.isNotBlankOrNull(cliente.getTelefono()))
			lblTelefono.setText("("+cliente.getTelefono()+")");		
		else
			lblTelefono.setText("");		
		
		model.setCuotas(getCuotas());
		actualizarTotal();
		
	}
	
	private void actualizarTotal(){
		BigDecimal total = new BigDecimal(0);
		if(Validator.isNotNull(cuotas)){
			for (CuotaDto cuota: cuotas){
				total = total.add(cuota.getFaltaPagar());
			}
		}
		lblTotal.setText(PropertiesManager.getProperty("cuentaCorrientePanel.total.cuotas", new Object[]{total}));
	}

	@Override
	public void setDefaultFocusField() {
		
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(this.btnEditar);
	}
	
	@Override
	protected void keyEscape() {
		btnVolver.doClick();
	}
	
	@Override
	protected void keyAdd() {
		btnNuevo.doClick();
	}
	
	@Override
	protected void keySubstract() {
		btnEliminar.doClick();
	}
	
	@Override
	protected void keyMultiply() {
		btnEditar.doClick();
	}
	
	@Override
	protected void keyF1() {
		btnPagar.doClick();
	}

	public List<CuotaDto> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotaDto> cuotas) {
		this.cuotas = cuotas;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
		actualizarTotalOriginal();
	}
	
	//Agregado para susmann
	private JLabel lblTotalOriginal;
	private JButton btnConfirmar;
	
	private JLabel getLblTotalOriginal() {
		if(lblTotalOriginal == null) {
			lblTotalOriginal = new JLabel("Total Original: $ 0");
			lblTotalOriginal.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
			lblTotalOriginal.setBounds(280, 441, 180, 60);	
		}
		actualizarTotalOriginal();
		return lblTotalOriginal;
	}
	
	public void actualizarTotalOriginal(){
		if(lblTotalOriginal != null) {
			BigDecimal total = new BigDecimal(0);
			if(Validator.isNotNull(cuotas)){
				for (CuotaDto cuota: cuotas){
					total = total.add(cuota.getFaltaPagar());
				}
			}
			lblTotalOriginal.setText("Total Original: $ " + total);
		}
	}
	
	public JButton getBtnConfirmar() {
		if (btnConfirmar == null) {
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.setToolTipText("Confirma los cambios en el monto que adeuda el cliente");
			btnConfirmar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));

			btnConfirmar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnConfirmar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnConfirmar.setMargin(new Insets(-1, -1, -1, -1));
			btnConfirmar.setBounds(300, 480, 60, 60);

			btnConfirmar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {		
					actualizarTotalOriginal();				
				}
			});
		}
		return btnConfirmar;
	}
}
