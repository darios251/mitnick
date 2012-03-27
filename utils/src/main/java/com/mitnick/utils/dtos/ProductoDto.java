package com.mitnick.utils.dtos;

import java.math.BigDecimal;

public class ProductoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;
	
	private TipoDto tipo;
	
	private String talle;
	
	private MarcaDto marca;
	
	private BigDecimal precio;
	
	private BigDecimal descuento;
	
	private int stock;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDto tipo) {
		this.tipo = tipo;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public MarcaDto getMarca() {
		return marca;
	}

	public void setMarca(MarcaDto marca) {
		this.marca = marca;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
