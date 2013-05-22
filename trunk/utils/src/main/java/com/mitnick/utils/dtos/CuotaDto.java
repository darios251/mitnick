package com.mitnick.utils.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;

public class CuotaDto extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	@MitnickField(fieldType=FieldType.DATE)
	private String fecha_pagar;
		
	private BigDecimal total;
	
	private int nroCuota;

	private String descripcion = "";
	
	private ClienteDto clienteDto;

	private List<PagoDto> pagos = new ArrayList<PagoDto>();
	
	private BigDecimal totalPagado;
	private BigDecimal faltaPagar;
	private boolean pagado;
	
	public String getFecha_pagar() {
		return fecha_pagar;
	}

	public void setFecha_pagar(String fecha_pagar) {
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

	public List<PagoDto> getPagos() {
		return pagos;
	}

	public List<PagoDto> getPagosComprobante() {
		List<PagoDto> pagosComprobante = new ArrayList<PagoDto>();
		if (getPagos()!=null) {
			Iterator<PagoDto> pagosIt = getPagos().iterator();
			while (pagosIt.hasNext()){
				PagoDto pago = pagosIt.next();
				if (!pago.isComprobante())
					pagosComprobante.add(pago);
			}
		}
		return pagosComprobante;
	}
	
	public BigDecimal getPagoComprobante() {
		BigDecimal monto = new BigDecimal(0);
		if (getPagos()!=null) {
			Iterator<PagoDto> pagosIt = getPagos().iterator();
			while (pagosIt.hasNext()){
				PagoDto pago = pagosIt.next();
				if (!pago.isComprobante())
					monto = monto.add(pago.getMonto());
			}
		}
		return monto;
	}
	
	public void setPagos(List<PagoDto> pagos) {
		this.pagos = pagos;
	}

	public BigDecimal getTotalPagado() {
		return totalPagado;
	}

	public void setTotalPagado(BigDecimal totalPagado) {
		this.totalPagado = totalPagado;
	}

	public BigDecimal getFaltaPagar() {
		return faltaPagar;
	}

	public void setFaltaPagar(BigDecimal faltaPagar) {
		this.faltaPagar = faltaPagar;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "CuotaDto [fecha_pagar=" + fecha_pagar + ", total=" + total
				+ ", nroCuota=" + nroCuota + ", descripcion=" + descripcion
				+ ", clienteDto=" + clienteDto + ", pagos=" + pagos
				+ ", totalPagado=" + totalPagado + ", faltaPagar=" + faltaPagar
				+ ", pagado=" + pagado + "]";
	}

}
