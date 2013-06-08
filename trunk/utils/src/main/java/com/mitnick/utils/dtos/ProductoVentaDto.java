package com.mitnick.utils.dtos;

import java.math.BigDecimal;

public class ProductoVentaDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private ProductoDto producto;
	
	private int cantidad;

	private String descripcion;
	
	/** Contiene el precio total del producto, contempla la cantidad y el iva. */
	private BigDecimal precioTotal;

	private BigDecimal iva;

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

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "ProductoVentaDto [producto=" + producto + ", cantidad="
				+ cantidad + ", descripcion=" + descripcion + ", precioTotal="
				+ precioTotal + ", iva=" + iva + "]";
	}

}
