package com.mitnick.utils.dtos;

import java.math.BigDecimal;

public class PagoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private MedioPagoDto medioPago;
	
	private BigDecimal monto;

	public MedioPagoDto getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioPagoDto medioPago) {
		this.medioPago = medioPago;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
}
