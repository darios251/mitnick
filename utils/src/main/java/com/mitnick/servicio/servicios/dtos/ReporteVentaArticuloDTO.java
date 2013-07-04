package com.mitnick.servicio.servicios.dtos;

import java.math.BigDecimal;

public class ReporteVentaArticuloDTO extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;
	
	private String fecha;
	private String productoDescripcion;
	private String productoMarca;
	private String productoCodigo;
	private int cantidad;
	private BigDecimal descuento;
	private BigDecimal total;
	private String talle;
		
	public String getProductoDescripcion() {
		return productoDescripcion;
	}
	public void setProductoDescripcion(String productoDescripcion) {
		this.productoDescripcion = productoDescripcion;
	}
	public String getProductoCodigo() {
		return productoCodigo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public String getTalle() {
		return talle;
	}
	public void setTalle(String talle) {
		this.talle = talle;
	}
	public void setProductoCodigo(String productoCodigo) {
		this.productoCodigo = productoCodigo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public String getProductoMarca() {
		return productoMarca;
	}
	public void setProductoMarca(String productoMarca) {
		this.productoMarca = productoMarca;
	}
	@Override
	public String toString() {
		return "ReporteVentaArticuloDTO [fecha=" + fecha + ", productoDescripcion=" + productoDescripcion + ", productoMarca="
				+ productoMarca + ", productoCodigo=" + productoCodigo + ", cantidad=" + cantidad + ", descuento=" + descuento
				+ ", total=" + total + ", talle=" + talle + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((descuento == null) ? 0 : descuento.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((productoCodigo == null) ? 0 : productoCodigo.hashCode());
		result = prime * result + ((productoDescripcion == null) ? 0 : productoDescripcion.hashCode());
		result = prime * result + ((productoMarca == null) ? 0 : productoMarca.hashCode());
		result = prime * result + ((talle == null) ? 0 : talle.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		if (!(obj instanceof ReporteVentaArticuloDTO)) {
			return false;
		}
		ReporteVentaArticuloDTO other = (ReporteVentaArticuloDTO) obj;
		if (cantidad != other.cantidad) {
			return false;
		}
		if (descuento == null) {
			if (other.descuento != null) {
				return false;
			}
		} else if (!descuento.equals(other.descuento)) {
			return false;
		}
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (productoCodigo == null) {
			if (other.productoCodigo != null) {
				return false;
			}
		} else if (!productoCodigo.equals(other.productoCodigo)) {
			return false;
		}
		if (productoDescripcion == null) {
			if (other.productoDescripcion != null) {
				return false;
			}
		} else if (!productoDescripcion.equals(other.productoDescripcion)) {
			return false;
		}
		if (productoMarca == null) {
			if (other.productoMarca != null) {
				return false;
			}
		} else if (!productoMarca.equals(other.productoMarca)) {
			return false;
		}
		if (talle == null) {
			if (other.talle != null) {
				return false;
			}
		} else if (!talle.equals(other.talle)) {
			return false;
		}
		if (total == null) {
			if (other.total != null) {
				return false;
			}
		} else if (!total.equals(other.total)) {
			return false;
		}
		return true;
	}
	
	
	

}
