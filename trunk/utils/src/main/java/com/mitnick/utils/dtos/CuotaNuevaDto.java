package com.mitnick.utils.dtos;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;

public class CuotaNuevaDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@MitnickField(fieldType=FieldType.DATE, required=true)
	private String fecha;
		
	@MitnickField(fieldType=FieldType.BIGDECIMAL, required=true)
	private String montoCuota;

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setMontoCuota(String montoCuota) {
		this.montoCuota = montoCuota;
	}
	
	public String getMontoCuota() {
		return montoCuota;
	}
	
}
