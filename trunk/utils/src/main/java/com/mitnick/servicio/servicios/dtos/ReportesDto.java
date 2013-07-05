package com.mitnick.servicio.servicios.dtos;

import java.util.Date;

import com.mitnick.utils.PropertiesManager;

public class ReportesDto extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private int caja;
	

	public ReportesDto() {
		this.caja = PropertiesManager.getPropertyAsInteger("application.caja.numero");
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return "ReportesDto [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", caja=" + caja + "]";
	}

	public int getCaja() {
		return caja;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + caja;
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
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
		if (!(obj instanceof ReportesDto)) {
			return false;
		}
		ReportesDto other = (ReportesDto) obj;
		if (caja != other.caja) {
			return false;
		}
		if (fechaFin == null) {
			if (other.fechaFin != null) {
				return false;
			}
		} else if (!fechaFin.equals(other.fechaFin)) {
			return false;
		}
		if (fechaInicio == null) {
			if (other.fechaInicio != null) {
				return false;
			}
		} else if (!fechaInicio.equals(other.fechaInicio)) {
			return false;
		}
		return true;
	}
	public void setCaja(int caja) {
		this.caja = caja;
	}
	
}
