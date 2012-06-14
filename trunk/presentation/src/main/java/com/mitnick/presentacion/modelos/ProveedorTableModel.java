package com.mitnick.presentacion.modelos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.ProveedorDto;

public class ProveedorTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<ProveedorDto> data;
	
	public ProveedorTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("proveedorTableModel.codigo"));
		columnNames.add(PropertiesManager.getProperty("proveedorTableModel.nombre"));
		columnNames.add(PropertiesManager.getProperty("proveedorTableModel.telefono"));
		data = new ArrayList<ProveedorDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<ProveedorDto>();
		this.fireTableStructureChanged();
	}

	public List<ProveedorDto> getProveedors() {
		return data;
	}

	public void setProveedores(List<ProveedorDto> proveedors) {
		this.data = proveedors;
		this.fireTableDataChanged();
	}
	
	public void addProveedor(ProveedorDto proveedor) {
		this.data.add(0, proveedor);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addProveedor(ProveedorDto proveedor, int index) {
		this.data.add(index, proveedor);
		this.fireTableRowsInserted(index, index);
	}
	
	public ProveedorDto getProveedor(int index) {
		return this.data.get(index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<ProveedorDto>();
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
		ProveedorDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return fila.getCodigo();
			case 1: 
				return fila.getNombre();
			case 2:
				return fila.getTelefono();
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ProveedorDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			fila.setCodigo((String)aValue); break; 
		case 1: 
			fila.setNombre((String)aValue); break;
		case 2:
			fila.setTelefono((String)aValue); break;
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
