package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class ReporteMovimientoClienteDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private Date fecha;
	private String nroComprobante;
	private BigDecimal monto;
	private BigDecimal debe;
	private BigDecimal haber;
	private BigDecimal credito;
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNroComprobante() {
		return nroComprobante;
	}
	public void setNroComprobante(String nroComprobante) {
		this.nroComprobante = nroComprobante;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public BigDecimal getDebe() {
		return debe;
	}
	public void setDebe(BigDecimal debe) {
		this.debe = debe;
	}
	public BigDecimal getHaber() {
		return haber;
	}
	public void setHaber(BigDecimal haber) {
		this.haber = haber;
	}
	public BigDecimal getCredito() {
		return credito;
	}
	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((credito == null) ? 0 : credito.hashCode());
		result = prime * result + ((debe == null) ? 0 : debe.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((haber == null) ? 0 : haber.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result
				+ ((nroComprobante == null) ? 0 : nroComprobante.hashCode());
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
		if (!(obj instanceof ReporteMovimientoClienteDto)) {
			return false;
		}
		ReporteMovimientoClienteDto other = (ReporteMovimientoClienteDto) obj;
		if (credito == null) {
			if (other.credito != null) {
				return false;
			}
		} else if (!credito.equals(other.credito)) {
			return false;
		}
		if (debe == null) {
			if (other.debe != null) {
				return false;
			}
		} else if (!debe.equals(other.debe)) {
			return false;
		}
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (haber == null) {
			if (other.haber != null) {
				return false;
			}
		} else if (!haber.equals(other.haber)) {
			return false;
		}
		if (monto == null) {
			if (other.monto != null) {
				return false;
			}
		} else if (!monto.equals(other.monto)) {
			return false;
		}
		if (nroComprobante == null) {
			if (other.nroComprobante != null) {
				return false;
			}
		} else if (!nroComprobante.equals(other.nroComprobante)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "ReporteMovimientoClienteDto [fecha=" + fecha
				+ ", nroComprobante=" + nroComprobante + ", monto=" + monto
				+ ", debe=" + debe + ", haber=" + haber + ", credito="
				+ credito + "]";
	}
	

}
