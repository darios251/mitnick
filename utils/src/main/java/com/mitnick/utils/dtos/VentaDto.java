package com.mitnick.utils.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.MitnickConstants;

public class VentaDto extends BaseDto {
	
	private static final long serialVersionUID = 1L;

	private List<ProductoVentaDto> productos;
	
	private BigDecimal subTotal = new BigDecimal(0);
	
	private BigDecimal total = new BigDecimal(0);
	
	private BigDecimal impuesto = new BigDecimal(0);
	
	private DescuentoDto descuento;
	
	private BigDecimal ajusteRedondeo;
	
	private List<PagoDto> pagos; 
	
	private ClienteDto cliente;
	
	private boolean pagado;
	
	private BigDecimal totalPagado = new BigDecimal(0);
	
	private BigDecimal faltaPagar = new BigDecimal(0);
	
	private BigDecimal vuelto = new BigDecimal(0);
	
	private List<CuotaDto> cuotas;
	
	private String numeroTicket;
	
	private String numeroTicketOriginal;
	
	private String tipoTicket; 
	
	private boolean impresa;
	
	private boolean cancelada;
	
	private boolean printed = false;
	
	private TipoCompradorDto tipoResponsabilidad = new TipoCompradorDto(MitnickConstants.TipoComprador.CONSUMIDOR_FINAL, MitnickConstants.TipoComprador.CONSUMIDOR_FINAL_DESC);

	private int tipo;
	
	private Date fecha;
	
	private VendedorDto vendedor;
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

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
	
	public void setNumeroTicket(String numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	
	public String getNumeroTicket() {
		return numeroTicket;
	}
	
	public void setTipoTicket(String tipoTicket) {
		this.tipoTicket = tipoTicket;
	}
	
	public String getTipoTicket() {
		return tipoTicket;
	}
	
	public boolean isCancelada() {
		return cancelada;
	}
	
	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}

	public boolean isImpresa() {
		return impresa;
	}
	
	public void setImpresa(boolean impresa) {
		this.impresa = impresa;
	}
	
	public BigDecimal getAjusteRedondeo() {
		return ajusteRedondeo;
	}
	
	public void setAjusteRedondeo(BigDecimal ajusteRedondeo) {
		this.ajusteRedondeo = ajusteRedondeo;
	}

	public void setTipoResponsabilidad(TipoCompradorDto tipoResponsabilidad) {
		this.tipoResponsabilidad = tipoResponsabilidad;
	}
	
	public TipoCompradorDto getTipoResponsabilidad() {
		return tipoResponsabilidad;
	}

	public String getNumeroTicketOriginal() {
		return numeroTicketOriginal;
	}

	public void setNumeroTicketOriginal(String numeroTicketOriginal) {
		this.numeroTicketOriginal = numeroTicketOriginal;
	}

	public List<PagoDto> getPagoNotaCredito(){
		Iterator<PagoDto> pagos = getPagos().iterator();
		List<PagoDto> nCreditos = new ArrayList<PagoDto>();
		while (pagos.hasNext()){
			PagoDto pago = pagos.next();
			if (MitnickConstants.Medio_Pago.NOTA_CREDITO.equals(pago.getMedioPago().getCodigo()))
				nCreditos.add(pago);
		}
		return nCreditos;
	}
	
	public BigDecimal getPagoContado(){
		BigDecimal total = new BigDecimal(0);
		Iterator<PagoDto> pagos = getPagos().iterator();
		while (pagos.hasNext()){
			PagoDto pago = pagos.next();
			if (!MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo()) && !MitnickConstants.Medio_Pago.NOTA_CREDITO.equals(pago.getMedioPago().getCodigo()))
				total = total.add(pago.getMonto());
		}
		return total;
	}
	
	public BigDecimal getPagoACuenta(){
		BigDecimal total = new BigDecimal(0);
		Iterator<PagoDto> pagos = getPagos().iterator();
		while (pagos.hasNext()){
			PagoDto pago = pagos.next();
			if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago.getMedioPago().getCodigo()))
				total = total.add(pago.getMonto());
		}
		return total;
	}
	
	public boolean isVenta(){
		return tipo == MitnickConstants.VENTA;	
	}
	
	public boolean isDevolucion(){
		return tipo == MitnickConstants.DEVOLUCION;
	}
	
	@Override
	public String toString() {
		return "VentaDto [productos=" + productos + ", subTotal=" + subTotal + ", total=" + total + ", impuesto=" + impuesto
				+ ", descuento=" + descuento + ", ajusteRedondeo=" + ajusteRedondeo + ", pagos=" + pagos + ", cliente=" + cliente
				+ ", pagado=" + pagado + ", totalPagado=" + totalPagado + ", faltaPagar=" + faltaPagar + ", vuelto=" + vuelto
				+ ", cuotas=" + cuotas + ", numeroTicket=" + numeroTicket + ", numeroTicketOriginal=" + numeroTicketOriginal
				+ ", tipoTicket=" + tipoTicket + ", impresa=" + impresa + ", cancelada=" + cancelada + ", printed=" + printed
				+ ", tipoResponsabilidad=" + tipoResponsabilidad + ", tipo=" + tipo + ", fecha=" + fecha + ", vendedor="
				+ vendedor + "]";
	}

	public boolean isPrinted() {
		return printed;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ajusteRedondeo == null) ? 0 : ajusteRedondeo.hashCode());
		result = prime * result + (cancelada ? 1231 : 1237);
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((cuotas == null) ? 0 : cuotas.hashCode());
		result = prime * result + ((descuento == null) ? 0 : descuento.hashCode());
		result = prime * result + ((faltaPagar == null) ? 0 : faltaPagar.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + (impresa ? 1231 : 1237);
		result = prime * result + ((impuesto == null) ? 0 : impuesto.hashCode());
		result = prime * result + ((numeroTicket == null) ? 0 : numeroTicket.hashCode());
		result = prime * result + ((numeroTicketOriginal == null) ? 0 : numeroTicketOriginal.hashCode());
		result = prime * result + (pagado ? 1231 : 1237);
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
		result = prime * result + (printed ? 1231 : 1237);
		result = prime * result + ((productos == null) ? 0 : productos.hashCode());
		result = prime * result + ((subTotal == null) ? 0 : subTotal.hashCode());
		result = prime * result + tipo;
		result = prime * result + ((tipoResponsabilidad == null) ? 0 : tipoResponsabilidad.hashCode());
		result = prime * result + ((tipoTicket == null) ? 0 : tipoTicket.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((totalPagado == null) ? 0 : totalPagado.hashCode());
		result = prime * result + ((vendedor == null) ? 0 : vendedor.hashCode());
		result = prime * result + ((vuelto == null) ? 0 : vuelto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VentaDto)) {
			return false;
		}
		VentaDto other = (VentaDto) obj;
		if (ajusteRedondeo == null) {
			if (other.ajusteRedondeo != null) {
				return false;
			}
		} else if (!ajusteRedondeo.equals(other.ajusteRedondeo)) {
			return false;
		}
		if (cancelada != other.cancelada) {
			return false;
		}
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
			return false;
		}
		if (cuotas == null) {
			if (other.cuotas != null) {
				return false;
			}
		} else if (!cuotas.equals(other.cuotas)) {
			return false;
		}
		if (descuento == null) {
			if (other.descuento != null) {
				return false;
			}
		} else if (!descuento.equals(other.descuento)) {
			return false;
		}
		if (faltaPagar == null) {
			if (other.faltaPagar != null) {
				return false;
			}
		} else if (!faltaPagar.equals(other.faltaPagar)) {
			return false;
		}
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (impresa != other.impresa) {
			return false;
		}
		if (impuesto == null) {
			if (other.impuesto != null) {
				return false;
			}
		} else if (!impuesto.equals(other.impuesto)) {
			return false;
		}
		if (numeroTicket == null) {
			if (other.numeroTicket != null) {
				return false;
			}
		} else if (!numeroTicket.equals(other.numeroTicket)) {
			return false;
		}
		if (numeroTicketOriginal == null) {
			if (other.numeroTicketOriginal != null) {
				return false;
			}
		} else if (!numeroTicketOriginal.equals(other.numeroTicketOriginal)) {
			return false;
		}
		if (pagado != other.pagado) {
			return false;
		}
		if (pagos == null) {
			if (other.pagos != null) {
				return false;
			}
		} else if (!pagos.equals(other.pagos)) {
			return false;
		}
		if (printed != other.printed) {
			return false;
		}
		if (productos == null) {
			if (other.productos != null) {
				return false;
			}
		} else if (!productos.equals(other.productos)) {
			return false;
		}
		if (subTotal == null) {
			if (other.subTotal != null) {
				return false;
			}
		} else if (!subTotal.equals(other.subTotal)) {
			return false;
		}
		if (tipo != other.tipo) {
			return false;
		}
		if (tipoResponsabilidad == null) {
			if (other.tipoResponsabilidad != null) {
				return false;
			}
		} else if (!tipoResponsabilidad.equals(other.tipoResponsabilidad)) {
			return false;
		}
		if (tipoTicket == null) {
			if (other.tipoTicket != null) {
				return false;
			}
		} else if (!tipoTicket.equals(other.tipoTicket)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
			return false;
		}
		if (totalPagado == null) {
			if (other.totalPagado != null) {
				return false;
			}
		} else if (!totalPagado.equals(other.totalPagado)) {
			return false;
		}
		if (vendedor == null) {
			if (other.vendedor != null) {
				return false;
			}
		} else if (!vendedor.equals(other.vendedor)) {
			return false;
		}
		if (vuelto == null) {
			if (other.vuelto != null) {
				return false;
			}
		} else if (!vuelto.equals(other.vuelto)) {
			return false;
		}
		return true;
	}

	public VendedorDto getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorDto vendedor) {
		this.vendedor = vendedor;
	}
	
}
