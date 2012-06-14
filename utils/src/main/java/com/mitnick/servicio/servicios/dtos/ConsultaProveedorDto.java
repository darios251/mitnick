package com.mitnick.servicio.servicios.dtos;



public class ConsultaProveedorDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String nombre;
	
	private String telefono;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "ConsultaProveedorDto [codigo=" + codigo + ", nombre=" + nombre
				+ ", telefono=" + telefono + "]";
	}
	
}
