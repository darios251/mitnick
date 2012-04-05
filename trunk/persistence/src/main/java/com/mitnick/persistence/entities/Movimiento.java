package com.mitnick.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.appfuse.model.BaseObject;

@Entity(name = "Movimiento")
public class Movimiento extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static int AJUSTE = 1;
	public static int VENTA = 2;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@Column(name = "tipo", nullable = false)
	private int tipo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + tipo;
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
		if (!(obj instanceof Movimiento)) {
			return false;
		}
		Movimiento other = (Movimiento) obj;
		if (cantidad != other.cantidad) {
			return false;
		}
		if (fecha == null) {
			if (other.fecha != null) {
				return false;
			}
		} else if (!fecha.equals(other.fecha)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (tipo != other.tipo) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", fecha=" + fecha + ", cantidad="
				+ cantidad + ", tipo=" + tipo + "]";
	}

}
