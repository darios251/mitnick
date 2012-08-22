package com.mitnick.servicio.servicios.dtos;

import java.util.Date;

public class ReportesDto extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;
	
	private Date fechaInicio;
	
	private Date fechaFin;

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
		return "ReporteVentaDto [fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + "]";
	}
	
}
