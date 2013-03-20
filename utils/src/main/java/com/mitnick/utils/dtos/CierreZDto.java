package com.mitnick.utils.dtos;

import java.util.Date;

public class CierreZDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String numero;
	
	private Date fecha;
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
}
