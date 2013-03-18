package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;

public class FacturaDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String nroFactura;

	private String cliente;
	
	private String cuit;
	
	private String condicion;
	
	private BigDecimal neto;
	
	private BigDecimal iva;
	
	private BigDecimal total;

	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public BigDecimal getNeto() {
		return neto;
	}

	public void setNeto(BigDecimal neto) {
		this.neto = neto;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "FacturaDto [nroFactura=" + nroFactura + ", cliente=" + cliente
				+ ", cuit=" + cuit + ", condicion=" + condicion + ", neto="
				+ neto + ", iva=" + iva + ", total=" + total + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((condicion == null) ? 0 : condicion.hashCode());
		result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((neto == null) ? 0 : neto.hashCode());
		result = prime * result
				+ ((nroFactura == null) ? 0 : nroFactura.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		if (!(obj instanceof FacturaDto)) {
			return false;
		}
		FacturaDto other = (FacturaDto) obj;
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
			return false;
		}
		if (condicion == null) {
			if (other.condicion != null) {
				return false;
			}
		} else if (!condicion.equals(other.condicion)) {
			return false;
		}
		if (cuit == null) {
			if (other.cuit != null) {
				return false;
			}
		} else if (!cuit.equals(other.cuit)) {
			return false;
		}
		if (iva == null) {
			if (other.iva != null) {
				return false;
			}
		} else if (!iva.equals(other.iva)) {
			return false;
		}
		if (neto == null) {
			if (other.neto != null) {
				return false;
			}
		} else if (!neto.equals(other.neto)) {
			return false;
		}
		if (nroFactura == null) {
			if (other.nroFactura != null) {
				return false;
			}
		} else if (!nroFactura.equals(other.nroFactura)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
			return false;
		}
		return true;
	}
	
	
}
