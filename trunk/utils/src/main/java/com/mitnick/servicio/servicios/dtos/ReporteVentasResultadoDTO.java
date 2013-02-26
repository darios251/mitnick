package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class ReporteVentasResultadoDTO extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private Date fecha;
	private BigDecimal totalEfectivo= new BigDecimal(0);
	private BigDecimal totalCC= new BigDecimal(0);
	private BigDecimal totalTarjeta= new BigDecimal(0);
	private BigDecimal totalDebito= new BigDecimal(0);
	private BigDecimal totalCredito= new BigDecimal(0);
	private BigDecimal totalNC= new BigDecimal(0);
	private BigDecimal total= new BigDecimal(0);
	

	public BigDecimal getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(BigDecimal totalDebito) {
		this.totalDebito = totalDebito;
	}

	public BigDecimal getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(BigDecimal totalCredito) {
		this.totalCredito = totalCredito;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotalEfectivo() {
		return totalEfectivo;
	}

	public void setTotalEfectivo(BigDecimal totalEfectivo) {
		this.totalEfectivo = totalEfectivo;
	}

	public BigDecimal getTotalCC() {
		return totalCC;
	}

	public void setTotalCC(BigDecimal totalCC) {
		this.totalCC = totalCC;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalTarjeta() {
		return totalTarjeta;
	}

	public void setTotalTarjeta(BigDecimal totalTarjeta) {
		this.totalTarjeta = totalTarjeta;
	}

	public BigDecimal getTotalNC() {
		return totalNC;
	}

	public void setTotalNC(BigDecimal totalNC) {
		this.totalNC = totalNC;
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
		result = prime * result + ((totalNC == null) ? 0 : totalNC.hashCode());
		result = prime * result
				+ ((totalTarjeta == null) ? 0 : totalTarjeta.hashCode());
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
		if (totalNC == null) {
			if (other.totalNC != null) {
				return false;
			}
		} else if (!totalNC.equals(other.totalNC)) {
			return false;
		}
		if (totalTarjeta == null) {
			if (other.totalTarjeta != null) {
				return false;
			}
		} else if (!totalTarjeta.equals(other.totalTarjeta)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReporteVentasResultadoDTO [fecha=" + fecha + ", totalEfectivo="
				+ totalEfectivo + ", totalCC=" + totalCC + ", totalTarjeta="
				+ totalTarjeta + ", totalDebito=" + totalDebito
				+ ", totalCredito=" + totalCredito + ", totalNC=" + totalNC
				+ ", total=" + total + "]";
	}

}