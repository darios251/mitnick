package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import com.mitnick.utils.FocusTraversalOnArray;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.ProductoDto;

@Panel("reporteDetalleMovimientosPanel")
public class ReporteDetalleMovimientosPanel extends BasePanel<ReporteMovimientosController> {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel lblCdigo;
	private JLabel lblDescripcin;
	private JLabel lblStockOriginal;
	private JLabel lblStokFinal;

	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnExportar;
	private JButton btnVolver;
	private TableRowSorter<DetalleMovimientoTableModel> sorter;
	private DetalleMovimientoTableModel model;
	
	private ProductoDto producto;
	private int stockOriginal;
	private int stockFinal;
	
	@Autowired
	public ReporteDetalleMovimientosPanel(@Qualifier ("reporteMovimientosController") ReporteMovimientosController reporteController) {
		controller = reporteController;
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
		
		lblCdigo = new JLabel("CÃ³digo: " + producto.getCodigo());
		lblCdigo.setBounds(125, 15, 100, 20);
		add(lblCdigo);
		
		lblDescripcin = new JLabel("DescripciÃ³n: " + producto.getDescripcion());
		lblDescripcin.setBounds(330, 15, 150, 20);
		add(lblDescripcin);
		
		
		lblStockOriginal = new JLabel("Stock original: " + String.valueOf(stockOriginal));
		lblStockOriginal.setBounds(125, 35, 100, 20);
		add(lblStockOriginal);
		
		lblStokFinal = new JLabel("Stock final: " + String.valueOf(stockFinal));
		lblStokFinal.setBounds(330, 35, 100, 20);
		add(lblStokFinal);
		
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
		add(getBtnVolver());
		
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

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JButton getBtnExportar() {
		if(btnExportar == null) {
			btnExportar = new JButton(PropertiesManager.getProperty("productoPanel.label.exportar"));
			btnExportar.setToolTipText(PropertiesManager.getProperty("productoPanel.tooltip.exportar"));
			
//			btnExportar.setIcon(new ImageIcon(this.getClass().getResource("/img/exportar.jpg")));
			btnExportar.setHorizontalTextPosition( SwingConstants.CENTER );
			btnExportar.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnExportar.setMargin(new Insets(-1, -1, -1, -1));
			
			btnExportar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.exportarDetalleMovimientoProducto(getTableModel().getMovimientoProducto(), producto, String.valueOf(stockOriginal), String.valueOf(stockFinal));
				}
			});
			btnExportar.setBounds(735, 299, 60, 60);
		}
		return btnExportar;
	}
	
	public JButton getBtnVolver() {
		if(btnVolver == null) {
			btnVolver = new JButton(PropertiesManager.getProperty("movimientoStock.label.volver"));
			btnVolver.setToolTipText(PropertiesManager.getProperty("movimientoStock.tooltip.volver"));
			
			btnVolver.setIcon(new ImageIcon(this.getClass().getResource("/img/volver.png")));
			btnVolver.setHorizontalTextPosition( SwingConstants.CENTER );
			btnVolver.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnVolver.setMargin(new Insets(-1, -1, -1, -1));
			
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.mostrarProductosPanel();
				}
			});
			btnVolver.setBounds(735, 370, 60, 60);
		}
		return btnVolver;
	}

	protected void setFocusTraversalPolicy() {
		super.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[]{ table, btnExportar, btnVolver}));
	}
	
	protected void consultarProductos() {
		try {
			ReporteDetalleMovimientosDto dto = new ReporteDetalleMovimientosDto();
			dto.setProducto(producto);
			List<MovimientoDto> movimientos = controller.reporteMovimientosDeProducto(dto);
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
		
		lblCdigo.setText("Código: " + producto.getCodigo());
		lblDescripcin.setText("Descripción: " + producto.getDescripcion());
		lblStockOriginal.setText("Stock original: " + String.valueOf(stockOriginal));
		lblStokFinal.setText("Stock final: " + String.valueOf(stockFinal));

	}

	@Override
	public void setDefaultFocusField() {
		btnExportar.setRequestFocusEnabled(true);
	}

	public void setReporteController(ReporteMovimientosController reporteController) {
		controller = reporteController;
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
		if(Validator.isNotNull(this.getRootPane()))
			this.getRootPane().setDefaultButton(getBtnExportar());
	}
	
}
