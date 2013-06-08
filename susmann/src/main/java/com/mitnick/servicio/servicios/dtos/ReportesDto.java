package com.mitnick.servicio.servicios.dtos;

import java.util.Date;

import com.mitnick.servicio.servicios.dtos.ServicioBaseDto;

public class ReportesDto extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private boolean orderByCode = false;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public boolean isOrderByCode() {
		return orderByCode;
	}

	public void setOrderByCode(boolean orderByCode) {
		this.orderByCode = orderByCode;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return "ReportesDto [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", orderByCode=" + orderByCode + "]";
	}
	
}
