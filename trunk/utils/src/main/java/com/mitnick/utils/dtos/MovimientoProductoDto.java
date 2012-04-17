package com.mitnick.utils.dtos;


public class MovimientoProductoDto extends BaseDto {

private static final long serialVersionUID = 1L;
	
	private int stockOriginal;
	
	private int stockFinal;
	
	private int ventas;
	
	private int ajustes;
	
	private ProductoDto producto;

	public int getStockOriginal() {
		return stockOriginal;
	}

	public void setStockOriginal(int stockOriginal) {
		this.stockOriginal = stockOriginal;
	}

	public int getVentas() {
		return ventas;
	}

	public void setVentas(int ventas) {
		this.ventas = ventas;
	}

	public int getAjustes() {
		return ajustes;
	}

	public void setAjustes(int ajustes) {
		this.ajustes = ajustes;
	}

	public ProductoDto getProducto() {
		return producto;
	}

	public void setProducto(ProductoDto producto) {
		this.producto = producto;
	}

	public int getStockFinal() {
		return stockFinal;
	}

	public void setStockFinal(int stockFinal) {
		this.stockFinal = stockFinal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ajustes;
		result = prime * result
				+ ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + stockFinal;
		result = prime * result + stockOriginal;
		result = prime * result + ventas;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MovimientoProductoDto)) {
			return false;
		}
		MovimientoProductoDto other = (MovimientoProductoDto) obj;
		if (ajustes != other.ajustes) {
			return false;
		}
		if (producto == null) {
			if (other.producto != null) {
				return false;
			}
		} else if (!producto.equals(other.producto)) {
			return false;
		}
		if (stockFinal != other.stockFinal) {
			return false;
		}
		if (stockOriginal != other.stockOriginal) {
			return false;
		}
		if (ventas != other.ventas) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MovimientoProductoDto [stockOriginal=" + stockOriginal
				+ ", stockFinal=" + stockFinal + ", ventas=" + ventas
				+ ", ajustes=" + ajustes + ", producto=" + producto + "]";
	}

	
}
