package com.mitnick.utils.dtos;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;

import com.mitnick.utils.Validator;

public class DireccionDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@MitnickField(fieldType=FieldType.APHANUMERIC, min=3, max=255)
	private String domicilio;
	
	
	private CiudadDto ciudad;
	
	@MitnickField(propertyName="clienteNuevoPanel.etiqueta.codigoPostal",fieldType=FieldType.INTEGER, min=3, max=5)
	private String codigoPostal;

	public String getDomicilio() {
		if (Validator.isNull(domicilio))
			return "";
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

	@Override
	public String toString() {
		return "DireccionDto [domicilio=" + domicilio + ", ciudad=" + ciudad
				+ ", codigoPostal=" + codigoPostal + "]";
	}

}
