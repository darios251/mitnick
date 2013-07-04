package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;

public class ComprobanteDto extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;
	
	private Long idCliente;
	
	private String cliente;
	
	private BigDecimal monto = new BigDecimal(0);
	
	private int numeroCaja;

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
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
		if (!(obj instanceof ComprobanteDto)) {
			return false;
		}
		ComprobanteDto other = (ComprobanteDto) obj;
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
			return false;
		}
		if (idCliente == null) {
			if (other.idCliente != null) {
				return false;
			}
		} else if (!idCliente.equals(other.idCliente)) {
			return false;
		}
		if (monto == null) {
			if (other.monto != null) {
				return false;
			}
		} else if (!monto.equals(other.monto)) {
			return false;
		}
		if (numeroCaja != other.numeroCaja) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ComprobanteDto [idCliente=" + idCliente + ", cliente=" + cliente + ", monto=" + monto + ", numeroCaja="
				+ numeroCaja + "]";
	}

}
