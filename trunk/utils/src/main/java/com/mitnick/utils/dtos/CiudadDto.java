package com.mitnick.utils.dtos;

public class CiudadDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String descripcion;
	
	private ProvinciaDto prinvinciaDto;

	public ProvinciaDto getPrinvinciaDto() {
		return prinvinciaDto;
	}

	public void setPrinvinciaDto(ProvinciaDto prinvinciaDto) {
		this.prinvinciaDto = prinvinciaDto;
	}

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
