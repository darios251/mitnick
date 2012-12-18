package com.mitnick.servicio.servicios.dtos;


public class ConsultaClienteDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String documento;
	
	private String nombre;
	
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

	@Override
	public String toString() {
		return "ConsultaClienteDto [documento=" + documento + ", nombre="
				+ nombre + "]";
	}
	
}
