package com.mitnick.utils.dtos;

import java.util.Date;

public class MovimientoDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private Date fecha;
	
	private int stock;
	
	private int cantidad;
	
	private String tipo;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	

}
