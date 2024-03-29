package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import com.mitnick.utils.AllowBlankMaskFormatter;
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
public class ReporteMovimientosPanel extends BasePanel<ReporteMovimientosController> {

	private static final long serialVersionUID = 1L;

	private JTextField txtProductoCodigo;
	private JTextField txtProductoDescripcion;
	private JComboBox<TipoDto> cmbTipo;
	private JComboBox<MarcaDto> cmbMarca;
	private JTextField txtFechaInicio;

	private MitnickComboBoxModel<TipoDto> modelCmbTipo;
	private MitnickComboBoxModel<MarcaDto> modelCmbMarca;
	
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
	private JButton btnCompra;
	private JButton btnVendedor;
	
	private JLabel lblReportesDescripcion;
	private JButton btnReporteProductoVentaDiario;
	private JButton btnReporteProductoVentaMensual;
	private JButton btnReporteVentaDiario;
	private JButton btnReporteVentaMensual;
	
	private boolean mostrarError = false;

	@Autowired
	public ReporteMovimientosPanel(
			@Qualifier("reporteMovimientosController") ReporteMovimientosController reporteController) {
		controller = reporteController;
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
	}

	@Override
	protected void initializeComponents() {

		setLayout(null);
		setSize(new Dimension(815, 520));

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
		add(getBtnCompra());
		
		if (Validator.isNotNull(PropertiesManager.getPropertyAsBoolean("application.venta.vendedor")) && PropertiesManager.getPropertyAsBoolean("application.venta.vendedor").booleanValue())
			add(getBtnVendedor());
		
		add(getBtnDetalleMovimientos());
		add(getBtnExportar());

		add(getLblReportesDescripcion());
		add(getBtnReporteProductoVentaDiario());
		add(getBtnReporteProductoVentaMensual());
		add(getBtnReporteVentaDiario());
		add(getBtnReporteVentaMensual());

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

	public JComboBox<TipoDto> createCmbTipo() {
		cmbTipo = new JComboBox<TipoDto>(getModelCmbTipo());
		cmbTipo.setBounds(200, 55, 110, 20);
		cmbTipo.insertItemAt(null, 0);
		return cmbTipo;
	}
	
	public JComboBox<TipoDto> getCmbTipo() {
		if (cmbTipo == null) {
			cmbTipo = new JComboBox<TipoDto>(getModelCmbTipo());
			cmbTipo.setBounds(200, 55, 110, 20);
			cmbTipo.insertItemAt(null, 0);
		}
		return cmbTipo;
	}
	
	public JComboBox<MarcaDto> createCmbMarca() {
		cmbMarca = new JComboBox<MarcaDto>(getModelCmbMarca());
		cmbMarca.setBounds(420, 55, 110, 20);
		cmbMarca.insertItemAt(null, 0);
		return cmbMarca;
	}
	
	public JComboBox<MarcaDto> getCmbMarca() {
		if (cmbMarca == null) {
			cmbMarca = new JComboBox<MarcaDto>(getModelCmbMarca());
			cmbMarca.setBounds(420, 55, 110, 20);
			cmbMarca.insertItemAt(null, 0);
		}
		return cmbMarca;
	}
	
	public MitnickComboBoxModel<TipoDto> getModelCmbTipo() {
		if (modelCmbTipo == null) {
			modelCmbTipo = new MitnickComboBoxModel<TipoDto>();			
		}
		modelCmbTipo.removeAllElements();
		modelCmbTipo.addItems(controller.obtenerTipos());
		return modelCmbTipo;
	}
	
	public MitnickComboBoxModel<MarcaDto> getModelCmbMarca() {
		if (modelCmbMarca == null) {
			modelCmbMarca = new MitnickComboBoxModel<MarcaDto>();			
		}
		modelCmbMarca.removeAllElements();
		modelCmbMarca.addItems(controller.obtenerMarcas());
		return modelCmbMarca;
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
			btnBuscar = new JButton(PropertiesManager
					.getProperty("productoPanel.button.buscar.texto"));
			btnBuscar.setToolTipText(PropertiesManager
					.getProperty("productoPanel.button.buscar.tooltip"));

			btnBuscar.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/buscar.png")));
			btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnBuscar.setMargin(new Insets(-1, -1, -1, -1));

			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					mostrarError=true;
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
			lblFechaInicio = new JLabel(PropertiesManager.getProperty("label.inicio"));
			lblFechaInicio.setBounds(125, 95, 90, 20);
		}
		return lblFechaInicio;
	}

	public JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel(PropertiesManager.getProperty("label.fin"));
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


	public JLabel getLblReportesDescripcion() {
		if (lblReportesDescripcion == null) {
			lblReportesDescripcion = new JLabel(
					PropertiesManager.getProperty("productoPanel.label.reportesDescripcion"));
			lblReportesDescripcion.setBounds(20, 460, 200, 20);
		}
		return lblReportesDescripcion;
	}
	
	public JButton getBtnReporteProductoVentaDiario() {
		if (btnReporteProductoVentaDiario == null) {
			btnReporteProductoVentaDiario = new JButton(PropertiesManager
					.getProperty("movmientosPanel.label.producto.reporteProductoVentaDiario"));
			btnReporteProductoVentaDiario.setToolTipText(PropertiesManager
					.getProperty("movmientosPanel.tooltip.producto.reporteProductoVentaDiario"));

			btnReporteProductoVentaDiario.setIcon(new ImageIcon(this.getClass().getResource("/img/reporte_prod_dias.png")));
			btnReporteProductoVentaDiario.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteProductoVentaDiario.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteProductoVentaDiario.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteProductoVentaDiario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reporteVentaProducto(true, false);
					
				}
			});
			
			btnReporteProductoVentaDiario.setBounds(250, 460, 60, 60);
		}
		return btnReporteProductoVentaDiario;
	}
	
	public JButton getBtnReporteProductoVentaMensual() {
		if (btnReporteProductoVentaMensual == null) {
			btnReporteProductoVentaMensual = new JButton(PropertiesManager
					.getProperty("movmientosPanel.label.producto.reporteProductoVentaMensual"));
			btnReporteProductoVentaMensual.setToolTipText(PropertiesManager
					.getProperty("movmientosPanel.tooltip.producto.reporteProductoVentaMensual"));

			btnReporteProductoVentaMensual.setIcon(new ImageIcon(this.getClass().getResource("/img/reporte_prod_mes.png")));
			btnReporteProductoVentaMensual.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteProductoVentaMensual.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteProductoVentaMensual.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteProductoVentaMensual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reporteVentaProducto(true, true);
					
				}
			});

			btnReporteProductoVentaMensual.setBounds(350, 460, 60, 60);
		}
		return btnReporteProductoVentaMensual;
	}
	
	public JButton getBtnReporteVentaDiario() {
		if (btnReporteVentaDiario == null) {
			btnReporteVentaDiario = new JButton(PropertiesManager
					.getProperty("movmientosPanel.label.producto.reporteVentaDiario"));
			btnReporteVentaDiario.setToolTipText(PropertiesManager
					.getProperty("movmientosPanel.tooltip.producto.reporteVentaDiario"));

			btnReporteVentaDiario.setIcon(new ImageIcon(this.getClass().getResource("/img/reporte_cantidad_dias.png")));
			btnReporteVentaDiario.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentaDiario.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentaDiario.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentaDiario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reporteVentaProducto(false, false);
				}
			});
			
			btnReporteVentaDiario.setBounds(450, 460, 60, 60);
		}
		return btnReporteVentaDiario;
	}
	
	public JButton getBtnReporteVentaMensual() {
		if (btnReporteVentaMensual == null) {
			btnReporteVentaMensual = new JButton(PropertiesManager
					.getProperty("movmientosPanel.label.producto.reporteVentaMensual"));
			btnReporteVentaMensual.setToolTipText(PropertiesManager
					.getProperty("movmientosPanel.tooltip.producto.reporteVentaMensual"));

			btnReporteVentaMensual.setIcon(new ImageIcon(this.getClass().getResource("/img/reporte_cantidad_mes.png")));
			btnReporteVentaMensual.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReporteVentaMensual.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnReporteVentaMensual.setMargin(new Insets(-1, -1, -1, -1));

			btnReporteVentaMensual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reporteVentaProducto(false, true);					
				}
			});

			btnReporteVentaMensual.setBounds(550, 460, 60, 60);
		}
		return btnReporteVentaMensual;
	}
	
	private void reporteVentaProducto(boolean agrupadoProducto, boolean agrupadoMes){
		ReporteMovimientosDto dto = new ReporteMovimientosDto();
		dto.setCodigo(getTxtProductoCodigo().getText());
		dto.setDescripcion(getTxtProductoDescripcion().getText());
		dto.setFechaInicio(getFechaInicio());
		dto.setFechaFin(getFechaFinal());
		dto.setMarca((MarcaDto) getCmbMarca().getSelectedItem());
		dto.setTipo((TipoDto) getCmbTipo().getSelectedItem());
		dto.setAgrupadoMes(agrupadoMes);
		dto.setAgrupadoProducto(agrupadoProducto);
		try{
			controller.consultarVentaPorArticulo(dto);	
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
	public JButton getBtnVendedor() {
		if (btnVendedor == null) {
			btnVendedor = new JButton(PropertiesManager
					.getProperty("productoPanel.button.reporteVendedor"));
			btnVendedor.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.reporteVendedor"));


			btnVendedor.setIcon(new ImageIcon(this.getClass().getResource("/img/reporteVendedor.png")));
			btnVendedor.setHorizontalTextPosition(SwingConstants.CENTER);
			btnVendedor.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnVendedor.setMargin(new Insets(-1, -1, -1, -1));

			btnVendedor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ReporteMovimientosDto dto = new ReporteMovimientosDto();
					dto.setCodigo(getTxtProductoCodigo().getText());
					dto.setDescripcion(getTxtProductoDescripcion().getText());
					dto.setFechaInicio(getFechaInicio());
					dto.setFechaFin(getFechaFinal());
					dto.setMarca((MarcaDto) getCmbMarca().getSelectedItem());
					dto.setTipo((TipoDto) getCmbTipo().getSelectedItem());
					
					try {
						controller.mostrarReporteVendedor(dto);
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
					
				}
			});

			btnVendedor.setBounds(735, 160, 60, 60);
		}
		return btnVendedor;
	}
	
	public JButton getBtnCompra() {
		if (btnCompra == null) {
			btnCompra = new JButton(PropertiesManager
					.getProperty("productoPanel.button.compraProductos"));
			btnCompra.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.compraProductos"));


			btnCompra.setIcon(new ImageIcon(this.getClass().getResource("/img/camion.png")));
			btnCompra.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCompra.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCompra.setMargin(new Insets(-1, -1, -1, -1));

			btnCompra.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ReporteMovimientosDto dto = new ReporteMovimientosDto();
					dto.setCodigo(getTxtProductoCodigo().getText());
					dto.setDescripcion(getTxtProductoDescripcion().getText());
					dto.setFechaInicio(getFechaInicio());
					dto.setFechaFin(getFechaFinal());
					dto.setMarca((MarcaDto) getCmbMarca().getSelectedItem());
					dto.setTipo((TipoDto) getCmbTipo().getSelectedItem());
					
					try {
						controller.mostrarCompraSugerida(dto);
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
					
				}
			});

			btnCompra.setBounds(735, 230, 60, 60);
		}
		return btnCompra;
	}
	
	public JButton getBtnDetalleMovimientos() {
		if (btnDetalleMovimientos == null) {
			btnDetalleMovimientos = new JButton(PropertiesManager
					.getProperty("productoPanel.label.detallesMovimientos"));
			btnDetalleMovimientos.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.detallesMovimientos"));

			btnDetalleMovimientos.setIcon(new ImageIcon(this.getClass()
					.getResource("/img/data_find.png")));
			btnDetalleMovimientos.setHorizontalTextPosition(SwingConstants.CENTER);
			btnDetalleMovimientos.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnDetalleMovimientos.setMargin(new Insets(-1, -1, -1, -1));

			btnDetalleMovimientos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.mostrarMovimientosPanel();
				}
			});

			btnDetalleMovimientos.setBounds(735, 300, 60, 60);
		}
		return btnDetalleMovimientos;
	}
	
	public JButton getBtnExportar() {
		if (btnExportar == null) {
			btnExportar = new JButton(PropertiesManager
					.getProperty("productoPanel.label.exportar"));
			btnExportar.setToolTipText(PropertiesManager
					.getProperty("productoPanel.tooltip.exportar"));

			btnExportar.setIcon(new ImageIcon(this.getClass().getResource("/img/pdfIcon.png")));
			btnExportar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnExportar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnExportar.setMargin(new Insets(-1, -1, -1, -1));

			btnExportar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						controller.exportarMovimientosProducto(getModel().getMovimientoProducto());
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});

			btnExportar.setBounds(735, 370, 60, 60);
		}
		return btnExportar;
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
			dto.setMarca((MarcaDto) getCmbMarca().getSelectedItem());
			dto.setTipo((TipoDto) getCmbTipo().getSelectedItem());
			
			getModel().setProductosMovimientos(controller.reporteMovimientosAgrupadosPorProducto(dto));
		} catch (PresentationException ex) {
			if (mostrarError)
				mostrarMensaje(ex);			
			getModel().setProductosMovimientos(new ArrayList<MovimientoProductoDto>());
		}
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(getModel());
			table.setRowSorter(getSorter());
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
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
		mostrarError = false;
		
		getCmbMarca();
		remove(getCmbMarca());
		add(createCmbMarca());
		
		getCmbTipo();
		remove(getCmbTipo());
		add(createCmbTipo());
		
		getCmbMarca().setSelectedItem(null);
		getCmbTipo().setSelectedItem(null);
		
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
			try {
				txtFechaInicio = new JFormattedTextField(new AllowBlankMaskFormatter(MitnickConstants.DATE_MASKFORMAT)); 		
				txtFechaInicio.setColumns(10);
				txtFechaInicio.setBounds(200, 95, 110, 20);
				txtFechaInicio.setText("01/01/1900");
			} catch (ParseException e) {}
		}
		return txtFechaInicio;
	}

	public JTextField getTxtFechaFinal() {
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
			this.getRootPane().setDefaultButton(getBtnBuscar());
	}
}
