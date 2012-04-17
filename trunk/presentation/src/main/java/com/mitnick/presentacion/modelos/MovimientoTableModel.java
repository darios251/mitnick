package com.mitnick.presentacion.modelos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.MovimientoProductoDto;

public class MovimientoTableModel extends AbstractTableModel implements TableModel{
	
private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<MovimientoProductoDto> data;
	
	public MovimientoTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.codigo"));
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.original"));
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.ventas"));
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.ajustes"));
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.actual"));
		data = new ArrayList<MovimientoProductoDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<MovimientoProductoDto>();
		this.fireTableStructureChanged();
	}

	public List<MovimientoProductoDto> getMovimientoProducto() {
		return data;
	}

	public void setProductosMovimientos(List<MovimientoProductoDto> movimientosProducto) {
		this.data = movimientosProducto;
		this.fireTableDataChanged();
	}
	
	public void addMovimientoProducto(MovimientoProductoDto movimientoProducto) {
		this.data.add(0, movimientoProducto);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addMovimientoProducto(MovimientoProductoDto movimientoProducto, int index) {
		this.data.add(index, movimientoProducto);
		this.fireTableRowsInserted(index, index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<MovimientoProductoDto>();
		this.fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		MovimientoProductoDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return fila.getProducto().getDescripcion(); 
			case 1: 
				return fila.getStockOriginal();
			case 2: 
				return fila.getVentas();			
			case 3: 
				return fila.getAjustes();			
			case 4: 
				return fila.getStockFinal();			
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		MovimientoProductoDto fila =  data.get(rowIndex);
		int valor = Integer.parseInt((String)aValue);
		switch(columnIndex) {
			case 1: 
				fila.setStockOriginal(valor); break; 
			case 2: 
				fila.setVentas(valor); break; 
			case 3: 
				fila.setAjustes(valor); break; 	
			case 4: 
				fila.setStockFinal(valor); break; 				
						
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
