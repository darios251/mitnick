package com.mitnick.business.servicios.dtos;


public class ConsultaClienteDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String documento;
	
	private String nombre;
	
	private String apellido;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
}
