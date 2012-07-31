package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ReporteMovimientosController;
import com.mitnick.presentacion.modelos.MitnickComboBoxModel;
import com.mitnick.presentacion.modelos.MovimientoTableModel;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Panel("reporteMovimientosPanel")
public class ReporteMovimientosPanel extends BasePanel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ReporteMovimientosController reporteController;

	private JTextField txtProductoCodigo;
	private JTextField txtProductoDescripcion;
	private JComboBox<TipoDto> cmbTipo;
	private JComboBox<MarcaDto> cmbMarca;
	private JTextField txtFechaInicio;

	private JTextField txtFechaFinal;

	private JLabel lblMarca;
	private JButton btnBuscar;
	private Component lblTipo;
	private JLabel lblDescripcin;
	private JLabel lblCdigo;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnExportar;
	private TableRowSorter<MovimientoTableModel> sorter;
	private MovimientoTableModel model;
	private JButton btnDetalleMovimientos;

	@Autowired
	public ReporteMovimientosPanel(
			@Qualifier("reporteMovimientosController") ReporteMovimientosController reporteController) {
		this.reporteController = reporteController;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ReporteMovimientosPanel(boolean modoDisenio) {
		initializeComponents();
	}

	@Override
	protected void limpiarCamposPantalla() {
		for (Component component : getComponents()) {
			if (component instanceof JTextField)
				((JTextField) component).setText("");
		}
		try {
			getCmbMarca().setSelectedIndex(0);
			getCmbTipo().setSelectedIndex(0);
		} catch (Exception e) {
		}
//		getModel().setProductosMovimientos(
//				new ArrayList<MovimientoProductoDto>());
	}

	@Override
	protected void initializeComponents() {

		setLayout(null);
		setSize(new Dimension(815, 470));

		add(getLblCdigo());
		add(getLblDescripcin());
		add(getLblTipo());
		add(getLblMarca());
		add(getLblFechaInicio());
		add(getLblFechaFin());
		
		add(getTxtProductoCodigo());
		add(getTxtProductoDescripcion());
		add(getTxtFechaInicio());
		add(getTxtFechaFinal());
		
		add(getCmbTipo());
		add(getCmbMarca());

		add(getScrollPane());

		add(getBtnBuscar());
		add(getBtnDetalleMovimientos());
		add(getBtnExportar());

		setFocusTraversalPolicy();
		this.actualizarPantalla();
	}

	public JTextField getTxtProductoCodigo() {
		if (txtProductoCodigo == null) {
			txtProductoCodigo = new JTextField();
			txtProductoCodigo.setColumns(10);
			txtProductoCodigo.setBounds(200, 15, 110, 20);
		}
		return txtProductoCodigo;
	}

	public JTextField getTxtProductoDescripcion() {
		if (txtProductoDescripcion == null) {
			txtProductoDescripcion = new JTextField();
			txtProductoDescripcion.setColumns(10);
			txtProductoDescripcion.setBounds(420, 15, 110, 20);
		}
		return txtProductoDescripcion;
	}

	public JComboBox<TipoDto> getCmbTipo() {
		if (cmbTipo == null) {
			MitnickComboBoxModel<TipoDto> modeloTipo = new MitnickComboBoxModel<TipoDto>();
			modeloTipo.addElement(MitnickConstants.tipoTodos);
			modeloTipo.addItems(reporteController.obtenerTipos());
			cmbTipo = new JComboBox<TipoDto>(modeloTipo);
			cmbTipo.setBounds(200, 55, 110, 20);
		}
		return cmbTipo;
	}

	public JComboBox<MarcaDto> getCmbMarca() {
		if (cmbMarca == null) {
			MitnickComboBoxModel<MarcaDto> modeloMarca = new MitnickComboBoxModel<MarcaDto>();
			modeloMarca.addElement(MitnickConstants.marcaTodos);
			modeloMarca.addItems(reporteController.obtenerMarcas());
			cmbMarca = new JComboBox<MarcaDto>(modeloMarca);
			cmbMarca.setBounds(420, 55, 110, 20);
		}
		return cmbMarca;
	}

	public JLabel getLblMarca() {
		if (lblMarca == null) {
			lblMarca = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.marca"));
			lblMarca.setBounds(330, 55, 60, 20);
		}
		return lblMarca;
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
					consultarProductos();
				}
			});
			btnBuscar.setBounds(560, 15, 60, 60);
		}
		return btnBuscar;
	}

	public Component getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.tipo"));
			lblTipo.setBounds(125, 55, 90, 20);
		}
		return lblTipo;
	}

	public JLabel getLblDescripcin() {
		if (lblDescripcin == null) {
			lblDescripcin = new JLabel(
					PropertiesManager
							.getProperty("productoPanel.label.descripcion"));
			lblDescripcin.setBounds(330, 15, 60, 20);
		}
		return lblDescripcin;
	}

	public JLabel getLblCdigo() {
		if (lblCdigo == null) {
			lblCdigo = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.codigo"));
			lblCdigo.setBounds(125, 15, 60, 20);
		}
		return lblCdigo;
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

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(25, 145, 700, 300);
		}
		return scrollPane;
	}

	public JButton getBtnExportar() {
		if (btnExportar == null) {
			btnExportar = new JButton();
			btnExportar.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.exportar"));

			btnExportar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/movimientos.png")));
			btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnExportar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnExportar.setMargin(new Insets(-1, -1, -1, -1));

			btnExportar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reporteController.exportarMovimientos();
				}
			});

			btnExportar.setBounds(735, 370, 60, 60);
		}
		return btnExportar;
	}

	public JButton getBtnDetalleMovimientos() {
		if (btnDetalleMovimientos == null) {
			btnDetalleMovimientos = new JButton();
			btnDetalleMovimientos.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.detallesMovimientos"));

			btnDetalleMovimientos.setIcon(new ImageIcon(this.getClass()
					.getResource("/img/movimientos.png")));
			btnDetalleMovimientos.setHorizontalTextPosition(SwingConstants.CENTER);
			btnDetalleMovimientos.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnDetalleMovimientos.setMargin(new Insets(-1, -1, -1, -1));

			btnDetalleMovimientos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reporteController.mostrarMovimientosPanel();
				}
			});

			btnDetalleMovimientos.setBounds(735, 300, 60, 60);
		}
		return btnDetalleMovimientos;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { txtProductoCodigo, txtProductoDescripcion,
						cmbTipo, cmbMarca, txtFechaInicio, txtFechaFinal }));
	}

	protected void consultarProductos() {
		try {
			ReporteMovimientosDto dto = new ReporteMovimientosDto();
			dto.setCodigo(getTxtProductoCodigo().getText());
			dto.setDescripcion(getTxtProductoDescripcion().getText());
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			dto.setMarca((MarcaDto) getCmbMarca().getSelectedItem());
			dto.setTipo((TipoDto) getCmbTipo().getSelectedItem());
			
			getModel().setProductosMovimientos(reporteController
					.reporteMovimientosAgrupadosPorProducto(dto));
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
			getModel().setProductosMovimientos(new ArrayList<MovimientoProductoDto>());
		}
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
			table.setRowSorter(getSorter());
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			// table.setFillsViewportHeight(true);
		}
		return table;
	}

	public MovimientoTableModel getModel() {
		if (model == null) {
			model = new MovimientoTableModel();
		}
		return model;
	}

	@Override
	public void actualizarPantalla() {
		consultarProductos();
	}

	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = getTxtProductoCodigo();
	}

	public TableRowSorter<MovimientoTableModel> getSorter() {
		if (sorter == null) {
			sorter = new TableRowSorter<MovimientoTableModel>(getModel());
		}
		return sorter;
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
}
