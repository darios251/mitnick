package com.mitnick.utils.dtos;

import java.math.BigDecimal;

import com.mitnick.utils.Validator;

public class CreditoDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String nroNC;
	private BigDecimal montoTotal;
	private BigDecimal montoUsado;
	public String getNroNC() {
		return nroNC;
	}
	public void setNroNC(String nroNC) {
		this.nroNC = nroNC;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	public BigDecimal getMontoUsado() {
		return montoUsado;
	}
	public void setMontoUsado(BigDecimal montoUsado) {
		this.montoUsado = montoUsado;
	}
	
	public BigDecimal getDisponible(){
		BigDecimal disponible = montoTotal.subtract(montoUsado);
		if (Validator.isMoreThanZero(disponible))
			return disponible;
		return new BigDecimal(0);
	}
	
	@Override
	public String toString() {
		return "CreditoDto [nroNC=" + nroNC + ", montoTotal=" + montoTotal
				+ ", montoUsado=" + montoUsado + "]";
	}

}
