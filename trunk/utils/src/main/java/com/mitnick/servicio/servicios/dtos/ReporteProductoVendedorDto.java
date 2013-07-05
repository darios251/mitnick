package com.mitnick.servicio.servicios.dtos;



public class ReporteProductoVendedorDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String nombre;
	private String productoCodigo;
	private String producto;
	private int cantidadVendida;
	private int cantidadDevuelta;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProductoCodigo() {
		return productoCodigo;
	}
	public void setProductoCodigo(String productoCodigo) {
		this.productoCodigo = productoCodigo;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public int getCantidadVendida() {
		return cantidadVendida;
	}
	public void setCantidadVendida(int cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}
	public int getCantidadDevuelta() {
		return cantidadDevuelta;
	}
	public void setCantidadDevuelta(int cantidadDevuelta) {
		this.cantidadDevuelta = cantidadDevuelta;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidadDevuelta;
		result = prime * result + cantidadVendida;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + ((productoCodigo == null) ? 0 : productoCodigo.hashCode());
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
		if (!(obj instanceof ReporteProductoVendedorDto)) {
			return false;
		}
		ReporteProductoVendedorDto other = (ReporteProductoVendedorDto) obj;
		if (cantidadDevuelta != other.cantidadDevuelta) {
			return false;
		}
		if (cantidadVendida != other.cantidadVendida) {
			return false;
		}
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		if (nombre == null) {
			if (other.nombre != null) {
				return false;
			}
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		if (producto == null) {
			if (other.producto != null) {
				return false;
			}
		} else if (!producto.equals(other.producto)) {
			return false;
		}
		if (productoCodigo == null) {
			if (other.productoCodigo != null) {
				return false;
			}
		} else if (!productoCodigo.equals(other.productoCodigo)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "ReporteProductoVendedorDto [codigo=" + codigo + ", nombre=" + nombre + ", productoCodigo=" + productoCodigo
				+ ", producto=" + producto + ", cantidadVendida=" + cantidadVendida + ", cantidadDevuelta=" + cantidadDevuelta
				+ "]";
	}
	
	

}
