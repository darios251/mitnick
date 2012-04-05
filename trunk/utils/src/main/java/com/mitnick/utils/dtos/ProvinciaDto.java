package com.mitnick.utils.dtos;

public class ProvinciaDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
