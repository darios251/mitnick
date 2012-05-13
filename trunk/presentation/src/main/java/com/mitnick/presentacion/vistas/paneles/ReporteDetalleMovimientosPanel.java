package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.FocusTraversalOnArray;
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
	private JLabel lblFechaInicial;
	private JLabel lblFechaFinal;

	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnExportar;
	private TableRowSorter<DetalleMovimientoTableModel> sorter;
	private DetalleMovimientoTableModel model;
	
	private ProductoDto producto;
	private Date fechaInicio;
	private Date fechaFin;
	private int stockOriginal;
	private int stockFinal;
	
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
		
		lblCdigo = new JLabel("Código: " + producto.getCodigo());
		lblCdigo.setBounds(125, 15, 100, 20);
		add(lblCdigo);
		
		lblDescripcin = new JLabel("Descripción: " + producto.getDescripcion());
		lblDescripcin.setBounds(330, 15, 150, 20);
		add(lblDescripcin);
		
		
		lblStockOriginal = new JLabel("Stock original: " + String.valueOf(stockOriginal));
		lblStockOriginal.setBounds(125, 35, 100, 20);
		add(lblStockOriginal);
		
		lblStokFinal = new JLabel("Stock final: " + String.valueOf(stockFinal));
		lblStokFinal.setBounds(330, 35, 100, 20);
		add(lblStokFinal);
		
		
		if (fechaInicio!=null) {
			lblFechaInicial = new JLabel("Desde: " + DateHelper.getFecha(fechaInicio));
			lblFechaInicial.setBounds(125, 55, 120, 20);
			add(lblFechaInicial);
		}
		
		if (fechaFin!=null) {	
			lblFechaFinal = new JLabel("Hasta: " + DateHelper.getFecha(fechaFin));
			lblFechaFinal.setBounds(330, 55, 120, 20);
			add(lblFechaFinal);
		}
		// Creo una tabla con un sorter
		model = new DetalleMovimientoTableModel();
        sorter = new TableRowSorter<DetalleMovimientoTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
//        table.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		
		

		
		add(getBtnExportar());
		
		
		JLabel lblProductos = new JLabel(PropertiesManager.getProperty("productoPanel.label.productos"));
		lblProductos.setBounds(25, 90, 70, 20);
		add(lblProductos);
	
		setFocusTraversalPolicy();		
	}
	
	
	
	public JLabel getLblCdigo() {
		return lblCdigo;
	}

	public JLabel getLblDescripcin() {
		return lblDescripcin;
	}

	public JLabel getLblStockOriginal() {
		return lblStockOriginal;
	}

	public JLabel getLblStokFinal() {
		return lblStokFinal;
	}

	public JLabel getLblFechaInicial() {
		return lblFechaInicial;
	}

	public JLabel getLblFechaFinal() {
		return lblFechaFinal;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JButton getBtnExportar() {
		if(btnExportar == null) {
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
		}
		return btnExportar;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{}));
	}
	
	protected void consultarProductos() {
		try {
			ReporteDetalleMovimientosDto dto = new ReporteDetalleMovimientosDto();
			dto.setProducto(producto);
			dto.setFechaInicio(fechaInicio);
			dto.setFechaFin(fechaFin);
			List<MovimientoDto> movimientos = reporteController.reporteMovimientosDeProducto(dto);
			if (fechaInicio==null)
				fechaInicio = movimientos.get(0).getFecha();
			if (fechaFin==null)
				fechaFin = movimientos.get(movimientos.size()-1).getFecha();
			model.setProductosMovimientos(movimientos);
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

	public int getStockOriginal() {
		return stockOriginal;
	}

	public void setStockOriginal(int stockOriginal) {
		this.stockOriginal = stockOriginal;
	}

	public int getStockFinal() {
		return stockFinal;
	}

	public void setStockFinal(int stockFinal) {
		this.stockFinal = stockFinal;
	}
	
	protected void setDefaultButton() {
		this.getRootPane().setDefaultButton(getBtnExportar());
	}
	
}
