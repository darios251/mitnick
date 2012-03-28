package com.mitnick.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.ClienteDto;

public class ClienteTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<ClienteDto> data;
	
	public ClienteTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("clienteTableModel.apellido"));
		columnNames.add(PropertiesManager.getProperty("clienteTableModel.nombre"));
		columnNames.add(PropertiesManager.getProperty("clienteTableModel.documento"));
		data = new ArrayList<ClienteDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<ClienteDto>();
		this.fireTableStructureChanged();
	}

	public List<ClienteDto> getClientes() {
		return data;
	}

	public void setClientes(List<ClienteDto> clientes) {
		this.data = clientes;
		this.fireTableDataChanged();
	}
	
	public void addCliente(ClienteDto cliente) {
		this.data.add(0, cliente);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addCliente(ClienteDto cliente, int index) {
		this.data.add(index, cliente);
		this.fireTableRowsInserted(index, index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<ClienteDto>();
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
		ClienteDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return fila.getApellido(); 
			case 1: 
				return fila.getNombre();
			case 2: 
				return fila.getDocumento();			
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ClienteDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
			case 0:
				fila.setApellido((String)aValue); break; 
			case 1: 
				fila.setNombre((String)aValue); break;
			case 2: 
				fila.setDocumento((String)aValue); break;			
						
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
