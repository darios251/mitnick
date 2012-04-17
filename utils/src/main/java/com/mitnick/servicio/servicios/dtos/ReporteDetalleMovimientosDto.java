package com.mitnick.servicio.servicios.dtos;

import java.util.Date;

import com.mitnick.utils.dtos.ProductoDto;

public class ReporteDetalleMovimientosDto extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private ProductoDto producto;

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

	public ProductoDto getProducto() {
		return producto;
	}

	public void setProducto(ProductoDto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "ReporteMovimientosDto [fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", producto=" + producto + "]";
	}
	
}
