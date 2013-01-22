package com.mitnick.utils.dtos;

public class TipoCompradorDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String tipoComprador;
	
	private String descripcion;

	public TipoCompradorDto(String tipoComprador, String descripcion) {
		this.tipoComprador = tipoComprador;
		this.descripcion = descripcion;
	}
	
	public String getTipoComprador() {
		return tipoComprador;
	}

	public void setTipoComprador(String tipoComprador) {
		this.tipoComprador = tipoComprador;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return descripcion;
	}
	
}
