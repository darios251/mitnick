package com.mitnick.servicio.servicios.dtos;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;


public class ConsultaProductoDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;
	
	private MarcaDto marca;
	
	private TipoDto tipo;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	@Override
	public String toString() {
		return "ConsultaProductoDto [codigo=" + codigo + ", descripcion="
				+ descripcion + ", marca=" + marca + ", tipo=" + tipo + "]";
	}
	
}
