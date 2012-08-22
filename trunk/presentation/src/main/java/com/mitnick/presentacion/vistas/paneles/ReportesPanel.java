package com.mitnick.presentacion.vistas.paneles;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
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

	private JButton btnBuscar;
	
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	
	
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
			getTxtFechaInicio().setText("");
			getTxtFechaFinal().setText("");
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
		
		add(getBtnBuscar());

		setFocusTraversalPolicy();
		this.actualizarPantalla();
	}



	public JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton();
			btnBuscar.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.buscarProducto"));

			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/buscar.png")));
			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));

			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					consultarIngresos();
				}
			});
			btnBuscar.setBounds(560, 15, 60, 60);
		}
		return btnBuscar;
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
			lblFechaFin.setBounds(330, 95, 60, 20);
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

	


	public JTextField getTxtFechaInicio() {
		if (txtFechaInicio == null) {
			txtFechaInicio = new JTextField();
			txtFechaInicio.setColumns(10);
			txtFechaInicio.setBounds(200, 95, 110, 20);
			txtFechaInicio.setText("01/01/1900");
		}
		return txtFechaInicio;
	}

	public JTextField getTxtFechaFinal() {
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
			this.getRootPane().setDefaultButton(getBtnBuscar());
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
}
