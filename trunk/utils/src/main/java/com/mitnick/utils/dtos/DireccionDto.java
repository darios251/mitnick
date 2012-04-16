package com.mitnick.utils.dtos;

public class DireccionDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String domicilio;
	
	private CiudadDto ciudad;
	
	private String codigoPostal;

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public CiudadDto getCiudad() {
		return ciudad;
	}

	public void setCiudad(CiudadDto ciudad) {
		this.ciudad = ciudad;
	}

}
