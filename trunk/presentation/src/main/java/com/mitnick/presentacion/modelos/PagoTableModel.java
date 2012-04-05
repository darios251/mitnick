package com.mitnick.presentacion.modelos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.PagoDto;

public class PagoTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;

	private List<String> columnNames;
	private List<PagoDto> data;
	
	public PagoTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("pagoTableModel.medioPago"));
		columnNames.add(PropertiesManager.getProperty("pagoTableModel.monto"));
		data = new ArrayList<PagoDto>();
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<PagoDto>();
		this.fireTableStructureChanged();
	}
	
	public void setPagos(List<PagoDto> pagos) {
		this.data = pagos;
		this.fireTableDataChanged();
	}
	
	public PagoDto getPago(int index) {
		return this.data.get(index);
	}
	
	public void addPago(List<PagoDto> pagos) {
		for(PagoDto pago : pagos) {
			addPago(pago);
		}
	}
	
	public void addPago(PagoDto pago) {
		this.data.add(0, pago);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addPago(PagoDto pago, int index) {
		this.data.add(index, pago);
		this.fireTableRowsInserted(index, index);
	}
	
	public void removePago(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removePago(PagoDto pago) {
		this.data.remove(pago);
		this.fireTableDataChanged();
	}
	
	public void removeAll() {
		this.data = new ArrayList<PagoDto>();
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
		PagoDto fila = data.get(rowIndex);
		
		switch(columnIndex) {
		case 0: 
			return fila.getMedioPago().getDescripcion();
		case 1:
			return fila.getMonto().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		PagoDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
		case 0: 
			fila.getMedioPago().setDescripcion((String)aValue);
		case 1:
			fila.setMonto((BigDecimal)aValue);
		}
		
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
