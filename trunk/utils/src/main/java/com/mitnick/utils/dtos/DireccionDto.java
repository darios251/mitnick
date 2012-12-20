package com.mitnick.utils.dtos;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.Required;
import org.hibernate.validator.constraints.MitnickField.FieldType;

public class DireccionDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@MitnickField(required=true, fieldType=FieldType.APHANUMERIC, min=3, max=255)
	private String domicilio;
	
	@Required
	private CiudadDto ciudad;
	
	@MitnickField(required=true, propertyName="clienteNuevoPanel.etiqueta.codigoPostal",fieldType=FieldType.INTEGER, min=3, max=5)
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
