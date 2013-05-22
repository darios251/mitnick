package com.mitnick.utils.dtos;

import java.math.BigDecimal;

import com.mitnick.utils.MitnickConstants;

public class PagoDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private MedioPagoDto medioPago;
	
	private BigDecimal monto;
	
	private boolean comprobante = false;
	
	private String nroNC;

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

	public boolean isComprobante() {
		return comprobante;
	}

	public void setComprobante(boolean comprobante) {
		this.comprobante = comprobante;
	}

	public String getNroNC() {
		return nroNC;
	}

	public void setNroNC(String nroNC) {
		this.nroNC = nroNC;
	}

	public boolean isEfectivo(){
		return MitnickConstants.Medio_Pago.EFECTIVO.equals(this.getMedioPago().getCodigo());
	}

	@Override
	public String toString() {
		return "PagoDto [medioPago=" + medioPago + ", monto=" + monto
				+ ", comprobante=" + comprobante + ", nroNC=" + nroNC + "]";
	}
	
}
