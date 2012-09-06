package com.mitnick.utils.dtos;

import com.mitnick.utils.MitnickConstants;

public class MedioPagoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean isCuentaCorriente(){
		return MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(codigo);	
	}
	
	@Override // es para que se muestre bien en el combo
	public String toString() {
		return descripcion;
	}
}
