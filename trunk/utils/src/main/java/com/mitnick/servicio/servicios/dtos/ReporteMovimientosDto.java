package com.mitnick.servicio.servicios.dtos;

import java.util.Date;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;

public class ReporteMovimientosDto extends ServicioBaseDto {
	
	private static final long serialVersionUID = 1L;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String descripcion;
	
	private String codigo;
	
	private MarcaDto marca;
	
	private TipoDto tipo;
	
	private boolean agrupadoMes=false; 
	
	private boolean agrupadoProducto=false; 
	
	public boolean isAgrupadoMes() {
		return agrupadoMes;
	}

	public void setAgrupadoMes(boolean agrupadoMes) {
		this.agrupadoMes = agrupadoMes;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public MarcaDto getMarca() {
		return marca;
	}

	public void setMarca(MarcaDto marca) {
		this.marca = marca;
	}

	public TipoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDto tipo) {
		this.tipo = tipo;
	}

	public boolean isAgrupadoProducto() {
		return agrupadoProducto;
	}

	public void setAgrupadoProducto(boolean agrupadoProducto) {
		this.agrupadoProducto = agrupadoProducto;
	}

	@Override
	public String toString() {
		return "ReporteMovimientosDto [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", descripcion=" + descripcion
				+ ", codigo=" + codigo + ", marca=" + marca + ", tipo=" + tipo + ", agrupadoMes=" + agrupadoMes
				+ ", agrupadoProducto=" + agrupadoProducto + "]";
	}
	
}
