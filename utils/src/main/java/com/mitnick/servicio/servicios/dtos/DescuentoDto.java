package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;


public class DescuentoDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	public static final int PORCENTAJE = 1;
	
	public static final int MONTO = 2;
	
	private int tipo;
	
	private BigDecimal descuentoSinIva;
	
	private BigDecimal iva;
	
	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getDescuentoSinIva() {
		return descuentoSinIva;
	}

	public void setDescuentoSinIva(BigDecimal descuentoSinIva) {
		this.descuentoSinIva = descuentoSinIva;
	}

	private BigDecimal descuento;

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "DescuentoDto [tipo=" + tipo + ", descuento=" + descuento + "]";
	}
	
}
