package com.mitnick.servicio.servicios.dtos;

public class ConsultaStockDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;
	
	private Long tipo;
	
	private Long marca;

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

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Long getMarca() {
		return marca;
	}

	public void setMarca(Long marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "ConsultaStockDto [codigo=" + codigo + ", descripcion="
				+ descripcion + ", tipo=" + tipo + ", marca=" + marca + "]";
	}
	
}
