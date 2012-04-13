package com.mitnick.presentacion.vistas.paneles;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.modelos.PagoTableModel;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;

@Panel("pagoPanel")
public class PagoPanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	
	private VentaController ventaController;
	
	private JTextField txtMonto;
	private JTable table;
	private JScrollPane scrollPane;
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
	private JButton btnAgregar;
	private JLabel lblMonto;
	private JComboBox<MedioPagoDto> cmbMedioPago;
	private JLabel lblMedioPago;
	private JButton btnVolver;
	private JButton btnQuitar;
	private JLabel lblAPagarValor;
	private JLabel lblTotalAPagar;
	
	private PagoTableModel pagoTableModel;
	
	@Autowired
	public PagoPanel(@Qualifier("ventaController") VentaController ventaController) {
		this.ventaController = ventaController;
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
		setLayout(null);
		setSize(new Dimension(815, 470));
		
		lblMedioPago = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.medioPago"));
		lblMedioPago.setHorizontalAlignment(SwingConstants.LEFT);
		lblMedioPago.setBounds(44, 86, 115, 20);
		add(lblMedioPago);
		
		cmbMedioPago = new JComboBox<MedioPagoDto>(new MitnickComboBoxModel<MedioPagoDto>());
		cmbMedioPago.setBounds(44, 117, 110, 20);
		add(cmbMedioPago);
		
		lblMonto = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.monto"));
		lblMonto.setHorizontalAlignment(SwingConstants.LEFT);
		lblMonto.setBounds(44, 148, 80, 20);
		add(lblMonto);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(44, 176, 110, 20);
		txtMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					agregarPago();
				}
			}
		});
		add(txtMonto);
		txtMonto.setColumns(10);
		
		pagoTableModel = new PagoTableModel();
		
		table = new JTable(pagoTableModel);
		table.setBounds(0, 0, 1, 1);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(269, 115, 516, 143);
		add(scrollPane);
		
		lblPagosRealizados = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.pagosRealizados"));
		lblPagosRealizados.setBounds(269, 86, 141, 20);
		add(lblPagosRealizados);
		
		lbl_ST = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.subtotal"));
		lbl_ST.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_ST.setBounds(269, 299, 88, 20);
		add(lbl_ST);
		
		lblSubtotal = new JLabel();
		lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubtotal.setBounds(650, 176, 109, 20);
		add(lblSubtotal);
		
		lbl_T = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.total"));
		lbl_T.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_T.setBounds(269, 348, 88, 20);
		add(lbl_T);
		
		lblTotal = new JLabel();
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(650, 207, 109, 20);
		add(lblTotal);
		
		lbl_TP = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalPagado"));
		lbl_TP.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_TP.setBounds(269, 384, 88, 20);
		add(lbl_TP);
		
		lblTotalPagado = new JLabel();
		lblTotalPagado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPagado.setBounds(269, 270, 109, 20);
		add(lblTotalPagado);
		
		lblTotalAPagar = new JLabel(PropertiesManager.getProperty("pagoPanel.etiqueta.totalAPagar"));
		lblTotalAPagar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalAPagar.setBounds(269, 420, 88, 20);
		add(lblTotalAPagar);
		
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
		btnAgregar.setBounds(188, 86, 60, 60);
		add(btnAgregar);
		
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
					PagoDto pagoDto = pagoTableModel.getPago(index);
					
					int opcion = mostrarMensajeAdvertencia(PropertiesManager.getProperty("pagoPanel.dialog.confirm.quitar"));
					
					if ( opcion == JOptionPane.YES_OPTION) {
						ventaController.quitarPago(pagoDto);	
					}
				}
				catch (IndexOutOfBoundsException exception) {
					if(pagoTableModel.getRowCount() == 0) {
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
		btnQuitar.setBounds(188, 157, 60, 60);
		add(btnQuitar);
		
		btnVolver = new JButton(PropertiesManager.getProperty("pagoPanel.boton.volver"));
		btnVolver.setToolTipText(PropertiesManager.getProperty("pagoPanel.tooltip.volver"));
		btnVolver.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVolver.setMargin(new Insets(-1, -1, -1, -1));
		btnVolver.setBounds(680, 364, 60, 60);
		btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ventaController.mostrarVentasPanel();
				}
				catch(PresentationException ex) {
					
					mostrarMensaje(ex);
				}
			}
		});
		
		add(btnVolver);
		
		lblSubtotalValor = new JLabel();
		lblSubtotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubtotalValor.setBounds(396, 299, 88, 20);
		add(lblSubtotalValor);
		
		lblTotalValor = new JLabel();
		lblTotalValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalValor.setBounds(396, 348, 88, 20);
		add(lblTotalValor);
		
		lblTotalPagadoValor = new JLabel();
		lblTotalPagadoValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPagadoValor.setBounds(396, 384, 88, 20);
		add(lblTotalPagadoValor);
		
		lblAPagarValor = new JLabel();
		lblAPagarValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAPagarValor.setBounds(396, 420, 88, 20);
		add(lblAPagarValor);
	}
	
	protected void agregarPago() {
		try {
			ventaController.agregarPago((MedioPagoDto)cmbMedioPago.getSelectedItem(), txtMonto.getText());
			
			
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void limpiarCamposPantalla() {
		if(Validator.isNotNull(txtMonto))
			txtMonto.setText("");
		if(Validator.isNotNull(pagoTableModel))
			pagoTableModel.setPagos(new ArrayList<PagoDto>());
	}

	public void actualizarPantalla() {
		if(Validator.isNotNull(lblSubtotalValor)) 
			lblSubtotalValor.setText(VentaManager.getVentaActual().getSubTotal().toString());
		if(Validator.isNotNull(lblTotalValor))
			lblTotalValor.setText(VentaManager.getVentaActual().getTotal().toString());
		if(Validator.isNotNull(lblTotalPagadoValor))
			lblTotalPagadoValor.setText(VentaManager.getVentaActual().getTotalPagado().toString());
		if(Validator.isNotNull(lblAPagarValor))
			lblAPagarValor.setText(VentaManager.getVentaActual().getFaltaPagar().toString());
		if(Validator.isNotNull(cmbMedioPago)) {
			List<MedioPagoDto> medioPagoList = new ArrayList<MedioPagoDto>();
			
			try {
				medioPagoList = ventaController.getAllMedioPago();
			}
			catch(BusinessException e) {
				;
			} 
			
			cmbMedioPago.setModel(new MitnickComboBoxModel<MedioPagoDto>());
			((MitnickComboBoxModel<MedioPagoDto>)cmbMedioPago.getModel()).addItems(medioPagoList);
		}
		if(Validator.isNotNull(pagoTableModel)) {
			pagoTableModel.setPagos(VentaManager.getVentaActual().getPagos());
		}
		if(Validator.isNotNull(txtMonto)) {
			txtMonto.setText("");
			txtMonto.requestFocus();
		}
	}
	
	public void finalizarVenta() {
		try {
			mostrarMensajeInformativo(PropertiesManager.getProperty("pagoPanel.finalizarVenta.exito", new Object[]{ VentaManager.getVentaActual().getVuelto().toString() }));
			ventaController.crearNuevaVenta();
			ventaController.limpiarVenta();
			ventaController.mostrarVentasPanel();
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	public int mostrarMensajeReintentar() {
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.reintentar" ), PropertiesManager.getProperty( "dialog.error.cancelarVenta" )  };
	     
	     return JOptionPane.showOptionDialog( currentView, PropertiesManager.getProperty( "dialog.error.MensajeReintentar" ), PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
}
