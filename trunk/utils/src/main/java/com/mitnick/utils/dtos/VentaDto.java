package com.mitnick.utils.dtos;

import java.math.BigDecimal;
import java.util.List;

public class VentaDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private List<ProductoVentaDto> productos;
	
	private BigDecimal subTotal;
	
	private BigDecimal total;
	
	private BigDecimal impuesto;
	
	private BigDecimal descuento;
	
	private List<PagoDto> pagos; 
	
	private ClienteDto cliente;
	
	private boolean pagado;
	
	private BigDecimal totalPagado;
	
	private BigDecimal faltaPagar;
	
	public List<ProductoVentaDto> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoVentaDto> productos) {
		this.productos = productos;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public List<PagoDto> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoDto> pagos) {
		this.pagos = pagos;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
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
}
