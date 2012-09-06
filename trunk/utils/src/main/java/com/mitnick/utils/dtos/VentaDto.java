package com.mitnick.utils.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;

public class VentaDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private List<ProductoVentaDto> productos;
	
	private BigDecimal subTotal = new BigDecimal(0);
	
	private BigDecimal total = new BigDecimal(0);
	
	private BigDecimal impuesto = new BigDecimal(0);
	
	private DescuentoDto descuento;
	
	private List<PagoDto> pagos; 
	
	private ClienteDto cliente;
	
	private boolean pagado;
	
	private BigDecimal totalPagado = new BigDecimal(0);
	
	private BigDecimal faltaPagar = new BigDecimal(0);
	
	private BigDecimal vuelto = new BigDecimal(0);
	
	private List<CuotaDto> cuotas;
	
	public List<ProductoVentaDto> getProductos() {
		if(productos == null)
			productos = new ArrayList<ProductoVentaDto>();
		return productos;
	}

	public void setProductos(List<ProductoVentaDto> productos) {
		this.productos = productos;
	}
	
	public void addProducto(ProductoVentaDto producto){
		this.getProductos().add(producto);
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

	public List<PagoDto> getPagos() {
		if(pagos == null)
			pagos = new ArrayList<PagoDto>();
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

	public DescuentoDto getDescuento() {
		return descuento;
	}

	public void setDescuento(DescuentoDto descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getVuelto() {
		return vuelto;
	}

	public void setVuelto(BigDecimal vuelto) {
		this.vuelto = vuelto;
	}

	public List<CuotaDto> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotaDto> cuotas) {
		this.cuotas = cuotas;
	}
	
	
}
