package com.mitnick.utils.dtos;

import java.math.BigDecimal;

public class ProductoVentaDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private ProductoDto producto;
	
	private int cantidad;

	private BigDecimal precioTotal;

	public ProductoDto getProducto() {
		return producto;
	}

	public void setProducto(ProductoDto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}
}
