package com.mitnick.presentation.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.TipoDto;

public class VentaTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;

	private List<String> columnNames;
	private List<ProductoVentaDto> data;
	
	public VentaTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add("Codigo");
		columnNames.add("Descripcion");
		columnNames.add("Precio Unitario");
		columnNames.add("Cantidad");
		columnNames.add("Precio Final");
		data = new ArrayList<ProductoVentaDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<ProductoVentaDto>();
		this.fireTableStructureChanged();
	}
	
	public void setProductosVenta(List<ProductoVentaDto> productos) {
		this.data = productos;
		this.fireTableDataChanged();
	}
	
	public void addProductosVentas(List<ProductoVentaDto> productos) {
		for(ProductoVentaDto prod : productos) {
			addProductoVenta(prod);
		}
	}
	
	public void addProductoVenta(ProductoVentaDto producto) {
		this.data.add(0, producto);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addProductoVenta(ProductoVentaDto producto, int index) {
		this.data.add(index, producto);
		this.fireTableRowsInserted(index, index);
	}
	
	public void removeProductoVenta(ProductoVentaDto producto, int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<ProductoVentaDto>();
		this.fireTableDataChanged();
	}
	
	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames.get(columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductoVentaDto fila = data.get(rowIndex);
		
		switch(columnIndex) {
		case 0: 
			return fila.getProducto().getCodigo();
		case 1:
			return fila.getProducto().getDescripcion();
		case 2:
			return fila.getProducto().getPrecio();
		case 3:
			return fila.getCantidad();
		case 4:
			return fila.getPrecioTotal();
		}
		
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ProductoVentaDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
		case 0: 
			fila.getProducto().setCodigo((String)aValue);
		case 1:
			fila.getProducto().setDescripcion((String)aValue);
		case 2:
			fila.getProducto().setPrecio(new BigDecimal((String)aValue));
		case 3:
			fila.setCantidad((Integer)aValue);
		case 4:
			fila.setPrecioTotal(new BigDecimal((String)aValue));
		}
		
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
