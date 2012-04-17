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
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
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
	public ReporteMovimientosPanel(@Qualifier ("reporteMovimientosController") ReporteMovimientosController reporteController) {
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
		
	}

	@Override
	protected void initializeComponents() {

		setLayout(null);
		setSize(new Dimension(815, 470));
		
		lblCdigo = new JLabel(PropertiesManager.getProperty("productoPanel.label.codigo"));
		lblCdigo.setBounds(125, 15, 60, 20);
		add(lblCdigo);
		
		txtProductoCodigo = new JTextField();
		txtProductoCodigo.setColumns(10);
		txtProductoCodigo.setBounds(200, 15, 110, 20);
		add(txtProductoCodigo);
		
		lblDescripcin = new JLabel(PropertiesManager.getProperty("productoPanel.label.descripcion"));
		lblDescripcin.setBounds(330, 15, 60, 20);
		add(lblDescripcin);
		
		txtProductoDescripcion = new JTextField();
		txtProductoDescripcion.setColumns(10);
		txtProductoDescripcion.setBounds(420, 15, 110, 20);
		add(txtProductoDescripcion);
		
		lblTipo = new JLabel(PropertiesManager.getProperty("productoPanel.label.tipo"));
		lblTipo.setBounds(125, 55, 90, 20);
		add(lblTipo);
		
		MitnickComboBoxModel<TipoDto> modeloTipo = new MitnickComboBoxModel<TipoDto>();
		modeloTipo.addElement(MitnickConstants.tipoTodos);
		modeloTipo.addItems(reporteController.obtenerTipos());
		cmbTipo = new JComboBox<TipoDto>(modeloTipo);
		cmbTipo.setBounds(200, 55, 110, 20);
		add(cmbTipo);
		
		MitnickComboBoxModel<MarcaDto> modeloMarca = new MitnickComboBoxModel<MarcaDto>();
		modeloMarca.addElement(MitnickConstants.marcaTodos);
		modeloMarca.addItems(reporteController.obtenerMarcas());
		cmbMarca = new JComboBox<MarcaDto>(modeloMarca);
		cmbMarca.setBounds(420, 55, 110, 20);
		add(cmbMarca);
		
		lblMarca = new JLabel(PropertiesManager.getProperty("productoPanel.label.marca"));
		lblMarca.setBounds(330, 55, 60, 20);
		add(lblMarca);
		
		lblFechaInicio = new JLabel("Inicio");
		lblFechaInicio.setBounds(125, 95, 90, 20);
		add(lblFechaInicio);
		
		txtFechaInicio = new JTextField();
		txtFechaInicio.setColumns(10);
		txtFechaInicio.setBounds(200, 95, 110, 20);
		add(txtFechaInicio);
		
		lblFechaFin = new JLabel("Fin");
		lblFechaFin .setBounds(330, 95, 60, 20);
		add(lblFechaFin);
		
		txtFechaFinal = new JTextField();
		txtFechaFinal.setColumns(10);
		txtFechaFinal.setBounds(420, 95, 110, 20);
		add(txtFechaFinal);
		
		// Creo una tabla con un sorter
		model = new MovimientoTableModel();
        sorter = new TableRowSorter<MovimientoTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 145, 700, 300);
		add(scrollPane);
		
		btnBuscar = new JButton();
		btnBuscar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.buscarProducto"));
		
		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar.png")));
		btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				consultarProductos();
			}
		});
		btnBuscar.setBounds(560, 15, 60, 60);
		add(btnBuscar);
		

		btnDetalleMovimientos = new JButton();
		btnDetalleMovimientos.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.detallesMovimientos"));
		
		btnDetalleMovimientos.setIcon(new ImageIcon(this.getClass().getResource("/img/movimientos.png")));
		btnDetalleMovimientos.setHorizontalTextPosition( SwingConstants.CENTER );
		btnDetalleMovimientos.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnDetalleMovimientos.setMargin(new Insets(-1, -1, -1, -1));
				
		btnDetalleMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reporteController.mostrarMovimientosPanel();
			}
		});
		
		btnDetalleMovimientos.setBounds(735, 300, 60, 60);
		add(btnDetalleMovimientos);
		
		btnExportar = new JButton();
		btnExportar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.exportar"));
		
		btnExportar.setIcon(new ImageIcon(this.getClass().getResource("/img/movimientos.png")));
		btnExportar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnExportar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnExportar.setMargin(new Insets(-1, -1, -1, -1));
				
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reporteController.exportarMovimientos();
			}
		});
		
		btnExportar.setBounds(735, 370, 60, 60);
		add(btnExportar);
		
	}
	
	protected void consultarProductos() {
		try {
			ReporteMovimientosDto dto = new ReporteMovimientosDto();
			dto.setCodigo(txtProductoCodigo.getText());
			dto.setDescripcion(txtProductoDescripcion.getText());
			dto.setFechaInicio(getFechaInicio());
			dto.setFechaFin(getFechaFinal());
			dto.setMarca((MarcaDto) cmbMarca.getSelectedItem());
			dto.setTipo((TipoDto) cmbTipo.getSelectedItem());
			model.setProductosMovimientos(reporteController.reporteMovimientosAgrupadosPorProducto(dto));
		}
		catch (PresentationException ex) {
			mostrarMensaje(ex);
			model.setProductosMovimientos(new ArrayList<MovimientoProductoDto>());
		}
	}

	public JTable getTable() {
		return table;
	}
	
	public MovimientoTableModel getTableModel() {
		return model;
	}
	
	@Override
	public void actualizarPantalla() {
		consultarProductos();
	}
	
	@Override
	public void setDefaultFocusField() {
		this.defaultFocusField = txtProductoCodigo; 
	}

	public void setReporteController(ReporteMovimientosController reporteController) {
		this.reporteController = reporteController;
	}

	public TableRowSorter<MovimientoTableModel> getSorter() {
		return sorter;
	}

	public void setSorter(TableRowSorter<MovimientoTableModel> sorter) {
		this.sorter = sorter;
	}

	public MovimientoTableModel getModel() {
		return model;
	}

	public void setModel(MovimientoTableModel model) {
		this.model = model;
	}

	public ReporteMovimientosController getReporteController() {
		return reporteController;
	}

	public JTextField getTxtFechaInicio() {
		return txtFechaInicio;
	}

	public void setTxtFechaInicio(JTextField txtFechaInicio) {
		this.txtFechaInicio = txtFechaInicio;
	}

	public JTextField getTxtFechaFinal() {
		return txtFechaFinal;
	}

	public void setTxtFechaFinal(JTextField txtFechaFinal) {
		this.txtFechaFinal = txtFechaFinal;
	}
	
	public Date getFechaInicio(){
		return null;
	}
	public Date getFechaFinal(){
		return null;
	}
	
}
