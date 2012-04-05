package com.mitnick.utils.dtos;

public class TipoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return this.descripcion;
	}
}
