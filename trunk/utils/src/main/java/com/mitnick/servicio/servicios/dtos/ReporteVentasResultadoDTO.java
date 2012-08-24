package com.mitnick.servicio.servicios.dtos;

import java.util.Date;

public class ReporteVentasResultadoDTO extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;
	
	private Date fecha;
	private Long totalEfectivo= new Long(0);
	private Long totalCC= new Long(0);
	private Long totalDebito= new Long(0);
	private Long totalCredito= new Long(0);
	private Long total= new Long(0);
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getTotalEfectivo() {
		return totalEfectivo;
	}

	public void setTotalEfectivo(Long totalEfectivo) {
		this.totalEfectivo = totalEfectivo;
	}

	public Long getTotalCC() {
		return totalCC;
	}

	public void setTotalCC(Long totalCC) {
		this.totalCC = totalCC;
	}

	public Long getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(Long totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Long getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Long totalCredito) {
		this.totalCredito = totalCredito;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((totalCC == null) ? 0 : totalCC.hashCode());
		result = prime * result
				+ ((totalCredito == null) ? 0 : totalCredito.hashCode());
		result = prime * result
				+ ((totalDebito == null) ? 0 : totalDebito.hashCode());
		result = prime * result
				+ ((totalEfectivo == null) ? 0 : totalEfectivo.hashCode());
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
		if (!(obj instanceof ReporteVentasResultadoDTO)) {
			return false;
		}
		ReporteVentasResultadoDTO other = (ReporteVentasResultadoDTO) obj;
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
			return false;
		}
		if (totalCC == null) {
			if (other.totalCC != null) {
				return false;
			}
		} else if (!totalCC.equals(other.totalCC)) {
			return false;
		}
		if (totalCredito == null) {
			if (other.totalCredito != null) {
				return false;
			}
		} else if (!totalCredito.equals(other.totalCredito)) {
			return false;
		}
		if (totalDebito == null) {
			if (other.totalDebito != null) {
				return false;
			}
		} else if (!totalDebito.equals(other.totalDebito)) {
			return false;
		}
		if (totalEfectivo == null) {
			if (other.totalEfectivo != null) {
				return false;
			}
		} else if (!totalEfectivo.equals(other.totalEfectivo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReporteVentasResultadoDTO [fecha=" + fecha + ", totalEfectivo="
				+ totalEfectivo + ", totalCC=" + totalCC + ", totalDebito="
				+ totalDebito + ", totalCredito=" + totalCredito + ", total="
				+ total + "]";
	}
	
	

}
