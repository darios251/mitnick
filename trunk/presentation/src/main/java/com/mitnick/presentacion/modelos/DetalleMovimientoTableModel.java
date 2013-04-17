package com.mitnick.presentacion.modelos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.DateHelper;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.MovimientoDto;

public class DetalleMovimientoTableModel extends AbstractTableModel implements TableModel{
	
private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<MovimientoDto> data;
	
	public DetalleMovimientoTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.fecha"));
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.tipo"));
		columnNames.add(PropertiesManager.getProperty("movimientoTableModel.cantidad"));
		data = new ArrayList<MovimientoDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<MovimientoDto>();
		this.fireTableStructureChanged();
	}

	public List<MovimientoDto> getMovimientoProducto() {
		return data;
	}

	public void setProductosMovimientos(List<MovimientoDto> movimientosProducto) {
		this.data = movimientosProducto;
		this.fireTableDataChanged();
	}
	
	public void addMovimientoProducto(MovimientoDto movimientoProducto) {
		this.data.add(0, movimientoProducto);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addMovimientoProducto(MovimientoDto movimientoProducto, int index) {
		this.data.add(index, movimientoProducto);
		this.fireTableRowsInserted(index, index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<MovimientoDto>();
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
		MovimientoDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return DateHelper.getFecha(fila.getFecha()); 
			case 1: 
				return fila.getTipo();
			case 2: 
				return fila.getCantidad();			
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		MovimientoDto fila =  data.get(rowIndex);
		String valor = (String)aValue;
		switch(columnIndex) {
			case 2: 
				fila.setTipo(valor); break; 
			case 3: 
				fila.setCantidad(Integer.parseInt(valor)); break; 	
						
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
