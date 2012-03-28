package com.mitnick.presentation.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

public class ProductoTableModel extends AbstractTableModel implements TableModel{

	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames;
	private List<ProductoDto> data;
	
	public ProductoTableModel() {
		columnNames = new ArrayList<String>();
		columnNames.add(PropertiesManager.getProperty("productoTableModel.codigo"));
		columnNames.add(PropertiesManager.getProperty("productoTableModel.descripcion"));
		columnNames.add(PropertiesManager.getProperty("productoTableModel.tipo"));
		columnNames.add(PropertiesManager.getProperty("productoTableModel.talle"));
		columnNames.add(PropertiesManager.getProperty("productoTableModel.marca"));
		columnNames.add(PropertiesManager.getProperty("productoTableModel.precio"));
		columnNames.add(PropertiesManager.getProperty("productoTableModel.stock"));
		data = new ArrayList<ProductoDto>();
		
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		this.data = new ArrayList<ProductoDto>();
		this.fireTableStructureChanged();
	}

	public List<ProductoDto> getProductos() {
		return data;
	}

	public void setProductos(List<ProductoDto> productos) {
		this.data = productos;
		this.fireTableDataChanged();
	}
	
	public void addProducto(ProductoDto producto) {
		this.data.add(0, producto);
		this.fireTableRowsInserted(0, 0);
	}
	
	public void addProducto(ProductoDto producto, int index) {
		this.data.add(index, producto);
		this.fireTableRowsInserted(index, index);
	}
	
	public void remove(int index) {
		this.data.remove(index);
		this.fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		this.data = new ArrayList<ProductoDto>();
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
		ProductoDto fila = data.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return fila.getCodigo(); 
			case 1: 
				return fila.getDescripcion();
			case 2: 
				return fila.getTipo();			
			case 3: 
				return fila.getTalle();
			case 4: 
				return fila.getMarca();
			case 5:
				return fila.getPrecio(); 
			case 6: 
				return fila.getStock();			
		}
		return data.get(-1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ProductoDto fila =  data.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			fila.setCodigo((String)aValue); break; 
		case 1: 
			fila.setDescripcion((String)aValue); break;
		case 2: 
			fila.setTipo((TipoDto)aValue); break;			
		case 3: 
			fila.setTalle((String)aValue); break;
		case 4: 
			fila.setMarca((MarcaDto)aValue); break;
		case 5:
			fila.setPrecio((BigDecimal)aValue); break; 
		case 6: 
			fila.getStock();			
		}
		data.set(rowIndex, fila);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
