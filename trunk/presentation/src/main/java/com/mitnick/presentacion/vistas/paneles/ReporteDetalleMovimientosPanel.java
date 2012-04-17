package com.mitnick.presentacion.vistas.paneles;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ReporteMovimientosController;
import com.mitnick.presentacion.modelos.DetalleMovimientoTableModel;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.ProductoDto;

@Panel("reporteDetalleMovimientosPanel")
public class ReporteDetalleMovimientosPanel extends BasePanel {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReporteMovimientosController reporteController;
	
	private JLabel lblCdigo;
	private JLabel lblDescripcin;
	private JLabel lblStockOriginal;
	private JLabel lblStokFinal;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnExportar;
	private TableRowSorter<DetalleMovimientoTableModel> sorter;
	private DetalleMovimientoTableModel model;
	
	private ProductoDto producto;
	private Date fechaInicio;
	private Date fechaFin;
	
	@Autowired
	public ReporteDetalleMovimientosPanel(@Qualifier ("reporteMovimientosController") ReporteMovimientosController reporteController) {
		this.reporteController = reporteController;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ReporteDetalleMovimientosPanel() {
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
		
		lblDescripcin = new JLabel(PropertiesManager.getProperty("productoPanel.label.descripcion"));
		lblDescripcin.setBounds(330, 15, 60, 20);
		add(lblDescripcin);

		lblStockOriginal = new JLabel("Stock original");
		lblStockOriginal.setBounds(125, 55, 90, 20);
		add(lblStockOriginal);
		
		lblStokFinal = new JLabel("Stock final");
		lblStokFinal.setBounds(330, 55, 60, 20);
		add(lblStokFinal);
		
		// Creo una tabla con un sorter
		model = new DetalleMovimientoTableModel();
        sorter = new TableRowSorter<DetalleMovimientoTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		
		

		btnExportar = new JButton();
		btnExportar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.detallesMovimientos"));
		
		btnExportar.setIcon(new ImageIcon(this.getClass().getResource("/img/movimientos.png")));
		btnExportar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnExportar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnExportar.setMargin(new Insets(-1, -1, -1, -1));
		
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExportar.setBounds(735, 325, 60, 60);
		add(btnExportar);
		
		
		JLabel lblProductos = new JLabel(PropertiesManager.getProperty("productoPanel.label.productos"));
		lblProductos.setBounds(25, 90, 70, 20);
		add(lblProductos);
	
	}
	
	protected void consultarProductos() {
		try {
			ReporteDetalleMovimientosDto dto = new ReporteDetalleMovimientosDto();
			dto.setProducto(producto);
			dto.setFechaInicio(fechaInicio);
			dto.setFechaFin(fechaFin);
			model.setProductosMovimientos(reporteController.reporteMovimientosDeProducto(dto));
		}
		catch (PresentationException ex) {
			mostrarMensaje(ex);
			model.setProductosMovimientos(new ArrayList<MovimientoDto>());
		}
	}

	public JTable getTable() {
		return table;
	}
	
	public DetalleMovimientoTableModel getTableModel() {
		return model;
	}
	
	@Override
	public void actualizarPantalla() {
		consultarProductos();
	}
	
	@Override
	public void setDefaultFocusField() {
		
	}

	public void setReporteController(ReporteMovimientosController reporteController) {
		this.reporteController = reporteController;
	}

	public TableRowSorter<DetalleMovimientoTableModel> getSorter() {
		return sorter;
	}

	public void setSorter(TableRowSorter<DetalleMovimientoTableModel> sorter) {
		this.sorter = sorter;
	}

	public DetalleMovimientoTableModel getModel() {
		return model;
	}

	public void setModel(DetalleMovimientoTableModel model) {
		this.model = model;
	}

	public ReporteMovimientosController getReporteController() {
		return reporteController;
	}

	public ProductoDto getProducto() {
		return producto;
	}

	public void setProducto(ProductoDto producto) {
		this.producto = producto;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
