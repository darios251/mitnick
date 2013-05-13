package com.mitnick.presentacion.vistas.paneles;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ReportesController;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.AllowBlankMaskFormatter;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;

@Panel("reportesPanel")
public class ReportesPanel extends BasePanel<ReportesController> {

	private static final long serialVersionUID = 1L;

	private JTextField txtFechaInicio;
	private JTextField txtFechaFinal;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;

	private JButton btnReporteCajero;
	private JButton btnReporteCaja;
	private JButton btnReporteVentas;
	private JButton btnReporteVentasDiario;
	private JButton btnReporteVentasMensual;
	private JButton btnReporteVentasAnual;
	

	private JButton btnReporteDeEstado;
	private JButton btnReporteDeEstadoPorCliente;
	private JButton btnListadoDeRecibo;

	private JButton btnReporteFacturas;
	
	private JButton btnConsultarTransaccion;
	
	@Autowired
	public ReportesPanel(@Qualifier("reportesController") ReportesController reportesController) {
		controller = reportesController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ReportesPanel(boolean modoDisenio) {
		initializeComponents();
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		try {
			txtFechaInicio.setText(DateHelper.getFecha(new Date()));
			txtFechaFinal.setText(DateHelper.getFecha(new Date()));
		} catch (Exception e) {
		}
	}

	@Override
	protected void initializeComponents() {

		setLayout(null);
		setSize(new Dimension(815, 470));

		add(getLblFechaInicio());
		add(getLblFechaFin());
		
		add(getTxtFechaInicio());
		add(getTxtFechaFinal());
		
		add(getBtnReporteCajero());
		add(getBtnReporteCaja());
		add(getBtnReporteVentas());
		add(getBtnReporteVentasDiario());
		add(getBtnReporteVentasMensual());
		add(getBtnReporteVentasAnual());
				
		add(getBtnListadoDeRecibos());		
		add(getBtnReporteDeEstado());
		add(getBtnReporteDeEstadoPorCliente());
		
		add(getBtnReporteFacturas());
		add(getBtnConsultarTransaccion());
		
		add(getImagenFondo());
		
		setFocusTraversalPolicy();
		this.actualizarPantalla();
	}

	public JButton getBtnReporteCajero() {
		if (btnReporteCajero == null) {
			btnReporteCajero = new JButton();
			btnReporteCajero.setText(PropertiesManager.getProperty("reportePanel.label.reporteCajero"));
			btnReporteCajero.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteCajero"));

			btnReporteCajero.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteCajero.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteCajero.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteCajero.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarCajaActual();
				}
			});
			btnReporteCajero.setBounds(200, 120, 330, 20);
		}
		return btnReporteCajero;
	}
	
	public JButton getBtnReporteCaja() {
		if (btnReporteCaja == null) {
			btnReporteCaja = new JButton();
			btnReporteCaja.setText(PropertiesManager.getProperty("reportePanel.label.reporteCaja"));
			btnReporteCaja.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteCaja"));

			btnReporteCaja.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteCaja.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteCaja.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteCaja.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarCaja();
				}
			});
			btnReporteCaja.setBounds(200, 150, 330, 20);
		}
		return btnReporteCaja;
	}

	public JButton getBtnReporteVentas() {
		if (btnReporteVentas == null) {
			btnReporteVentas = new JButton();
			btnReporteVentas.setText(PropertiesManager.getProperty("reportePanel.label.reporteVentas"));
			btnReporteVentas.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteVentas"));

			btnReporteVentas.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentas.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentas.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarIngresos(IReportesServicio.TRANSACCIONAL);
				}
			});
			btnReporteVentas.setBounds(200, 180, 330, 20);
		}
		return btnReporteVentas;
	}
	
	public JButton getBtnReporteVentasDiario() {
		if (btnReporteVentasDiario == null) {
			btnReporteVentasDiario = new JButton();
			btnReporteVentasDiario.setText(PropertiesManager.getProperty("reportePanel.label.reporteVentas.diario"));
			btnReporteVentasDiario.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteVentas.diario"));

			btnReporteVentasDiario.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentasDiario.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentasDiario.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentasDiario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarIngresos(IReportesServicio.DIARIO);
				}
			});
			btnReporteVentasDiario.setBounds(200, 210, 330, 20);
		}
		return btnReporteVentasDiario;
	}
	public JButton getBtnReporteVentasMensual() {
		if (btnReporteVentasMensual == null) {
			btnReporteVentasMensual = new JButton();
			btnReporteVentasMensual.setText(PropertiesManager.getProperty("reportePanel.label.reporteVentas.mensual"));
			btnReporteVentasMensual.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteVentas.mensual"));

			btnReporteVentasMensual.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentasMensual.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentasMensual.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentasMensual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarIngresos(IReportesServicio.MENSUAL);
				}
			});
			btnReporteVentasMensual.setBounds(200, 240, 330, 20);
		}
		return btnReporteVentasMensual;
	}
	public JButton getBtnReporteVentasAnual() {
		if (btnReporteVentasAnual == null) {
			btnReporteVentasAnual = new JButton();
			btnReporteVentasAnual.setText(PropertiesManager.getProperty("reportePanel.label.reporteVentas.anual"));
			btnReporteVentasAnual.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteVentas.anual"));

			btnReporteVentasAnual.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentasAnual.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentasAnual.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentasAnual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarIngresos(IReportesServicio.ANUAL);
				}
			});
			btnReporteVentasAnual.setBounds(200, 270, 330, 20);
		}
		return btnReporteVentasAnual;
	}
	
	private JButton getBtnListadoDeRecibos() {
		if (btnListadoDeRecibo == null) {
			btnListadoDeRecibo = new JButton();
			btnListadoDeRecibo.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnListadoDeRecibo.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.listadoRecibo"));
			btnListadoDeRecibo.setText(PropertiesManager.getProperty("reportePanel.label.listadoRecibo"));
			btnListadoDeRecibo.setMargin(new Insets(-1, -1, -1, -1));
			btnListadoDeRecibo.setHorizontalTextPosition(SwingConstants.CENTER);
			btnListadoDeRecibo.setBounds(200, 310, 330, 20);
			btnListadoDeRecibo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarListadoDeRecibos();
				}
			});
		}
		return btnListadoDeRecibo;
	}
	
	private JButton getBtnReporteDeEstado() {
		if (btnReporteDeEstado == null) {
			btnReporteDeEstado = new JButton();
			btnReporteDeEstado.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteDeEstado.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.estadoCuotas"));
			btnReporteDeEstado.setText(PropertiesManager.getProperty("reportePanel.label.estadoCuotas"));
			btnReporteDeEstado.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteDeEstado.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteDeEstado.setBounds(200, 340, 330, 20);
			btnReporteDeEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarEstadoCuentas();
				}
			});
		}
		return btnReporteDeEstado;
	}
	
	private JButton getBtnReporteDeEstadoPorCliente() {
		if (btnReporteDeEstadoPorCliente == null) {
			btnReporteDeEstadoPorCliente = new JButton();
			btnReporteDeEstadoPorCliente.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteDeEstadoPorCliente.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.estadoCuentas"));
			btnReporteDeEstadoPorCliente.setText(PropertiesManager.getProperty("reportePanel.label.estadoCuentas"));
			btnReporteDeEstadoPorCliente.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteDeEstadoPorCliente.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteDeEstadoPorCliente.setBounds(200, 380, 330, 20);
			btnReporteDeEstadoPorCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarEstadoCuentasClientes();
				}
			});
		}
		return btnReporteDeEstadoPorCliente;
	}
	
	private JButton getBtnReporteFacturas() {
		if (btnReporteFacturas == null) {
			btnReporteFacturas = new JButton();
			btnReporteFacturas.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteFacturas.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.reporteFacturas"));
			btnReporteFacturas.setText(PropertiesManager.getProperty("reportePanel.label.reporteFacturas"));
			btnReporteFacturas.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteFacturas.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteFacturas.setBounds(200, 420, 330, 20);
			btnReporteFacturas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarReporteFacturas();
				}
			});
		}
		return btnReporteFacturas;
	}
	
	private JButton getBtnConsultarTransaccion() {
		if (btnConsultarTransaccion == null) {
			btnConsultarTransaccion = new JButton();
			btnConsultarTransaccion.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnConsultarTransaccion.setToolTipText(PropertiesManager.getProperty("reportePanel.tooltip.consultaTransaccion"));
			btnConsultarTransaccion.setText(PropertiesManager.getProperty("reportePanel.label.consultaTransaccion"));
			btnConsultarTransaccion.setMargin(new Insets(-1, -1, -1, -1));
			btnConsultarTransaccion.setHorizontalTextPosition(SwingConstants.CENTER);
			btnConsultarTransaccion.setBounds(200, 450, 330, 20);
			btnConsultarTransaccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarTransaccion();
				}
			});
		}
		return btnConsultarTransaccion;
	}
	
	private JLabel getImagenFondo() {
		JLabel img = new JLabel(" ");

		img.setIcon(new ImageIcon(this.getClass().getResource("/img/estadisticas.png")));
		img.setSize(256,256);
		img.setLocation(500,300);
		img.setVisible(true); 
		
		return img;
	}
	
	public JLabel getLblFechaInicio() {
		if (lblFechaInicio == null) {
			lblFechaInicio = new JLabel(PropertiesManager.getProperty("label.inicio"));
			lblFechaInicio.setBounds(125, 95, 90, 20);
		}
		return lblFechaInicio;
	}

	public JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel(PropertiesManager.getProperty("label.fin"));
			lblFechaFin.setBounds(350, 95, 60, 20);
		}
		return lblFechaFin;
	}
	
	

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { txtFechaInicio, txtFechaFinal }));
	}

	

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtFechaInicio();
	}

	private JTextField getTxtFechaInicio() {
		if (txtFechaInicio == null) {
			try {
				txtFechaInicio = new JFormattedTextField(new AllowBlankMaskFormatter(MitnickConstants.DATE_MASKFORMAT)); 	
				txtFechaInicio.setColumns(10);
				txtFechaInicio.setBounds(200, 95, 110, 20);
				txtFechaInicio.setText(DateHelper.getFecha(new Date()));
			} catch (ParseException e) {}
		}
		return txtFechaInicio;
	}

	private JTextField getTxtFechaFinal() {
		if (txtFechaFinal == null) {
			try {
				txtFechaFinal = new JFormattedTextField(new AllowBlankMaskFormatter(MitnickConstants.DATE_MASKFORMAT)); 	
				txtFechaFinal.setColumns(10);
				txtFechaFinal.setBounds(420, 95, 110, 20);
				txtFechaFinal.setText(DateHelper.getFecha(new Date()));
			} catch (ParseException e) {}
		}
		return txtFechaFinal;
	}

	public Date getFechaInicio() {
		if (Validator.isNotBlankOrNull(txtFechaInicio.getText()))
			return DateHelper.getFecha(txtFechaInicio.getText());
		return null;
	}

	public Date getFechaFinal() {
		if (Validator.isNotBlankOrNull(txtFechaFinal.getText()))
			return DateHelper.getFecha(txtFechaFinal.getText());
		return null;
	}

	protected void setDefaultButton() {
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnReporteVentas());
	}

	@Override
	public void actualizarPantalla() {
		// TODO Auto-generated method stub
		
	}
		
	protected void consultarCaja() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.reporteCaja(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarCajaActual() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(new Date());
			dto.setFechaFin(new Date());
			controller.reporteCajero(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarIngresos(int tipo) {
		try {
			String año = JOptionPane.showInputDialog(PropertiesManager.getProperty("reportePanel.query.ingreseAno"));
			ReportesDto dto = new ReportesDto();
			if (Validator.isNotBlankOrNull(año)){
				if (Validator.isNumeric(año) && año.length()==4){
					String inicio = "01/01/".concat(año);
					String fin = "31/12/".concat(año);
					dto.setFechaInicio(DateHelper.getFecha(inicio));
					dto.setFechaFin(DateHelper.getFecha(fin));
				}
			} else {
				dto.setFechaInicio(getFechaInicio());
				dto.setFechaFin(getFechaFinal());
			}
			controller.reporteIngresos(dto, tipo);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
		
	protected void consultarListadoDeRecibos() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarListadoDeRecibo(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
		
	protected void consultarEstadoCuentas() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarEstadoCuentas(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}	
	
	protected void consultarEstadoCuentasClientes() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarEstadoCuentasCliente(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}	
		
	protected void consultarReporteFacturas() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarReporteFacturas(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}	
	
	protected void consultarTransaccion() {
		try {			
			String nroTrx = JOptionPane.showInputDialog(PropertiesManager.getProperty("reportePanel.query.numeroTransaccion"));
			if (Validator.isNotBlankOrNull(nroTrx))
				controller.consultarTransaccion(nroTrx);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}	
			

}
