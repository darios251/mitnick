package com.mitnick.presentacion.vistas.paneles;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ReportesController;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;

@Panel("reportesPanel")
public class ReportesPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ReportesController reportesController;
	private JTextField txtFechaInicio;
	private JTextField txtFechaFinal;

	private JButton btnReporteVentas;
	private JButton btnReporteVentasAgrupado;;
	
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JButton btnReporteDeEstado;
	private JButton btnListadoDeControl;
	private JButton btnReporteDeVentasProducto;
	
	
	@Autowired
	public ReportesPanel(
			@Qualifier("reportesController") ReportesController reportesController) {
		this.reportesController = reportesController;
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
		
		btnReporteVentasAgrupado = new JButton();
		btnReporteVentasAgrupado.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnReporteVentasAgrupado.setToolTipText("productoPanel.tooltip.buscarProducto");
		btnReporteVentasAgrupado.setText("Reporte de Ventas agrupado por fecha");
		btnReporteVentasAgrupado.setMargin(new Insets(-1, -1, -1, -1));
		btnReporteVentasAgrupado.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReporteVentasAgrupado.setBounds(200, 182, 330, 20);
		add(btnReporteVentasAgrupado);
		add(getBtnReporteDeEstado());
		add(getBtnListadoDeControl());
		add(getBtnReporteDeVentasProducto());

		getBtnReporteVentasAgrupados();
		
		setFocusTraversalPolicy();
		this.actualizarPantalla();
	}



	public JButton getBtnReporteVentas() {
		if (btnReporteVentas == null) {
			btnReporteVentas = new JButton();
			btnReporteVentas.setText("Reporte de Ventas");
			btnReporteVentas.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.buscarProducto"));

			btnReporteVentas.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentas.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentas.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarIngresos();
				}
			});
			btnReporteVentas.setBounds(200, 151, 330, 20);
		}
		return btnReporteVentas;
	}
	
	public void getBtnReporteVentasAgrupados() {

		btnReporteVentasAgrupado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				consultarIngresosAgrupados();
			}
		});
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
			txtFechaInicio = new JTextField();
			txtFechaInicio.setColumns(10);
			txtFechaInicio.setBounds(200, 95, 110, 20);
			txtFechaInicio.setText("01/01/1900");
		}
		return txtFechaInicio;
	}

	private JTextField getTxtFechaFinal() {
		if (txtFechaFinal == null) {
			txtFechaFinal = new JTextField();
			txtFechaFinal.setColumns(10);
			txtFechaFinal.setBounds(420, 95, 110, 20);
			txtFechaFinal.setText(DateHelper.getFecha(new Date()));
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
	
	protected void consultarIngresos() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			reportesController.reporteIngresos(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarIngresosAgrupados() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			reportesController.reporteIngresosAgrupados(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarListadoDeControl() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			reportesController.consultarListadoDeControl(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarEstadoCuentas() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			reportesController.consultarEstadoCuentas(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	protected void consultarVentaPorArticulo() {
		try {
			ReportesDto dto = new ReportesDto();
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			reportesController.consultarVentaPorArticulo(dto);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
		
	
	private JButton getBtnReporteDeEstado() {
		if (btnReporteDeEstado == null) {
			btnReporteDeEstado = new JButton();
			btnReporteDeEstado.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteDeEstado.setToolTipText("productoPanel.tooltip.buscarProducto");
			btnReporteDeEstado.setText("Reporte de Estado de Cuentas");
			btnReporteDeEstado.setMargin(new Insets(-1, -1, -1, -1));
			btnReporteDeEstado.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteDeEstado.setBounds(200, 275, 330, 20);
			btnReporteDeEstado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarEstadoCuentas();
				}
			});
		}
		return btnReporteDeEstado;
	}
	private JButton getBtnListadoDeControl() {
		if (btnListadoDeControl == null) {
			btnListadoDeControl = new JButton();
			btnListadoDeControl.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnListadoDeControl.setToolTipText("productoPanel.tooltip.buscarProducto");
			btnListadoDeControl.setText("Listado de Control");
			btnListadoDeControl.setMargin(new Insets(-1, -1, -1, -1));
			btnListadoDeControl.setHorizontalTextPosition(SwingConstants.CENTER);
			btnListadoDeControl.setBounds(200, 244, 330, 20);
			btnListadoDeControl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarListadoDeControl();
				}
			});
		}
		return btnListadoDeControl;
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
}
