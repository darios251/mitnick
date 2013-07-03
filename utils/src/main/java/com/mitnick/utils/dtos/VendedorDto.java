package com.mitnick.utils.dtos;


public class VendedorDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String nombre;

	private String codigo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
