package com.mitnick.utils.dtos;

public class ConfiguracionImpresoraDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String domicilioComercial1;
	
	private String domicilioComercial2;
	
	private String domicilioComercial3;
	
	private String domicilioFiscal1;
	
	private String domicilioFiscal2;
	
	private String domicilioFiscal3;
	
	private String ingresosBrutos1;
	
	private String ingresosBrutos2;
	
	private String ingresosBrutos3;
	
	private String fechaInicioActividades;

	public String getDomicilioComercial1() {
		return domicilioComercial1;
	}

	public void setDomicilioComercial1(String domicilioComercial1) {
		this.domicilioComercial1 = domicilioComercial1;
	}

	public String getDomicilioComercial2() {
		return domicilioComercial2;
	}

	public void setDomicilioComercial2(String domicilioComercial2) {
		this.domicilioComercial2 = domicilioComercial2;
	}

	public String getDomicilioComercial3() {
		return domicilioComercial3;
	}

	public void setDomicilioComercial3(String domicilioComercial3) {
		this.domicilioComercial3 = domicilioComercial3;
	}

	public String getDomicilioFiscal1() {
		return domicilioFiscal1;
	}

	public void setDomicilioFiscal1(String domicilioFiscal1) {
		this.domicilioFiscal1 = domicilioFiscal1;
	}

	public String getDomicilioFiscal2() {
		return domicilioFiscal2;
	}

	public void setDomicilioFiscal2(String domicilioFiscal2) {
		this.domicilioFiscal2 = domicilioFiscal2;
	}

	public String getDomicilioFiscal3() {
		return domicilioFiscal3;
	}

	public void setDomicilioFiscal3(String domicilioFiscal3) {
		this.domicilioFiscal3 = domicilioFiscal3;
	}

	public String getIngresosBrutos1() {
		return ingresosBrutos1;
	}

	public void setIngresosBrutos1(String ingresosBrutos1) {
		this.ingresosBrutos1 = ingresosBrutos1;
	}

	public String getIngresosBrutos2() {
		return ingresosBrutos2;
	}

	public void setIngresosBrutos2(String ingresosBrutos2) {
		this.ingresosBrutos2 = ingresosBrutos2;
	}

	public String getIngresosBrutos3() {
		return ingresosBrutos3;
	}

	public void setIngresosBrutos3(String ingresosBrutos3) {
		this.ingresosBrutos3 = ingresosBrutos3;
	}

	public String getFechaInicioActividades() {
		return fechaInicioActividades;
	}

	public void setFechaInicioActividades(String fechaInicioActividades) {
		this.fechaInicioActividades = fechaInicioActividades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((domicilioComercial1 == null) ? 0 : domicilioComercial1
						.hashCode());
		result = prime
				* result
				+ ((domicilioComercial2 == null) ? 0 : domicilioComercial2
						.hashCode());
		result = prime
				* result
				+ ((domicilioComercial3 == null) ? 0 : domicilioComercial3
						.hashCode());
		result = prime
				* result
				+ ((domicilioFiscal1 == null) ? 0 : domicilioFiscal1.hashCode());
		result = prime
				* result
				+ ((domicilioFiscal2 == null) ? 0 : domicilioFiscal2.hashCode());
		result = prime
				* result
				+ ((domicilioFiscal3 == null) ? 0 : domicilioFiscal3.hashCode());
		result = prime
				* result
				+ ((fechaInicioActividades == null) ? 0
						: fechaInicioActividades.hashCode());
		result = prime * result
				+ ((ingresosBrutos1 == null) ? 0 : ingresosBrutos1.hashCode());
		result = prime * result
				+ ((ingresosBrutos2 == null) ? 0 : ingresosBrutos2.hashCode());
		result = prime * result
				+ ((ingresosBrutos3 == null) ? 0 : ingresosBrutos3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfiguracionImpresoraDto other = (ConfiguracionImpresoraDto) obj;
		if (domicilioComercial1 == null) {
			if (other.domicilioComercial1 != null)
				return false;
		} else if (!domicilioComercial1.equals(other.domicilioComercial1))
			return false;
		if (domicilioComercial2 == null) {
			if (other.domicilioComercial2 != null)
				return false;
		} else if (!domicilioComercial2.equals(other.domicilioComercial2))
			return false;
		if (domicilioComercial3 == null) {
			if (other.domicilioComercial3 != null)
				return false;
		} else if (!domicilioComercial3.equals(other.domicilioComercial3))
			return false;
		if (domicilioFiscal1 == null) {
			if (other.domicilioFiscal1 != null)
				return false;
		} else if (!domicilioFiscal1.equals(other.domicilioFiscal1))
			return false;
		if (domicilioFiscal2 == null) {
			if (other.domicilioFiscal2 != null)
				return false;
		} else if (!domicilioFiscal2.equals(other.domicilioFiscal2))
			return false;
		if (domicilioFiscal3 == null) {
			if (other.domicilioFiscal3 != null)
				return false;
		} else if (!domicilioFiscal3.equals(other.domicilioFiscal3))
			return false;
		if (fechaInicioActividades == null) {
			if (other.fechaInicioActividades != null)
				return false;
		} else if (!fechaInicioActividades.equals(other.fechaInicioActividades))
			return false;
		if (ingresosBrutos1 == null) {
			if (other.ingresosBrutos1 != null)
				return false;
		} else if (!ingresosBrutos1.equals(other.ingresosBrutos1))
			return false;
		if (ingresosBrutos2 == null) {
			if (other.ingresosBrutos2 != null)
				return false;
		} else if (!ingresosBrutos2.equals(other.ingresosBrutos2))
			return false;
		if (ingresosBrutos3 == null) {
			if (other.ingresosBrutos3 != null)
				return false;
		} else if (!ingresosBrutos3.equals(other.ingresosBrutos3))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConfiguracionImpresoraDto [domicilioComercial1="
				+ domicilioComercial1 + ", domicilioComercial2="
				+ domicilioComercial2 + ", domicilioComercial3="
				+ domicilioComercial3 + ", domicilioFiscal1="
				+ domicilioFiscal1 + ", domicilioFiscal2=" + domicilioFiscal2
				+ ", domicilioFiscal3=" + domicilioFiscal3
				+ ", ingresosBrutos1=" + ingresosBrutos1 + ", ingresosBrutos2="
				+ ingresosBrutos2 + ", ingresosBrutos3=" + ingresosBrutos3
				+ ", fechaInicioActividades=" + fechaInicioActividades + "]";
	}
	
}
