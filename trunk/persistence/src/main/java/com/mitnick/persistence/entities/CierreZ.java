package com.mitnick.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.appfuse.model.BaseObject;

@Entity(name = "CierreZ")
public class CierreZ extends BaseObject {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;

	@Column(name = "numero", nullable = false)
	private Integer numero;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	@Column(name= "numeroCaja", nullable = false)
	private int numeroCaja;
	
	public Long getId() {
		return id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + numeroCaja;
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
		if (!(obj instanceof CierreZ)) {
			return false;
		}
		CierreZ other = (CierreZ) obj;
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
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (numeroCaja != other.numeroCaja) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CierreZ [id=" + id + ", numero=" + numero + ", fecha=" + fecha + ", numeroCaja=" + numeroCaja + "]";
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}
	
}
