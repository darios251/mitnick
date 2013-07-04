package com.mitnick.utils.dtos;

import java.util.Date;

public class CierreZDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String numero;
	
	private Date fecha;
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + numeroCaja;
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
		if (!(obj instanceof CierreZDto)) {
			return false;
		}
		CierreZDto other = (CierreZDto) obj;
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (numeroCaja != other.numeroCaja) {
			return false;
		}
		return true;
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	private int numeroCaja;
	
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

	@Override
	public String toString() {
		return "CierreZDto [numero=" + numero + ", fecha=" + fecha + ", numeroCaja=" + numeroCaja + "]";
	}

	
}
