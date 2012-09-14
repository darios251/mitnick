package com.mitnick.utils.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class CuotaDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private Date fecha_pagar;
	
	private BigDecimal total;
	
	private int nroCuota;
	
	private ClienteDto clienteDto;

	public Date getFecha_pagar() {
		return fecha_pagar;
	}

	public void setFecha_pagar(Date fecha_pagar) {
		this.fecha_pagar = fecha_pagar;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getNroCuota() {
		return nroCuota;
	}

	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	
}
