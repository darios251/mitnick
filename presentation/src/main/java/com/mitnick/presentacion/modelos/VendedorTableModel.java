package com.mitnick.presentacion.modelos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.VendedorDto;

public class VendedorTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<VendedorDto> data;
	
	public VendedorTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("vendedorTableModel.codigo"));
		columnNames.add(PropertiesManager.getProperty("vendedorTableModel.nombre"));		
		data = new ArrayList<VendedorDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<VendedorDto>();
		this.fireTableStructureChanged();
	}

	public List<VendedorDto> getVendedores() {
		return data;
	}

	public void setVendedores(List<VendedorDto> vendedores) {
		this.data = vendedores;
		this.fireTableDataChanged();
	}
	
	public void addVendedor(VendedorDto vendedor) {
		this.data.add(0, vendedor);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addVendedor(VendedorDto vendedor, int index) {
		this.data.add(index, vendedor);
		this.fireTableRowsInserted(index, index);
	}
	
	public VendedorDto getVendedor(int index) {
		return this.data.get(index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<VendedorDto>();
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
		VendedorDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return fila.getCodigo();
			case 1: 
				return fila.getNombre();
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		VendedorDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			fila.setCodigo((String)aValue); break; 
		case 1: 
			fila.setNombre((String)aValue); break;
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
