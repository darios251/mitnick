package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReporteFacturasDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private Date fecha;
	private int corteZ;
	
	private BigDecimal netoA;
	private BigDecimal ivaA;
	private BigDecimal totalA;
	
	private BigDecimal netoNA;
	private BigDecimal ivaNA;
	private BigDecimal totalNA;
	
	private BigDecimal totalB;
	
	private BigDecimal totalNB;
	
	private BigDecimal total;
	
	private List<FacturaDto> facturas;

	public Date getFecha() {
		return fecha;
	}

	public List<FacturaDto> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaDto> facturas) {
		this.facturas = facturas;
	}

	public void addfactura(FacturaDto factura) {
		if (this.facturas == null)
			facturas = new ArrayList<FacturaDto>();
		facturas.add(factura);
	}
	
	public BigDecimal getNetoA() {
		return netoA;
	}

	public void setNetoA(BigDecimal netoA) {
		this.netoA = netoA;
	}

	public BigDecimal getIvaA() {
		return ivaA;
	}

	public void setIvaA(BigDecimal ivaA) {
		this.ivaA = ivaA;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCorteZ() {
		return corteZ;
	}

	public void setCorteZ(int corteZ) {
		this.corteZ = corteZ;
	}

	public BigDecimal getTotalA() {
		return totalA;
	}

	public void setTotalA(BigDecimal totalA) {
		this.totalA = totalA;
	}

	public BigDecimal getTotalB() {
		return totalB;
	}

	public void setTotalB(BigDecimal totalB) {
		this.totalB = totalB;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + corteZ;
		result = prime * result
				+ ((facturas == null) ? 0 : facturas.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((ivaA == null) ? 0 : ivaA.hashCode());
		result = prime * result + ((netoA == null) ? 0 : netoA.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((totalA == null) ? 0 : totalA.hashCode());
		result = prime * result + ((totalB == null) ? 0 : totalB.hashCode());
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
		if (!(obj instanceof ReporteFacturasDto)) {
			return false;
		}
		ReporteFacturasDto other = (ReporteFacturasDto) obj;
		if (corteZ != other.corteZ) {
			return false;
		}
		if (facturas == null) {
			if (other.facturas != null) {
				return false;
			}
		} else if (!facturas.equals(other.facturas)) {
			return false;
		}
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (ivaA == null) {
			if (other.ivaA != null) {
				return false;
			}
		} else if (!ivaA.equals(other.ivaA)) {
			return false;
		}
		if (netoA == null) {
			if (other.netoA != null) {
				return false;
			}
		} else if (!netoA.equals(other.netoA)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
			return false;
		}
		if (totalA == null) {
			if (other.totalA != null) {
				return false;
			}
		} else if (!totalA.equals(other.totalA)) {
			return false;
		}
		if (totalB == null) {
			if (other.totalB != null) {
				return false;
			}
		} else if (!totalB.equals(other.totalB)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReporteFacturasDto [fecha=" + fecha + ", corteZ=" + corteZ
				+ ", netoA=" + netoA + ", ivaA=" + ivaA + ", totalA=" + totalA
				+ ", totalB=" + totalB + ", total=" + total + ", facturas="
				+ facturas + "]";
	}

	public BigDecimal getNetoNA() {
		return netoNA;
	}

	public void setNetoNA(BigDecimal netoNA) {
		this.netoNA = netoNA;
	}

	public BigDecimal getIvaNA() {
		return ivaNA;
	}

	public void setIvaNA(BigDecimal ivaNA) {
		this.ivaNA = ivaNA;
	}

	public BigDecimal getTotalNA() {
		return totalNA;
	}

	public void setTotalNA(BigDecimal totalNA) {
		this.totalNA = totalNA;
	}

	public BigDecimal getTotalNB() {
		return totalNB;
	}

	public void setTotalNB(BigDecimal totalNB) {
		this.totalNB = totalNB;
	}

}
