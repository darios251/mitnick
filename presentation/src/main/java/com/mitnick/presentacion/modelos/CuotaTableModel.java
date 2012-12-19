package com.mitnick.presentacion.modelos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.CuotaDto;

public class CuotaTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<CuotaDto> data;
	
	public CuotaTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("cuotaTableModel.fecha"));
		columnNames.add(PropertiesManager.getProperty("cuotaTableModel.numero"));
		columnNames.add(PropertiesManager.getProperty("cuotaTableModel.monto"));
		data = new ArrayList<CuotaDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<CuotaDto>();
		this.fireTableStructureChanged();
	}
	
	public CuotaDto getCuota(int index) {
		return this.data.get(index);
	}

	public List<CuotaDto> getCuotas() {
		return data;
	}

	public void setCuotas(List<CuotaDto> cuota) {
		this.data = cuota;
		this.fireTableDataChanged();
	}
	
	public void addCuota(CuotaDto cuota) {
		this.data.add(0, cuota);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addCuota(CuotaDto cuota, int index) {
		this.data.add(index, cuota);
		this.fireTableRowsInserted(index, index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<CuotaDto>();
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
		CuotaDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return fila.getFecha_pagar(); 
			case 1: 
				return fila.getNroCuota();
			case 2: 
				return fila.getTotal();			
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		CuotaDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
			case 0:
				fila.setFecha_pagar((String)aValue); break; 
			case 1: 
				fila.setNroCuota((int) aValue); break;
			case 2: 
				fila.setTotal(new BigDecimal((String)aValue)); break;			
						
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}
