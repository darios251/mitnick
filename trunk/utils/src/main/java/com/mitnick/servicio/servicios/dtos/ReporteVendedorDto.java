package com.mitnick.servicio.servicios.dtos;


public class ReporteVendedorDto extends ServicioBaseDto {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String nombre;
	private int cantidadProductosVendidos;
	private int cantidadProductosDevueltos;
	public int getCantidadProductosVendidos() {
		return cantidadProductosVendidos;
	}


	public void setCantidadProductosVendidos(int cantidadProductosVendidos) {
		this.cantidadProductosVendidos = cantidadProductosVendidos;
	}


	public int getCantidadProductosDevueltos() {
		return cantidadProductosDevueltos;
	}


	public void setCantidadProductosDevueltos(int cantidadProductosDevueltos) {
		this.cantidadProductosDevueltos = cantidadProductosDevueltos;
	}

	private int cantidadVentas;
	private int cantidadDevoluciones;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidadDevoluciones;
		result = prime * result + cantidadProductosDevueltos;
		result = prime * result + cantidadProductosVendidos;
		result = prime * result + cantidadVentas;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		if (!(obj instanceof ReporteVendedorDto)) {
			return false;
		}
		ReporteVendedorDto other = (ReporteVendedorDto) obj;
		if (cantidadDevoluciones != other.cantidadDevoluciones) {
			return false;
		}
		if (cantidadProductosDevueltos != other.cantidadProductosDevueltos) {
			return false;
		}
		if (cantidadProductosVendidos != other.cantidadProductosVendidos) {
			return false;
		}
		if (cantidadVentas != other.cantidadVentas) {
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
		return true;
	}


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


	public int getCantidadVentas() {
		return cantidadVentas;
	}


	public void setCantidadVentas(int cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}


	public int getCantidadDevoluciones() {
		return cantidadDevoluciones;
	}


	public void setCantidadDevoluciones(int cantidadDevoluciones) {
		this.cantidadDevoluciones = cantidadDevoluciones;
	}

	@Override
	public String toString() {
		return "ReporteVendedorDto [codigo=" + codigo + ", nombre=" + nombre + ", cantidadProductosVendidos="
				+ cantidadProductosVendidos + ", cantidadProductosDevueltos=" + cantidadProductosDevueltos + ", cantidadVentas="
				+ cantidadVentas + ", cantidadDevoluciones=" + cantidadDevoluciones + "]";
	}

}
