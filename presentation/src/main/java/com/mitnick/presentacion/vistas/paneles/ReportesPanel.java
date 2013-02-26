package com.mitnick.presentacion.vistas.paneles;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
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

	private JButton btnReporteVentas;
	private JButton btnReporteVentasDiario;
	private JButton btnReporteVentasMensual;
	private JButton btnReporteVentasAnual;
	

	private JButton btnReporteDeEstado;
	private JButton btnListadoDeControl;

	private JButton btnReporteDeVentasProducto;
	private JButton btnReporteDeVentasZapatillas;	
	
	
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
			txtFechaInicio.setText("01/01/1900");
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
		
		add(getBtnReporteVentas());
		add(getBtnReporteVentasDiario());
		add(getBtnReporteVentasMensual());
		add(getBtnReporteVentasAnual());
		
//		add(getBtnReporteDeVentasProducto());
//		add(getBtnReporteDeVentasZapatillas());	

		add(getBtnListadoDeControl());
		add(getBtnReporteDeEstado());
		
		setFocusTraversalPolicy();
		this.actualizarPantalla();
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
			btnReporteVentas.setBounds(200, 150, 330, 20);
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
			btnReporteVentasDiario.setBounds(200, 180, 330, 20);
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
			btnReporteVentasMensual.setBounds(200, 210, 330, 20);
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
			btnReporteVentasAnual.setBounds(200, 240, 330, 20);
		}
		return btnReporteVentasAnual;
	}

	private JButton getBtnReporteDeVentasProducto() {
		if (btnReporteDeVentasProducto == null) {
			btnReporteDeVentasProducto = new JButton();
			btnReporteDeVentasProducto.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteDeVentasProducto.setToolTipText("productoPanel.tooltip.buscarProducto");
			btnReporteDeVentasProducto.setText("Reporte de Ventas por producto");
			btnReporteDeVentasProducto.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteDeVentasProducto.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteDeVentasProducto.setBounds(200, 213, 330, 20);
			btnReporteDeVentasProducto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarVentaPorArticulo();
				}
			});
		}
		return btnReporteDeVentasProducto;
	}
	
	private JButton getBtnReporteDeVentasZapatillas() {
		if (btnReporteDeVentasZapatillas == null) {
			btnReporteDeVentasZapatillas = new JButton();
			btnReporteDeVentasZapatillas.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteDeVentasZapatillas.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.reporteZapatillas"));
			btnReporteDeVentasZapatillas.setText(PropertiesManager.getProperty("productoPanel.button.reporteZapatillas"));
			btnReporteDeVentasZapatillas.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteDeVentasZapatillas.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteDeVentasZapatillas.setBounds(200, 244, 330, 20);			
			btnReporteDeVentasZapatillas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarVentaZapatilla();
				}
			});
		}
		return btnReporteDeVentasZapatillas;
	}
	
	private JButton getBtnListadoDeControl() {
		if (btnListadoDeControl == null) {
			btnListadoDeControl = new JButton();
			btnListadoDeControl.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnListadoDeControl.setToolTipText("productoPanel.tooltip.buscarProducto");
			btnListadoDeControl.setText("Listado de Control");
			btnListadoDeControl.setMargin(new Insets(-1, -1, -1, -1));
			btnListadoDeControl.setHorizontalTextPosition(SwingConstants.CENTER);
			btnListadoDeControl.setBounds(200, 280, 330, 20);
			btnListadoDeControl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarListadoDeControl();
				}
			});
		}
		return btnListadoDeControl;
	}
	
	private JButton getBtnReporteDeEstado() {
		if (btnReporteDeEstado == null) {
			btnReporteDeEstado = new JButton();
			btnReporteDeEstado.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteDeEstado.setToolTipText("productoPanel.tooltip.buscarProducto");
			btnReporteDeEstado.setText("Reporte de Estado de Cuentas");
			btnReporteDeEstado.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteDeEstado.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteDeEstado.setBounds(200, 310, 330, 20);
			btnReporteDeEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarEstadoCuentas();
				}
			});
		}
		return btnReporteDeEstado;
	}
	
	public JLabel getLblFechaInicio() {
		if (lblFechaInicio == null) {
			lblFechaInicio = new JLabel("Inicio");
			lblFechaInicio.setBounds(125, 95, 90, 20);
		}
		return lblFechaInicio;
	}

	public JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel("Fin");
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
				txtFechaInicio.setText("01/01/1900");
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
	
	protected void consultarIngresos(int tipo) {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.reporteIngresos(dto, tipo);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarListadoDeControl() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarListadoDeControl(dto);
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
	
	protected void consultarVentaPorArticulo() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarVentaPorArticulo(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarVentaZapatilla() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			controller.consultarVentaZapatillaPorTalle(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
			

}
